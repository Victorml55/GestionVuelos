module com.example.gestionvuelos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gestionvuelos to javafx.fxml;
    exports com.example.gestionvuelos;
}