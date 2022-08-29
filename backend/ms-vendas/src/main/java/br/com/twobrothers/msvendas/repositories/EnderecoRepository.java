package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {

    @Query("Select e From EnderecoEntity e where e.dataCadastro between ?1 and ?2")
    List<EnderecoEntity> buscaPorRangeDeDataCadastro(LocalDateTime dataInicio, LocalDateTime dataFim);

}
