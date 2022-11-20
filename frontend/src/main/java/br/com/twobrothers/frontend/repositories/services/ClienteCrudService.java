package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.ClienteDTO;
import br.com.twobrothers.frontend.models.dto.UsuarioDTO;
import br.com.twobrothers.frontend.models.entities.ClienteEntity;
import br.com.twobrothers.frontend.models.entities.OrdemEntity;
import br.com.twobrothers.frontend.models.enums.ValidationType;
import br.com.twobrothers.frontend.repositories.ClienteRepository;
import br.com.twobrothers.frontend.repositories.OrdemRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import br.com.twobrothers.frontend.validations.ClienteValidation;
import br.com.twobrothers.frontend.validations.EnderecoValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
public class ClienteCrudService {

    @Autowired
    ClienteRepository repository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    OrdemRepository ordemRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    ClienteValidation validation = new ClienteValidation();
    EnderecoValidation enderecoValidation = new EnderecoValidation();

    public List<ClienteEntity> buscaTodos() {
        return repository.findAll(Sort.by("nomeCompleto"));
    }

    public void criaNovo(ClienteDTO cliente) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação");

        trataAtributosVaziosDoObjetoCliente(cliente);
        trataAtributosVaziosDoObjetoEndereco(cliente.getEndereco());

        log.info("[PROGRESS] Setando a data de cadastro no cliente: {}", LocalDate.now());
        cliente.setDataCadastro(LocalDate.now().toString());

        log.info("[PROGRESS] Setando usuário responsável pelo cadastro do cliente...");
        cliente.setUsuarioResponsavel(modelMapper.mapper().map(UsuarioUtils.loggedUser(usuarioRepository), UsuarioDTO.class));

        log.info("[PROGRESS] Validando objeto do tipo ClienteDTO recebido por meio da requisição: {}", cliente);
        validation.validaCorpoRequisicao(cliente, repository, ValidationType.CREATE);

        log.info("[PROGRESS] Verificando se o objeto ClienteDTO recebido por meio da requisição possui um objeto do tipo EnderecoDTO acoplado...");
        if (!verificaSeEnderecoNulo(cliente.getEndereco())) {

            log.info("[INFO] Objeto do tipo EnderecoDTO detectado: {}", cliente.getEndereco());

            log.info("[PROGRESS] Setando data de cadastro do endereço...");
            cliente.getEndereco().setDataCadastro(LocalDate.now().toString());

            log.info("[PROGRESS] Validando objeto do tipo EnderecoDTO recebido dentro do objeto ClienteDTO...");
            enderecoValidation.validaCorpoRequisicao(cliente.getEndereco());

        }

        log.info("[PROGRESS] Salvando o cliente na base de dados...");
        repository.save(modelMapper.mapper().map(cliente, ClienteEntity.class));

        log.info("[SUCCESS] Requisição finalizada com sucesso");
    }

    public List<ClienteEntity> buscaPorRangeDeData(Pageable pageable, String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de cliente por range de data...");
        validation.validaRangeData(dataInicio, dataFim);
        return repository.buscaPorRangeDeDataCadastroPaginado(pageable, dataInicio, dataFim);
    }

    public List<ClienteEntity> buscaPorPeriodo(Pageable pageable, Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de cliente por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorPeriodoPaginado(pageable, dataInicio.toString(), dataFim.toString());
    }

    public List<ClienteEntity> buscaHoje(Pageable pageable) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todos os clientes cadastrados hoje...");
        LocalDate hoje = LocalDate.now();
        return repository.buscaHojePaginado(pageable, hoje.toString());
    }

    public List<ClienteEntity> buscaPorNomeCompleto(Pageable pageable, String nomeCompleto) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de cliente por nome...");
        return repository.buscaPorNomeCompletoPaginado(pageable, nomeCompleto);
    }

    public List<ClienteEntity> buscaPorCpfCnpj(Pageable pageable, String cpfCnpj) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de cliente por cpfCnpj...");
        return repository.buscaPorCpfCnpjPaginado(pageable, cpfCnpj);
    }

    public List<ClienteEntity> buscaPorTelefone(Pageable pageable, String telefone) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de cliente por telefone...");
        return repository.buscaPorTelefonePaginado(pageable, telefone);
    }

    public List<ClienteEntity> buscaPorRangeDeDataSemPaginacao(String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de cliente por range de data...");
        return repository.buscaPorRangeDeDataCadastroSemPaginacao(dataInicio, dataFim);
    }

    public List<ClienteEntity> buscaPorPeriodoSemPaginacao(Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de cliente por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorPeriodoSemPaginacao(dataInicio.toString(), dataFim.toString());
    }

    public List<ClienteEntity> buscaHojeSemPaginacao() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todos os clientes cadastrados hoje...");
        LocalDate hoje = LocalDate.now();
        return repository.buscaHojeSemPaginacao(hoje.toString());
    }

    public List<ClienteEntity> buscaPorNomeCompletoSemPaginacao(String nomeCompleto) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de cliente por nome...");
        return repository.buscaPorNomeCompletoSemPaginacao(nomeCompleto);
    }

    public List<ClienteEntity> buscaPorCpfCnpjSemPaginacao(String cpfCnpj) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de cliente por cpfCnpj...");
        return repository.buscaPorCpfCnpjSemPaginacao(cpfCnpj);
    }

    public List<ClienteEntity> buscaPorTelefoneSemPaginacao(String telefone) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de cliente por telefone...");
        return repository.buscaPorTelefoneSemPaginacao(telefone);
    }

    public ClienteDTO atualizaPorId(ClienteDTO cliente) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Inicializando método de atualização de cliente...");

        trataAtributosVaziosDoObjetoCliente(cliente);
        trataAtributosVaziosDoObjetoEndereco(cliente.getEndereco());

        log.info("[PROGRESS] Criando as variáveis do cliente: clienteOptional e clienteEncontrado...");
        Optional<ClienteEntity> clienteOptional = repository.findById(cliente.getId());
        ClienteEntity clienteEncontrado;

        log.info("[PROGRESS] Criando o objeto clienteAtualizado setagem dos atributos...");
        ClienteEntity clienteAtualizado;

        log.info("[PROGRESS] Verificando se um cliente com o id {} já existe na base de dados...", cliente.getId());
        if (clienteOptional.isPresent()) {

            validation.validaCorpoRequisicao(cliente, repository, ValidationType.UPDATE);

            clienteEncontrado = clienteOptional.get();
            log.warn("[INFO] Cliente encontrado: {}", clienteEncontrado.getNomeCompleto());

            log.info("[PROGRESS] Atualizando o cliente encontrado com os valores recebidos no corpo da requisição...");
            clienteAtualizado = clienteEncontrado;
            clienteAtualizado.setNomeCompleto(cliente.getNomeCompleto());
            clienteAtualizado.setCpfCnpj(cliente.getCpfCnpj());
            clienteAtualizado.setTelefone(cliente.getTelefone());
            clienteAtualizado.setEmail(cliente.getEmail());
            clienteAtualizado.setDataNascimento(cliente.getDataNascimento());

            log.info("[PROGRESS] Verificando se cliente possui objeto do tipo endereço acoplado...");
            if (!verificaSeEnderecoNulo(cliente.getEndereco())) {
                log.warn("[INFO] Endereço acoplado detectado.");
                enderecoValidation.validaCorpoRequisicao(cliente.getEndereco());
                log.info("[PROGRESS] Setando data de cadastro no endereço...");
                cliente.getEndereco().setDataCadastro(LocalDate.now().toString());

                log.info("[PROGRESS] Acoplando objeto do tipo EnderecoDTO na variável clienteAtualizado...");
                clienteAtualizado.getEndereco().setLogradouro(cliente.getEndereco().getLogradouro());
                clienteAtualizado.getEndereco().setNumero(cliente.getEndereco().getNumero());
                clienteAtualizado.getEndereco().setBairro(cliente.getEndereco().getBairro());
                clienteAtualizado.getEndereco().setCidade(cliente.getEndereco().getCidade());
                clienteAtualizado.getEndereco().setComplemento(cliente.getEndereco().getComplemento());
                clienteAtualizado.getEndereco().setCep(cliente.getEndereco().getCep());
                cliente.getEndereco().setEstado(cliente.getEndereco().getEstado());

            } else {
                log.warn("[INFO] Nenhum endereço foi detectado.");

                if (clienteAtualizado.getEndereco() != null) {
                    log.info("[PROGRESS] Setando endereço da variável clienteAtualizado como null...");
                    clienteAtualizado.getEndereco().setLogradouro(null);
                    clienteAtualizado.getEndereco().setNumero(null);
                    clienteAtualizado.getEndereco().setBairro(null);
                    clienteAtualizado.getEndereco().setCidade(null);
                    clienteAtualizado.getEndereco().setComplemento(null);
                    clienteAtualizado.getEndereco().setCep(null);
                }
            }

            ClienteEntity clienteEntity;

            log.info("[PROGRESS] Iniciando persistência do cliente na base de dados...");
            try {
                clienteEntity = repository.save(clienteAtualizado);
            } catch (DataIntegrityViolationException exception) {
                log.error(exception.toString());
                throw new InvalidRequestException("O CPF/CNPJ ou o E-MAIL Inserido já existe na base de dados");
            }

            log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return modelMapper.mapper().map(clienteEntity, ClienteDTO.class);

        }

        log.info(CLIENTE_NAO_ENCONTRADO_LOG);
        throw new InvalidRequestException(CLIENTE_NAO_ENCONTRADO);

    }

    public void deletaPorId(Long id) {
        log.info(BARRA_DE_LOG);
        log.info("[INICIANDO] Inicializando método deletaPorId...");
        log.info("[PROGRESS] Buscando por um cliente com o id {}...", id);
        Optional<ClienteEntity> clienteOptional = repository.findById(id);
        if (clienteOptional.isPresent()) {
            log.warn("[INFO] Cliente encontrado.");

            ClienteEntity cliente = clienteOptional.get();

            if (cliente.getOrdens() != null && !cliente.getOrdens().isEmpty()) {
                List<OrdemEntity> ordensDoCliente = cliente.getOrdens();
                for(int i = 0; i < ordensDoCliente.size(); i++) {
                    cliente = remocaoDeClienteDaOrdem(ordensDoCliente.get(i));
                    if (!ordensDoCliente.isEmpty()) i -= 1;
                }
            }

            log.info("[PROGRESS] Removendo o cliente da base de dados...");
            repository.deleteById(id);
            log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
        } else {
            log.error("[FAILURE] Cliente com o id {} não encontrado", id);
            throw new ObjectNotFoundException(CLIENTE_NAO_ENCONTRADO);
        }

    }

    private ClienteEntity remocaoDeClienteDaOrdem(OrdemEntity ordem) {
        ClienteEntity clienteEntity = ordem.getCliente();
        clienteEntity.removeOrdem(ordem);
        ordemRepository.save(ordem);
        return repository.save(clienteEntity);
    }

}
