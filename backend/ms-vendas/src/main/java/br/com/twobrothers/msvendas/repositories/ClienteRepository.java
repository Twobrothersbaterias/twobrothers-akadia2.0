package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    @Query("Select c From ClienteEntity c where c.dataCadastro between ?1 and ?2")
    List<ClienteEntity> buscaPorRangeDeDataCadastro(LocalDateTime dataInicio, LocalDateTime dataFim);

    @Query("Select c From ClienteEntity c where c.cpfCnpj = ?1")
    Optional<ClienteEntity> buscaPorCpfCnpj(String cpfCnpj);

    @Query("Select c From ClienteEntity c where c.email = ?1")
    Optional<ClienteEntity> buscaPorEmail(String email);

    @Query("Select c From ClienteEntity c where c.telefone = ?1")
    List<ClienteEntity> buscaPorTelefone(String telefone);

    @Query("Select c From ClienteEntity c where c.nomeCompleto = ?1")
    List<ClienteEntity> buscaPorNomeCompleto(String nomeCompleto);

}
