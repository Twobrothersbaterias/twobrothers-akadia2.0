package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.ProdutoEstoqueDTO;
import br.com.twobrothers.frontend.models.entities.PatrimonioEntity;
import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.models.enums.TipoPatrimonioEnum;
import br.com.twobrothers.frontend.models.enums.TipoProdutoEnum;
import br.com.twobrothers.frontend.models.enums.ValidationType;
import br.com.twobrothers.frontend.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import br.com.twobrothers.frontend.validations.ProdutoEstoqueValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.com.twobrothers.frontend.utils.StringConstants.BARRA_DE_LOG;
import static br.com.twobrothers.frontend.utils.StringConstants.REQUISICAO_FINALIZADA_COM_SUCESSO;

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
public class ProdutoEstoqueCrudService {

    @Autowired
    ProdutoEstoqueRepository repository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    ProdutoEstoqueValidation validation = new ProdutoEstoqueValidation();

    public ProdutoEstoqueDTO criaNovo(ProdutoEstoqueDTO produto) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação...");

        log.info("[PROGRESS] Setando data de cadastro do produto: {}...", LocalDateTime.now());
        produto.setDataCadastro(LocalDate.now().toString());

        log.info("[PROGRESS] Setando como zero o custo total e unitário do produto...");
        produto.setCustoTotal(0.0);
        produto.setCustoUnitario(0.0);

        log.info("[PROGRESS] Setando o usuário responsável pelo cadastro do produto: {}...", UsuarioUtils.loggedUser(usuarioRepository).getUsername());
        produto.setUsuarioResponsavel(UsuarioUtils.loggedUser(usuarioRepository).getUsername());

        log.info("[PROGRESS] Iniciando validação do objeto ProdutoDTO...");
        validation.validaCorpoRequisicao(produto, repository, ValidationType.CREATE);

        log.info("[PROGRESS] Setando quantidade do novo produto cadastrado para 0...");
        produto.setQuantidade(0);

        if (produto.getQuantidadeMinima() == null) produto.setQuantidadeMinima(0);

        log.info("[PROGRESS] Salvando o novo produto na base de dados...");
        ProdutoEstoqueEntity produtoEstoqueEntity = repository.save(modelMapper.mapper()
                .map(produto, ProdutoEstoqueEntity.class));

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
        return modelMapper.mapper().map(produtoEstoqueEntity, ProdutoEstoqueDTO.class);
    }

    public List<ProdutoEstoqueEntity> buscaPorRangeDeData(Pageable pageable, String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de produto por range de data...");
        validation.validaRangeData(dataInicio, dataFim);
        return repository.buscaPorRangeDeData(pageable, dataInicio, dataFim);
    }

    public List<ProdutoEstoqueEntity> buscaPorPeriodo(Pageable pageable, Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de produto por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorPeriodo(pageable, dataInicio.toString(), dataFim.toString());
    }

    public List<ProdutoEstoqueEntity> buscaPorDescricao(Pageable pageable, String descricao) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de produto por descrição...");
        return repository.buscaPorDescricao(pageable, descricao);
    }

    public List<ProdutoEstoqueEntity> buscaPorTipo(Pageable pageable, TipoProdutoEnum tipo) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de produtos por tipo...");
        return repository.buscaPorTipo(pageable, tipo);
    }

    public List<ProdutoEstoqueEntity> buscaPorRangeDeDataSemPaginacao(String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de produto por range de data...");
        return repository.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
    }

    public List<ProdutoEstoqueEntity> buscaPorPeriodoSemPaginacao(Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de produto por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorPeriodoSemPaginacao(dataInicio.toString(), dataFim.toString());
    }

    public List<ProdutoEstoqueEntity> buscaPorDescricaoSemPaginacao(String descricao) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de produto por descrição...");
        return repository.buscaPorDescricaoSemPaginacao(descricao);
    }

    public List<ProdutoEstoqueEntity> buscaPorTipoSemPaginacao(TipoProdutoEnum tipo) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de produto por tipo...");
        return repository.buscaPorTipoSemPaginacao(tipo);
    }

    public ProdutoEstoqueDTO atualizaPorId(ProdutoEstoqueDTO produto) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de atualização de produto por id...");

        Optional<ProdutoEstoqueEntity> produtoOptional = repository.findById(produto.getId());

        if (produtoOptional.isEmpty()) {
            log.error("[ERROR] Não existe nenhum produto cadastrado com o id {}", produto.getId());
            throw new ObjectNotFoundException("Não existe nenhum produto cadastrado com o id " + produto.getId());
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

    public void deletaPorId(Long id) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de remoção de produto por id...");
        Optional<ProdutoEstoqueEntity> produtoOptional = repository.findById(id);
        if (produtoOptional.isPresent()) {
            log.info("[PROGRESS] Inicializando remoção do produto de id {}...", id);
            repository.deleteById(id);
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
        } else {
            log.error("[ERROR] Nenhum produto foi encontrado com o id {}", id);
            throw new ObjectNotFoundException("Não existe nenhum produto cadastrado com o id " + id);
        }
    }

}
