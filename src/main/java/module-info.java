module com.example.gestionvuelos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.gestionvuelos to javafx.fxml;
    exports com.example.gestionvuelos;
    exports com.example.gestionvuelos.models;
    exports com.example.gestionvuelos.database;
}