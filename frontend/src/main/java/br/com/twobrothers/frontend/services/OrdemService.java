package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.filters.FiltroOrdemDTO;
import br.com.twobrothers.frontend.models.entities.EntradaOrdemEntity;
import br.com.twobrothers.frontend.models.entities.OrdemEntity;
import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.repositories.OrdemRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.OrdemCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static br.com.twobrothers.frontend.utils.StringConstants.URI_ORDENS;

@Slf4j
@Service
public class OrdemService {

    @Autowired
    OrdemCrudService crudService;

    @Autowired
    UsuarioRepository usuario;

    @Autowired
    OrdemRepository ordemRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    public List<OrdemEntity> filtroOrdens(
            Pageable pageable,
            String dataInicio,
            String dataFim,
            Integer mes,
            Integer ano) throws InvalidRequestException {
        if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeData(pageable, dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodo(pageable, mes, ano);
        else return crudService.buscaHojePaginado(pageable);
    }

    public List<OrdemEntity> filtroOrdensSemPaginacao(
            String dataInicio,
            String dataFim,
            Integer mes,
            Integer ano) throws InvalidRequestException {
        if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(mes, ano);
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
                    custo += (entrada.getProduto().getCustoUnitario() * entrada.getQuantidade());
                }
            }
        }
        return custo;
    }

    public String constroiUriFiltro(FiltroOrdemDTO filtroOrdemDTO) {

        URI_ORDENS = "ordens?";

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

        return URI_ORDENS;
    }

}
