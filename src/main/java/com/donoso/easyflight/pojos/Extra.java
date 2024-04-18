package com.donoso.easyflight.pojos;


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
public class Extra implements Serializable {


    private Integer id;

    private String nombre;

    private String descripcion;

    private Double coste;

    private Set<ReservaExtra> reservaExtras;

}
