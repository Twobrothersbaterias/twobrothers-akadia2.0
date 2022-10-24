package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.EntradaOrdemDTO;
import br.com.twobrothers.frontend.models.dto.OrdemDTO;
import br.com.twobrothers.frontend.repositories.services.OrdemCrudService;
import br.com.twobrothers.frontend.services.ProdutoEstoqueService;
import br.com.twobrothers.frontend.utils.ConversorDeDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/lancamento")
public class LancamentoController {

    @Autowired
    ProdutoEstoqueService produtoEstoqueService;

    @Autowired
    OrdemCrudService ordemCrudService;

    @GetMapping
    public ModelAndView lancamentoGet(ModelAndView modelAndView, Model model) {

        model.addAttribute("produtos", produtoEstoqueService.buscaTodos());

        modelAndView.setViewName("lancamento");

        return modelAndView;

    }

    @PostMapping
    public ModelAndView lancamentoPost(ModelAndView modelAndView, OrdemDTO ordem) {

        ordemCrudService.criaNovo(ordem);

        modelAndView.setViewName("redirect:/lancamento");
        return modelAndView;
    }

}
