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
import javafx.scene.Parent;
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
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.*;

public class MozosController {

    @FXML
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

        if (mesa.isDisponible()) {
            MenuItem abrir = new MenuItem("Abrir mesa");
            abrir.setOnAction(e -> {
                ConsumoMesa consumo = miRestaurante.abrirMesa(mesa.getNumeroDeMesa());
                actualizarVisualMesa(botonMesa, mesa);
            });
            menuMesa.getItems().add(abrir);
        } else {
            MenuItem verPedido = new MenuItem("Ver consumo");
            MenuItem agregarItem = new MenuItem("Agregar item a pedido");
            MenuItem eliminarItem = new MenuItem("Eliminar item de pedido");

            verPedido.setOnAction(e -> mostrarConsumoMesa(mesa));
            agregarItem.setOnAction(e -> agregarAlPedido(mesa));
            eliminarItem.setOnAction(e -> eliminarItemDePedido(mesa));


            menuMesa.getItems().addAll(verPedido, agregarItem, eliminarItem);
        }

        menuMesa.show(botonMesa, event.getScreenX(), event.getScreenY());
    }

    private void eliminarItemDePedido(Mesa mesa) {

        ConsumoMesa consumo = miRestaurante.getConsumosActivosPorMesa().get(mesa.getNumeroDeMesa());

        Stage stage = new Stage();
        stage.setTitle("Eliminar ítem - Mesa " + mesa.getNumeroDeMesa());
        stage.initModality(Modality.APPLICATION_MODAL);

        TableView<Map.Entry<ItemMenu, Integer>> tabla = new TableView<>();

        TableColumn<Map.Entry<ItemMenu, Integer>, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Map.Entry<ItemMenu, Integer>, Integer> colCantidad = new TableColumn<>("Cantidad");
        TableColumn<Map.Entry<ItemMenu, Integer>, Double> colPrecio = new TableColumn<>("Precio");

        colNombre.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getKey().getNombre()));
        colCantidad.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getValue()));
        colPrecio.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getKey().getPrecio()));

        tabla.getColumns().addAll(colNombre, colCantidad, colPrecio);
        tabla.getItems().addAll(consumo.getConsumo().entrySet());

        tabla.setPrefHeight(300);

        Label lblCantidad = new Label("Cantidad a eliminar:");
        Spinner<Integer> spnCantidad = new Spinner<>(1, 10, 1);
        spnCantidad.setEditable(true);

        HBox cantidadBox = new HBox(10, lblCantidad, spnCantidad);
        cantidadBox.setAlignment(Pos.CENTER_LEFT);

        Button btnEliminar = new Button("Eliminar");
        Button btnCancelar = new Button("Cancelar");
        HBox botones = new HBox(10, btnEliminar, btnCancelar);
        botones.setAlignment(Pos.CENTER_RIGHT);

        btnEliminar.setOnAction(e -> {
            Map.Entry<ItemMenu, Integer> seleccionado = tabla.getSelectionModel().getSelectedItem();

            int cantidadAEliminar = spnCantidad.getValue();
            ItemMenu item = seleccionado.getKey();
            int cantidadActual = seleccionado.getValue();

            consumo.eliminarItem(item, cantidadAEliminar);
            if (consumo.getConsumo().get(item) <= 0) {
                consumo.getConsumo().remove(item);
            }

            consumo.precioFinal();

            stage.close();
        });

        btnCancelar.setOnAction(e -> stage.close());

        VBox layout = new VBox(15,
                new Label("Seleccione un ítem del pedido:"),
                tabla,
                cantidadBox,
                botones
        );
        layout.setPadding(new Insets(15));
        layout.setStyle("-fx-background-color: #f8f9fa;");

        stage.setScene(new Scene(layout, 500, 450));
        stage.showAndWait();
    }

    private void agregarAlPedido(Mesa mesa) {

        Stage stage = new Stage();
        stage.setTitle("Agregar ítem a la mesa " + mesa.getNumeroDeMesa());
        stage.initModality(Modality.APPLICATION_MODAL);

        List<ItemMenu> items = new ArrayList<>(miRestaurante.getMenu().obtenerValores());
        items.sort(Comparator.comparing(ItemMenu::getNombre));

        TableView<ItemMenu> tablaItems = new TableView<>();
        TableColumn<ItemMenu, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<ItemMenu, String> colCategoria = new TableColumn<>("Categoría");
        TableColumn<ItemMenu, Double> colPrecio = new TableColumn<>("Precio");

        colNombre.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getNombre()));
        colCategoria.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getCategoria().toString()));
        colPrecio.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getPrecio()));

        tablaItems.getColumns().addAll(colNombre, colCategoria, colPrecio);
        tablaItems.getItems().addAll(items);

        tablaItems.setPrefHeight(300);

        Label lblCantidad = new Label("Cantidad:");
        Spinner<Integer> spnCantidad = new Spinner<>(1, 10, 1);
        spnCantidad.setEditable(true);
        HBox cantidadBox = new HBox(10, lblCantidad, spnCantidad);
        cantidadBox.setAlignment(Pos.CENTER_LEFT);

        Button btnAgregar = new Button("Agregar");
        Button btnCancelar = new Button("Cancelar");
        HBox botonesBox = new HBox(10, btnAgregar, btnCancelar);
        botonesBox.setAlignment(Pos.CENTER_RIGHT);

        btnAgregar.setOnAction(e -> {
            ItemMenu seleccionado = tablaItems.getSelectionModel().getSelectedItem();
            int cantidad = spnCantidad.getValue();

            if (seleccionado == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "¡Seleccione un ítem del menú!.");
                alerta.showAndWait();
                return;
            }

            ConsumoMesa consumo = miRestaurante.getConsumosActivosPorMesa().get(mesa.getNumeroDeMesa());

            consumo.agregarItem(seleccionado, cantidad);

            stage.close();
        });

        btnCancelar.setOnAction(e -> stage.close());

        VBox layout = new VBox(15,
                new Label("Seleccione un ítem del menú:"),
                tablaItems,
                cantidadBox,
                botonesBox
        );
        layout.setPadding(new Insets(15));
        layout.setStyle("-fx-background-color: #f8f9fa;");

        stage.setScene(new Scene(layout, 500, 450));
        stage.showAndWait();
    }

    public void mostrarConsumoMesa(Mesa mesa) {
        ConsumoMesa consumo = miRestaurante.getConsumosActivosPorMesa().get(mesa.getNumeroDeMesa());

        Stage stage = new Stage();
        stage.setTitle("Consumo: Mesa " + mesa.getNumeroDeMesa());
        stage.initModality(Modality.APPLICATION_MODAL); // bloquea la ventana principal

        Label lblTitulo = new Label("Mesa " + mesa.getNumeroDeMesa());
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

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

        Label lblTotal = new Label("TOTAL: $" + String.format("%.2f", consumo.getPrecioTotal()));
        lblTotal.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

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


