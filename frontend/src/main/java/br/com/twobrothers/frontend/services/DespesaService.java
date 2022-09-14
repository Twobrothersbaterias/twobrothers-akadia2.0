package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroDespesaDTO;
import br.com.twobrothers.frontend.models.entities.DespesaEntity;
import br.com.twobrothers.frontend.models.enums.StatusDespesaEnum;
import br.com.twobrothers.frontend.models.enums.TipoDespesaEnum;
import br.com.twobrothers.frontend.repositories.DespesaRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.DespesaCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.twobrothers.frontend.utils.StringConstants.BARRA_DE_LOG;

@Slf4j
@Service
public class DespesaService {

    @Autowired
    DespesaCrudService crudService;

    @Autowired
    UsuarioRepository usuario;

    @Autowired
    DespesaRepository despesaRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    public String encaminhaParaCriacaoDoCrudService(DespesaDTO despesa) {
        try {
            crudService.criaNovaDespesa(despesa);
            return null;
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<DespesaEntity> filtroDespesas(Pageable pageable,
                                           String descricao,
                                           TipoDespesaEnum tipo,
                                           String dataInicio,
                                           String dataFim,
                                           Integer mes,
                                           Integer ano) throws InvalidRequestException {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de direcionamento de filtro de despesas...");
        if (descricao != null) return crudService.buscaPorDescricao(pageable, descricao);
        else if (tipo != null) return crudService.buscaPorTipo(pageable, tipo);
        else if (dataInicio != null && dataFim != null) return crudService.buscaPorRangeDeData(pageable, dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodo(pageable, mes, ano);
        else return crudService.buscaHoje(pageable);
    }

    public List<DespesaEntity> filtroDespesasSemPaginacao(
                                              String descricao,
                                              TipoDespesaEnum tipo,
                                              String dataInicio,
                                              String dataFim,
                                              Integer mes,
                                              Integer ano) throws InvalidRequestException {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de direcionamento de filtro de despesas...");
        if (descricao != null) return crudService.buscaPorDescricaoSemPaginacao(descricao);
        else if (tipo != null) return crudService.buscaPorTipoSemPaginacao(tipo);
        else if (dataInicio != null && dataFim != null) return crudService.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(mes, ano);
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

    public Integer pendentesHoje() {
        List<DespesaEntity> despesas = crudService.buscaAgendadosHojeSemPaginacao();
        Integer quantidade = 0;
        if (despesas != null && !despesas.isEmpty()) {
            for (DespesaEntity despesa: despesas) {
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

}
