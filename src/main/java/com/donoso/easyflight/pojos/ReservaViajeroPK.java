package com.donoso.easyflight.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaViajeroPK implements Serializable {

    private String reservaId;

    private Integer viajeroId;

}
