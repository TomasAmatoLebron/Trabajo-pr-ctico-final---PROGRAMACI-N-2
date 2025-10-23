import java.util.HashMap;

public class ConsumoMesa {

    private int id;
    private int contador = 0;
    private Mozo mozo;
    private HashMap<ItemMenu, Integer> consumo;
    private double precioTotal;

    public ConsumoMesa(Mozo mozo, double precioTotal) {
        this.mozo = mozo;
        this.contador++;
        id = contador;
        consumo = new HashMap<ItemMenu, Integer>();


    }


    public void setMozo(Mozo mozo) {
        this.mozo = mozo;
    }

    public void setConsumo(HashMap<ItemMenu, Integer> consumo) {
        this.consumo = consumo;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public int getId() {
        return id;
    }

    public int getContador() {
        return contador;
    }

    public Mozo getMozo() {
        return mozo;
    }

    public HashMap<ItemMenu, Integer> getConsumo() {
        return consumo;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void precioFinal() {
        double subtotal = 0;
        for (ItemMenu value : consumo.keySet()) {

            subtotal += value.getPrecio();


        }
        setPrecioTotal(subtotal);
    }

    public void agregarItem(ItemMenu item,int cantidad){

        consumo.put(item,cantidad);


    }
    public void eliminarItem(ItemMenu item){

        consumo.remove(item);


    }

    }