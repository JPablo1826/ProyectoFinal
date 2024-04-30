package co.edu.uniquindio.poo;


import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Getter
@Setter
public class Cupon {
    private  int cuponRegistro;
    private int cuponPrimeraCompra;
    private Estado estado;
    
}
