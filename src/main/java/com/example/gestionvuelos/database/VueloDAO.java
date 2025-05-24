package com.example.gestionvuelos.database;

import com.example.gestionvuelos.models.Vuelo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VueloDAO {
    private Connection connection;

    public VueloDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Vuelo> obtenerTodosLosVuelos() {
        List<Vuelo> vuelos = new ArrayList<>();
        String query = "SELECT id_vuelo, numero, destino, fecha_salida, fecha_llegada, estado, id_aeronave, id_aeropuerto FROM vuelo";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                vuelos.add(new Vuelo(
                        rs.getInt("id_vuelo"),
                        rs.getString("numero"),
                        rs.getString("destino"),
                        rs.getTimestamp("fecha_salida").toLocalDateTime(),
                        rs.getTimestamp("fecha_llegada").toLocalDateTime(),
                        rs.getString("estado"),
                        rs.getInt("id_aeronave"),
                        rs.getInt("id_aeropuerto")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vuelos;
    }

    public boolean agregarVuelo(Vuelo vuelo) {
        String query = "INSERT INTO vuelo (numero, destino, fecha_salida, fecha_llegada, estado, id_aeronave, id_aeropuerto) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vuelo.getNumero());
            stmt.setString(2, vuelo.getDestino());
            stmt.setTimestamp(3, Timestamp.valueOf(vuelo.getFecha_salida()));
            stmt.setTimestamp(4, Timestamp.valueOf(vuelo.getFecha_llegada()));
            stmt.setString(5, vuelo.getEstado());
            stmt.setInt(6, vuelo.getId_aeronave());
            stmt.setInt(7, vuelo.getId_aeropuerto());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarVuelo(Vuelo vuelo) {
        String query = "UPDATE vuelo SET destino = ?, fecha_salida = ?, fecha_llegada = ?, estado = ?, id_aeronave = ?, id_aeropuerto = ? WHERE id_vuelo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vuelo.getDestino());
            stmt.setTimestamp(2, Timestamp.valueOf(vuelo.getFecha_salida()));
            stmt.setTimestamp(3, Timestamp.valueOf(vuelo.getFecha_llegada()));
            stmt.setString(4, vuelo.getEstado());
            stmt.setInt(5, vuelo.getId_aeronave());
            stmt.setInt(6, vuelo.getId_aeropuerto());
            stmt.setInt(7, vuelo.getId_vuelo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarVuelo(int id_vuelo) {
        String query = "DELETE FROM vuelo WHERE id_vuelo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id_vuelo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
