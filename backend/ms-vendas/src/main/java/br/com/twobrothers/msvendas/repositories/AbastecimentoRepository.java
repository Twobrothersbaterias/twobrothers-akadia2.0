package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.AbastecimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AbastecimentoRepository extends JpaRepository<AbastecimentoEntity, Long> {

    @Query("Select a From AbastecimentoEntity a where a.dataCadastro between ?1 and ?2")
    List<AbastecimentoEntity> buscaPorRangeDeDataCadastro(LocalDateTime dataInicio, LocalDateTime dataFim);

}
