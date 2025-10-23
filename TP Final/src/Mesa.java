public class Mesa {
    public int numeroDeMesa;
    public boolean disponible;

    public Mesa(int numeroDeMesa, boolean disponible) {
        this.numeroDeMesa = numeroDeMesa;
        this.disponible = disponible;
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
        if(isDisponible()){
            disponible=false;
        }
        else{ disponible=true
        ;}





    }


}
