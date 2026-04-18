package com.fiap.fase1.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Login ou senha inválidos");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
