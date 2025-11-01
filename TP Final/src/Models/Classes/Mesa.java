package Models.Classes;

import org.json.JSONException;
import org.json.JSONObject;

public class Mesa {
    private int numeroDeMesa;
    private boolean disponible;

    public Mesa(int numeroDeMesa) {
        this.numeroDeMesa = numeroDeMesa;
        disponible = true;
    }

    public Mesa() {
        numeroDeMesa = 100;
        disponible = true;
    }

    public int getNumeroDeMesa() {
        return numeroDeMesa;
    }

    public void setNumeroDeMesa(int numeroDeMesa) {
        this.numeroDeMesa = numeroDeMesa;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }


    public void modificarEstadoMesa() {
        this.disponible = !this.disponible;

    }

    public JSONObject toJSON() {
        try {
            JSONObject json = new JSONObject();
            json.put("numeroDeMesa", numeroDeMesa);
            json.put("disponible", disponible);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
