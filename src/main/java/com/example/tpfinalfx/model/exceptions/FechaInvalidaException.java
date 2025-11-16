package com.example.tpfinalfx.model.exceptions;

public class FechaInvalidaException extends Exception {
    public FechaInvalidaException() {
        super("¡La fecha ingresada no es válida!");
    }
}
