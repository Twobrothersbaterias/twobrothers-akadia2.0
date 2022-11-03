package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.postagem.SubCategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCategoriaRepository extends JpaRepository<SubCategoriaEntity, Long> {

    @Query("Select s From SubCategoriaEntity s where s.nome = ?1 AND s.categoria.nome = ?2")
    Optional<SubCategoriaEntity> buscaPorNomeNaCategoriaInformada(String nome, String categoriaNome);

}
