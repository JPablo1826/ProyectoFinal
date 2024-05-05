package co.edu.uniquindio.poo;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import java.util.List;
import lombok.Data;
@Data
@AllArgsConstructor

public class Evento {
    private String IdEvento;
    private String NombreEvento;
    private String Ciudad;
    private String Descripcion;
    private TipoEvento tipoEvento;
    private String imagen; 
    private LocalDate Fecha;
    private String Direccion;
    private List<Localidad> localidades;

}

