package Models.Classes;

import Models.Enumerators.ETipoProducto;

public class ItemMenu {
    private String nombre;
    private double precio;
    private ETipoProducto categoria;
    private String descripcion;
    private int cantidadDePedidos;


    public ItemMenu(String nombre, double precio, ETipoProducto categoria, String descripcion) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.cantidadDePedidos = 0;
    }

    public ItemMenu() {
        nombre = "a";
        precio = 0.0;
        categoria = ETipoProducto.PRINCIPAL;
        descripcion = "a";
        cantidadDePedidos = 0;
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

    public int getCantidadDePedidos() {
        return cantidadDePedidos;
    }

    public void setCantidadDePedidos(int cantidadDePedidos) {
        this.cantidadDePedidos = cantidadDePedidos;
    }

    @Override
    public String toString() {
        return nombre + " - Precio: " + precio + " ";
    }
}
