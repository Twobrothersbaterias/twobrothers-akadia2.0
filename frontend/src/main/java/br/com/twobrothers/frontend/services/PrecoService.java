package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.models.dto.PrecoFornecedorDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroPrecoDTO;
import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
import br.com.twobrothers.frontend.models.entities.PrecoFornecedorEntity;
import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.repositories.FornecedorRepository;
import br.com.twobrothers.frontend.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.PrecoFornecedorCrudService;
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

import static br.com.twobrothers.frontend.utils.StringConstants.TIPO_FILTRO;
import static br.com.twobrothers.frontend.utils.StringConstants.URI_PRECO;

@Slf4j
@Service
public class PrecoService {

    @Autowired
    PrecoFornecedorCrudService crudService;

    @Autowired
    ProdutoEstoqueService produtoEstoqueService;

    @Autowired
    FornecedorService fornecedorService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ProdutoEstoqueRepository produtoEstoqueRepository;

    public String encaminhaParaCriacaoDoCrudService(PrecoFornecedorDTO preco) {

        Long idFornecedor = null;

        if (preco.getFornecedor() != null) {
            idFornecedor = preco.getFornecedor().getId();
            preco.setFornecedor(null);
        }

        try {
            crudService.criaNovo(preco, idFornecedor, preco.getProduto().getId());
            return null;
        } catch (Exception e) {
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
        else if (produtoId != null) return crudService.buscaPorProdutoIdPaginado(pageable, produtoId);
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
        else if (produtoId != null) return crudService.buscaPorProdutoIdSemPaginacao(produtoId);
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

    public ModelMap modelMapBuilder(ModelMap modelMap, Pageable pageable, HttpServletRequest req,
                                    String fornecedorId, String fornecedor, String produtoId, String produto) {

        log.info("[STARTING] Iniciando construção do modelMap...");
        HashMap<String, Object> atributos = new HashMap<>();

        log.info("[PROGRESS] Setando lista de itens encontrados...");
        List<PrecoFornecedorEntity> precosSemPaginacao = filtroPrecosSemPaginacao(
                fornecedorId,
                fornecedor,
                produtoId,
                produto);

        List<PrecoFornecedorEntity> precosPaginados = filtroPrecos(
                pageable,
                fornecedorId,
                fornecedor,
                produtoId,
                produto);

        log.info("[PROGRESS] Inicializando setagem de tipo de filtro...");

        atributos.put(TIPO_FILTRO, "todos");

        FornecedorEntity fornecedorEncontradoPorId = new FornecedorEntity();
        ProdutoEstoqueEntity produtoEncontradoPorId = new ProdutoEstoqueEntity();

        if (fornecedorId != null) {
            atributos.replace(TIPO_FILTRO, "fornecedorId");
            fornecedorEncontradoPorId = (fornecedorRepository.findById(Long.parseLong(fornecedorId)).get());
        }
        if (produtoId != null) {
            atributos.replace(TIPO_FILTRO, "produtoId");
            produtoEncontradoPorId = (produtoEstoqueRepository.findById(Long.parseLong(produtoId)).get());
        }

        if (fornecedor != null) atributos.replace(TIPO_FILTRO, "fornecedor");
        if (produto != null) atributos.replace(TIPO_FILTRO, "produto");

        log.info("[PROGRESS] Setando valores dos filtros...");
        atributos.put("fornecedorId", fornecedorId);
        atributos.put("fornecedorEncontradoPorId", fornecedorEncontradoPorId);
        atributos.put("fornecedor", fornecedor);
        atributos.put("produtoId", produtoId);
        atributos.put("produtoEncontradoPorId", produtoEncontradoPorId);
        atributos.put("produto", produto);
        atributos.put("precos", precosPaginados);
        atributos.put("produtos", produtoEstoqueService.buscaTodos());
        atributos.put("fornecedores", fornecedorService.buscaTodos());

        log.info("[PROGRESS] Setando atributos da página...");
        atributos.put("pagina", pageable.getPageNumber());
        atributos.put("paginas", calculaQuantidadePaginas(precosSemPaginacao, pageable));
        atributos.put("totalItens", precosSemPaginacao.size());

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/precos?" + req.getQueryString();

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
