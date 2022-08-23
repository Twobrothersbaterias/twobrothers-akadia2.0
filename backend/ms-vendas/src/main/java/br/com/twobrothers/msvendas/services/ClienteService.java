package br.com.twobrothers.msvendas.services;

import br.com.twobrothers.msvendas.config.ModelMapperConfig;
import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.ClienteDTO;
import br.com.twobrothers.msvendas.models.dto.EnderecoDTO;
import br.com.twobrothers.msvendas.models.entities.ClienteEntity;
import br.com.twobrothers.msvendas.models.entities.EnderecoEntity;
import br.com.twobrothers.msvendas.models.entities.OrdemEntity;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.ClienteRepository;
import br.com.twobrothers.msvendas.repositories.EnderecoRepository;
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
    EnderecoRepository enderecoRepository;

    @Autowired
    EnderecoService enderecoService;

    @Autowired
    ModelMapperConfig modelMapper;

    ClienteValidation validation = new ClienteValidation();
    EnderecoValidation enderecoValidation = new EnderecoValidation();

    public ClienteDTO criaNovo(ClienteDTO cliente) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação");

        log.info("[PROGRESS] Setando a data de cadastro no cliente: {}", LocalDateTime.now());
        cliente.setDataCadastro(LocalDateTime.now());

        log.info("[PROGRESS] Verificando se objeto ClienteDTO possui objeto do tipo EnderecoDTO acoplado...");

        if (cliente.getEndereco() != null) {

            log.warn("[INFO] Objeto EnderecoDTO acoplado detectado");

            log.info("[PROGRESS] Validando corpo do objeto ClienteDTO passado via JSON...");
            if (validation.validaCorpoRequisicao(cliente, repository, ValidationType.CREATE)) {

                log.info("[PROGRESS] Verificando se o endereço já existe na base de dados: {}", cliente.getEndereco());

                Optional<EnderecoEntity> enderecoEntity = enderecoRepository.buscaPorAtributos(
                        cliente.getEndereco().getLogradouro(),
                        cliente.getEndereco().getBairro(),
                        cliente.getEndereco().getNumero()
                );

                EnderecoDTO enderecoDTO;

                if (enderecoEntity.isPresent()) {
                    log.warn("[INFO] O endereço passado já existe");
                    enderecoDTO = modelMapper.mapper().map(enderecoEntity.get(), EnderecoDTO.class);
                } else {
                    log.warn("[INFO] O endereço passado não existe");
                    enderecoDTO = cliente.getEndereco();

                    log.info("[PROGRESS] Validando corpo do objeto EnderecoDTO passado via JSON...");
                    if (enderecoValidation.validaCorpoRequisicao(enderecoDTO, enderecoRepository, ValidationType.CREATE)) {
                        log.info("[PROGRESS] Setando a data de cadastro no endereço: {}", LocalDateTime.now());
                        enderecoDTO.setDataCadastro(LocalDateTime.now());
                    } else {
                        log.error(VALIDACAO_DO_ENDERECO_FALHOU_LOG);
                        throw new InvalidRequestException(VALIDACAO_DO_ENDERECO_FALHOU);
                    }
                }

                enderecoDTO.addCliente(cliente);

                log.info("[PROGRESS] Salvando o cliente na base de dados...");
                enderecoRepository.save(modelMapper.mapper().map(enderecoDTO, EnderecoEntity.class));
                log.info("[SUCCESS] Requisição finalizada com sucesso");
                return cliente;

            }

        } else {

            log.warn("[INFO] Objeto EnderecoDTO acoplado não detectado");

            log.info("[PROGRESS] Validando corpo do objeto EnderecoDTO passado via JSON...");
            if (validation.validaCorpoRequisicao(cliente, repository, ValidationType.CREATE)) {
                log.info("[PROGRESS] Salvando o cliente na base de dados...");
                log.info("[SUCCESS] Requisição finalizada com sucesso");
                return modelMapper.mapper().map(repository.save(modelMapper.mapper().map(cliente, ClienteEntity.class)), ClienteDTO.class);
            }

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

    public ClienteDTO atualizaCliente(Long id, ClienteDTO cliente) {

        Optional<ClienteEntity> clienteOptional = repository.findById(id);

        if (clienteOptional.isPresent()) {

            ClienteEntity clienteAtualizado = clienteOptional.get();

            if (validation.validaCorpoRequisicao(cliente, repository, ValidationType.UPDATE)) {

                clienteAtualizado.setCpfCnpj(cliente.getCpfCnpj());
                clienteAtualizado.setEmail(cliente.getEmail());
                clienteAtualizado.setEndereco(modelMapper.mapper().map(cliente.getEndereco(), EnderecoEntity.class));
                clienteAtualizado.setDataNascimento(cliente.getDataNascimento());
                clienteAtualizado.setNomeCompleto(cliente.getNomeCompleto());
                clienteAtualizado.setTelefone(cliente.getTelefone());
                clienteAtualizado.setOrdens(cliente.getOrdens().stream()
                        .map(x -> modelMapper.mapper().map(x, OrdemEntity.class)).collect(Collectors.toList()));

                return modelMapper.mapper().map(repository.save(clienteAtualizado), ClienteDTO.class);

            }

            throw new InvalidRequestException("Corpo da requisição inválido");

        }
        throw new ObjectNotFoundException("Não existe nenhum cliente cadastrado com o id " + id);

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

            log.info("[PROGRESS] Verificando se algum endereço foi enviado via JSON...");
            if (cliente.getEndereco() != null) {

                if (!enderecoValidation.validaCorpoRequisicao(cliente.getEndereco(), enderecoRepository, ValidationType.UPDATE)) {
                    log.error(VALIDACAO_DO_ENDERECO_FALHOU_LOG);
                    throw new InvalidRequestException(VALIDACAO_DO_ENDERECO_FALHOU);
                }

                log.info("[PROGRESS] Criando as variáveis do endereço: enderecoOptional e enderecoEncontrado...");
                Optional<EnderecoEntity> enderecoOptional = enderecoRepository.buscaPorAtributos(
                        cliente.getEndereco().getLogradouro(),
                        cliente.getEndereco().getBairro(),
                        cliente.getEndereco().getNumero()
                );
                EnderecoEntity enderecoEncontrado;

                log.info("[PROGRESS] Criando o objeto enderecoAtualizado para setagem dos atributos...");
                EnderecoEntity enderecoAtualizado;

                log.info("[PROGRESS] Verificando se o endereço recebido via JSON existe na base de dados...");
                if (enderecoOptional.isPresent()) {

                    enderecoEncontrado = enderecoOptional.get();
                    log.info("[INFO] Endereço encontrado: {}, {}", enderecoEncontrado.getLogradouro(), enderecoEncontrado.getNumero());

                    log.info("[PROGRESS] Verificando se o endereço passado na requisição é diferente do antigo endereço do cliente...");
                    if (clienteEncontrado.getEndereco() != enderecoEncontrado) {

                        log.warn("[INFO] O endereço recebido é diferente.");
                        log.info("[PROGRESS] Removendo o cliente a ser atualizado do endereço antigo...");
                        EnderecoEntity enderecoAntigo = clienteEncontrado.getEndereco();
                        enderecoAntigo.removeCliente(clienteEncontrado);

                        log.info("[PROGRESS] Salvando o endereço antigo sem o cliente...");
                        enderecoRepository.save(enderecoAntigo);

                        log.info("[PROGRESS] Setando o valor do endereço atualizado com o valor do endereço encontrado...");
                        enderecoAtualizado = modelMapper.mapper().map(enderecoEncontrado, EnderecoEntity.class);

                    } else {
                        log.warn("[INFO] O endereço recebido na requisição é igual ao endereço antigo do cliente.");
                        log.info("[PROGRESS] Setando a variável enderecoAtualizado com o valor do endereco encontrado...");
                        enderecoAtualizado = enderecoEncontrado;

                        log.info("[PROGRESS] Removendo o cliente do endereço antigo...");
                        EnderecoEntity enderecoAntigo = clienteEncontrado.getEndereco();
                        enderecoAntigo.removeCliente(clienteEncontrado);

                    }

                } else {

                    log.warn("[INFO] Endereço não encontrado.");

                    log.info("[PROGRESS] Removendo o cliente do endereço antigo...");
                    EnderecoEntity enderecoAntigo = clienteEncontrado.getEndereco();
                    enderecoAntigo.removeCliente(clienteEncontrado);

                    log.info("[PROGRESS] Salvando o endereço antigo sem o cliente...");
                    enderecoRepository.save(enderecoAntigo);

                    log.info("[PROGRESS] Setando o valor da variável enderecoAtualizado com o valor passado via JSON...");
                    enderecoAtualizado = modelMapper.mapper().map(cliente.getEndereco(), EnderecoEntity.class);
                    enderecoAtualizado.setDataCadastro(LocalDateTime.now());

                }

                log.info("[PROGRESS] Adicionando o endereço ao cliente e o cliente ao endereço...");
                enderecoAtualizado.addCliente(clienteAtualizado);

                log.info("[PROGRESS] Salvando o novo endereço com o cliente atualizado dentro...");
                enderecoRepository.save(enderecoAtualizado);

            } else {

                log.warn("[INFO] Nenhum endereço foi recebido na requisição");

                log.info("[INFO] Verifica se o cliente antigo possuia um endereço cadastrado");
                if (clienteEncontrado.getEndereco() != null) {

                    EnderecoEntity enderecoEncontrado = new EnderecoEntity();

                    Optional<EnderecoEntity> enderecoOptional = enderecoRepository.buscaPorAtributos(
                            clienteEncontrado.getEndereco().getLogradouro(),
                            clienteEncontrado.getEndereco().getBairro(),
                            clienteEncontrado.getEndereco().getNumero()
                    );

                    if (enderecoOptional.isPresent()) {
                        enderecoEncontrado = enderecoOptional.get();
                    }

                    enderecoEncontrado.getClientes().remove(clienteEncontrado);
                    enderecoRepository.save(enderecoEncontrado);
                }

                log.info("[PROGRESS] Salvando o cliente na base de dados...");
                repository.save(clienteAtualizado);

            }

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

            log.info("[PROGRESS] Buscando pelo endereço do cliente na base de dados...");
            Optional<EnderecoEntity> optionalAddress = enderecoRepository.buscaPorAtributos(
                    clienteOptional.get().getEndereco().getLogradouro(),
                    clienteOptional.get().getEndereco().getBairro(),
                    clienteOptional.get().getEndereco().getNumero()
            );

            if (optionalAddress.isPresent()) {
                log.warn("[INFO] Endereço encontrado: {}, {}", optionalAddress.get().getLogradouro(), optionalAddress.get().getNumero());
                log.info("[PROGRESS] Removendo o cliente da lista de clientes do endereço...");
                optionalAddress.get().getClientes().remove(clienteOptional.get());
                log.info("[PROGRESS] Salvando o endereço sem o cliente na lista..");
                enderecoRepository.save(optionalAddress.get());
            }

            log.info("[PROGRESS] Removendo o cliente da base de dados...");
            repository.deleteById(id);

            log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return true;

        }
        log.error("[FAILURE] Cliente com o id {} não encontrado", id);
        throw new ObjectNotFoundException(CLIENTE_NAO_ENCONTRADO);

    }

}
