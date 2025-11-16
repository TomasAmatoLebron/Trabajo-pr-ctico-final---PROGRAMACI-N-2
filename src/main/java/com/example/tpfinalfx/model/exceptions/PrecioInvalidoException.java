package com.example.tpfinalfx.model.exceptions;

public class PrecioInvalidoException extends RuntimeException {
    public PrecioInvalidoException() {
        super("El precio ingresado no es válido.");
    }
}
