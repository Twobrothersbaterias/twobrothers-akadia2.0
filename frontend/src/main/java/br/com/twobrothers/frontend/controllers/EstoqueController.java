package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.ProdutoEstoqueDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroProdutoDTO;
import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.models.enums.TipoProdutoEnum;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.ProdutoEstoqueCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.ProdutoEstoqueService;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                                    @RequestParam("mes") Optional<Integer> mes,
                                    @RequestParam("ano") Optional<Integer> ano,
                                    @RequestParam("tipo") Optional<TipoProdutoEnum> tipo,
                                    Model model, ModelAndView modelAndView,
                                    RedirectAttributes redirAttrs,
                                    ModelMap modelMap,
                                    HttpServletRequest req) {

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/estoque?" + req.getQueryString();

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
                    ano.orElse(null));

            produtosSemPaginacao = produtoEstoqueService.filtroProdutosSemPaginacao(
                    descricao.orElse(null),
                    tipo.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null));

            paginas = produtoEstoqueService.calculaQuantidadePaginas(produtosSemPaginacao, pageable);

        } catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:estoque");
            return modelAndView;
        }

        model.addAttribute("descricao", descricao.orElse(null));
        model.addAttribute("dataInicio", inicio.orElse(null));
        model.addAttribute("dataFim", fim.orElse(null));
        model.addAttribute("mes", mes.orElse(null));
        model.addAttribute("ano", ano.orElse(null));
        model.addAttribute("tipo", tipo.orElse(null));
        model.addAttribute("paginas", paginas);
        model.addAttribute("pagina", pageable.getPageNumber());
        model.addAttribute("produtos", produtosPaginados);
        model.addAttribute("bruto", produtoEstoqueService.calculaValorBruto(produtosSemPaginacao));
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
        produtoEstoqueCrudService.deletaPorId(id);
        redirAttrs.addFlashAttribute("SucessoDelete", "Produto deletado com sucesso");
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


}
