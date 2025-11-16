package com.example.tpfinalfx.model.exceptions;

public class MesaInvalidaException extends Exception {
    public MesaInvalidaException() {
        super("El número de mesa ingresado no es válido.");
    }
}
