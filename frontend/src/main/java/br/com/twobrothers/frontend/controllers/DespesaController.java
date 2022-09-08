package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.services.DespesaService;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
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
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Controller
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    DespesaService despesaService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Bean
    public Function<String, String> currentUrlWithoutParam() {
        return param ->   ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(param).toUriString();
    }

    @GetMapping
    public ModelAndView despesas(Pageable pageable, //TODO Tamanho da página deve ser calculado em JS e repassado pelo parâmetro
                                 @RequestParam("description")Optional<String> description,
                                 @RequestParam("range-inicio")Optional<String> inicio,
                                 @RequestParam("range-fim")Optional<String> fim,
                                 Model model, ModelAndView modelAndView,
                                 RedirectAttributes redirAttrs,
                                 ModelMap modelMap,
                                 HttpServletRequest req) {

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();

        modelMap.addAttribute("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        modelMap.addAttribute("baseUrl", baseUrl);

        model.addAttribute("despesas", despesaService.filtrandoDespesas(pageable));

        modelAndView.setViewName("despesas");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView novaDespesa(DespesaDTO despesa, ModelAndView modelAndView) {
        despesaService.tratamentoDeNovaDespesa(despesa);
        modelAndView.setViewName("despesas");
        return modelAndView;
    }
}
