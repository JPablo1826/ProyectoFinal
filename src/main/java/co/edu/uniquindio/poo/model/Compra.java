package co.edu.uniquindio.poo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@AllArgsConstructor
@Getter
@Builder
@Setter
public class Compra implements Serializable {
    private String idCompra;
    private Cliente cliente;
    private Evento evento;
    private int cantidad;
    private TipoLocalidad localidad;
    private Cupon cupon;

    public boolean verificarCapacidadEvento() {
        if (localidad == TipoLocalidad.GENERAL)
            return evento.getLocalidadGeneral().getCapacidad() >= cantidad;
        return evento.getLocalidadVIP().getCapacidad() >= cantidad;
    }

}
