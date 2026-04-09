package com.fiap.fase1.dto;

import com.fiap.fase1.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Dados de retorno do usuário")
public record UserResponseDTO(
    @Schema(description = "ID do usuário", example = "1")
    Long id,

    @Schema(description = "Nome completo do usuário", example = "João Silva")
    String name,

    @Schema(description = "Email do usuário", example = "joao@email.com")
    String email,

    @Schema(description = "Login do usuário", example = "joaosilva")
    String login,

    @Schema(description = "Data da última alteração", example = "2026-04-08T10:30:00")
    LocalDateTime lastModifiedDate
) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getLogin(),
            user.getLastModifiedDate()
        );
    }
}
