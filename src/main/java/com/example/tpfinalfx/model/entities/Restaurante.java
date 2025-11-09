package com.example.tpfinalfx.model.entities;

import com.example.tpfinalfx.model.exceptions.PasswordInvalidaException;
import com.example.tpfinalfx.model.interfaces.Administrador;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Restaurante {

    private Gestora<Integer, Mesa> gestoraMesas;
    private Gestora<String, Empleado> gestoraEmpleados;
    private ConsumoDia consumoDelDia;
    private HashMap<Integer, ConsumoMesa> consumosActivosPorMesa;
    private Menu menu;

    public Restaurante() {
        this.gestoraMesas = new Gestora<>();
        this.gestoraEmpleados = new Gestora<>();
        this.consumoDelDia = new ConsumoDia();
        this.consumosActivosPorMesa = new HashMap<>();
        this.menu = new Menu();
        mesasFromJSON();
        empleadosFromJSON();
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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setConsumosActivosPorMesa(HashMap<Integer, ConsumoMesa> consumosActivosPorMesa) {
        this.consumosActivosPorMesa = consumosActivosPorMesa;
    }

    public void agregarMesa(Mesa mesa) {
        gestoraMesas.agregar(mesa.getNumeroDeMesa(), mesa);
    }

    public void agregarEmpleado(Empleado empleado) {
        gestoraEmpleados.agregar(empleado.getDni(), empleado);
    }

    public void eliminarMesa(Mesa mesa) {
        gestoraMesas.eliminar(mesa.getNumeroDeMesa());
    }

    public void eliminarEmpleado(Empleado empleado) {
        gestoraEmpleados.eliminar(empleado.getDni());
    }

    public ConsumoMesa abrirMesa(int numeroMesa) {
        Mesa mesaEncontrada = null;
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

    public void terminarJornada()
    {
        guardarEmpleados();
        guardarMesas();
        guardarConsumoDia();
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
}
