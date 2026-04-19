package com.fiap.fase1.repository;

import com.fiap.fase1.model.User;
import com.fiap.fase1.model.UserType;
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
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(new User("João Silva", "joao@email.com", "joaosilva", "senha_hash", "Rua A, 123", UserType.CUSTOMER));
    }

    @Test
    @DisplayName("Deve encontrar usuário por email")
    void shouldFindByEmail() {
        Optional<User> result = repository.findByEmail("joao@email.com");
        assertTrue(result.isPresent());
        assertEquals("joao@email.com", result.get().getEmail());
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar email inexistente")
    void shouldReturnEmptyNonExistentEmail() {
        assertTrue(repository.findByEmail("naoexiste@email.com").isEmpty());
    }

    @Test
    @DisplayName("Deve encontrar usuário por login")
    void shouldFindByLogin() {
        Optional<User> result = repository.findByLogin("joaosilva");
        assertTrue(result.isPresent());
        assertEquals("joaosilva", result.get().getLogin());
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar login inexistente")
    void shouldReturnEmptyNonExistentLogin() {
        assertTrue(repository.findByLogin("naoexiste").isEmpty());
    }

    @Test
    @DisplayName("Deve retornar true para email já cadastrado")
    void shouldConfirmExistingEmail() {
        assertTrue(repository.existsByEmail("joao@email.com"));
    }

    @Test
    @DisplayName("Deve retornar false para email não cadastrado")
    void shouldConfirmNonExistingEmail() {
        assertFalse(repository.existsByEmail("naoexiste@email.com"));
    }

    @Test
    @DisplayName("Deve retornar true para login já cadastrado")
    void shouldConfirmExistingLogin() {
        assertTrue(repository.existsByLogin("joaosilva"));
    }

    @Test
    @DisplayName("Deve retornar false para login não cadastrado")
    void shouldConfirmNonExistingLogin() {
        assertFalse(repository.existsByLogin("naoexiste"));
    }

    @Test
    @DisplayName("findAll deve retornar todos os usuários salvos")
    void shouldFindAllSavedUsers() {
        repository.save(new User("Maria", "maria@email.com", "maria", "senha_hash", "Rua B, 456", UserType.RESTAURANT_OWNER));

        assertEquals(2, repository.findAll().size());
    }

    @Test
    @DisplayName("deleteById deve remover o usuário")
    void shouldDeleteById() {
        Long id = repository.findByLogin("joaosilva").get().getId();

        repository.deleteById(id);

        assertFalse(repository.existsById(id));
    }

    @Test
    @DisplayName("existsById deve retornar true para usuário existente")
    void shouldReturnTrueExistingId() {
        Long id = repository.findByLogin("joaosilva").get().getId();

        assertTrue(repository.existsById(id));
    }

    @Test
    @DisplayName("existsById deve retornar false para ID inexistente")
    void shouldReturnFalseNonExistingId() {
        assertFalse(repository.existsById(9999L));
    }

}
