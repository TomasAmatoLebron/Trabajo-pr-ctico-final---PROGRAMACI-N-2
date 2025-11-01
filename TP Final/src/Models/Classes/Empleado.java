package Models.Classes;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Empleado {
    private String nombre;
    private String apellido;
    private LocalDate fechaDeNacimiento;
    private String dni;
    private String password;


    public Empleado(String nombre, String apellido, LocalDate fechaDeNacimiento, String dni, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.dni = dni;
        this.password = password;
    }

    public Empleado() {
        nombre="a";
        apellido="a";
        fechaDeNacimiento=LocalDate.of(2000, 1, 1);
        dni="a";
        password="a";
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public String getDni() {
        return dni;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Empleado empleado)) return false;
        return Objects.equals(dni, empleado.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("nombre", nombre);
        json.put("apellido", apellido);
        json.put("fechaDeNacimiento", fechaDeNacimiento);
        json.put("dni", dni);
        json.put("password", password);
        return json;
    }
}
