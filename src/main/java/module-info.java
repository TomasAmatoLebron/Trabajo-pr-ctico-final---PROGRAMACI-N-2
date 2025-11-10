module com.example.tpfinalfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    requires org.kordamp.bootstrapfx.core;
    requires com.example.tpfinalfx;

    opens com.example.tpfinalfx to javafx.fxml, javafx.graphics;
    opens com.example.tpfinalfx.model.entities to javafx.fxml;

    exports com.example.tpfinalfx;
    exports com.example.tpfinalfx.model.entities;

}