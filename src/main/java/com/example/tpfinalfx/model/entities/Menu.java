package com.example.tpfinalfx.model.entities;

import com.example.tpfinalfx.model.enums.ETipoProducto;

import java.util.HashSet;
import java.util.Set;

public class Menu {
    private Set<ItemMenu> items;

    public Set<ItemMenu> getItems() {
        return items;
    }

    public void setItems(Set<ItemMenu> items) {
        this.items = items;
    }

    public Menu() {
        this.items = new HashSet<>() {
        };
        crearMenu();
    }

    //Cada vez que se inicialice un menu se crea automaticamente con estos items, si se desea agregar items al menu
    // deberia agregarse en la funcion crear menu
    private void crearMenu() {
        // Entradas
        items.add(new ItemMenu("Provoleta", 12.50, ETipoProducto.ENTRADA, "Queso provolone a la parrilla"));
        items.add(new ItemMenu("Empanada de Carne", 3.50, ETipoProducto.ENTRADA, "Empanada frita de carne cortada a cuchillo"));

        // Principales
        items.add(new ItemMenu("Bife de Chorizo", 25.00, ETipoProducto.PRINCIPAL, "Corte de carne argentino a la parrilla"));
        items.add(new ItemMenu("Milanesa a la Napolitana", 22.00, ETipoProducto.PRINCIPAL, "Milanesa de ternera con salsa de tomate, jamón y queso"));

        // Postres
        items.add(new ItemMenu("Flan con Dulce de Leche", 8.00, ETipoProducto.POSTRE, "Postre tradicional argentino"));
        items.add(new ItemMenu("Helado", 7.50, ETipoProducto.POSTRE, "Dos bochas de helado artesanal"));

        // Bebidas
        items.add(new ItemMenu("Agua sin gas", 3.00, ETipoProducto.BEBIDA, "Botella de 500ml"));
        items.add(new ItemMenu("Coca-Cola", 4.00, ETipoProducto.BEBIDA, "Lata de 354ml"));
    }

    //Metodo para obtener item desde el menu en base a un String
    public ItemMenu obtenerItem(String item) {
        for (ItemMenu itemMenu : items) {
            if (itemMenu.getNombre().equals(item)) {
                return itemMenu;
            }
        }
        return null;
    }
}