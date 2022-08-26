package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.FornecedorEntity;
import br.com.twobrothers.msvendas.models.entities.OrdemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdemRepository extends JpaRepository<OrdemEntity, Long> {

    @Query("Select o From OrdemEntity o where o.dataCadastro between ?1 and ?2")
    List<OrdemEntity> buscaPorRangeDeDataCadastro(LocalDateTime dataInicio, LocalDateTime dataFim);

}
