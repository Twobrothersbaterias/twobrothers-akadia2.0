package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.FornecedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Repository
public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Long> {

    @Query("Select f From FornecedorEntity f where f.dataCadastro between ?1 and ?2")
    List<FornecedorEntity> buscaPorRangeDeDataCadastro(LocalDateTime dataInicio, LocalDateTime dataFim);

    @Query("Select f From FornecedorEntity f where f.cpfCnpj = ?1")
    Optional<FornecedorEntity> buscaPorCpfCnpj(String cpfCnpj);

    @Query("Select f From FornecedorEntity f where f.email = ?1")
    Optional<FornecedorEntity> buscaPorEmail(String email);

    @Query("Select f From FornecedorEntity f where f.telefone = ?1")
    List<FornecedorEntity> buscaPorTelefone(String telefone);

    @Query("Select f From FornecedorEntity f where f.nome = ?1")
    List<FornecedorEntity> buscaPorNome(String nome);

}
