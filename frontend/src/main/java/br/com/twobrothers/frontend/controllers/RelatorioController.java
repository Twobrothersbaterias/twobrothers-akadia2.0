package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.enums.PrivilegioEnum;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.RelatorioService;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {
    @Autowired
    RelatorioService relatorioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public ModelAndView relatorio(@PageableDefault(size = 20, page = 0, sort = {"dataCadastro"}, direction = Sort.Direction.DESC) Pageable pageable,
                                  ModelAndView modelAndView,
                                  RedirectAttributes redirAttrs,
                                  @RequestParam("mes") Optional<String> mes,
                                  @RequestParam("ano") Optional<String> ano,
                                  ModelMap modelMap,
                                  HttpServletRequest req) {

        try {
            relatorioService.modelMapBuilder(modelMap);

            if (!UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().equals(PrivilegioEnum.VENDEDOR)) {
                modelAndView.setViewName("relatorios");
            } else {
                modelAndView.setViewName("redirect:/");
                redirAttrs.addFlashAttribute("ErroCadastro",
                        "Você não possui o privilégio necessário para acessar a página de relatórios");
            }
        } catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:relatorios");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return modelAndView;

    }
}