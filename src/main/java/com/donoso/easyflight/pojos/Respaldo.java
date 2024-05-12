package com.donoso.easyflight.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Respaldo {
    private Integer id;
    private String nombre;
    private LocalDate fechaGenerada;
    private LocalDate fechaRestaurada;
}
