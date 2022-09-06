package br.com.twobrothers.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter (RouteLocatorBuilder builder) {

        return builder.routes()
                .route(p -> p.path("/despesas/**")
                        .uri("lb://frontend"))
                .route(p -> p.path("/api/despesas/**")
                        .uri("lb://ms-despesas"))
                .route(p -> p.path("/api/patrimonio/**")
                        .uri("lb://ms-balanco"))
                .route(p -> p.path("/api/vendas/**")
                        .uri("lb://ms-vendas"))
                .build();

    }

}
