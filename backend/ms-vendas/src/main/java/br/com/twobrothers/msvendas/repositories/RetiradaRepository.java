package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.RetiradaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetiradaRepository extends JpaRepository<RetiradaEntity, Long> {
}
