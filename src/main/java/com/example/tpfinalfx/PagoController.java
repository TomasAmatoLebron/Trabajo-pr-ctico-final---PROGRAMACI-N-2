package com.example.tpfinalfx;

import com.example.tpfinalfx.model.entities.ConsumoMesa;
import com.example.tpfinalfx.model.entities.ItemMenu;
import com.example.tpfinalfx.model.entities.Restaurante;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.Map;

public class PagoController {

    private Restaurante restaurante;
    private ConsumoMesa consumoActual;
    private boolean pagoExitoso = false;

    @FXML
    private Label totalAmountLabel;
    @FXML
    private Button cashButton, cardButton, qrButton, cancelButton;
    @FXML
    private TableView<Map.Entry<ItemMenu, Integer>> consumoTableView;
    @FXML
    private TableColumn<Map.Entry<ItemMenu, Integer>, String> productoColumn;
    @FXML
    private TableColumn<Map.Entry<ItemMenu, Integer>, Integer> cantidadColumn;
    @FXML
    private TableColumn<Map.Entry<ItemMenu, Integer>, Double> precioColumn;
    @FXML
    private TableColumn<Map.Entry<ItemMenu, Integer>, Double> subtotalColumn;

    public void inicializarDatos(Restaurante restaurante, ConsumoMesa consumo) {
        this.restaurante = restaurante;
        this.consumoActual = consumo;

        if (consumo == null) return;

        totalAmountLabel.setText(String.format("$%.2f", consumo.getPrecioTotal()));
        productoColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getKey().getNombre()));
        cantidadColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getValue()));
        precioColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getKey().getPrecio()));
        subtotalColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getKey().getPrecio() * cell.getValue().getValue()));
        consumoTableView.setItems(FXCollections.observableArrayList(consumo.getConsumo().entrySet()));
    }


    public boolean isPagoExitoso() {
        return pagoExitoso;
    }

    @FXML
    void onCashButtonClick() {
        this.pagoExitoso = true;
        cerrarVentana();
    }

    @FXML
    void onCardButtonClick() {
        this.pagoExitoso = true;
        cerrarVentana();
    }

    @FXML
    void onQrButtonClick() {
        System.out.println("Pago con QR seleccionado.");
        this.pagoExitoso = true;
        cerrarVentana();
    }

    @FXML
    void onCancelButtonClick() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) cashButton.getScene().getWindow();
        stage.close();
    }
}
