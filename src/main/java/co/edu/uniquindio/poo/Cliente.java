package co.edu.uniquindio.poo;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@Setter
public class Cliente extends Usuario{
    private String contaseña;
    private List<Compra> listaCompra = new ArrayList<>();
    private int cantidadEntradas;
    @Override
    public void displayUserInfo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayUserInfo'");
    }
    public void realizarCompra(){

    }
    public void cancelarCompra(){
        
    }

    

}
