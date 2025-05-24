package com.example.gestionvuelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/aerolinea";
    private static final String USER = "root";
    private static final String PASSWORD = "Lamamiesjuan1";

    public static Connection getConnection() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos:");
            e.printStackTrace();
        }
        return conexion;
    }
}
