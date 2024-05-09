package co.edu.uniquindio.poo.model;

import java.io.Serializable;

public interface EstrategiaDescuento extends Serializable {
    double aplicarDescuento(double precio);
}