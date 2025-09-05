package br.com.dr1_tp3_crm.CRM.controller;

import br.com.dr1_tp3_crm.CRM.model.Fornecedor;
import br.com.dr1_tp3_crm.CRM.service.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fornecedor")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService service;

    @GetMapping
    public ResponseEntity<List<Fornecedor>> listar(
            @RequestHeader(value = "X-PAGE", defaultValue = "0") int page,
            @RequestHeader(value = "X-SIZE", defaultValue = "5") int size
    ) {
        Page<Fornecedor> fornecedores = service.listar(page, size);
        if (fornecedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(fornecedores.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(fornecedores.getTotalPages()))
                .header("X-Page-Size", String.valueOf(size))
                .body(fornecedores.getContent());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> getById(@PathVariable UUID id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Fornecedor> criar(@RequestBody Fornecedor fornecedor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(fornecedor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> update(@PathVariable UUID id, @RequestBody Fornecedor fornecedor) {
        fornecedor.setId(id);
        return ResponseEntity.ok(service.salvar(fornecedor));
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
