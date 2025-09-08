package br.com.dr1_tp3_crm.CRM.controller;

import br.com.dr1_tp3_crm.CRM.model.Cliente;
import br.com.dr1_tp3_crm.CRM.service.ClienteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    private Cliente cliente1;
    private Cliente cliente2;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        UUID uuid = new UUID(8, 64);
        cliente1 = new Cliente(new UUID(8, 64), "Cliente 1", "cliente1@mail.com", "219999999999");
        cliente2 = new Cliente(new UUID(8, 64), "Cliente 2", "cliente2@mail.com", "219999999999");
        objectMapper = new ObjectMapper();
    }

    @Test
    void listar() throws Exception {
        int p = 0;
        int s = 5;
        PageRequest pageRequest = PageRequest.of(p, s);
        when(clienteService.listar(p, s)).thenReturn(new PageImpl<>(Arrays.asList(cliente1, cliente2)));

        mockMvc.perform(get("/cliente")
                        .header("X-PAGE", p)
                        .header("X-SIZE", s)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(clienteService, times(1)).listar(p, s);
    }

    @Test
    void getById() throws Exception {
        UUID id = cliente1.getId();
        when(clienteService.buscarPorId(id)).thenReturn(Optional.ofNullable(cliente1));

        mockMvc.perform(get("/cliente/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(cliente1.getNome()));

        verify(clienteService, times(1)).buscarPorId(id);
    }

    @Test
    void criar() throws Exception {
        when(clienteService.salvar(any(Cliente.class))).thenReturn(cliente1);

        mockMvc.perform(post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(cliente1.getNome()));

        verify(clienteService, times(1)).salvar(any(Cliente.class));
    }

    @Test
    void update() throws Exception {
        UUID id = cliente2.getId();
        when(clienteService.salvar(any(Cliente.class))).thenReturn(cliente2);

        mockMvc.perform(put("/cliente/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(cliente2.getNome()));

        verify(clienteService, times(1)).salvar(any(Cliente.class));
    }

    @Test
    void delete() throws Exception {
        UUID id = cliente2.getId();
        doNothing().when(clienteService).deletarPorId(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/cliente/" + id))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).deletarPorId(id);
    }
}