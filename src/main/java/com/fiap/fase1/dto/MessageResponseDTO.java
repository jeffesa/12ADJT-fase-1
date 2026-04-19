package com.fiap.fase1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta com mensagem de confirmação")
public record MessageResponseDTO(
        @Schema(description = "Mensagem de confirmação", example = "Senha alterada com sucesso")
        String mensagem
) {}
