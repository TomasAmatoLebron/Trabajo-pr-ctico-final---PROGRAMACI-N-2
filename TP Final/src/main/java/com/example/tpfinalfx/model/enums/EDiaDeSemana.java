package com.example.tpfinalfx.model.enums;

import java.time.DayOfWeek;

public enum EDiaDeSemana {
    LUNES("lunes"),
    MARTES("Martes"),
    MIERCOLES("Miercoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sabado"),
    DOMINGO("Domingo");

    private String descripcion;

    EDiaDeSemana(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public static EDiaDeSemana desdeDayOfWeek(DayOfWeek diaJava) {
        switch (diaJava) {
            case MONDAY:
                return LUNES;
            case TUESDAY:
                return MARTES;
            case WEDNESDAY:
                return MIERCOLES;
            case THURSDAY:
                return JUEVES;
            case FRIDAY:
                return VIERNES;
            case SATURDAY:
                return SABADO;
            case SUNDAY:
                return DOMINGO;
        }
        return null;
    }
}