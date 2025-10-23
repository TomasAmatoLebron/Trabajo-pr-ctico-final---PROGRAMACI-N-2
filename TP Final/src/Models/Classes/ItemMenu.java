package Models.Classes;

public class ItemMenu {
    private String nombre;
    private double precio;
    private Enum<ETipoProducto> categoria;
    private String descripcion;
    private int cantidadDePedidos;


    public ItemMenu(String nombre, double precio, Enum<ETipoProducto> categoria,
                    String descripcion, int cantidadDePedidos) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.cantidadDePedidos = cantidadDePedidos;
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

    public void setCategoria(Enum<ETipoProducto> categoria) {
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




}
