package co.edu.uniquindio.poo.model;

import java.io.Serializable;

public class ConciertoFactory implements EventoFactory,Serializable {

    
    @Override
    public Evento crearEvento() {
        return new Evento();
    }
}