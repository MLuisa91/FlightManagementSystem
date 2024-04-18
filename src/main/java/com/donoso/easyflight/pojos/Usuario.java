package com.donoso.easyflight.pojos;


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
public class Usuario implements Serializable {

    private String idDni;
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
}
