package co.edu.uniquindio.poo.model;

public class TeatroFactory implements EventoFactory {
    @Override
    public Evento crearEvento() {
        return new Evento();
    }
}