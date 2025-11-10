package com.example.tpfinalfx;

import com.example.tpfinalfx.model.entities.Restaurante;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

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
    @FXML
    private Button cerrar;
    @FXML
    private Button cerrarDia;


    public void initialize() {
    }

    public void setRestaurante(Restaurante restaurante) {
        this.miRestaurante = restaurante;
    }

    @FXML
    public void verEmpleados() {}

    @FXML
    public void agregarEmpleado() {}

    @FXML
    public void eliminarEmpleado() {}

    @FXML
    public void verMenu() {}

    @FXML
    public void agregarItemMenu() {}

    @FXML
    public void eliminarItemMenu() {}

    @FXML
    public void verMesas() {}

    @FXML
    public void agregarMesa() {}

    @FXML
    public void eliminarMesa() {}

    @FXML
    public void finalizarJornada() {}

    @FXML
    public void cerrarSesion() {
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
