package br.com.dr1_tp3_crm.CRM.controller;

import br.com.dr1_tp3_crm.CRM.model.Produto;
import br.com.dr1_tp3_crm.CRM.model.Produto;
import br.com.dr1_tp3_crm.CRM.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @GetMapping
    public ResponseEntity<List<Produto>> listar(
            @RequestHeader(value = "X-PAGE", defaultValue = "0") int page,
            @RequestHeader(value = "X-SIZE", defaultValue = "5") int size
    ) {
        Page<Produto> produtos = service.listar(page, size);
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(produtos.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(produtos.getTotalPages()))
                .header("X-Page-Size", String.valueOf(size))
                .body(produtos.getContent());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable UUID id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable UUID id, @RequestBody Produto produto) {
        produto.setId(id);
        return ResponseEntity.ok(service.salvar(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        try {
            service.deletarPorId(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
    }
}
