package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.OrdemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemRepository extends JpaRepository<OrdemEntity, Long> {
}
