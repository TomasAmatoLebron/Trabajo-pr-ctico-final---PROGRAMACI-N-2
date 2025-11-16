package com.example.tpfinalfx.model.entities;

import java.time.LocalDate;

public class Mozo extends Empleado {

    public Mozo(String nombre, String apellido, LocalDate fechaDeNacimiento, Integer dni, String password) {
        super(nombre, apellido, fechaDeNacimiento, dni, password);
    }

    public Mozo() {
        super();
    }
}