package co.edu.uniquindio.poo.model;
import java.io.Serializable;

import lombok.*;

@Data
@AllArgsConstructor

public abstract class Usuario implements Serializable{
    public String ID;
    public String nombre;
    public String telefono;
    public String correo;
    


    public abstract void displayUserInfo();

}


