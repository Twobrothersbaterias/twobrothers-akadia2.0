package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.models.enums.TipoProdutoEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Gabriel Lagrota
 * @version 1.0
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @since 30-08-22
 */
@Repository
public interface ProdutoEstoqueRepository extends JpaRepository<ProdutoEstoqueEntity, Long> {

    @Query("Select p From ProdutoEstoqueEntity p where p.tipoProduto = ?1")
    List<ProdutoEstoqueEntity> buscaTodasBaterias(Sort sort, TipoProdutoEnum tipoProdutoEnum);

    @Query("Select p From ProdutoEstoqueEntity p where p.sigla = ?1")
    Optional<ProdutoEstoqueEntity> buscaPorSigla(String sigla);

    // Com paginação
    @Query("Select p From ProdutoEstoqueEntity p where p.dataCadastro between ?1 and ?2")
    List<ProdutoEstoqueEntity> buscaPorRangeDeData(Pageable pageable, String dataInicio, String dataFim);

    @Query("Select p From ProdutoEstoqueEntity p where p.dataCadastro between ?1 and ?2")
    List<ProdutoEstoqueEntity> buscaPorPeriodo(Pageable pageable, String dataInicio, String dataFim);

    @Query("Select p From ProdutoEstoqueEntity p where p.sigla like %:descricao% OR p.marcaBateria like %:descricao% OR p.especificacao like %:descricao%")
    List<ProdutoEstoqueEntity> buscaPorDescricao(Pageable pageable, @Param("descricao") String descricao);

    @Query("Select p From ProdutoEstoqueEntity p where p.tipoProduto = ?1")
    List<ProdutoEstoqueEntity> buscaPorTipo(Pageable pageable, TipoProdutoEnum tipo);

    // Sem paginação
    @Query("Select p From ProdutoEstoqueEntity p where p.dataCadastro between ?1 and ?2")
    List<ProdutoEstoqueEntity> buscaPorRangeDeDataSemPaginacao(String dataInicio, String dataFim);

    @Query("Select p From ProdutoEstoqueEntity p where p.dataCadastro between ?1 and ?2")
    List<ProdutoEstoqueEntity> buscaPorPeriodoSemPaginacao(String dataInicio, String dataFim);

    @Query("Select p From ProdutoEstoqueEntity p where p.sigla like %:descricao% OR p.marcaBateria like %:descricao% OR p.especificacao like %:descricao%")
    List<ProdutoEstoqueEntity> buscaPorDescricaoSemPaginacao(@Param("descricao") String descricao);

    @Query("Select p From ProdutoEstoqueEntity p where p.tipoProduto = ?1")
    List<ProdutoEstoqueEntity> buscaPorTipoSemPaginacao(TipoProdutoEnum tipo);

}
