package com.donoso.easyflight.pojos;


import javafx.util.StringConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario extends StringConverter<Usuario> implements Serializable {

    private Integer id;
    private String dni;
    private String nombre;
    private String apellidos;
    private String user;
    private String password;
    private String email;
    private String telefono;
    private Pais pais;

    private Set<UsuarioRol> usuarioRol;

    public Boolean getIsAdministrador(){
        return Boolean.TRUE;
    }

    public Usuario (Integer id){
        this.id = id;
    }

    @Override
    public String toString(Usuario usuario) {
        return null;
    }

    @Override
    public Usuario fromString(String s) {
        return null;
    }
}
