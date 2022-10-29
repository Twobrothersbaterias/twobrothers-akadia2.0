package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.FornecedorDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroFornecedorDTO;
import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
import br.com.twobrothers.frontend.models.entities.user.PrivilegioEnum;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.FornecedorCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.FornecedorService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/fornecedores")
public class FornecedorController {
    @Autowired
    FornecedorService fornecedorService;

    @Autowired
    FornecedorCrudService fornecedorCrudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public ModelAndView fornecedores(@PageableDefault(size = 10, page = 0, sort = {"dataCadastro"}, direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam("descricao") Optional<String> descricao,
                                 @RequestParam("inicio") Optional<String> inicio,
                                 @RequestParam("fim") Optional<String> fim,
                                 @RequestParam("mes") Optional<Integer> mes,
                                 @RequestParam("ano") Optional<Integer> ano,
                                 @RequestParam("cpfCnpj") Optional<String> cpfCnpj,
                                 @RequestParam("telefone") Optional<String> telefone,
                                 Model model, ModelAndView modelAndView,
                                 RedirectAttributes redirAttrs,
                                 ModelMap modelMap,
                                 HttpServletRequest req) {

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/fornecedores?" + req.getQueryString();

        modelMap.addAttribute("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        modelMap.addAttribute("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        modelMap.addAttribute("baseUrl", baseUrl);
        modelMap.addAttribute("queryString", req.getQueryString());
        modelMap.addAttribute("completeUrl", completeUrl);

        List<FornecedorEntity> fornecedoresPaginados = new ArrayList<>();
        List<FornecedorEntity> fornecedoresSemPaginacao = new ArrayList<>();
        List<Integer> paginas = new ArrayList<>();

        try {
            fornecedoresPaginados = fornecedorService.filtroFornecedores(
                    pageable,
                    descricao.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null),
                    cpfCnpj.orElse(null),
                    telefone.orElse(null));

            fornecedoresSemPaginacao = fornecedorService.filtroFornecedoresSemPaginacao(
                    descricao.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null),
                    cpfCnpj.orElse(null),
                    telefone.orElse(null));

            paginas = fornecedorService.calculaQuantidadePaginas(fornecedoresSemPaginacao, pageable);

        } catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:fornecedores");
            return modelAndView;
        }

        model.addAttribute("tipoFiltro", "hoje");

        if (inicio.isPresent() && fim.isPresent()) model.addAttribute("tipoFiltro", "data");
        if (mes.isPresent() && ano.isPresent()) model.addAttribute("tipoFiltro", "periodo");
        if (descricao.isPresent()) model.addAttribute("tipoFiltro", "descricao");
        if (cpfCnpj.isPresent()) model.addAttribute("tipoFiltro", "cpfCnpj");
        if (telefone.isPresent()) model.addAttribute("tipoFiltro", "telefone");

        model.addAttribute("totalItens", fornecedoresSemPaginacao.size());
        model.addAttribute("descricao", descricao.orElse(null));
        model.addAttribute("dataInicio", inicio.orElse(null));
        model.addAttribute("dataFim", fim.orElse(null));
        model.addAttribute("mes", mes.orElse(null));
        model.addAttribute("ano", ano.orElse(null));
        model.addAttribute("cpfCnpj", cpfCnpj.orElse(null));
        model.addAttribute("telefone", telefone.orElse(null));
        model.addAttribute("paginas", paginas);
        model.addAttribute("pagina", pageable.getPageNumber());
        model.addAttribute("fornecedores", fornecedoresPaginados);

        if(!UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().equals(PrivilegioEnum.VENDEDOR)) {
            modelAndView.setViewName("fornecedores");
        }
        else {
            modelAndView.setViewName("redirect:/");
            redirAttrs.addFlashAttribute("ErroCadastro",
                    "Você não possui o privilégio necessário para acessar a página de controle de fornecedores");
        }

        return modelAndView;

    }

    @PostMapping
    public ModelAndView novoFornecedor(FornecedorDTO fornecedor,
                                       String query,
                                       ModelAndView modelAndView,
                                       RedirectAttributes redirAttrs) {

        String criaFornecedor = fornecedorService.encaminhaParaCriacaoDoCrudService(fornecedor);

        if (criaFornecedor == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro do fornecedor salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", criaFornecedor);

        modelAndView.setViewName("redirect:fornecedores?" + query);
        return modelAndView;
    }

    @PostMapping("/filtro")
    public ModelAndView filtraFornecedor(FiltroFornecedorDTO filtroFornecedor, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:../" + fornecedorService.constroiUriFiltro(filtroFornecedor));
        return modelAndView;
    }

    @PostMapping("/deleta-{id}")
    public ModelAndView deletaFornecedor(@PathVariable Long id,
                                      RedirectAttributes redirAttrs,
                                      ModelAndView modelAndView,
                                      String query) {
        fornecedorCrudService.deletaPorId(id);
        redirAttrs.addFlashAttribute("SucessoDelete", "Fornecedor deletado com sucesso");
        modelAndView.setViewName("redirect:../fornecedores?" + query);
        return modelAndView;
    }

    @PostMapping("/alterar")
    public ModelAndView atualizaFornecedor(FornecedorDTO fornecedor,
                                        RedirectAttributes redirAttrs,
                                        ModelAndView modelAndView,
                                        String query) {

        String atualizaFornecedor = fornecedorService.encaminhaParaUpdateDoCrudService(fornecedor);

        if (atualizaFornecedor == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro do fornecedor salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", atualizaFornecedor);

        modelAndView.setViewName("redirect:../fornecedores?" + query);
        return modelAndView;
    }

}

