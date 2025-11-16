package com.example.tpfinalfx.model.entities;

import com.example.tpfinalfx.model.exceptions.ElementoDuplicadoException;
import com.example.tpfinalfx.model.exceptions.ElementoInexistenteException;

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

}