package com.donoso.easyflight.pojos;


import javafx.util.StringConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vuelo extends StringConverter<Vuelo> implements Serializable {

    private String id;

    private Aeropuerto origen;

    private Aeropuerto destino;

    private LocalDate fechaSalida;

    private LocalTime horaSalida;

    private LocalTime horaLlegada;

    private Avion avion;
    private Double precio;

    public Vuelo(String id){
        this.id = id;
    }

    @Override
    public String toString(Vuelo vuelo) {
        return vuelo != null ? vuelo.getId() : "Seleccione una opción";
    }

    @Override
    public Vuelo fromString(String s) {
        return null;
    }
}
