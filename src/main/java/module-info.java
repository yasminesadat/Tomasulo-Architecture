module com.tomasolu {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.tomasolu to javafx.fxml;
    exports com.tomasolu;
}
