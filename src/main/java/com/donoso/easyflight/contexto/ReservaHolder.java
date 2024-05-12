package com.donoso.easyflight.contexto;

import com.donoso.easyflight.pojos.Reserva;

public final class ReservaHolder {

    private Reserva reserva;
    private Object latestScreen;

    private static final ReservaHolder INSTANCE = new ReservaHolder();

    public static ReservaHolder getInstance() {
        return INSTANCE;
    }

    public Reserva getReserva() {
        return this.reserva;
    }

    public void setUsuario(Reserva reserva) {
        this.reserva = reserva;
    }

    public void setLatestScreen(Object screen) {
        this.latestScreen = screen;
    }

    public Object getLatestScreen() {
        return this.latestScreen;
    }
}
