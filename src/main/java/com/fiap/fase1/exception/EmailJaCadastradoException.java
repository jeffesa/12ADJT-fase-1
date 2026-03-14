package com.fiap.fase1.exception;

public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException(String email) {
        super("Email já cadastrado: " + email);
    }
}
