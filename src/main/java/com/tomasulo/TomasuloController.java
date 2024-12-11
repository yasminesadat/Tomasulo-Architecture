package com.tomasulo;

import java.util.Vector;

import com.tomasulo.classes.ReservationStation;
import com.tomasulo.classes.ReservationStationEntry;
import com.tomasulo.classes.Simulator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TomasuloController {

    private TableView<ReservationStationEntry> addSubReservationStationTable;
    private TableView<ReservationStationEntry> mulDivReservationStationTable;
    private TableView<ReservationStationEntry> loadReservationStationTable;
    private TableView<ReservationStationEntry> storeReservationStationTable;

    public TomasuloController() {
        // No-argument constructor
    }

    public void initialize(Stage stage) {
        // Initialize the TableViews and TableColumns
        addSubReservationStationTable = createTableView();
        mulDivReservationStationTable = createTableView();
        loadReservationStationTable = createTableView();
        storeReservationStationTable = createTableView();

        // Create a VBox to hold the tables
        VBox vbox = new VBox();
        vbox.getChildren().addAll(addSubReservationStationTable, mulDivReservationStationTable, loadReservationStationTable, storeReservationStationTable);

        // Create a scene and add the VBox to it
        Scene scene = new Scene(vbox, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private TableView<ReservationStationEntry> createTableView() {
        TableView<ReservationStationEntry> tableView = new TableView<>();

        TableColumn<ReservationStationEntry, String> tagColumn = new TableColumn<>("Tag");
        TableColumn<ReservationStationEntry, Boolean> busyColumn = new TableColumn<>("Busy");
        TableColumn<ReservationStationEntry, Integer> addressColumn = new TableColumn<>("Address");
        TableColumn<ReservationStationEntry, Double> vjColumn = new TableColumn<>("Vj");
        TableColumn<ReservationStationEntry, Double> vkColumn = new TableColumn<>("Vk");
        TableColumn<ReservationStationEntry, String> qjColumn = new TableColumn<>("Qj");
        TableColumn<ReservationStationEntry, String> qkColumn = new TableColumn<>("Qk");

        // Set up the columns with appropriate cell value factories
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));
        busyColumn.setCellValueFactory(new PropertyValueFactory<>("busy"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        vjColumn.setCellValueFactory(new PropertyValueFactory<>("vj"));
        vkColumn.setCellValueFactory(new PropertyValueFactory<>("vk"));
        qjColumn.setCellValueFactory(new PropertyValueFactory<>("qj"));
        qkColumn.setCellValueFactory(new PropertyValueFactory<>("qk"));

        // Add columns to the table
        tableView.getColumns().add(tagColumn);
        tableView.getColumns().add(busyColumn);
        tableView.getColumns().add(addressColumn);
        tableView.getColumns().add(vjColumn);
        tableView.getColumns().add(vkColumn);
        tableView.getColumns().add(qjColumn);
        tableView.getColumns().add(qkColumn);

        return tableView;
    }

    public void populateReservationStationAddSub() {
        ReservationStation rs = Simulator.getAddSubReservationStation();
        Vector<ReservationStationEntry> vectorEntries = rs.getEntries();
        ObservableList<ReservationStationEntry> observableEntries = FXCollections.observableArrayList(vectorEntries);
        addSubReservationStationTable.setItems(observableEntries);      
    }

    public void populateReservationStationMulDiv() {
        Vector<ReservationStationEntry> vectorEntries = Simulator.getMulDivReservationStation().getEntries();
        ObservableList<ReservationStationEntry> observableEntries = FXCollections.observableArrayList(vectorEntries);
        mulDivReservationStationTable.setItems(observableEntries);
    }

    public void populateReservationStationLoad() {
        Vector<ReservationStationEntry> vectorEntries = Simulator.getLoadBuffer().getEntries();
        ObservableList<ReservationStationEntry> observableEntries = FXCollections.observableArrayList(vectorEntries);
        loadReservationStationTable.setItems(observableEntries);
    }

    public void populateReservationStationStore() {
        Vector<ReservationStationEntry> vectorEntries = Simulator.getStoreBuffer().getEntries();
        ObservableList<ReservationStationEntry> observableEntries = FXCollections.observableArrayList(vectorEntries);
        storeReservationStationTable.setItems(observableEntries);
    }

    public void updateAndRefreshTables() {
        // Call the method in Simulator to update entries
        Simulator.executeNextCycle();
        // Refresh the tables
        populateReservationStationAddSub();
        populateReservationStationMulDiv();
        populateReservationStationLoad();
        populateReservationStationStore();
    }

}