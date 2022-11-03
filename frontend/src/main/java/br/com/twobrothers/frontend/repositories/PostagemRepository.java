package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.postagem.PostagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostagemRepository extends JpaRepository<PostagemEntity, Long> {
}
