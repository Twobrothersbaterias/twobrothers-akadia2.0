package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
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
public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Long> {

    @Query("Select f From FornecedorEntity f where f.email = ?1")
    Optional<FornecedorEntity> buscaPorEmail(String email);

    @Query("Select f From FornecedorEntity f where f.cpfCnpj = ?1")
    Optional<FornecedorEntity> buscaPorCpfCnpj(String cpfCnpj);

    @Query("Select f From FornecedorEntity f where f.dataCadastro = ?1")
    List<FornecedorEntity> buscaHojePaginado(Pageable pageable, String hoje);

    @Query("Select f From FornecedorEntity f where f.dataCadastro between ?1 and ?2")
    List<FornecedorEntity> buscaPorRangeDeDataCadastroPaginado(Pageable pageable, String dataInicio, String dataFim);

    @Query("Select f From FornecedorEntity f where f.dataCadastro between ?1 and ?2")
    List<FornecedorEntity> buscaPorPeriodoPaginado(Pageable pageable, String dataInicio, String dataFim);

    @Query("Select f From FornecedorEntity f where f.nome like %:nome%")
    List<FornecedorEntity> buscaPorNomePaginado(Pageable pageable, @Param("nome") String nome);

    @Query("Select f From FornecedorEntity f where f.cpfCnpj like %:cpfCnpj%")
    List<FornecedorEntity> buscaPorCpfCnpjPaginado(Pageable pageable, @Param("cpfCnpj") String cpfCnpj);

    @Query("Select f From FornecedorEntity f where f.telefone like %:telefone%")
    List<FornecedorEntity> buscaPorTelefonePaginado(Pageable pageable, @Param("telefone") String telefone);

    @Query("Select f From FornecedorEntity f where f.dataCadastro = ?1")
    List<FornecedorEntity> buscaHojeSemPaginacao(String hoje);

    @Query("Select f From FornecedorEntity f where f.dataCadastro between ?1 and ?2")
    List<FornecedorEntity> buscaPorRangeDeDataCadastroSemPaginacao(String dataInicio, String dataFim);

    @Query("Select f From FornecedorEntity f where f.dataCadastro between ?1 and ?2")
    List<FornecedorEntity> buscaPorPeriodoSemPaginacao(String dataInicio, String dataFim);

    @Query("Select f From FornecedorEntity f where f.nome like %:nome%")
    List<FornecedorEntity> buscaPorNomeSemPaginacao(@Param("nome") String nome);

    @Query("Select f From FornecedorEntity f where f.cpfCnpj like %:cpfCnpj%")
    List<FornecedorEntity> buscaPorCpfCnpjSemPaginacao(@Param("cpfCnpj") String cpfCnpj);

    @Query("Select f From FornecedorEntity f where f.telefone like %:telefone%")
    List<FornecedorEntity> buscaPorTelefoneSemPaginacao(@Param("telefone") String telefone);



}
