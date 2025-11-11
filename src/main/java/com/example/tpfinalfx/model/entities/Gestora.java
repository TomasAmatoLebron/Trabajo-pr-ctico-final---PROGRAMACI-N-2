package com.example.tpfinalfx.model.entities;

import com.example.tpfinalfx.model.exceptions.ElementoDuplicadoException;
import com.example.tpfinalfx.model.exceptions.ElementoInexistenteException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Gestora<K, T> {

    private HashMap<K, T> listado;

    public Gestora() {
        listado = new HashMap<>();
    }

    public boolean agregar(K k, T t) throws ElementoDuplicadoException {
        if (!listado.containsKey(k)) {
            listado.put(k, t);
            return true;
        } else {
            throw new ElementoDuplicadoException("¡Ya se encuentra el elemento ingresado!");
        }
    }

    public boolean eliminar(K k) throws ElementoInexistenteException {
        if (listado.containsKey(k)) {
            listado.remove(k);
            return true;
        } else {
            throw new ElementoInexistenteException("¡No se encuentra el elemento ingresado!");
        }
    }

    public Set<K> obtenerClave() {
        return listado.keySet();
    }

    public Collection<T> obtenerValores() {
        return listado.values();
    }

    public T obtenerMesa(K k) {
        return listado.get(k);
    }

}