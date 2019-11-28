package br.com.hbsis.categoriaProduto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {

}
