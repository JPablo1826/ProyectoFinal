package co.edu.uniquindio.poo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Administrador extends Usuario {
    private final String correo = "admin@unieventos.com";
    private final String contrasena = "admin123";
}
