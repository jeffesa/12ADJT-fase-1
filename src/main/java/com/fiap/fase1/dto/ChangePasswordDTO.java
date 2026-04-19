package com.fiap.fase1.dto;

import com.fiap.fase1.validation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para troca de senha do usuário")
public record ChangePasswordDTO(
        @NotBlank(message = "A senha atual é obrigatória")
        @Schema(description = "Senha atual do usuário", example = "senha123")
        String currentPassword,

        @NotBlank(message = "A nova senha é obrigatória")
        @ValidPassword
        @Schema(description = "Nova senha do usuário", example = "NovaSenha456")
        String newPassword
) {}
