package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.postagem.PostagemEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostagemRepository extends JpaRepository<PostagemEntity, Long> {

    @Query("Select c From PostagemEntity c where c.dataCadastro = ?1")
    List<PostagemEntity> buscaHojePaginado(Pageable pageable, String hoje);

    @Query("Select c From PostagemEntity c where c.dataCadastro between ?1 and ?2")
    List<PostagemEntity> buscaPorRangeDeDataCadastroPaginado(Pageable pageable, String dataInicio, String dataFim);

    @Query("Select c From PostagemEntity c where c.dataCadastro between ?1 and ?2")
    List<PostagemEntity> buscaPorPeriodoPaginado(Pageable pageable, String dataInicio, String dataFim);

    @Query("Select c From PostagemEntity c where c.titulo like %:titulo%")
    List<PostagemEntity> buscaPorTituloPaginado(Pageable pageable, @Param("titulo") String titulo);

    @Query("Select c From PostagemEntity c where c.categoria.nome like %:categoria%")
    List<PostagemEntity> buscaPorCategoriaPaginado(Pageable pageable, @Param("categoria") String categoria);

    @Query("Select c From PostagemEntity c where c.dataCadastro = ?1")
    List<PostagemEntity> buscaHojeSemPaginacao(String hoje);

    @Query("Select c From PostagemEntity c where c.dataCadastro between ?1 and ?2")
    List<PostagemEntity> buscaPorRangeDeDataCadastroSemPaginacao(String dataInicio, String dataFim);

    @Query("Select c From PostagemEntity c where c.dataCadastro between ?1 and ?2")
    List<PostagemEntity> buscaPorPeriodoSemPaginacao(String dataInicio, String dataFim);

    @Query("Select c From PostagemEntity c where c.titulo like %:titulo%")
    List<PostagemEntity> buscaPorTituloSemPaginacao(@Param("titulo") String titulo);

    @Query("Select c From PostagemEntity c where c.categoria.nome like %:categoria%")
    List<PostagemEntity> buscaPorCategoriaSemPaginacao(@Param("categoria") String categoria);

}
