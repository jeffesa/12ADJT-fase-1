package com.fiap.fase1.exception;

public class CredenciaisInvalidasException extends RuntimeException {
    public CredenciaisInvalidasException() {
        super("Login ou senha inválidos");
    }
}
