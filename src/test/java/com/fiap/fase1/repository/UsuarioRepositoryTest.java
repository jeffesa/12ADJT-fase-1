package com.fiap.fase1.repository;

import com.fiap.fase1.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = repository.save(new Usuario("João Silva", "joao@email.com", "joaosilva", "senha_hash"));
    }

    @Test
    @DisplayName("Deve encontrar usuário por email")
    void deveEncontrarPorEmail() {
        Optional<Usuario> resultado = repository.findByEmail("joao@email.com");
        assertTrue(resultado.isPresent());
        assertEquals("joao@email.com", resultado.get().getEmail());
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar email inexistente")
    void deveRetornarVazioEmailInexistente() {
        assertTrue(repository.findByEmail("naoexiste@email.com").isEmpty());
    }

    @Test
    @DisplayName("Deve encontrar usuário por login")
    void deveEncontrarPorLogin() {
        Optional<Usuario> resultado = repository.findByLogin("joaosilva");
        assertTrue(resultado.isPresent());
        assertEquals("joaosilva", resultado.get().getLogin());
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar login inexistente")
    void deveRetornarVazioLoginInexistente() {
        assertTrue(repository.findByLogin("naoexiste").isEmpty());
    }

    @Test
    @DisplayName("Deve retornar true para email já cadastrado")
    void deveConfirmarEmailExistente() {
        assertTrue(repository.existsByEmail("joao@email.com"));
    }

    @Test
    @DisplayName("Deve retornar false para email não cadastrado")
    void deveConfirmarEmailInexistente() {
        assertFalse(repository.existsByEmail("naoexiste@email.com"));
    }

    @Test
    @DisplayName("Deve retornar true para login já cadastrado")
    void deveConfirmarLoginExistente() {
        assertTrue(repository.existsByLogin("joaosilva"));
    }

    @Test
    @DisplayName("Deve retornar false para login não cadastrado")
    void deveConfirmarLoginInexistente() {
        assertFalse(repository.existsByLogin("naoexiste"));
    }
}
