package Models.Classes;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ConsumoMesa {

    private int id;
    private static int contador = 0;
    private Mozo mozo;
    private Mesa mesa;
    private HashMap<ItemMenu, Integer> consumo;
    private double precioTotal;
    private LocalDateTime fecha;

    public ConsumoMesa(Mozo mozo, Mesa mesa) {
        this.mozo = mozo;
        contador++;
        id = contador;
        consumo = new HashMap<ItemMenu, Integer>();
        precioTotal = 0;
        fecha = LocalDateTime.now();
        this.mesa = mesa;
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

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public HashMap<ItemMenu, Integer> getConsumo() {
        return consumo;
    }

    public double getPrecioTotal() {

        precioFinal();
        return precioTotal;
    }

    public void precioFinal() {
        double subtotal = 0;
        for (ItemMenu item: consumo.keySet())
        {
            subtotal+=item.getPrecio()*consumo.get(item);
        }
        precioTotal = subtotal;
    }

    public void agregarItem(ItemMenu item, int cantidad) {

        if (!consumo.containsKey(item)) {
            consumo.put(item, cantidad);
        } else {
            consumo.put(item, consumo.get(item) + cantidad);
        }

    }

    public void eliminarItem(ItemMenu item, int cantidad) {

        if (consumo.containsKey(item)) {
            consumo.put(item, consumo.get(item) - cantidad);
        }

    }

    @Override
    public String toString() {

        precioFinal();
        return "Consumo:\n[ID = " + id + "\nMesa: " + mesa.getNumeroDeMesa() + "\nMozo: " + mozo.getNombre() + " " +mozo.getApellido() + "\nDetalle:\n" + mostrarConsumo() + "Precio total: " + precioTotal + "]";

    }

    public String mostrarConsumo() {

        StringBuilder sb = new StringBuilder();
        for (Map.Entry entrada: consumo.entrySet())
        {
            sb.append("        " + entrada.getKey().toString() + " (" + entrada.getValue() + ")\n");
        }
        return sb.toString();
    }

    public JSONObject toJSON()
    {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("mesa", mesa.getNumeroDeMesa());
        json.put("mozo", mozo.getNombre());
        json.put("precioTotal", precioTotal);
        json.put("fecha", fecha);
        json.put("consumo", consumo);
        return json;
    }
}