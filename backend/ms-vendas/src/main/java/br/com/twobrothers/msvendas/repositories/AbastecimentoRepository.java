package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.AbastecimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbastecimentoRepository extends JpaRepository<AbastecimentoEntity, Long> {
}
