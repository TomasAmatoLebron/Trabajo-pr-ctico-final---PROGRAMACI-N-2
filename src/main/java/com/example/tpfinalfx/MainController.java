package com.example.tpfinalfx;
import com.example.tpfinalfx.model.entities.Restaurante;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class MainController {

        @FXML
        private GridPane mesasGrid;

        private Restaurante miRestaurante;

        public void setRestaurante(Restaurante restaurante) {
            this.miRestaurante = restaurante;
            System.out.println();
        }

        @FXML
        public void initialize() {

        }

    }

