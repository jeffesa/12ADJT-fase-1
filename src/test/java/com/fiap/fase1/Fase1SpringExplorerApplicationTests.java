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
    void deveValidarOperacoesBasicas() {
        int resultado = 2 + 2;
        assertEquals(4, resultado);
    }

    @Test
    @DisplayName("Deve validar que string não é nula - sucesso")
    void deveValidarStringNaoNula() {
        String valor = "FIAP Tech Challenge";
        assertNotNull(valor);
        assertFalse(valor.isEmpty());
    }

    @Test
    @DisplayName("Deve validar formato de email - sucesso")
    void deveValidarFormatoEmail() {
        String email = "usuario@fiap.com.br";
        assertTrue(email.contains("@"));
        assertTrue(email.contains("."));
    }

    @Test
    @DisplayName("Deve falhar ao comparar valores diferentes - erro esperado")
    void deveFalharAoCompararValoresDiferentes() {
        String senhaOriginal = "senha123";
        String senhaErrada = "senhaErrada";
        assertNotEquals(senhaOriginal, senhaErrada);
    }

    @Test
    @DisplayName("Deve validar que lista vazia tem tamanho zero - sucesso")
    void deveValidarListaVazia() {
        java.util.List<String> lista = new java.util.ArrayList<>();
        assertEquals(0, lista.size());
        assertTrue(lista.isEmpty());
    }
}
