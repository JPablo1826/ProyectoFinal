package co.edu.uniquindio.poo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Resena implements Serializable{
    private String descipcion;
    private int puntaje;

    
}
