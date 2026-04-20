package com.fiap.fase1.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SafeInputValidatorTest {

    private SafeInputValidator validator;

    @BeforeEach
    void setUp() {
        validator = new SafeInputValidator();
    }

    @Test
    @DisplayName("Deve aceitar valor nulo (responsabilidade do @NotBlank)")
    void deveAceitarNulo() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    @DisplayName("Deve aceitar texto simples")
    void deveAceitarTextoSimples() {
        assertTrue(validator.isValid("João Silva", null));
    }

    @Test
    @DisplayName("Deve aceitar endereço com vírgula e números")
    void deveAceitarEndereco() {
        assertTrue(validator.isValid("Rua das Flores, 123", null));
    }

    @Test
    @DisplayName("Deve rejeitar tag HTML com <")
    void deveRejeitarTagAbrindo() {
        assertFalse(validator.isValid("<script>alert(1)</script>", null));
    }

    @Test
    @DisplayName("Deve rejeitar input com >")
    void deveRejeitarTagFechando() {
        assertFalse(validator.isValid("Nome>Sobrenome", null));
    }

    @Test
    @DisplayName("Deve rejeitar aspas duplas")
    void deveRejeitarAspasDuplas() {
        assertFalse(validator.isValid("Nome \"apelido\"", null));
    }

    @Test
    @DisplayName("Deve rejeitar aspas simples")
    void deveRejeitarAspasSimples() {
        assertFalse(validator.isValid("Nome 'apelido'", null));
    }

    @Test
    @DisplayName("Deve rejeitar backtick")
    void deveRejeitarBacktick() {
        assertFalse(validator.isValid("Nome `apelido`", null));
    }

    @Test
    @DisplayName("Deve rejeitar null byte")
    void deveRejeitarNullByte() {
        assertFalse(validator.isValid("Nome\u0000injetado", null));
    }
}
