package com.fiap.fase1.dto;

import com.fiap.fase1.validation.SafeInput;
import com.fiap.fase1.validation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.fiap.fase1.model.UserType;

@Schema(description = "Dados para criação ou atualização de usuário")
public record UserRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        @SafeInput
        @Schema(description = "Nome completo do usuário", example = "João Silva")
        String name,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser válido")
        @Schema(description = "Email do usuário (único)", example = "joao@email.com")
        String email,

        @NotBlank(message = "O login é obrigatório")
        @Size(min = 3, max = 50, message = "O login deve ter entre 3 e 50 caracteres")
        @SafeInput
        @Schema(description = "Login do usuário", example = "joaosilva")
        String login,

        @NotBlank(message = "A senha é obrigatória")
        @ValidPassword
        @Schema(description = "Senha do usuário", example = "Senha123")
        String password,

        @NotBlank(message = "O endereço é obrigatório")
        @Size(max = 255, message = "O endereço deve ter no máximo 255 caracteres")
        @SafeInput
        @Schema(description = "Endereço do usuário", example = "Rua das Flores, 123")
        String address,

        @NotNull(message = "O tipo de usuário é obrigatório")
        @Schema(description = "Tipo de usuário", example = "CUSTOMER")
        UserType type
) {}