public class Cajero extends Empleado {
private double horasTrabajadas;





    @Override
    public void estadoMesa(Mesa mesa) {
     if (mesa.isDisponible()){
         mesa.setDisponible(false);

     }else {
         mesa.setDisponible(true);
     }




    }
}
