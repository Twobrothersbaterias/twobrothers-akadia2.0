package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.dto.AbastecimentoDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroAbastecimentoDTO;
import br.com.twobrothers.frontend.models.entities.AbastecimentoEntity;
import br.com.twobrothers.frontend.models.enums.FormaPagamentoEnum;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.AbastecimentoCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.services.AbastecimentoService;
import br.com.twobrothers.frontend.services.FornecedorService;
import br.com.twobrothers.frontend.services.ProdutoEstoqueService;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.twobrothers.frontend.utils.ConversorDeDados.converteValorDoubleParaValorMonetario;

@Controller
@RequestMapping("/compras")
public class AbastecimentoController {

    @Autowired
    AbastecimentoService abastecimentoService;

    @Autowired
    ProdutoEstoqueService produtoEstoqueService;

    @Autowired
    FornecedorService fornecedorService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public ModelAndView abastecimentos(@PageableDefault(size = 10, page = 0, sort = {"dataCadastro"}, direction = Sort.Direction.ASC) Pageable pageable,
                                       @RequestParam("inicio") Optional<String> inicio,
                                       @RequestParam("fim") Optional<String> fim,
                                       @RequestParam("mes") Optional<Integer> mes,
                                       @RequestParam("ano") Optional<Integer> ano,
                                       @RequestParam("fornecedorId") Optional<String> fornecedorId,
                                       @RequestParam("fornecedor") Optional<String> fornecedor,
                                       @RequestParam("produto") Optional<String> produto,
                                       Model model, ModelAndView modelAndView,
                                       RedirectAttributes redirAttrs,
                                       ModelMap modelMap,
                                       HttpServletRequest req) {

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/abastecimentos?" + req.getQueryString();

        modelMap.addAttribute("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        modelMap.addAttribute("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        modelMap.addAttribute("baseUrl", baseUrl);
        modelMap.addAttribute("queryString", req.getQueryString());
        modelMap.addAttribute("completeUrl", completeUrl);

        List<AbastecimentoEntity> abastecimentosPaginados = new ArrayList<>();
        List<AbastecimentoEntity> abastecimentosSemPaginacao = new ArrayList<>();
        List<Integer> paginas = new ArrayList<>();

        try {

            abastecimentosPaginados = abastecimentoService.filtroAbastecimentos(
                    pageable,
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null),
                    fornecedorId.orElse(null),
                    fornecedor.orElse(null),
                    produto.orElse(null));

            abastecimentosSemPaginacao = abastecimentoService.filtroAbastecimentosSemPaginacao(
                    inicio.orElse(null),
                    fim.orElse(null),
                    mes.orElse(null),
                    ano.orElse(null),
                    fornecedorId.orElse(null),
                    fornecedor.orElse(null),
                    produto.orElse(null));

            paginas = abastecimentoService.calculaQuantidadePaginas(abastecimentosSemPaginacao, pageable);

        }

        catch (InvalidRequestException e) {
            redirAttrs.addFlashAttribute("ErroBusca", e.getMessage());
            modelAndView.setViewName("redirect:abastecimentos");
            return modelAndView;
        }

        model.addAttribute("inicio", inicio.orElse(null));
        model.addAttribute("fim", fim.orElse(null));
        model.addAttribute("mes", mes.orElse(null));
        model.addAttribute("ano", ano.orElse(null));
        model.addAttribute("fornecedorId", fornecedorId.orElse(null));
        model.addAttribute("fornecedor", fornecedor.orElse(null));
        model.addAttribute("produto", produto.orElse(null));
        model.addAttribute("paginas", paginas);
        model.addAttribute("pagina", pageable.getPageNumber());
        model.addAttribute("abastecimentos", abastecimentosPaginados);
        model.addAttribute("produtos", produtoEstoqueService.buscaTodos());
        model.addAttribute("fornecedores", fornecedorService.buscaTodos());
        model.addAttribute("especie", converteValorDoubleParaValorMonetario(abastecimentoService.calculaFormaPagamento(abastecimentosSemPaginacao, FormaPagamentoEnum.DINHEIRO)));
        model.addAttribute("credito", converteValorDoubleParaValorMonetario(abastecimentoService.calculaFormaPagamento(abastecimentosSemPaginacao, FormaPagamentoEnum.CREDITO)));
        model.addAttribute("debito", converteValorDoubleParaValorMonetario(abastecimentoService.calculaFormaPagamento(abastecimentosSemPaginacao, FormaPagamentoEnum.DEBITO)));
        model.addAttribute("cheque", converteValorDoubleParaValorMonetario(abastecimentoService.calculaFormaPagamento(abastecimentosSemPaginacao, FormaPagamentoEnum.CHEQUE)));
        model.addAttribute("pix", converteValorDoubleParaValorMonetario(abastecimentoService.calculaFormaPagamento(abastecimentosSemPaginacao, FormaPagamentoEnum.PIX)));
        model.addAttribute("boleto", converteValorDoubleParaValorMonetario(abastecimentoService.calculaFormaPagamento(abastecimentosSemPaginacao, FormaPagamentoEnum.BOLETO)));

        modelAndView.setViewName("abastecimentos");
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

//    @PostMapping("/deleta-{id}")
//    public ModelAndView deletaAbastecimento(@PathVariable Long id,
//                                            RedirectAttributes redirAttrs,
//                                            ModelAndView modelAndView,
//                                            String query) {
//        abastecimentoCrudService.deletaPorId(id);
//        redirAttrs.addFlashAttribute("SucessoDelete", "Compra removida com sucesso");
//        modelAndView.setViewName("redirect:../abastecimentos?" + query);
//        return modelAndView;
//    }
//
//    @PostMapping("/alterar")
//    public ModelAndView atualizaAbastecimento(AbastecimentoDTO abastecimento,
//                                              RedirectAttributes redirAttrs,
//                                              ModelAndView modelAndView,
//                                              String query) {
//
//        String atualizaAbastecimento = abastecimentoService.encaminhaParaUpdateDoCrudService(abastecimento);
//
//        if (atualizaAbastecimento == null)
//            redirAttrs.addFlashAttribute("SucessoCadastro", "Cadastro da compra realizado salvo com sucesso");
//        else
//            redirAttrs.addFlashAttribute("ErroCadastro", atualizaAbastecimento);
//
//        modelAndView.setViewName("redirect:../abastecimentos?" + query);
//        return modelAndView;
//    }

}
