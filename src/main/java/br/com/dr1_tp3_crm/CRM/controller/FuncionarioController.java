package br.com.dr1_tp3_crm.CRM.controller;

import br.com.dr1_tp3_crm.CRM.model.Funcionario;
import br.com.dr1_tp3_crm.CRM.service.FuncionarioService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funcionario")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService service;

    @GetMapping
    public ResponseEntity<List<Funcionario>> listar(
            @RequestHeader(value = "X-PAGE", defaultValue = "0") int page,
            @RequestHeader(value = "X-SIZE", defaultValue = "5") int size
    ) {
        Page<Funcionario> funcionarios = service.listar(page, size);
        if (funcionarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(funcionarios.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(funcionarios.getTotalPages()))
                .header("X-Page-Size", String.valueOf(size))
                .body(funcionarios.getContent());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getById(@PathVariable UUID id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Funcionario> criar(@RequestBody Funcionario funcionario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(funcionario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> update(@PathVariable UUID id, @RequestBody Funcionario funcionario) {
        funcionario.setId(id);
        return ResponseEntity.ok(service.salvar(funcionario));
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
