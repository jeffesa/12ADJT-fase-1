package com.fiap.fase1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para validação de login")
public record LoginRequestDTO(
    @NotBlank(message = "O login é obrigatório")
    @Schema(description = "Login do usuário", example = "joaosilva")
    String login,

    @NotBlank(message = "A senha é obrigatória")
    @Schema(description = "Senha do usuário", example = "Senha123")
    String password
) {}
