package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import br.com.twobrothers.frontend.services.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    DespesaService despesaService;

    @GetMapping
    public ModelAndView despesas() {
        ModelAndView modelAndView = new ModelAndView();
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
