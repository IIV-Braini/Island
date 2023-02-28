module com.example.island {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;

    opens com.example.island to javafx.fxml;
    exports com.example.island;
    exports com.example.island.Creator;
    opens com.example.island.Creator to javafx.fxml;
    exports com.example.island.GameField;
    opens com.example.island.GameField to javafx.fxml;
    exports com.example.island.UI;
    opens com.example.island.UI to javafx.fxml;
}