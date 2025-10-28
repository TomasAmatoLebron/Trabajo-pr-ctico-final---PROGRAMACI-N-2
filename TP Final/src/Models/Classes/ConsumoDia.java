package Models.Classes;

import Models.Enumerators.EDiaDeSemana;

import java.time.LocalDate;
import java.util.HashSet;

public class ConsumoDia {

    private LocalDate fecha;
    private EDiaDeSemana dia;
    private HashSet<ConsumoMesa> consumos;
    private double gananciaTotal;

    public ConsumoDia(EDiaDeSemana dia) {
        fecha = LocalDate.now();
        this.dia = dia;
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

    public void cerrarDia() {
        calcularGananciaDia();
    }
}
