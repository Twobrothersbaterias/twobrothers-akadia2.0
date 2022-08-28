package br.com.twobrothers.msvendas.repositories.services;

import br.com.twobrothers.msvendas.config.ModelMapperConfig;
import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.EntradaOrdemDTO;
import br.com.twobrothers.msvendas.models.dto.OrdemDTO;
import br.com.twobrothers.msvendas.models.entities.*;
import br.com.twobrothers.msvendas.models.enums.StatusRetiradaEnum;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.*;
import br.com.twobrothers.msvendas.services.GerenciamentoEstoqueService;
import br.com.twobrothers.msvendas.validations.ClienteValidation;
import br.com.twobrothers.msvendas.validations.EnderecoValidation;
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
    EnderecoValidation enderecoValidation = new EnderecoValidation();
    ProdutoEstoqueValidation produtoEstoqueValidation = new ProdutoEstoqueValidation();

    String PERSISTENCIA_EM_CASCATA_STRING =
            "[PROGRESS] Persistindo endereço na base de dados com cliente -> ordem acoplado...";

    public OrdemDTO criaNovo(OrdemDTO ordem) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação...");

        log.info("[PROGRESS] Validando objeto do tipo OrdemDTO enviado via JSON...");
        validation.validaCorpoRequisicao(ordem);

        OrdemEntity ordemEntity = modelMapper.mapper().map(ordem, OrdemEntity.class);
        ordemEntity.setEntradas(new ArrayList<>());
        ordemEntity.setCliente(null);
        ordemEntity.setRetirada(null);

        ClienteEntity clienteEntity = new ClienteEntity();

        log.info("[PROGRESS] Verificando se cliente passado pelo corpo da requisição é nulo...");
        if (ordem.getCliente() != null) {

            clienteValidation.validaCorpoRequisicao(ordem.getCliente(), clienteRepository, ValidationType.UPDATE);

            log.warn("[INFO] Cliente recebido pelo corpo da requisição");

            Optional<ClienteEntity> clienteEntityOptional =
                    clienteRepository.buscaPorCpfCnpj(ordem.getCliente().getCpfCnpj());

            log.info("[PROGRESS] Verificando se cliente passado existe na base de dados...");
            if (clienteEntityOptional.isPresent()) {
                log.info("[INFO] Cliente encontrado: {}", clienteEntityOptional.get().getNomeCompleto());
                clienteEntity = clienteEntityOptional.get();

            } else {
                log.info("[INFO] Cliente não encontrado");
                clienteEntity = modelMapper.mapper().map(ordem.getCliente(), ClienteEntity.class);
                clienteEntity.setDataCadastro(LocalDateTime.now());
            }

            if (ordem.getCliente().getEndereco() != null) {
                enderecoValidation.validaCorpoRequisicao(ordem.getCliente().getEndereco());
                clienteEntity.setEndereco(modelMapper.mapper().map(ordem.getCliente().getEndereco(), EnderecoEntity.class));
                clienteEntity.getEndereco().setDataCadastro(LocalDateTime.now());
            } else {
                clienteEntity.setEndereco(null);
            }

        }

        for (EntradaOrdemDTO entradaOrdemDTO : ordem.getEntradas()) {

            Optional<ProdutoEstoqueEntity> produtoEstoqueEntityOptional =
                    produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla());

            if (produtoEstoqueEntityOptional.isPresent()) {

                gerenciamentoEstoqueService.distribuiParaTrocaOuProduto(entradaOrdemDTO);

                ordemEntity = repository.save(ordemEntity);

                log.info("[PROGRESS] Persistindo as entradas no banco de dados SEM o produto e SEM a ordem...");
                entradaOrdemDTO.setProduto(null);
                EntradaOrdemEntity entradaOrdemEntity =
                        entradaOrdemRepository.save(modelMapper.mapper().map(entradaOrdemDTO, EntradaOrdemEntity.class));

                ProdutoEstoqueEntity produtoEstoqueEntity = produtoEstoqueEntityOptional.get();

                produtoEstoqueEntity.addEntrada(entradaOrdemEntity);

                produtoEstoqueRepository.save(produtoEstoqueEntity);

                log.info("[PROGRESS] Adicionando entrada à ordem e ordem à entrada...");
                ordemEntity.addEntrada(entradaOrdemEntity);

            } else {
                throw new ObjectNotFoundException("O produto buscado não existe na base de dados");
            }

        }

        ordemEntity.setDataCadastro(LocalDateTime.now());

        if (ordem.getRetirada().getStatusRetirada().equals(StatusRetiradaEnum.LOJA_FISICA)) {
            ordem.getRetirada().setDataRetirada(LocalDateTime.now());
        }

        ordemEntity.setRetirada(modelMapper.mapper().map(ordem.getRetirada(), RetiradaEntity.class));

        if (ordem.getCliente() != null) {
            clienteEntity.addOrdem(ordemEntity);
            clienteRepository.save(clienteEntity);
        } else {
            ordemEntity =
                    repository.save(modelMapper.mapper().map(ordemEntity, OrdemEntity.class));
        }

        return modelMapper.mapper().map(ordemEntity, OrdemDTO.class);

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
