package br.com.twobrothers.msvendas.repositories.services;

import br.com.twobrothers.msvendas.config.ModelMapperConfig;
import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.ProdutoEstoqueDTO;
import br.com.twobrothers.msvendas.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.msvendas.validations.ProdutoEstoqueValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.twobrothers.msvendas.utils.StringConstants.BARRA_DE_LOG;
import static br.com.twobrothers.msvendas.utils.StringConstants.REQUISICAO_FINALIZADA_COM_SUCESSO;

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
public class ProdutoEstoqueService {

    @Autowired
    ProdutoEstoqueRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    ProdutoEstoqueValidation validation = new ProdutoEstoqueValidation();

    public ProdutoEstoqueDTO criaNovo(ProdutoEstoqueDTO produto) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação...");

        log.info("[PROGRESS] Iniciando validação do objeto ProdutoDTO...");
        validation.validaCorpoRequisicao(produto, repository, ValidationType.CREATE);

        log.info("[PROGRESS] Setando data de cadastro do produto: {}...", LocalDateTime.now());
        produto.setDataCadastro(LocalDateTime.now());
        log.info("[PROGRESS] Setando quantidade do novo produto cadastrado para 0...");
        produto.setQuantidade(0);

        if (produto.getQuantidadeMinima() == null) produto.setQuantidadeMinima(0);

        log.info("[PROGRESS] Salvando o novo produto na base de dados...");
        ProdutoEstoqueEntity produtoEstoqueEntity = repository.save(modelMapper.mapper()
                .map(produto, ProdutoEstoqueEntity.class));

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
        return modelMapper.mapper().map(produtoEstoqueEntity, ProdutoEstoqueDTO.class);
    }

    public List<ProdutoEstoqueDTO> buscaTodos() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca por todos os produtos...");
        if (!repository.findAll().isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.findAll().stream()
                    .map(x -> modelMapper.mapper().map(x, ProdutoEstoqueDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhum produto salvo na base de dados");
        throw new ObjectNotFoundException("Não existe nenhum produto salvo na base de dados.");
    }

    public List<ProdutoEstoqueDTO> buscaPorPaginacao(Pageable paginacao) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de produto por paginação...");
        if (!repository.findAll(paginacao).isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.findAll(paginacao)
                    .getContent().stream().map(x -> modelMapper.mapper().map(x, ProdutoEstoqueDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhum produto cadastrado na página indicada");
        throw new ObjectNotFoundException("Não existe nenhum produto cadastrado na página indicada");
    }

    public List<ProdutoEstoqueDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de produto por range de data de cadastro...");

        List<ProdutoEstoqueEntity> produtos = repository.buscaPorRangeDeDataCadastro(
                (LocalDate.parse(dataInicio)).atTime(0, 0),
                (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

        if (!produtos.isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return produtos.stream().map(x -> modelMapper.mapper().map(x, ProdutoEstoqueDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhum produto cadastrado no range de datas indicado");
        throw new ObjectNotFoundException("Não existe nenhum produto cadastrado no range de datas indicado");

    }

    public ProdutoEstoqueDTO buscaPorSigla(String sigla) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca por sigla...");
        if (repository.buscaPorSigla(sigla).isPresent()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return modelMapper.mapper().map(repository.buscaPorSigla(sigla).get(), ProdutoEstoqueDTO.class);
        }
        log.error("[ERRROR] Nenhum produto foi encontrado através do atributo sigla enviado");
        throw new ObjectNotFoundException("Nenhum produto foi encontrado através do atributo sigla enviado.");
    }

    public ProdutoEstoqueDTO buscaPorId(Long id) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca por id...");
        if (repository.findById(id).isPresent()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return modelMapper.mapper().map(repository.findById(id).get(), ProdutoEstoqueDTO.class);
        }
        log.error("[ERROR] Não existe nenhum produto cadastrado no banco de dados com o id {}", id);
        throw new ObjectNotFoundException("Não existe nenhum produto cadastrado no banco de dados com o id " + id);
    }

    public ProdutoEstoqueDTO atualizaPorId(Long id, ProdutoEstoqueDTO produto) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de atualização de produto por id...");

        Optional<ProdutoEstoqueEntity> produtoOptional = repository.findById(id);

        if (produtoOptional.isEmpty()) {
            log.error("[ERROR] Não existe nenhum produto cadastrado com o id {}", id);
            throw new ObjectNotFoundException("Não existe nenhum produto cadastrado com o id " + id);
        }

        log.info("[PROGRESS] Atribuindo produto encontrado pelo id ao valor da variável produtoAtualizado...");
        ProdutoEstoqueEntity produtoAtualizado = produtoOptional.get();

        if (repository.buscaPorSigla(produto.getSigla()).isPresent()
                && !Objects.equals(repository.buscaPorSigla(produto.getSigla()).get().getId(), produtoOptional.get().getId())) {
            throw new InvalidRequestException("O produto com a sigla " + produto.getSigla() + " já existe na base de dados");
        }

        log.info("[PROGRESS] Iniciando método de validação do objeto produto...");
        validation.validaCorpoRequisicao(produto, repository, ValidationType.UPDATE);

        log.info("[PROGRESS] Inicializando setagem e atualização dos atributos da variável produtoAtualizado com base" +
                "no objeto produto recebido pela requisição...");
        produtoAtualizado.setSigla(produto.getSigla());
        produtoAtualizado.setEspecificacao(produto.getEspecificacao());
        produtoAtualizado.setMarcaBateria(produto.getMarcaBateria());
        produtoAtualizado.setTipoProduto(produto.getTipoProduto());

        if (produto.getQuantidadeMinima() == null) produtoAtualizado.setQuantidadeMinima(0);
        else produtoAtualizado.setQuantidadeMinima(produto.getQuantidadeMinima());

        log.info("[PROGRESS] Persistindo produto atualizado na base de dados...");
        ProdutoEstoqueEntity produtoEstoqueEntity = repository.save(produtoAtualizado);

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
        return modelMapper.mapper().map(produtoEstoqueEntity, ProdutoEstoqueDTO.class);

    }

    public Boolean deletaPorId(Long id) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de remoção de produto por id...");

        Optional<ProdutoEstoqueEntity> produtoOptional = repository.findById(id);
        if (produtoOptional.isPresent()) {
            log.info("[PROGRESS] Inicializando remoção do produto de id {}...", id);
            repository.deleteById(id);
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return true;
        }
        log.error("[ERROR] Nenhum produto foi encontrado com o id {}", id);
        throw new ObjectNotFoundException("Não existe nenhum produto cadastrado com o id " + id);
    }

}
