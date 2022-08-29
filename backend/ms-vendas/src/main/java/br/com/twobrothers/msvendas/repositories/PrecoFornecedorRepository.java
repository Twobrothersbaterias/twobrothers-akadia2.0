package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.PrecoFornecedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PrecoFornecedorRepository extends JpaRepository<PrecoFornecedorEntity, Long> {

    @Query("Select p From PrecoFornecedorEntity p where p.dataCadastro between ?1 and ?2")
    List<PrecoFornecedorEntity> buscaPorRangeDeDataCadastro(LocalDateTime dataInicio, LocalDateTime dataFim);

}
