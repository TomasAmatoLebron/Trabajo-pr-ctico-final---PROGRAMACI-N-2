package com.example.tpfinalfx.model.exceptions;

public class PasswordIncompatibleException extends Exception {
    public PasswordIncompatibleException() {
        super("La contraseña ingresada es muy larga (máximo 12 caracteres).");
    }
}
