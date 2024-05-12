package com.donoso.easyflight.pojos;


import javafx.util.StringConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Oferta extends StringConverter<Vuelo> implements Serializable {

    private Integer id;

    private String nombre;

    private String descripcion;

    private Double descuento;

    private LocalDate fechaInicio;

    private LocalDate fechaFinal;

    private Vuelo vuelo;

    public Oferta (Integer id, String nombre, String descripcion){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    @Override
    public String toString(Vuelo vuelo) {
        return null;
    }

    @Override
    public Vuelo fromString(String s) {
        return null;
    }
}
