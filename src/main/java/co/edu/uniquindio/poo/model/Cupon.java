package co.edu.uniquindio.poo.model;


import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import java.io.Serializable;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Getter
@Builder
@Setter
public class Cupon implements Serializable {
    private String codigo;
    private boolean cuponRegistro;
    private Estado estado;
    private int porcentaje;
    
}
