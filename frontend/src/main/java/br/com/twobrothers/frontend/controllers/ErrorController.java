package br.com.twobrothers.frontend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping
    public ModelAndView error(ModelAndView modelAndView) {
        modelAndView.setViewName("error");
        return modelAndView;
    }

}
