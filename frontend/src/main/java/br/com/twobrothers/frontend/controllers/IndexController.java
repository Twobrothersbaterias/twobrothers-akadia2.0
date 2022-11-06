package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.FornecedorDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroClienteDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroPostagemDTO;
import br.com.twobrothers.frontend.models.dto.postagem.PostagemDTO;
import br.com.twobrothers.frontend.models.entities.ClienteEntity;
import br.com.twobrothers.frontend.models.entities.postagem.PostagemEntity;
import br.com.twobrothers.frontend.models.entities.postagem.SubCategoriaEntity;
import br.com.twobrothers.frontend.repositories.PostagemRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.CategoriaCrudService;
import br.com.twobrothers.frontend.repositories.services.PostagemCrudService;
import br.com.twobrothers.frontend.repositories.services.SubCategoriaCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.PostagemService;
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

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class IndexController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PostagemRepository postagemRepository;

    @Autowired
    PostagemService postagemService;

    @Autowired
    CategoriaCrudService categoriaCrudService;

    @Autowired
    PostagemCrudService postagemCrudService;

    @Autowired
    SubCategoriaCrudService subCategoriaCrudService;

    @GetMapping
    public ModelAndView telaPrincipal(@PageableDefault(size = 10, page = 0, sort = {"dataCadastro"}, direction = Sort.Direction.DESC) Pageable pageable,
                                      @RequestParam("titulo") Optional<String> titulo,
                                      @RequestParam("inicio") Optional<String> inicio,
                                      @RequestParam("fim") Optional<String> fim,
                                      @RequestParam("mes") Optional<Integer> mes,
                                      @RequestParam("ano") Optional<Integer> ano,
                                      @RequestParam("categoria") Optional<String> categoria,
                                      ModelAndView modelAndView, ModelMap modelMap,
                                      RedirectAttributes redirAttrs, Model model) {

        modelMap.addAttribute("privilegio",
                UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());

        List<PostagemEntity> postagens = new ArrayList<>();
        List<Integer> paginas = new ArrayList<>();

        try {

            postagens = postagemService.filtroPostagensSemPaginacao(
                    titulo.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null),
                    categoria.orElse(null));

            paginas = postagemService.calculaQuantidadePaginas(postagens, pageable);

        } catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:clientes");
            return modelAndView;
        }

        model.addAttribute("tipoFiltro", "hoje");

        if (inicio.isPresent() && fim.isPresent()) model.addAttribute("tipoFiltro", "data");
        if (mes.isPresent() && ano.isPresent()) model.addAttribute("tipoFiltro", "periodo");
        if (titulo.isPresent()) model.addAttribute("tipoFiltro", "titulo");
        if (categoria.isPresent()) model.addAttribute("tipoFiltro", "categoria");

        model.addAttribute("totalItens", postagens.size());
        model.addAttribute("titulo", titulo.orElse(null));
        model.addAttribute("dataInicio", inicio.orElse(null));
        model.addAttribute("dataFim", fim.orElse(null));
        model.addAttribute("mes", mes.orElse(null));
        model.addAttribute("ano", ano.orElse(null));
        model.addAttribute("categoria", categoria.orElse(null));
        model.addAttribute("paginas", paginas);
        model.addAttribute("pagina", pageable.getPageNumber());
        modelMap.addAttribute("postagens", postagens);
        modelMap.addAttribute("categorias", categoriaCrudService.buscaTodasCategorias());

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
