package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.AbastecimentoDTO;
import br.com.twobrothers.frontend.models.entities.AbastecimentoEntity;
import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.repositories.AbastecimentoRepository;
import br.com.twobrothers.frontend.repositories.FornecedorRepository;
import br.com.twobrothers.frontend.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import br.com.twobrothers.frontend.validations.AbastecimentoValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
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
public class AbastecimentoCrudService {

    @Autowired
    AbastecimentoRepository repository;

    @Autowired
    ProdutoEstoqueRepository produtoEstoqueRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    AbastecimentoValidation validation = new AbastecimentoValidation();

    public AbastecimentoDTO criaNovo(AbastecimentoDTO abastecimento, Long idProduto, Long idFornecedor) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação...");

        log.info("[PROGRESS] Setando o usuário responsável no abastecimento: {}", UsuarioUtils.loggedUser(usuarioRepository));
        abastecimento.setUsuarioResponsavel(UsuarioUtils.loggedUser(usuarioRepository).getUsername());

        log.info("[PROGRESS] Validando objeto do tipo AbastecimentoDTO enviado via JSON...");
        validation.validaCorpoRequisicao(abastecimento);

        log.info("[PROGRESS] Verificando se produto com o id {} existe na base de dados...", idProduto);
        if (!produtoEstoqueRepository.existsById(idProduto)) {
            throw new InvalidRequestException("Não existe nenhum produto com o id " + idProduto);
        }

        log.info("[PROGRESS] Verificando se fornecedor com o id {} existe na base de dados...", idFornecedor);
        if (idFornecedor != null && !fornecedorRepository.existsById(idFornecedor)) {
            throw new InvalidRequestException("Não existe nenhum fornecedor com o id " + idFornecedor);
        }

        log.info("[PROGRESS] Setando data de cadastro no abastecimento: {}", LocalDateTime.now());
        abastecimento.setDataCadastro(LocalDate.now().toString());

        log.info("[PROGRESS] Setando o custo unitário do abastecimento...");
        abastecimento.setCustoUnitario(abastecimento.getCustoTotal() / abastecimento.getQuantidade());

        log.info("[PROGRESS] Persistindo o abastecimento no banco de dados SEM o produto e SEM o fornecedor...");
        AbastecimentoEntity abastecimentoEntity =
                repository.save(modelMapper.mapper().map(abastecimento, AbastecimentoEntity.class));

        ProdutoEstoqueEntity produtoEstoque = produtoEstoqueRepository.findById(idProduto).get();

        log.info("[PROGRESS] Atualizando o custo total do produto em estoque...");
        produtoEstoque.setCustoTotal(produtoEstoque.getCustoTotal() + abastecimento.getCustoTotal());

        log.info("[PROGRESS] Aumentando a quantidade de produtos em estoque com a quantidade passada no abastecimento...");
        produtoEstoque.setQuantidade(produtoEstoque.getQuantidade() + abastecimento.getQuantidade());

        log.info("[PROGRESS] Atualizando o custo unitário do produto em estoque...");
        produtoEstoque.setCustoUnitario(produtoEstoque.getCustoTotal() / produtoEstoque.getQuantidade());

        log.info("[PROGRESS] Adicionando abastecimento ao produto e produto ao abastecimento...");
        produtoEstoque.addAbastecimento(abastecimentoEntity);

        log.info("[PROGRESS] Persistindo produto no banco de dados com o novo abastecimento na lista...");
        produtoEstoqueRepository.save(produtoEstoque);

        if (idFornecedor != null) {
            log.info("[PROGRESS] Adicionando abastecimento ao fornecedor e fornecedor ao abastecimento...");
            FornecedorEntity fornecedor = fornecedorRepository.findById(idFornecedor).get();
            fornecedor.addAbastecimento(abastecimentoEntity);

            log.info("[PROGRESS] Persistindo fornecedor no banco de dados com o novo abastecimento na lista...");
            fornecedorRepository.save(fornecedor);
        }

        log.info("[PROGRESS] Persistindo novo abastecimento na base de dados com relacionamento bidirecional finalizado...");
        log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
        return modelMapper.mapper().map(repository.save(abastecimentoEntity), AbastecimentoDTO.class);

    }

    public List<AbastecimentoEntity> buscaPorRangeDeDataPaginado(Pageable pageable, String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de abastecimento por range de data...");
        validation.validaRangeData(dataInicio, dataFim);
        return repository.buscaPorRangeDeDataPaginado(pageable, dataInicio, dataFim);
    }

    public List<AbastecimentoEntity> buscaPorPeriodoPaginado(Pageable pageable, Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de abastecimento por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorRangeDeDataPaginado(pageable, dataInicio.toString(), dataFim.toString());
    }

    public List<AbastecimentoEntity> buscaHojePaginado(Pageable pageable) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todos as abastecimentos realizados hoje...");
        LocalDate hoje = LocalDate.now();
        return repository.buscaHojePaginado(pageable, hoje.toString());
    }

    public List<AbastecimentoEntity> buscaPorFornecedorIdPaginado(Pageable pageable, String fornecedorId) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de abastecimento por fornecedorId: {}", fornecedorId);
        return repository.buscaPorFornecedorIdPaginado(pageable, Long.parseLong(fornecedorId));
    }

    public List<AbastecimentoEntity> buscaPorFornecedorPaginado(Pageable pageable, String fornecedor) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de abastecimento por fornecedor...");
        return repository.buscaPorFornecedorPaginado(pageable, fornecedor);
    }

    public List<AbastecimentoEntity> buscaPorProdutoPaginado(Pageable pageable, String produto) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de abastecimento por produto...");
        return repository.buscaPorProdutoPaginado(pageable, produto);
    }

    public List<AbastecimentoEntity> buscaPorRangeDeDataSemPaginacao(String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de abastecimento por range de data...");
        validation.validaRangeData(dataInicio, dataFim);
        return repository.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
    }

    public List<AbastecimentoEntity> buscaPorPeriodoSemPaginacao(Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de abastecimento por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return repository.buscaPorRangeDeDataSemPaginacao(dataInicio.toString(), dataFim.toString());
    }

    public List<AbastecimentoEntity> buscaHojeSemPaginacao() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todos as abastecimentos realizados hoje...");
        LocalDate hoje = LocalDate.now();
        return repository.buscaHojeSemPaginacao(hoje.toString());
    }

    public List<AbastecimentoEntity> buscaPorFornecedorIdSemPaginacao(String fornecedorId) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de abastecimento por fornecedorId...");
        return repository.buscaPorFornecedorIdSemPaginacao(Long.parseLong(fornecedorId));
    }

    public List<AbastecimentoEntity> buscaPorFornecedorSemPaginacao(String fornecedor) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de abastecimento por fornecedor...");
        return repository.buscaPorFornecedorSemPaginacao(fornecedor);
    }
    public List<AbastecimentoEntity> buscaPorProdutoSemPaginacao(String produto) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de abastecimento por produto...");
        return repository.buscaPorProdutoSemPaginacao(produto);
    }

}
