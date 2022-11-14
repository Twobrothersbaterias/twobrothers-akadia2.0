package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.OrdemDTO;
import br.com.twobrothers.frontend.repositories.OrdemRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.ClienteCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.LancamentoService;
import br.com.twobrothers.frontend.services.OrdemService;
import br.com.twobrothers.frontend.services.ProdutoEstoqueService;
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
    UsuarioRepository usuarioRepository;

    @Autowired
    OrdemRepository ordemRepository;

    @Autowired
    ProdutoEstoqueService produtoEstoqueService;

    @Autowired
    ClienteCrudService clienteCrudService;

    @Autowired
    OrdemService ordemService;

    @Autowired
    LancamentoService lancamentoService;

    @GetMapping
    public ModelAndView lancamentoGet(ModelAndView modelAndView, RedirectAttributes redirAttrs,
                                      @RequestParam("id") Optional<String> id, ModelMap modelMap) {
        try {
            lancamentoService.modelMapBuilder(modelMap, id.orElse(null));
            modelAndView.setViewName("lancamento");
            return modelAndView;
        }
        catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:lancamento");
            return modelAndView;
        }

    }

    @PostMapping
    public ModelAndView lancamentoPost(ModelAndView modelAndView,
                                       RedirectAttributes redirAttrs,
                                       OrdemDTO ordem) {

        String criaOrdem = ordemService.encaminhaParaCriacaoDoCrudService(ordem);

        if (criaOrdem == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro da ordem salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", criaOrdem);

        modelAndView.setViewName("redirect:vendas");
        return modelAndView;

    }

    @PostMapping("/alterar")
    public ModelAndView atualizaOrdem(OrdemDTO ordem,
                                              RedirectAttributes redirAttrs,
                                              ModelAndView modelAndView,
                                              String query) {

        String atualizaOrdem = ordemService.encaminhaParaUpdateDoCrudService(ordem);

        if (atualizaOrdem == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Atualização da ordem realizada com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", atualizaOrdem);

        modelAndView.setViewName("redirect:../vendas?" + query);
        return modelAndView;
    }

}
