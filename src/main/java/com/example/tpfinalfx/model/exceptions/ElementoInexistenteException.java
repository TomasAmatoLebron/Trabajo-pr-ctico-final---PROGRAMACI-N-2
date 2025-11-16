package com.example.tpfinalfx.model.exceptions;

public class ElementoInexistenteException extends Exception {
    public ElementoInexistenteException(String message) {
        super("¡El elemento ingresado no existe!");
    }
}
