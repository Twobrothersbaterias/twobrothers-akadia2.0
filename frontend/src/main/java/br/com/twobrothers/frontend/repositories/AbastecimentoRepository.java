package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.AbastecimentoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Repository
public interface AbastecimentoRepository extends JpaRepository<AbastecimentoEntity, Long> {

    @Query("Select a From AbastecimentoEntity a where a.dataCadastro between ?1 and ?2")
    List<AbastecimentoEntity> buscaPorRangeDeDataPaginado(Pageable pageable, String dataInicio, String dataFim);

    @Query("Select a From AbastecimentoEntity a where a.produto.sigla like %:produto%")
    List<AbastecimentoEntity> buscaPorProdutoPaginado(Pageable pageable, @Param("produto") String produto);

    @Query("Select a From AbastecimentoEntity a where a.fornecedor.nome like %:fornecedor%")
    List<AbastecimentoEntity> buscaPorFornecedorPaginado(Pageable pageable, @Param("fornecedor") String fornecedor);

    @Query("Select a From AbastecimentoEntity a where a.dataCadastro = ?1")
    List<AbastecimentoEntity> buscaHojePaginado(Pageable pageable, String hoje);
    @Query("Select a From AbastecimentoEntity a where a.dataCadastro between ?1 and ?2")
    List<AbastecimentoEntity> buscaPorRangeDeDataSemPaginacao(String dataInicio, String dataFim);

    @Query("Select a From AbastecimentoEntity a where a.produto.sigla like %:produto%")
    List<AbastecimentoEntity> buscaPorProdutoSemPaginacao(@Param("produto") String produto);

    @Query("Select a From AbastecimentoEntity a where a.fornecedor.nome like %:fornecedor%")
    List<AbastecimentoEntity> buscaPorFornecedorSemPaginacao(@Param("fornecedor") String fornecedor);

    @Query("Select a From AbastecimentoEntity a where a.dataCadastro = ?1")
    List<AbastecimentoEntity> buscaHojeSemPaginacao(String hoje);

}
