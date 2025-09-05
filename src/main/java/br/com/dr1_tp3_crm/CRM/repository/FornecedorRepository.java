package br.com.dr1_tp3_crm.CRM.repository;

import br.com.dr1_tp3_crm.CRM.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FornecedorRepository extends JpaRepository<Fornecedor, UUID> {}
