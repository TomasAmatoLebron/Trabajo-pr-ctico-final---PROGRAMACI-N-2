import java.util.HashMap;
import java.util.Map;

public class Gestora <T>{

    private HashMap<Integer, T> listado;
    private static int id=1;

    public Gestora() {
        listado = new HashMap<>();
    }

    public HashMap<Integer, T> getListado() {
        return listado;
    }

    public boolean agregar(T t)
    {
        if(!listado.containsValue(t)) {
            listado.put(id, t);            id++;
            return true;
        }
        else {
            return false;
        }
    }

    public Integer conseguirId(T t)
    {
        for (Map.Entry<Integer, T> item:listado.entrySet())
        {
            if (t.equals(item))
            {
                return item.getKey();
            }
        }
        return -1;
    }

    public boolean eliminar(T t)
    {
        int id1= conseguirId(t);
        if (id1!=-1)
        {
               listado.remove(id1);
               return true;
        }
        else {
            return false;
        }
    }

    public String buscar(String a)
    {
        for(T value: listado.values()){


            if(a.equals(value.))

        }
    }
}
