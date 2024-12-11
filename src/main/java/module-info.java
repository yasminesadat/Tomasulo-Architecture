module com.tomasulo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.tomasulo to javafx.fxml;
    exports com.tomasulo;
}
