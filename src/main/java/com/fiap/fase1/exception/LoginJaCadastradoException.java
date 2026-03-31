package com.fiap.fase1.exception;

public class LoginJaCadastradoException extends RuntimeException {
    public LoginJaCadastradoException(String login) {
        super("Login já cadastrado: " + login);
    }
}
