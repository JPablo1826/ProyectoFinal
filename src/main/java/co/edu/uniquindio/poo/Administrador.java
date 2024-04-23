package co.edu.uniquindio.poo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Administrador extends Usuario {
    private final String correo = "admin@unieventos.com";
    private final String contrasena = "admin123";

    private static Administrador instancia;

    
    public static Administrador obtenerInstancia() {
        
        if (instancia == null) {
            instancia = new Administrador();
        }
        
        return instancia;
    }

    @Override
    public void displayUserInfo() {
        System.out.println("Cliente: " + getCorreo() + ", nombre: " + getContrasena());
        
    }
}


