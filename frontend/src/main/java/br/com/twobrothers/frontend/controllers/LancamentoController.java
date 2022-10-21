package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.EntradaOrdemDTO;
import br.com.twobrothers.frontend.models.dto.OrdemDTO;
import br.com.twobrothers.frontend.services.ProdutoEstoqueService;
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

    @GetMapping
    public ModelAndView lancamentoGet(ModelAndView modelAndView, Model model) {

        model.addAttribute("produtos", produtoEstoqueService.buscaTodos());

        System.err.println(produtoEstoqueService.buscaTodos());

        modelAndView.setViewName("/lancamento");

        return modelAndView;

    }

    @PostMapping
    public ModelAndView lancamentoPost(ModelAndView modelAndView, OrdemDTO ordem) {

        System.err.println(ordem);
        modelAndView.setViewName("/lancamento");
        return modelAndView;
    }

}
