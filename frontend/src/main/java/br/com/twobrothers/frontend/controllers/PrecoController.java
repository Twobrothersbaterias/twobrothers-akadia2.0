package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.PrecoFornecedorDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroPrecoDTO;
import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
import br.com.twobrothers.frontend.models.entities.PrecoFornecedorEntity;
import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.models.entities.UsuarioEntity;
import br.com.twobrothers.frontend.models.enums.PrivilegioEnum;
import br.com.twobrothers.frontend.repositories.FornecedorRepository;
import br.com.twobrothers.frontend.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.PrecoFornecedorCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.FornecedorService;
import br.com.twobrothers.frontend.services.PrecoService;
import br.com.twobrothers.frontend.services.ProdutoEstoqueService;
import br.com.twobrothers.frontend.services.exporter.ColaboradorPdfExporter;
import br.com.twobrothers.frontend.services.exporter.PrecoPdfExporter;
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
@RequestMapping("/precos")
public class PrecoController {

    @Autowired
    PrecoService precoService;

    @Autowired
    PrecoFornecedorCrudService precoCrudService;

    @Autowired
    ProdutoEstoqueService produtoEstoqueService;

    @Autowired
    FornecedorService fornecedorService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ProdutoEstoqueRepository produtoEstoqueRepository;

    @GetMapping
    public ModelAndView abastecimentos(@PageableDefault(size = 10, page = 0, sort = {"dataCadastro"}, direction = Sort.Direction.ASC) Pageable pageable,
                                       @RequestParam("fornecedorId") Optional<String> fornecedorId,
                                       @RequestParam("fornecedor") Optional<String> fornecedor,
                                       @RequestParam("produtoId") Optional<String> produtoId,
                                       @RequestParam("produto") Optional<String> produto,
                                       Model model, ModelAndView modelAndView,
                                       RedirectAttributes redirAttrs,
                                       ModelMap modelMap,
                                       HttpServletRequest req) {

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/precos?" + req.getQueryString();

        modelMap.addAttribute("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        modelMap.addAttribute("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        modelMap.addAttribute("baseUrl", baseUrl);
        modelMap.addAttribute("queryString", req.getQueryString());
        modelMap.addAttribute("completeUrl", completeUrl);

        List<PrecoFornecedorEntity> precosPaginados = new ArrayList<>();
        List<PrecoFornecedorEntity> precosSemPaginacao = new ArrayList<>();
        List<Integer> paginas = new ArrayList<>();

        try {

            precosPaginados = precoService.filtroPrecos(
                    pageable,
                    fornecedorId.orElse(null),
                    fornecedor.orElse(null),
                    produtoId.orElse(null),
                    produto.orElse(null));

            precosSemPaginacao = precoService.filtroPrecosSemPaginacao(
                    fornecedorId.orElse(null),
                    fornecedor.orElse(null),
                    produtoId.orElse(null),
                    produto.orElse(null));

        }

        catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:precos");
            return modelAndView;
        }

        model.addAttribute("tipoFiltro", "todos");

        FornecedorEntity fornecedorEncontradoPorId = new FornecedorEntity();
        ProdutoEstoqueEntity produtoEncontradoPorId = new ProdutoEstoqueEntity();

        if (fornecedorId.isPresent()) model.addAttribute("tipoFiltro", "fornecedorId");
        if (fornecedorId.isPresent()) {
            fornecedorEncontradoPorId = (fornecedorRepository.findById(Long.parseLong(fornecedorId.orElse(String.valueOf(0L)))).get());
            model.addAttribute("tipoFiltro", "fornecedorId");
        }
        if (fornecedor.isPresent()) model.addAttribute("tipoFiltro", "fornecedor");
        if (produtoId.isPresent()) {
            produtoEncontradoPorId = (produtoEstoqueRepository.findById(Long.parseLong(produtoId.orElse(String.valueOf(0L)))).get());
            model.addAttribute("tipoFiltro", "produtoId");
        }
        if (produto.isPresent()) model.addAttribute("tipoFiltro", "produto");

        paginas = precoService.calculaQuantidadePaginas(precosSemPaginacao, pageable);

        model.addAttribute("totalItens", precosSemPaginacao.size());
        model.addAttribute("fornecedorId", fornecedorId.orElse(null));
        model.addAttribute("fornecedorEncontradoPorId", fornecedorEncontradoPorId);
        model.addAttribute("fornecedor", fornecedor.orElse(null));
        model.addAttribute("produtoId", produtoId.orElse(null));
        model.addAttribute("produtoEncontradoPorId", produtoEncontradoPorId);
        model.addAttribute("produto", produto.orElse(null));
        model.addAttribute("paginas", paginas);
        model.addAttribute("pagina", pageable.getPageNumber());
        model.addAttribute("precos", precosPaginados);
        model.addAttribute("produtos", produtoEstoqueService.buscaTodos());
        model.addAttribute("fornecedores", fornecedorService.buscaTodos());

        if(!UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().equals(PrivilegioEnum.VENDEDOR)) {
            modelAndView.setViewName("precos");
        }
        else {
            modelAndView.setViewName("redirect:/");
            redirAttrs.addFlashAttribute("ErroCadastro",
                    "Você não possui o privilégio necessário para acessar a página de preços");
        }

        return modelAndView;
    }

    @PostMapping
    public ModelAndView novoPreco(PrecoFornecedorDTO preco,
                                  String query,
                                  ModelAndView modelAndView,
                                  RedirectAttributes redirAttrs) {

        String criaPreco = precoService.encaminhaParaCriacaoDoCrudService(preco);

        if (criaPreco == null)
            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro do preço realizado com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", criaPreco);

        modelAndView.setViewName("redirect:precos?" + query);
        return modelAndView;
    }

    @PostMapping("/filtro")
    public ModelAndView filtraPreco(FiltroPrecoDTO filtroPreco, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:../" + precoService.constroiUriFiltro(filtroPreco));
        return modelAndView;
    }

    @PostMapping("/deleta-{id}")
    public ModelAndView deletaPreco(@PathVariable Long id,
                                            RedirectAttributes redirAttrs,
                                            ModelAndView modelAndView,
                                            String query) {
        precoCrudService.deletaPorId(id);
        redirAttrs.addFlashAttribute("SucessoDelete", "Preço removido com sucesso");
        modelAndView.setViewName("redirect:../precos?" + query);
        return modelAndView;
    }


    @PostMapping("/alterar")
    public ModelAndView atualizaPreco(PrecoFornecedorDTO preco,
                                      RedirectAttributes redirAttrs,
                                      ModelAndView modelAndView,
                                      String query) {

        String atualizaPreco = precoService.encaminhaParaUpdateDoCrudService(preco);

        if (atualizaPreco == null)
            redirAttrs.addFlashAttribute("SucessoCadastro",
                    "Atualização do preço realizado com sucesso");
        else
            redirAttrs.addFlashAttribute("ErroCadastro", atualizaPreco);

        modelAndView.setViewName("redirect:../precos?" + query);
        return modelAndView;
    }

    @GetMapping("/relatorio")
    public void relatorio(ModelAndView modelAndView,
                          @RequestParam("fornecedorId") Optional<String> fornecedorId,
                          @RequestParam("fornecedor") Optional<String> fornecedor,
                          @RequestParam("produtoId") Optional<String> produtoId,
                          @RequestParam("produto") Optional<String> produto,
                          HttpServletResponse res) throws DocumentException, IOException {

        List<PrecoFornecedorEntity> precos = precoService.filtroPrecosSemPaginacao(
                fornecedorId.orElse(null),
                fornecedor.orElse(null),
                produtoId.orElse(null),
                produto.orElse(null));

        HashMap<String, String> filtros = new HashMap<>();
        filtros.put("fornecedorId", fornecedorId.orElse(null));
        filtros.put("fornecedor", fornecedor.orElse(null));
        filtros.put("produtoId", produtoId.orElse(null));
        filtros.put("produto", produto.orElse(null));

        res.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachement; filename=akadia_precos_"
                + new SimpleDateFormat("dd.MM.yyyy_HHmmss").format(new Date())
                + ".pdf";
        res.setHeader(headerKey, headerValue);
        PrecoPdfExporter pdfExporterService = new PrecoPdfExporter();
        pdfExporterService.export(res, precos, precoService, filtros, usuarioRepository, fornecedorRepository);

    }

}
