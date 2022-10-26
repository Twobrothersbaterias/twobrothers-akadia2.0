package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.OrdemDTO;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.ClienteCrudService;
import br.com.twobrothers.frontend.repositories.services.OrdemCrudService;
import br.com.twobrothers.frontend.services.OrdemService;
import br.com.twobrothers.frontend.services.ProdutoEstoqueService;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
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
    UsuarioRepository usuarioRepository;

    @Autowired
    ProdutoEstoqueService produtoEstoqueService;

    @Autowired
    ClienteCrudService clienteCrudService;

    @Autowired
    OrdemService ordemService;

    @GetMapping
    public ModelAndView lancamentoGet(ModelAndView modelAndView,
                                      Model model) {

        model.addAttribute("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        model.addAttribute("produtos", produtoEstoqueService.buscaTodos());
        model.addAttribute("clientes", clienteCrudService.buscaTodos());

        modelAndView.setViewName("lancamento");

        return modelAndView;

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

}
