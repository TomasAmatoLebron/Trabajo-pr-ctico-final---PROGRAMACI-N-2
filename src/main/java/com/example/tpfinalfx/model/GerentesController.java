package com.example.tpfinalfx.model;

import com.example.tpfinalfx.model.entities.Restaurante;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GerentesController {

    private Restaurante miRestaurante;
    @FXML
    private Label labelEmpleados;
    @FXML
    private Label labelEstadisticas;
    @FXML
    private Label labelConfiguracion;
    @FXML
    private Button verEmp;
    @FXML
    private Button aggEmp;
    @FXML
    private Button eliEmp;
    @FXML
    private Button verM;
    @FXML
    private Button aggM;
    @FXML
    private Button eliM;
    @FXML
    private Button verMes;
    @FXML
    private Button aggMes;
    @FXML
    private Button eliMes;


    public void initialize() {
    }

    public void setRestaurante(Restaurante restaurante) {
        this.miRestaurante = restaurante;
    }
}
