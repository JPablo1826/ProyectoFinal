package co.edu.uniquindio.poo.model;

public class DescuentoPrimerCompra implements EstrategiaDescuento{
    private static final double PORCENTAJE_DESCUENTO = 0.10; // 10%

    @Override
    public double aplicarDescuento(double precio) {
        return precio * (1 - PORCENTAJE_DESCUENTO);
    }
}