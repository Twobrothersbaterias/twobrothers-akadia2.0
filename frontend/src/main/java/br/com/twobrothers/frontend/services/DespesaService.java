package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroDespesaDTO;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.DespesaCrudService;
import br.com.twobrothers.frontend.utils.ConversorDeDatas;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DespesaService {

    @Autowired
    DespesaCrudService crudService;

    @Autowired
    UsuarioRepository usuario;

    public void tratamentoDeNovaDespesa(DespesaDTO despesaDTO) {

        despesaDTO.setIdUsuarioResponsavel(UsuarioUtils.loggedUser(usuario).getId());

        if (despesaDTO.getDataPagamento() != null)
            despesaDTO.setDataPagamento(ConversorDeDatas.converteDataUsParaDataBr(despesaDTO.getDataPagamento()));

        if (despesaDTO.getDataAgendamento() != null)
            despesaDTO.setDataAgendamento(ConversorDeDatas.converteDataUsParaDataBr(despesaDTO.getDataAgendamento()));

        crudService.criaNovaDespesa(despesaDTO);
    }

    public List<DespesaDTO> filtrandoDespesas(Pageable pageable) {
        return crudService.buscaPorPaginacao(pageable);
    }

    public String constroiUriFiltro(FiltroDespesaDTO filtroDespesaDTO) {

        String uri = "despesas?";

        if (filtroDespesaDTO.getDescricao() != null && !filtroDespesaDTO.getDescricao().equals("")) {
            uri += "descricao=" + filtroDespesaDTO.getDescricao();
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
