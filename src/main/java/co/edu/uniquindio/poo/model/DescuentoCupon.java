package co.edu.uniquindio.poo.model;

import java.io.Serializable;

public class DescuentoCupon implements EstrategiaDescuento,Serializable {
    private final double PORCENTAJE_DESCUENTO;

    public DescuentoCupon(Cupon cuponGeneral) {
        PORCENTAJE_DESCUENTO = cuponGeneral.getPorcentaje() / 100.0;
    }

    @Override
    public double aplicarDescuento(double precio) {
        return precio * (1 - PORCENTAJE_DESCUENTO);
    }
}