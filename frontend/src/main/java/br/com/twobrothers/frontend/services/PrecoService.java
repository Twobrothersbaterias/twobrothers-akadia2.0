package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.PrecoFornecedorDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroPrecoDTO;
import br.com.twobrothers.frontend.models.entities.PrecoFornecedorEntity;
import br.com.twobrothers.frontend.repositories.PrecoFornecedorRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.PrecoFornecedorCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.utils.TrataAtributosVazios;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static br.com.twobrothers.frontend.utils.StringConstants.URI_PRECO;

@Slf4j
@Service
public class PrecoService {

    @Autowired
    PrecoFornecedorCrudService crudService;

    public String encaminhaParaCriacaoDoCrudService(PrecoFornecedorDTO preco) {

        Long idFornecedor = null;

        if (preco.getFornecedor() != null) {
            idFornecedor = preco.getFornecedor().getId();
            preco.setFornecedor(null);
        }

        try {
            crudService.criaNovo(preco, idFornecedor, preco.getProduto().getId());
            return null;
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String encaminhaParaUpdateDoCrudService(PrecoFornecedorDTO preco) {
        try {
            crudService.atualizaPorId(preco);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<PrecoFornecedorEntity> filtroPrecos(Pageable pageable,
                                                    String fornecedorId,
                                                    String fornecedor,
                                                    String produtoId,
                                                    String produto) throws InvalidRequestException {
        if (fornecedorId != null) return crudService.buscaPorFornecedorIdPaginado(pageable, fornecedorId);
        else if (fornecedor != null) return crudService.buscaPorFornecedorPaginado(pageable, fornecedor);
        else if (produtoId != null) return crudService.buscaPorProdutoIdPaginado(pageable, produto);
        else if (produto != null) return crudService.buscaPorProdutoPaginado(pageable, produto);
        else return crudService.buscaTodosPaginado(pageable);
    }

    public List<PrecoFornecedorEntity> filtroPrecosSemPaginacao(
            String fornecedorId,
            String fornecedor,
            String produtoId,
            String produto) throws InvalidRequestException {

        if (fornecedorId != null) return crudService.buscaPorFornecedorIdSemPaginacao(fornecedorId);
        else if (fornecedor != null) return crudService.buscaPorFornecedorSemPaginacao(fornecedor);
        else if (produtoId != null) return crudService.buscaPorProdutoIdSemPaginacao(produto);
        else if (produto != null) return crudService.buscaPorProdutoSemPaginacao(produto);
        else return crudService.buscaTodosSemPaginacao();
    }

    public List<Integer> calculaQuantidadePaginas(List<PrecoFornecedorEntity> precos, Pageable pageable) {
        List<Integer> paginas = new ArrayList<>();
        Integer contadorPaginas = 0;
        Integer contador = 0;
        paginas.add(contadorPaginas);
        for (int i = 0; i < precos.size(); i++) {

            if (contador == pageable.getPageSize()) {
                contadorPaginas++;
                paginas.add(contadorPaginas);
                contador = 0;
            }
            contador++;

        }
        return paginas;
    }

    public String constroiUriFiltro(FiltroPrecoDTO filtro) {

        URI_PRECO = "precos?";

        if (filtro.getProduto() != null && !filtro.getProduto().equals("")) {
            URI_PRECO += "produto=" + filtro.getProduto();
        }

        if (filtro.getFornecedor() != null && !filtro.getFornecedor().equals("")) {
            URI_PRECO += "fornecedor=" + filtro.getFornecedor();
        }

        return URI_PRECO;
    }

}
