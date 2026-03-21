package com.fiap.fase1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.fase1.dto.LoginRequestDTO;
import com.fiap.fase1.dto.UsuarioRequestDTO;
import com.fiap.fase1.dto.UsuarioResponseDTO;
import com.fiap.fase1.exception.EmailJaCadastradoException;
import com.fiap.fase1.exception.UsuarioNaoEncontradoException;
import com.fiap.fase1.model.Usuario;
import com.fiap.fase1.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fiap.fase1.config.SecurityConfig;
import org.springframework.context.annotation.Import;

@WebMvcTest(UsuarioController.class)
@Import(SecurityConfig.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService service;

    private UsuarioResponseDTO responseDTO;
    private UsuarioRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario("João Silva", "joao@email.com", "senha_hash");
        try {
            var field = Usuario.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(usuario, 1L);
        } catch (Exception ignored) {}

        responseDTO = new UsuarioResponseDTO(usuario);

        requestDTO = new UsuarioRequestDTO();
        requestDTO.setNome("João Silva");
        requestDTO.setEmail("joao@email.com");
        requestDTO.setSenha("senha123");
    }

    @Test
    @DisplayName("POST /api/usuarios - deve criar usuário e retornar 201")
    void deveCriarUsuario() throws Exception {
        when(service.criar(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("joao@email.com"))
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    @DisplayName("POST /api/usuarios - deve retornar 409 com email duplicado")
    void deveRetornar409EmailDuplicado() throws Exception {
        when(service.criar(any())).thenThrow(new EmailJaCadastradoException("joao@email.com"));

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("POST /api/usuarios - deve retornar 400 com dados inválidos")
    void deveRetornar400DadosInvalidos() throws Exception {
        UsuarioRequestDTO invalido = new UsuarioRequestDTO();
        invalido.setNome("");
        invalido.setEmail("email-invalido");
        invalido.setSenha("123");

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/usuarios - deve listar usuários e retornar 200")
    void deveListarUsuarios() throws Exception {
        when(service.listar()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("joao@email.com"));
    }

    @Test
    @DisplayName("GET /api/usuarios/{id} - deve retornar usuário por ID")
    void deveBuscarPorId() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/usuarios/{id} - deve retornar 404 para ID inexistente")
    void deveRetornar404IdInexistente() throws Exception {
        when(service.buscarPorId(99L)).thenThrow(new UsuarioNaoEncontradoException(99L));

        mockMvc.perform(get("/api/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/usuarios/{id} - deve atualizar usuário e retornar 200")
    void deveAtualizarUsuario() throws Exception {
        when(service.atualizar(eq(1L), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    @DisplayName("PUT /api/usuarios/{id} - deve retornar 404 para ID inexistente")
    void deveRetornar404AoAtualizarInexistente() throws Exception {
        when(service.atualizar(eq(99L), any())).thenThrow(new UsuarioNaoEncontradoException(99L));

        mockMvc.perform(put("/api/usuarios/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/usuarios/{id} - deve deletar usuário e retornar 204")
    void deveDeletarUsuario() throws Exception {
        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/usuarios/{id} - deve retornar 404 para ID inexistente")
    void deveRetornar404AoDeletarInexistente() throws Exception {
        doThrow(new UsuarioNaoEncontradoException(99L)).when(service).deletar(99L);

        mockMvc.perform(delete("/api/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/usuarios/login - deve realizar login e retornar 200")
    void deveRealizarLogin() throws Exception {
        LoginRequestDTO loginDTO = new LoginRequestDTO();
        loginDTO.setEmail("joao@email.com");
        loginDTO.setSenha("senha123");

        when(service.login(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    @DisplayName("POST /api/usuarios/login - deve retornar 401 com credenciais inválidas")
    void deveRetornar401CredenciaisInvalidas() throws Exception {
        LoginRequestDTO loginDTO = new LoginRequestDTO();
        loginDTO.setEmail("joao@email.com");
        loginDTO.setSenha("senhaErrada");

        when(service.login(any())).thenThrow(new IllegalArgumentException("Email ou senha inválidos"));

        mockMvc.perform(post("/api/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized());
    }
}
