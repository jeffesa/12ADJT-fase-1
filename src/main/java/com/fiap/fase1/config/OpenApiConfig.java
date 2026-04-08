package com.fiap.fase1.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gerenciamento de Usuários - Tech Challenge FIAP")
                        .description("API RESTful para cadastro, atualização, exclusão e validação de login de usuários.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FIAP - Fase 1")
                                .url("https://github.com/jeffesa/12ADJT-fase-1")));
    }
}
