package com.donoso.easyflight.pojos;


import javafx.util.StringConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Extra extends StringConverter<Extra> implements Serializable {


    private Integer id;

    private String nombre;

    private String descripcion;

    private Double coste;

    private Set<ReservaExtra> reservaExtras;

    @Override
    public String toString(Extra extra) {
        return extra != null ? extra.getNombre() : "Seleccione una opción";
    }

    @Override
    public Extra fromString(String s) {
        return null;
    }
}
