package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.AbastecimentoDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroAbastecimentoDTO;
import br.com.twobrothers.frontend.models.dto.postagem.PostagemDTO;
import br.com.twobrothers.frontend.models.entities.AbastecimentoEntity;
import br.com.twobrothers.frontend.models.entities.postagem.PostagemEntity;
import br.com.twobrothers.frontend.models.enums.FormaPagamentoEnum;
import br.com.twobrothers.frontend.repositories.AbastecimentoRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.AbastecimentoCrudService;
import br.com.twobrothers.frontend.repositories.services.PostagemCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static br.com.twobrothers.frontend.utils.StringConstants.URI_PRECO;

@Slf4j
@Service
public class PostagemService {

    @Autowired
    PostagemCrudService crudService;

    @Autowired
    UsuarioRepository usuario;

    @Autowired
    ModelMapperConfig modelMapper;

    public String encaminhaParaCriacaoDoCrudService(PostagemDTO postagem) {
        try {
            crudService.criaNovo(postagem);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String encaminhaParaUpdateDoCrudService(PostagemDTO postagem) {
        try {
            crudService.atualizaPorId(postagem);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

//    public List<AbastecimentoEntity> filtroAbastecimentos(Pageable pageable,
//                                                          String dataInicio,
//                                                          String dataFim,
//                                                          Integer mes,
//                                                          Integer ano,
//                                                          String fornecedorId,
//                                                          String fornecedor,
//                                                          String produto,
//                                                          String meio) throws InvalidRequestException {
//        if (dataInicio != null && dataFim != null)
//            return crudService.buscaPorRangeDeDataPaginado(pageable, dataInicio, dataFim);
//        else if (mes != null && ano != null) return crudService.buscaPorPeriodoPaginado(pageable, mes, ano);
//        else if(fornecedorId != null) return crudService.buscaPorFornecedorIdPaginado(pageable, fornecedorId);
//        else if (fornecedor != null) return crudService.buscaPorFornecedorPaginado(pageable, fornecedor);
//        else if (produto != null) return crudService.buscaPorProdutoPaginado(pageable, produto);
//        else if (meio != null) return crudService.buscaPorFormaDePagamentoPaginado(pageable, meio);
//        else return crudService.buscaHojePaginado(pageable);
//    }
//
//    public List<AbastecimentoEntity> filtroAbastecimentosSemPaginacao(
//            String dataInicio,
//            String dataFim,
//            Integer mes,
//            Integer ano,
//            String fornecedorId,
//            String fornecedor,
//            String produto,
//            String meio) throws InvalidRequestException {
//        if (dataInicio != null && dataFim != null)
//            return crudService.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
//        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(mes, ano);
//        else if (fornecedorId != null) return crudService.buscaPorFornecedorIdSemPaginacao(fornecedorId);
//        else if (fornecedor != null) return crudService.buscaPorFornecedorSemPaginacao(fornecedor);
//        else if (produto != null) return crudService.buscaPorProdutoSemPaginacao(produto);
//        else if (meio != null) return crudService.buscaPorFormaDePagamentoSemPaginacao(meio);
//        else return crudService.buscaHojeSemPaginacao();
//    }

    public List<Integer> calculaQuantidadePaginas(List<PostagemEntity> postagens, Pageable pageable) {
        List<Integer> paginas = new ArrayList<>();
        Integer contadorPaginas = 0;
        Integer contador = 0;
        paginas.add(contadorPaginas);
        for (int i = 0; i < postagens.size(); i++) {

            if (contador == pageable.getPageSize()) {
                contadorPaginas++;
                paginas.add(contadorPaginas);
                contador = 0;
            }
            contador++;

        }
        return paginas;
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

}
