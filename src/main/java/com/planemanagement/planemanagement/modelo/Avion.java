package com.planemanagement.planemanagement.modelo;

public class Avion {

    private String id;
    private String modelo;
    private Integer pasajeros;

    public Avion() {
    }

    public Avion(String id, String modelo, Integer pasajeros) {
        this.id = id;
        this.modelo = modelo;
        this.pasajeros = pasajeros;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(Integer pasajeros) {
        this.pasajeros = pasajeros;
    }

}
