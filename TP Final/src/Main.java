import Models.Classes.*;
import Models.Classes.Menu;
import Models.Enumerators.ETipoProducto;

import java.awt.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Mesa mesa2 = new Mesa(9);
        Mesa mesa1 = new Mesa(12);
        Mozo mozo1 = new Mozo("Tomas", "Amato", LocalDate.of(1999,7,28), "42101900", "holahola");
        Mozo mozo2 = new Mozo("Marcelo", "Gallardo", LocalDate.of(1976,1,18), "24482933", "chauchau");
        Menu menu = new Menu();
        Restaurante restaurante= new Restaurante();
        restaurante.agregarMesa(mesa1);
        restaurante.agregarMesa(mesa2);
        restaurante.agregarEmpleado(mozo1);
        restaurante.agregarEmpleado(mozo2);
        ConsumoMesa con1 = restaurante.abrirMesa(12,"42101900");
        ConsumoMesa con2 = restaurante.abrirMesa(9,"24482933");
        restaurante.agregarItemAMesa(12,menu.obtenerItem("Empanada de Carne"),2);
        restaurante.agregarItemAMesa(12,menu.obtenerItem("Coca-Cola"),3);
        restaurante.agregarItemAMesa(9,menu.obtenerItem("Bife de Chorizo"),1);
        restaurante.agregarItemAMesa(9,menu.obtenerItem("Helado"),4);
        restaurante.cerrarMesa(12);
        restaurante.cerrarMesa(9);
        restaurante.terminarJornada();

    }
}