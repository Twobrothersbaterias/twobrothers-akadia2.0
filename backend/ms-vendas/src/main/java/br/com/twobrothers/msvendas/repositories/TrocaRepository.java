package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.TrocaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrocaRepository extends JpaRepository<TrocaEntity, Long> {
}
