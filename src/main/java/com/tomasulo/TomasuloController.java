package com.tomasulo;

import java.io.IOException;
import javafx.fxml.FXML;

public class TomasuloController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}