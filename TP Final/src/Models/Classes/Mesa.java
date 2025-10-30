package Models.Classes;

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
       this.disponible=!this.disponible;

    }


}
