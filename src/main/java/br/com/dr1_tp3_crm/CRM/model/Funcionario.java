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
public class Funcionario {

    @Id
    @GeneratedValue()
    private UUID id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cargo;

    private int salario;

    // getters e setters
}