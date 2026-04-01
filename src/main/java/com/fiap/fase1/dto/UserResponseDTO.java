package com.fiap.fase1.dto;

import com.fiap.fase1.model.User;
import java.time.LocalDateTime;

public record UserResponseDTO(
    Long id,
    String name,
    String email,
    String login,
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
