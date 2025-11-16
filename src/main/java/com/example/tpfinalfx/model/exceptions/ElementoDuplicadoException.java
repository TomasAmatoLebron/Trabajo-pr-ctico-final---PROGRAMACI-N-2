package com.example.tpfinalfx.model.exceptions;

public class ElementoDuplicadoException extends Exception {
    public ElementoDuplicadoException(String message) {
        super("Error: elemento duplicado.");
    }
}
