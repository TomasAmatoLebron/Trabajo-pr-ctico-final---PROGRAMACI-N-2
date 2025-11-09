package com.example.tpfinalfx;

import com.example.tpfinalfx.model.entities.Empleado;
import com.example.tpfinalfx.model.entities.Restaurante;
import com.example.tpfinalfx.model.exceptions.PasswordInvalidaException;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloController {

    private final Restaurante restaurante = new Restaurante();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    protected void onHelloButtonClick() {
        String pass = passwordField.getText();

        if (pass.isEmpty()) {
            welcomeLabel.setText("Por favor, ingrese la contraseña.");
            welcomeLabel.setStyle("-fx-text-fill: #dc3545;");
            return;
        }

        Empleado empleado = null;
        try {
            empleado = restaurante.verificarPassword(pass);
        } catch (PasswordInvalidaException e) {
            welcomeLabel.setText("Contraseña no encontrado. Intente de nuevo.");
            welcomeLabel.setStyle("-fx-text-fill: #dc3545;");
        }

        if (empleado != null) {
            String puesto = empleado.getClass().getSimpleName();
            welcomeLabel.setText("¡Bienvenido, " + empleado.getNombre() + "! Puesto: " + puesto);
            welcomeLabel.setStyle("-fx-text-fill: #28a745;"); // Color verde para éxito

            loginButton.setDisable(true);

            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> cambiarAMainView());
            delay.play();

        }
    }
    private void cambiarAMainView() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            Parent root = loader.load();


            MainController mainController = loader.getController();
            mainController.setRestaurante(this.restaurante);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestor de Mesas");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
