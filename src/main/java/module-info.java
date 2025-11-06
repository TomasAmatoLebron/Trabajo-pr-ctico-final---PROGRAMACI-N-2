module com.example.tpfinalfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tpfinalfx to javafx.fxml;
    exports com.example.tpfinalfx;
}