package br.com.dr1_tp3_crm.CRM.service;

import br.com.dr1_tp3_crm.CRM.model.Produto;
import br.com.dr1_tp3_crm.CRM.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repo;

    public Produto salvar(Produto produto) {
        return repo.save(produto);
    }

    public Page<Produto> listar(Integer page, Integer size) {
        int p = page == null ? 0 : Math.max(0, page);
        int s = size == null ? 0 : Math.max(1, size);
        PageRequest pageRequest = PageRequest.of(p, s);
        Page<Produto> paginados = repo.findAll(pageRequest);

        return paginados;
    }

    public Optional<Produto> buscarPorId(UUID id) {
        return repo.findById(id);
    }

    public void deletarPorId(UUID id) {
        repo.deleteById(id);
    }
}
