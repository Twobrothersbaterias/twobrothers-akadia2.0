package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.filters.FiltroPostagemDTO;
import br.com.twobrothers.frontend.models.dto.postagem.PostagemDTO;
import br.com.twobrothers.frontend.repositories.services.PostagemCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.PostagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("")
public class IndexController {

    @Autowired
    PostagemService postagemService;

    @Autowired
    PostagemCrudService postagemCrudService;

    @GetMapping
    public ModelAndView telaPrincipal(@PageableDefault(size = 10, page = 0, sort = {"dataCadastro"}, direction = Sort.Direction.DESC) Pageable pageable,
                                      @RequestParam("titulo") Optional<String> titulo,
                                      @RequestParam("inicio") Optional<String> inicio,
                                      @RequestParam("fim") Optional<String> fim,
                                      @RequestParam("mes") Optional<Integer> mes,
                                      @RequestParam("ano") Optional<Integer> ano,
                                      @RequestParam("categoria") Optional<String> categoria,
                                      ModelAndView modelAndView, ModelMap modelMap,
                                      RedirectAttributes redirAttrs) {
        try {
            postagemService.modelMapperBuilder(modelMap, pageable, titulo.orElse(null), inicio.orElse(null),
                    fim.orElse(null), mes.orElse(null), ano.orElse(null), categoria.orElse(null));
            modelAndView.setViewName("index");
            return modelAndView;
        } catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:inicio");
            return modelAndView;
        }
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

    @PostMapping("/filtro")
    public ModelAndView filtraPostagem(FiltroPostagemDTO filtroPostagem, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:" + postagemService.constroiUriFiltro(filtroPostagem));
        return modelAndView;
    }

    @GetMapping("/deleta/{id}")
    public ModelAndView deletaCliente(@PathVariable("id") Long id,
                                      RedirectAttributes redirAttrs,
                                      ModelAndView modelAndView,
                                      String query) {
        postagemCrudService.deletaPorId(id);
        redirAttrs.addFlashAttribute("SucessoDelete", "Postagem removida com sucesso");
        modelAndView.setViewName("redirect:../?" + query);
        return modelAndView;
    }

}
