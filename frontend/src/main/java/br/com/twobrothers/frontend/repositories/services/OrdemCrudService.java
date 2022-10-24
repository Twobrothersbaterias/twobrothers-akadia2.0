package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.EntradaOrdemDTO;
import br.com.twobrothers.frontend.models.dto.OrdemDTO;
import br.com.twobrothers.frontend.models.entities.*;
import br.com.twobrothers.frontend.models.enums.StatusRetiradaEnum;
import br.com.twobrothers.frontend.models.enums.ValidationType;
import br.com.twobrothers.frontend.repositories.ClienteRepository;
import br.com.twobrothers.frontend.repositories.EntradaOrdemRepository;
import br.com.twobrothers.frontend.repositories.OrdemRepository;
import br.com.twobrothers.frontend.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.frontend.services.GerenciamentoEstoqueService;
import br.com.twobrothers.frontend.services.ProdutoEstoqueService;
import br.com.twobrothers.frontend.services.enums.OperacaoEstoque;
import br.com.twobrothers.frontend.utils.ConversorDeDados;
import br.com.twobrothers.frontend.utils.TrataAtributosVazios;
import br.com.twobrothers.frontend.validations.*;
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

import static br.com.twobrothers.frontend.utils.StringConstants.BARRA_DE_LOG;
import static br.com.twobrothers.frontend.utils.StringConstants.REQUISICAO_FINALIZADA_COM_SUCESSO;
import static br.com.twobrothers.frontend.utils.TrataAtributosVazios.verificaSeClienteNulo;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Slf4j
@Service
public class OrdemCrudService {

    @Autowired
    OrdemRepository repository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProdutoEstoqueRepository produtoEstoqueRepository;

    @Autowired
    EntradaOrdemRepository entradaOrdemRepository;

    @Autowired
    GerenciamentoEstoqueService gerenciamentoEstoqueService;

    @Autowired
    ProdutoEstoqueService produtoEstoqueService;

    @Autowired
    ModelMapperConfig modelMapper;

    OrdemValidation validation = new OrdemValidation();
    ClienteValidation clienteValidation = new ClienteValidation();
    EnderecoValidation enderecoValidation = new EnderecoValidation();
    PagamentoValidation pagamentoValidation = new PagamentoValidation();
    RetiradaValidation retiradaValidation = new RetiradaValidation();
    ProdutoEstoqueValidation produtoEstoqueValidation = new ProdutoEstoqueValidation();

    public OrdemDTO criaNovo(OrdemDTO ordem) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação...");

        log.info("[PROGRESS] Realizando conversão das strings contendo as entradas e os pagamentos para objetos...");
        ConversorDeDados.cargaEntradasPagamentos(produtoEstoqueService, ordem);

        log.info("[PROGRESS] Validando objeto do tipo OrdemDTO enviado via JSON...");
        validation.validaCorpoRequisicao(ordem);

        log.info("[PROGRESS] Atribuindo os valores passados via JSON à variável ordemEntity...");
        OrdemEntity ordemEntity = modelMapper.mapper().map(ordem, OrdemEntity.class);

        log.info("[PROGRESS] Setando as entradas, o cliente e a retirada da ordem como nulos...");
        ordemEntity.setEntradas(new ArrayList<>());
        ordemEntity.setCliente(null);
        ordemEntity.setRetirada(null);

        ClienteEntity clienteEntity = new ClienteEntity();

        TrataAtributosVazios.trataAtributosVaziosDoObjetoCliente(ordem.getCliente());

        log.info("[PROGRESS] Verificando se cliente passado pelo corpo da requisição é nulo...");
        if (!verificaSeClienteNulo(ordem.getCliente())) {

            log.info("[PROGRESS] Iniciando validação do objeto ClienteDTO recebido pela requisição...");
            clienteValidation.validaCorpoRequisicao(ordem.getCliente(), clienteRepository, ValidationType.UPDATE);

            log.warn("[INFO] Cliente recebido pelo corpo da requisição");

            Optional<ClienteEntity> clienteEntityOptional =
                    clienteRepository.buscaPorCpfCnpj(ordem.getCliente().getCpfCnpj());

            log.info("[PROGRESS] Verificando se cliente passado existe na base de dados...");
            if (clienteEntityOptional.isPresent()) {
                log.warn("[INFO] Cliente encontrado: {}", clienteEntityOptional.get().getNomeCompleto());

                log.info("[PROGRESS] Setando valor do cliente encontrado na variável clienteEntity...");
                clienteEntity = clienteEntityOptional.get();

                log.info("[PROGRESS] Atualizando atributos do cliente com base no corpo da requisição...");
                clienteEntity.setNomeCompleto(ordem.getCliente().getNomeCompleto());
                clienteEntity.setEmail(ordem.getCliente().getEmail());
                clienteEntity.setTelefone(ordem.getCliente().getTelefone());
                clienteEntity.setDataNascimento(ordem.getCliente().getDataNascimento());
                clienteEntity.setCpfCnpj(ordem.getCliente().getCpfCnpj());

            } else {
                log.info("[INFO] Cliente não encontrado");

                log.info("[PROGRESS] Setando valor do cliente recebido na requisição através da ordem na variável clienteEntity...");
                clienteEntity = modelMapper.mapper().map(ordem.getCliente(), ClienteEntity.class);
                clienteEntity.setDataCadastro(LocalDateTime.now().toString());
            }

            log.info("[PROGRESS] Verificando se o endereço do cliente foi passado na requisição...");
            if (ordem.getCliente().getEndereco() != null) {
                log.warn("[INFO] Endereço detectado: {}", ordem.getCliente().getEndereco());

                log.info("[PROGRESS] Iniciando validação do objeto EnderecoDTO recebido pela requisição...");
                enderecoValidation.validaCorpoRequisicao(ordem.getCliente().getEndereco());

                log.info("[PROGRESS] Setando endereço no clienteEntity...");
                clienteEntity.setEndereco(modelMapper.mapper().map(ordem.getCliente().getEndereco(), EnderecoEntity.class));

                log.info("[PROGRESS] Setando data de cadastro no endereço: {}...", LocalDateTime.now());
                clienteEntity.getEndereco().setDataCadastro(LocalDateTime.now().toString());
            } else {
                log.warn("[INFO] Nenhum endereço foi encontrado dentro do objeto cliente recebido via requisição");
                clienteEntity.setEndereco(null);
            }

        }

        log.info("[PROGRESS] Iniciando validação em massa da relação ENTRADA -> PRODUTO...");
        gerenciamentoEstoqueService.validacoesEmMassa(ordem.getEntradas());

        log.info("[PROGRESS] Iniciando validação do objeto RetiradaDTO recebido acoplado no objeto OrdemDTO...");
        retiradaValidation.validaCorpoRequisicao(ordem.getRetirada());

        log.info("[PROGRESS] Iniciando validação em massa da relação ORDEM -> PAGAMENTO...");
        pagamentoValidation.validaCorpoRequisicaoEmMassa(ordem.getPagamentos());

        log.info("[PROGRESS] Iniciando iteração por todas as entradas recebidas na requisição...");
        for (EntradaOrdemDTO entradaOrdemDTO : ordem.getEntradas()) {

            log.info("[PROGRESS] Iniciando validação do objeto ProdutoDTO de sigla {} recebido pela requisição através" +
                    "do objeto OrdemDTO -> EntradaDTO...", entradaOrdemDTO.getProduto().getSigla());
            produtoEstoqueValidation.validaCorpoRequisicao
                    (entradaOrdemDTO.getProduto(), produtoEstoqueRepository, ValidationType.UPDATE);

            log.info("[PROGRESS] Iniciando verificação se objeto Produto DTO {} existe na base de dados...",
                    entradaOrdemDTO.getProduto().getSigla());
            Optional<ProdutoEstoqueEntity> produtoEstoqueEntityOptional =
                    produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla());

            if (produtoEstoqueEntityOptional.isEmpty()) {
                log.error("[ERROR] O produto informado de sigla {} não existe na base de dados",
                        entradaOrdemDTO.getProduto().getSigla());
                throw new ObjectNotFoundException("O produto buscado não existe na base de dados");
            }

            log.info("[PROGRESS] Iniciando acionamento do serviço de gerenciamento e alteração de estoque...");
            gerenciamentoEstoqueService.distribuiParaOperacaoCorreta(entradaOrdemDTO, OperacaoEstoque.CRIACAO);

            log.info("[PROGRESS] Persistindo ordem no banco de dados e atribuindo seu retorno à variável ordemEntity...");
            ordemEntity = repository.save(ordemEntity);

            log.info("[PROGRESS] Persistindo as entradas no banco de dados SEM o produto e SEM a ordem...");
            entradaOrdemDTO.setProduto(null);
            entradaOrdemDTO.setOrdem(null); //ADIÇÃO DE TESTE
            EntradaOrdemEntity entradaOrdemEntity =
                    entradaOrdemRepository.save(modelMapper.mapper().map(entradaOrdemDTO, EntradaOrdemEntity.class));

            log.info("[PROGRESS] Adicionando o produto à ordem e a ordem ao produto...");
            ProdutoEstoqueEntity produtoEstoqueEntity = produtoEstoqueEntityOptional.get();
            produtoEstoqueEntity.addEntrada(entradaOrdemEntity);

            log.info("[PROGRESS] Atualizando o produto na base de dados com a entrada acoplada...");
            produtoEstoqueRepository.save(produtoEstoqueEntity);

            log.info("[PROGRESS] Adicionando entrada à ordem e ordem à entrada...");
            ordemEntity.addEntrada(entradaOrdemEntity);

        }

        log.warn("[INFO] Iteração finalizada com sucesso");

        log.info("[PROGRESS] Setando data de cadastro da ordem: {}...", LocalDateTime.now());
        ordemEntity.setDataCadastro(LocalDateTime.now());

        if (ordem.getRetirada().getStatusRetirada().equals(StatusRetiradaEnum.LOJA_FISICA)) {
            log.info("[PROGRESS] Setando a data de retirada da ordem: {}...", LocalDate.now().toString());
            ordem.getRetirada().setDataRetirada(LocalDate.now().toString());
        }

        log.info("[PROGRESS] Setando a retirada recebida pela requisição ao objeto ordemEntity...");
        ordemEntity.setRetirada(modelMapper.mapper().map(ordem.getRetirada(), RetiradaEntity.class));

        if (ordem.getCliente() != null) {
            log.info("[PROGRESS] Adicionando cliente à ordem e ordem ao cliente...");
            clienteEntity.addOrdem(ordemEntity);
            log.info("[PROGRESS] Realizando persistência em cascata: ClienteEntity -> OrdemEntity -> ...");
            clienteRepository.save(clienteEntity);
        } else {
            log.info("[PROGRESS] Realizando persistência da ordem na base de dados...");
            ordemEntity =
                    repository.save(modelMapper.mapper().map(ordemEntity, OrdemEntity.class));
        }

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
        return modelMapper.mapper().map(ordemEntity, OrdemDTO.class);

    }

    public List<OrdemDTO> buscaTodos() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de buscar todas as ordens...");
        if (!repository.findAll().isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.findAll().stream()
                    .map(x -> modelMapper.mapper().map(x, OrdemDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhuma ordem salva na base de dados...");
        throw new ObjectNotFoundException("Não existe nenhuma ordem salva na base de dados.");
    }

    public List<OrdemDTO> buscaPorPaginacao(Pageable paginacao) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca por paginação...");
        if (!repository.findAll(paginacao).isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.findAll(paginacao)
                    .getContent().stream().map(x -> modelMapper.mapper().map(x, OrdemDTO.class)).collect(Collectors.toList());
        }
        throw new ObjectNotFoundException("Não existe nenhuma ordem cadastrada na página indicada");
    }

    public List<OrdemDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca por ordens por range de data de cadastro...");

        List<OrdemEntity> ordens = repository.buscaPorRangeDeDataCadastro(
                (LocalDate.parse(dataInicio)).atTime(0, 0),
                (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

        if (!ordens.isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return ordens.stream().map(x -> modelMapper.mapper().map(x, OrdemDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhuma ordem cadastrada no range de datas indicado");
        throw new ObjectNotFoundException("Não existe nenhuma ordem cadastrada no range de datas indicado");

    }

    public OrdemDTO buscaPorId(Long id) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de ordem por id......");
        if (repository.findById(id).isPresent()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return modelMapper.mapper().map(repository.findById(id).get(), OrdemDTO.class);
        }
        log.error("[ERROR] Não existe nenhuma ordem cadastrada no banco de dados com o id {}", id);
        throw new ObjectNotFoundException("Não existe nenhuma ordem cadastrada no banco de dados com o id " + id);
    }

    public OrdemDTO atualizaPorId(Long id, OrdemDTO ordem) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de atualização de ordem...");
        deletaPorId(id);
        return criaNovo(ordem);
    }

    public Boolean deletaPorId(Long id) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de remoção de ordem por id...");

        Optional<OrdemEntity> ordemEntityOptional = repository.findById(id);

        if (ordemEntityOptional.isEmpty()) {
            log.error("[ERROR] Nenhuma ordem foi encontrado com o id {}", id);
            throw new ObjectNotFoundException("Nenhuma ordem foi encontrada com o id " + id);
        }

        OrdemEntity ordemEntity = ordemEntityOptional.get();

        log.info("[PROGRESS] Iniciando iteração pela lista de entradas da ordem encontrada...");
        for (EntradaOrdemEntity entradaOrdemEntity : ordemEntity.getEntradas()) {

            log.info("[PROGRESS] Iniciando acesso à classe de gerenciamento de estoque, que irá realizar o processo " +
                    "de remoção das relações ENTRADA -> PRODUTO...");
            gerenciamentoEstoqueService.distribuiParaOperacaoCorreta(
                    modelMapper.mapper().map(entradaOrdemEntity, EntradaOrdemDTO.class), OperacaoEstoque.REMOCAO);

            log.info("[PROGRESS] Removendo o produto da entrada e a entrada do produto...");
            ProdutoEstoqueEntity produtoEstoqueEntity = entradaOrdemEntity.getProduto();
            produtoEstoqueEntity.removeEntrada(entradaOrdemEntity);

            log.info("[PROGRESS] Persistindo produto atualizado na base de dados...");
            produtoEstoqueRepository.save(produtoEstoqueEntity);

        }

        if (ordemEntity.getCliente() != null) {
            log.info("[PROGRESS] Removendo cliente da ordem e ordem do cliente...");
            ClienteEntity clienteEntity = ordemEntity.getCliente();
            clienteEntity.removeOrdem(ordemEntity);

            log.info("[PROGRESS] Persistindo cliente atualizado na base de dados...");
            clienteRepository.save(clienteEntity);
        }

        log.info("[PROGRESS] Removendo ordem da base de dados...");
        repository.delete(ordemEntity);

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
        return true;

    }

}
