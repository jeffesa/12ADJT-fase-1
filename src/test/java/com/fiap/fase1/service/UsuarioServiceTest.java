package com.fiap.fase1.service;

import com.fiap.fase1.dto.LoginRequestDTO;
import com.fiap.fase1.dto.UsuarioRequestDTO;
import com.fiap.fase1.dto.UsuarioResponseDTO;
import com.fiap.fase1.exception.EmailJaCadastradoException;
import com.fiap.fase1.exception.UsuarioNaoEncontradoException;
import com.fiap.fase1.model.Usuario;
import com.fiap.fase1.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;
    private UsuarioRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("João Silva", "joao@email.com", "joaosilva", "senha_hash");
        try {
            var field = Usuario.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(usuario, 1L);
        } catch (Exception ignored) {}

        requestDTO = new UsuarioRequestDTO();
        requestDTO.setNome("João Silva");
        requestDTO.setEmail("joao@email.com");
        requestDTO.setLogin("joaosilva");
        requestDTO.setSenha("senha123");
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void deveCriarUsuario() {
        when(repository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("senha_hash");
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO response = service.criar(requestDTO);

        assertNotNull(response);
        assertEquals("João Silva", response.getNome());
        assertEquals("joao@email.com", response.getEmail());
        verify(repository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com email já cadastrado")
    void deveLancarExcecaoEmailDuplicado() {
        when(repository.existsByEmail(requestDTO.getEmail())).thenReturn(true);

        assertThrows(EmailJaCadastradoException.class, () -> service.criar(requestDTO));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve listar todos os usuários")
    void deveListarUsuarios() {
        when(repository.findAll()).thenReturn(List.of(usuario));

        List<UsuarioResponseDTO> lista = service.listar();

        assertEquals(1, lista.size());
        assertEquals("joao@email.com", lista.get(0).getEmail());
    }

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void deveBuscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO response = service.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar usuário com ID inexistente")
    void deveLancarExcecaoIdInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> service.buscarPorId(99L));
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void deveAtualizarUsuario() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode(anyString())).thenReturn("nova_senha_hash");
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO response = service.atualizar(1L, requestDTO);

        assertNotNull(response);
        verify(repository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar usuário inexistente")
    void deveLancarExcecaoAoAtualizarInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> service.atualizar(99L, requestDTO));
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void deveDeletarUsuario() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deletar(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar usuário inexistente")
    void deveLancarExcecaoAoDeletarInexistente() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(UsuarioNaoEncontradoException.class, () -> service.deletar(99L));
    }

    @Test
    @DisplayName("Deve realizar login com sucesso")
    void deveRealizarLogin() {
        LoginRequestDTO loginDTO = new LoginRequestDTO();
        loginDTO.setEmail("joao@email.com");
        loginDTO.setSenha("senha123");

        when(repository.findByEmail("joao@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha123", "senha_hash")).thenReturn(true);

        UsuarioResponseDTO response = service.login(loginDTO);

        assertNotNull(response);
        assertEquals("joao@email.com", response.getEmail());
    }

    @Test
    @DisplayName("Deve lançar exceção ao fazer login com email inexistente")
    void deveLancarExcecaoLoginEmailInexistente() {
        LoginRequestDTO loginDTO = new LoginRequestDTO();
        loginDTO.setEmail("naoexiste@email.com");
        loginDTO.setSenha("senha123");

        when(repository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.login(loginDTO));
    }

    @Test
    @DisplayName("Deve lançar exceção ao fazer login com senha incorreta")
    void deveLancarExcecaoLoginSenhaIncorreta() {
        LoginRequestDTO loginDTO = new LoginRequestDTO();
        loginDTO.setEmail("joao@email.com");
        loginDTO.setSenha("senhaErrada");

        when(repository.findByEmail("joao@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senhaErrada", "senha_hash")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> service.login(loginDTO));
    }
}
