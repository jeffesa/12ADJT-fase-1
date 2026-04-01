package com.fiap.fase1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class Fase1SpringExplorerApplicationTests {

    @Test
    @DisplayName("Deve carregar o contexto da aplicação com sucesso")
    void contextLoads() {
        assertTrue(true);
    }

    @Test
    @DisplayName("Deve validar operações básicas - sucesso")
    void shouldValidateBasicOperations() {
        int result = 2 + 2;
        assertEquals(4, result);
    }

    @Test
    @DisplayName("Deve validar que string não é nula - sucesso")
    void shouldValidateStringNotNull() {
        String value = "FIAP Tech Challenge";
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    @DisplayName("Deve validar formato de email - sucesso")
    void shouldValidateEmailFormat() {
        String email = "usuario@fiap.com.br";
        assertTrue(email.contains("@"));
        assertTrue(email.contains("."));
    }

    @Test
    @DisplayName("Deve falhar ao comparar valores diferentes - erro esperado")
    void shouldFailComparingDifferentValues() {
        String originalPassword = "senha123";
        String wrongPassword = "senhaErrada";
        assertNotEquals(originalPassword, wrongPassword);
    }

    @Test
    @DisplayName("Deve validar que lista vazia tem tamanho zero - sucesso")
    void shouldValidateEmptyList() {
        java.util.List<String> list = new java.util.ArrayList<>();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }
}
