package co.edu.uniquindio.poo.model;

public class ObservadorCorreo implements Observador{
    @Override
    public void actualizar(String mensaje) {
        // Implementación para enviar correo
        System.out.println("Enviando correo: " + mensaje);
    }

    
}
