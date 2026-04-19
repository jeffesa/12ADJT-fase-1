package com.fiap.fase1.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gerenciamento de Usuários")
                        .description("""
                                API RESTful para gerenciamento de usuários desenvolvida como Tech Challenge Fase 1 da FIAP.

                                **Funcionalidades:**
                                - Cadastro de usuários com validação de dados
                                - Atualização de perfil
                                - Exclusão de conta
                                - Autenticação via login e senha
                                - Troca de senha

                                **Regras de senha:** mínimo 8 caracteres, incluindo letra maiúscula, minúscula e número.

                                **Tipos de usuário:** `CUSTOMER` (cliente) e `RESTAURANT_OWNER` (dono de restaurante).
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FIAP Tech Challenge - Fase 1")
                                .url("https://github.com/jeffesa/12ADJT-fase-1")))
                .tags(List.of(
                        new Tag()
                                .name("Usuários")
                                .description("Operações de criação, consulta, atualização e exclusão de usuários"),
                        new Tag()
                                .name("Autenticação")
                                .description("Operações de login e troca de senha")
                ));
    }
}
