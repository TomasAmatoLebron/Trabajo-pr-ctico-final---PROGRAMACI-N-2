import Models.Classes.*;
import Models.Classes.Menu;
import Models.Enumerators.ETipoProducto;

import java.awt.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Mesa mesa1 = new Mesa(12);
        Mozo mozo1 = new Mozo("Tomas", "Amato", LocalDate.of(1999,7,28), "42101900", "holahola");
        Menu menu = new Menu();
        Restaurante restaurante= new Restaurante();
        restaurante.agregarMesa(mesa1);
        restaurante.agregarEmpleado(mozo1);
        ConsumoMesa con = restaurante.abrirMesa(12,"42101900");
        restaurante.agregarItemAMesa(12,menu.obtenerItem("Empanada de Carne"),2);
        restaurante.agregarItemAMesa(12,menu.obtenerItem("Coca-Cola"),3);

        IO.println(con.toString());
        restaurante.cerrarMesa(12);
        restaurante.guardarConsumoDia();
        restaurante.guardarEmpleados();
    }
}