package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.OrdemDTO;
import br.com.twobrothers.frontend.repositories.services.ClienteCrudService;
import br.com.twobrothers.frontend.repositories.services.OrdemCrudService;
import br.com.twobrothers.frontend.services.ProdutoEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/lancamento")
public class LancamentoController {

    @Autowired
    ProdutoEstoqueService produtoEstoqueService;

    @Autowired
    ClienteCrudService clienteCrudService;

    @Autowired
    OrdemCrudService ordemCrudService;

    @GetMapping
    public ModelAndView lancamentoGet(ModelAndView modelAndView, Model model) {

        model.addAttribute("produtos", produtoEstoqueService.buscaTodos());
        model.addAttribute("clientes", clienteCrudService.buscaTodos());

        modelAndView.setViewName("lancamento");

        return modelAndView;

    }

    @PostMapping
    public ModelAndView lancamentoPost(ModelAndView modelAndView,
                                       RedirectAttributes redirAttrs,
                                       OrdemDTO ordem) {

        ordemCrudService.criaNovo(ordem);
        redirAttrs.addFlashAttribute("SucessoCadastro", "Ordem cadastrada com sucesso");
        modelAndView.setViewName("redirect:/vendas");
        return modelAndView;
    }

}
