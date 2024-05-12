package co.edu.uniquindio.poo.model;

public class ConciertoFactory implements EventoFactory {

    
    @Override
    public Evento crearEvento() {
        return new Evento();
    }
}