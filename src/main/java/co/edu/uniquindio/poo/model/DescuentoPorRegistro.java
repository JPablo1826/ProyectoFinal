package co.edu.uniquindio.poo.model;
public class DescuentoPorRegistro implements EstrategiaDescuento {
    private static final double PORCENTAJE_DESCUENTO = 0.15; // 15%

    @Override
    public double aplicarDescuento(double precio) {
        return precio * (1 - PORCENTAJE_DESCUENTO);
    }
}