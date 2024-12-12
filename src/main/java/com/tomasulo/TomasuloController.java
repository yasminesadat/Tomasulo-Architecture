package com.tomasulo;

import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javafx.scene.text.Font;
import com.tomasulo.classes.ReservationStation;
import com.tomasulo.classes.ReservationStationEntry;
import com.tomasulo.classes.Instruction;
import com.tomasulo.classes.InstructionQueue;
import com.tomasulo.classes.Register;
import com.tomasulo.classes.RegisterFile;
import com.tomasulo.classes.Simulator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TomasuloController {

    private TableView<ReservationStationEntry> addSubReservationStationTable;
    private TableView<ReservationStationEntry> mulDivReservationStationTable;
    private TableView<ReservationStationEntry> loadReservationStationTable;
    private TableView<ReservationStationEntry> storeReservationStationTable;
    private TableView<Register> integerRegisterTable;
    private TableView<Register> floatRegisterTable;
    private TableView<Instruction> instructionQueueTable;
    private ObservableList<ReservationStationEntry> mulDivReservationStationObservableList = FXCollections
            .observableArrayList();
    private ObservableList<ReservationStationEntry> addSubReservationStationObservableList = FXCollections
            .observableArrayList();
    private ObservableList<ReservationStationEntry> loadReservationStationObservableList = FXCollections
            .observableArrayList();
    private ObservableList<ReservationStationEntry> storeReservationStationObservableList = FXCollections
            .observableArrayList();
    private ObservableList<Register> integerRegisters = FXCollections.observableArrayList();
    private ObservableList<Register> floatRegisters = FXCollections.observableArrayList();
    private Label clockCycleLabel;
    private Button nextCycleButton;

    public TomasuloController() {

    }

    public void initialize(Stage stage) {
        // Initialize all tables
        addSubReservationStationTable = createReservationStationTableView();
        mulDivReservationStationTable = createReservationStationTableView();
        loadReservationStationTable = createReservationStationTableView();
        storeReservationStationTable = createReservationStationTableView();
        integerRegisterTable = createRegisterTableView("Integer Registers");
        floatRegisterTable = createRegisterTableView("Float Registers");
        instructionQueueTable = createInstructionQueueTableView();

        // Create labels for each table
        Label addSubLabel = createTableLabel("Add/Sub Reservation Station");
        Label mulDivLabel = createTableLabel("Mul/Div Reservation Station");
        Label loadLabel = createTableLabel("Load Buffer");
        Label storeLabel = createTableLabel("Store Buffer");
        Label intRegLabel = createTableLabel("Integer Registers");
        Label floatRegLabel = createTableLabel("Float Registers");
        Label instructionQueueLabel = createTableLabel("Instruction Queue");

        // Clock cycle controls
        clockCycleLabel = new Label("Current Clock Cycle: " + Simulator.getClockCycle());
        nextCycleButton = new Button("Next Cycle");
        nextCycleButton.setOnAction(e -> advanceClockCycle());

        // Create an HBox for clock cycle controls
        HBox clockControlBox = new HBox(10, clockCycleLabel, nextCycleButton);
        clockControlBox.setPadding(new Insets(10));

        // Create containers for each section with label and table
        VBox addSubSection = createTableSection(addSubLabel, addSubReservationStationTable);
        VBox mulDivSection = createTableSection(mulDivLabel, mulDivReservationStationTable);
        VBox loadSection = createTableSection(loadLabel, loadReservationStationTable);
        VBox storeSection = createTableSection(storeLabel, storeReservationStationTable);
        VBox intRegSection = createTableSection(intRegLabel, integerRegisterTable);
        VBox floatRegSection = createTableSection(floatRegLabel, floatRegisterTable);
        VBox instructionSection = createTableSection(instructionQueueLabel, instructionQueueTable);

        // Create layout for reservation stations (left side)
        VBox reservationStations = new VBox(10);
        reservationStations.getChildren().addAll(addSubSection, mulDivSection, loadSection, storeSection);
        reservationStations.setPrefWidth(500);

        // Create layout for registers (right side)
        VBox registers = new VBox(10);
        registers.getChildren().addAll(intRegSection, floatRegSection);
        registers.setPrefWidth(300);

        // Create horizontal layout for main content
        HBox mainContent = new HBox(10);
        mainContent.getChildren().addAll(reservationStations, registers);
        mainContent.setPadding(new Insets(10));

        // Create final vertical layout
        VBox root = new VBox(10);
        root.getChildren().addAll(clockControlBox, mainContent, instructionSection);
        root.setPadding(new Insets(10));

        // Set table sizes
        setTableSizes();

        // Populate initial data
        populateInstructionQueueTable();

        // Create and configure the scene
        Scene scene = new Scene(root);
        scene.getRoot().setStyle("-fx-font-family: 'Arial';");

        // Configure and show the stage
        stage.setTitle("Tomasulo Algorithm Simulator");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    private Label createTableLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        return label;
    }

    private VBox createTableSection(Label label, TableView<?> table) {
        VBox section = new VBox(5);
        section.getChildren().addAll(label, table);
        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);
        return section;
    }

    private void setTableSizes() {
        // Set preferred heights for all tables
        addSubReservationStationTable.setPrefHeight(150);
        mulDivReservationStationTable.setPrefHeight(150);
        loadReservationStationTable.setPrefHeight(150);
        storeReservationStationTable.setPrefHeight(150);
        integerRegisterTable.setPrefHeight(200);
        floatRegisterTable.setPrefHeight(200);
        instructionQueueTable.setPrefHeight(150);

        // Make tables fill their containers
        addSubReservationStationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        mulDivReservationStationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadReservationStationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        storeReservationStationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        integerRegisterTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        floatRegisterTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        instructionQueueTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void advanceClockCycle() {
        // Execute next cycle
        boolean continueSimulation = Simulator.executeNextCycle();

        // Update clock cycle label
        clockCycleLabel.setText("Current Clock Cycle: " + Simulator.getClockCycle());

        populateReservationStationTables();
        populateRegisterTables();
        // populateInstructionQueueTable();
        // updateAndRefreshTables();
        if (!continueSimulation) {
            nextCycleButton.setDisable(true);
        }

    }

    private TableView<ReservationStationEntry> createReservationStationTableView() {
        TableView<ReservationStationEntry> tableView = new TableView<>();

        TableColumn<ReservationStationEntry, String> tagColumn = new TableColumn<>("Tag");
        TableColumn<ReservationStationEntry, Boolean> busyColumn = new TableColumn<>("Busy");
        TableColumn<ReservationStationEntry, Integer> addressColumn = new TableColumn<>("Address");
        TableColumn<ReservationStationEntry, Double> vjColumn = new TableColumn<>("Vj");
        TableColumn<ReservationStationEntry, Double> vkColumn = new TableColumn<>("Vk");
        TableColumn<ReservationStationEntry, String> qjColumn = new TableColumn<>("Qj");
        TableColumn<ReservationStationEntry, String> qkColumn = new TableColumn<>("Qk");

        // New column for remaining time and current clock cycle
        TableColumn<ReservationStationEntry, String> remainingTimeColumn = new TableColumn<>(
                "Remaining Time/Clock Cycle");
        tagColumn.setPrefWidth(80);
        busyColumn.setPrefWidth(60);
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

        remainingTimeColumn.setCellValueFactory(cellData -> {
            ReservationStationEntry entry = cellData.getValue();
            if (entry.getCurrInstruction() != null
                    && entry.getCurrInstruction().getIssueTime() == Simulator.clockCycle) {
                int remainingTime = entry.getCurrInstruction().getEndTime() - Simulator.clockCycle;
                return new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(Math.max(0, remainingTime)));
            }
            remainingTimeColumn.setMinWidth(200);
            return new javafx.beans.property.SimpleStringProperty("N/A");
        });
        tableView.getColumns().addAll(
                tagColumn, busyColumn, addressColumn,
                vjColumn, vkColumn, qjColumn, qkColumn,
                remainingTimeColumn);
        return tableView;
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

    public void populateReservationStationTables() {
        populateReservationStationAddSub();
        populateReservationStationMulDiv();
        populateReservationStationLoad();
        populateReservationStationStore();
    }

    private void populateReservationStationAddSub() {
        addSubReservationStationObservableList.setAll(Simulator.getAddSubReservationStation().getEntries());
        addSubReservationStationTable.setItems(addSubReservationStationObservableList);
    }

    private void populateReservationStationMulDiv() {
        mulDivReservationStationObservableList.setAll(Simulator.getMulDivReservationStation().getEntries()); // Clears
                                                                                                             // and adds
                                                                                                             // new
                                                                                                             // entries
        mulDivReservationStationTable.setItems(mulDivReservationStationObservableList);
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

    public void updateAndRefreshTables() {

        // Simulator.executeNextCycle();
        populateReservationStationTables();
        populateRegisterTables();
        // populateInstructionQueueTable();
    }
}