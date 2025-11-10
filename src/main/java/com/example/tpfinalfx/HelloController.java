package com.example.tpfinalfx;

import com.example.tpfinalfx.model.GerentesController;
import com.example.tpfinalfx.model.entities.Cajero;
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
import java.util.Objects;

public class HelloController {

    private Restaurante restaurante = new Restaurante();

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
            welcomeLabel.setText("Contraseña no encontrada. Intente de nuevo.");
            welcomeLabel.setStyle("-fx-text-fill: #dc3545;");
        }

        if (empleado != null) {
            String puesto = empleado.getClass().getSimpleName();
            welcomeLabel.setText("¡Bienvenido/a, " + empleado.getNombre() + "! Puesto: " + puesto);
            welcomeLabel.setStyle("-fx-text-fill: #28a745;"); // Color verde para éxito

            loginButton.setDisable(true);

            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> cambiarAMainView(puesto));
            delay.play();

        }
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    private void cambiarAMainView(String puesto) {
        try {
            if (Objects.equals(puesto, "Mozo")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Vista_Mozos.fxml"));
                Parent root = loader.load();


                MozosController mozosController = loader.getController();
                mozosController.setRestaurante(this.restaurante);

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Panel de mozo");
            }
            else if (puesto.equals("Cajero")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Vista_Cajeros.fxml"));
                Parent root = loader.load();

                CajerosController cajerosController = loader.getController();
                cajerosController.setRestaurante(this.restaurante);

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Panel de cajero");
            }
            else if (puesto.equals("Gerente")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Vista_Gerentes.fxml"));
                Parent root = loader.load();

                GerentesController gerentesController = loader.getController();
                gerentesController.setRestaurante(this.restaurante);

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Panel de gerente");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
