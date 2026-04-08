package com.fiap.fase1.dto;

import java.time.LocalDateTime;

public record LoginResponseDTO(
    String message,
    Long userId,
    String login,
    String email,
    LocalDateTime lastModifiedDate
) {}
