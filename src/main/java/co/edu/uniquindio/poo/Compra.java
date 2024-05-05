package co.edu.uniquindio.poo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@AllArgsConstructor
@Getter
@Builder
@Setter
public class Compra {
    private String idCompra;
    private Cliente cliente;
    private Evento evento;
    private Localidad localidad;
    private Cupon cupon;

}
