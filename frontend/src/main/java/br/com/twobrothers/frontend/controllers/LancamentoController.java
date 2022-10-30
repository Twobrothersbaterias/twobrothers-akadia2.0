package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.AbastecimentoDTO;
import br.com.twobrothers.frontend.models.dto.OrdemDTO;
import br.com.twobrothers.frontend.models.entities.OrdemEntity;
import br.com.twobrothers.frontend.models.entities.UsuarioEntity;
import br.com.twobrothers.frontend.repositories.OrdemRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.ClienteCrudService;
import br.com.twobrothers.frontend.services.OrdemService;
import br.com.twobrothers.frontend.services.ProdutoEstoqueService;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
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

    @GetMapping
    public ModelAndView lancamentoGet(ModelAndView modelAndView,
                                      @RequestParam("id") Optional<String> id,
                                      Model model) {

        String idOrdem = id.orElse(null);
        OrdemEntity ordem = null;

        if (idOrdem != null) ordem = ordemRepository.findById(Long.parseLong(idOrdem)).get();

        model.addAttribute("ordem", null);
        model.addAttribute("entradas", null);
        model.addAttribute("pagamentos", null);
        model.addAttribute("colaboradores", usuarioRepository.buscaTodosSemPaginacao());

        if (ordem != null) {
            model.addAttribute("ordemEdicao", ordem);
        }

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
