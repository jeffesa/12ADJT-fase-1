package com.fiap.fase1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Dados de retorno da validação de login")
public record LoginResponseDTO(
    @Schema(description = "Mensagem de resultado", example = "Login realizado com sucesso")
    String message,

    @Schema(description = "ID do usuário", example = "1")
    Long userId,

    @Schema(description = "Login do usuário", example = "joaosilva")
    String login,

    @Schema(description = "Email do usuário", example = "joao@email.com")
    String email,

    @Schema(description = "Data da última alteração", example = "2026-04-08T10:30:00")
    LocalDateTime lastModifiedDate
) {}
