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
public class Aeropuerto extends StringConverter<Aeropuerto> implements Serializable {

    private Integer id;

    private String nombre;

    @Override
    public String toString(Aeropuerto aeropuerto) {
        return null;
    }

    @Override
    public Aeropuerto fromString(String s) {
        return null;
    }
}
