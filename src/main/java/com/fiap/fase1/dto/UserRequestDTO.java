package com.fiap.fase1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para criação ou atualização de usuário")
public record UserRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        @Schema(description = "Nome completo do usuário", example = "João Silva")
        String name,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser válido")
        @Schema(description = "Email do usuário (único)", example = "joao@email.com")
        String email,

        @NotBlank(message = "O login é obrigatório")
        @Size(min = 3, max = 50, message = "O login deve ter entre 3 e 50 caracteres")
        @Schema(description = "Login do usuário", example = "joaosilva")
        String login,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        @Schema(description = "Senha do usuário", example = "senha123")
        String password,

        @NotBlank(message = "O endereço é obrigatório")
        @Size(max = 255, message = "O endereço deve ter no máximo 255 caracteres")
        @Schema(description = "Endereço do usuário", example = "Rua das Flores, 123")
        String address
) {}