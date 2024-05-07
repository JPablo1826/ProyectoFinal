package co.edu.uniquindio.poo.model;

import lombok.Data;

@Data
public class ObservadorCorreo implements Observador {
    private String correo;
    private String nombre;

    public ObservadorCorreo(String correo, String nombre) {
        this.correo = correo;
        this.nombre = nombre;
    }

    @Override
    public void actualizar(String mensaje) {
        // Implementación para enviar correo
        System.out.println("Enviando correo: " + mensaje);
    }

}
