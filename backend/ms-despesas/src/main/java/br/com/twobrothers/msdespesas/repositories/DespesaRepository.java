package br.com.twobrothers.msdespesas.repositories;

import br.com.twobrothers.msdespesas.models.entities.DespesaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DespesaRepository extends JpaRepository<DespesaEntity, Long> {

    @Query("Select d From DespesaEntity d where d.dataCadastro between ?1 and ?2")
    List<DespesaEntity> buscaPorRangeDeDataCadastro(LocalDateTime dataInicio, LocalDateTime dataFim);

}
