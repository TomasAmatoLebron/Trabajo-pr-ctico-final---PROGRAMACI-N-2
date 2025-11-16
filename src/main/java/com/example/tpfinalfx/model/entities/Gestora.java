package com.example.tpfinalfx.model.entities;

import com.example.tpfinalfx.model.enums.ETipoProducto;
import com.example.tpfinalfx.model.exceptions.ElementoDuplicadoException;
import com.example.tpfinalfx.model.exceptions.ElementoInexistenteException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Gestora<T> {

    private HashMap<Integer, T> listado;

    public Gestora() {
        listado = new HashMap<>();
    }

    public boolean agregar(Integer num, T t) throws ElementoDuplicadoException {
        if (!listado.containsKey(num)) {
            listado.put(num, t);
            return true;
        } else {
            throw new ElementoDuplicadoException("¡Ya se encuentra el elemento ingresado!");
        }
    }

    public boolean eliminar(Integer num) throws ElementoInexistenteException {
        if (listado.containsKey(num)) {
            listado.remove(num);
            return true;
        } else {
            throw new ElementoInexistenteException("¡No se encuentra el elemento ingresado!");
        }
    }

    public Set<Integer> obtenerClave() {
        return listado.keySet();
    }

    public Collection<T> obtenerValores() {
        return listado.values();
    }

    public T obtenerMesa(Integer num) {
        return listado.get(num);
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
                item.setId(jsonMenu.getInt("ID"));
                item.setNombre(jsonMenu.getString("Nombre"));
                item.setPrecio(jsonMenu.getDouble("Precio"));
                item.setDescripcion(jsonMenu.getString("Descripcion"));
                item.setCategoria(ETipoProducto.valueOf(jsonMenu.getString("Categoria")));
                agregar(item.getId(), (T) item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ElementoDuplicadoException e) {

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
                agregar(mesa.getNumeroDeMesa(), (T) mesa);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ElementoDuplicadoException e) {

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
                    empleado.setDni(empleadoJSON.getInt("DNI"));
                    empleado.setNombre(empleadoJSON.getString("Nombre"));
                    empleado.setApellido(empleadoJSON.getString("Apellido"));
                    empleado.setPassword(empleadoJSON.getString("Contraseña"));
                    empleado.setFechaDeNacimiento(LocalDate.parse(empleadoJSON.getString("Fecha de nacimiento"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    agregar(empleado.getDni(), (T) empleado);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ElementoDuplicadoException e) {

        }
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


}