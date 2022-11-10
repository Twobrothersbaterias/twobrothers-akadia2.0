package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.ClienteDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroClienteDTO;
import br.com.twobrothers.frontend.models.entities.ClienteEntity;
import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.ClienteCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.ClienteService;
import br.com.twobrothers.frontend.services.exporter.ClientePdfExporter;
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
import java.util.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    ClienteCrudService clienteCrudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public ModelAndView clientes(@PageableDefault(size = 10, page = 0, sort = {"dataCadastro"}, direction = Sort.Direction.DESC) Pageable pageable,
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

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/clientes?" + req.getQueryString();

        modelMap.addAttribute("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        modelMap.addAttribute("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        modelMap.addAttribute("baseUrl", baseUrl);
        modelMap.addAttribute("queryString", req.getQueryString());
        modelMap.addAttribute("completeUrl", completeUrl);

        List<ClienteEntity> clientesPaginados = new ArrayList<>();
        List<ClienteEntity> clientesSemPaginacao = new ArrayList<>();
        List<Integer> paginas = new ArrayList<>();

        try {
            clientesPaginados = clienteService.filtroClientes(
                    pageable,
                    descricao.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null),
                    cpfCnpj.orElse(null),
                    telefone.orElse(null));

            clientesSemPaginacao = clienteService.filtroClientesSemPaginacao(
                    descricao.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null),
                    cpfCnpj.orElse(null),
                    telefone.orElse(null));

            paginas = clienteService.calculaQuantidadePaginas(clientesSemPaginacao, pageable);

        } catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:clientes");
            return modelAndView;
        }

        model.addAttribute("tipoFiltro", "hoje");

        if (inicio.isPresent() && fim.isPresent()) model.addAttribute("tipoFiltro", "data");
        if (mes.isPresent() && ano.isPresent()) model.addAttribute("tipoFiltro", "periodo");
        if (descricao.isPresent()) model.addAttribute("tipoFiltro", "descricao");
        if (cpfCnpj.isPresent()) model.addAttribute("tipoFiltro", "cpfCnpj");
        if (telefone.isPresent()) model.addAttribute("tipoFiltro", "telefone");

        model.addAttribute("totalItens", clientesSemPaginacao.size());
        model.addAttribute("descricao", descricao.orElse(null));
        model.addAttribute("dataInicio", inicio.orElse(null));
        model.addAttribute("dataFim", fim.orElse(null));
        model.addAttribute("inicio", fim.orElse(null));
        model.addAttribute("fim", fim.orElse(null));
        model.addAttribute("mes", mes.orElse(null));
        model.addAttribute("ano", ano.orElse(null));
        model.addAttribute("cpfCnpj", cpfCnpj.orElse(null));
        model.addAttribute("telefone", telefone.orElse(null));
        model.addAttribute("paginas", paginas);
        model.addAttribute("pagina", pageable.getPageNumber());
        model.addAttribute("clientes", clientesPaginados);

        modelAndView.setViewName("clientes");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView novoCliente(ClienteDTO cliente,
                                    String query,
                                    ModelAndView modelAndView,
                                    RedirectAttributes redirAttrs) {

        String criaCliente = clienteService.encaminhaParaCriacaoDoCrudService(cliente);

        if (criaCliente == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro do cliente salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", criaCliente);

        modelAndView.setViewName("redirect:clientes?" + query);
        return modelAndView;
    }

    @PostMapping("/filtro")
    public ModelAndView filtraCliente(FiltroClienteDTO filtroCliente, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:../" + clienteService.constroiUriFiltro(filtroCliente));
        return modelAndView;
    }

    @PostMapping("/deleta-{id}")
    public ModelAndView deletaCliente(@PathVariable Long id,
                                      RedirectAttributes redirAttrs,
                                      ModelAndView modelAndView,
                                      String query) {
        clienteCrudService.deletaPorId(id);
        redirAttrs.addFlashAttribute("SucessoDelete", "Cliente deletado com sucesso");
        modelAndView.setViewName("redirect:../clientes?" + query);
        return modelAndView;
    }

    @PostMapping("/alterar")
    public ModelAndView atualizaCliente(ClienteDTO cliente,
                                        RedirectAttributes redirAttrs,
                                        ModelAndView modelAndView,
                                        String query) {

        String atualizaCliente = clienteService.encaminhaParaUpdateDoCrudService(cliente);

        if (atualizaCliente == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro do cliente salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", atualizaCliente);

        modelAndView.setViewName("redirect:../clientes?" + query);
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

        List<ClienteEntity> clientes = clienteService.filtroClientesSemPaginacao(
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
        String headerValue = "attachement; filename=akadia_clientes_"
                + new SimpleDateFormat("dd.MM.yyyy_HHmmss").format(new Date())
                + ".pdf";
        res.setHeader(headerKey, headerValue);
        ClientePdfExporter pdfExporterService = new ClientePdfExporter();
        pdfExporterService.export(res, clientes, clienteService, filtros, usuarioRepository);

    }

}

