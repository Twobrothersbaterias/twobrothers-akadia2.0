package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.ProdutoEstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoEstoqueRepository extends JpaRepository<ProdutoEstoqueEntity, Long> {

    @Query("Select p From ProdutoEstoqueEntity p where p.sigla = ?1")
    Optional<ProdutoEstoqueEntity> buscaPorSigla(String sigla);

}
