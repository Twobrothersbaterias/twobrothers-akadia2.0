package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.UsuarioDTO;
import br.com.twobrothers.frontend.models.entities.UsuarioEntity;
import br.com.twobrothers.frontend.models.enums.ValidationType;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.frontend.utils.TrataAtributosVazios;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import br.com.twobrothers.frontend.validations.EnderecoValidation;
import br.com.twobrothers.frontend.validations.UsuarioValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.com.twobrothers.frontend.utils.StringConstants.*;

/**
 * @author Gabriel Lagrota
 * @version 1.0
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @since 30-08-22
 */
@Slf4j
@Service
public class UsuarioCrudService {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    UsuarioValidation validation = new UsuarioValidation();

    public List<UsuarioEntity> buscaTodosPaginado(Pageable pageable) {
        return repository.buscaTodosPaginado(pageable);
    }

    public List<UsuarioEntity> buscaTodosSemPaginacao() {
        return repository.buscaTodosSemPaginacao();
    }

    public void criaNovo(UsuarioDTO usuario) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação");

        log.info("[PROGRESS] Setando a data de cadastro no usuário: {}", LocalDate.now());
        usuario.setDataCadastro(LocalDate.now().toString());

        log.info("[PROGRESS] Setando usuário responsável pelo cadastro do novo usuário...");
        usuario.setUsuarioResponsavel(modelMapper.mapper().map(UsuarioUtils.loggedUser(repository), UsuarioDTO.class));

        usuario.setHabilitado(true);

        TrataAtributosVazios.trataAtributosVaziosDoObjetoColaborador(usuario);

        log.info("[PROGRESS] Validando objeto do tipo UsuarioDTO recebido por meio da requisição: {}", usuario);
        validation.validaCorpoRequisicao(usuario, repository, ValidationType.CREATE);

        log.info("[PROGRESS] Criptografando a senha do usuário...");
        usuario.setSenhaCriptografada(new BCryptPasswordEncoder().encode(usuario.getSenha()));

        log.info("[PROGRESS] Salvando o usuário na base de dados...");
        repository.save(modelMapper.mapper().map(usuario, UsuarioEntity.class));

        log.info("[SUCCESS] Requisição finalizada com sucesso");
    }

    public List<UsuarioEntity> buscaPorRangeDeDataPaginado(Pageable pageable, String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de usuário por range de data...");
        validation.validaRangeData(dataInicio, dataFim);
        return repository.buscaPorRangeDeDataCadastroPaginado(pageable, dataInicio, dataFim);
    }

    public List<UsuarioEntity> buscaPorPeriodoPaginado(Pageable pageable, Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de usuário por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorPeriodoPaginado(pageable, dataInicio.toString(), dataFim.toString());
    }

    public List<UsuarioEntity> buscaPorNomePaginado(Pageable pageable, String nome) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de usuário por nome...");
        return repository.buscaPorNomeCompletoPaginado(pageable, nome);
    }

    public List<UsuarioEntity> buscaPorUsernamePaginado(Pageable pageable, String username) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de usuário por username...");
        return repository.buscaPorNomeDeUsuarioPaginado(pageable, username);
    }

    public List<UsuarioEntity> buscaPorRangeDeDataSemPaginacao(String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de usuário por range de data...");
        validation.validaRangeData(dataInicio, dataFim);
        return repository.buscaPorRangeDeDataCadastroSemPaginacao(dataInicio, dataFim);
    }

    public List<UsuarioEntity> buscaPorPeriodoSemPaginacao(Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de usuário por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorPeriodoSemPaginacao(dataInicio.toString(), dataFim.toString());
    }

    public List<UsuarioEntity> buscaPorNomeSemPaginacao(String nome) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de usuário por nome...");
        return repository.buscaPorNomeCompletoSemPaginacao(nome);
    }

    public List<UsuarioEntity> buscaPorUsernameSemPaginacao(String username) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de usuário por username...");
        return repository.buscaPorNomeDeUsuarioSemPaginacao(username);
    }



    public UsuarioDTO atualizaPorId(UsuarioDTO usuario) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Inicializando método de atualização de usuário...");

        log.info("[PROGRESS] Criando as variáveis do usuário: usuarioOptional e usuarioEncontrado...");
        Optional<UsuarioEntity> usuarioOptional = repository.findById(usuario.getId());
        UsuarioEntity usuarioEncontrado;

        log.info("[PROGRESS] Criando o objeto usuarioAtualizado setagem dos atributos...");
        UsuarioEntity usuarioAtualizado;

        log.info("[PROGRESS] Verificando se um usuario com o id {} já existe na base de dados...", usuario.getId());
        if (usuarioOptional.isPresent()) {

            TrataAtributosVazios.trataAtributosVaziosDoObjetoColaborador(usuario);

            validation.validaCorpoRequisicao(usuario, repository, ValidationType.UPDATE);

            usuarioEncontrado = usuarioOptional.get();
            log.warn("[INFO] Usuário encontrado: {}", usuarioEncontrado.getNome());

            if (usuario.getNomeUsuario() != null
                    && repository.findByNomeUsuario(usuario.getNomeUsuario()).isPresent()
                    && !Objects.equals(usuarioEncontrado.getNomeUsuario(), usuario.getNomeUsuario()))
                throw new InvalidRequestException("O nome de usuário informado já existe");

            if (usuario.getEmail() != null
                    && repository.buscaPorEmail(usuario.getEmail()).isPresent()
                    && !Objects.equals(usuarioEncontrado.getEmail(), usuario.getEmail()))
                throw new InvalidRequestException("O e-mail informado já existe");

            if (usuario.getCpfCnpj() != null
                    && repository.buscaPorCpfCnpj(usuario.getCpfCnpj()).isPresent()
                    && !Objects.equals(usuarioEncontrado.getCpfCnpj(), usuario.getCpfCnpj()))
                throw new InvalidRequestException("O CPF informado já existe");


            log.info("[PROGRESS] Atualizando o usuário encontrado com os valores recebidos no corpo da requisição...");
            usuarioAtualizado = usuarioEncontrado;
            usuarioAtualizado.setNome(usuario.getNome());
            usuarioAtualizado.setSenha(usuario.getSenha());
            usuarioAtualizado.setSenhaCriptografada(new BCryptPasswordEncoder().encode(usuario.getSenha()));
            usuarioAtualizado.setNomeUsuario(usuario.getNomeUsuario());
            usuarioAtualizado.setPrivilegio(usuario.getPrivilegio());
            usuarioAtualizado.setCpfCnpj(usuario.getCpfCnpj());
            usuarioAtualizado.setTelefone(usuario.getTelefone());
            usuarioAtualizado.setEmail(usuario.getEmail());
            usuarioAtualizado.setDataNascimento(usuario.getDataNascimento());
            usuarioAtualizado.setHabilitado(true);

            UsuarioEntity usuarioEntity;

            log.info("[PROGRESS] Iniciando persistência do usuário na base de dados...");
            try {
                usuarioEntity = repository.save(usuarioAtualizado);
            } catch (DataIntegrityViolationException exception) {
                log.error(exception.toString());
                throw new InvalidRequestException(
                        "O Cpf, e-mail ou username do colaborador devem ser únicos no banco de dados");
            }

            log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return modelMapper.mapper().map(usuarioEntity, UsuarioDTO.class);

        }

        log.info(USUARIO_NAO_ENCONTRADO_LOG);
        throw new InvalidRequestException(USUARIO_NAO_ENCONTRADO);

    }

    public void deletaPorId(Long id) {
        log.info(BARRA_DE_LOG);
        log.info("[INICIANDO] Inicializando método deletaPorId...");
        log.info("[PROGRESS] Buscando por um usuário com o id {}...", id);
        Optional<UsuarioEntity> usuarioOptional = repository.findById(id);
        if (usuarioOptional.isPresent()) {
            log.warn("[INFO] Usuário encontrado.");

            UsuarioEntity usuario = usuarioOptional.get();
            usuario.setHabilitado(false);

            log.info("[PROGRESS] Alterando status do usuário para desabilitado...");
            repository.save(usuario);
            log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
        } else {
            log.error("[FAILURE] Usuário com o id {} não encontrado", id);
            throw new ObjectNotFoundException(USUARIO_NAO_ENCONTRADO);
        }

    }

}
