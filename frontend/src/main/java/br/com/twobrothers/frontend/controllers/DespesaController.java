package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroDespesaDTO;
import br.com.twobrothers.frontend.models.entities.DespesaEntity;
import br.com.twobrothers.frontend.models.enums.TipoDespesaEnum;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.DespesaCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.DespesaService;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import br.com.twobrothers.frontend.utils.comparators.Despesa.ComparadorDeAgendamento;
import br.com.twobrothers.frontend.utils.comparators.Despesa.ComparadorDeDataDePagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
    public ModelAndView despesas(@PageableDefault(size = 10, page = 0) Pageable pageable,
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

        modelMap.addAttribute("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        modelMap.addAttribute("baseUrl", baseUrl);

        List<DespesaEntity> despesasPaginadas = new ArrayList<>();
        List<DespesaEntity> despesasSemPaginacao = new ArrayList<>();

        try {
            despesasPaginadas = despesaService.filtroDespesas(
                    pageable,
                    descricao.orElse(null),
                    tipo.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null));

            Collections.sort(despesasPaginadas, new ComparadorDeDataDePagamento());
            Collections.sort(despesasPaginadas, new ComparadorDeAgendamento());

            despesasSemPaginacao = despesaService.filtroDespesasSemPaginacao(
                    descricao.orElse(null),
                    tipo.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null));
        }
        catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:despesas");
            return modelAndView;
        }

        model.addAttribute("despesas", despesasPaginadas);
        model.addAttribute("pago", despesaService.calculaValorPago(despesasSemPaginacao));
        model.addAttribute("pendente", despesaService.calculaValorPendente(despesasSemPaginacao));
        model.addAttribute("pagar", despesaService.pendentesHoje());

        modelAndView.setViewName("despesas");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView novaDespesa(DespesaDTO despesa, ModelAndView modelAndView, RedirectAttributes redirAttrs) {

        String criaDespesa = despesaService.encaminhaParaCriacaoDoCrudService(despesa);

        if (criaDespesa == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro da despesa salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", criaDespesa);

        modelAndView.setViewName("redirect:despesas");
        return modelAndView;
    }

    @PostMapping("/filtro")
    public ModelAndView filtraDespesa(FiltroDespesaDTO filtroDespesa, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:../" + despesaService.constroiUriFiltro(filtroDespesa));
        return modelAndView;
    }
}
