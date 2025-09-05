package br.com.dr1_tp3_crm.CRM.service;

import br.com.dr1_tp3_crm.CRM.model.Cliente;
import br.com.dr1_tp3_crm.CRM.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repo;

    public Cliente salvar(Cliente cliente) {
        return repo.save(cliente);
    }

    public Page<Cliente> listar(Integer page, Integer size) {
        int p = page == null ? 0 : Math.max(0, page);
        int s = size == null ? 0 : Math.max(1, size);
        PageRequest pageRequest = PageRequest.of(p, s);
        Page<Cliente> paginados = repo.findAll(pageRequest);

        return paginados;
    }

    public Optional<Cliente> buscarPorId(UUID id) {
        return repo.findById(id);
    }

    public void deletarPorId(UUID id) {
        repo.deleteById(id);
    }
}
