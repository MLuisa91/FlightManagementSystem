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


    private String id;

    private Usuario usuario;

    private Vuelo vuelo;

    private Oferta oferta;

    private Integer numPasajeros;

    private Double total;

    private Set<ReservaExtra> reservaExtras;
    private LocalDate fechaReserva;
    Set<ReservaViajero> reservaViajeros;

    public Reserva (String id, Usuario usuario, Vuelo vuelo, Oferta oferta, LocalDate fecha, Double total){
        this.id = id;
        this.usuario = usuario;
        this.vuelo = vuelo;
        this.oferta = oferta;
        this.fechaReserva = fecha;
        this.total = total;
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
