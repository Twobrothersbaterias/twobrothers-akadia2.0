package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroDespesaDTO;
import br.com.twobrothers.frontend.models.entities.DespesaEntity;
import br.com.twobrothers.frontend.models.enums.PrivilegioEnum;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.DespesaCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.DespesaService;
import br.com.twobrothers.frontend.services.exporter.DespesaPdfExporter;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import com.lowagie.text.DocumentException;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
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
                                 @RequestParam("mes") Optional<String> mes,
                                 @RequestParam("ano") Optional<String> ano,
                                 @RequestParam("tipo") Optional<String> tipo,
                                 Model model, ModelAndView modelAndView,
                                 RedirectAttributes redirAttrs,
                                 ModelMap modelMap,
                                 HttpServletRequest req) {

        try {
            despesaService.modelMapBuilder(modelMap, pageable, req, descricao.orElse(null), tipo.orElse(null),
                    inicio.orElse(null), fim.orElse(null), mes.orElse(null), ano.orElse(null));
            if(!UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().equals(PrivilegioEnum.VENDEDOR))
                modelAndView.setViewName("despesas");
            else {
                modelAndView.setViewName("redirect:/");
                redirAttrs.addFlashAttribute("ErroCadastro",
                        "Você não possui o privilégio necessário para acessar a página de despesas");
            }
            return modelAndView;
        }
        catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:despesas");
            return modelAndView;
        }

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

    @GetMapping("/relatorio")
    public void relatorio(ModelAndView modelAndView,
                          @RequestParam("descricao") Optional<String> descricao,
                          @RequestParam("inicio") Optional<String> inicio,
                          @RequestParam("fim") Optional<String> fim,
                          @RequestParam("mes") Optional<String> mes,
                          @RequestParam("ano") Optional<String> ano,
                          @RequestParam("tipo") Optional<String> tipo,
                          HttpServletResponse res) throws DocumentException, IOException {

        List<DespesaEntity> despesas = despesaService.filtroDespesasSemPaginacao(
                descricao.orElse(null),
                tipo.orElse(null),
                inicio.orElse(null),
                fim.orElse(null),
                mes.orElse(null),
                ano.orElse(null));

        HashMap<String, String> filtros = new HashMap<>();
        filtros.put("descricao", descricao.orElse(null));
        filtros.put("tipo", tipo.orElse(null));
        filtros.put("inicio", inicio.orElse(null));
        filtros.put("fim", fim.orElse(null));
        filtros.put("mes", mes.orElse(null));
        filtros.put("ano", ano.orElse(null));

        res.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachement; filename=akadia_despesas_"
                + new SimpleDateFormat("dd.MM.yyyy_HHmmss").format(new Date())
                + ".pdf";
        res.setHeader(headerKey, headerValue);
        DespesaPdfExporter pdfExporterService = new DespesaPdfExporter();
        pdfExporterService.export(res, despesas, despesaService, filtros, usuarioRepository);

    }


}
