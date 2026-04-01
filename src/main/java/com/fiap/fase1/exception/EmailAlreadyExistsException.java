package com.fiap.fase1.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("Email já cadastrado: " + email);
    }
}
