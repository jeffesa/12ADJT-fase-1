package com.fiap.fase1.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    private PasswordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PasswordValidator();
    }

    @Test
    @DisplayName("Deve rejeitar senha nula")
    void deveRejeitarSenhaNula() {
        assertFalse(validator.isValid(null, null));
    }

    @Test
    @DisplayName("Deve rejeitar senha com menos de 8 caracteres")
    void deveRejeitarSenhaCurta() {
        assertFalse(validator.isValid("Ab1", null));
    }

    @Test
    @DisplayName("Deve rejeitar senha sem letra maiúscula")
    void deveRejeitarSenhaSemMaiuscula() {
        assertFalse(validator.isValid("senha123", null));
    }

    @Test
    @DisplayName("Deve rejeitar senha sem letra minúscula")
    void deveRejeitarSenhaSemMinuscula() {
        assertFalse(validator.isValid("SENHA123", null));
    }

    @Test
    @DisplayName("Deve rejeitar senha sem número")
    void deveRejeitarSenhaSemNumero() {
        assertFalse(validator.isValid("SenhaForte", null));
    }

    @Test
    @DisplayName("Deve aceitar senha válida com 8 caracteres")
    void deveAceitarSenhaMinima() {
        assertTrue(validator.isValid("Senha123", null));
    }

    @Test
    @DisplayName("Deve aceitar senha válida longa")
    void deveAceitarSenhaLonga() {
        assertTrue(validator.isValid("MinhaSenhaSegura99", null));
    }

    @Test
    @DisplayName("Deve aceitar senha com caracteres especiais")
    void deveAceitarSenhaComEspeciais() {
        assertTrue(validator.isValid("Senha@123!", null));
    }
}
