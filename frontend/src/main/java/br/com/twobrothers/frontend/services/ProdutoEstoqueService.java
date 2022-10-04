package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.ProdutoEstoqueDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroProdutoDTO;
import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.models.enums.TipoProdutoEnum;
import br.com.twobrothers.frontend.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.ProdutoEstoqueCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static br.com.twobrothers.frontend.utils.StringConstants.URI_ESTOQUE;

@Slf4j
@Service
public class ProdutoEstoqueService {

    @Autowired
    ProdutoEstoqueCrudService crudService;

    @Autowired
    UsuarioRepository usuario;

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
            TipoProdutoEnum tipo,
            String dataInicio,
            String dataFim,
            Integer mes,
            Integer ano) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorDescricao(pageable, descricao);
        else if (tipo != null) return crudService.buscaPorTipo(pageable, tipo);
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeData(pageable, dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodo(pageable, mes, ano);
        else return produtoEstoqueRepository.findAll(pageable).toList();
    }

    public List<ProdutoEstoqueEntity> filtroProdutosSemPaginacao(
            String descricao,
            TipoProdutoEnum tipo,
            String dataInicio,
            String dataFim,
            Integer mes,
            Integer ano) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorDescricaoSemPaginacao(descricao);
        else if (tipo != null) return crudService.buscaPorTipoSemPaginacao(tipo);
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(mes, ano);
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

}
