package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.ClienteDTO;
import br.com.twobrothers.frontend.models.entities.ClienteEntity;
import br.com.twobrothers.frontend.models.entities.EnderecoEntity;
import br.com.twobrothers.frontend.models.enums.ValidationType;
import br.com.twobrothers.frontend.repositories.ClienteRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.frontend.validations.ClienteValidation;
import br.com.twobrothers.frontend.validations.EnderecoValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
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
public class ClienteCrudService {

    @Autowired
    ClienteRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    ClienteValidation validation = new ClienteValidation();
    EnderecoValidation enderecoValidation = new EnderecoValidation();

    public void criaNovo(ClienteDTO cliente) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação");

        log.info("[PROGRESS] Setando a data de cadastro no cliente: {}", LocalDateTime.now());
        cliente.setDataCadastro(LocalDateTime.now());

        log.info("[PROGRESS] Validando objeto do tipo ClienteDTO recebido por meio da requisição...");
        validation.validaCorpoRequisicao(cliente, repository, ValidationType.CREATE);

        log.info("[PROGRESS] Verificando se o objeto ClienteDTO recebido por meio da requisição possui um objeto do tipo EnderecoDTO acoplado...");
        if (cliente.getEndereco() != null) {
            log.info("[INFO] Objeto do tipo EnderecoDTO detectado.");

            log.info("[PROGRESS] Setando data de cadastro do endereço...");
            cliente.getEndereco().setDataCadastro(LocalDateTime.now());

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
            clienteAtualizado.setDataNascimento(cliente.getDataNascimento());

            log.info("[PROGRESS] Verificando se cliente possui objeto do tipo endereço acoplado...");
            if (cliente.getEndereco() != null) {
                log.warn("[INFO] Endereço acoplado detectado.");
                enderecoValidation.validaCorpoRequisicao(cliente.getEndereco());
                log.info("[PROGRESS] Setando data de cadastro no endereço...");
                cliente.getEndereco().setDataCadastro(LocalDateTime.now());
                log.info("[PROGRESS] Acoplando objeto do tipo EnderecoDTO na variável clienteAtualizado...");
                clienteAtualizado.setEndereco(modelMapper.mapper().map(cliente.getEndereco(), EnderecoEntity.class));
            } else {
                log.warn("[INFO] Nenhum endereço foi detectado.");

                log.info("[PROGRESS] Setando endereço da variável clienteAtualizado como null...");
                clienteAtualizado.setEndereco(null);
            }

            log.info("[PROGRESS] Iniciando persistência do cliente na base de dados...");
            ClienteEntity clienteEntity = repository.save(clienteAtualizado);

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
            log.info("[PROGRESS] Removendo o cliente da base de dados...");
            repository.deleteById(id);
            log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
        }
        else {
            log.error("[FAILURE] Cliente com o id {} não encontrado", id);
            throw new ObjectNotFoundException(CLIENTE_NAO_ENCONTRADO);
        }

    }

}
