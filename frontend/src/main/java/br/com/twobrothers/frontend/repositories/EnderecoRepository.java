package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {

    @Query("Select e From EnderecoEntity e where e.dataCadastro between ?1 and ?2")
    List<EnderecoEntity> buscaPorRangeDeDataCadastro(LocalDateTime dataInicio, LocalDateTime dataFim);

}
