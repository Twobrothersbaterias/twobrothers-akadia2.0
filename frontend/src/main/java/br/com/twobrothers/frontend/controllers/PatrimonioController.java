package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.PatrimonioDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroPatrimonioDTO;
import br.com.twobrothers.frontend.models.entities.PatrimonioEntity;
import br.com.twobrothers.frontend.models.enums.TipoPatrimonioEnum;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.PatrimonioCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.PatrimonioService;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Controller
@RequestMapping("/patrimonios")
public class PatrimonioController {

    @Autowired
    PatrimonioService patrimonioService;

    @Autowired
    PatrimonioCrudService patrimonioCrudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public ModelAndView patrimonios(@PageableDefault(size = 10, page = 0, sort = {"dataAgendamento", "dataPagamento"}, direction = Sort.Direction.ASC) Pageable pageable,
                                    @RequestParam("descricao") Optional<String> descricao,
                                    @RequestParam("inicio") Optional<String> inicio,
                                    @RequestParam("fim") Optional<String> fim,
                                    @RequestParam("mes") Optional<Integer> mes,
                                    @RequestParam("ano") Optional<Integer> ano,
                                    @RequestParam("tipo") Optional<TipoPatrimonioEnum> tipo,
                                    Model model, ModelAndView modelAndView,
                                    RedirectAttributes redirAttrs,
                                    ModelMap modelMap,
                                    HttpServletRequest req) {

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/patrimonios?" + req.getQueryString();

        modelMap.addAttribute("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        modelMap.addAttribute("baseUrl", baseUrl);
        modelMap.addAttribute("queryString", req.getQueryString());
        modelMap.addAttribute("completeUrl", completeUrl);

        List<PatrimonioEntity> patrimoniosPaginados = new ArrayList<>();
        List<PatrimonioEntity> patrimoniosSemPaginacao = new ArrayList<>();
        List<Integer> paginas = new ArrayList<>();

        try {
            patrimoniosPaginados = patrimonioService.filtroPatrimonios(
                    pageable,
                    descricao.orElse(null),
                    tipo.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null));

            patrimoniosSemPaginacao = patrimonioService.filtroPatrimoniosSemPaginacao(
                    descricao.orElse(null),
                    tipo.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null));

            paginas = patrimonioService.calculaQuantidadePaginas(patrimoniosSemPaginacao, pageable);

        } catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:patrimonios");
            return modelAndView;
        }

        model.addAttribute("descricao", descricao.orElse(null));
        model.addAttribute("dataInicio", inicio.orElse(null));
        model.addAttribute("dataFim", fim.orElse(null));
        model.addAttribute("mes", mes.orElse(null));
        model.addAttribute("ano", ano.orElse(null));
        model.addAttribute("tipo", tipo.orElse(null));
        model.addAttribute("paginas", paginas);
        model.addAttribute("pagina", pageable.getPageNumber());
        model.addAttribute("patrimonios", patrimoniosPaginados);
        model.addAttribute("bruto", patrimonioService.calculaValorBruto(patrimoniosSemPaginacao));
        model.addAttribute("pendente", patrimonioService.calculaValorPendente(patrimoniosSemPaginacao));
        model.addAttribute("passivos", patrimonioService.calculaValorPassivos(patrimoniosSemPaginacao));
        model.addAttribute("caixa", patrimonioService.calculaValorCaixa(patrimoniosSemPaginacao));

        modelAndView.setViewName("patrimonio");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView novoPatrimonio(PatrimonioDTO patrimonio,
                                       String query,
                                       ModelAndView modelAndView,
                                       RedirectAttributes redirAttrs) {

        String criaPatrimonio = patrimonioService.encaminhaParaCriacaoDoCrudService(patrimonio);

        if (criaPatrimonio == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro do patrimônio salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", criaPatrimonio);

        modelAndView.setViewName("redirect:patrimonios?" + query);
        return modelAndView;
    }

    @PostMapping("/filtro")
    public ModelAndView filtraPatrimonio(FiltroPatrimonioDTO filtroPatrimonio, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:../" + patrimonioService.constroiUriFiltro(filtroPatrimonio));
        return modelAndView;
    }

    @PostMapping("/deleta-{id}")
    public ModelAndView deletaPatrimonio(@PathVariable Long id,
                                         RedirectAttributes redirAttrs,
                                         ModelAndView modelAndView,
                                         String query) {
        patrimonioCrudService.deletaPorId(id);
        redirAttrs.addFlashAttribute("SucessoDelete", "Patrimônio deletada com sucesso");
        modelAndView.setViewName("redirect:../patrimonios?" + query);
        return modelAndView;
    }

    @PostMapping("/alterar")
    public ModelAndView atualizaPatrimonio(PatrimonioDTO patrimonio,
                                           RedirectAttributes redirAttrs,
                                           ModelAndView modelAndView,
                                           String query) {

        String atualizaPatrimonio = patrimonioService.encaminhaParaUpdateDoCrudService(patrimonio);

        if (atualizaPatrimonio == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro do patrimônio salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", atualizaPatrimonio);

        modelAndView.setViewName("redirect:../patrimonios?" + query);
        return modelAndView;
    }


}
