package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.UsuarioDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroUsuarioDTO;
import br.com.twobrothers.frontend.models.entities.UsuarioEntity;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.UsuarioCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.UsuarioService;
import br.com.twobrothers.frontend.services.exporter.ColaboradorPdfExporter;
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
@RequestMapping("/colaboradores")
public class ColaboradoresController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioCrudService usuarioCrudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public ModelAndView usuarios(@PageableDefault(size = 10, page = 0, sort = {"dataCadastro"}, direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam("descricao") Optional<String> descricao,
                                 @RequestParam("inicio") Optional<String> inicio,
                                 @RequestParam("fim") Optional<String> fim,
                                 @RequestParam("mes") Optional<String> mes,
                                 @RequestParam("ano") Optional<String> ano,
                                 @RequestParam("usuario") Optional<String> usuario,
                                 Model model, ModelAndView modelAndView,
                                 RedirectAttributes redirAttrs,
                                 ModelMap modelMap,
                                 HttpServletRequest req) {

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/colaboradores?" + req.getQueryString();

        modelMap.addAttribute("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        modelMap.addAttribute("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        modelMap.addAttribute("baseUrl", baseUrl);
        modelMap.addAttribute("queryString", req.getQueryString());
        modelMap.addAttribute("completeUrl", completeUrl);

        List<UsuarioEntity> usuariosPaginados = new ArrayList<>();
        List<UsuarioEntity> usuariosSemPaginacao = new ArrayList<>();
        List<Integer> paginas = new ArrayList<>();

        try {
            usuariosPaginados = usuarioService.filtroUsuarios(
                    pageable,
                    descricao.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null),
                    usuario.orElse(null));

            usuariosSemPaginacao = usuarioService.filtroUsuariosSemPaginacao(
                    descricao.orElse(null),
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null),
                    usuario.orElse(null));

            paginas = usuarioService.calculaQuantidadePaginas(usuariosSemPaginacao, pageable);

        } catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:usuarios");
            return modelAndView;
        }

        model.addAttribute("tipoFiltro", "hoje");

        if (inicio.isPresent() && fim.isPresent()) model.addAttribute("tipoFiltro", "data");
        if (mes.isPresent() && ano.isPresent()) model.addAttribute("tipoFiltro", "periodo");
        if (descricao.isPresent()) model.addAttribute("tipoFiltro", "descricao");
        if (usuario.isPresent()) model.addAttribute("tipoFiltro", "usuario");

        model.addAttribute("totalItens", usuariosSemPaginacao.size());
        model.addAttribute("descricao", descricao.orElse(null));
        model.addAttribute("dataInicio", inicio.orElse(null));
        model.addAttribute("dataFim", fim.orElse(null));
        model.addAttribute("mes", mes.orElse(null));
        model.addAttribute("ano", ano.orElse(null));
        model.addAttribute("usuario", usuario.orElse(null));
        model.addAttribute("paginas", paginas);
        model.addAttribute("pagina", pageable.getPageNumber());
        model.addAttribute("usuarios", usuariosPaginados);

        modelAndView.setViewName("colaboradores");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView novoUsuario(UsuarioDTO usuario,
                                    String query,
                                    ModelAndView modelAndView,
                                    RedirectAttributes redirAttrs) {

        String criaUsuario = usuarioService.encaminhaParaCriacaoDoCrudService(usuario);

        if (criaUsuario == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro do usuario salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", criaUsuario);

        modelAndView.setViewName("redirect:colaboradores?" + query);
        return modelAndView;
    }

    @PostMapping("/filtro")
    public ModelAndView filtraUsuario(FiltroUsuarioDTO filtroUsuario, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:../" + usuarioService.constroiUriFiltro(filtroUsuario));
        return modelAndView;
    }

    @PostMapping("/deleta-{id}")
    public ModelAndView deletaUsuario(@PathVariable Long id,
                                      RedirectAttributes redirAttrs,
                                      ModelAndView modelAndView,
                                      String query) {
        usuarioCrudService.deletaPorId(id);
        redirAttrs.addFlashAttribute("SucessoDelete", "Usuário deletado com sucesso");
        modelAndView.setViewName("redirect:../colaboradores?" + query);
        return modelAndView;
    }

    @PostMapping("/alterar")
    public ModelAndView atualizaUsuario(UsuarioDTO usuario,
                                        RedirectAttributes redirAttrs,
                                        ModelAndView modelAndView,
                                        String query) {

        String atualizaUsuario = usuarioService.encaminhaParaUpdateDoCrudService(usuario);

        if (atualizaUsuario == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro do usuario salvo com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", atualizaUsuario);

        modelAndView.setViewName("redirect:../colaboradores?" + query);
        return modelAndView;
    }

    @GetMapping("/relatorio")
    public void relatorio(ModelAndView modelAndView,
                          @RequestParam("descricao") Optional<String> descricao,
                          @RequestParam("inicio") Optional<String> inicio,
                          @RequestParam("fim") Optional<String> fim,
                          @RequestParam("mes") Optional<String> mes,
                          @RequestParam("ano") Optional<String> ano,
                          @RequestParam("usuario") Optional<String> usuario,
                          HttpServletResponse res) throws DocumentException, IOException {

        List<UsuarioEntity> usuarios = usuarioService.filtroUsuariosSemPaginacao(
                descricao.orElse(null),
                inicio.orElse(null),
                fim.orElse(null),
                mes.orElse(null),
                ano.orElse(null),
                usuario.orElse(null));

        HashMap<String, String> filtros = new HashMap<>();
        filtros.put("descricao", descricao.orElse(null));
        filtros.put("inicio", inicio.orElse(null));
        filtros.put("fim", fim.orElse(null));
        filtros.put("mes", mes.orElse(null));
        filtros.put("ano", ano.orElse(null));
        filtros.put("usuario", usuario.orElse(null));

        res.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachement; filename=akadia_colaboradores_"
                + new SimpleDateFormat("dd.MM.yyyy_HHmmss").format(new Date())
                + ".pdf";
        res.setHeader(headerKey, headerValue);
        ColaboradorPdfExporter pdfExporterService = new ColaboradorPdfExporter();
        pdfExporterService.export(res, usuarios, usuarioService, filtros, usuarioRepository);

    }

}

