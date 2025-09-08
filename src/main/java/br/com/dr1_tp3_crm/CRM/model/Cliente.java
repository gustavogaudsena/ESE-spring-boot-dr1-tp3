package br.com.dr1_tp3_crm.CRM.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "cliente")
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class Cliente {

    @Id@GeneratedValue()
    private UUID id;

    @Column(nullable = false)
    private String nome;
    private String email;
    private String telefone;

}