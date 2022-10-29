package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class IndexController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public ModelAndView telaPrincipal(ModelAndView modelAndView,
                                      ModelMap modelMap) {

        modelMap.addAttribute("privilegio",
                UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());

        modelAndView.setViewName("index");
        return modelAndView;
    }

}
