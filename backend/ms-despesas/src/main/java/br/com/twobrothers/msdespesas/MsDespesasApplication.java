package br.com.twobrothers.msdespesas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSpringDataWebSupport
@EntityScan(basePackages = {"br.com.twobrothers.msdespesas.models"})
@EnableJpaRepositories("br.com.twobrothers.msdespesas.repositories")
@ComponentScan("br.com.twobrothers.msdespesas.config")
@ComponentScan("br.com.twobrothers.msdespesas.controllers")
@ComponentScan("br.com.twobrothers.msdespesas.services")
@ComponentScan("br.com.twobrothers.msdespesas.validations")
@ComponentScan("br.com.twobrothers.msdespesas.utils")
@ComponentScan("br.com.twobrothers.msdespesas.services.exceptions")
public class MsDespesasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsDespesasApplication.class, args);
	}

}
