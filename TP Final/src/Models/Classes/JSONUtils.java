package Models.Classes;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONUtils {


    public static void grabarJSON(String ruta, JSONArray array)
    {
        try {
            FileWriter file = new FileWriter(ruta);
            file.write(array.toString());
        }
        catch (IOException e)
            {
            e.printStackTrace();
            }
    }

    public static JSONTokener leer(String archivo)
    {
        JSONTokener tokener = null;
        try {
            tokener = new JSONTokener(new FileReader(archivo));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tokener;
    }
}