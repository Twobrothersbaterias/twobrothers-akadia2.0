package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.AbastecimentoDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroAbastecimentoDTO;
import br.com.twobrothers.frontend.models.entities.AbastecimentoEntity;
import br.com.twobrothers.frontend.models.enums.PrivilegioEnum;
import br.com.twobrothers.frontend.repositories.FornecedorRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.AbastecimentoCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.AbastecimentoService;
import br.com.twobrothers.frontend.services.exporter.AbastecimentoPdfExporter;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/compras")
public class AbastecimentoController {

    @Autowired
    AbastecimentoService abastecimentoService;

    @Autowired
    AbastecimentoCrudService abastecimentoCrudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @GetMapping
    public ModelAndView abastecimentos(@PageableDefault(size = 20, page = 0, sort = {"dataCadastro"}, direction = Sort.Direction.ASC) Pageable pageable,
                                       @RequestParam("inicio") Optional<String> inicio,
                                       @RequestParam("fim") Optional<String> fim,
                                       @RequestParam("mes") Optional<String> mes,
                                       @RequestParam("ano") Optional<String> ano,
                                       @RequestParam("fornecedorId") Optional<String> fornecedorId,
                                       @RequestParam("fornecedor") Optional<String> fornecedor,
                                       @RequestParam("produto") Optional<String> produto,
                                       @RequestParam("meio") Optional<String> meio,
                                       Model model, ModelAndView modelAndView,
                                       RedirectAttributes redirAttrs,
                                       ModelMap modelMap,
                                       HttpServletRequest req) {

        try {
            abastecimentoService.modelMapBuilder(modelMap, pageable, req, inicio.orElse(null), fim.orElse(null),
                    mes.orElse(null), ano.orElse(null), fornecedorId.orElse(null), fornecedor.orElse(null),
                    produto.orElse(null), meio.orElse(null));
            if(!UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().equals(PrivilegioEnum.VENDEDOR)) {
                modelAndView.setViewName("abastecimentos");
            }
            else {
                modelAndView.setViewName("redirect:/");
                redirAttrs.addFlashAttribute("ErroCadastro",
                        "Você não possui o privilégio necessário para acessar a página de abastecimentos");
            }
        }

        catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:abastecimentos");
        }

        return modelAndView;
    }

    @PostMapping
    public ModelAndView novoAbastecimento(AbastecimentoDTO abastecimento,
                                          String query,
                                          ModelAndView modelAndView,
                                          RedirectAttributes redirAttrs) {

        String criaAbastecimento = abastecimentoService.encaminhaParaCriacaoDoCrudService(abastecimento);

        if (criaAbastecimento == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro da compra realizado com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", criaAbastecimento);

        modelAndView.setViewName("redirect:compras?" + query);
        return modelAndView;
    }

    @PostMapping("/filtro")
    public ModelAndView filtraAbastecimento(FiltroAbastecimentoDTO filtroAbastecimento, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:../" + abastecimentoService.constroiUriFiltro(filtroAbastecimento));
        return modelAndView;
    }

    @PostMapping("/deleta-{id}")
    public ModelAndView deletaAbastecimento(@PathVariable Long id,
                                            RedirectAttributes redirAttrs,
                                            ModelAndView modelAndView,
                                            String query) {
        abastecimentoCrudService.deletaPorId(id);
        redirAttrs.addFlashAttribute("SucessoDelete", "Compra removida com sucesso");
        modelAndView.setViewName("redirect:../compras?" + query);
        return modelAndView;
    }

    @PostMapping("/alterar")
    public ModelAndView atualizaAbastecimento(AbastecimentoDTO abastecimento,
                                              RedirectAttributes redirAttrs,
                                              ModelAndView modelAndView,
                                              String query) {

        String atualizaAbastecimento = abastecimentoService.encaminhaParaUpdateDoCrudService(abastecimento);

        if (atualizaAbastecimento == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Atualização da compra realizada com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", atualizaAbastecimento);

        modelAndView.setViewName("redirect:../compras?" + query);
        return modelAndView;
    }

    @GetMapping("/relatorio")
    public void relatorio(ModelAndView modelAndView,
                          @RequestParam("inicio") Optional<String> inicio,
                          @RequestParam("fim") Optional<String> fim,
                          @RequestParam("mes") Optional<String> mes,
                          @RequestParam("ano") Optional<String> ano,
                          @RequestParam("fornecedorId") Optional<String> fornecedorId,
                          @RequestParam("fornecedor") Optional<String> fornecedor,
                          @RequestParam("produto") Optional<String> produto,
                          @RequestParam("meio") Optional<String> meio,
                          HttpServletResponse res) throws DocumentException, IOException {

        List<AbastecimentoEntity> abastecimentos = abastecimentoService.filtroAbastecimentosSemPaginacao(
                inicio.orElse(null),
                fim.orElse(null),
                mes.orElse(null),
                ano.orElse(null),
                fornecedorId.orElse(null),
                fornecedor.orElse(null),
                produto.orElse(null),
                meio.orElse(null));

        HashMap<String, String> filtros = new HashMap<>();
        filtros.put("inicio", inicio.orElse(null));
        filtros.put("fim", fim.orElse(null));
        filtros.put("mes", mes.orElse(null));
        filtros.put("ano", ano.orElse(null));
        filtros.put("fornecedorId", fornecedorId.orElse(null));
        filtros.put("fornecedor", fornecedor.orElse(null));
        filtros.put("produto", produto.orElse(null));
        filtros.put("meio", meio.orElse(null));

        res.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachement; filename=akadia_compras_"
                + new SimpleDateFormat("dd.MM.yyyy_HHmmss").format(new Date())
                + ".pdf";
        res.setHeader(headerKey, headerValue);
        AbastecimentoPdfExporter pdfExporterService = new AbastecimentoPdfExporter();
        pdfExporterService.export(res, abastecimentos, abastecimentoService, filtros, usuarioRepository, fornecedorRepository);

    }

}
