package com.example.tpfinalfx.model.entities;

import com.example.tpfinalfx.model.enums.ETipoProducto;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ItemMenu {
    private String nombre;
    private double precio;
    private ETipoProducto categoria;
    private String descripcion;


    public ItemMenu(String nombre, double precio, ETipoProducto categoria, String descripcion) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public ItemMenu() {
        nombre = "a";
        precio = 0.0;
        categoria = ETipoProducto.PRINCIPAL;
        descripcion = "a";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Enum<ETipoProducto> getCategoria() {
        return categoria;
    }

    public void setCategoria(ETipoProducto categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre + " - Precio: " + precio + " ";
    }

    public JSONObject toJSON() {
        try {
            JSONObject json = new JSONObject();
            json.put("Nombre", nombre);
            json.put("Precio", precio);
            json.put("Categoria", ETipoProducto.valueOf(categoria.toString()));
            json.put("Descripcion", descripcion);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ItemMenu itemMenu)) return false;
        return Objects.equals(nombre, itemMenu.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }
}