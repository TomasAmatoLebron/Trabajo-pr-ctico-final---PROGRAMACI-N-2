public class Gerente extends  Empleado {


    @Override
    public void estadoMesa(Mesa mesa) {
        if (mesa.isDisponible()) {
            mesa.setDisponible(false);

        } else {
            mesa.setDisponible(true);
        }


    }
}