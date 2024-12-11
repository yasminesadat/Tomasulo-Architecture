module com.tomasulo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.tomasulo to javafx.fxml;
    opens com.tomasulo.classes to javafx.base;

    exports com.tomasulo;
    exports com.tomasulo.classes;
}
