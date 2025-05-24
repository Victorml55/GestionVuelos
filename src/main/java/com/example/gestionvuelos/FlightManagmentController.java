package com.example.gestionvuelos;

import com.example.gestionvuelos.database.VueloDAO;
import com.example.gestionvuelos.models.Vuelo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FlightManagmentController {

    @FXML private TableView<Vuelo> tableVuelos;
    @FXML private TableColumn<Vuelo, String> colNumero;
    @FXML private TableColumn<Vuelo, String> colDestino;
    @FXML private TableColumn<Vuelo, String> colFechaSalida;
    @FXML private TableColumn<Vuelo, String> colFechaLlegada;
    @FXML private TableColumn<Vuelo, String> colEstado;

    @FXML private TextField txtNumero;
    @FXML private TextField txtDestino;
    @FXML private DatePicker dpFechaSalida;
    @FXML private DatePicker dpFechaLlegada;
    @FXML private ComboBox<String> cmbEstado;
    @FXML private Button btnGuardar;
    @FXML private Button btnModificar;
    @FXML private Button btnEliminar;

    private Connection connection;
    private VueloDAO vueloDAO;
    private ObservableList<Vuelo> vuelosList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        conectarBaseDatos();
        vueloDAO = new VueloDAO(connection);
        configurarTabla();
        cargarVuelos();

        cmbEstado.setItems(FXCollections.observableArrayList("programado", "retrasado", "cancelado", "salido", "llegado"));

        tableVuelos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) cargarFormulario(newSelection);
        });
    }

    private void conectarBaseDatos() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/aerolinea", "root", "password");
        } catch (SQLException e) {
            mostrarAlerta("Error de conexión", "No se pudo conectar a la base de datos.");
        }
    }

    private void configurarTabla() {
        colNumero.setCellValueFactory(cellData -> cellData.getValue().numeroProperty());
        colDestino.setCellValueFactory(cellData -> cellData.getValue().destinoProperty());
        colFechaSalida.setCellValueFactory(cellData -> cellData.getValue().fecha_salidaProperty().asString());
        colFechaLlegada.setCellValueFactory(cellData -> cellData.getValue().fecha_llegadaProperty().asString());
        colEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());

        tableVuelos.setItems(vuelosList);
    }

    private void cargarVuelos() {
        vuelosList.clear();
        vuelosList.addAll(vueloDAO.obtenerTodosLosVuelos());
    }

    private void guardarVuelo() {
        Vuelo vuelo = new Vuelo(
                0,
                txtNumero.getText(),
                txtDestino.getText(),
                dpFechaSalida.getValue().atStartOfDay(),
                dpFechaLlegada.getValue().atStartOfDay(),
                cmbEstado.getValue(),
                1, // ID aeronave (modificar según lógica de selección)
                1  // ID aeropuerto (modificar según lógica de selección)
        );

        if (vueloDAO.agregarVuelo(vuelo)) {
            cargarVuelos();
        } else {
            mostrarAlerta("Error", "No se pudo guardar el vuelo.");
        }
    }

    private void modificarVuelo() {
        Vuelo vuelo = tableVuelos.getSelectionModel().getSelectedItem();
        if (vuelo == null) {
            mostrarAlerta("Advertencia", "Selecciona un vuelo para modificar.");
            return;
        }

        vuelo.setNumero(txtNumero.getText());
        vuelo.setDestino(txtDestino.getText());
        vuelo.setFecha_salida(dpFechaSalida.getValue().atStartOfDay());
        vuelo.setFecha_llegada(dpFechaLlegada.getValue().atStartOfDay());
        vuelo.setEstado(cmbEstado.getValue());

        if (vueloDAO.actualizarVuelo(vuelo)) {
            cargarVuelos();
        } else {
            mostrarAlerta("Error", "No se pudo modificar el vuelo.");
        }
    }

    private void eliminarVuelo() {
        Vuelo vuelo = tableVuelos.getSelectionModel().getSelectedItem();
        if (vuelo == null) {
            mostrarAlerta("Advertencia", "Selecciona un vuelo para eliminar.");
            return;
        }

        if (vueloDAO.eliminarVuelo(vuelo.getId_vuelo())) {
            cargarVuelos();
        } else {
            mostrarAlerta("Error", "No se pudo eliminar el vuelo.");
        }
    }

    private void cargarFormulario(Vuelo vuelo) {
        txtNumero.setText(vuelo.getNumero());
        txtDestino.setText(vuelo.getDestino());
        dpFechaSalida.setValue(vuelo.getFecha_salida().toLocalDate());
        dpFechaLlegada.setValue(vuelo.getFecha_llegada().toLocalDate());
        cmbEstado.setValue(vuelo.getEstado());
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}