package co.edu.uniquindio.poo.model;

import java.io.Serializable;

public interface Observador extends Serializable {
    void actualizar(String mensaje);
}
