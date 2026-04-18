package com.fiap.fase1.service;

import com.fiap.fase1.dto.LoginRequestDTO;
import com.fiap.fase1.model.UserType;
import com.fiap.fase1.dto.LoginResponseDTO;
import com.fiap.fase1.dto.UserRequestDTO;
import com.fiap.fase1.dto.UserResponseDTO;
import com.fiap.fase1.exception.InvalidCredentialsException;
import com.fiap.fase1.exception.EmailAlreadyExistsException;
import com.fiap.fase1.exception.LoginAlreadyExistsException;
import com.fiap.fase1.exception.UserNotFoundException;
import com.fiap.fase1.model.User;
import com.fiap.fase1.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService service;

    private User user;
    private UserRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        user = new User("João Silva", "joao@email.com", "joaosilva", "senha_hash", "Rua A, 123", UserType.CUSTOMER);
        try {
            var field = User.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(user, 1L);

            var fieldDate = User.class.getDeclaredField("lastModifiedDate");
            fieldDate.setAccessible(true);
            fieldDate.set(user, LocalDateTime.now());
        } catch (Exception e) {
            throw new RuntimeException("Falha ao setar campos via reflection", e);
        }

        requestDTO = new UserRequestDTO("João Silva", "joao@email.com", "joaosilva", "senha123", "Rua A, 123", UserType.CUSTOMER);
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void shouldCreateUser() {
        when(repository.existsByEmail(requestDTO.email())).thenReturn(false);
        when(repository.existsByLogin(requestDTO.login())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("senha_hash");
        when(repository.save(any(User.class))).thenReturn(user);

        UserResponseDTO response = service.create(requestDTO);

        assertNotNull(response);
        assertEquals("João Silva", response.name());
        assertEquals("joao@email.com", response.email());
        verify(repository).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com email já cadastrado")
    void shouldThrowExceptionDuplicateEmail() {
        when(repository.existsByEmail(requestDTO.email())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> service.create(requestDTO));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com login já cadastrado")
    void shouldThrowExceptionDuplicateLogin() {
        when(repository.existsByEmail(requestDTO.email())).thenReturn(false);
        when(repository.existsByLogin(requestDTO.login())).thenReturn(true);

        assertThrows(LoginAlreadyExistsException.class, () -> service.create(requestDTO));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve listar todos os usuários")
    void shouldListAllUsers() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<UserResponseDTO> list = service.findAll();

        assertEquals(1, list.size());
        assertEquals("joao@email.com", list.get(0).email());
    }

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void shouldFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDTO response = service.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar usuário com ID inexistente")
    void shouldThrowExceptionNonExistentId() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void shouldUpdateUser() {
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.findByEmail(requestDTO.email())).thenReturn(Optional.of(user));
        when(repository.findByLogin(requestDTO.login())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("nova_senha_hash");
        when(repository.save(any(User.class))).thenReturn(user);

        UserResponseDTO response = service.update(1L, requestDTO);

        assertNotNull(response);
        verify(repository).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar com email de outro usuário")
    void shouldThrowExceptionUpdateDuplicateEmail() {
        User otherUser = new User("Outro", "joao@email.com", "outro", "hash", "Rua A, 123", UserType.CUSTOMER);
        try {
            var field = User.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(otherUser, 2L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.findByEmail(requestDTO.email())).thenReturn(Optional.of(otherUser));

        assertThrows(EmailAlreadyExistsException.class, () -> service.update(1L, requestDTO));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar com login de outro usuário")
    void shouldThrowExceptionUpdateDuplicateLogin() {
        User otherUser = new User("Outro", "outro@email.com", "joaosilva", "hash", "Rua A, 123", UserType.CUSTOMER);
        try {
            var field = User.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(otherUser, 2L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.findByEmail(requestDTO.email())).thenReturn(Optional.of(user));
        when(repository.findByLogin(requestDTO.login())).thenReturn(Optional.of(otherUser));

        assertThrows(LoginAlreadyExistsException.class, () -> service.update(1L, requestDTO));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar usuário inexistente")
    void shouldThrowExceptionUpdateNonExistent() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.update(99L, requestDTO));
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void shouldDeleteUser() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar usuário inexistente")
    void shouldThrowExceptionDeleteNonExistent() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> service.delete(99L));
    }

    @Test
    @DisplayName("Deve realizar login com sucesso")
    void shouldLoginSuccessfully() {
        LoginRequestDTO loginDTO = new LoginRequestDTO("joaosilva", "senha123");

        when(repository.findByLogin("joaosilva")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("senha123", "senha_hash")).thenReturn(true);

        LoginResponseDTO response = service.login(loginDTO);

        assertNotNull(response);
        assertEquals("Login realizado com sucesso", response.message());
        assertEquals("joao@email.com", response.email());
        assertEquals("joaosilva", response.login());
    }

    @Test
    @DisplayName("Deve lançar exceção ao fazer login com login inexistente")
    void shouldThrowExceptionLoginNonExistent() {
        LoginRequestDTO loginDTO = new LoginRequestDTO("naoexiste", "senha123");

        when(repository.findByLogin("naoexiste")).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> service.login(loginDTO));
    }

    @Test
    @DisplayName("Deve lançar exceção ao fazer login com senha incorreta")
    void shouldThrowExceptionLoginWrongPassword() {
        LoginRequestDTO loginDTO = new LoginRequestDTO("joaosilva", "senhaErrada");

        when(repository.findByLogin("joaosilva")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("senhaErrada", "senha_hash")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> service.login(loginDTO));
    }
}
