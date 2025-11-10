package com.example.tpfinalfx;

import com.example.tpfinalfx.model.entities.*;
import com.example.tpfinalfx.model.enums.ETipoProducto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

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
    public void verEmpleados() {

        ObservableList<Empleado> empleados = FXCollections.observableArrayList(miRestaurante.getGestoraEmpleados().obtenerValores());

        TableView<Empleado> tabla = new TableView<>(empleados);

        TableColumn<Empleado, String> colDni = new TableColumn<>("DNI");
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));

        TableColumn<Empleado, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Empleado, String> colApellido = new TableColumn<>("Apellido");
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));

        TableColumn<Empleado, String> colFecha = new TableColumn<>("Fecha de Nacimiento");
        colFecha.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(() -> {
            if (cellData.getValue().getFechaDeNacimiento() != null) {
                return cellData.getValue().getFechaDeNacimiento().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } else {
                return "-";
            }
        }));

        TableColumn<Empleado, String> colPuesto = new TableColumn<>("Puesto");
        colPuesto.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        cellData.getValue().getClass().getSimpleName()));

        tabla.getColumns().addAll(colDni, colNombre, colApellido, colFecha, colPuesto);

        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla.setPrefHeight(400);

        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: #ffffff; -fx-padding: 15;");
        layout.getChildren().add(tabla);

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Listado de Empleados");
        popup.setScene(new Scene(layout, 600, 400));
        popup.showAndWait();
    }

    @FXML
    public void agregarEmpleado() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Agregar nuevo empleado");
        popupStage.initModality(Modality.APPLICATION_MODAL);

        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre");

        TextField apellidoField = new TextField();
        apellidoField.setPromptText("Apellido");

        TextField dniField = new TextField();
        dniField.setPromptText("DNI");

        DatePicker fechaPicker = new DatePicker();
        fechaPicker.setPromptText("Fecha de nacimiento");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");

        ComboBox<String> puestoBox = new ComboBox<>();
        puestoBox.getItems().addAll("Mozo", "Cajero", "Gerente");
        puestoBox.setPromptText("Seleccionar puesto");

        Button btnAgregar = new Button("Agregar");
        Button btnCancelar = new Button("Cancelar");

        btnAgregar.setOnAction(e -> {
            String nombre = nombreField.getText();
            String apellido = apellidoField.getText();
            String dni = dniField.getText();
            LocalDate fecha = fechaPicker.getValue();
            String password = passwordField.getText();
            String puesto = puestoBox.getValue();

            Empleado nuevoEmpleado = null;
            switch (puesto) {
                case "Mozo":
                    nuevoEmpleado = new Mozo(nombre, apellido, fecha, dni, password);
                    break;
                case "Cajero":
                    nuevoEmpleado = new Cajero(nombre, apellido, fecha, dni, password);
                    break;
                case "Gerente":
                    nuevoEmpleado = new Gerente(nombre, apellido, fecha, dni, password);
                    break;
            }

            miRestaurante.agregarEmpleado(nuevoEmpleado);
            popupStage.close();
            miRestaurante.guardarEmpleados();

        });

        btnCancelar.setOnAction(e -> popupStage.close());

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        grid.addRow(0, new Label("Nombre:"), nombreField);
        grid.addRow(1, new Label("Apellido:"), apellidoField);
        grid.addRow(2, new Label("DNI:"), dniField);
        grid.addRow(3, new Label("Fecha de Nacimiento:"), fechaPicker);
        grid.addRow(4, new Label("Contraseña:"), passwordField);
        grid.addRow(5, new Label("Puesto:"), puestoBox);
        grid.addRow(6, btnAgregar, btnCancelar);

        GridPane.setMargin(btnCancelar, new Insets(0, 0, 0, 10));

        Scene scene = new Scene(grid, 400, 350);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    @FXML
    public void eliminarEmpleado() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Eliminar Empleado");
        popupStage.initModality(Modality.APPLICATION_MODAL);

        TableView<Empleado> tabla = new TableView<>();

        TableColumn<Empleado, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Empleado, String> colApellido = new TableColumn<>("Apellido");
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));

        TableColumn<Empleado, String> colDni = new TableColumn<>("DNI");
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));

        TableColumn<Empleado, String> colPuesto = new TableColumn<>("Puesto");
        colPuesto.setCellValueFactory(cellData -> {
            String clase = cellData.getValue().getClass().getSimpleName();
            return new SimpleStringProperty(clase);
        });

        tabla.getColumns().addAll(colNombre, colApellido, colDni, colPuesto);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        if (miRestaurante != null) {
            tabla.setItems(FXCollections.observableArrayList(miRestaurante.getGestoraEmpleados().obtenerValores()));
        }

        // Botones
        Button btnEliminar = new Button("Eliminar seleccionado");
        btnEliminar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        Button btnCancelar = new Button("Cancelar");

        btnEliminar.setOnAction(e -> {
            Empleado seleccionado = tabla.getSelectionModel().getSelectedItem();

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Está seguro de que desea eliminar a " + seleccionado.getNombre() + " " + seleccionado.getApellido() + "?");

            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    miRestaurante.eliminarEmpleado(seleccionado);
                    tabla.getItems().remove(seleccionado);
                    miRestaurante.guardarEmpleados();
                }
            });
        });

        btnCancelar.setOnAction(e -> popupStage.close());

        HBox botones = new HBox(10, btnEliminar, btnCancelar);
        botones.setAlignment(Pos.CENTER_RIGHT);
        botones.setPadding(new Insets(10));

        VBox layout = new VBox(10, tabla, botones);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 600, 400);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    @FXML
    public void verMenu() {
        Stage ventana = new Stage();
        ventana.setTitle("Listado del Menú");

        TableView<ItemMenu> tabla = new TableView<>();

        TableColumn<ItemMenu, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<ItemMenu, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<ItemMenu, ETipoProducto> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        TableColumn<ItemMenu, String> colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        tabla.getColumns().addAll(colNombre, colPrecio, colCategoria, colDescripcion);

        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tabla.setItems(FXCollections.observableArrayList(miRestaurante.getMenu().obtenerValores()));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(new Text("Listado de Ítems del Menú:"), tabla);

        Scene escena = new Scene(layout, 700, 400);
        ventana.setScene(escena);
        ventana.show();
    }

    @FXML
    public void agregarItemMenu() {
        Stage ventana = new Stage();
        ventana.setTitle("Agregar nuevo ítem al menú");

        TextField nombreField = new TextField();
        TextField precioField = new TextField();
        ComboBox<ETipoProducto> categoriaBox = new ComboBox<>();
        categoriaBox.getItems().addAll(ETipoProducto.values());
        TextArea descripcionArea = new TextArea();

        Label lblNombre = new Label("Nombre:");
        Label lblPrecio = new Label("Precio:");
        Label lblCategoria = new Label("Categoría:");
        Label lblDescripcion = new Label("Descripción:");

        Button btnAceptar = new Button("Agregar");
        Button btnCancelar = new Button("Cancelar");

        HBox botones = new HBox(10, btnAceptar, btnCancelar);
        botones.setAlignment(Pos.CENTER_RIGHT);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        grid.add(lblNombre, 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(lblPrecio, 0, 1);
        grid.add(precioField, 1, 1);
        grid.add(lblCategoria, 0, 2);
        grid.add(categoriaBox, 1, 2);
        grid.add(lblDescripcion, 0, 3);
        grid.add(descripcionArea, 1, 3);
        grid.add(botones, 1, 4);

        Scene scene = new Scene(grid, 400, 350);
        ventana.setScene(scene);
        ventana.show();

        btnAceptar.setOnAction(e -> {
            try {
                String nombre = nombreField.getText().trim();
                double precio = Double.parseDouble(precioField.getText().trim());
                ETipoProducto categoria = categoriaBox.getValue();
                String descripcion = descripcionArea.getText().trim();


                ItemMenu nuevoItem = new ItemMenu(nombre, precio, categoria, descripcion);

                miRestaurante.agregarMenu(nuevoItem);
                miRestaurante.guardarMenu();

            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        });

        btnCancelar.setOnAction(e -> ventana.close());
    }

    @FXML
    public void eliminarItemMenu() {
        Stage ventana = new Stage();
        ventana.setTitle("Eliminar ítem del menú");

        TableView<ItemMenu> tabla = new TableView<>();
        tabla.setPrefWidth(500);
        tabla.setPrefHeight(300);

        TableColumn<ItemMenu, String> nombreCol = new TableColumn<>("Nombre");
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        nombreCol.setPrefWidth(150);

        TableColumn<ItemMenu, Double> precioCol = new TableColumn<>("Precio");
        precioCol.setCellValueFactory(new PropertyValueFactory<>("precio"));
        precioCol.setPrefWidth(100);

        TableColumn<ItemMenu, ETipoProducto> categoriaCol = new TableColumn<>("Categoría");
        categoriaCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        categoriaCol.setPrefWidth(120);

        TableColumn<ItemMenu, String> descCol = new TableColumn<>("Descripción");
        descCol.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        descCol.setPrefWidth(200);

        tabla.getColumns().addAll(nombreCol, precioCol, categoriaCol, descCol);

        ObservableList<ItemMenu> items = FXCollections.observableArrayList(miRestaurante.getMenu().obtenerValores());
        tabla.setItems(items);

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        Button btnCancelar = new Button("Cancelar");

        HBox botones = new HBox(10, btnEliminar, btnCancelar);
        botones.setAlignment(Pos.CENTER_RIGHT);

        VBox layout = new VBox(15, tabla, botones);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 600, 400);
        ventana.setScene(scene);
        ventana.show();

        btnEliminar.setOnAction(e -> {
            ItemMenu seleccionado = tabla.getSelectionModel().getSelectedItem();


            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Está seguro de que desea eliminar \"" + seleccionado.getNombre() + "\"?");
            Optional<ButtonType> resultado = confirmacion.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                miRestaurante.eliminarMenu(seleccionado);
                items.remove(seleccionado);
                miRestaurante.guardarMenu();
            }
        });

        btnCancelar.setOnAction(e -> ventana.close());
    }

    @FXML
    public void verMesas() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Listado de Mesas");
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // === Crear la tabla ===
        TableView<Mesa> tabla = new TableView<>();

        // Columna: Número de mesa
        TableColumn<Mesa, Integer> colNumero = new TableColumn<>("Número de Mesa");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numeroDeMesa"));
        colNumero.setPrefWidth(150);

        // Columna: Disponible
        TableColumn<Mesa, String> colDisponible = new TableColumn<>("Estado");
        colDisponible.setCellValueFactory(cellData -> {
            boolean disponible = cellData.getValue().isDisponible();
            String texto;
            if (disponible) {
                texto = "Disponible";
            } else {
                texto = "Ocupada";
            }
            return new SimpleStringProperty(texto);
        });
        colDisponible.setPrefWidth(150);

        tabla.getColumns().addAll(colNumero, colDisponible);

        ObservableList<Mesa> listaMesas = FXCollections.observableArrayList(
                miRestaurante.getGestoraMesas().obtenerValores()
        );
        tabla.setItems(listaMesas);

        // === Botón de cierre ===
        Button cerrarBtn = new Button("Cerrar");
        cerrarBtn.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        cerrarBtn.setOnAction(e -> popupStage.close());

        VBox layout = new VBox(15, tabla, cerrarBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 350, 400);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    @FXML
    public void agregarMesa() {

        Stage popupStage = new Stage();
        popupStage.setTitle("Agregar Mesa");
        popupStage.initModality(Modality.APPLICATION_MODAL);

        Label labelNumero = new Label("Número de Mesa:");
        TextField campoNumero = new TextField();
        campoNumero.setPromptText("Número de mesa");

        HBox numeroBox = new HBox(10, labelNumero, campoNumero);
        numeroBox.setAlignment(Pos.CENTER_LEFT);

        Button btnAceptar = new Button("Aceptar");
        btnAceptar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");

        HBox botonesBox = new HBox(15, btnAceptar, btnCancelar);
        botonesBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, numeroBox, botonesBox);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 350, 150);
        popupStage.setScene(scene);
        popupStage.show();

        btnAceptar.setOnAction(e -> {
            String numeroTexto = campoNumero.getText().trim();

            Mesa nuevaMesa = new Mesa(Integer.parseInt(numeroTexto));
            miRestaurante.agregarMesa(nuevaMesa);
            popupStage.close();
            miRestaurante.guardarMesas();
        });

        btnCancelar.setOnAction(e -> popupStage.close());
    }

    @FXML
    public void eliminarMesa() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Eliminar Mesa");
        popupStage.initModality(Modality.APPLICATION_MODAL);

        TableView<Mesa> tablaMesas = new TableView<>();

        TableColumn<Mesa, Integer> colNumero = new TableColumn<>("Número de Mesa");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numeroDeMesa"));
        colNumero.setPrefWidth(150);

        TableColumn<Mesa, String> colDisponible = new TableColumn<>("Estado");
        colDisponible.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isDisponible() ? "Disponible" : "Ocupada"));
        colDisponible.setPrefWidth(150);

        tablaMesas.getColumns().addAll(colNumero, colDisponible);

        tablaMesas.getItems().addAll(miRestaurante.getGestoraMesas().obtenerValores());

        Button btnEliminar = new Button("Eliminar Mesa");
        btnEliminar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white;");

        HBox botonesBox = new HBox(15, btnEliminar, btnCancelar);
        botonesBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, tablaMesas, botonesBox);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 400);
        popupStage.setScene(scene);
        popupStage.show();

        btnEliminar.setOnAction(e -> {
            Mesa seleccionada = tablaMesas.getSelectionModel().getSelectedItem();

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Está seguro que desea eliminar la mesa " + seleccionada.getNumeroDeMesa() + "?");

            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    miRestaurante.eliminarMesa(seleccionada);
                    tablaMesas.getItems().remove(seleccionada);
                    miRestaurante.guardarMesas();
                }
            });
        });

        btnCancelar.setOnAction(e -> popupStage.close());
    }

    @FXML
    public void finalizarJornada() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar cierre");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Confirmar cierre de jornada?");

        ButtonType botonAceptar = new ButtonType("Aceptar");
        ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alerta.getButtonTypes().setAll(botonAceptar, botonCancelar);

        alerta.showAndWait().ifPresent(respuesta -> {
            if (respuesta == botonAceptar) {

                miRestaurante.terminarJornada();

                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Jornada finalizada");
                info.setHeaderText(null);
                info.setContentText("La jornada se cerró correctamente.");
                info.showAndWait();
            }
        });
    }

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
