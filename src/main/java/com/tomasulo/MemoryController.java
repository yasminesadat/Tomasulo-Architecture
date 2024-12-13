package com.tomasulo;

import com.tomasulo.classes.Memory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MemoryController {
    // move this to the simulator
    private Memory memory;

    private TextField addressField;
    private TextField valueField;
    private ComboBox<String> dataTypeCombo;
    private Stage primaryStage;

    public void initialize(Stage stage) {
        this.primaryStage = stage;
        memory = new Memory(8);

        // Create the main container
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setStyle("-fx-background-color: #f4f4f4;");

        // Create title
        Label titleLabel = new Label("Memory Editor Page");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Create input form
        GridPane inputForm = createInputForm();

        // Create proceed to simulator button
        Button proceedButton = createProceedToSimulatorButton();

        // Add components to main container
        mainContainer.getChildren().addAll(
            titleLabel, 
            inputForm, 
            proceedButton
        );
        mainContainer.setAlignment(Pos.CENTER);

        // Set up the scene
        Scene scene = new Scene(mainContainer);
        
        // Add external CSS styling
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setMaximized(true);
        stage.show();
    }

    private Button createProceedToSimulatorButton() {
        Button proceedButton = new Button("Proceed to Simulator");
        proceedButton.getStyleClass().add("proceed-button");
        proceedButton.setOnAction(e -> openSimulator());
        return proceedButton;
    }

    private void openSimulator() {
        try {
            // Create new stage for simulator
            Stage simulatorStage = new Stage();
            
            // Create an instance of TomasuloSimulator 
            TomasuloController simulator = new TomasuloController();
            
            // Pass the configured memory to the simulator
            //simulator.setMemory(this.memory);
            
            // Initialize the simulator
            simulator.initialize(simulatorStage);
            
            // Close the current memory controller stage
            this.primaryStage.close();
        } catch (Exception ex) {
            showError("Failed to open simulator: " + ex.getMessage());
        }
    }

    private GridPane createInputForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: white; -fx-padding: 20px; -fx-background-radius: 10;");

        // Address Label and Field
        Label addressLabel = new Label("Memory Address:");
        addressField = new TextField();
        addressField.setPromptText("Enter memory address");
        addressField.getStyleClass().add("input-field");
        grid.add(addressLabel, 0, 0);
        grid.add(addressField, 1, 0);

        // Value Label and Field
        Label valueLabel = new Label("Value:");
        valueField = new TextField();
        valueField.setPromptText("Enter value to write");
        valueField.getStyleClass().add("input-field");
        grid.add(valueLabel, 0, 1);
        grid.add(valueField, 1, 1);

        // Data Type Combo Box
        Label dataTypeLabel = new Label("Data Type:");
        dataTypeCombo = new ComboBox<>();
        dataTypeCombo.getItems().addAll(
            "Word (4 bytes)", 
            "Float (4 bytes)", 
            "Double (8 bytes)"
        );
        dataTypeCombo.getSelectionModel().selectFirst();
        dataTypeCombo.getStyleClass().add("combo-box");
        grid.add(dataTypeLabel, 0, 2);
        grid.add(dataTypeCombo, 1, 2);

        // Write Button
        Button writeButton = new Button("Write to Memory");
        writeButton.getStyleClass().add("action-button");
        writeButton.setOnAction(e -> writeToMemory());
        grid.add(writeButton, 1, 3);

        return grid;
    }

    private void writeToMemory() {
        try {
            // Get address and value input
            int address = Integer.parseInt(addressField.getText());
            String value = valueField.getText();
            String dataType = dataTypeCombo.getValue();

            // Depending on the selected data type, write the corresponding value to memory
            switch (dataType) {
                case "Word (4 bytes)":
                    if (value.matches("-?\\d+")) {
                        memory.writeInt(address, Integer.parseInt(value));
                        memory.displayMemory();
                        clearInputs();
                    } else {
                        showError("Invalid value for Int");
                    }
                    break;
                case "Float (4 bytes)":
                    try {
                        float floatValue = Float.parseFloat(value);
                        memory.writeFloat(address, floatValue);
                        memory.displayMemory();
                        clearInputs();
                    } catch (NumberFormatException ex) {
                        showError("Invalid value for Float");
                    }
                    break;
                case "Double (8 bytes)":
                    try {
                        double doubleValue = Double.parseDouble(value);
                        memory.writeDouble(address, doubleValue);
                        memory.displayMemory();
                        clearInputs();
                    } catch (NumberFormatException ex) {
                        showError("Invalid value for Double");
                    }
                    break;
                default:
                    showError("Unknown data type");
            }
        } catch (NumberFormatException ex) {
            showError("Invalid address format");
        }
    }

    private void clearInputs() {
        addressField.clear();
        valueField.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
