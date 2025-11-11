package com.example.tpfinalfx;

import com.example.tpfinalfx.model.entities.ConsumoMesa;
import com.example.tpfinalfx.model.entities.Mesa;
import com.example.tpfinalfx.model.entities.Restaurante;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CajerosController {

    private Restaurante miRestaurante;

    @FXML
    private Button mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, mesa7, mesa8, mesa9, mesa10, mesa11, mesa12;
    @FXML
    private Button cerrar;
    @FXML
    private Map<Button, Mesa> mesas = new HashMap<>();

    public void setRestaurante(Restaurante restaurante) {
        this.miRestaurante = restaurante;
        inicializarMesas();
    }

    private void inicializarMesas() {
        mesas.put(mesa1, miRestaurante.getGestoraMesas().obtenerMesa(1));
        mesas.put(mesa2, miRestaurante.getGestoraMesas().obtenerMesa(2));
        mesas.put(mesa3, miRestaurante.getGestoraMesas().obtenerMesa(3));
        mesas.put(mesa4, miRestaurante.getGestoraMesas().obtenerMesa(4));
        mesas.put(mesa5, miRestaurante.getGestoraMesas().obtenerMesa(5));
        mesas.put(mesa6, miRestaurante.getGestoraMesas().obtenerMesa(6));
        mesas.put(mesa7, miRestaurante.getGestoraMesas().obtenerMesa(7));
        mesas.put(mesa8, miRestaurante.getGestoraMesas().obtenerMesa(8));
        mesas.put(mesa9, miRestaurante.getGestoraMesas().obtenerMesa(9));
        mesas.put(mesa10, miRestaurante.getGestoraMesas().obtenerMesa(10));
        mesas.put(mesa11, miRestaurante.getGestoraMesas().obtenerMesa(11));
        mesas.put(mesa12, miRestaurante.getGestoraMesas().obtenerMesa(12));

        mesas.forEach(this::actualizarVisualMesa);
    }

    private void actualizarVisualMesa(Button boton, Mesa mesa) {
        if (mesa == null) return;
        VBox content = new VBox(5);
        content.setAlignment(Pos.CENTER);
        Label lblMesa = new Label("Mesa " + mesa.getNumeroDeMesa());
        lblMesa.setFont(new Font("System Bold", 16));
        Label lblEstado = new Label(mesa.isDisponible() ? "Disponible" : "Ocupada");
        lblEstado.setTextFill(mesa.isDisponible() ? Color.web("#155724") : Color.web("#721c24"));
        content.getChildren().addAll(lblMesa, lblEstado);
        boton.setGraphic(content);

        if (mesa.isDisponible()) {
            boton.setStyle("-fx-background-color: #d4edda; -fx-border-color: #c3e6cb; -fx-border-radius: 5; -fx-background-radius: 5;");
        } else {
            boton.setStyle("-fx-background-color: #f8d7da; -fx-border-color: #f5c6cb; -fx-border-radius: 5; -fx-background-radius: 5;");
        }
    }

    @FXML
    private void mostrarMenuMesa(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY && event.getButton() != MouseButton.SECONDARY) return;

        Button botonMesa = (Button) event.getSource();
        Mesa mesa = mesas.get(botonMesa);
        if (mesa == null) return;

        ContextMenu menuMesa = new ContextMenu();

        if (!mesa.isDisponible()) {
            MenuItem cerrarMesaItem = new MenuItem("Cerrar Mesa y Pagar");
            cerrarMesaItem.setOnAction(e -> abrirVentanaPago(mesa));
            menuMesa.getItems().add(cerrarMesaItem);
        }
        menuMesa.show(botonMesa, event.getScreenX(), event.getScreenY());
    }

    private void abrirVentanaPago(Mesa mesa) {
        ConsumoMesa consumo = miRestaurante.getConsumosActivosPorMesa().get(mesa.getNumeroDeMesa());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vista_pago.fxml"));
            Parent root = loader.load();
            PagoController pagoController = loader.getController();
            pagoController.inicializarDatos(miRestaurante, consumo);

            Stage stage = new Stage();
            stage.setTitle("Pago - Mesa " + mesa.getNumeroDeMesa());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();


            if (pagoController.isPagoExitoso()) {
                miRestaurante.cerrarMesa(mesa.getNumeroDeMesa());
                actualizarVisualMesa(getBotonPorMesa(mesa), mesa);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Button getBotonPorMesa(Mesa mesa) {
        for (Map.Entry<Button, Mesa> entry : mesas.entrySet()) {
            if (entry.getValue().equals(mesa)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(loader.load());

            HelloController helloController = loader.getController();
            helloController.setRestaurante(miRestaurante);

            Stage stage = (Stage) cerrar.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Inicio de sesión");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
