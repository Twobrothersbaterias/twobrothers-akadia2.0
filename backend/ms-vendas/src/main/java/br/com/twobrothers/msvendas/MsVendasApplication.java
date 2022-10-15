package br.com.twobrothers.msvendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsVendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsVendasApplication.class, args);
	}

}
