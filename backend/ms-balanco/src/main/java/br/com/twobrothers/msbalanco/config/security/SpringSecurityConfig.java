package br.com.twobrothers.msbalanco.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    //TODO CONFIGURAR CLASSE DE SEGURANÇA PARA AUTENTICAR COM TOKEN QUE DEVE SER CRIADO NA TELA DE LOGIN

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Montar classe
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/balanco/**").permitAll()
                .anyRequest().permitAll();
        http.cors().and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //Montar classe
    }


}
