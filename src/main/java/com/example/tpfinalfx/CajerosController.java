package com.example.tpfinalfx;

import com.example.tpfinalfx.model.entities.ConsumoMesa;
import com.example.tpfinalfx.model.entities.ItemMenu;
import com.example.tpfinalfx.model.entities.Mesa;
import com.example.tpfinalfx.model.entities.Restaurante;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class CajerosController {

    private GridPane mesasGrid;

    private Restaurante miRestaurante;


    @FXML
    private Button mesa1;
    @FXML
    private Button mesa2;
    @FXML
    private Button mesa3;
    @FXML
    private Button mesa4;
    @FXML
    private Button mesa5;
    @FXML
    private Button mesa6;
    @FXML
    private Button mesa7;
    @FXML
    private Button mesa8;
    @FXML
    private Button mesa9;
    @FXML
    private Button mesa10;
    @FXML
    private Button mesa11;
    @FXML
    private Button mesa12;
    @FXML
    private Button cerrar;
    @FXML
    private Map<Button, Mesa> mesas = new HashMap<>();
    @FXML
    private ContextMenu menuMesa;

    @FXML
    public void initialize() {
    }

    public void setRestaurante(Restaurante restaurante) {
        this.miRestaurante = restaurante;
        inicializarMesas();
    }


    private void actualizarVisualMesa(Button boton, Mesa mesa) {
        if (mesa.isDisponible()) {
            boton.setStyle("-fx-background-color: #d4edda; -fx-border-color: #c3e6cb; -fx-border-radius: 5; -fx-background-radius: 5;");
        } else {
            boton.setStyle("-fx-background-color: #f8d7da; -fx-border-color: #f5c6cb; -fx-border-radius: 5; -fx-background-radius: 5;");
        }

        if (boton.getGraphic() instanceof VBox vbox) {
            Label estadoLabel = (Label) vbox.getChildren().get(1);
            if (mesa.isDisponible()) {
                estadoLabel.setText("Disponible");
                estadoLabel.setTextFill(Color.web("#155724"));
            } else {
                estadoLabel.setText("Ocupada");
                estadoLabel.setTextFill(Color.web("#721c24"));
            }
        }
        else {
            Label lblMesa = new Label("Mesa " + mesa.getNumeroDeMesa());
            lblMesa.setFont(new Font("System Bold", 16));
            Label lblEstado = new Label(mesa.isDisponible() ? "Disponible" : "Ocupada");
            lblEstado.setTextFill(mesa.isDisponible() ? Color.web("#155724") : Color.web("#721c24"));
            VBox content = new VBox(lblMesa, lblEstado);
            content.setSpacing(5);
            boton.setGraphic(content);
        }
    }

    @FXML
    private void mostrarMenuMesa(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY && event.getButton() != MouseButton.SECONDARY)
            return;

        Button botonMesa = (Button) event.getSource();
        Mesa mesa = mesas.get(botonMesa);
        if (mesa == null) return;

        ContextMenu menuMesa = new ContextMenu();

        if (!mesa.isDisponible()) {
            MenuItem verPedido = new MenuItem("Ver consumo");
            MenuItem cerrarMesa = new MenuItem("Cerrar Mesa");

            verPedido.setOnAction(e -> mostrarConsumoMesa(mesa));

            cerrarMesa.setOnAction(e -> {
                miRestaurante.cerrarMesa(mesa.getNumeroDeMesa());
                actualizarVisualMesa(botonMesa, mesa);
                menuMesa.getItems().addAll(verPedido, cerrarMesa);
            });
            menuMesa.getItems().addAll(verPedido, cerrarMesa);
        }
        menuMesa.show(botonMesa, event.getScreenX(), event.getScreenY());
    }

    public void mostrarConsumoMesa(Mesa mesa) {
        ConsumoMesa consumo = miRestaurante.getConsumosActivosPorMesa().get(mesa.getNumeroDeMesa());

        // Crear ventana nueva
        Stage stage = new Stage();
        stage.setTitle("Consumo: Mesa " + mesa.getNumeroDeMesa());
        stage.initModality(Modality.APPLICATION_MODAL); // bloquea la ventana principal

        // Encabezado
        Label lblTitulo = new Label("Mesa " + mesa.getNumeroDeMesa());
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Tabla
        TableView<Map.Entry<ItemMenu, Integer>> tabla = new TableView<>();
        TableColumn<Map.Entry<ItemMenu, Integer>, String> colProducto = new TableColumn<>("Producto");
        TableColumn<Map.Entry<ItemMenu, Integer>, Integer> colCantidad = new TableColumn<>("Cantidad");
        TableColumn<Map.Entry<ItemMenu, Integer>, Double> colPrecio = new TableColumn<>("Precio");
        TableColumn<Map.Entry<ItemMenu, Integer>, Double> colSubtotal = new TableColumn<>("Subtotal");

        colProducto.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getKey().getNombre()));
        colCantidad.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getValue()));
        colPrecio.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getKey().getPrecio()));
        colSubtotal.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(
                cell.getValue().getKey().getPrecio() * cell.getValue().getValue()
        ));

        tabla.getColumns().addAll(colProducto, colCantidad, colPrecio, colSubtotal);
        tabla.getItems().addAll(consumo.getConsumo().entrySet());

        // Total
        Label lblTotal = new Label("TOTAL: $" + String.format("%.2f", consumo.getPrecioTotal()));
        lblTotal.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Layout
        VBox layout = new VBox(10, lblTitulo, tabla, lblTotal);
        layout.setStyle("-fx-padding: 15; -fx-background-color: #f8f9fa;");
        Scene scene = new Scene(layout, 500, 400);

        stage.setScene(scene);
        stage.showAndWait();
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

        mesas.forEach((boton, mesa) -> actualizarVisualMesa(boton, mesa));
    }

    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(loader.load());

            // Obtener el controlador del login
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
