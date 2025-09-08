package br.com.dr1_tp3_crm.CRM.service;

import br.com.dr1_tp3_crm.CRM.model.Cliente;
import br.com.dr1_tp3_crm.CRM.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente1;
    private Cliente cliente2;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        UUID uuid = new UUID(8, 64);
        cliente1 = new Cliente(new UUID(8, 64), "Cliente 1", "cliente1@mail.com", "219999999999");
        cliente2 = new Cliente(new UUID(8, 64), "Cliente 2", "cliente2@mail.com", "219999999999");
    }


    @Test
    void salvar() {
        when(clienteRepository.save(cliente1)).thenReturn(cliente1);
        Cliente resultado = clienteService.salvar(cliente1);
        assertNotNull(resultado);
        assertEquals(cliente1.getNome(), resultado.getNome());
        verify(clienteRepository, times(1)).save(cliente1);
    }

    @Test
    void listar() {
        int p = 0;
        int s = 5;
        PageRequest pageRequest = PageRequest.of(p, s);
        when(clienteRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(Arrays.asList(cliente1, cliente2)));
        Page<Cliente> resultado = clienteService.listar(p, s);
        assertNotNull(resultado);
        assertEquals(2, resultado.getTotalElements());
        assertEquals(Arrays.asList(cliente1, cliente2), resultado.stream().toList());
        verify(clienteRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void buscarPorId() {
        when(clienteRepository.findById(cliente1.getId())).thenReturn(Optional.ofNullable(cliente1));
        Optional<Cliente> resultado = clienteService.buscarPorId(cliente1.getId());
        assertNotNull(resultado);
        assertEquals(cliente1.getNome(), resultado.map(Cliente::getNome).orElseThrow());
        verify(clienteRepository, times(1)).findById(cliente1.getId());
    }

    @Test
    void deletarPorId() {
        doNothing().when(clienteRepository).deleteById(cliente1.getId());

        clienteService.deletarPorId(cliente1.getId());

        verify(clienteRepository, times(1)).deleteById(cliente1.getId());

    }
}