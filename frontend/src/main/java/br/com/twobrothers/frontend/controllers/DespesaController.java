package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroDespesaDTO;
import br.com.twobrothers.frontend.models.entities.DespesaEntity;
import br.com.twobrothers.frontend.models.entities.user.PrivilegioEnum;
import br.com.twobrothers.frontend.models.enums.TipoDespesaEnum;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.DespesaCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.DespesaService;
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

import static br.com.twobrothers.frontend.utils.ConversorDeDados.converteValorDoubleParaValorMonetario;

@Controller
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    DespesaService despesaService;

    @Autowired
    DespesaCrudService despesaCrudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Bean
    public Function<String, String> currentUrlWithoutParam() {
        return param -> ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(param).toUriString();
    }

    @GetMapping
    public ModelAndView despesas(@PageableDefault(size = 10, page = 0, sort = {"dataAgendamento", "dataPagamento"}, direction = Sort.Direction.ASC) Pageable pageable,
                                 @RequestParam("descricao") Optional<String> descricao,
                                 @RequestParam("inicio") Optional<String> inicio,
                                 @RequestParam("fim") Optional<String> fim,
                                 @RequestParam("mes") Optional<Integer> mes,
                                 @RequestParam("ano") Optional<Integer> ano,
                                 @RequestParam("tipo") Optional<TipoDespesaEnum> tipo,
                                 Model model, ModelAndView modelAndView,
                                 RedirectAttributes redirAttrs,
                                 ModelMap modelMap,
                                 HttpServletRequest req) {

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/despesas?" + req.getQueryString();

        modelMap.addAttribute("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        modelMap.addAttribute("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        modelMap.addAttribute("baseUrl", baseUrl);
        modelMap.addAttribute("queryString", req.getQueryString());
        modelMap.addAttribute("completeUrl", completeUrl);

        List<DespesaEntity> despesasPaginadas = new ArrayList<>();
        List<DespesaEntity> despesasSemPaginacao = new ArrayList<>();
        List<Integer> paginas = new ArrayList<>();

        try {
            despesasPaginadas = despesaService.filtroDespesas(
                    pageable,
                    descricao.orElse(null),
                    tipo.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null));

            despesasSemPaginacao = despesaService.filtroDespesasSemPaginacao(
                    descricao.orElse(null),
                    tipo.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null));

            paginas = despesaService.calculaQuantidadePaginas(despesasSemPaginacao, pageable);

        }
        catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:despesas");
            return modelAndView;
        }

        model.addAttribute("tipoFiltro", "hoje");

        if (inicio.isPresent() && fim.isPresent()) model.addAttribute("tipoFiltro", "data");
        if (mes.isPresent() && ano.isPresent()) model.addAttribute("tipoFiltro", "periodo");
        if (descricao.isPresent()) model.addAttribute("tipoFiltro", "descricao");
        if (tipo.isPresent()) model.addAttribute("tipoFiltro", "tipo");

        model.addAttribute("descricao", descricao.orElse(null));
        model.addAttribute("dataInicio", inicio.orElse(null));
        model.addAttribute("dataFim", fim.orElse(null));
        model.addAttribute("mes", mes.orElse(null));
        model.addAttribute("ano", ano.orElse(null));
        model.addAttribute("tipo", tipo.orElse(null));
        model.addAttribute("paginas", paginas);
        model.addAttribute("pagina", pageable.getPageNumber());
        model.addAttribute("despesas", despesasPaginadas);
        model.addAttribute("pago", converteValorDoubleParaValorMonetario(despesaService.calculaValorPago(despesasSemPaginacao)));
        model.addAttribute("pendente", converteValorDoubleParaValorMonetario(despesaService.calculaValorPendente(despesasSemPaginacao)));
        model.addAttribute("pagar", despesaService.pendentesHoje());

        if(!UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().equals(PrivilegioEnum.VENDEDOR)) {
            modelAndView.setViewName("despesas");
        }
        else {
            modelAndView.setViewName("redirect:/");
            redirAttrs.addFlashAttribute("ErroCadastro",
                    "Você não possui o privilégio necessário para acessar a página de despesas");
        }

        return modelAndView;
    }

    @PostMapping
    public ModelAndView novaDespesa(DespesaDTO despesa,
                                    String query,
                                    ModelAndView modelAndView,
                                    RedirectAttributes redirAttrs) {

        String criaDespesa = despesaService.encaminhaParaCriacaoDoCrudService(despesa);

        if (criaDespesa == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro da despesa salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", criaDespesa);

        modelAndView.setViewName("redirect:despesas?" + query);
        return modelAndView;
    }

    @PostMapping("/filtro")
    public ModelAndView filtraDespesa(FiltroDespesaDTO filtroDespesa, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:../" + despesaService.constroiUriFiltro(filtroDespesa));
        return modelAndView;
    }

    @PostMapping("/deleta-{id}")
    public ModelAndView filtraDespesa(@PathVariable Long id,
                                      RedirectAttributes redirAttrs,
                                      ModelAndView modelAndView,
                                      String query) {
        despesaCrudService.deletaDespesaPorId(id);
        redirAttrs.addFlashAttribute("SucessoDelete", "Despesa deletada com sucesso");
        modelAndView.setViewName("redirect:../despesas?" + query);
        return modelAndView;
    }

    @PostMapping("/alterar")
    public ModelAndView atualizaDespesa(DespesaDTO despesa,
                                        RedirectAttributes redirAttrs,
                                        ModelAndView modelAndView,
                                        String query) {

        String atualizaDespesa = despesaService.encaminhaParaUpdateDoCrudService(despesa);

        if (atualizaDespesa == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro da despesa salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", atualizaDespesa);

        modelAndView.setViewName("redirect:../despesas?" + query);
        return modelAndView;
    }


}
