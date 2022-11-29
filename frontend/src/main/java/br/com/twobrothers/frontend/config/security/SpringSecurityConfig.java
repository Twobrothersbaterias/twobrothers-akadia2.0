package br.com.twobrothers.frontend.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.authorizeRequests()
//                .antMatchers("/api/despesas/**").permitAll()
//                .anyRequest().permitAll();
//        http.cors().and().csrf().disable();

        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .antMatchers("/index").hasAnyRole("USER", "ADMIN")
                .antMatchers("/swagger-ui.html").hasAnyRole("ADMIN")
                .antMatchers("/api/**").hasAnyRole("ADMIN")
                .antMatchers("/signup").permitAll()
                .antMatchers("/api/despesas/**").permitAll() //TODO TRATAR
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .permitAll();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Construir
    }

    public static void main(String[] args) throws IOException {

//        String s = "minusplusminus";
//        StringBuilder reformatedString = new StringBuilder();
//
//        for(int i = 0; i < s.length(); i++) {
//            if (s.length() >= (i+4) && s.substring(i, i+4).equals("plus")) reformatedString.append("+");
//            if (s.length() >= (i+5) && s.substring(i, i+5).equals("minus")) reformatedString.append("-");
//        }

//        ImprimeNota imprimeNota = new ImprimeNota();
//        System.err.println(retornaImpressoras());
//        imprimeNota.detectaImpressoras("Microsoft Print to PDF");
//        imprimeNota.imprime("Testando impressão do arquivo");

//        int maximo = 150;
//
//        System.out.print("INSERT INTO tb_cliente(cpf_cnpj, data_cadastro, data_nascimento, email, nome_completo, telefone, endereco_id, usuario_id) VALUES ");
//        for (int i=0; i <= maximo; i++) {
//            System.out.print("(NULL, '2022-11-13',  '1998-07-21',NULL, 'Carga de dados', '(11)97981-5415', NULL, 1)");
//            if (i < maximo) System.out.print(",");
//        }
//        System.out.print(";");

    }

}
