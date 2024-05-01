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
public class Pais extends StringConverter<Pais> implements Serializable {

    private Integer id;

    private String nombre;

    @Override
    public String toString(Pais pais) {
        return pais != null ? pais.getNombre() : "Seleccione una opción";
    }

    @Override
    public Pais fromString(String s) {
        return null;
    }
}
