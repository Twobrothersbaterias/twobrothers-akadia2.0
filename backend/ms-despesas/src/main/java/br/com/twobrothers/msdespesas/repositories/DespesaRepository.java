package br.com.twobrothers.msdespesas.repositories;

import br.com.twobrothers.msdespesas.models.entities.DespesaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Repository
public interface DespesaRepository extends JpaRepository<DespesaEntity, Long> {

    @Query("Select d From DespesaEntity d where d.dataCadastro between ?1 and ?2")
    List<DespesaEntity> buscaPorRangeDeDataCadastro(LocalDateTime dataInicio, LocalDateTime dataFim);

    @Query("Select d From DespesaEntity d where d.descricao = ?1")
    List<DespesaEntity> buscaPorDescricao(String descricao);

}
