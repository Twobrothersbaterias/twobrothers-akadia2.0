package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.FornecedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Long> {
}
