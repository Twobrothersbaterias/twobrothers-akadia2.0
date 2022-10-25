package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
import br.com.twobrothers.frontend.models.entities.OrdemEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
public interface OrdemRepository extends JpaRepository<OrdemEntity, Long> {

    @Query("Select f From OrdemEntity f where f.dataCadastro = ?1")
    List<OrdemEntity> buscaHojePaginado(Pageable pageable, String hoje);

    @Query("Select f From OrdemEntity f where f.dataCadastro between ?1 and ?2")
    List<OrdemEntity> buscaPorPeriodoPaginado(Pageable pageable, String dataInicio, String dataFim);

    @Query("Select f From OrdemEntity f where f.dataCadastro = ?1")
    List<OrdemEntity> buscaHojeSemPaginacao(String hoje);

    @Query("Select f From OrdemEntity f where f.dataCadastro between ?1 and ?2")
    List<OrdemEntity> buscaPorPeriodoSemPaginacao(String dataInicio, String dataFim);

}
