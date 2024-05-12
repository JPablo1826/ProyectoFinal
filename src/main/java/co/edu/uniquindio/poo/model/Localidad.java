package co.edu.uniquindio.poo.model;

import java.io.Serializable;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Localidad implements Serializable {
    private double precio;
    private TipoLocalidad tipo;
    private int capacidad;
    
}
