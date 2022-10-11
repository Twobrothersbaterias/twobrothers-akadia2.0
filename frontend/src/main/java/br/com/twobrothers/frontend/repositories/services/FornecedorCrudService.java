package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.FornecedorDTO;
import br.com.twobrothers.frontend.models.entities.AbastecimentoEntity;
import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
import br.com.twobrothers.frontend.models.entities.OrdemEntity;
import br.com.twobrothers.frontend.models.enums.ValidationType;
import br.com.twobrothers.frontend.repositories.AbastecimentoRepository;
import br.com.twobrothers.frontend.repositories.FornecedorRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import br.com.twobrothers.frontend.validations.EnderecoValidation;
import br.com.twobrothers.frontend.validations.FornecedorValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.twobrothers.frontend.utils.StringConstants.*;
import static br.com.twobrothers.frontend.utils.TrataAtributosVazios.*;

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
public class FornecedorCrudService {

    @Autowired
    FornecedorRepository repository;

    @Autowired
    AbastecimentoRepository abastecimentoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    FornecedorValidation validation = new FornecedorValidation();
    EnderecoValidation enderecoValidation = new EnderecoValidation();

    public void criaNovo(FornecedorDTO fornecedor) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação");

        trataAtributosVaziosDoObjetoFornecedor(fornecedor);
        trataAtributosVaziosDoObjetoEndereco(fornecedor.getEndereco());

        log.info("[PROGRESS] Setando a data de cadastro no fornecedor: {}", LocalDateTime.now());
        fornecedor.setDataCadastro(LocalDate.now().toString());

        log.info("[PROGRESS] Setando usuário responsável pelo cadastro do fornecedor...");
        fornecedor.setUsuarioResponsavel(UsuarioUtils.loggedUser(usuarioRepository).getUsername());

        log.info("[PROGRESS] Validando objeto do tipo FornecedorDTO recebido por meio da requisição: {}", fornecedor);
        validation.validaCorpoRequisicao(fornecedor, repository, ValidationType.CREATE);

        log.info("[PROGRESS] Verificando se o objeto FornecedorDTO recebido por meio da requisição possui um objeto do tipo EnderecoDTO acoplado...");
        if (!verificaSeEnderecoNulo(fornecedor.getEndereco())) {

            log.info("[INFO] Objeto do tipo EnderecoDTO detectado: {}", fornecedor.getEndereco());

            log.info("[PROGRESS] Setando data de cadastro do endereço...");
            fornecedor.getEndereco().setDataCadastro(LocalDate.now().toString());

            log.info("[PROGRESS] Validando objeto do tipo EnderecoDTO recebido dentro do objeto FornecedorDTO...");
            enderecoValidation.validaCorpoRequisicao(fornecedor.getEndereco());

        }

        log.info("[PROGRESS] Salvando o fornecedor na base de dados...");
        repository.save(modelMapper.mapper().map(fornecedor, FornecedorEntity.class));

        log.info("[SUCCESS] Requisição finalizada com sucesso");
    }

    public List<FornecedorEntity> buscaPorRangeDeData(Pageable pageable, String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de fornecedor por range de data...");
        validation.validaRangeData(dataInicio, dataFim);
        return repository.buscaPorRangeDeDataCadastroPaginado(pageable, dataInicio, dataFim);
    }

    public List<FornecedorEntity> buscaPorPeriodo(Pageable pageable, Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de fornecedor por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorPeriodoPaginado(pageable, dataInicio.toString(), dataFim.toString());
    }

    public List<FornecedorEntity> buscaHoje(Pageable pageable) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todos os fornecedores cadastrados hoje...");
        LocalDate hoje = LocalDate.now();
        return repository.buscaHojePaginado(pageable, hoje.toString());
    }

    public List<FornecedorEntity> buscaPorNomeCompleto(Pageable pageable, String nomeCompleto) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de fornecedor por nome...");
        return repository.buscaPorNomePaginado(pageable, nomeCompleto);
    }

    public List<FornecedorEntity> buscaPorCpfCnpj(Pageable pageable, String cpfCnpj) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de fornecedor por cpfCnpj...");
        return repository.buscaPorCpfCnpjPaginado(pageable, cpfCnpj);
    }

    public List<FornecedorEntity> buscaPorTelefone(Pageable pageable, String telefone) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de fornecedor por telefone...");
        return repository.buscaPorTelefonePaginado(pageable, telefone);
    }

    public List<FornecedorEntity> buscaPorRangeDeDataSemPaginacao(String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de fornecedor por range de data...");
        return repository.buscaPorRangeDeDataCadastroSemPaginacao(dataInicio, dataFim);
    }

    public List<FornecedorEntity> buscaPorPeriodoSemPaginacao(Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de fornecedor por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorPeriodoSemPaginacao(dataInicio.toString(), dataFim.toString());
    }

    public List<FornecedorEntity> buscaHojeSemPaginacao() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todos os fornecedores cadastrados hoje...");
        LocalDate hoje = LocalDate.now();
        return repository.buscaHojeSemPaginacao(hoje.toString());
    }

    public List<FornecedorEntity> buscaPorNomeCompletoSemPaginacao(String nomeCompleto) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de fornecedor por nome...");
        return repository.buscaPorNomeSemPaginacao(nomeCompleto);
    }

    public List<FornecedorEntity> buscaPorCpfCnpjSemPaginacao(String cpfCnpj) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de fornecedor por cpfCnpj...");
        return repository.buscaPorCpfCnpjSemPaginacao(cpfCnpj);
    }

    public List<FornecedorEntity> buscaPorTelefoneSemPaginacao(String telefone) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de fornecedor por telefone...");
        return repository.buscaPorTelefoneSemPaginacao(telefone);
    }

    public FornecedorDTO atualizaPorId(FornecedorDTO fornecedor) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Inicializando método de atualização de fornecedor...");

        trataAtributosVaziosDoObjetoFornecedor(fornecedor);
        trataAtributosVaziosDoObjetoEndereco(fornecedor.getEndereco());

        log.info("[PROGRESS] Criando as variáveis do fornecedor: fornecedorOptional e fornecedorEncontrado...");
        Optional<FornecedorEntity> fornecedorOptional = repository.findById(fornecedor.getId());
        FornecedorEntity fornecedorEncontrado;

        log.info("[PROGRESS] Criando o objeto fornecedorAtualizado setagem dos atributos...");
        FornecedorEntity fornecedorAtualizado;

        log.info("[PROGRESS] Verificando se um fornecedor com o id {} já existe na base de dados...", fornecedor.getId());
        if (fornecedorOptional.isPresent()) {

            validation.validaCorpoRequisicao(fornecedor, repository, ValidationType.UPDATE);

            fornecedorEncontrado = fornecedorOptional.get();
            log.warn("[INFO] Fornecedor encontrado: {}", fornecedorEncontrado.getNome());

            log.info("[PROGRESS] Atualizando o fornecedor encontrado com os valores recebidos no corpo da requisição...");
            fornecedorAtualizado = fornecedorEncontrado;
            fornecedorAtualizado.setNome(fornecedor.getNome());
            fornecedorAtualizado.setCpfCnpj(fornecedor.getCpfCnpj());
            fornecedorAtualizado.setTelefone(fornecedor.getTelefone());
            fornecedorAtualizado.setEmail(fornecedor.getEmail());
            fornecedorAtualizado.setDataNascimento(fornecedor.getDataNascimento());

            log.info("[PROGRESS] Verificando se fornecedor possui objeto do tipo endereço acoplado...");
            if (!verificaSeEnderecoNulo(fornecedor.getEndereco())) {
                log.warn("[INFO] Endereço acoplado detectado.");
                enderecoValidation.validaCorpoRequisicao(fornecedor.getEndereco());
                log.info("[PROGRESS] Setando data de cadastro no endereço...");
                fornecedor.getEndereco().setDataCadastro(LocalDate.now().toString());

                log.info("[PROGRESS] Acoplando objeto do tipo EnderecoDTO na variável fornecedorAtualizado...");
                fornecedorAtualizado.getEndereco().setLogradouro(fornecedor.getEndereco().getLogradouro());
                fornecedorAtualizado.getEndereco().setNumero(fornecedor.getEndereco().getNumero());
                fornecedorAtualizado.getEndereco().setBairro(fornecedor.getEndereco().getBairro());
                fornecedorAtualizado.getEndereco().setCidade(fornecedor.getEndereco().getCidade());
                fornecedorAtualizado.getEndereco().setComplemento(fornecedor.getEndereco().getComplemento());
                fornecedorAtualizado.getEndereco().setCep(fornecedor.getEndereco().getCep());
                fornecedor.getEndereco().setEstado(fornecedor.getEndereco().getEstado());

            } else {
                log.warn("[INFO] Nenhum endereço foi detectado.");

                log.info("[PROGRESS] Setando endereço da variável fornecedorAtualizado como null...");
                fornecedorAtualizado.getEndereco().setLogradouro(null);
                fornecedorAtualizado.getEndereco().setNumero(null);
                fornecedorAtualizado.getEndereco().setBairro(null);
                fornecedorAtualizado.getEndereco().setCidade(null);
                fornecedorAtualizado.getEndereco().setComplemento(null);
                fornecedorAtualizado.getEndereco().setCep(null);
            }

            FornecedorEntity fornecedorEntity;

            log.info("[PROGRESS] Iniciando persistência do fornecedor na base de dados...");
            try {
                fornecedorEntity = repository.save(fornecedorAtualizado);
            } catch (DataIntegrityViolationException exception) {
                log.error(exception.toString());
                throw new InvalidRequestException("O CPF/CNPJ ou o E-MAIL Inserido já existe na base de dados");
            }

            log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return modelMapper.mapper().map(fornecedorEntity, FornecedorDTO.class);

        }

        log.info(FORNECEDOR_NAO_ENCONTRADO_LOG);
        throw new InvalidRequestException(FORNECEDOR_NAO_ENCONTRADO);

    }

    public void deletaPorId(Long id) {
        log.info(BARRA_DE_LOG);
        log.info("[INICIANDO] Inicializando método deletaPorId...");

        log.info("[PROGRESS] Buscando por um fornecedor com o id {}...", id);
        Optional<FornecedorEntity> fornecedorOptional = repository.findById(id);

        if (fornecedorOptional.isPresent()) {

            log.warn("[INFO] Fornecedor encontrado.");

            FornecedorEntity fornecedorEntity = fornecedorOptional.get();
            fornecedorEntity.setAbastecimentos(new ArrayList<>());
            repository.save(fornecedorEntity);

            for(AbastecimentoEntity abastecimentoEntity: abastecimentoRepository.buscaPorIdFornecedor(id)) {
                abastecimentoEntity.setFornecedor(null);
                abastecimentoRepository.save(abastecimentoEntity);
            }

            log.info("[PROGRESS] Removendo o fornecedor da base de dados...");
            repository.deleteById(id);

            log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);

        } else {
            log.error("[FAILURE] Fornecedor com o id {} não encontrado", id);
            throw new ObjectNotFoundException(FORNECEDOR_NAO_ENCONTRADO);
        }

    }

}
