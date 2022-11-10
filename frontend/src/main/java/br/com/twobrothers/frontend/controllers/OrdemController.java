package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.filters.FiltroOrdemDTO;
import br.com.twobrothers.frontend.models.entities.OrdemEntity;
import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.repositories.OrdemRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.OrdemCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.OrdemService;
import br.com.twobrothers.frontend.services.exporter.EstoquePdfExporter;
import br.com.twobrothers.frontend.services.exporter.VendaPdfExporter;
import br.com.twobrothers.frontend.utils.ConversorDeDados;
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

@Controller
@RequestMapping("/vendas")
public class OrdemController {

    @Autowired
    OrdemService ordemService;

    @Autowired
    OrdemCrudService ordemCrudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    OrdemRepository ordemRepository;

    @GetMapping
    public ModelAndView ordens(@PageableDefault(size = 10, page = 0,
            sort = {"retirada.dataAgendamento"}, direction = Sort.Direction.ASC) Pageable pageable,
                               @RequestParam("inicio") Optional<String> inicio,
                               @RequestParam("fim") Optional<String> fim,
                               @RequestParam("mes") Optional<String> mes,
                               @RequestParam("ano") Optional<String> ano,
                               @RequestParam("produto") Optional<String> produto,
                               @RequestParam("bairro") Optional<String> bairro,
                               @RequestParam("cliente") Optional<String> cliente,
                               Model model, ModelAndView modelAndView,
                               RedirectAttributes redirAttrs,
                               ModelMap modelMap,
                               HttpServletRequest req) {

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/vendas?" + req.getQueryString();

        modelMap.addAttribute("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        modelMap.addAttribute("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        modelMap.addAttribute("baseUrl", baseUrl);
        modelMap.addAttribute("queryString", req.getQueryString());
        modelMap.addAttribute("completeUrl", completeUrl);

        List<OrdemEntity> ordensPaginadas = new ArrayList<>();
        List<OrdemEntity> ordensSemPaginacao = new ArrayList<>();
        List<Integer> paginas = new ArrayList<>();

        try {
            ordensPaginadas = ordemService.filtroOrdens(
                    pageable,
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null),
                    produto.orElse(null),
                    bairro.orElse(null),
                    cliente.orElse(null));

            ordensSemPaginacao = ordemService.filtroOrdensSemPaginacao(
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null),
                    produto.orElse(null),
                    bairro.orElse(null),
                    cliente.orElse(null));

            paginas = ordemService.calculaQuantidadePaginas(ordensSemPaginacao, pageable);

        } catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:vendas");
            return modelAndView;
        }

        model.addAttribute("tipoFiltro", "hoje");

        if (inicio.isPresent() && fim.isPresent()) model.addAttribute("tipoFiltro", "data");
        if (mes.isPresent() && ano.isPresent()) model.addAttribute("tipoFiltro", "periodo");
        if (produto.isPresent()) model.addAttribute("tipoFiltro", "produto");
        if (bairro.isPresent()) model.addAttribute("tipoFiltro", "bairro");

        model.addAttribute("totalItens", ordensSemPaginacao.size());
        model.addAttribute("dataInicio", inicio.orElse(null));
        model.addAttribute("dataFim", fim.orElse(null));
        model.addAttribute("mes", mes.orElse(null));
        model.addAttribute("ano", ano.orElse(null));
        model.addAttribute("produto", produto.orElse(null));
        model.addAttribute("bairro", bairro.orElse(null));

        model.addAttribute("paginas", paginas);
        model.addAttribute("pagina", pageable.getPageNumber());
        model.addAttribute("quantidadeVendida", ordemService.calculaQuantidadeVendida(ordensSemPaginacao));
        model.addAttribute("bruto",
                ConversorDeDados.converteValorDoubleParaValorMonetario(ordemService.calculaBrutoVendido(ordensSemPaginacao)));
        model.addAttribute("custo",
                ConversorDeDados.converteValorDoubleParaValorMonetario(ordemService.calculaCustoVenda(ordensSemPaginacao)));
        model.addAttribute("liquido",
                ConversorDeDados.converteValorDoubleParaValorMonetario(
                        (ordemService.calculaBrutoVendido(ordensSemPaginacao) - ordemService.calculaCustoVenda(ordensSemPaginacao))));
        model.addAttribute("ordens", ordensPaginadas);

        modelAndView.setViewName("vendas");
        return modelAndView;
    }

    @PostMapping("/deleta-{id}")
    public ModelAndView deletaOrdem(@PathVariable Long id,
                                    RedirectAttributes redirAttrs,
                                    ModelAndView modelAndView,
                                    String query) {
        ordemCrudService.deletaPorId(id);
        redirAttrs.addFlashAttribute("SucessoDelete", "Ordem deletada com sucesso");
        modelAndView.setViewName("redirect:../vendas?" + query);
        return modelAndView;
    }

    @PostMapping("/filtro")
    public ModelAndView filtraOrdem(FiltroOrdemDTO filtroOrdem, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:../" + ordemService.constroiUriFiltro(filtroOrdem));
        return modelAndView;
    }

    @GetMapping("/relatorio")
    public void relatorio(ModelAndView modelAndView,
                          @RequestParam("inicio") Optional<String> inicio,
                          @RequestParam("fim") Optional<String> fim,
                          @RequestParam("mes") Optional<String> mes,
                          @RequestParam("ano") Optional<String> ano,
                          @RequestParam("produto") Optional<String> produto,
                          @RequestParam("bairro") Optional<String> bairro,
                          @RequestParam("cliente") Optional<String> cliente,
                          HttpServletResponse res) throws DocumentException, IOException {

        List<OrdemEntity> ordens = ordemService.filtroOrdensSemPaginacao(
                inicio.orElse(null),
                fim.orElse(null),
                mes.orElse(null),
                ano.orElse(null),
                produto.orElse(null),
                bairro.orElse(null),
                cliente.orElse(null));

        HashMap<String, String> filtros = new HashMap<>();
        filtros.put("inicio", inicio.orElse(null));
        filtros.put("fim", fim.orElse(null));
        filtros.put("mes", mes.orElse(null));
        filtros.put("ano", ano.orElse(null));
        filtros.put("produto", produto.orElse(null));
        filtros.put("bairro", bairro.orElse(null));
        filtros.put("cliente", cliente.orElse(null));

        res.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachement; filename=akadia_ordens_"
                + new SimpleDateFormat("dd.MM.yyyy_HHmmss").format(new Date())
                + ".pdf";
        res.setHeader(headerKey, headerValue);
        VendaPdfExporter pdfExporterService = new VendaPdfExporter();
        pdfExporterService.export(res, ordens, ordemService, filtros, usuarioRepository);

    }

}
