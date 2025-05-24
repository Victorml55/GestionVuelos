package com.example.gestionvuelos.models;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class Vuelo {
    private IntegerProperty id_vuelo;
    private StringProperty numero;
    private StringProperty destino;
    private ObjectProperty<LocalDateTime> fecha_salida;
    private ObjectProperty<LocalDateTime> fecha_llegada;
    private StringProperty estado;
    private IntegerProperty id_aeronave;
    private IntegerProperty id_aeropuerto;

    public Vuelo() {
        this.id_vuelo = new SimpleIntegerProperty();
        this.numero = new SimpleStringProperty();
        this.destino = new SimpleStringProperty();
        this.fecha_salida = new SimpleObjectProperty<>();
        this.fecha_llegada = new SimpleObjectProperty<>();
        this.estado = new SimpleStringProperty();
        this.id_aeronave = new SimpleIntegerProperty();
        this.id_aeropuerto = new SimpleIntegerProperty();
    }

    public Vuelo(int id_vuelo, String numero, String destino, LocalDateTime fecha_salida,
                 LocalDateTime fecha_llegada, String estado, int id_aeronave, int id_aeropuerto) {
        this.id_vuelo = new SimpleIntegerProperty(id_vuelo);
        this.numero = new SimpleStringProperty(numero);
        this.destino = new SimpleStringProperty(destino);
        this.fecha_salida = new SimpleObjectProperty<>(fecha_salida);
        this.fecha_llegada = new SimpleObjectProperty<>(fecha_llegada);
        this.estado = new SimpleStringProperty(estado);
        this.id_aeronave = new SimpleIntegerProperty(id_aeronave);
        this.id_aeropuerto = new SimpleIntegerProperty(id_aeropuerto);
    }

    // Getters y Setters usando Property
    public int getId_vuelo() { return id_vuelo.get(); }
    public void setId_vuelo(int id_vuelo) { this.id_vuelo.set(id_vuelo); }
    public IntegerProperty id_vueloProperty() { return id_vuelo; }

    public String getNumero() { return numero.get(); }
    public void setNumero(String numero) { this.numero.set(numero); }
    public StringProperty numeroProperty() { return numero; }

    public String getDestino() { return destino.get(); }
    public void setDestino(String destino) { this.destino.set(destino); }
    public StringProperty destinoProperty() { return destino; }

    public LocalDateTime getFecha_salida() { return fecha_salida.get(); }
    public void setFecha_salida(LocalDateTime fecha_salida) { this.fecha_salida.set(fecha_salida); }
    public ObjectProperty<LocalDateTime> fecha_salidaProperty() { return fecha_salida; }

    public LocalDateTime getFecha_llegada() { return fecha_llegada.get(); }
    public void setFecha_llegada(LocalDateTime fecha_llegada) { this.fecha_llegada.set(fecha_llegada); }
    public ObjectProperty<LocalDateTime> fecha_llegadaProperty() { return fecha_llegada; }

    public String getEstado() { return estado.get(); }
    public void setEstado(String estado) { this.estado.set(estado); }
    public StringProperty estadoProperty() { return estado; }

    public int getId_aeronave() { return id_aeronave.get(); }
    public void setId_aeronave(int id_aeronave) { this.id_aeronave.set(id_aeronave); }
    public IntegerProperty id_aeronaveProperty() { return id_aeronave; }

    public int getId_aeropuerto() { return id_aeropuerto.get(); }
    public void setId_aeropuerto(int id_aeropuerto) { this.id_aeropuerto.set(id_aeropuerto); }
    public IntegerProperty id_aeropuertoProperty() { return id_aeropuerto; }

    @Override
    public String toString() {
        return "Vuelo{" +
                "id_vuelo=" + getId_vuelo() +
                ", numero='" + getNumero() + '\'' +
                ", destino='" + getDestino() + '\'' +
                ", fecha_salida=" + getFecha_salida() +
                ", fecha_llegada=" + getFecha_llegada() +
                ", estado='" + getEstado() + '\'' +
                ", id_aeronave=" + getId_aeronave() +
                ", id_aeropuerto=" + getId_aeropuerto() +
                '}';
    }
}