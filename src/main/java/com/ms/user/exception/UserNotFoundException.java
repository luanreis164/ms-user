package com.ms.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Usuario não encontrado ou inativo.");
    }
}
