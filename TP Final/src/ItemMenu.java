public class ItemMenu {

    private String nombre;
    private double precio;
    private ETipoProducto categoria;
    private String descripcion;
    private int cantPedidos;

    public ItemMenu(String nombre, double precio, ETipoProducto categoria, String descripcion) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.descripcion = descripcion;
        cantPedidos=0;
    }

    public ItemMenu() {
        nombre="a";
        precio=1;
        categoria=ETipoProducto.ENTRADA;
        descripcion="a";
        cantPedidos=0;
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

    public ETipoProducto getCategoria() {
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

    public int getCantPedidos() {
        return cantPedidos;
    }

    public void setCantPedidos(int cantPedidos) {
        this.cantPedidos = cantPedidos;
    }

}
