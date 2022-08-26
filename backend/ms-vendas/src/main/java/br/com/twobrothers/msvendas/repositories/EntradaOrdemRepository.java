package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.EntradaOrdemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntradaOrdemRepository extends JpaRepository<EntradaOrdemEntity, Long> {
}
