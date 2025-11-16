package com.example.tpfinalfx.model.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Empleado {
    private String nombre;
    private String apellido;
    private LocalDate fechaDeNacimiento;
    private Integer dni;
    private String password;


    public Empleado(String nombre, String apellido, LocalDate fechaDeNacimiento, Integer dni, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.dni = dni;
        this.password = password;
    }

    public Empleado() {
        nombre = "a";
        apellido = "a";
        fechaDeNacimiento = LocalDate.of(2000, 1, 1);
        dni = 10000000;
        password = "a";
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

    public Integer getDni() {
        return dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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
        try {
            JSONObject json = new JSONObject();
            json.put("Nombre", nombre);
            json.put("Apellido", apellido);
            json.put("Fecha de nacimiento", fechaDeNacimiento);
            json.put("DNI", dni);
            json.put("Contraseña", password);
            json.put("Puesto", getClass().getSimpleName());
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}