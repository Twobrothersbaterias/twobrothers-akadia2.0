package br.com.twobrothers.msbalanco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableSpringDataWebSupport
@EntityScan(basePackages = {"br.com.twobrothers.msbalanco.models"})
@EnableJpaRepositories("br.com.twobrothers.msbalanco.repositories")
@ComponentScan("br.com.twobrothers.msbalanco.config")
@ComponentScan("br.com.twobrothers.msbalanco.controllers")
@ComponentScan("br.com.twobrothers.msbalanco.services")
@ComponentScan("br.com.twobrothers.msbalanco.validations")
@ComponentScan("br.com.twobrothers.msbalanco.utils")
@ComponentScan("br.com.twobrothers.msbalanco.services.exceptions")
public class MsBalancoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBalancoApplication.class, args);
	}

}
