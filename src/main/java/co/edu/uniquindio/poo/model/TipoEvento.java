package co.edu.uniquindio.poo.model;

import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TipoEvento {
    CONCIERTO("Concierto"),
    TEATRO("Teatro"),
    DEPORTE("Deporte"),
    FESTIVAL("Festival");

    @Getter
    private String nombre;

    public static String[] stringValues() {  
        TipoEvento[] tipos = TipoEvento.values();
        String[] nombres = new String[tipos.length];
        for (int i = 0; i < tipos.length; i++) {
            nombres[i] = tipos[i].nombre;
        }
        return nombres;
    }
    public static TipoEvento getTipoEvento(String nombre) {
        for (TipoEvento tipoEvento : TipoEvento.values()) {
            if (tipoEvento.getNombre().equals(nombre)) {
                return tipoEvento;
            }
        }
        return null;

    }

    
}