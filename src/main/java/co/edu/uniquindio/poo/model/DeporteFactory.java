package co.edu.uniquindio.poo.model;

import java.io.Serializable;

public class DeporteFactory implements EventoFactory, Serializable {
    @Override
    public Evento crearEvento() {
        return new Evento();
    }
}