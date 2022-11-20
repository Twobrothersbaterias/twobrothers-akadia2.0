package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.models.entities.UsuarioEntity;
import br.com.twobrothers.frontend.models.enums.PrivilegioEnum;
import br.com.twobrothers.frontend.models.enums.TipoProdutoEnum;
import br.com.twobrothers.frontend.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProdutoEstoqueRepository produtoEstoqueRepository;

    @GetMapping
    public ModelAndView paginaDeLogin(ModelAndView modelAndView) {

        if (usuarioRepository.findByNomeUsuario("admin").isEmpty())
            usuarioRepository.save(UsuarioEntity.builder()
                    .email("gabriellagrota23@gmail.com")
                    .cpfCnpj("471.534.278-21")
                    .dataCadastro(LocalDate.now().toString())
                    .dataNascimento("1998-07-21")
                    .nome("Gabriel Lagrota")
                    .senha("762")
                    .privilegio(PrivilegioEnum.DESENVOLVEDOR)
                    .senhaCriptografada(new BCryptPasswordEncoder().encode("762"))
                    .nomeUsuario("admin")
                    .build());

        if (produtoEstoqueRepository.buscaPorSigla("SUC45").isEmpty())
            produtoEstoqueRepository.save(ProdutoEstoqueEntity.builder()
                    .abastecimentos(new ArrayList<>())
                    .custoTotal(0.0)
                    .custoUnitario(0.0)
                    .sigla("SUC45")
                    .especificacao("SUC45")
                    .marcaBateria("SUCATA")
                    .quantidadeMinima(0)
                    .quantidade(0)
                    .tipoProduto(TipoProdutoEnum.SUCATA)
                    .dataCadastro(LocalDate.now().toString())
                    .entradas(new ArrayList<>())
                    .precosFornecedor(new ArrayList<>())
                    .usuarioResponsavel(usuarioRepository.findByNomeUsuario("admin").get())
                    .build());

        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView realizarLogin(ModelAndView modelAndView) {
        modelAndView.setViewName("despesas");
        return modelAndView;
    }

}
