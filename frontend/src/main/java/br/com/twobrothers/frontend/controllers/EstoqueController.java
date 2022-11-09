package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.ProdutoEstoqueDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroProdutoDTO;
import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.ProdutoEstoqueCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.frontend.services.ProdutoEstoqueService;
import br.com.twobrothers.frontend.services.exporter.EstoquePdfExporter;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static br.com.twobrothers.frontend.utils.ConversorDeDados.converteValorDoubleParaValorMonetario;

@Controller
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    ProdutoEstoqueService produtoEstoqueService;

    @Autowired
    ProdutoEstoqueCrudService produtoEstoqueCrudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public ModelAndView Produtos(@PageableDefault(size = 10, page = 0, sort = {"quantidade"}, direction = Sort.Direction.ASC) Pageable pageable,
                                 @RequestParam("descricao") Optional<String> descricao,
                                 @RequestParam("inicio") Optional<String> inicio,
                                 @RequestParam("fim") Optional<String> fim,
                                 @RequestParam("mes") Optional<String> mes,
                                 @RequestParam("ano") Optional<String> ano,
                                 @RequestParam("tipo") Optional<String> tipo,
                                 @RequestParam("fornecedor") Optional<String> fornecedor,
                                 Model model, ModelAndView modelAndView,
                                 RedirectAttributes redirAttrs,
                                 ModelMap modelMap,
                                 HttpServletRequest req) {

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/estoque?" + req.getQueryString();

        modelMap.addAttribute("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        modelMap.addAttribute("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        modelMap.addAttribute("baseUrl", baseUrl);
        modelMap.addAttribute("queryString", req.getQueryString());
        modelMap.addAttribute("completeUrl", completeUrl);

        List<ProdutoEstoqueEntity> produtosPaginados = new ArrayList<>();
        List<ProdutoEstoqueEntity> produtosSemPaginacao = new ArrayList<>();
        List<Integer> paginas = new ArrayList<>();

        try {
            produtosPaginados = produtoEstoqueService.filtroProdutos(
                    pageable,
                    descricao.orElse(null),
                    tipo.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null),
                    fornecedor.orElse(null));

            produtosSemPaginacao = produtoEstoqueService.filtroProdutosSemPaginacao(
                    descricao.orElse(null),
                    tipo.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null),
                    fornecedor.orElse(null));

            paginas = produtoEstoqueService.calculaQuantidadePaginas(produtosSemPaginacao, pageable);

        } catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:estoque");
            return modelAndView;
        }

        model.addAttribute("tipoFiltro", "todos");

        if (inicio.isPresent() && fim.isPresent()) model.addAttribute("tipoFiltro", "data");
        if (mes.isPresent() && ano.isPresent()) model.addAttribute("tipoFiltro", "periodo");
        if (descricao.isPresent()) model.addAttribute("tipoFiltro", "descricao");
        if (tipo.isPresent()) model.addAttribute("tipoFiltro", "tipo");
        if (fornecedor.isPresent()) model.addAttribute("tipoFiltro", "fornecedor");

        model.addAttribute("descricao", descricao.orElse(null));
        model.addAttribute("dataInicio", inicio.orElse(null));
        model.addAttribute("dataFim", fim.orElse(null));
        model.addAttribute("mes", mes.orElse(null));
        model.addAttribute("ano", ano.orElse(null));
        model.addAttribute("tipo", tipo.orElse(null));
        model.addAttribute("fornecedor", fornecedor.orElse(null));
        model.addAttribute("paginas", paginas);
        model.addAttribute("pagina", pageable.getPageNumber());
        model.addAttribute("produtos", produtosPaginados);
        model.addAttribute("bruto", converteValorDoubleParaValorMonetario(produtoEstoqueService.calculaValorBruto(produtosSemPaginacao)));
        model.addAttribute("qtdeBaterias", produtoEstoqueService.calculaQtdBaterias(produtosSemPaginacao));
        model.addAttribute("qtdeSucatas", produtoEstoqueService.calculaQtdSucatas(produtosSemPaginacao));

        modelAndView.setViewName("estoque");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView novoProduto(ProdutoEstoqueDTO produto,
                                    String query,
                                    ModelAndView modelAndView,
                                    RedirectAttributes redirAttrs) {

        String criaProduto = produtoEstoqueService.encaminhaParaCriacaoDoCrudService(produto);

        if (criaProduto == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro do produto salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", criaProduto);

        modelAndView.setViewName("redirect:estoque?" + query);
        return modelAndView;
    }

    @PostMapping("/filtro")
    public ModelAndView filtraProduto(FiltroProdutoDTO filtroProduto, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:../" + produtoEstoqueService.constroiUriFiltro(filtroProduto));
        return modelAndView;
    }

    @PostMapping("/deleta-{id}")
    public ModelAndView deletaProduto(@PathVariable Long id,
                                      RedirectAttributes redirAttrs,
                                      ModelAndView modelAndView,
                                      String query) {
        try {
            produtoEstoqueCrudService.deletaPorId(id);
            redirAttrs.addFlashAttribute("SucessoDelete", "Produto deletado com sucesso");
        } catch (Exception e) {
            redirAttrs.addFlashAttribute("ErroCadastro", e.getMessage());
        }
        modelAndView.setViewName("redirect:../estoque?" + query);
        return modelAndView;
    }

    @PostMapping("/alterar")
    public ModelAndView atualizaProduto(ProdutoEstoqueDTO produto,
                                        RedirectAttributes redirAttrs,
                                        ModelAndView modelAndView,
                                        String query) {

        String atualizaProduto = produtoEstoqueService.encaminhaParaUpdateDoCrudService(produto);

        if (atualizaProduto == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Atualização do produto realizada com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", atualizaProduto);

        modelAndView.setViewName("redirect:../estoque?" + query);
        return modelAndView;
    }

    @GetMapping("/relatorio")
    public void relatorio(ModelAndView modelAndView,
                          @RequestParam("descricao") Optional<String> descricao,
                          @RequestParam("inicio") Optional<String> inicio,
                          @RequestParam("fim") Optional<String> fim,
                          @RequestParam("mes") Optional<String> mes,
                          @RequestParam("ano") Optional<String> ano,
                          @RequestParam("tipo") Optional<String> tipo,
                          @RequestParam("fornecedor") Optional<String> fornecedor,
                          HttpServletResponse res) throws DocumentException, IOException {

        List<ProdutoEstoqueEntity> produtos = produtoEstoqueService.filtroProdutosSemPaginacao(
                descricao.orElse(null),
                tipo.orElse(null),
                inicio.orElse(null),
                fim.orElse(null),
                mes.orElse(null),
                ano.orElse(null),
                fornecedor.orElse(null));

        HashMap<String, String> filtros = new HashMap<>();
        filtros.put("descricao", descricao.orElse(null));
        filtros.put("tipo", tipo.orElse(null));
        filtros.put("inicio", inicio.orElse(null));
        filtros.put("fim", fim.orElse(null));
        filtros.put("mes", mes.orElse(null));
        filtros.put("ano", ano.orElse(null));
        filtros.put("fornecedor", fornecedor.orElse(null));

        res.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachement; filename=akadia_estoque_"
                + new SimpleDateFormat("dd.MM.yyyy_HHmmss").format(new Date())
                + ".pdf";
        res.setHeader(headerKey, headerValue);
        EstoquePdfExporter pdfExporterService = new EstoquePdfExporter();
        pdfExporterService.export(res, produtos, produtoEstoqueService, filtros, usuarioRepository);

    }


}
