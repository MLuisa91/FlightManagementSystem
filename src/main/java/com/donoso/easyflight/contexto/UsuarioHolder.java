package com.donoso.easyflight.contexto;

import com.donoso.easyflight.pojos.Usuario;

public final class UsuarioHolder {

    private Usuario usuario;
    private Object latestScreen;

    private static final UsuarioHolder INSTANCE = new UsuarioHolder();

    public static UsuarioHolder getInstance() {
        return INSTANCE;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setLatestScreen(Object screen) {
        this.latestScreen = screen;
    }

    public Object getLatestScreen() {
        return this.latestScreen;
    }
}
