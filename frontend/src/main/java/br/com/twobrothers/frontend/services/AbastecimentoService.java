package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.AbastecimentoDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroAbastecimentoDTO;
import br.com.twobrothers.frontend.models.entities.AbastecimentoEntity;
import br.com.twobrothers.frontend.models.enums.FormaPagamentoEnum;
import br.com.twobrothers.frontend.repositories.AbastecimentoRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.AbastecimentoCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static br.com.twobrothers.frontend.utils.StringConstants.URI_ABASTECIMENTO;

@Slf4j
@Service
public class AbastecimentoService {

    @Autowired
    AbastecimentoCrudService crudService;

    @Autowired
    UsuarioRepository usuario;

    @Autowired
    AbastecimentoRepository abastecimentoRepository;

    @Autowired
    ModelMapperConfig modelMapper;

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

//    public String encaminhaParaUpdateDoCrudService(AbastecimentoDTO abastecimento) {
//        try {
//            crudService.atualizaPorId(abastecimento);
//            return null;
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }

    public List<AbastecimentoEntity> filtroAbastecimentos(Pageable pageable,
                                                          String dataInicio,
                                                          String dataFim,
                                                          Integer mes,
                                                          Integer ano,
                                                          String fornecedor,
                                                          String produto) throws InvalidRequestException {
        if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataPaginado(pageable, dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoPaginado(pageable, mes, ano);
        else if (fornecedor != null) return crudService.buscaPorFornecedorPaginado(pageable, fornecedor);
        else if (produto != null) return crudService.buscaPorProdutoPaginado(pageable, produto);
        else return crudService.buscaHojePaginado(pageable);
    }

    public List<AbastecimentoEntity> filtroAbastecimentosSemPaginacao(
            String dataInicio,
            String dataFim,
            Integer mes,
            Integer ano,
            String fornecedor,
            String produto) throws InvalidRequestException {
        if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(mes, ano);
        else if (fornecedor != null) return crudService.buscaPorFornecedorSemPaginacao(fornecedor);
        else if (produto != null) return crudService.buscaPorProdutoSemPaginacao(produto);
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

        URI_ABASTECIMENTO = "compras?";

        if (filtro.getDataInicio() != null && !filtro.getDataInicio().equals("")) {
            URI_ABASTECIMENTO += "inicio=" + filtro.getDataInicio();
        }

        if (filtro.getDataFim() != null && !filtro.getDataFim().equals("")) {
            URI_ABASTECIMENTO += "&fim=" + filtro.getDataFim();
        }

        if (filtro.getPeriodoMes() != null && !filtro.getPeriodoMes().equals("")) {
            URI_ABASTECIMENTO += "mes=" + filtro.getPeriodoMes();
        }

        if (filtro.getPeriodoAno() != null && !filtro.getPeriodoAno().equals("")) {
            URI_ABASTECIMENTO += "&ano=" + filtro.getPeriodoAno();
        }

        if (filtro.getProduto() != null && !filtro.getProduto().equals("")) {
            URI_ABASTECIMENTO += "produto=" + filtro.getProduto();
        }

        if (filtro.getFornecedor() != null && !filtro.getFornecedor().equals("")) {
            URI_ABASTECIMENTO += "fornecedor=" + filtro.getFornecedor();
        }

        return URI_ABASTECIMENTO;
    }

}
