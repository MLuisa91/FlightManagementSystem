package com.donoso.easyflight.pojos;


import javafx.util.StringConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reserva extends StringConverter<Reserva> implements Serializable {


    private Integer id;
    private String code;

    private Usuario usuario;

    private Vuelo vueloIda;
    private Vuelo vueloVuelta;

    private Oferta oferta;

    private Integer numPasajeros;

    private Double total;

    private Set<ReservaExtra> reservaExtras;
    private LocalDate fechaReserva;
    Set<ReservaViajero> reservaViajeros;

    /*public Reserva (Integer id, Usuario usuario, Vuelo vueloIda, Oferta oferta, LocalDate fecha, Double total){
        this.id = id;
        this.usuario = usuario;
        this.vueloIda = vueloIda;
        this.oferta = oferta;
        this.fechaReserva = fecha;
        this.total = total;
    }*/

    public Reserva(Integer id) {
        this.id = id;
    }

    @Override
    public String toString(Reserva reserva) {
        return null;
    }

    @Override
    public Reserva fromString(String s) {
        return null;
    }
}
