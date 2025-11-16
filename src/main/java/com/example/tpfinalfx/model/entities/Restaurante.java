package com.example.tpfinalfx.model.entities;

import com.example.tpfinalfx.model.enums.ETipoProducto;
import com.example.tpfinalfx.model.exceptions.ElementoDuplicadoException;
import com.example.tpfinalfx.model.exceptions.ElementoInexistenteException;
import com.example.tpfinalfx.model.exceptions.PasswordInvalidaException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;

public class Restaurante {

    private Gestora<Integer, Mesa> gestoraMesas;
    private Gestora<String, Empleado> gestoraEmpleados;
    private ConsumoDia consumoDelDia;
    private HashMap<Integer, ConsumoMesa> consumosActivosPorMesa;
    private Gestora<String, ItemMenu> menu;

    public Restaurante() {
        this.gestoraMesas = new Gestora<>();
        this.gestoraEmpleados = new Gestora<>();
        this.consumoDelDia = new ConsumoDia();
        this.consumosActivosPorMesa = new HashMap<>();
        this.menu = new Gestora<>();
        mesasFromJSON();
        empleadosFromJSON();
        menuFromJSON();
    }

    public Gestora<Integer, Mesa> getGestoraMesas() {
        return gestoraMesas;
    }

    public void setGestoraMesas(Gestora<Integer, Mesa> gestoraMesas) {
        this.gestoraMesas = gestoraMesas;
    }

    public Gestora<String, Empleado> getGestoraEmpleados() {
        return gestoraEmpleados;
    }

    public void setGestoraEmpleados(Gestora<String, Empleado> gestoraEmpleados) {
        this.gestoraEmpleados = gestoraEmpleados;
    }

    public ConsumoDia getConsumoDelDia() {
        return consumoDelDia;
    }

    public void setConsumoDelDia(ConsumoDia consumoDelDia) {
        this.consumoDelDia = consumoDelDia;
    }

    public HashMap<Integer, ConsumoMesa> getConsumosActivosPorMesa() {
        return consumosActivosPorMesa;
    }

    public void setConsumosActivosPorMesa(HashMap<Integer, ConsumoMesa> consumosActivosPorMesa) {
        this.consumosActivosPorMesa = consumosActivosPorMesa;
    }

    public Gestora<String, ItemMenu> getMenu() {
        return menu;
    }

    public void setMenu(Gestora<String, ItemMenu> menu) {
        this.menu = menu;
    }

    public void menuFromJSON() {
        JSONTokener tokener = JSONUtils.leer("menu.json");
        if (tokener == null) {
            return;
        }
        try {
            JSONArray arreglo = new JSONArray(tokener);
            for (int i = 0; i < arreglo.length(); i++) {
                ItemMenu item = new ItemMenu();
                JSONObject jsonMenu = arreglo.getJSONObject(i);
                item.setNombre(jsonMenu.getString("Nombre"));
                item.setPrecio(jsonMenu.getDouble("Precio"));
                item.setDescripcion(jsonMenu.getString("Descripcion"));
                item.setCategoria(ETipoProducto.valueOf(jsonMenu.getString("Categoria")));
                menu.agregar(item.getNombre(), item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void mesasFromJSON() {
        JSONTokener tokener = JSONUtils.leer("mesas.json");
        if (tokener == null) {
            return;
        }
        try {
            JSONArray arreglo = new JSONArray(tokener);
            for (int i = 0; i < arreglo.length(); i++) {
                Mesa mesa = new Mesa();
                JSONObject jsonMesa = arreglo.getJSONObject(i);
                mesa.setNumeroDeMesa(jsonMesa.getInt("Numero de mesa"));
                mesa.setDisponible(jsonMesa.getBoolean("Disponible"));
                gestoraMesas.agregar(mesa.getNumeroDeMesa(), mesa);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void empleadosFromJSON() {
        org.json.JSONTokener tokener = JSONUtils.leer("empleados.json");
        if (tokener == null) {
            return;
        }
        try {
            JSONArray arreglo = new JSONArray(tokener);
            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject empleadoJSON = arreglo.getJSONObject(i);
                String puesto = empleadoJSON.getString("Puesto");
                Empleado empleado = crearEmpleadoPorPuesto(puesto);

                if (empleado != null) {
                    empleado.setDni(empleadoJSON.getString("DNI"));
                    empleado.setNombre(empleadoJSON.getString("Nombre"));
                    empleado.setApellido(empleadoJSON.getString("Apellido"));
                    empleado.setPassword(empleadoJSON.getString("Contraseña"));
                    empleado.setFechaDeNacimiento(LocalDate.parse(empleadoJSON.getString("Fecha de nacimiento"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    gestoraEmpleados.agregar(empleado.getDni(), empleado);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void agregar(ItemMenu item) {
        try {
            menu.agregar(item.getNombre(), item);
        } catch (ElementoDuplicadoException e) {
            mostrarAlertaElementoDuplicado();
        }
    }

    public void eliminar(ItemMenu item) {
        try {
            menu.eliminar(item.getNombre());
        }
        catch (ElementoInexistenteException e) {
            mostrarAlertaElementoInexistente();
        }
    }

    public void agregar(Mesa mesa) {
        try {
            gestoraMesas.agregar(mesa.getNumeroDeMesa(), mesa);
        } catch (ElementoDuplicadoException e) {
            mostrarAlertaElementoDuplicado();
        }
    }

    public void agregar(Empleado empleado) throws  ElementoDuplicadoException {
        try {
            gestoraEmpleados.agregar(empleado.getDni(), empleado);
        }
        catch (ElementoDuplicadoException e) {
            mostrarAlertaElementoDuplicado();
        }
    }

    public void eliminar(Mesa mesa) {
        try {
            gestoraMesas.eliminar(mesa.getNumeroDeMesa());
        } catch (ElementoInexistenteException e) {
            mostrarAlertaElementoInexistente();
        }
    }

    public void eliminar(Empleado empleado) {
        try {
            gestoraEmpleados.eliminar(empleado.getDni());
        } catch (ElementoInexistenteException e) {
            mostrarAlertaElementoInexistente();
        }
    }

    public ConsumoMesa abrirMesa(int numeroMesa) {
        Mesa mesaEncontrada = getGestoraMesas().obtenerMesa(numeroMesa);
        for (Mesa mesa : gestoraMesas.obtenerValores()) {
            if (mesa.getNumeroDeMesa() == numeroMesa) {
                mesa.modificarEstadoMesa();
                ConsumoMesa nuevoConsumo = new ConsumoMesa(mesaEncontrada);
                consumosActivosPorMesa.put(numeroMesa, nuevoConsumo);
                return nuevoConsumo;
            }
        }
        return null;
    }

    public void agregarItemAMesa(int numeroMesa, ItemMenu item, int cantidad) {
        if (consumosActivosPorMesa.containsKey(numeroMesa)) {
            ConsumoMesa consumo = consumosActivosPorMesa.get(numeroMesa);
            consumo.agregarItem(item, cantidad);
        }
    }


    public void cerrarMesa(int numeroMesa) {
        if (consumosActivosPorMesa.containsKey(numeroMesa)) {

            ConsumoMesa consumoFinalizado = consumosActivosPorMesa.get(numeroMesa);

            consumoDelDia.agregarConsumoMesa(consumoFinalizado);

            if (gestoraMesas.obtenerClave().contains(numeroMesa)) {
                for (Mesa mesa : gestoraMesas.obtenerValores()) {
                    if (mesa.getNumeroDeMesa() == numeroMesa) {
                        mesa.modificarEstadoMesa();
                    }
                }
            }
            consumosActivosPorMesa.remove(numeroMesa);
        }
    }

    public void guardarConsumoDia() {
        LocalDate fecha = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaString = fecha.format(formatter);
        consumoDelDia.cerrarDia();
        JSONUtils.grabarJSON(fechaString + ".json", consumoDelDia.toJSONArray());
    }

    public JSONArray empleadosToArray() {
        try {
            JSONArray array = new JSONArray();
            for (Empleado empleado : gestoraEmpleados.obtenerValores()) {
                array.put(empleado.toJSON());
            }
            return array;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void guardarEmpleados() {
        JSONUtils.grabarJSON("empleados.json", empleadosToArray());
    }

    public JSONArray mesasToArray() {
        JSONArray array = new JSONArray();
        for (Mesa mesa : gestoraMesas.obtenerValores()) {
            array.put(mesa.toJSON());
        }
        return array;
    }

    public void guardarMesas() {
        JSONUtils.grabarJSON("mesas.json", mesasToArray());
    }

    public Empleado crearEmpleadoPorPuesto(String puesto) {
        switch (puesto) {
            case "Mozo":
                return new Mozo();
            case "Cajero":
                return new Cajero();
            case "Gerente":
                return new Gerente();
            default:
                return null;
        }
    }

    public JSONArray menuToArray() {
        JSONArray array = new JSONArray();
        for (ItemMenu item : menu.obtenerValores()) {
            array.put(item.toJSON());
        }
        return array;
    }

    public void guardarMenu() {
        JSONUtils.grabarJSON("menu.json", menuToArray());
    }

    public void terminarJornada()
    {
        for (Mesa mesa : gestoraMesas.obtenerValores()) {
            cerrarMesa(mesa.getNumeroDeMesa());
        }
        guardarEmpleados();
        guardarMesas();
        guardarConsumoDia();
        guardarMenu();
    }

    public Empleado verificarPassword(String pass) throws PasswordInvalidaException {
        for (Empleado emp : gestoraEmpleados.obtenerValores()) {
            if (pass.equals(emp.getPassword()))
            {
                return emp;
            }
        }
        throw new PasswordInvalidaException("¡Contraseña inválida!");
    }


    private void mostrarAlertaElementoInexistente() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Elemento no encontrado");
        alerta.setHeaderText(null);
        alerta.setContentText("No se ha encontrado el elemento ingresado.");

        alerta.getButtonTypes().setAll(new ButtonType("Continuar"));

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent()) {
            alerta.close();
        }
    }

    private void mostrarAlertaElementoDuplicado() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Elemento duplicado");
        alerta.setHeaderText(null);
        alerta.setContentText("Ya se encuentra el elemento ingresado.");

        alerta.getButtonTypes().setAll(new ButtonType("Continuar"));

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent()) {
            alerta.close();
        }
    }
}
