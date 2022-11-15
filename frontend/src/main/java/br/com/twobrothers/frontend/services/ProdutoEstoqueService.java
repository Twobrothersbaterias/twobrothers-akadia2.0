package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.ProdutoEstoqueDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroProdutoDTO;
import br.com.twobrothers.frontend.models.entities.DespesaEntity;
import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.models.enums.TipoProdutoEnum;
import br.com.twobrothers.frontend.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.DespesaCrudService;
import br.com.twobrothers.frontend.repositories.services.ProdutoEstoqueCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.frontend.utils.ConversorDeDados;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static br.com.twobrothers.frontend.utils.StringConstants.TIPO_FILTRO;
import static br.com.twobrothers.frontend.utils.StringConstants.URI_ESTOQUE;

@Slf4j
@Service
public class ProdutoEstoqueService {

    @Autowired
    ProdutoEstoqueCrudService crudService;

    @Autowired
    DespesaCrudService despesaCrudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProdutoEstoqueRepository produtoEstoqueRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    public String encaminhaParaCriacaoDoCrudService(ProdutoEstoqueDTO produto) {
        try {
            crudService.criaNovo(produto);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String encaminhaParaUpdateDoCrudService(ProdutoEstoqueDTO produto) {
        try {
            crudService.atualizaPorId(produto);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<ProdutoEstoqueEntity> filtroProdutos(
            Pageable pageable,
            String descricao,
            String tipo,
            String dataInicio,
            String dataFim,
            String mes,
            String ano,
            String fornecedor) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorDescricao(pageable, descricao);
        else if (tipo != null) return crudService.buscaPorTipo(pageable, TipoProdutoEnum.valueOf(tipo));
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeData(pageable, dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodo(pageable, Integer.parseInt(mes), Integer.parseInt(ano));
        else if (fornecedor != null) return crudService.buscaPorFornecedor(pageable, fornecedor);
        else return produtoEstoqueRepository.findAll(pageable).toList();
    }

    public List<ProdutoEstoqueEntity> filtroProdutosSemPaginacao(
            String descricao,
            String tipo,
            String dataInicio,
            String dataFim,
            String mes,
            String ano,
            String fornecedor) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorDescricaoSemPaginacao(descricao);
        else if (tipo != null) return crudService.buscaPorTipoSemPaginacao(TipoProdutoEnum.valueOf(tipo));
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(Integer.parseInt(mes), Integer.parseInt(ano));
        else if (fornecedor != null) return crudService.buscaPorFornecedorSemPaginacao(fornecedor);
        else return produtoEstoqueRepository.findAll();
    }

    public Double calculaValorBruto(List<ProdutoEstoqueEntity> produtos) {
        Double valor = 0.0;
        for (ProdutoEstoqueEntity produto: produtos) {
            if (produto.getTipoProduto().equals(TipoProdutoEnum.BATERIA)) valor += produto.getCustoTotal();
        }
        return valor;
    }

    public int calculaQtdBaterias(List<ProdutoEstoqueEntity> produtos) {
        int qtde = 0;
        for (ProdutoEstoqueEntity produto: produtos) {
            if (produto.getTipoProduto().equals(TipoProdutoEnum.BATERIA)) qtde += produto.getQuantidade();
        }
        return qtde;
    }

    public int calculaQtdSucatas(List<ProdutoEstoqueEntity> produtos) {
        int qtde = 0;
        for (ProdutoEstoqueEntity produto: produtos) {
            if (produto.getTipoProduto().equals(TipoProdutoEnum.SUCATA)) qtde += produto.getQuantidade();
        }
        return qtde;
    }

    public List<Integer> calculaQuantidadePaginas(List<ProdutoEstoqueEntity> produtos, Pageable pageable) {
        List<Integer> paginas = new ArrayList<>();
        Integer contadorPaginas = 0;
        int contador = 0;
        paginas.add(contadorPaginas);
        for (int i = 0; i < produtos.size(); i++) {

            if (contador == pageable.getPageSize()) {
                contadorPaginas++;
                paginas.add(contadorPaginas);
                contador = 0;
            }
            contador++;

        }
        return paginas;
    }

    public List<ProdutoEstoqueEntity> buscaTodos() {
        return produtoEstoqueRepository.buscaTodasBaterias(Sort.by("sigla"), TipoProdutoEnum.BATERIA);
    }

    public ProdutoEstoqueDTO buscaPorSigla(String sigla) {
        if (produtoEstoqueRepository.buscaPorSigla(sigla).isPresent())
            return modelMapper.mapper().map(produtoEstoqueRepository.buscaPorSigla(sigla).get(), ProdutoEstoqueDTO.class);
        throw new ObjectNotFoundException("O produto inserido na ordem não foi encontrado");
    }

    public String constroiUriFiltro(FiltroProdutoDTO filtroProdutoDTO) {

        URI_ESTOQUE = "estoque?";

        if (filtroProdutoDTO.getDescricao() != null && !filtroProdutoDTO.getDescricao().equals("")) {
            URI_ESTOQUE += "descricao=" + filtroProdutoDTO.getDescricao();
        }

        if (filtroProdutoDTO.getDataInicio() != null && !filtroProdutoDTO.getDataInicio().equals("")) {
            URI_ESTOQUE += "inicio=" + filtroProdutoDTO.getDataInicio();
        }

        if (filtroProdutoDTO.getDataFim() != null && !filtroProdutoDTO.getDataFim().equals("")) {
            URI_ESTOQUE += "&fim=" + filtroProdutoDTO.getDataFim();
        }

        if (filtroProdutoDTO.getPeriodoMes() != null && !filtroProdutoDTO.getPeriodoMes().equals("")) {
            URI_ESTOQUE += "mes=" + filtroProdutoDTO.getPeriodoMes();
        }

        if (filtroProdutoDTO.getPeriodoAno() != null && !filtroProdutoDTO.getPeriodoAno().equals("")) {
            URI_ESTOQUE += "&ano=" + filtroProdutoDTO.getPeriodoAno();
        }

        if (filtroProdutoDTO.getTipo() != null && !filtroProdutoDTO.getTipo().equals("")) {
            URI_ESTOQUE += "tipo=" + filtroProdutoDTO.getTipo();
        }

        return URI_ESTOQUE;
    }

    public ModelMap modelMapBuilder(ModelMap modelMap, Pageable pageable, HttpServletRequest req,
                                    String descricao, String inicio, String fim, String mes, String ano,
                                    String tipo, String fornecedor) {

        log.info("[STARTING] Iniciando construção do modelMap...");
        HashMap<String, Object> atributos = new HashMap<>();

        log.info("[PROGRESS] Verificando se existem itens em atraso ou que vencem hoje...");
        List<DespesaEntity> despesasAtrasadas = despesaCrudService.buscaDespesasAtrasadas();
        List<DespesaEntity> despesasHoje = despesaCrudService.buscaDespesasComVencimentoParaHoje();

        log.info("[PROGRESS] Iniciando setagem da lista de objetos encontrados com o filtro atual...");
        List<ProdutoEstoqueEntity> produtosSemPaginacao = filtroProdutosSemPaginacao(
                descricao,
                tipo,
                inicio,
                fim,
                mes,
                ano,
                fornecedor);

        List<ProdutoEstoqueEntity> produtosPaginados = filtroProdutos(
                pageable,
                descricao,
                tipo,
                inicio,
                fim,
                mes,
                ano,
                fornecedor);

        log.info("[PROGRESS] Setando valores dos informativos...");
        atributos.put("bruto",
                ConversorDeDados.converteValorDoubleParaValorMonetario(calculaValorBruto(produtosSemPaginacao)));

        atributos.put("qtdeBaterias", calculaQtdBaterias(produtosSemPaginacao));

        atributos.put("qtdeSucatas", calculaQtdSucatas(produtosSemPaginacao));

        log.info("[PROGRESS] Setando valores dos filtros...");
        atributos.put("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        atributos.put("despesasAtrasadas", despesasAtrasadas);
        atributos.put("despesasHoje", despesasHoje);
        atributos.put("descricao", descricao);
        atributos.put("tipo", tipo);
        atributos.put("dataInicio", inicio);
        atributos.put("dataFim", fim);
        atributos.put("mes", mes);
        atributos.put("ano", ano);
        atributos.put("fornecedor", fornecedor);
        atributos.put("produtos", produtosPaginados);

        log.info("[PROGRESS] Inicializando setagem de tipo de filtro...");
        atributos.put(TIPO_FILTRO, "todos");
        if (inicio != null && fim != null) atributos.replace(TIPO_FILTRO, "data");
        if (mes != null && ano != null) atributos.replace(TIPO_FILTRO, "periodo");
        if (descricao != null) atributos.replace(TIPO_FILTRO, "descricao");
        if (tipo != null) atributos.replace(TIPO_FILTRO, "tipo");
        if (fornecedor != null) atributos.replace(TIPO_FILTRO, "fornecedor");

        log.info("[PROGRESS] Setando atributos da página...");
        atributos.put("pagina", pageable.getPageNumber());
        atributos.put("paginas", calculaQuantidadePaginas(produtosSemPaginacao, pageable));
        atributos.put("totalItens", produtosSemPaginacao.size());

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/estoque?" + req.getQueryString();

        atributos.put("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        atributos.put("baseUrl", baseUrl);
        atributos.put("queryString", req.getQueryString());
        atributos.put("completeUrl", completeUrl);

        modelMap.addAllAttributes(atributos);

        log.info("[SUCCESS] ModelMap construído com sucesso. Retornando para o controller...");
        return modelMap;
    }

}
