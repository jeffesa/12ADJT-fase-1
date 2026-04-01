package com.fiap.fase1.exception;

public class LoginAlreadyExistsException extends RuntimeException {
    public LoginAlreadyExistsException(String login) {
        super("Login já cadastrado: " + login);
    }
}
