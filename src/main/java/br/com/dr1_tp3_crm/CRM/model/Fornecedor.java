package br.com.dr1_tp3_crm.CRM.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Fornecedor {

    @Id@GeneratedValue()
    private UUID id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, length = 30)
    private String cnpj;
    private String contato;

    // getters e setters
}