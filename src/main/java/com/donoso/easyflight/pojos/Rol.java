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
public class Rol implements Serializable {

    private Integer id;

    private String nombre;

    private Set<UsuarioRol> usuarioRol;

    public Rol(Integer id){
        this.id = id;
    }

}
