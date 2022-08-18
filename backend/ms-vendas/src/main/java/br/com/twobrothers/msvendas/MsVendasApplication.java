package br.com.twobrothers.msvendas;

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
@EntityScan(basePackages = {"br.com.twobrothers.msvendas.models"})
@EnableJpaRepositories("br.com.twobrothers.msvendas.repositories")
@ComponentScan("br.com.twobrothers.msvendas.config")
@ComponentScan("br.com.twobrothers.msvendas.controllers")
@ComponentScan("br.com.twobrothers.msvendas.services")
@ComponentScan("br.com.twobrothers.msvendas.validations")
@ComponentScan("br.com.twobrothers.msvendas.utils")
@ComponentScan("br.com.twobrothers.msvendas.services.exceptions")
public class MsVendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsVendasApplication.class, args);
	}

}
