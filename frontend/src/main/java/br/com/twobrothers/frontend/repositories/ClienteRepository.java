package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.ClienteEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    @Query("Select c From ClienteEntity c where c.email = ?1")
    Optional<ClienteEntity> buscaPorEmail(String email);

    @Query("Select c From ClienteEntity c where c.cpfCnpj = ?1")
    Optional<ClienteEntity> buscaPorCpfCnpj(String cpfCnpj);

    @Query("Select c From ClienteEntity c where c.dataCadastro = ?1")
    List<ClienteEntity> buscaHojePaginado(Pageable pageable, String hoje);

    @Query("Select c From ClienteEntity c where c.dataCadastro between ?1 and ?2")
    List<ClienteEntity> buscaPorRangeDeDataCadastroPaginado(Pageable pageable, String dataInicio, String dataFim);

    @Query("Select c From ClienteEntity c where c.dataCadastro between ?1 and ?2")
    List<ClienteEntity> buscaPorPeriodoPaginado(Pageable pageable, String dataInicio, String dataFim);

    @Query("Select c From ClienteEntity c where c.nomeCompleto like %:nomeCompleto%")
    List<ClienteEntity> buscaPorNomeCompletoPaginado(Pageable pageable, @Param("nomeCompleto") String nomeCompleto);

    @Query("Select c From ClienteEntity c where c.cpfCnpj like %:cpfCnpj%")
    List<ClienteEntity> buscaPorCpfCnpjPaginado(Pageable pageable, @Param("cpfCnpj") String cpfCnpj);

    @Query("Select c From ClienteEntity c where c.telefone like %:telefone%")
    List<ClienteEntity> buscaPorTelefonePaginado(Pageable pageable, @Param("telefone") String telefone);

    @Query("Select c From ClienteEntity c where c.dataCadastro = ?1")
    List<ClienteEntity> buscaHojeSemPaginacao(String hoje);

    @Query("Select c From ClienteEntity c where c.dataCadastro between ?1 and ?2")
    List<ClienteEntity> buscaPorRangeDeDataCadastroSemPaginacao(String dataInicio, String dataFim);

    @Query("Select c From ClienteEntity c where c.dataCadastro between ?1 and ?2")
    List<ClienteEntity> buscaPorPeriodoSemPaginacao(String dataInicio, String dataFim);

    @Query("Select c From ClienteEntity c where c.nomeCompleto like %:nomeCompleto%")
    List<ClienteEntity> buscaPorNomeCompletoSemPaginacao(@Param("nomeCompleto") String nomeCompleto);

    @Query("Select c From ClienteEntity c where c.cpfCnpj like %:cpfCnpj%")
    List<ClienteEntity> buscaPorCpfCnpjSemPaginacao(@Param("cpfCnpj") String cpfCnpj);

    @Query("Select c From ClienteEntity c where c.telefone like %:telefone%")
    List<ClienteEntity> buscaPorTelefoneSemPaginacao(@Param("telefone") String telefone);



}
