package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.PrecoFornecedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecoFornecedorRepository extends JpaRepository<PrecoFornecedorEntity, Long> {
}
