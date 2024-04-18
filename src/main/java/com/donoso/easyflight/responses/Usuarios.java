package com.donoso.easyflight.responses;


public class Usuarios {
    Integer id;
    String nombre;
    String apellidos;
    Pais pais;

    public Usuarios() {
        super();
    }

    public Usuarios(Integer id, String nombre, String apellidos, Pais pais) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.pais = pais;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
