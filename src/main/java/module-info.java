module com.example.tpfinalfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.example.tpfinalfx to javafx.fxml;
    exports com.example.tpfinalfx;
}