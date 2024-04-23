package co.edu.uniquindio.poo;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor

@Data
public class Cliente extends Usuario{
    private String contaseña;
    public List<Compra> compras = new ArrayList<>();
   

    public void  realizarCompra(Compra compra){
        compras.add(compra);
    }

    public void cancelar (Compra compra){
        compras.remove(compra);
    }
   

    @Override
    public void displayUserInfo() {
        System.out.println("Cliente: " + getID() + ", nombre: " + getNombre());
    }
    

}
