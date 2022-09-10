package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.DespesaEntity;
import br.com.twobrothers.frontend.models.enums.TipoDespesaEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
public interface DespesaRepository extends JpaRepository<DespesaEntity, Long> {

    // Com paginação
    @Query("Select d From DespesaEntity d where d.dataCadastro between ?1 and ?2 " +
            "OR d.dataPagamento between ?1 and ?2 " +
            "OR d.dataAgendamento between ?1 and ?2")
    List<DespesaEntity> buscaPorRangeDeData(Pageable pageable, LocalDate dataInicio, LocalDate dataFim);

    @Query("Select d From DespesaEntity d where d.dataCadastro between ?1 and ?2 " +
            "OR d.dataPagamento between ?1 and ?2 " +
            "OR d.dataAgendamento between ?1 and ?2")
    List<DespesaEntity> buscaPorPeriodo(Pageable pageable, LocalDate dataInicio, LocalDate dataFim);

    @Query("Select d From DespesaEntity d where d.dataCadastro = ?1 " +
            "OR d.dataPagamento = ?1 " +
            "OR d.dataAgendamento = ?1")
    List<DespesaEntity> buscaHoje(Pageable pageable, LocalDate hoje);

    @Query("Select d From DespesaEntity d where d.dataAgendamento = ?1")
    List<DespesaEntity> buscaAgendadosHoje(Pageable pageable, LocalDate hoje);

    @Query("Select d From DespesaEntity d where d.descricao = ?1")
    List<DespesaEntity> buscaPorDescricao(Pageable pageable, String descricao);

    @Query("Select d From DespesaEntity d where d.tipoDespesa = ?1")
    List<DespesaEntity> buscaPorTipo(Pageable pageable, TipoDespesaEnum tipo);

    // Sem paginação
    @Query("Select d From DespesaEntity d where d.dataCadastro between ?1 and ?2 " +
            "OR d.dataPagamento between ?1 and ?2 " +
            "OR d.dataAgendamento between ?1 and ?2")
    List<DespesaEntity> buscaPorRangeDeDataSemPaginacao(LocalDate dataInicio, LocalDate dataFim);

    @Query("Select d From DespesaEntity d where d.dataCadastro between ?1 and ?2 " +
            "OR d.dataPagamento between ?1 and ?2 " +
            "OR d.dataAgendamento between ?1 and ?2")
    List<DespesaEntity> buscaPorPeriodoSemPaginacao(LocalDate dataInicio, LocalDate dataFim);

    @Query("Select d From DespesaEntity d where d.dataCadastro = ?1 " +
            "OR d.dataPagamento = ?1 " +
            "OR d.dataAgendamento = ?1")
    List<DespesaEntity> buscaHojeSemPaginacao(LocalDate hoje);

    @Query("Select d From DespesaEntity d where d.dataAgendamento = ?1")
    List<DespesaEntity> buscaAgendadosHojeSemPaginacao(LocalDate hoje);

    @Query("Select d From DespesaEntity d where d.descricao = ?1")
    List<DespesaEntity> buscaPorDescricaoSemPaginacao(String descricao);

    @Query("Select d From DespesaEntity d where d.tipoDespesa = ?1")
    List<DespesaEntity> buscaPorTipoSemPaginacao(TipoDespesaEnum tipo);

}
