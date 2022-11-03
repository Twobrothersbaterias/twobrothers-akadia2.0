package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.postagem.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

    Optional<CategoriaEntity> findByNome(String nome);

}
