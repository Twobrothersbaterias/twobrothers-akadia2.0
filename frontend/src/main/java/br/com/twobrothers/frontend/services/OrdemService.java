package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.OrdemDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroOrdemDTO;
import br.com.twobrothers.frontend.models.entities.EntradaOrdemEntity;
import br.com.twobrothers.frontend.models.entities.OrdemEntity;
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

}
