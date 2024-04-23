package co.edu.uniquindio.poo;
import lombok.*;

@Data


public abstract class Usuario {
    public String ID;
    public String nombre;
    public String telefono;
    public String correo;
    


    public abstract void displayUserInfo();

}


