package Models.Classes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Gestora<K, T> {

    private HashMap<K, T> listado;

    public Gestora() {
        listado = new HashMap<>();
    }

    public boolean agregar(K k, T t) {
        if (!listado.containsKey(k)) {
            listado.put(k, t);
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminar(K k) {
        if (listado.containsKey(k)) {
            listado.remove(k);
            return true;
        } else {
            return false;
        }
    }

    public Set<K> obtenerClave()
    {
        return listado.keySet();
    }

    public Collection<T> obtenerValores()
    {
        return listado.values();
    }


    }


