package com.kenndevs.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI kennstackOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("KennStack Enterprise Backend API")
                        .version("1.0.0")
                        .description("Production-grade Spring Boot REST API powering the KennStack software solutions platform. " +
                                "Provides contact inquiry dispatch, newsletter subscriptions, dynamic service catalog, portfolio case studies, and operational telemetry.")
                        .contact(new Contact()
                                .name("KennStack Engineering Team")
                                .email("ketanwachar2000@gmail.com")
                                .url("https://kenndevs-frontend-cjlm.vercel.app"))
                        .license(new License()
                                .name("Proprietary - KennStack Digital Solutions")
                                .url("https://kenndevs-frontend-cjlm.vercel.app/terms-of-service")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Local Development Server"),
                        new Server()
                                .url("https://api.kennstack.com")
                                .description("Production Cloud Gateway")
                ));
    }
}
