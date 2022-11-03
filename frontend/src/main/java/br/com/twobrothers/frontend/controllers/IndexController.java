package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.FornecedorDTO;
import br.com.twobrothers.frontend.models.dto.postagem.PostagemDTO;
import br.com.twobrothers.frontend.models.entities.postagem.SubCategoriaEntity;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.CategoriaCrudService;
import br.com.twobrothers.frontend.repositories.services.SubCategoriaCrudService;
import br.com.twobrothers.frontend.services.PostagemService;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("")
public class IndexController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PostagemService postagemService;

    @Autowired
    CategoriaCrudService categoriaCrudService;

    @Autowired
    SubCategoriaCrudService subCategoriaCrudService;

    @GetMapping
    public ModelAndView telaPrincipal(ModelAndView modelAndView,
                                      ModelMap modelMap) {

        modelMap.addAttribute("privilegio",
                UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        modelMap.addAttribute("categorias", categoriaCrudService.buscaTodasCategorias());
        modelMap.addAttribute("subcategorias", subCategoriaCrudService.buscaTodasCategorias());

        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView novoPost(PostagemDTO postagem,
                                 String query,
                                 ModelAndView modelAndView,
                                 RedirectAttributes redirAttrs) {

        String criaPostagem = postagemService.encaminhaParaCriacaoDoCrudService(postagem);

        if (criaPostagem == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Post salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", criaPostagem);

        modelAndView.setViewName("redirect:/?" + query);
        return modelAndView;
    }

}
