package Models.Classes;

public class Mesa {
    private int numeroDeMesa;
    private boolean disponible;

    public Mesa(int numeroDeMesa, boolean disponible) {
        this.numeroDeMesa = numeroDeMesa;
        this.disponible = disponible;
    }

    public Mesa() {
        numeroDeMesa=100;
        disponible=true;
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
}
