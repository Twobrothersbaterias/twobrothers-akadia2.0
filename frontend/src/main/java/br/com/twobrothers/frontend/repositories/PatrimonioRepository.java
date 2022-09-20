package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.PatrimonioEntity;
import br.com.twobrothers.frontend.models.enums.TipoPatrimonioEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Gabriel Lagrota
 * @version 1.0
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @since 30-08-22
 */
@Repository
public interface PatrimonioRepository extends JpaRepository<PatrimonioEntity, Long> {

    // Com paginação
    @Query("Select p From PatrimonioEntity p where p.dataPagamento between ?1 and ?2 OR p.dataAgendamento between ?1 and ?2")
    List<PatrimonioEntity> buscaPorRangeDeData(Pageable pageable, String dataInicio, String dataFim);

    @Query("Select p From PatrimonioEntity p where p.dataPagamento between ?1 and ?2 OR p.dataAgendamento between ?1 and ?2")
    List<PatrimonioEntity> buscaPorPeriodo(Pageable pageable, String dataInicio, String dataFim);

    @Query("Select p From PatrimonioEntity p where p.dataPagamento = ?1 OR p.dataAgendamento <= ?1")
    List<PatrimonioEntity> buscaHoje(Pageable pageable, String hoje);

    @Query("Select p From PatrimonioEntity p where p.nome like %:nome%")
    List<PatrimonioEntity> buscaPorDescricao(Pageable pageable, @Param("nome") String nome);

    @Query("Select p From PatrimonioEntity p where p.tipoPatrimonio = ?1")
    List<PatrimonioEntity> buscaPorTipo(Pageable pageable, TipoPatrimonioEnum tipo);

    // Sem paginação
    @Query("Select p From PatrimonioEntity p where p.dataPagamento between ?1 and ?2 OR p.dataAgendamento between ?1 and ?2")
    List<PatrimonioEntity> buscaPorRangeDeDataSemPaginacao(String dataInicio, String dataFim);

    @Query("Select p From PatrimonioEntity p where p.dataPagamento between ?1 and ?2 OR p.dataAgendamento between ?1 and ?2")
    List<PatrimonioEntity> buscaPorPeriodoSemPaginacao(String dataInicio, String dataFim);

    @Query("Select p From PatrimonioEntity p where p.dataPagamento = ?1 OR p.dataAgendamento <= ?1")
    List<PatrimonioEntity> buscaHojeSemPaginacao(String hoje);

    @Query("Select p From PatrimonioEntity p where p.dataAgendamento <= ?1")
    List<PatrimonioEntity> buscaAgendadosHojeSemPaginacao(String hoje);

    @Query("Select p From PatrimonioEntity p where p.nome like %:nome%")
    List<PatrimonioEntity> buscaPorDescricaoSemPaginacao(@Param("nome") String nome);

    @Query("Select p From PatrimonioEntity p where p.tipoPatrimonio = ?1")
    List<PatrimonioEntity> buscaPorTipoSemPaginacao(TipoPatrimonioEnum tipo);

}
