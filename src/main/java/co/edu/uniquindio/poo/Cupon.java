package co.edu.uniquindio.poo;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Getter
@Setter
public class Cupon {
    private  int cuponRegistro;
    private int cuponPrimeraCompra;
    private Estado estado;
    
}
