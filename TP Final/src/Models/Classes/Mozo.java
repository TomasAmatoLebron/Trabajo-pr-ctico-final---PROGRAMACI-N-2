package Models.Classes;

import java.time.LocalDate;

public class Mozo extends Empleado {

    public Mozo(String nombre, String apellido, LocalDate fechaDeNacimiento, String dni, String password) {
        super(nombre, apellido, fechaDeNacimiento, dni, password);
    }

    public Mozo() {
        super();
    }
}