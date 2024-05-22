package co.edu.uniquindio.poo.model;

import java.io.Serializable;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@AllArgsConstructor
public class Localidad implements Serializable {
    private double precio;
    private TipoLocalidad tipo;
    private int capacidad;
    
}
