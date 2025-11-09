module com.example.tpfinalfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    opens com.example.tpfinalfx to javafx.fxml;
    exports com.example.tpfinalfx;
}