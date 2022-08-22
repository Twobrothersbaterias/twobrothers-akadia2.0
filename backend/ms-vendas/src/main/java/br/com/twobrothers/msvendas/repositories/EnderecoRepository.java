package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {

    @Query("Select e From EnderecoEntity e where e.dataCadastro between ?1 and ?2")
    List<EnderecoEntity> buscaPorRangeDeDataCadastro(LocalDateTime dataInicio, LocalDateTime dataFim);

    @Query("Select e From EnderecoEntity where e.logradouro = ?1 and e.bairro = ?2 and e.numero = ?3")
    Optional<EnderecoEntity> buscaPorAtributos(String logradouro, String bairro, Integer numero);

}
