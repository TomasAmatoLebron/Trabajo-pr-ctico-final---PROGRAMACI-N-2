import Models.Classes.ConsumoMesa;
import Models.Classes.ItemMenu;
import Models.Classes.Mesa;
import Models.Classes.Mozo;
import Models.Enumerators.ETipoProducto;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Mesa mesa1 = new Mesa(1);
        Mozo mozo1 = new Mozo("Tomas", "Amato", LocalDate.of(1999,7,28), "42101900", "holahola");
        ItemMenu i1 = new ItemMenu("Hamburguesa", 6500, ETipoProducto.PRINCIPAL, "Con queso");
        ItemMenu i2 = new ItemMenu("Coca", 3100, ETipoProducto.BEBIDA, "Clasica");
        ConsumoMesa cm1 = new ConsumoMesa(mozo1, mesa1);

        cm1.agregarItem(i1, 2);
        cm1.agregarItem(i1, 1);
        cm1.agregarItem(i2, 4);
        cm1.eliminarItem(i1, 1);

        IO.println(cm1.toString());
    }
}