package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import br.com.twobrothers.frontend.proxys.DespesaServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    DespesaServiceProxy proxy;

    @GetMapping
    public ModelAndView despesas() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("despesas");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView novaDespesa(@RequestBody DespesaDTO despesa, ModelAndView modelAndView) {

        System.err.println(despesa);
        DespesaDTO despesaCriada = proxy.novaDespesa(despesa).getBody();
        System.err.println(despesaCriada);

        modelAndView.setViewName("despesas");
        return modelAndView;
    }
}
