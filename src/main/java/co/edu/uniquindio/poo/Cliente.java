package co.edu.uniquindio.poo;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor

@Data
public class Cliente extends Usuario{
    private String contaseña;
   

   

    @Override
    public void displayUserInfo() {
        System.out.println("Cliente: " + getID() + ", nombre: " + getNombre());
    }
    

}
