package br.com.twobrothers.msvendas.repositories.services;

import br.com.twobrothers.msvendas.config.ModelMapperConfig;
import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.OrdemDTO;
import br.com.twobrothers.msvendas.models.dto.EntradaOrdemDTO;
import br.com.twobrothers.msvendas.models.entities.ClienteEntity;
import br.com.twobrothers.msvendas.models.entities.OrdemEntity;
import br.com.twobrothers.msvendas.models.enums.StatusRetiradaEnum;
import br.com.twobrothers.msvendas.models.enums.TipoOrdemEnum;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.ClienteRepository;
import br.com.twobrothers.msvendas.repositories.OrdemRepository;
import br.com.twobrothers.msvendas.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.msvendas.services.GerenciamentoEstoqueService;
import br.com.twobrothers.msvendas.validations.ClienteValidation;
import br.com.twobrothers.msvendas.validations.OrdemValidation;
import br.com.twobrothers.msvendas.validations.ProdutoEstoqueValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.twobrothers.msvendas.utils.StringConstants.BARRA_DE_LOG;
import static br.com.twobrothers.msvendas.utils.StringConstants.REQUISICAO_FINALIZADA_COM_SUCESSO;

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
    GerenciamentoEstoqueService gerenciamentoEstoqueService;

    @Autowired
    ModelMapperConfig modelMapper;

    OrdemValidation validation = new OrdemValidation();
    ClienteValidation clienteValidation = new ClienteValidation();
    ProdutoEstoqueValidation produtoEstoqueValidation = new ProdutoEstoqueValidation();

    public OrdemDTO criaNovo(OrdemDTO ordem) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação...");

        log.info("[PROGRESS] Inicializando validação do objeto ordem recebido via JSON");
        if (validation.validaCorpoRequisicao(ordem, repository, ValidationType.CREATE)) {

            log.info("[PROGRESS] Setando timestamp de cadastro: {}", LocalDateTime.now());
            ordem.setDataCadastro(LocalDateTime.now());

            if (ordem.getRetirada().getStatusRetirada().equals(StatusRetiradaEnum.LOJA_FISICA)) {
                log.info("[PROGRESS] Setando timestamp da retirada: {}", LocalDateTime.now());
                ordem.getRetirada().setDataRetirada(LocalDateTime.now());
            }

            log.info("[PROGRESS] Percorrendo as entradas passadas na ordem...");
            for (EntradaOrdemDTO entradaOrdemDTO : ordem.getEntradas()) {
                log.info("[PROGRESS] Setando a ordem à entrada");
                entradaOrdemDTO.setOrdem(ordem);
                if (entradaOrdemDTO.getTipoOrdem() != TipoOrdemEnum.PADRAO_SERVICO)
                    gerenciamentoEstoqueService.distribuiParaTrocaOuProduto(entradaOrdemDTO);
            }

            log.info("[PROGRESS] Adicionando a retirada à ordem e a ordem à retirada...");
            ordem.getRetirada().addOrdem(ordem);

            log.info("[PROGRESS] Verificando se objeto cliente passado via JSON existe na base de dados...");
            Optional<ClienteEntity> clienteOptional =
                    clienteRepository.buscaPorAtributos(ordem.getCliente().getCpfCnpj(), ordem.getCliente().getNomeCompleto());

            if (clienteOptional.isPresent()) {

                log.warn("[INFO] Cliente encontrado: {}", clienteOptional.get().getNomeCompleto());
                ClienteEntity clienteEncontrado = clienteOptional.get();

                log.info("[PROGRESS] Adicionando ordem no cliente e cliente na ordem...");
                clienteEncontrado.addOrdem(modelMapper.mapper().map(ordem, OrdemEntity.class));

                log.info("[PROGRESS] Persistindo cliente com objeto ordem acoplado no banco de dados...");
                clienteRepository.save(clienteEncontrado);

                log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
                return ordem;

            } else {

                log.warn("[INFO] Cliente não encontrado.");

                log.info("[PROGRESS] Iniciando criação dos objetos através de persistência em cascata na base de dados " +
                        "[Ordem -> ALL]");

                log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
                return modelMapper.mapper().map(
                        repository.save(modelMapper.mapper().map(ordem, OrdemEntity.class)), OrdemDTO.class);
            }

        }

        throw new InvalidRequestException("A validação da requisição falhou. Verificar corpo da requisição.");
    }

    public List<OrdemDTO> buscaTodos() {
        if (!repository.findAll().isEmpty()) return repository.findAll().stream()
                .map(x -> modelMapper.mapper().map(x, OrdemDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhuma ordem salva na base de dados.");
    }
//
//    public List<OrdemDTO> buscaPorPaginacao(Pageable paginacao) {
//        if (!repository.findAll(paginacao).isEmpty()) return repository.findAll(paginacao)
//                .getContent().stream().map(x -> modelMapper.mapper().map(x, OrdemDTO.class)).collect(Collectors.toList());
//        throw new ObjectNotFoundException("Não existe nenhuma ordem cadastrada na página indicada");
//    }
//
//    public List<OrdemDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {
//
//        List<OrdemEntity> ordens = repository.buscaPorRangeDeDataCadastro(
//                (LocalDate.parse(dataInicio)).atTime(0, 0),
//                (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));
//
//        if (!ordens.isEmpty())
//            return ordens.stream().map(x -> modelMapper.mapper().map(x, OrdemDTO.class)).collect(Collectors.toList());
//        throw new ObjectNotFoundException("Não existe nenhum produto cadastrado no range de datas indicado");
//
//    }
//
//    public OrdemDTO buscaPorId(Long id) {
//        if (repository.findById(id).isPresent()) {
//            return modelMapper.mapper().map(repository.findById(id).get(), OrdemDTO.class);
//        }
//        throw new ObjectNotFoundException("Não existe nenhuma ordem cadastrada no banco de dados com o id " + id);
//    }
//
//    public OrdemDTO atualizaPorId(Long id, OrdemDTO ordem) {
//
//        Optional<OrdemEntity> ordemOptional = repository.findById(id);
//
//        if (ordemOptional.isPresent()) {
//
//            OrdemEntity ordemAtualizada = ordemOptional.get();
//
//            if (validation.validaCorpoRequisicao(ordem)) {
//                // SETAGEM DE ATRIBUTOS
//                return modelMapper.mapper().map(repository.save(ordemAtualizada), OrdemDTO.class);
//            }
//            throw new InvalidRequestException("Requisição inválida.");
//        }
//        throw new ObjectNotFoundException("Nenhuma ordem foi encontrada com o id " + id);
//    }
//
//    public Boolean deletaPorId(Long id) {
//        if (repository.findById(id).isPresent()) {
//            repository.delete(repository.findById(id).get());
//            return true;
//        }
//        throw new ObjectNotFoundException("Nenhuma ordem foi encontrada com o id " + id);
//    }

}
