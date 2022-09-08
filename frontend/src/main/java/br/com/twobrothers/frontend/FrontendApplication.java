package br.com.twobrothers.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontendApplication.class, args);
	}

}
