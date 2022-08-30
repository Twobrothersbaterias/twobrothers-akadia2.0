package br.com.twobrothers.msvendas.repositories;

import br.com.twobrothers.msvendas.models.entities.RetiradaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Repository
public interface RetiradaRepository extends JpaRepository<RetiradaEntity, Long> {
}
