package com.donoso.easyflight.pojos;


import javafx.util.StringConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Avion extends StringConverter<Avion> implements Serializable {

    private String id;

    private String modelo;

    private Integer pasajeros;

    @Override
    public String toString(Avion avion) {
        return avion != null ? avion.getId() : "Seleccione una opción";
    }

    @Override
    public Avion fromString(String s) {
        return null;
    }
}
