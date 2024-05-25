package com.donoso.easyflight.contexto;

import com.donoso.easyflight.pojos.Reserva;

public final class ReservaHolder {

    private Reserva reserva;

    private static final ReservaHolder INSTANCE = new ReservaHolder();

    public static ReservaHolder getInstance() {
        return INSTANCE;
    }

    public Reserva getReserva() {
        return this.reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }


}
