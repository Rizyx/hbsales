package br.com.hbsis.fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface IFornecedorRepository extends JpaRepository<Fornecedor, Long> {
  Optional<Fornecedor> findByCnpj(Long cnpj);
}
