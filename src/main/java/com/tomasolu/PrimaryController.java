package com.tomasolu;

import java.io.IOException;

import com.tomasolu.classes.Simulator;
import com.tomasolu.classes.UserInputValues;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    private TextField addIntegerLatencyField;
    @FXML
    private TextField subIntegerLatencyField;
    @FXML
    private TextField branchLatencyField;
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
    public void initialize() {
        saveButton.setOnAction(event -> {
            try {
                saveConfiguration();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void saveConfiguration() throws IOException {
        // Gather user inputs and set them in UserInputValues
        UserInputValues.storeLatency = Integer.parseInt(storeLatencyField.getText());
        UserInputValues.loadLatency = Integer.parseInt(loadLatencyField.getText());
        UserInputValues.addLatency = Integer.parseInt(addLatencyField.getText());
        UserInputValues.mulLatency = Integer.parseInt(mulLatencyField.getText());
        UserInputValues.subLatency = Integer.parseInt(subLatencyField.getText());
        UserInputValues.divLatency = Integer.parseInt(divLatencyField.getText());
        UserInputValues.addIntegerLatency = Integer.parseInt(addIntegerLatencyField.getText());
        UserInputValues.subIntegerLatency = Integer.parseInt(subIntegerLatencyField.getText());
        UserInputValues.branchLatency = Integer.parseInt(branchLatencyField.getText());
        UserInputValues.cacheBlockSize = Integer.parseInt(cacheBlockSizeField.getText());
        UserInputValues.cacheSize = Integer.parseInt(cacheSizeField.getText());
        UserInputValues.reservationStationAddSubSize = Integer.parseInt(reservationStationAddSubSizeField.getText());
        UserInputValues.reservationStationMulDivSize = Integer.parseInt(reservationStationMulDivSizeField.getText());
        //UserInputValues.reservationStationAddSubIntegerSize = Integer.parseInt(reservationStationAddSubIntegerSizeField.getText());
        UserInputValues.loadBufferSize = Integer.parseInt(loadBufferSizeField.getText());
        UserInputValues.storeBufferSize = Integer.parseInt(storeBufferSizeField.getText());


        Simulator.init();
        loadReservationStationScene();

        System.out.println("Configuration saved and simulator initialized.");
    }

    private void loadReservationStationScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tomasolu/reservationStations.fxml"));
        Scene reservationStationScene = new Scene(loader.load());
    
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.setScene(reservationStationScene);
        stage.setTitle("Reservation Stations");
        stage.show();
    }
}