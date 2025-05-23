package com.example.gestionvuelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FlightManagementController {

    @FXML private TableView<Flight> tableFlights;
    @FXML private TableColumn<Flight, String> colNumber;
    @FXML private TableColumn<Flight, String> colDestination;
    @FXML private TableColumn<Flight, String> colDepartureDate;
    @FXML private TableColumn<Flight, String> colArrivalDate;
    @FXML private TableColumn<Flight, String> colStatus;

    @FXML private TextField txtNumber;
    @FXML private TextField txtDestination;
    @FXML private DatePicker dpDepartureDate;
    @FXML private DatePicker dpArrivalDate;
    @FXML private ComboBox<String> cmbStatus;
    @FXML private Button btnSave;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;

    private Connection connection;
    private ObservableList<Flight> flightList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        connectDatabase();
        setupTable();
        loadFlights();
        cmbStatus.setItems(FXCollections.observableArrayList("Scheduled", "Delayed", "Canceled", "Departed", "Arrived"));

        tableFlights.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) loadForm(newSelection);
        });
    }

    private void connectDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/airline", "root", "password");
        } catch (SQLException e) {
            showAlert("Connection Error", "Could not connect to the database.");
        }
    }

    private void setupTable() {
        colNumber.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
        colDestination.setCellValueFactory(cellData -> cellData.getValue().destinationProperty());
        colDepartureDate.setCellValueFactory(cellData -> cellData.getValue().departureDateProperty());
        colArrivalDate.setCellValueFactory(cellData -> cellData.getValue().arrivalDateProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        tableFlights.setItems(flightList);
    }

    private void loadFlights() {
        flightList.clear();
        String query = "SELECT number, destination, departure_date, arrival_date, status FROM flight";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                flightList.add(new Flight(
                        rs.getString("number"),
                        rs.getString("destination"),
                        rs.getTimestamp("departure_date").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        rs.getTimestamp("arrival_date").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            showAlert("Error", "Could not load flights.");
        }
    }

    private void saveFlight() {
        String query = "INSERT INTO flight (number, destination, departure_date, arrival_date, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, txtNumber.getText());
            stmt.setString(2, txtDestination.getText());
            stmt.setTimestamp(3, Timestamp.valueOf(dpDepartureDate.getValue().atStartOfDay()));
            stmt.setTimestamp(4, Timestamp.valueOf(dpArrivalDate.getValue().atStartOfDay()));
            stmt.setString(5, cmbStatus.getValue());
            stmt.executeUpdate();
            loadFlights();
        } catch (SQLException e) {
            showAlert("Error", "Could not save flight.");
        }
    }

    private void updateFlight() {
        String query = "UPDATE flight SET destination=?, departure_date=?, arrival_date=?, status=? WHERE number=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, txtDestination.getText());
            stmt.setTimestamp(2, Timestamp.valueOf(dpDepartureDate.getValue().atStartOfDay()));
            stmt.setTimestamp(3, Timestamp.valueOf(dpArrivalDate.getValue().atStartOfDay()));
            stmt.setString(4, cmbStatus.getValue());
            stmt.setString(5, txtNumber.getText());
            stmt.executeUpdate();
            loadFlights();
        } catch (SQLException e) {
            showAlert("Error", "Could not update flight.");
        }
    }

    private void deleteFlight() {
        String query = "DELETE FROM flight WHERE number=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, txtNumber.getText());
            stmt.executeUpdate();
            loadFlights();
        } catch (SQLException e) {
            showAlert("Error", "Could not delete flight.");
        }
    }

    private void loadForm(Flight flight) {
        txtNumber.setText(flight.getNumber());
        txtDestination.setText(flight.getDestination());
        dpDepartureDate.setValue(LocalDate.parse(flight.getDepartureDate().split(" ")[0]));
        dpArrivalDate.setValue(LocalDate.parse(flight.getArrivalDate().split(" ")[0]));
        cmbStatus.setValue(flight.getStatus());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
