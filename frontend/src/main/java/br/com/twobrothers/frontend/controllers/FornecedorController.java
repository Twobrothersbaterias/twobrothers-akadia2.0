package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.FornecedorDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroFornecedorDTO;
import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
import br.com.twobrothers.frontend.models.enums.PrivilegioEnum;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.FornecedorCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.FornecedorService;
import br.com.twobrothers.frontend.services.exporter.FornecedorPdfExporter;
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
@RequestMapping("/fornecedores")
public class FornecedorController {
    @Autowired
    FornecedorService fornecedorService;

    @Autowired
    FornecedorCrudService fornecedorCrudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public ModelAndView fornecedores(@PageableDefault(size = 10, page = 0, sort = {"dataCadastro"}, direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam("descricao") Optional<String> descricao,
                                 @RequestParam("inicio") Optional<String> inicio,
                                 @RequestParam("fim") Optional<String> fim,
                                 @RequestParam("mes") Optional<String> mes,
                                 @RequestParam("ano") Optional<String> ano,
                                 @RequestParam("cpfCnpj") Optional<String> cpfCnpj,
                                 @RequestParam("telefone") Optional<String> telefone,
                                 Model model, ModelAndView modelAndView,
                                 RedirectAttributes redirAttrs,
                                 ModelMap modelMap,
                                 HttpServletRequest req) {

        try {
            fornecedorService.modelMapBuilder(modelMap, pageable, req, descricao.orElse(null), inicio.orElse(null), fim.orElse(null),
                    mes.orElse(null), ano.orElse(null), cpfCnpj.orElse(null), telefone.orElse(null));

            if(!UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().equals(PrivilegioEnum.VENDEDOR)) {
                modelAndView.setViewName("fornecedores");
            }
            else {
                modelAndView.setViewName("redirect:/");
                redirAttrs.addFlashAttribute("ErroCadastro",
                        "Você não possui o privilégio necessário para acessar a página de fornecedores");
            }
        } catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:fornecedores");
        }
        return modelAndView;

    }

    @PostMapping
    public ModelAndView novoFornecedor(FornecedorDTO fornecedor,
                                       String query,
                                       ModelAndView modelAndView,
                                       RedirectAttributes redirAttrs) {

        String criaFornecedor = fornecedorService.encaminhaParaCriacaoDoCrudService(fornecedor);

        if (criaFornecedor == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro do fornecedor salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", criaFornecedor);

        modelAndView.setViewName("redirect:fornecedores?" + query);
        return modelAndView;
    }

    @PostMapping("/filtro")
    public ModelAndView filtraFornecedor(FiltroFornecedorDTO filtroFornecedor, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:../" + fornecedorService.constroiUriFiltro(filtroFornecedor));
        return modelAndView;
    }

    @PostMapping("/deleta-{id}")
    public ModelAndView deletaFornecedor(@PathVariable Long id,
                                      RedirectAttributes redirAttrs,
                                      ModelAndView modelAndView,
                                      String query) {
        fornecedorCrudService.deletaPorId(id);
        redirAttrs.addFlashAttribute("SucessoDelete", "Fornecedor deletado com sucesso");
        modelAndView.setViewName("redirect:../fornecedores?" + query);
        return modelAndView;
    }

    @PostMapping("/alterar")
    public ModelAndView atualizaFornecedor(FornecedorDTO fornecedor,
                                        RedirectAttributes redirAttrs,
                                        ModelAndView modelAndView,
                                        String query) {

        String atualizaFornecedor = fornecedorService.encaminhaParaUpdateDoCrudService(fornecedor);

        if (atualizaFornecedor == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro do fornecedor salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", atualizaFornecedor);

        modelAndView.setViewName("redirect:../fornecedores?" + query);
        return modelAndView;
    }

    @GetMapping("/relatorio")
    public void relatorio(ModelAndView modelAndView,
                          @RequestParam("descricao") Optional<String> descricao,
                          @RequestParam("inicio") Optional<String> inicio,
                          @RequestParam("fim") Optional<String> fim,
                          @RequestParam("mes") Optional<String> mes,
                          @RequestParam("ano") Optional<String> ano,
                          @RequestParam("cpfCnpj") Optional<String> cpfCnpj,
                          @RequestParam("telefone") Optional<String> telefone,
                          HttpServletResponse res) throws DocumentException, IOException {

        List<FornecedorEntity> fornecedores = fornecedorService.filtroFornecedoresSemPaginacao(
                descricao.orElse(null),
                inicio.orElse(null),
                fim.orElse(null),
                mes.orElse(null),
                ano.orElse(null),
                cpfCnpj.orElse(null),
                telefone.orElse(null));

        HashMap<String, String> filtros = new HashMap<>();
        filtros.put("inicio", inicio.orElse(null));
        filtros.put("fim", fim.orElse(null));
        filtros.put("mes", mes.orElse(null));
        filtros.put("ano", ano.orElse(null));
        filtros.put("descricao", descricao.orElse(null));
        filtros.put("cpfCnpj", cpfCnpj.orElse(null));
        filtros.put("telefone", telefone.orElse(null));

        res.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachement; filename=akadia_fornecedores_"
                + new SimpleDateFormat("dd.MM.yyyy_HHmmss").format(new Date())
                + ".pdf";
        res.setHeader(headerKey, headerValue);
        FornecedorPdfExporter pdfExporterService = new FornecedorPdfExporter();
        pdfExporterService.export(res, fornecedores, fornecedorService, filtros, usuarioRepository);

    }


}

