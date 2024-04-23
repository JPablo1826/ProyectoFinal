package co.edu.uniquindio.poo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Administrador extends Usuario {
    private final String correo = "admin@unieventos.com";
    private final String contrasena = "admin123";

    private static Administrador instancia;

    // Método estático para obtener la instancia única de Administrador
    public static Administrador obtenerInstancia() {
        // Si la instancia aún no ha sido creada, la creamos
        if (instancia == null) {
            instancia = new Administrador();
        }
        // Devolvemos la instancia existente
        return instancia;
    }

    @Override
    public void displayUserInfo() {
        System.out.println("Cliente: " + getCorreo() + ", nombre: " + getContrasena());
        
    }
}


