package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.models.dto.OrdemDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroOrdemDTO;
import br.com.twobrothers.frontend.models.entities.EntradaOrdemEntity;
import br.com.twobrothers.frontend.models.entities.OrdemEntity;
import br.com.twobrothers.frontend.repositories.ClienteRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.OrdemCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.utils.ConversorDeDados;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static br.com.twobrothers.frontend.utils.StringConstants.TIPO_FILTRO;
import static br.com.twobrothers.frontend.utils.StringConstants.URI_ORDENS;

@Slf4j
@Service
public class OrdemService {

    @Autowired
    OrdemCrudService crudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public String encaminhaParaCriacaoDoCrudService(OrdemDTO ordem) {
        try {
            crudService.criaNovo(ordem);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String encaminhaParaUpdateDoCrudService(OrdemDTO ordem) {
        try {
            crudService.atualizaPorId(ordem.getId(), ordem);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<OrdemEntity> filtroOrdens(
            Pageable pageable,
            String dataInicio,
            String dataFim,
            String mes,
            String ano,
            String produto,
            String bairro,
            String cliente) throws InvalidRequestException {
        if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeData(pageable, dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodo(pageable, Integer.parseInt(mes), Integer.parseInt(ano));
        else if (produto != null) return crudService.buscaPorProdutoPaginado(pageable, produto);
        else if (bairro != null) return crudService.buscaPorBairroPaginado(pageable, bairro);
        else if (cliente != null) return crudService.buscaPorClientePaginado(pageable, cliente);
        else return crudService.buscaHojePaginado(pageable);
    }

    public List<OrdemEntity> filtroOrdensSemPaginacao(
            String dataInicio,
            String dataFim,
            String mes,
            String ano,
            String produto,
            String bairro,
            String cliente) throws InvalidRequestException {
        if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(Integer.parseInt(mes), Integer.parseInt(ano));
        else if (produto != null) return crudService.buscaPorProdutoSemPaginacao(produto);
        else if (bairro != null) return crudService.buscaPorBairroSemPaginacao(bairro);
        else if (cliente != null) return crudService.buscaPorClienteSemPaginacao(cliente);
        else return crudService.buscaHojeSemPaginacao();
    }

    public List<Integer> calculaQuantidadePaginas(List<OrdemEntity> ordens, Pageable pageable) {
        List<Integer> paginas = new ArrayList<>();
        Integer contadorPaginas = 0;
        int contador = 0;
        paginas.add(contadorPaginas);
        for (int i = 0; i < ordens.size(); i++) {

            if (contador == pageable.getPageSize()) {
                contadorPaginas++;
                paginas.add(contadorPaginas);
                contador = 0;
            }
            contador++;

        }
        return paginas;
    }

    public Integer calculaQuantidadeVendida(List<OrdemEntity> ordens) {

        Integer quantidade = 0;
        if (ordens != null && !ordens.isEmpty()) {
            for (OrdemEntity ordem : ordens) {
                for(EntradaOrdemEntity entrada : ordem.getEntradas()) {
                    quantidade += entrada.getQuantidade();
                }
            }
        }
        return quantidade;
    }

    public Double calculaBrutoVendido(List<OrdemEntity> ordens) {

        Double valor = 0.0;
        if (ordens != null && !ordens.isEmpty()) {
            for (OrdemEntity ordem : ordens) {
                for(EntradaOrdemEntity entrada : ordem.getEntradas()) {
                    valor += entrada.getValor();
                }
            }
        }
        return valor;
    }

    public Double calculaCustoVenda(List<OrdemEntity> ordens) {

        double custo = 0.0;
        if (ordens != null && !ordens.isEmpty()) {
            for (OrdemEntity ordem : ordens) {
                for(EntradaOrdemEntity entrada : ordem.getEntradas()) {
                    if (entrada.getProduto() != null) {
                        custo += (entrada.getProduto().getCustoUnitario() * entrada.getQuantidade());
                    }
                }
            }
        }
        return custo;
    }

    public String constroiUriFiltro(FiltroOrdemDTO filtroOrdemDTO) {

        URI_ORDENS = "vendas?";

        if (filtroOrdemDTO.getDataInicio() != null && !filtroOrdemDTO.getDataInicio().equals("")) {
            URI_ORDENS += "inicio=" + filtroOrdemDTO.getDataInicio();
        }

        if (filtroOrdemDTO.getDataFim() != null && !filtroOrdemDTO.getDataFim().equals("")) {
            URI_ORDENS += "&fim=" + filtroOrdemDTO.getDataFim();
        }

        if (filtroOrdemDTO.getPeriodoMes() != null && !filtroOrdemDTO.getPeriodoMes().equals("")) {
            URI_ORDENS += "mes=" + filtroOrdemDTO.getPeriodoMes();
        }

        if (filtroOrdemDTO.getPeriodoAno() != null && !filtroOrdemDTO.getPeriodoAno().equals("")) {
            URI_ORDENS += "&ano=" + filtroOrdemDTO.getPeriodoAno();
        }

        if (filtroOrdemDTO.getProduto() != null && !filtroOrdemDTO.getProduto().equals("")) {
            URI_ORDENS += "produto=" + filtroOrdemDTO.getProduto();
        }

        if (filtroOrdemDTO.getBairro() != null && !filtroOrdemDTO.getBairro().equals("")) {
            URI_ORDENS += "bairro=" + filtroOrdemDTO.getBairro();
        }

        return URI_ORDENS;
    }

    public ModelMap modelMapBuilder(ModelMap modelMap, Pageable pageable, HttpServletRequest req,
                                    String inicio, String fim,
                                    String mes, String ano, String produto, String bairro, String cliente) {

        log.info("[STARTING] Iniciando construção do modelMap...");
        HashMap<String, Object> atributos = new HashMap<>();

        log.info("[PROGRESS] Setando lista de itens encontrados...");

        List<OrdemEntity> ordensSemPaginacao = filtroOrdensSemPaginacao(
                inicio,
                fim,
                mes,
                ano,
                produto,
                bairro,
                cliente);

        List<OrdemEntity> ordensPaginadas = filtroOrdens(
                pageable,
                inicio,
                fim,
                mes,
                ano,
                produto,
                bairro,
                cliente);

        log.info("[PROGRESS] Setando valores dos informativos...");
        atributos.put("quantidadeVendida", calculaQuantidadeVendida(ordensSemPaginacao));

        atributos.put("bruto",
                ConversorDeDados.converteValorDoubleParaValorMonetario(calculaBrutoVendido(ordensSemPaginacao)));

        atributos.put("custo",
                ConversorDeDados.converteValorDoubleParaValorMonetario(calculaCustoVenda(ordensSemPaginacao)));

        atributos.put("liquido",
                ConversorDeDados.converteValorDoubleParaValorMonetario(
                        (calculaBrutoVendido(ordensSemPaginacao) - calculaCustoVenda(ordensSemPaginacao))));

        log.info("[PROGRESS] Setando valores dos filtros...");
        atributos.put("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        atributos.put("dataInicio", inicio);
        atributos.put("dataFim", fim);
        atributos.put("mes", mes);
        atributos.put("ano", ano);
        atributos.put("produto", produto);
        atributos.put("bairro", bairro);
        atributos.put("cliente", cliente);
        atributos.put("ordens", ordensPaginadas);

        log.info("[PROGRESS] Inicializando setagem de tipo de filtro...");
        atributos.put(TIPO_FILTRO, "hoje");
        if (inicio != null && fim != null) atributos.replace(TIPO_FILTRO, "data");
        if (mes != null && ano != null) atributos.replace(TIPO_FILTRO, "periodo");
        if (produto != null) atributos.replace(TIPO_FILTRO, "produto");
        if (bairro != null) atributos.replace(TIPO_FILTRO, "bairro");
        if (cliente != null) atributos.replace(TIPO_FILTRO, "cliente");
        if (cliente != null) atributos.put("clienteNome", clienteRepository.findById(Long.valueOf(cliente)).get().getNomeCompleto());

        log.info("[PROGRESS] Setando atributos da página...");
        atributos.put("pagina", pageable.getPageNumber());
        atributos.put("paginas", calculaQuantidadePaginas(ordensSemPaginacao, pageable));
        atributos.put("totalItens", ordensSemPaginacao.size());

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/vendas?" + req.getQueryString();

        atributos.put("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        atributos.put("baseUrl", baseUrl);
        atributos.put("queryString", req.getQueryString());
        atributos.put("completeUrl", completeUrl);

        modelMap.addAllAttributes(atributos);

        log.info("[SUCCESS] ModelMap construído com sucesso. Retornando para o controller...");
        return modelMap;
    }

}
