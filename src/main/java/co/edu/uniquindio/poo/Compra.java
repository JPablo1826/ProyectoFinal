package co.edu.uniquindio.poo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class Compra {
    private String idCompra;
    private Cliente cliente;
    private Evento evento;
    private Localidad localidad;
    private Cupon cupon;
    private Factura factura;


    

}
