package br.com.twobrothers.msbalanco.repositories;

import br.com.twobrothers.msbalanco.models.entities.PatrimonioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PatrimonioRepository extends JpaRepository<PatrimonioEntity, Long> {

    @Query("Select p From PatrimonioEntity p where p.dataCadastro between ?1 and ?2")
    List<PatrimonioEntity> buscaPorRangeDeDataCadastro(LocalDateTime dataInicio, LocalDateTime dataFim);

    @Query("Select p From PatrimonioEntity p where p.nome = ?1")
    List<PatrimonioEntity> buscaPorDescricao(String nome);

}
