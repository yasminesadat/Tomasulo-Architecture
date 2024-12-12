package com.tomasulo;

import java.io.IOException;

import com.tomasulo.classes.Simulator;
import com.tomasulo.classes.UserInputValues;
import javafx.scene.text.Font;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    private Button saveButton;
    @FXML
    private TextField storeLatencyField;
    @FXML
    private TextField loadLatencyField;
    @FXML
    private TextField addLatencyField;
    @FXML
    private TextField mulLatencyField;
    @FXML
    private TextField subLatencyField;
    @FXML
    private TextField divLatencyField;
    @FXML
    private TextField cacheBlockSizeField;
    @FXML
    private TextField cacheSizeField;
    @FXML
    private TextField reservationStationAddSubSizeField;
    @FXML
    private TextField reservationStationMulDivSizeField;
    @FXML
    private TextField loadBufferSizeField;
    @FXML
    private TextField storeBufferSizeField;
    @FXML
    private TextField reservationStationIntegerSizeField;

    @FXML
    private void saveConfiguration() throws IOException {
        // Gather user inputs and set them in UserInputValues
        UserInputValues.storeLatency = Integer.parseInt(storeLatencyField.getText());
        UserInputValues.loadLatency = Integer.parseInt(loadLatencyField.getText());
        UserInputValues.addLatency = Integer.parseInt(addLatencyField.getText());
        UserInputValues.mulLatency = Integer.parseInt(mulLatencyField.getText());
        UserInputValues.subLatency = Integer.parseInt(subLatencyField.getText());
        UserInputValues.divLatency = Integer.parseInt(divLatencyField.getText());
        UserInputValues.cacheBlockSize = Integer.parseInt(cacheBlockSizeField.getText());
        UserInputValues.cacheSize = Integer.parseInt(cacheSizeField.getText());
        UserInputValues.reservationStationAddSubIntegerSize = Integer
                .parseInt(reservationStationIntegerSizeField.getText());
        UserInputValues.reservationStationAddSubSize = Integer.parseInt(reservationStationAddSubSizeField.getText());
        UserInputValues.reservationStationMulDivSize = Integer.parseInt(reservationStationMulDivSizeField.getText());
        // UserInputValues.reservationStationAddSubIntegerSize =
        // Integer.parseInt(reservationStationAddSubIntegerSizeField.getText());
        UserInputValues.loadBufferSize = Integer.parseInt(loadBufferSizeField.getText());
        UserInputValues.storeBufferSize = Integer.parseInt(storeBufferSizeField.getText());

        Simulator.init();
        loadReservationStationScene();

        System.out.println("Configuration saved and simulator initialized.");
    }

    private void loadReservationStationScene() throws IOException {
        Stage stage = (Stage) saveButton.getScene().getWindow();

        TomasuloController tomasuloController = new TomasuloController();
        tomasuloController.initialize(stage);
        tomasuloController.updateAndRefreshTables();
    }
}