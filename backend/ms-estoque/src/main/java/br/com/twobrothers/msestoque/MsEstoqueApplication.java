package br.com.twobrothers.msestoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsEstoqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEstoqueApplication.class, args);
	}

}
