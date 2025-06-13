package com.desafio.nubank.desafio.infra.configuration.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(new Info()
                .title("Dessafio Nubank Api")
                .description("API desafio back end nubank")
                .contact(new Contact()
                        .name("Time BackEnd")
                        .email("backend@gmail.com"))
                        .license(new License().name("apache")
                                .url("http://nubank.desafio.com/api/license"))
                );
    }
}
