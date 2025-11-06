package com.example.tpfinalfx.model.entities;

import java.time.LocalDate;

public class Cajero extends Empleado {

    public Cajero(String nombre, String apellido, LocalDate fechaDeNacimiento, String dni, String password) {
        super(nombre, apellido, fechaDeNacimiento, dni, password);
    }

    public Cajero() {
        super();
    }
}
