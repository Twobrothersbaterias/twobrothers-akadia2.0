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

        if (validation.validaCorpoRequisicao(cliente, repository, ValidationType.CREATE)) {

            if (cliente.getEndereco() != null) {
                cliente.getEndereco().setDataCadastro(LocalDateTime.now());
                if (!enderecoValidation.validaCorpoRequisicao(cliente.getEndereco())) {
                    log.info(VALIDACAO_DO_ENDERECO_FALHOU_LOG);
                    throw new InvalidRequestException(VALIDACAO_DO_ENDERECO_FALHOU);
                }
            }

            log.info("[PROGRESS] Salvando o cliente na base de dados...");
            log.info("[SUCCESS] Requisição finalizada com sucesso");
            return modelMapper.mapper().map(repository.save(modelMapper.mapper().map(cliente, ClienteEntity.class)), ClienteDTO.class);
        }

        log.info(VALIDACAO_DO_CLIENTE_FALHOU_LOG);
        throw new InvalidRequestException(VALIDACAO_DO_CLIENTE_FALHOU);

    }

    public List<ClienteDTO> buscaTodos() {
        if (!repository.findAll().isEmpty()) return repository.findAll().stream()
                .map(x -> modelMapper.mapper().map(x, ClienteDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum cliente salvo na base de dados.");
    }

    public List<ClienteDTO> buscaPorPaginacao(Pageable paginacao) {
        if (!repository.findAll(paginacao).isEmpty()) return repository.findAll(paginacao)
                .getContent().stream().map(x -> modelMapper.mapper().map(x, ClienteDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum cliente cadastrado na página indicada");
    }

    public List<ClienteDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {

        List<ClienteEntity> clientes = repository.buscaPorRangeDeDataCadastro(
                (LocalDate.parse(dataInicio)).atTime(0, 0),
                (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

        if (!clientes.isEmpty())
            return clientes.stream().map(x -> modelMapper.mapper().map(x, ClienteDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum cliente cadastrado no range de datas indicado");

    }

    public ClienteDTO buscaPorId(Long id) {
        if (repository.findById(id).isPresent()) {
            return modelMapper.mapper().map(repository.findById(id).get(), ClienteDTO.class);
        }
        throw new ObjectNotFoundException("Não existe nenhum cliente cadastrado no banco de dados com o id " + id);
    }

    public ClienteDTO buscaPorCpfCnpj(String cpfCnpj) {
        if (repository.buscaPorCpfCnpj(cpfCnpj).isPresent()) {
            return modelMapper.mapper().map(repository.buscaPorCpfCnpj(cpfCnpj).get(), ClienteDTO.class);
        }
        throw new ObjectNotFoundException("Nenhum cliente foi encontrado através do atributo cpfCnpj enviado.");
    }

    public ClienteDTO buscaPorEmail(String email) {
        if (repository.buscaPorEmail(email).isPresent())
            return modelMapper.mapper().map(repository.buscaPorEmail(email).get(), ClienteDTO.class);
        throw new ObjectNotFoundException("Nenhum cliente foi encontrado através do atributo email enviado.");
    }

    public List<ClienteDTO> buscaPorTelefone(String telefone) {
        if (!repository.buscaPorTelefone(telefone).isEmpty())
            return repository.buscaPorTelefone(telefone).stream().map(x -> modelMapper.mapper().map(x, ClienteDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Nenhum cliente foi encontrado através do atributo telefone enviado.");
    }

    public List<ClienteDTO> buscaPorNomeCompleto(String nomeCompleto) {
        if (!repository.buscaPorNomeCompleto(nomeCompleto).isEmpty())
            return repository.buscaPorNomeCompleto(nomeCompleto).stream().map(x -> modelMapper.mapper().map(x, ClienteDTO.class)).collect(Collectors.toList());
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
        if (validation.validaCorpoRequisicao(cliente, repository, ValidationType.UPDATE)
                && clienteOptional.isPresent()) {

            clienteEncontrado = clienteOptional.get();
            log.warn("[INFO] Cliente encontrado: {}", clienteEncontrado.getNomeCompleto());

            log.info("[PROGRESS] Atualizando o cliente encontrado com os valores recebidos no corpo da requisição...");
            clienteAtualizado = clienteEncontrado;
            clienteAtualizado.setNomeCompleto(cliente.getNomeCompleto());
            clienteAtualizado.setCpfCnpj(cliente.getCpfCnpj());
            clienteAtualizado.setTelefone(cliente.getTelefone());
            clienteAtualizado.setDataNascimento(cliente.getDataNascimento());

            if (cliente.getEndereco() != null) {

                if (!enderecoValidation.validaCorpoRequisicao(cliente.getEndereco())) {
                    log.info(VALIDACAO_DO_ENDERECO_FALHOU_LOG);
                    throw new InvalidRequestException(VALIDACAO_DO_ENDERECO_FALHOU);
                }

                cliente.getEndereco().setDataCadastro(LocalDateTime.now());
                clienteAtualizado.setEndereco(modelMapper.mapper().map(cliente.getEndereco(), EnderecoEntity.class));
            }
            else{
                clienteAtualizado.setEndereco(null);
            }

            log.info("[PROGRESS] Iniciando persistência do cliente na base de dados...");
            repository.save(clienteAtualizado);

            log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return modelMapper.mapper().map(clienteAtualizado, ClienteDTO.class);

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
