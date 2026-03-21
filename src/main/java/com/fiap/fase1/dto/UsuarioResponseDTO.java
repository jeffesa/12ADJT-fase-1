package com.fiap.fase1.dto;

import com.fiap.fase1.model.Usuario;

import java.time.LocalDateTime;

public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String login;
    private LocalDateTime dataUltimaAlteracao;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.login = usuario.getLogin();
        this.dataUltimaAlteracao = usuario.getDataUltimaAlteracao();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getLogin() { return login; }
    public LocalDateTime getDataUltimaAlteracao() { return dataUltimaAlteracao; }
}
