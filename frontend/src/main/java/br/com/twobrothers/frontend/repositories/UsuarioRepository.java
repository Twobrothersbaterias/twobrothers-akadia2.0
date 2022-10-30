package br.com.twobrothers.frontend.repositories;

import br.com.twobrothers.frontend.models.entities.UsuarioEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByNomeUsuario(String nomeUsuario);

    @Query("Select u From UsuarioEntity u where u.email = ?1")
    Optional<UsuarioEntity> buscaPorEmail(String email);

    @Query("Select u From UsuarioEntity u where u.cpfCnpj = ?1")
    Optional<UsuarioEntity> buscaPorCpfCnpj(String cpfCnpj);



    @Query("Select u From UsuarioEntity u where u.dataCadastro between ?1 and ?2 and u.privilegio != 'DESENVOLVEDOR'")
    List<UsuarioEntity> buscaPorRangeDeDataCadastroPaginado(Pageable pageable, String dataInicio, String dataFim);

    @Query("Select u From UsuarioEntity u where u.dataCadastro between ?1 and ?2 and u.privilegio != 'DESENVOLVEDOR'")
    List<UsuarioEntity> buscaPorPeriodoPaginado(Pageable pageable, String dataInicio, String dataFim);

    @Query("Select u From UsuarioEntity u where u.nome like %:nome% and u.privilegio != 'DESENVOLVEDOR'")
    List<UsuarioEntity> buscaPorNomeCompletoPaginado(Pageable pageable, @Param("nome") String nome);

    @Query("Select u From UsuarioEntity u where u.nomeUsuario like %:username% and u.privilegio != 'DESENVOLVEDOR'")
    List<UsuarioEntity> buscaPorNomeDeUsuarioPaginado(Pageable pageable, @Param("username") String username);

    @Query("Select u From UsuarioEntity u where u.privilegio != 'DESENVOLVEDOR'")
    List<UsuarioEntity> buscaTodosPaginado(Pageable pageable);

    @Query("Select u From UsuarioEntity u where u.dataCadastro between ?1 and ?2 and u.privilegio != 'DESENVOLVEDOR'")
    List<UsuarioEntity> buscaPorRangeDeDataCadastroSemPaginacao(String dataInicio, String dataFim);

    @Query("Select u From UsuarioEntity u where u.dataCadastro between ?1 and ?2 and u.privilegio != 'DESENVOLVEDOR'")
    List<UsuarioEntity> buscaPorPeriodoSemPaginacao(String dataInicio, String dataFim);

    @Query("Select u From UsuarioEntity u where u.nome like %:nome% and u.privilegio != 'DESENVOLVEDOR'")
    List<UsuarioEntity> buscaPorNomeCompletoSemPaginacao(@Param("nome") String nome);

    @Query("Select u From UsuarioEntity u where u.nomeUsuario like %:username% and u.privilegio != 'DESENVOLVEDOR'")
    List<UsuarioEntity> buscaPorNomeDeUsuarioSemPaginacao(@Param("username") String username);

    @Query("Select u From UsuarioEntity u where u.privilegio != 'DESENVOLVEDOR'")
    List<UsuarioEntity> buscaTodosSemPaginacao();




}
