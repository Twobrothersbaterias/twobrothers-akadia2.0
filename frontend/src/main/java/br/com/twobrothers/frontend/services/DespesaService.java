package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroDespesaDTO;
import br.com.twobrothers.frontend.models.entities.DespesaEntity;
import br.com.twobrothers.frontend.models.enums.StatusDespesaEnum;
import br.com.twobrothers.frontend.models.enums.TipoDespesaEnum;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.DespesaCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
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

import static br.com.twobrothers.frontend.utils.ConversorDeDados.converteValorDoubleParaValorMonetario;
import static br.com.twobrothers.frontend.utils.StringConstants.TIPO_FILTRO;

@Slf4j
@Service
public class DespesaService {

    @Autowired
    DespesaCrudService crudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    public String encaminhaParaCriacaoDoCrudService(DespesaDTO despesa) {
        try {
            crudService.criaNovaDespesa(despesa);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String encaminhaParaUpdateDoCrudService(DespesaDTO despesa) {
        try {
            crudService.atualizaPorId(despesa);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<DespesaEntity> filtroDespesas(Pageable pageable,
                                              String descricao,
                                              String tipo,
                                              String dataInicio,
                                              String dataFim,
                                              String mes,
                                              String ano) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorDescricao(pageable, descricao);
        else if (tipo != null) return crudService.buscaPorTipo(pageable, TipoDespesaEnum.valueOf(tipo));
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeData(pageable, dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodo(pageable, Integer.parseInt(mes), Integer.parseInt(ano));
        else return crudService.buscaHoje(pageable);
    }

    public List<DespesaEntity> filtroDespesasSemPaginacao(
            String descricao,
            String tipo,
            String dataInicio,
            String dataFim,
            String mes,
            String ano) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorDescricaoSemPaginacao(descricao);
        else if (tipo != null) return crudService.buscaPorTipoSemPaginacao(TipoDespesaEnum.valueOf(tipo));
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(Integer.parseInt(mes), Integer.parseInt(ano));
        else return crudService.buscaHojeSemPaginacao();
    }

    public Double calculaValorPago(List<DespesaEntity> despesas) {

        Double valor = 0.0;
        if (despesas != null && !despesas.isEmpty()) {
            for (DespesaEntity despesa : despesas) {
                if (despesa.getStatusDespesa() == StatusDespesaEnum.PAGO) valor += despesa.getValor();
            }
        }
        return valor;
    }

    public Double calculaValorPendente(List<DespesaEntity> despesas) {

        Double valor = 0.0;
        if (despesas != null && !despesas.isEmpty()) {
            for (DespesaEntity despesa : despesas) {
                if (despesa.getStatusDespesa() == StatusDespesaEnum.PENDENTE) valor += despesa.getValor();
            }
        }
        return valor;

    }

    public List<Integer> calculaQuantidadePaginas(List<DespesaEntity> despesas, Pageable pageable) {
        List<Integer> paginas = new ArrayList<>();
        Integer contadorPaginas = 0;
        Integer contador = 0;
        paginas.add(contadorPaginas);
        for (int i = 0; i < despesas.size(); i++) {

            if (contador == pageable.getPageSize()) {
                contadorPaginas++;
                paginas.add(contadorPaginas);
                contador = 0;
            }
            contador++;

        }
        return paginas;
    }

    public Integer pendentesHoje() {
        List<DespesaEntity> despesas = crudService.buscaAgendadosHojeSemPaginacao();
        Integer quantidade = 0;
        if (despesas != null && !despesas.isEmpty()) {
            for (DespesaEntity despesa : despesas) {
                if (despesa.getStatusDespesa() == StatusDespesaEnum.PENDENTE) quantidade++;
            }
        }
        return quantidade;
    }

    public String constroiUriFiltro(FiltroDespesaDTO filtroDespesaDTO) {

        String uri = "despesas?";

        if (filtroDespesaDTO.getDescricao() != null && !filtroDespesaDTO.getDescricao().equals("")) {
            if (uri.equals("despesas?")) uri += "descricao=" + filtroDespesaDTO.getDescricao();
            else uri += "&descricao=" + filtroDespesaDTO.getDescricao();
        }

        if (filtroDespesaDTO.getDataInicio() != null && !filtroDespesaDTO.getDataInicio().equals("")) {
            if (uri.equals("despesas?")) uri += "inicio=" + filtroDespesaDTO.getDataInicio();
            else uri += "&inicio=" + filtroDespesaDTO.getDataInicio();
        }

        if (filtroDespesaDTO.getDataFim() != null && !filtroDespesaDTO.getDataFim().equals("")) {
            if (uri.equals("despesas?")) uri += "fim=" + filtroDespesaDTO.getDataFim();
            else uri += "&fim=" + filtroDespesaDTO.getDataFim();
        }

        if (filtroDespesaDTO.getPeriodoMes() != null && !filtroDespesaDTO.getPeriodoMes().equals("")) {
            if (uri.equals("despesas?")) uri += "mes=" + filtroDespesaDTO.getPeriodoMes();
            else uri += "&mes=" + filtroDespesaDTO.getPeriodoMes();
        }

        if (filtroDespesaDTO.getPeriodoAno() != null && !filtroDespesaDTO.getPeriodoAno().equals("")) {
            if (uri.equals("despesas?")) uri += "ano=" + filtroDespesaDTO.getPeriodoAno();
            else uri += "&ano=" + filtroDespesaDTO.getPeriodoAno();
        }

        if (filtroDespesaDTO.getTipo() != null && !filtroDespesaDTO.getTipo().equals("")) {
            if (uri.equals("despesas?")) uri += "tipo=" + filtroDespesaDTO.getTipo();
            else uri += "&tipo=" + filtroDespesaDTO.getTipo();
        }

        return uri;
    }

    public ModelMap modelMapBuilder(ModelMap modelMap, Pageable pageable, HttpServletRequest req,
                                    String descricao, String tipo, String inicio, String fim, String mes, String ano) {

        log.info("[STARTING] Iniciando construção do modelMap...");
        HashMap<String, Object> atributos = new HashMap<>();

        log.info("[PROGRESS] Verificando se existem itens em atraso ou que vencem hoje...");
        List<DespesaEntity> despesasAtrasadas = crudService.buscaDespesasAtrasadas();
        List<DespesaEntity> despesasHoje = crudService.buscaDespesasComVencimentoParaHoje();

        log.info("[PROGRESS] Iniciando setagem da lista de objetos encontrados com o filtro atual...");
        List<DespesaEntity> despesasSemPaginacao = filtroDespesasSemPaginacao(
                descricao,
                tipo,
                inicio,
                fim,
                mes,
                ano);

        List<DespesaEntity> despesasPaginadas = filtroDespesas(
                pageable,
                descricao,
                tipo,
                inicio,
                fim,
                mes,
                ano);

        log.info("[PROGRESS] Setando valores dos informativos...");
        atributos.put("pago", converteValorDoubleParaValorMonetario(calculaValorPago(despesasSemPaginacao)));
        atributos.put("pendente", converteValorDoubleParaValorMonetario(calculaValorPendente(despesasSemPaginacao)));
        atributos.put("pagar", pendentesHoje());

        log.info("[PROGRESS] Setando valores dos filtros...");
        atributos.put("despesasAtrasadas", despesasAtrasadas);
        atributos.put("despesasHoje", despesasHoje);
        atributos.put("descricao", descricao);
        atributos.put("tipo", tipo);
        atributos.put("dataInicio", inicio);
        atributos.put("dataFim", fim);
        atributos.put("mes", mes);
        atributos.put("ano", ano);
        atributos.put("despesas", despesasPaginadas);

        log.info("[PROGRESS] Inicializando setagem de tipo de filtro...");
        atributos.put(TIPO_FILTRO, "hoje");
        if (inicio != null && fim != null) atributos.replace(TIPO_FILTRO, "data");
        if (mes != null && ano != null) atributos.replace(TIPO_FILTRO, "periodo");
        if (descricao != null) atributos.replace(TIPO_FILTRO, "descricao");
        if (tipo != null) atributos.replace(TIPO_FILTRO, "tipo");

        log.info("[PROGRESS] Setando atributos da página...");
        atributos.put("pagina", pageable.getPageNumber());
        atributos.put("paginas", calculaQuantidadePaginas(despesasSemPaginacao, pageable));
        atributos.put("totalItens", despesasSemPaginacao.size());

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/despesas?" + req.getQueryString();

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
