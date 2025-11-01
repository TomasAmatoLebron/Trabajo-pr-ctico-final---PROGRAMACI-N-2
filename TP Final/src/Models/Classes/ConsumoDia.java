package Models.Classes;

import Models.Enumerators.EDiaDeSemana;
import org.json.JSONArray;

import java.time.LocalDate;
import java.util.HashSet;

public class ConsumoDia {

    private LocalDate fecha;
    private EDiaDeSemana dia;
    private HashSet<ConsumoMesa> consumos;
    private double gananciaTotal;

    public ConsumoDia() {
        fecha = LocalDate.now();
        // Se obtiene el día de la semana de la fecha y se convierte a nuestro enum.
        this.dia = EDiaDeSemana.desdeDayOfWeek(fecha.getDayOfWeek());
        consumos = new HashSet<>();
        gananciaTotal = 0;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EDiaDeSemana getDia() {
        return dia;
    }

    public void setDia(EDiaDeSemana dia) {
        this.dia = dia;
    }

    public void calcularGananciaDia() {
        for (ConsumoMesa consumo : consumos) {
            gananciaTotal += consumo.getPrecioTotal();
        }
    }

    public void agregarConsumoMesa(ConsumoMesa consumoMesa) {

        consumos.add(consumoMesa);

    }

    public void eliminarConsumoMesa(ConsumoMesa consumoMesa) {

        consumos.remove(consumoMesa);

    }

    public double getGananciaTotal() {
        return gananciaTotal;
    }

    public void cerrarDia() {
        calcularGananciaDia();
    }

    public JSONArray toJSONArray() {
        JSONArray arr = new JSONArray();
        for (ConsumoMesa consumoMesa : consumos) {
            arr.put(consumoMesa.toJSON());
        }
        return arr;
    }
}
