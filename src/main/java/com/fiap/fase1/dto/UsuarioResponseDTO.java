package com.fiap.fase1.dto;

import com.fiap.fase1.model.Usuario;
import java.time.LocalDateTime;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String email,
    String login,
    LocalDateTime dataUltimaAlteracao
) {
    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getLogin(),
            usuario.getDataUltimaAlteracao()
        );
    }
}
