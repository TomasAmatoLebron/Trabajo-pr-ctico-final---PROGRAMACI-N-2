package Models.Classes;

import Models.Interfaces.Administrador;

import java.time.LocalDate;

public class Gerente extends Empleado implements Administrador {

    public Gerente(String nombre, String apellido, LocalDate fechaDeNacimiento, String dni, String password) {
        super(nombre, apellido, fechaDeNacimiento, dni, password);
    }

    public Gerente() {
        super();
    }
}