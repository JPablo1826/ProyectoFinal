package co.edu.uniquindio.poo.model;
import java.io.Serializable;

import lombok.*;

@Data

public abstract class Usuario implements Serializable{
    public String ID;
    public String nombre;
    public String telefono;
    public String correo;
    


    public Usuario(String iD, String nombre, String telefono, String correo) {
        ID = iD;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }



    public abstract void displayUserInfo();

}


