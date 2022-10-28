package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.ClienteEntity;
import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
import br.com.twobrothers.frontend.models.entities.OrdemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
public interface OrdemRepository extends JpaRepository<OrdemEntity, Long> {

    Page<OrdemEntity> findByEntradasProdutoSigla(Pageable pageable, String sigla);

    @Query("Select o From OrdemEntity o where o.dataCadastro = ?1")
    List<OrdemEntity> buscaHojePaginado(Pageable pageable, String hoje);

    @Query("Select o From OrdemEntity o where o.dataCadastro between ?1 and ?2")
    List<OrdemEntity> buscaPorPeriodoPaginado(Pageable pageable, String dataInicio, String dataFim);

    @Query("Select o From OrdemEntity o where o.cliente.endereco.bairro like %:bairro%")
    List<OrdemEntity> buscaPorBairroPaginado(Pageable pageable, @Param("bairro") String bairro);

    @Query("Select o From OrdemEntity o where o.dataCadastro = ?1")
    List<OrdemEntity> buscaHojeSemPaginacao(String hoje);

    @Query("Select o From OrdemEntity o where o.dataCadastro between ?1 and ?2")
    List<OrdemEntity> buscaPorPeriodoSemPaginacao(String dataInicio, String dataFim);

    @Query("Select o From OrdemEntity o where o.cliente.endereco.bairro like %:bairro%")
    List<OrdemEntity> buscaPorBairroSemPaginacao( @Param("bairro") String bairro);

}
