package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.ProdutoEstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoEstoqueRepository extends JpaRepository<ProdutoEstoqueEntity, Long> {
}
