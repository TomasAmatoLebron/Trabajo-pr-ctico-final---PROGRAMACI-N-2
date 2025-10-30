package Models.Classes;

import java.util.HashMap;

public class Restaurante {


    public class SistemaRestaurante {

        private Gestora<Integer, Mesa> gestoraMesas;
        private Gestora<Integer, Mozo> gestoraMozos;
        private ConsumoDia consumoDelDia;
        private HashMap<Integer, ConsumoMesa> consumosActivosPorMesa;
        // creacion de gestora de restaurante s

        public SistemaRestaurante() {
            // creamos 2 gestoras de mezas y empleados un consumo  y un mapa de consumos
            this.gestoraMesas = new Gestora<>();
            this.gestoraMozos = new Gestora<>();
            this.consumoDelDia = new ConsumoDia();
            this.consumosActivosPorMesa = new HashMap<>();

        }



        public void agregarMesa(Mesa mesa) {
            gestoraMesas.agregar(mesa.getNumeroDeMesa(), mesa);
        }

        public void agregarMozo(Mozo mozo) {
            gestoraMozos.agregar(mozo.getDni(), mozo);
        }

        public void abrirMesa(int numeroMesa, int DniMozo) {
            Mesa mesaEncontrada = null;
            for (Mesa mesa : gestoraMesas.obtenerValores()) {
                if (mesa.getNumeroDeMesa() == numeroMesa) {
                    mesaEncontrada = mesa;
                }
            }


            Mozo mozoEncontrado = null;
            for (Mozo mozo : gestoraMozos.obtenerValores()) {
                if (mozo.getDni() == DniMozo) {
                    mozoEncontrado = mozo;
                }
            }

            if (mesaEncontrada != null && mozoEncontrado != null && mesaEncontrada.isDisponible()) {
                mesaEncontrada.modificarEstadoMesa();
                ConsumoMesa nuevoConsumo = new ConsumoMesa(mozoEncontrado, mesaEncontrada);
                consumosActivosPorMesa.put(numeroMesa, nuevoConsumo);
            }
        }

        public void agregarItemAMesa(int numeroMesa, ItemMenu item, int cantidad) {
            if (consumosActivosPorMesa.containsKey(numeroMesa)) {
                ConsumoMesa consumo = consumosActivosPorMesa.get(numeroMesa);
                consumo.agregarItem(item, cantidad);
            }
        }


        public void cerrarMesa(int numeroMesa) {
            if (consumosActivosPorMesa.containsKey(numeroMesa)) {

                ConsumoMesa consumoFinalizado = consumosActivosPorMesa.get(numeroMesa);


                consumoDelDia.agregarConsumoMesa(consumoFinalizado);

                if (gestoraMesas.obtenerClave().contains(numeroMesa)){
                    for (Mesa mesa : gestoraMesas.obtenerValores()) {
                        if (mesa.getNumeroDeMesa() == numeroMesa) {
                            mesa.modificarEstadoMesa();
                        }
                    }


                }

                consumosActivosPorMesa.remove(numeroMesa);


            }
        }

    }


}
