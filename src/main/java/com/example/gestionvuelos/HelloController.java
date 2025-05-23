package com.example.gestionvuelos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class HelloController {

    @FXML
    private StackPane mainContainer;

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node newView = loader.load();
            mainContainer.getChildren().setAll(newView); // Replaces current view
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadFlightsView() {
        loadView("Flight-Managment-view.fxml");
    }

    @FXML
    public void loadReservationsView() {
        loadView("ReservationsView.fxml");
    }

    @FXML
    public void loadCustomersView() {
        loadView("CustomersView.fxml");
    }
}
