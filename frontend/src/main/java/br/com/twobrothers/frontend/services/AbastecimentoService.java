package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.AbastecimentoDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroAbastecimentoDTO;
import br.com.twobrothers.frontend.models.entities.AbastecimentoEntity;
import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
import br.com.twobrothers.frontend.models.entities.PatrimonioEntity;
import br.com.twobrothers.frontend.models.enums.FormaPagamentoEnum;
import br.com.twobrothers.frontend.repositories.AbastecimentoRepository;
import br.com.twobrothers.frontend.repositories.FornecedorRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.AbastecimentoCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.utils.ConversorDeDados;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static br.com.twobrothers.frontend.utils.ConversorDeDados.converteValorDoubleParaValorMonetario;
import static br.com.twobrothers.frontend.utils.StringConstants.TIPO_FILTRO;
import static br.com.twobrothers.frontend.utils.StringConstants.URI_PRECO;

@Slf4j
@Service
public class AbastecimentoService {

    @Autowired
    AbastecimentoCrudService crudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ProdutoEstoqueService produtoEstoqueService;

    @Autowired
    FornecedorService fornecedorService;

    public String encaminhaParaCriacaoDoCrudService(AbastecimentoDTO abastecimento) {

        Long idFornecedor = null;

        if (abastecimento.getFornecedor() != null) {
            idFornecedor = abastecimento.getFornecedor().getId();
            abastecimento.setFornecedor(null);
        }

        try {
            if (abastecimento.getProduto() != null) {
                crudService.criaNovo(abastecimento, abastecimento.getProduto().getId(), idFornecedor);
                return null;
            }
            else {
                return "É preciso inserir um produto para cadastrar uma compra";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String encaminhaParaUpdateDoCrudService(AbastecimentoDTO abastecimento) {
        try {
            crudService.atualizaPorId(abastecimento);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<AbastecimentoEntity> filtroAbastecimentos(Pageable pageable,
                                                          String dataInicio,
                                                          String dataFim,
                                                          String mes,
                                                          String ano,
                                                          String fornecedorId,
                                                          String fornecedor,
                                                          String produto,
                                                          String meio) throws InvalidRequestException {
        if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataPaginado(pageable, dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoPaginado(pageable, Integer.parseInt(mes), Integer.parseInt(ano));
        else if(fornecedorId != null) return crudService.buscaPorFornecedorIdPaginado(pageable, fornecedorId);
        else if (fornecedor != null) return crudService.buscaPorFornecedorPaginado(pageable, fornecedor);
        else if (produto != null) return crudService.buscaPorProdutoPaginado(pageable, produto);
        else if (meio != null) return crudService.buscaPorFormaDePagamentoPaginado(pageable, meio);
        else return crudService.buscaHojePaginado(pageable);
    }

    public List<AbastecimentoEntity> filtroAbastecimentosSemPaginacao(
            String dataInicio,
            String dataFim,
            String mes,
            String ano,
            String fornecedorId,
            String fornecedor,
            String produto,
            String meio) throws InvalidRequestException {
        if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(Integer.parseInt(mes), Integer.parseInt(ano));
        else if (fornecedorId != null) return crudService.buscaPorFornecedorIdSemPaginacao(fornecedorId);
        else if (fornecedor != null) return crudService.buscaPorFornecedorSemPaginacao(fornecedor);
        else if (produto != null) return crudService.buscaPorProdutoSemPaginacao(produto);
        else if (meio != null) return crudService.buscaPorFormaDePagamentoSemPaginacao(meio);
        else return crudService.buscaHojeSemPaginacao();
    }

    public List<Integer> calculaQuantidadePaginas(List<AbastecimentoEntity> abastecimentos, Pageable pageable) {
        List<Integer> paginas = new ArrayList<>();
        Integer contadorPaginas = 0;
        Integer contador = 0;
        paginas.add(contadorPaginas);
        for (int i = 0; i < abastecimentos.size(); i++) {

            if (contador == pageable.getPageSize()) {
                contadorPaginas++;
                paginas.add(contadorPaginas);
                contador = 0;
            }
            contador++;

        }
        return paginas;
    }

    public Double calculaFormaPagamento(List<AbastecimentoEntity> abastecimentos, FormaPagamentoEnum formaPagamento) {
        Double valor = 0.0;
        for (AbastecimentoEntity abastecimento : abastecimentos) {
            if (abastecimento.getFormaPagamento().equals(formaPagamento)) valor += abastecimento.getCustoTotal();
        }
        return valor;
    }

    public String constroiUriFiltro(FiltroAbastecimentoDTO filtro) {

        URI_PRECO = "compras?";

        if (filtro.getDataInicio() != null && !filtro.getDataInicio().equals("")) {
            URI_PRECO += "inicio=" + filtro.getDataInicio();
        }

        if (filtro.getDataFim() != null && !filtro.getDataFim().equals("")) {
            URI_PRECO += "&fim=" + filtro.getDataFim();
        }

        if (filtro.getPeriodoMes() != null && !filtro.getPeriodoMes().equals("")) {
            URI_PRECO += "mes=" + filtro.getPeriodoMes();
        }

        if (filtro.getPeriodoAno() != null && !filtro.getPeriodoAno().equals("")) {
            URI_PRECO += "&ano=" + filtro.getPeriodoAno();
        }

        if (filtro.getProduto() != null && !filtro.getProduto().equals("")) {
            URI_PRECO += "produto=" + filtro.getProduto();
        }

        if (filtro.getFornecedor() != null && !filtro.getFornecedor().equals("")) {
            URI_PRECO += "fornecedor=" + filtro.getFornecedor();
        }

        return URI_PRECO;
    }

    public List<AbastecimentoEntity> filtraFormaDePagamentoSemPaginacao(List<AbastecimentoEntity> abastecimentos,
                                                                        String formaPagamento) {

        FormaPagamentoEnum formaPagamentoEnum = null;
        List<AbastecimentoEntity> abastecimentoEntities = new ArrayList<>();

        try{
            formaPagamentoEnum = FormaPagamentoEnum.valueOf(formaPagamento);
        }
        catch (Exception e) {
            throw new InvalidRequestException("Parâmetro meio inserido na URL é inválido");
        }

        for (AbastecimentoEntity abastecimento: abastecimentos) {
            if (abastecimento.getFormaPagamento().equals(formaPagamentoEnum)) abastecimentoEntities.add(abastecimento);
        }

        return abastecimentoEntities;
    }

    public List<AbastecimentoEntity> filtraFormaDePagamentoPaginada(Pageable pageableParam,
                                                                    List<AbastecimentoEntity> abastecimentos,
                                                                    String formaPagamento) {

        FormaPagamentoEnum formaPagamentoEnum = null;
        List<AbastecimentoEntity> abastecimentoEntities = new ArrayList<>();

        try{
            formaPagamentoEnum = FormaPagamentoEnum.valueOf(formaPagamento);
        }
        catch (Exception e) {
            throw new InvalidRequestException("Parâmetro meio inserido na URL é inválido");
        }

        for (AbastecimentoEntity abastecimento: abastecimentos) {
            if (abastecimento.getFormaPagamento().equals(formaPagamentoEnum))
                abastecimentoEntities.add(abastecimento);
        }

        Pageable pageable = PageRequest.of(
                pageableParam.getPageNumber(),
                pageableParam.getPageSize(),
                pageableParam.getSort());

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), abastecimentoEntities.size());
        final Page<AbastecimentoEntity> page =
                new PageImpl<>(abastecimentoEntities.subList(start, end), pageable, abastecimentoEntities.size());

        return page.toList();
    }

    public ModelMap modelMapBuilder(ModelMap modelMap, Pageable pageable, HttpServletRequest req,
                                    String inicio, String fim, String mes, String ano, String fornecedorId,
                                    String fornecedor, String produto, String meio) {

        log.info("[STARTING] Iniciando construção do modelMap...");
        HashMap<String, Object> atributos = new HashMap<>();

        log.info("[PROGRESS] Setando lista de itens encontrados...");
        List<AbastecimentoEntity> abastecimentosSemPaginacao = filtroAbastecimentosSemPaginacao(
                inicio,
                fim,
                mes,
                ano,
                fornecedorId,
                fornecedor,
                produto,
                meio);

        List<AbastecimentoEntity> abastecimentosPaginados = filtroAbastecimentos(
                pageable,
                inicio,
                fim,
                mes,
                ano,
                fornecedorId,
                fornecedor,
                produto,
                meio);

        log.info("[PROGRESS] Setando valores dos informativos...");
        atributos.put("especie", ConversorDeDados.converteValorDoubleParaValorMonetario(calculaFormaPagamento(abastecimentosSemPaginacao, FormaPagamentoEnum.DINHEIRO)));
        atributos.put("credito", ConversorDeDados.converteValorDoubleParaValorMonetario(calculaFormaPagamento(abastecimentosSemPaginacao, FormaPagamentoEnum.CREDITO)));
        atributos.put("debito", ConversorDeDados.converteValorDoubleParaValorMonetario(calculaFormaPagamento(abastecimentosSemPaginacao, FormaPagamentoEnum.DEBITO)));
        atributos.put("cheque", ConversorDeDados.converteValorDoubleParaValorMonetario(calculaFormaPagamento(abastecimentosSemPaginacao, FormaPagamentoEnum.CHEQUE)));
        atributos.put("pix", ConversorDeDados.converteValorDoubleParaValorMonetario(calculaFormaPagamento(abastecimentosSemPaginacao, FormaPagamentoEnum.PIX)));
        atributos.put("boleto", ConversorDeDados.converteValorDoubleParaValorMonetario(calculaFormaPagamento(abastecimentosSemPaginacao, FormaPagamentoEnum.BOLETO)));

        log.info("[PROGRESS] Inicializando setagem de tipo de filtro...");
        atributos.put(TIPO_FILTRO, "hoje");
        atributos.put("meioAtivo", null);

        FornecedorEntity fornecedorEncontradoPorId = new FornecedorEntity();

        if (inicio != null && fim != null) atributos.replace(TIPO_FILTRO, "data");
        if (mes != null && ano != null) atributos.replace(TIPO_FILTRO, "periodo");
        if (fornecedorId != null) {
            fornecedorEncontradoPorId = (fornecedorRepository.findById(Long.parseLong(fornecedorId)).get());
            atributos.replace(TIPO_FILTRO, "fornecedorId");
        }
        if (fornecedor != null) atributos.replace(TIPO_FILTRO, "fornecedor");
        if (produto != null) atributos.replace(TIPO_FILTRO, "produto");
        if (meio != null) atributos.replace("meioAtivo", meio);

        if (meio != null) {
            abastecimentosSemPaginacao = filtraFormaDePagamentoSemPaginacao(abastecimentosSemPaginacao, meio);
            abastecimentosPaginados = filtraFormaDePagamentoPaginada(pageable, abastecimentosSemPaginacao, meio);
        }

        log.info("[PROGRESS] Setando valores dos filtros...");
        atributos.put("inicio", inicio);
        atributos.put("fim", fim);
        atributos.put("mes", mes);
        atributos.put("ano", ano);
        atributos.put("fornecedorId", fornecedorId);
        atributos.put("fornecedor", fornecedor);
        atributos.put("produto", produto);
        atributos.put("meio", meio);
        atributos.put("produtos", produtoEstoqueService.buscaTodos());
        atributos.put("fornecedores", fornecedorService.buscaTodos());
        atributos.put("abastecimentos", abastecimentosPaginados);

        log.info("[PROGRESS] Setando atributos da página...");
        atributos.put("pagina", pageable.getPageNumber());
        atributos.put("paginas", calculaQuantidadePaginas(abastecimentosSemPaginacao, pageable));
        atributos.put("totalItens", abastecimentosSemPaginacao.size());

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/compras?" + req.getQueryString();

        atributos.put("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        atributos.put("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        atributos.put("baseUrl", baseUrl);
        atributos.put("queryString", req.getQueryString());
        atributos.put("completeUrl", completeUrl);

        modelMap.addAllAttributes(atributos);

        log.info("[SUCCESS] ModelMap construído com sucesso. Retornando para o controller...");
        return modelMap;
    }


}
