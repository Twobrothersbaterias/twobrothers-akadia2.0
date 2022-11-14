package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.PrecoFornecedorEntity;
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
public interface PrecoFornecedorRepository extends JpaRepository<PrecoFornecedorEntity, Long> {

    @Query("Select p From PrecoFornecedorEntity p where p.produto.id = ?1")
    List<PrecoFornecedorEntity> buscaPorProdutoIdPaginado(Pageable pageable, Long id);

    @Query("Select p From PrecoFornecedorEntity p where p.produto.sigla like %:produto%")
    List<PrecoFornecedorEntity> buscaPorProdutoPaginado(Pageable pageable, @Param("produto") String produto);

    @Query("Select p From PrecoFornecedorEntity p where p.fornecedor.id = ?1")
    List<PrecoFornecedorEntity> buscaPorFornecedorIdPaginado(Pageable pageable, Long id);

    @Query("Select p From PrecoFornecedorEntity p where p.fornecedor.nome like %:fornecedor%")
    List<PrecoFornecedorEntity> buscaPorFornecedorPaginado(Pageable pageable, @Param("fornecedor") String fornecedor);

    @Query("Select p From PrecoFornecedorEntity p where p.produto.id = ?1")
    List<PrecoFornecedorEntity> buscaPorProdutoIdSemPaginacao(Long id);

    @Query("Select p From PrecoFornecedorEntity p where p.produto.sigla like %:produto%")
    List<PrecoFornecedorEntity> buscaPorProdutoSemPaginacao(@Param("produto") String produto);

    @Query("Select p From PrecoFornecedorEntity p where p.fornecedor.id = ?1")
    List<PrecoFornecedorEntity> buscaPorFornecedorIdSemPaginacao(Long id);

    @Query("Select p From PrecoFornecedorEntity p where p.fornecedor.nome like %:fornecedor%")
    List<PrecoFornecedorEntity> buscaPorFornecedorSemPaginacao(@Param("fornecedor") String fornecedor);




}
