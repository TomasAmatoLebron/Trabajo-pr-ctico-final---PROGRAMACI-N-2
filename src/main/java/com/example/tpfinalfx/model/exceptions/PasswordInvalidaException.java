package com.example.tpfinalfx.model.exceptions;

public class PasswordInvalidaException extends Exception {
    public PasswordInvalidaException(String message) {
        super("¡La contraseña introducida no es válida!");
    }
}
