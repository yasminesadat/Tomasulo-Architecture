package com.tomasulo;

import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javafx.scene.text.Font;
import com.tomasulo.classes.ReservationStation;
import com.tomasulo.classes.ReservationStationEntry;
import com.tomasulo.classes.Cache;
import com.tomasulo.classes.Instruction;
import com.tomasulo.classes.InstructionQueue;
import com.tomasulo.classes.InstructionStatus;
import com.tomasulo.classes.Memory;
import com.tomasulo.classes.Register;
import com.tomasulo.classes.RegisterFile;
import com.tomasulo.classes.Simulator;
import com.tomasulo.classes.UserInputValues;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TomasuloController {

    private List<Instruction> completedInstructions = new ArrayList<>();
    private TableView<InstructionStatus> instructionStatusTable;
    private TableView<ReservationStationEntry> addSubReservationStationTable;
    private TableView<ReservationStationEntry> mulDivReservationStationTable;
    private TableView<ReservationStationEntry> loadReservationStationTable;
    private TableView<ReservationStationEntry> storeReservationStationTable;
    private TableView<ReservationStationEntry> integerReservationStationTable;
    private TableView<Register> integerRegisterTable;
    private TableView<Register> floatRegisterTable;
    private TableView<Cache.CacheLine> cacheTable;
    private TableView<Instruction> instructionQueueTable;
    private ObservableList<Cache.CacheLine> cacheObservableList = FXCollections.observableArrayList();
    private ObservableList<ReservationStationEntry> mulDivReservationStationObservableList = FXCollections
            .observableArrayList();
    private ObservableList<ReservationStationEntry> addSubReservationStationObservableList = FXCollections
            .observableArrayList();
    private ObservableList<ReservationStationEntry> loadReservationStationObservableList = FXCollections
            .observableArrayList();
    private ObservableList<ReservationStationEntry> storeReservationStationObservableList = FXCollections
            .observableArrayList();
    private ObservableList<ReservationStationEntry> integerReservationStationObservableList = FXCollections
            .observableArrayList();
    private ObservableList<Register> integerRegisters = FXCollections.observableArrayList();
    private ObservableList<Register> floatRegisters = FXCollections.observableArrayList();
    private Label clockCycleLabel;
    private Button nextCycleButton;
    private Button goBackButton;

    public TomasuloController() {

    }

    public void initialize(Stage stage) {
        // Initialize all tables
        // In initialize()
        instructionStatusTable = createInstructionStatusTableView();
        addSubReservationStationTable = createReservationStationTableView("ADD_SUB", false);
        mulDivReservationStationTable = createReservationStationTableView("MUL_DIV", false);
        loadReservationStationTable = createReservationStationTableView("LOAD", false);
        storeReservationStationTable = createReservationStationTableView("STORE", false);
        integerReservationStationTable = createReservationStationTableView("INTEGER", true);
        integerRegisterTable = createRegisterTableView("Integer Registers");
        floatRegisterTable = createRegisterTableView("Float Registers");
        instructionQueueTable = createInstructionQueueTableView();

        // Create labels for each table
        Label instructionStatus = createTableLabel("Instructions Status");
        Label addSubLabel = createTableLabel("Add/Sub Reservation Station");
        Label mulDivLabel = createTableLabel("Mul/Div Reservation Station");
        Label loadLabel = createTableLabel("Load Buffer");
        Label storeLabel = createTableLabel("Store Buffer");
        Label intRegLabel = createTableLabel("Integer Registers");
        Label floatRegLabel = createTableLabel("Float Registers");
        Label integerLabel = createTableLabel("Integer Reservation Station");
        Label instructionQueueLabel = createTableLabel("Instruction Queue");

        // Clock cycle controls
        clockCycleLabel = new Label("Current Clock Cycle: " + 0);
        clockCycleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        nextCycleButton = new Button("Next Cycle");
        goBackButton = new Button("Start Over");
        goBackButton.setOnAction(e -> {
            try {
                navigateToStart();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        nextCycleButton.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-background-color: #3498db; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 1);");
        nextCycleButton.setOnMouseEntered(e -> nextCycleButton.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-background-color: #2980b9; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0, 0, 2);"));
        nextCycleButton.setOnMouseExited(e -> nextCycleButton.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-background-color: #3498db; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 1);"));
        nextCycleButton.setOnAction(e -> advanceClockCycle());

        Button memoryViewButton = new Button("View Memory");
        Button cacheViewButton = new Button("View Cache");
        memoryViewButton.setOnAction(e -> showMemoryView());
        cacheViewButton.setOnAction(e -> showCacheView(Simulator.getCache().cacheLines));

        // Create an HBox for clock cycle controls
        HBox clockControlBox = new HBox(20, clockCycleLabel, nextCycleButton, memoryViewButton, cacheViewButton);
        clockControlBox.setAlignment(javafx.geometry.Pos.CENTER);
        clockControlBox.setPadding(new Insets(15));
        clockControlBox.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #ddd; -fx-border-width: 0 0 1 0;");

        VBox leftColumn = new VBox(10);
        leftColumn.getChildren().addAll(
                createTableSection(instructionStatus, instructionStatusTable),
                createTableSection(addSubLabel, addSubReservationStationTable),
                createTableSection(mulDivLabel, mulDivReservationStationTable));
        leftColumn.setPrefWidth(400);
        // Create containers for each section with label and table
        VBox middleColumn = new VBox(10);
        middleColumn.getChildren().addAll(
                createTableSection(loadLabel, loadReservationStationTable),
                createTableSection(storeLabel, storeReservationStationTable),
                createTableSection(integerLabel, integerReservationStationTable));
        middleColumn.setPrefWidth(400);
        VBox instructionQueueSection = createTableSection(instructionQueueLabel, instructionQueueTable);
        instructionQueueSection.setPrefHeight(150);
        VBox rightColumn = new VBox(10);
        rightColumn.getChildren().addAll(
                createTableSection(intRegLabel, integerRegisterTable),
                createTableSection(floatRegLabel, floatRegisterTable),
                instructionQueueSection);
        rightColumn.setPrefWidth(300);
        VBox actualRight = new VBox(15);
        actualRight.getChildren().addAll(
                rightColumn,
                instructionQueueSection);
        actualRight.setPrefWidth(600);
        // Create layout for reservation stations (left side)

        // Create layout for registers (right side)
        // Create horizontal layout for the three columns
        HBox columnsLayout = new HBox(15);
        columnsLayout.getChildren().addAll(leftColumn, middleColumn, actualRight);
        columnsLayout.setPadding(new Insets(10));
        columnsLayout.setStyle("-fx-background-color: white;");

        // Create horizontal layout for main content

        VBox root = new VBox(10);
        root.getChildren().addAll(clockControlBox, columnsLayout);
        root.setStyle("-fx-background-color: white;");

        // Modify the createTableSection method to add styling
        setTableSizes();

        // Populate initial data
        populateInstructionQueueTable();

        // Create and configure the scene
        Scene scene = new Scene(root);
        scene.getRoot().setStyle("-fx-font-family: 'Arial'; -fx-background-color: white;");

        // Configure and show the stage
        stage.setTitle("Tomasulo Algorithm Simulator");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    private TableView<Register> createRegisterTableView(String title) {
        TableView<Register> tableView = new TableView<>();

        // Add register label column
        TableColumn<Register, String> labelColumn = new TableColumn<>("Register");
        labelColumn.setCellValueFactory(cellData -> {
            int index = tableView.getItems().indexOf(cellData.getValue());
            String prefix = title.startsWith("Float") ? "F" : "R";
            return new javafx.beans.property.SimpleStringProperty(prefix + index);
        });

        TableColumn<Register, Double> valueColumn = new TableColumn<>("Value");
        TableColumn<Register, String> qColumn = new TableColumn<>("Q");

        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        qColumn.setCellValueFactory(new PropertyValueFactory<>("Q"));

        // Set column widths
        labelColumn.setPrefWidth(80);
        valueColumn.setPrefWidth(100);
        qColumn.setPrefWidth(80);

        tableView.getColumns().addAll(labelColumn, valueColumn, qColumn);
        tableView.setPlaceholder(new javafx.scene.control.Label(title + " - No Data"));

        return tableView;
    }

    private Label createTableLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 5px 0; -fx-text-fill: #2c3e50;");
        return label;
    }

    private VBox createTableSection(Label label, TableView<?> table) {
        VBox section = new VBox(5);
        section.getChildren().addAll(label, table);
        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);
        section.setStyle("-fx-background-color: white; -fx-padding: 10; " +
                "-fx-border-color: #e0e0e0; -fx-border-radius: 4; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0, 0, 1);");
        return section;
    }

    // Set heights for all tables
    private void setTableSizes() {
        double rowHeight = 40; // height per row

        // Create mapping of tables to their respective sizes
        Map<TableView<?>, Integer> tableSizeMap = new HashMap<>();
        tableSizeMap.put(addSubReservationStationTable, UserInputValues.reservationStationAddSubSize);
        tableSizeMap.put(mulDivReservationStationTable, UserInputValues.reservationStationMulDivSize);
        tableSizeMap.put(loadReservationStationTable, UserInputValues.loadBufferSize);
        tableSizeMap.put(storeReservationStationTable, UserInputValues.storeBufferSize);
        tableSizeMap.put(integerReservationStationTable, UserInputValues.reservationStationAddSubIntegerSize);

        // tableSizeMap.put(instructionQueueTable, Simulator.instructionQueue.size());
        // // Reasonable default for
        // instruction queue
        Map<TableView<?>, Integer> registerSizeMap = new HashMap<>();
        registerSizeMap.put(integerRegisterTable, 32);
        registerSizeMap.put(floatRegisterTable, 32);
        Map<TableView<?>, Integer> queueMap = new HashMap<>();
        queueMap.put(instructionQueueTable, Simulator.instructionQueue.size()); // Reasonable default for

        // Apply settings to each table
        for (Map.Entry<TableView<?>, Integer> entry : tableSizeMap.entrySet()) {
            TableView<?> table = entry.getKey();
            int rows = entry.getValue();

            table.setFixedCellSize(rowHeight);
            table.setPrefHeight(rowHeight * rows + 30); // +30 for header
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1; " +
                    "-fx-border-radius: 4; -fx-background-color: white;");
        }
        for (Map.Entry<TableView<?>, Integer> entry : registerSizeMap.entrySet()) {
            TableView<?> table = entry.getKey();
            int rows = entry.getValue();

            table.setFixedCellSize(rowHeight);
            table.setPrefHeight(5 * rows + 30); // +30 for header
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1; " +
                    "-fx-border-radius: 4; -fx-background-color: white;");
        }
        for (Map.Entry<TableView<?>, Integer> entry : queueMap.entrySet()) {
            TableView<?> table = entry.getKey();
            int rows = entry.getValue();

            table.setFixedCellSize(rowHeight);
            table.setPrefHeight(10 * rows + 30); // +30 for header
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1; " +
                    "-fx-border-radius: 4; -fx-background-color: white;");
        }

        // instructionQueueTable.setPrefHeight(150);
    }

    // Make instruction queue table slightly shorter

    private void advanceClockCycle() {
        clockCycleLabel.setText("Current Clock Cycle: " + (Simulator.getClockCycle()));
        boolean stopSimulation = Simulator.executeNextCycle();

        // Update clock cycle labels
        populateReservationStationTables();
        populateRegisterTables();
        // populateInstructionQueueTable();
        // updateAndRefreshTables();

        if (stopSimulation) {

            nextCycleButton.setDisable(true);
        }

    }

    private void navigateToStart() throws IOException {
        // Stage stage = (Stage) goBackButton.getScene().getWindow();
        // App primarycontroller = new App();
        // primarycontroller.start(stage);
        // 3yza arga3

    }

    private void showMemoryView() {
        Stage memoryStage = new Stage();
        memoryStage.setTitle("Tomasulo Simulator - Memory Contents");
        TableView<List<String>> memoryTableView = new TableView<>();

        TableColumn<List<String>, String> addressColumn = new TableColumn<>("Address");
        TableColumn<List<String>, String> hexColumn = new TableColumn<>("Hex Value");

        addressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        hexColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));

        memoryTableView.getColumns().addAll(addressColumn, hexColumn);

        ObservableList<List<String>> memoryData = FXCollections.observableArrayList();
        Memory memory = Simulator.getMemory();

        for (int i = 0; i < memory.getSize(); i++) {
            byte value = memory.readByte(i);
            // System.out.printf("Address 0x%04X: 0x%02X%n", i, value);
            memoryData.add(Arrays.asList(
                    String.format("0x%04X", i),
                    String.format("0x%02X", value)));
        }

        memoryTableView.setItems(memoryData);

        TextField searchField = new TextField();
        searchField.setPromptText("Search Address or Value");

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            memoryTableView.setItems(
                    memoryData.filtered(entry -> entry.get(0).toLowerCase().contains(newValue.toLowerCase()) ||
                            entry.get(1).toLowerCase().contains(newValue.toLowerCase())));
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(searchField, memoryTableView);
        layout.setPadding(new Insets(10));

        Scene memoryScene = new Scene(layout, 500, 600);
        memoryScene.getRoot().setStyle("-fx-font-family: 'Arial'; -fx-background-color: white;");

        memoryStage.setScene(memoryScene);
        memoryStage.show();
    }

    private void showCacheView(Cache.CacheLine[] cacheLines) {
        // Create a new Stage for the pop-up
        Stage popupStage = new Stage();

        // Create the TableView
        TableView<Cache.CacheLine> tableView = createCacheTableView(cacheLines);

        // Set the stage properties
        popupStage.setTitle("Cache View"); // Set the title of the pop-up
        popupStage.initModality(Modality.APPLICATION_MODAL); // Make the window modal (blocks interaction with parent)
        popupStage.setWidth(500); // Set width for the pop-up
        popupStage.setHeight(400); // Set height for the pop-up

        // Create a layout and add the TableView to it
        StackPane layout = new StackPane();
        layout.getChildren().add(tableView);

        // Set up the scene
        Scene scene = new Scene(layout, 500, 400); // Define size for the scene
        popupStage.setScene(scene); // Attach the scene to the stage
        scene.getRoot().setStyle("-fx-font-family: 'Arial'; -fx-background-color: white;");

        tableView.setFixedCellSize(30);
        tableView.setPrefHeight(cacheLines.length * 30 + 30); // +30 for header
        tableView.setMaxHeight(cacheLines.length * 30 + 30);

        // Show the pop-up window
        popupStage.show();
    }

    private TableView<ReservationStationEntry> createReservationStationTableView(String type, boolean isBranch) {
        TableView<ReservationStationEntry> tableView = new TableView<>();
        TableColumn<ReservationStationEntry, String> tagColumn = new TableColumn<>("Tag");
        TableColumn<ReservationStationEntry, String> operationColumn = new TableColumn<>("Op");
        operationColumn.setCellValueFactory(cellData -> {
            ReservationStationEntry entry = cellData.getValue();
            if (entry != null && entry.getCurrInstruction() != null) {
                return new SimpleStringProperty(entry.getCurrInstruction().getType());
            }
            return new SimpleStringProperty("");
        });
        TableColumn<ReservationStationEntry, Boolean> busyColumn = new TableColumn<>("Busy");
        TableColumn<ReservationStationEntry, Integer> addressColumn = new TableColumn<>(
                isBranch ? "Branch Address " : "Address");
        TableColumn<ReservationStationEntry, Double> vjColumn = new TableColumn<>("Vj");
        TableColumn<ReservationStationEntry, Double> vkColumn = new TableColumn<>("Vk");
        TableColumn<ReservationStationEntry, String> qjColumn = new TableColumn<>("Qj");
        TableColumn<ReservationStationEntry, String> qkColumn = new TableColumn<>("Qk");
        TableColumn<ReservationStationEntry, Integer> remainingTimeColumn = new TableColumn<>("Remaining Time");

        tagColumn.setPrefWidth(80);
        busyColumn.setPrefWidth(60);
        operationColumn.setPrefWidth(80);
        addressColumn.setPrefWidth(80);
        vjColumn.setPrefWidth(80);
        vkColumn.setPrefWidth(80);
        qjColumn.setPrefWidth(80);
        qkColumn.setPrefWidth(80);
        remainingTimeColumn.setPrefWidth(200);
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));
        busyColumn.setCellValueFactory(new PropertyValueFactory<>("busy"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        vjColumn.setCellValueFactory(new PropertyValueFactory<>("vj"));
        vkColumn.setCellValueFactory(new PropertyValueFactory<>("vk"));
        qjColumn.setCellValueFactory(new PropertyValueFactory<>("qj"));
        qkColumn.setCellValueFactory(new PropertyValueFactory<>("qk"));

        // Add columns based on type
        List<TableColumn<ReservationStationEntry, ?>> columns = new ArrayList<>();
        columns.add(tagColumn);
        columns.add(busyColumn);

        switch (type) {
            case "LOAD":

                columns.add(addressColumn);
                break;

            case "STORE":

                columns.add(addressColumn);
                columns.add(vjColumn); // for base address calculation
                columns.add(qjColumn);
                columns.add(vkColumn); // for base address calculation
                columns.add(qkColumn);
                break;

            case "ADD_SUB":
            case "MUL_DIV":
            case "INTEGER":
                columns.add(operationColumn);
                columns.add(vjColumn);
                columns.add(vkColumn);
                columns.add(qjColumn);
                columns.add(qkColumn);
                break;
        }

        tableView.getColumns().addAll(columns);
        return tableView;
    }

    private TableView<InstructionStatus> createInstructionStatusTableView() {
        TableView<InstructionStatus> tableView = new TableView<>();
        tableView.setPlaceholder(new javafx.scene.control.Label("Instruction Status"));

        TableColumn<InstructionStatus, String> typeColumn = new TableColumn<>("Type");
        TableColumn<InstructionStatus, String> rdColumn = new TableColumn<>("Rd");
        TableColumn<InstructionStatus, String> issueTimeColumn = new TableColumn<>("Issue Time");
        TableColumn<InstructionStatus, String> startTimeColumn = new TableColumn<>("Start Time");
        TableColumn<InstructionStatus, String> endTimeColumn = new TableColumn<>("End Time");
        TableColumn<InstructionStatus, String> writeTimeColumn = new TableColumn<>("Write Time");
        TableColumn<InstructionStatus, Integer> remainingTimeColumn = new TableColumn<>("Remaining Time");

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        rdColumn.setCellValueFactory(new PropertyValueFactory<>("rd"));
        issueTimeColumn.setCellValueFactory(new PropertyValueFactory<>("issueTime"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        writeTimeColumn.setCellValueFactory(new PropertyValueFactory<>("writeTime"));
        remainingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("remainingTime"));

        tableView.getColumns().addAll(typeColumn, rdColumn, issueTimeColumn, startTimeColumn,
                endTimeColumn, writeTimeColumn, remainingTimeColumn);
        return tableView;
    }

    private TableView<Instruction> createInstructionQueueTableView() {
        TableView<Instruction> tableView = new TableView<>();
        tableView.setPlaceholder(new javafx.scene.control.Label("Instruction Queue - No Instructions"));

        TableColumn<Instruction, String> typeColumn = new TableColumn<>("Type");
        TableColumn<Instruction, String> rsColumn = new TableColumn<>("Rs");
        TableColumn<Instruction, String> rtColumn = new TableColumn<>("Rt");
        TableColumn<Instruction, String> rdColumn = new TableColumn<>("Rd");
        TableColumn<Instruction, Integer> pcColumn = new TableColumn<>("PC");
        TableColumn<Instruction, Integer> immediateColumn = new TableColumn<>("Immediate");

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        rsColumn.setCellValueFactory(new PropertyValueFactory<>("rs"));
        rtColumn.setCellValueFactory(new PropertyValueFactory<>("rt"));
        rdColumn.setCellValueFactory(new PropertyValueFactory<>("rd"));
        pcColumn.setCellValueFactory(new PropertyValueFactory<>("pc"));
        immediateColumn.setCellValueFactory(new PropertyValueFactory<>("immediate"));

        tableView.getColumns().addAll(typeColumn, rsColumn, rtColumn, rdColumn, pcColumn, immediateColumn);

        return tableView;
    }

    private TableView<Cache.CacheLine> createCacheTableView(Cache.CacheLine[] cacheLines) {
        TableView<Cache.CacheLine> tableView = new TableView<>();

        // Set the table's preferred size based on cache lines
        tableView.setPrefHeight(cacheLines.length * 30);

        // Index Column - using list position since there's no stored index
        TableColumn<Cache.CacheLine, Integer> indexColumn = new TableColumn<>("Index");
        indexColumn.setCellValueFactory(cellData -> {
            int index = tableView.getItems().indexOf(cellData.getValue());
            return new SimpleIntegerProperty(index).asObject();
        });
        indexColumn.setPrefWidth(60);

        // Valid Column with checkbox
        TableColumn<Cache.CacheLine, Boolean> validColumn = new TableColumn<>("Valid");
        validColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null) {
                // System.out.println("Valid Value: " + cellData.getValue().getValid());
                return new SimpleBooleanProperty(cellData.getValue().getValid()).asObject();
            }
            return null;
        });

        validColumn.setPrefWidth(60);

        // Tag Column - now showing as regular integer
        TableColumn<Cache.CacheLine, Integer> tagColumn = new TableColumn<>("Tag");
        tagColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTag()).asObject());
        tagColumn.setPrefWidth(80);

        // Data Column with hex formatting
        TableColumn<Cache.CacheLine, String> dataColumn = new TableColumn<>("Data");
        dataColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(bytesToHexString(cellData.getValue().getData())));
        dataColumn.setPrefWidth(200);

        // Add all columns to the table
        tableView.getColumns().addAll(indexColumn, validColumn, tagColumn, dataColumn);

        // Initialize table with the actual cache lines array
        ObservableList<Cache.CacheLine> cacheLinesList = FXCollections.observableArrayList(Arrays.asList(cacheLines));
        tableView.setItems(cacheLinesList);

        return tableView;
    }

    private String bytesToHexString(byte[] bytes) {
        if (bytes == null)
            return "";
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02X ", b));
        }
        return hex.toString().trim();
    }

    // Method to update the table view when cache state changes
    public void updateCacheView(TableView<Cache.CacheLine> tableView, Cache.CacheLine[] cacheLines) {
        ObservableList<Cache.CacheLine> items = tableView.getItems();
        items.clear();
        items.addAll(Arrays.asList(cacheLines));
    }

    public void populateReservationStationTables() {
        populateReservationStationAddSub();
        populateReservationStationMulDiv();
        populateReservationStationLoad();
        populateReservationStationStore();
        populateReservationStationInteger();
        populateInstructionStatusTable();
    }

    private void populateReservationStationInteger() {
        integerReservationStationObservableList.setAll(Simulator.getIntegerReservationStation().getEntries());
        integerReservationStationTable.setItems(integerReservationStationObservableList);
        integerReservationStationTable.refresh();
    }

    private void populateReservationStationAddSub() {
        addSubReservationStationObservableList.setAll(Simulator.getAddSubReservationStation().getEntries());
        addSubReservationStationTable.setItems(addSubReservationStationObservableList);
        addSubReservationStationTable.refresh();
    }

    private void populateReservationStationMulDiv() {
        mulDivReservationStationObservableList.setAll(Simulator.getMulDivReservationStation().getEntries()); // Clears
                                                                                                             // and adds
                                                                                                             // new
                                                                                                             // entries
        mulDivReservationStationTable.setItems(mulDivReservationStationObservableList);
        mulDivReservationStationTable.refresh();
    }

    private void populateReservationStationLoad() {
        loadReservationStationObservableList.setAll(Simulator.getLoadBuffer().getEntries());
        loadReservationStationTable.setItems(loadReservationStationObservableList);
    }

    private void populateReservationStationStore() {
        storeReservationStationObservableList.setAll(Simulator.getStoreBuffer().getEntries());
        storeReservationStationTable.setItems(storeReservationStationObservableList);

    }

    private void populateRegisterTables() {
        integerRegisters.setAll(Simulator.registerFile.getR());
        integerRegisterTable.setItems(integerRegisters);
        floatRegisters.setAll(Simulator.registerFile.getF());
        floatRegisterTable.setItems(floatRegisters);
    }

    private void populateInstructionStatusTable() {
        List<InstructionStatus> instructionStatusList = new ArrayList<>();
        List<Instruction> allInstructions = new ArrayList<>();
        allInstructions.addAll(completedInstructions);

        for (ReservationStationEntry entry : Simulator.addSubReservationStation.getEntries()) {
            if (entry.getCurrInstruction() != null) {
                if(!allInstructions.contains(entry.getCurrInstruction())) {
                    
                    allInstructions.add(entry.getCurrInstruction());
                }
               
            }

        }

        for (ReservationStationEntry entry : Simulator.mulDivReservationStation.getEntries()) {
            if (entry.getCurrInstruction() != null) {
                if(!allInstructions.contains(entry.getCurrInstruction())) {
                allInstructions.add(entry.getCurrInstruction());
                }
            }

        }

        for (ReservationStationEntry entry : Simulator.integerReservationStation.getEntries()) {
            if (entry.getCurrInstruction() != null) {
                if(!allInstructions.contains(entry.getCurrInstruction())) {
                allInstructions.add(entry.getCurrInstruction());
                }
            }

        }

        for (ReservationStationEntry entry : Simulator.loadBuffer.getEntries()) {
            if (entry.getCurrInstruction() != null) {
                if(!allInstructions.contains(entry.getCurrInstruction())) {
                allInstructions.add(entry.getCurrInstruction());
                }
            }

        }

        for (ReservationStationEntry entry : Simulator.storeBuffer.getEntries()) {
            if (entry.getCurrInstruction() != null) {
                if(!allInstructions.contains(entry.getCurrInstruction())) {
                allInstructions.add(entry.getCurrInstruction());
                }
            }

        }

        

        allInstructions.sort(Comparator.comparingInt(Instruction::getIssueTime));

        
        //System.out.println("All Instructionsssssssssssssssssssss: " + allInstructions.size());

        for (Instruction instruction : allInstructions) {
            //System.out.println("Instructionnnnnnnnnnnnnnnnnnnnn: " + instruction.getIssueTime());
            // if (instruction.getIssueTime() != -1) {
                InstructionStatus instructionStatus = new InstructionStatus(
                    instruction.getType(),
                    instruction.getRd(),
                    instruction.getIssueTime() == -1 ? "Null" : String.valueOf(instruction.getIssueTime()),
                    instruction.getStartTime() == -1 ? "Null" : String.valueOf(instruction.getStartTime()),
                    instruction.getEndTime() == -1 ? "Null" : String.valueOf(instruction.getEndTime()),
                    instruction.getWriteTime() == -1 ? "Null" : String.valueOf(instruction.getWriteTime()),
                    instruction.getType()
                   
                );

                
                if(instruction.getIssueTime()!=-1 && instruction.getStartTime()==-1) {
                    instructionStatus.setRemainingTime(InstructionStatus.getInitialRemainingTime(instruction.getType()));
                }

                if (!"Null".equals(instructionStatus.getStartTime()) && !"0".equals(instructionStatus.getRemainingTime())) {
                    int remaining = Integer.parseInt(instructionStatus.getRemainingTime());
                    if (remaining > 0) {
                       // remaining = Integer.parseInt(instructionStatus.getEndTime()) + Integer.parseInt(instructionStatus.getStartTime()) - Simulator.getClockCycle();
                       remaining = Integer.parseInt(instructionStatus.getEndTime()) - Simulator.getClockCycle() + 1;
                       if (remaining >= 0) {
                            instructionStatus.setRemainingTime(String.valueOf(remaining));
                        } else {
                            instructionStatus.setRemainingTime("0");
                        }
                    }
                }
              
        

                instructionStatusList.add(instructionStatus);
                
                if (instruction.getWriteTime() != -1 && !completedInstructions.contains(instruction) ) {
                    instruction.setStartTime(instruction.getStartTime());
                    completedInstructions.add(instruction);
                }

            // }
        }

        //System.out.println("Instruction Status Listtttttttttttttttt: " + instructionStatusList.size());
    
        ObservableList<InstructionStatus> observableInstructions = FXCollections.observableArrayList(instructionStatusList);
        instructionStatusTable.setItems(observableInstructions);
       
    }

    private void populateInstructionQueueTable() {
        List<Instruction> instructionList = new ArrayList<>();
        int size = Simulator.getInstructionQueue().size();
        InstructionQueue instructionQueue = Simulator.getInstructionQueue();
        for (int i = 0; i < size; i++) {
            Instruction instruction = instructionQueue.dequeueInstruction();
            if (instruction != null) {
                instructionList.add(instruction);
            }
        }
        ObservableList<Instruction> observableInstructions = FXCollections.observableArrayList(instructionList);
        instructionQueueTable.setItems(observableInstructions);
    }

    private void populateCacheTable() {
        cacheObservableList.setAll(Simulator.getCache().cacheLines);
        System.out.println("populate cache table" + Simulator.getCache());
        cacheTable.setItems(cacheObservableList);
    }

    public void updateAndRefreshTables() {

        // Simulator.executeNextCycle();
        populateReservationStationTables();
        populateRegisterTables();
        // populateInstructionQueueTable();
    }
}