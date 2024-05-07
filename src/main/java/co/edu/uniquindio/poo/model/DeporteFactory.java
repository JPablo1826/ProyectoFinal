package co.edu.uniquindio.poo.model;

public class DeporteFactory implements EventoFactory {
    @Override
    public Evento crearEvento() {
        return new Evento();
    }
}