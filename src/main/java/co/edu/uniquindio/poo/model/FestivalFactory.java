package co.edu.uniquindio.poo.model;


public class FestivalFactory  implements EventoFactory {
    @Override
    public Evento crearEvento() {
        return new Evento();
    }
    
}
