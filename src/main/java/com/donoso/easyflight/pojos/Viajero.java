package com.donoso.easyflight.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Viajero implements Serializable {

    private Integer id;
    private String dni;

    private String nombre;

    private String apellidos;

    private boolean adulto;

}
