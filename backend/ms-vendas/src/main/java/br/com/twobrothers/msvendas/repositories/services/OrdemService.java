package br.com.twobrothers.msvendas.repositories.services;

import br.com.twobrothers.msvendas.config.ModelMapperConfig;
import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.EntradaOrdemDTO;
import br.com.twobrothers.msvendas.models.dto.OrdemDTO;
import br.com.twobrothers.msvendas.models.entities.*;
import br.com.twobrothers.msvendas.repositories.*;
import br.com.twobrothers.msvendas.services.GerenciamentoEstoqueService;
import br.com.twobrothers.msvendas.validations.ClienteValidation;
import br.com.twobrothers.msvendas.validations.OrdemValidation;
import br.com.twobrothers.msvendas.validations.ProdutoEstoqueValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.twobrothers.msvendas.utils.StringConstants.BARRA_DE_LOG;

@Slf4j
@Service
public class OrdemService {

    @Autowired
    OrdemRepository repository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProdutoEstoqueRepository produtoEstoqueRepository;

    @Autowired
    EntradaOrdemRepository entradaOrdemRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    GerenciamentoEstoqueService gerenciamentoEstoqueService;

    @Autowired
    ModelMapperConfig modelMapper;

    OrdemValidation validation = new OrdemValidation();
    ClienteValidation clienteValidation = new ClienteValidation();
    ProdutoEstoqueValidation produtoEstoqueValidation = new ProdutoEstoqueValidation();

    String PERSISTENCIA_EM_CASCATA_STRING =
            "[PROGRESS] Persistindo endereço na base de dados com cliente -> ordem acoplado...";

    public OrdemDTO criaNovo(OrdemDTO ordem) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação...");

        log.info("[PROGRESS] Validando objeto do tipo OrdemDTO enviado via JSON...");
        if (validation.validaCorpoRequisicao(ordem)) {

            OrdemEntity ordemEntity = new OrdemEntity();
            List<EntradaOrdemEntity> entradaOrdemEntities = new ArrayList<>();

            for (EntradaOrdemDTO entrada : ordem.getEntradas()) {

                log.info("[PROGRESS] Encaminhando para método de gerenciamento de estoque...");
                gerenciamentoEstoqueService.distribuiParaTrocaOuProduto(entrada);

                String sigla = entrada.getProduto().getSigla();
                entrada.setOrdem(null);
                entrada.setProduto(null);

                log.info("[PROGRESS] Setando data de cadastro na ordem: {}", LocalDateTime.now());
                ordem.setDataCadastro(LocalDateTime.now());

                log.info("[PROGRESS] Persistindo as entradas no banco de dados SEM o produto e SEM a ordem...");

                EntradaOrdemEntity entradaOrdemEntity =
                        entradaOrdemRepository.save(modelMapper.mapper().map(entrada, EntradaOrdemEntity.class));

                ProdutoEstoqueEntity produtoEstoque = produtoEstoqueRepository.buscaPorSigla(sigla).get();

                log.info("[PROGRESS] Adicionando entrada ao produto e produto à entrada...");
                produtoEstoque.addEntrada(entradaOrdemEntity);

                log.info("[PROGRESS] Persistindo produto no banco de dados com a nova entrada na lista...");
                produtoEstoqueRepository.save(produtoEstoque);

                log.info("[PROGRESS] Adicionando entrada à ordem e ordem à entrada...");
                ordem.setEntradas(new ArrayList<>());

                ordemEntity = modelMapper.mapper().map(ordem, OrdemEntity.class);
                ordemEntity.setCliente(null);

                entradaOrdemEntities.add(entradaOrdemEntity);

            }

            ordemEntity.setEntradas(entradaOrdemEntities);
            ordemEntity = repository.save(ordemEntity);

            log.info("[PROGRESS] Verificando se cliente passado pelo corpo da requisição é nulo...");
            if (ordem.getCliente() != null) {

                log.warn("[INFO] Cliente recebido pelo corpo da requisição");

                Optional<ClienteEntity> clienteEntityOptional =
                        clienteRepository.buscaPorCpfCnpj(ordem.getCliente().getCpfCnpj());

                ClienteEntity clienteEntity;

                log.info("[PROGRESS] Verificando se cliente passado existe na base de dados...");
                if (clienteEntityOptional.isPresent()) {
                    log.info("[INFO] Cliente encontrado: {}", clienteEntityOptional.get().getNomeCompleto());

                    clienteEntity = clienteEntityOptional.get();
                    clienteEntity.addOrdem(ordemEntity);

                    log.info("[PROGRESS] Verificando se foi passado um endereço através do corpo do cliente...");
                    if (ordem.getCliente().getEndereco() != null) {

                        log.info("[INFO] Endereço passado através do corpo do cliente");

                        Optional<EnderecoEntity> optionalEnderecoEntity =
                                enderecoRepository.buscaPorAtributos(ordem.getCliente().getEndereco().getLogradouro(),
                                        ordem.getCliente().getEndereco().getBairro(),
                                        ordem.getCliente().getEndereco().getNumero());

                        log.info("[PROGRESS] Verificando se endereço passado na requisição é o mesmo persistido na base de dados...");
                        if (optionalEnderecoEntity.isPresent() && optionalEnderecoEntity.get().equals(clienteEntity.getEndereco())) {
                            log.info("[INFO] O endereço do cliente é o mesmo de antes");

                            log.info("[PROGRESS] Atualizando o cliente com a ordem acoplada na base de dados...");
                            clienteRepository.save(clienteEntity);
                        }
                        else {
                            log.info("[INFO] O endereço do cliente mudou");

                            log.info("[PROGRESS] Verificando se o endereço para qual o cliente mudou existe na base de dados...");
                            if (optionalEnderecoEntity.isPresent()) {
                                log.info("[INFO] Endereço encontrado: {}", optionalEnderecoEntity.get().getLogradouro());
                                EnderecoEntity enderecoEntity = optionalEnderecoEntity.get();

                                log.info("[PROGRESS] Adicionando o endereço ao cliente e o cliente ao endereço...");
                                enderecoEntity.addCliente(clienteEntity);

                                log.info(PERSISTENCIA_EM_CASCATA_STRING);
                                enderecoRepository.save(enderecoEntity);
                            }
                            else {
                                log.info("[INFO] Nenhum endereço foi encontrado na base de dados...");
                                EnderecoEntity enderecoEntity = modelMapper.mapper().map(ordem.getCliente().getEndereco(), EnderecoEntity.class);

                                log.info("[PROGRESS] Adicionando data de cadastro ao endereço: {}", LocalDateTime.now());
                                enderecoEntity.setDataCadastro(LocalDateTime.now());

                                log.info("[PROGRESS] Adicionando o endereço ao cliente e o cliente ao endereço...");
                                enderecoEntity.addCliente(clienteEntity);

                                log.info(PERSISTENCIA_EM_CASCATA_STRING);
                                enderecoRepository.save(enderecoEntity);
                            }
                        }
                    }
                    else {
                        log.info("[INFO] Nenhum endereço foi passado através do corpo do cliente");
                        clienteEntity.addOrdem(ordemEntity);
                        clienteEntity.setEndereco(null);
                        clienteRepository.save(clienteEntity);
                    }
                }
                else {
                    log.info("[INFO] Cliente não encontrado");

                    log.info("[PROGRESS] Setando valor da variável clienteEntity para o valor do objeto cliente passado via JSON...");
                    clienteEntity = modelMapper.mapper().map(ordem.getCliente(), ClienteEntity.class);

                    log.info("[PROGRESS] Setando data de cadastro do cliente para {}...", LocalDateTime.now());
                    clienteEntity.setDataCadastro(LocalDateTime.now());

                    log.info("[PROGRESS] Adicionando ordem ao cliente e cliente à ordem...");
                    clienteEntity.addOrdem(ordemEntity);

                    log.info("[PROGRESS] Verificando se foi passado um endereço através do corpo do cliente...");
                    if (ordem.getCliente().getEndereco() != null) {

                        log.info("[INFO] Endereço passado através do corpo do cliente");

                        Optional<EnderecoEntity> optionalEnderecoEntity =
                                enderecoRepository.buscaPorAtributos(ordem.getCliente().getEndereco().getLogradouro(),
                                        ordem.getCliente().getEndereco().getBairro(),
                                        ordem.getCliente().getEndereco().getNumero());

                        log.info("[PROGRESS] Verificando se endereço passado existe na base de dados...");
                        if (optionalEnderecoEntity.isPresent()) {
                            log.warn("[INFO] Endereço encontrado: {}", optionalEnderecoEntity.get().getLogradouro());
                            EnderecoEntity enderecoEntity = optionalEnderecoEntity.get();

                            log.info("[PROGRESS] Setando data de cadastro para o enderecoEntity {}...", LocalDateTime.now());
                            enderecoEntity.setDataCadastro(LocalDateTime.now());

                            log.info("[PROGRESS] Adicionando cliente ao endereço e endereço ao cliente...");
                            enderecoEntity.addCliente(clienteEntity);

                            log.info(PERSISTENCIA_EM_CASCATA_STRING);
                            enderecoRepository.save(enderecoEntity);
                        }
                        else {
                            log.warn("[INFO] Endereço não encontrado");
                            EnderecoEntity enderecoEntity = clienteEntity.getEndereco();

                            log.info("[PROGRESS] Setando data de cadastro para o enderecoEntity {}...", LocalDateTime.now());
                            enderecoEntity.setDataCadastro(LocalDateTime.now());

                            log.info("[PROGRESS] Adicionando cliente ao endereço e endereço ao cliente...");
                            enderecoEntity.addCliente(clienteEntity);

                            log.info(PERSISTENCIA_EM_CASCATA_STRING);
                            enderecoRepository.save(enderecoEntity);
                        }
                    }
                    else {
                        log.info("[INFO] Nenhum endereço foi passado através do corpo do cliente");
                        clienteEntity.addOrdem(ordemEntity);
                        clienteEntity.setEndereco(null);
                        clienteRepository.save(clienteEntity);
                    }

                }

            } else {
                log.warn("[INFO] Nenhum cliente foi recebido pelo corpo da requisição");
            }

            return modelMapper.mapper().map(ordemEntity, OrdemDTO.class);

        }

        throw new InvalidRequestException("A validação da requisição falhou. Verificar corpo da requisição.");

    }

    public List<OrdemDTO> buscaTodos() {
        if (!repository.findAll().isEmpty()) return repository.findAll().stream()
                .map(x -> modelMapper.mapper().map(x, OrdemDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhuma ordem salva na base de dados.");
    }

    public List<OrdemDTO> buscaPorPaginacao(Pageable paginacao) {
        if (!repository.findAll(paginacao).isEmpty()) return repository.findAll(paginacao)
                .getContent().stream().map(x -> modelMapper.mapper().map(x, OrdemDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhuma ordem cadastrada na página indicada");
    }

    public List<OrdemDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {

        List<OrdemEntity> ordens = repository.buscaPorRangeDeDataCadastro(
                (LocalDate.parse(dataInicio)).atTime(0, 0),
                (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

        if (!ordens.isEmpty())
            return ordens.stream().map(x -> modelMapper.mapper().map(x, OrdemDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhuma ordem cadastrada no range de datas indicado");

    }

    public OrdemDTO buscaPorId(Long id) {
        if (repository.findById(id).isPresent()) {
            return modelMapper.mapper().map(repository.findById(id).get(), OrdemDTO.class);
        }
        throw new ObjectNotFoundException("Não existe nenhuma ordem cadastrada no banco de dados com o id " + id);
    }

    public OrdemDTO atualizaPorId(Long id, OrdemDTO ordem) {

        Optional<OrdemEntity> ordemOptional = repository.findById(id);

        if (ordemOptional.isPresent()) {

            OrdemEntity ordemAtualizada = ordemOptional.get();

            if (validation.validaCorpoRequisicao(ordem)) {

                ordemAtualizada.setVeiculo(ordem.getVeiculo());
                ordemAtualizada.setLoja(ordem.getLoja());
                ordemAtualizada.setFormaPagamento(ordem.getFormaPagamento());
                ordemAtualizada.setEmiteNfe(ordem.getEmiteNfe());
                ordemAtualizada.setRetirada(modelMapper.mapper().map(ordem.getRetirada(), RetiradaEntity.class));
                ordemAtualizada.setEntradas(ordem.getEntradas().stream().map(x -> modelMapper.mapper().map(x, EntradaOrdemEntity.class)).collect(Collectors.toList()));

                return modelMapper.mapper().map(repository.save(ordemAtualizada), OrdemDTO.class);
            }
            throw new InvalidRequestException("Requisição inválida.");
        }
        throw new ObjectNotFoundException("Nenhuma ordem foi encontrada com o id " + id);
    }

    public Boolean deletaPorId(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.delete(repository.findById(id).get());
            return true;
        }
        throw new ObjectNotFoundException("Nenhuma ordem foi encontrada com o id " + id);
    }

}
