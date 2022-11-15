package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.OrdemDTO;
import br.com.twobrothers.frontend.repositories.services.OrdemCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.LancamentoService;
import br.com.twobrothers.frontend.services.OrdemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/lancamento")
public class LancamentoController {

    @Autowired
    OrdemService ordemService;

    @Autowired
    OrdemCrudService ordemCrudService;

    @Autowired
    LancamentoService lancamentoService;

    @GetMapping
    public ModelAndView lancamentoGet(ModelAndView modelAndView, RedirectAttributes redirAttrs,
                                      @RequestParam("id") Optional<String> id, ModelMap modelMap) {
        try {
            lancamentoService.modelMapBuilder(modelMap, id.orElse(null));
            modelAndView.setViewName("lancamento");
            return modelAndView;
        } catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:lancamento");
            return modelAndView;
        }

    }

    @PostMapping
    public ModelAndView lancamentoPost(ModelAndView modelAndView,
                                       RedirectAttributes redirAttrs,
                                       OrdemDTO ordem) {
        ordemCrudService.criaNovo(ordem);
        redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro da ordem salvo com sucesso");
        modelAndView.setViewName("redirect:vendas");
        return modelAndView;
    }

    @PostMapping("/alterar")
    public ModelAndView atualizaOrdem(OrdemDTO ordem,
                                      RedirectAttributes redirAttrs,
                                      ModelAndView modelAndView,
                                      String query) {
        ordemCrudService.atualizaPorId(ordem.getId(), ordem);
        redirAttrs.addFlashAttribute("SucessoCadastro", "Atualização da ordem realizada com sucesso");
        modelAndView.setViewName("redirect:../vendas?" + query);
        return modelAndView;
    }

}
