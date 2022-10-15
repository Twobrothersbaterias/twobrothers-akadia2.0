package br.com.twobrothers.msbalanco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsBalancoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBalancoApplication.class, args);
	}

}
