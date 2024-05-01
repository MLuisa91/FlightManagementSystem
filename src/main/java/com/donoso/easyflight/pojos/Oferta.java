package com.donoso.easyflight.pojos;


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
public class Oferta implements Serializable {

    private Integer id;

    private String nombre;

    private String descripcion;

    private Double descuento;

    private LocalDate fechaInicio;

    private LocalDate fechaFinal;

    public Oferta (Integer id, String nombre, String descripcion){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }


}
