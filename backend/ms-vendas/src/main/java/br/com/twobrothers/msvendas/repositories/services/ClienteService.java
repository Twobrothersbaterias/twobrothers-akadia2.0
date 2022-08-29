package br.com.twobrothers.msvendas.repositories.services;

import br.com.twobrothers.msvendas.config.ModelMapperConfig;
import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.ClienteDTO;
import br.com.twobrothers.msvendas.models.entities.ClienteEntity;
import br.com.twobrothers.msvendas.models.entities.EnderecoEntity;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.ClienteRepository;
import br.com.twobrothers.msvendas.validations.ClienteValidation;
import br.com.twobrothers.msvendas.validations.EnderecoValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.twobrothers.msvendas.utils.StringConstants.*;

@Slf4j
@Service
public class ClienteService {

    @Autowired
    ClienteRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    ClienteValidation validation = new ClienteValidation();
    EnderecoValidation enderecoValidation = new EnderecoValidation();

    public ClienteDTO criaNovo(ClienteDTO cliente) {
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
        ClienteEntity clienteEntity = repository.save(modelMapper.mapper().map(cliente, ClienteEntity.class));

        log.info("[SUCCESS] Requisição finalizada com sucesso");
        return modelMapper.mapper().map(clienteEntity, ClienteDTO.class);

    }

    public List<ClienteDTO> buscaTodos() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todos os clientes...");

        if (!repository.findAll().isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.findAll().stream()
                    .map(x -> modelMapper.mapper().map(x, ClienteDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhum cliente salvo na base de dados");
        throw new ObjectNotFoundException("Não existe nenhum cliente salvo na base de dados.");
    }

    public List<ClienteDTO> buscaPorPaginacao(Pageable paginacao) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de cliente por paginação...");
        if (!repository.findAll(paginacao).isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.findAll(paginacao)
                    .getContent().stream().map(x -> modelMapper.mapper().map(x, ClienteDTO.class)).collect(Collectors.toList());
        }
        log.error("Nenhum cliente foi encontrado considerando os parâmetros da paginação...");
        throw new ObjectNotFoundException("Não existe nenhum cliente cadastrado na página indicada");
    }

    public List<ClienteDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca por range de cadastro...");

        List<ClienteEntity> clientes = repository.buscaPorRangeDeDataCadastro(
                (LocalDate.parse(dataInicio)).atTime(0, 0),
                (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

        if (!clientes.isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return clientes.stream().map(x -> modelMapper.mapper().map(x, ClienteDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Nenhum cliente foi encontrado considerando os parâmetros recebidos");
        throw new ObjectNotFoundException("Não existe nenhum cliente cadastrado no range de datas indicado");

    }

    public ClienteDTO buscaPorId(Long id) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca por id...");
        if (repository.findById(id).isPresent()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return modelMapper.mapper().map(repository.findById(id).get(), ClienteDTO.class);
        }
        log.error("[ERROR] Não existe nenhum cliente cadastrado no banco de dados com o id " + id);
        throw new ObjectNotFoundException("Não existe nenhum cliente cadastrado no banco de dados com o id " + id);
    }

    public ClienteDTO buscaPorCpfCnpj(String cpfCnpj) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de cliente por CPF/CNPJ");
        if (repository.buscaPorCpfCnpj(cpfCnpj).isPresent()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return modelMapper.mapper().map(repository.buscaPorCpfCnpj(cpfCnpj).get(), ClienteDTO.class);
        }
        log.error("[ERROR] Nenhum cliente foi encontrado através do tributo CPF/CNPJ enviado");
        throw new ObjectNotFoundException("Nenhum cliente foi encontrado através do atributo cpfCnpj enviado.");
    }

    public ClienteDTO buscaPorEmail(String email) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de cliente por EMAIL");
        if (repository.buscaPorEmail(email).isPresent()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return modelMapper.mapper().map(repository.buscaPorEmail(email).get(), ClienteDTO.class);
        }
        log.error("[ERROR] Nenhum cliente foi encontrado através do atributo EMAIL enviado");
        throw new ObjectNotFoundException("Nenhum cliente foi encontrado através do atributo email enviado.");
    }

    public List<ClienteDTO> buscaPorTelefone(String telefone) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de cliente por TELEFONE");
        if (!repository.buscaPorTelefone(telefone).isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.buscaPorTelefone(telefone).stream().map(x -> modelMapper.mapper().map(x, ClienteDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Nenhum cliente foi encontrado através do atributo TELEFONE enviado");
        throw new ObjectNotFoundException("Nenhum cliente foi encontrado através do atributo telefone enviado.");
    }

    public List<ClienteDTO> buscaPorNomeCompleto(String nomeCompleto) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de cliente por NOME COMPLETO");
        if (!repository.buscaPorNomeCompleto(nomeCompleto).isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.buscaPorNomeCompleto(nomeCompleto).stream().map(x -> modelMapper.mapper().map(x, ClienteDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Nenhum cliente foi encontrado através do atributo nomeCompleto enviado");
        throw new ObjectNotFoundException("Nenhum cliente foi encontrado através do atributo nomeCompleto enviado.");
    }

    public ClienteDTO atualizaPorId(Long id, ClienteDTO cliente) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Inicializando método de atualização de cliente...");

        log.info("[PROGRESS] Criando as variáveis do cliente: clienteOptional e clienteEncontrado...");
        Optional<ClienteEntity> clienteOptional = repository.findById(id);
        ClienteEntity clienteEncontrado;

        log.info("[PROGRESS] Criando o objeto clienteAtualizado setagem dos atributos...");
        ClienteEntity clienteAtualizado;

        log.info("[PROGRESS] Verificando se um cliente com o id {} já existe na base de dados...", id);
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
            }
            else {
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

    public Boolean deletaPorId(Long id) {

        log.info(BARRA_DE_LOG);
        log.info("[INICIANDO] Inicializando método deletaPorId...");

        log.info("[PROGRESS] Buscando por um cliente com o id {}...", id);
        Optional<ClienteEntity> clienteOptional = repository.findById(id);

        if (clienteOptional.isPresent()) {

            log.warn("[INFO] Cliente encontrado.");

            log.info("[PROGRESS] Removendo o cliente da base de dados...");
            repository.deleteById(id);

            log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return true;

        }
        log.error("[FAILURE] Cliente com o id {} não encontrado", id);
        throw new ObjectNotFoundException(CLIENTE_NAO_ENCONTRADO);

    }

}
