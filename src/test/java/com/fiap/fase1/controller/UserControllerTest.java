package com.fiap.fase1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.fase1.model.UserType;
import com.fiap.fase1.dto.ChangePasswordDTO;
import com.fiap.fase1.dto.LoginRequestDTO;
import com.fiap.fase1.dto.LoginResponseDTO;
import com.fiap.fase1.dto.UserRequestDTO;
import com.fiap.fase1.dto.UserResponseDTO;
import com.fiap.fase1.dto.UserUpdateDTO;
import com.fiap.fase1.exception.InvalidCredentialsException;
import com.fiap.fase1.exception.EmailAlreadyExistsException;
import com.fiap.fase1.exception.UserNotFoundException;
import com.fiap.fase1.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import com.fiap.fase1.config.SecurityConfig;
import org.springframework.context.annotation.Import;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService service;

    private UserResponseDTO responseDTO;
    private UserRequestDTO requestDTO;
    private UserUpdateDTO updateDTO;
    private LoginResponseDTO loginResponseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new UserResponseDTO(1L, "João Silva", "joao@email.com", "joaosilva", "Rua A, 123", UserType.CUSTOMER, LocalDateTime.now());
        requestDTO = new UserRequestDTO("João Silva", "joao@email.com", "joaosilva", "senha123", "Rua A, 123", UserType.CUSTOMER);
        updateDTO = new UserUpdateDTO("João Silva", "joao@email.com", "joaosilva", "Rua A, 123", UserType.CUSTOMER);
        loginResponseDTO = new LoginResponseDTO("Login realizado com sucesso", 1L, "joaosilva", "joao@email.com", LocalDateTime.now());
    }

    @Test
    @DisplayName("POST /api/v1/usuarios - deve criar usuário e retornar 201")
    void shouldCreateUser() throws Exception {
        when(service.create(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.email").value("joao@email.com"))
                .andExpect(jsonPath("$.name").value("João Silva"));
    }

    @Test
    @DisplayName("POST /api/v1/usuarios - deve retornar 409 com email duplicado")
    void shouldReturn409DuplicateEmail() throws Exception {
        when(service.create(any())).thenThrow(new EmailAlreadyExistsException("joao@email.com"));

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("POST /api/v1/usuarios - deve retornar 400 com dados inválidos")
    void shouldReturn400InvalidData() throws Exception {
        UserRequestDTO invalid = new UserRequestDTO("", "email-invalido", "login", "123", "Rua A, 123", UserType.CUSTOMER);

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/usuarios - deve listar usuários e retornar 200")
    void shouldListUsers() throws Exception {
        when(service.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("joao@email.com"));
    }

    @Test
    @DisplayName("GET /api/v1/usuarios/{id} - deve retornar usuário por ID")
    void shouldFindById() throws Exception {
        when(service.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/v1/usuarios/{id} - deve retornar 404 para ID inexistente")
    void shouldReturn404NonExistentId() throws Exception {
        when(service.findById(99L)).thenThrow(new UserNotFoundException(99L));

        mockMvc.perform(get("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/v1/usuarios/{id} - deve atualizar usuário e retornar 200")
    void shouldUpdateUser() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    @DisplayName("PUT /api/v1/usuarios/{id} - deve retornar 404 para ID inexistente")
    void shouldReturn404UpdateNonExistent() throws Exception {
        when(service.update(eq(99L), any())).thenThrow(new UserNotFoundException(99L));

        mockMvc.perform(put("/api/v1/usuarios/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/usuarios/{id} - deve deletar usuário e retornar 204")
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/v1/usuarios/{id} - deve retornar 404 para ID inexistente")
    void shouldReturn404DeleteNonExistent() throws Exception {
        doThrow(new UserNotFoundException(99L)).when(service).delete(99L);

        mockMvc.perform(delete("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/usuarios/login - deve realizar login e retornar 200")
    void shouldLogin() throws Exception {
        LoginRequestDTO loginDTO = new LoginRequestDTO("joaosilva", "senha123");

        when(service.login(any())).thenReturn(loginResponseDTO);

        mockMvc.perform(post("/api/v1/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login realizado com sucesso"))
                .andExpect(jsonPath("$.email").value("joao@email.com"))
                .andExpect(jsonPath("$.login").value("joaosilva"));
    }

    @Test
    @DisplayName("POST /api/v1/usuarios/login - deve retornar 401 com credenciais inválidas")
    void shouldReturn401InvalidCredentials() throws Exception {
        LoginRequestDTO loginDTO = new LoginRequestDTO("joaosilva", "senhaErrada");

        when(service.login(any())).thenThrow(new InvalidCredentialsException());

        mockMvc.perform(post("/api/v1/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("PATCH /api/v1/usuarios/{id}/password - deve trocar senha e retornar 200")
    void shouldChangePassword() throws Exception {
        ChangePasswordDTO dto = new ChangePasswordDTO("senha123", "novaSenha456");

        mockMvc.perform(patch("/api/v1/usuarios/1/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Senha alterada com sucesso"));
    }

    @Test
    @DisplayName("PATCH /api/v1/usuarios/{id}/password - deve retornar 400 com senha atual incorreta")
    void shouldReturn400WrongCurrentPassword() throws Exception {
        ChangePasswordDTO dto = new ChangePasswordDTO("senhaErrada", "novaSenha456");

        doThrow(new IllegalArgumentException("Senha atual incorreta"))
                .when(service).changePassword(eq(1L), any());

        mockMvc.perform(patch("/api/v1/usuarios/1/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PATCH /api/v1/usuarios/{id}/password - deve retornar 404 para usuário inexistente")
    void shouldReturn404ChangePasswordNonExistent() throws Exception {
        ChangePasswordDTO dto = new ChangePasswordDTO("senha123", "novaSenha456");

        doThrow(new UserNotFoundException(99L))
                .when(service).changePassword(eq(99L), any());

        mockMvc.perform(patch("/api/v1/usuarios/99/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}
