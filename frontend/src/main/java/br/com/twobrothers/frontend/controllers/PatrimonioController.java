package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.PatrimonioDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroPatrimonioDTO;
import br.com.twobrothers.frontend.models.entities.PatrimonioEntity;
import br.com.twobrothers.frontend.models.enums.PrivilegioEnum;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.PatrimonioCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.PatrimonioService;
import br.com.twobrothers.frontend.services.exporter.PatrimonioPdfExporter;
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
import java.util.*;

import static br.com.twobrothers.frontend.utils.ConversorDeDados.converteValorDoubleParaValorMonetario;

@Controller
@RequestMapping("/patrimonios")
public class PatrimonioController {

    @Autowired
    PatrimonioService patrimonioService;

    @Autowired
    PatrimonioCrudService patrimonioCrudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public ModelAndView patrimonios(@PageableDefault(size = 10, page = 0, sort = {"statusPatrimonio"}, direction = Sort.Direction.DESC) Pageable pageable,
                                    @RequestParam("descricao") Optional<String> descricao,
                                    @RequestParam("mes") Optional<String> mes,
                                    @RequestParam("ano") Optional<String> ano,
                                    @RequestParam("tipo") Optional<String> tipo,
                                    Model model, ModelAndView modelAndView,
                                    RedirectAttributes redirAttrs,
                                    ModelMap modelMap,
                                    HttpServletRequest req) {

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/patrimonios?" + req.getQueryString();

        modelMap.addAttribute("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        modelMap.addAttribute("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        modelMap.addAttribute("baseUrl", baseUrl);
        modelMap.addAttribute("queryString", req.getQueryString());
        modelMap.addAttribute("completeUrl", completeUrl);

        List<PatrimonioEntity> patrimoniosPaginados = new ArrayList<>();
        List<PatrimonioEntity> patrimoniosSemPaginacao = new ArrayList<>();
        List<Integer> paginas = new ArrayList<>();

        try {
            patrimoniosPaginados = patrimonioService.filtroPatrimonios(
                    pageable,
                    descricao.orElse(null),
                    tipo.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null));

            patrimoniosSemPaginacao = patrimonioService.filtroPatrimoniosSemPaginacao(
                    descricao.orElse(null),
                    tipo.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null));

            paginas = patrimonioService.calculaQuantidadePaginas(patrimoniosSemPaginacao, pageable);

        } catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:patrimonios");
            return modelAndView;
        }

        model.addAttribute("tipoFiltro", "mesAtual");

        if (mes.isPresent() && ano.isPresent()) model.addAttribute("tipoFiltro", "periodo");
        if (descricao.isPresent()) model.addAttribute("tipoFiltro", "descricao");
        if (tipo.isPresent()) model.addAttribute("tipoFiltro", "tipo");

        model.addAttribute("descricao", descricao.orElse(null));
        model.addAttribute("mes", mes.orElse(null));
        model.addAttribute("ano", ano.orElse(null));
        model.addAttribute("tipo", tipo.orElse(null));
        model.addAttribute("paginas", paginas);
        model.addAttribute("pagina", pageable.getPageNumber());
        model.addAttribute("patrimonios", patrimoniosPaginados);
        model.addAttribute("bruto", converteValorDoubleParaValorMonetario(patrimonioService.calculaValorBruto(patrimoniosSemPaginacao)));
        model.addAttribute("pendente", converteValorDoubleParaValorMonetario(patrimonioService.calculaValorPendente(patrimoniosSemPaginacao)));
        model.addAttribute("passivos", converteValorDoubleParaValorMonetario(patrimonioService.calculaValorPassivos(patrimoniosSemPaginacao)));
        model.addAttribute("caixa", converteValorDoubleParaValorMonetario(patrimonioService.calculaValorCaixa(patrimoniosSemPaginacao)));

        if(!UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().equals(PrivilegioEnum.VENDEDOR)) {
            modelAndView.setViewName("patrimonio");
        }
        else {
            modelAndView.setViewName("redirect:/");
            redirAttrs.addFlashAttribute("ErroCadastro",
                    "Você não possui o privilégio necessário para acessar a página de gestão patrimonial");
        }

        return modelAndView;

    }

    @GetMapping("/patrimonio-carga")
    public ModelAndView patrimonioCarga(ModelAndView modelAndView,
                                        RedirectAttributes redirAttrs) {
        try {
            patrimonioService.cargaDePatrimonio();
            redirAttrs.addFlashAttribute("SucessoCadastro", "A carga de patrimônios foi realizada com sucesso");
            modelAndView.setViewName("redirect:../patrimonios");
            return modelAndView;
        }
        catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroCadastro", e.getMessage());
            modelAndView.setViewName("redirect:../patrimonios");
            return modelAndView;
        }
    }

    @PostMapping
    public ModelAndView novoPatrimonio(PatrimonioDTO patrimonio,
                                       String query,
                                       ModelAndView modelAndView,
                                       RedirectAttributes redirAttrs) {

        String criaPatrimonio = patrimonioService.encaminhaParaCriacaoDoCrudService(patrimonio);

        if (criaPatrimonio == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro do patrimônio salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", criaPatrimonio);

        modelAndView.setViewName("redirect:patrimonios?" + query);
        return modelAndView;
    }

    @PostMapping("/filtro")
    public ModelAndView filtraPatrimonio(FiltroPatrimonioDTO filtroPatrimonio, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:../" + patrimonioService.constroiUriFiltro(filtroPatrimonio));
        return modelAndView;
    }

    @PostMapping("/deleta-{id}")
    public ModelAndView deletaPatrimonio(@PathVariable Long id,
                                         RedirectAttributes redirAttrs,
                                         ModelAndView modelAndView,
                                         String query) {
        patrimonioCrudService.deletaPorId(id);
        redirAttrs.addFlashAttribute("SucessoDelete", "Patrimônio deletado com sucesso");
        modelAndView.setViewName("redirect:../patrimonios?" + query);
        return modelAndView;
    }

    @PostMapping("/alterar")
    public ModelAndView atualizaPatrimonio(PatrimonioDTO patrimonio,
                                           RedirectAttributes redirAttrs,
                                           ModelAndView modelAndView,
                                           String query) {

        String atualizaPatrimonio = patrimonioService.encaminhaParaUpdateDoCrudService(patrimonio);

        if (atualizaPatrimonio == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro do patrimônio salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", atualizaPatrimonio);

        modelAndView.setViewName("redirect:../patrimonios?" + query);
        return modelAndView;
    }

    @GetMapping("/relatorio")
    public void relatorio(ModelAndView modelAndView,
                          @RequestParam("descricao") Optional<String> descricao,
                          @RequestParam("mes") Optional<String> mes,
                          @RequestParam("ano") Optional<String> ano,
                          @RequestParam("tipo") Optional<String> tipo,
                          HttpServletResponse res) throws DocumentException, IOException {

        List<PatrimonioEntity> patrimonios = patrimonioService.filtroPatrimoniosSemPaginacao(
                descricao.orElse(null),
                tipo.orElse(null),
                mes.orElse(null),
                ano.orElse(null));

        HashMap<String, String> filtros = new HashMap<>();

        filtros.put("descricao", descricao.orElse(null));
        filtros.put("tipo", tipo.orElse(null));
        filtros.put("mes", mes.orElse(null));
        filtros.put("ano", ano.orElse(null));

        res.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachement; filename=akadia_patrimonios_"
                + new SimpleDateFormat("dd.MM.yyyy_HHmmss").format(new Date())
                + ".pdf";
        res.setHeader(headerKey, headerValue);
        PatrimonioPdfExporter pdfExporterService = new PatrimonioPdfExporter();
        pdfExporterService.export(res, patrimonios, patrimonioService, filtros, usuarioRepository);

    }


}
