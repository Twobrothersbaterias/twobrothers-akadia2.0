package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Repository
public interface ProdutoEstoqueRepository extends JpaRepository<ProdutoEstoqueEntity, Long> {

    @Query("Select p From ProdutoEstoqueEntity p where p.sigla = ?1")
    Optional<ProdutoEstoqueEntity> buscaPorSigla(String sigla);

    @Query("Select p From ProdutoEstoqueEntity p where p.dataCadastro between ?1 and ?2")
    List<ProdutoEstoqueEntity> buscaPorRangeDeDataCadastro(LocalDateTime dataInicio, LocalDateTime dataFim);

}
