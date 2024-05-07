package co.edu.uniquindio.poo.model;

import java.io.Serializable;

public interface EventoFactory extends Serializable {

    Evento crearEvento();
}