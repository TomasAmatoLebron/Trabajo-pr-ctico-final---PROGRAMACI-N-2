package com.example.tpfinalfx.model.enums;

public enum ETipoProducto {
    ENTRADA ("Entrada"),
    PRINCIPAL("Principal"),
    POSTRE("Postre"),
    BEBIDA("Bebida");

    private String descripcion;

    ETipoProducto(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
