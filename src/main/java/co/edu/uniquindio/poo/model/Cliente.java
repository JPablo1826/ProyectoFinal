package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import co.edu.uniquindio.poo.exceptions.ObjetoExistenteException;

@Data
@EqualsAndHashCode(callSuper=true)
public class Cliente extends Usuario {
    public String correo;
    public String telefono;
    private String contaseña;
    public final List<Compra> compras = new ArrayList<>();

    @Builder
    public Cliente(String ID, String nombre, String telefono, String correo, String contaseña) {
        super(ID, nombre, telefono, correo);
        this.contaseña = contaseña;
    }


    public void realizarCompra(Compra compra) {
        compras.add(compra);
    }

    public void cancelar(Compra compra) {
        compras.remove(compra);
    }

    @Override
    public void displayUserInfo() {
        System.out.println("Cliente: " + getID() + ", nombre: " + getNombre());
    }

    public void agregarCompra(Compra compra) throws ObjetoExistenteException {
        for (Compra c : compras) {
            if (c.getIdCompra().equals(compra.getIdCompra())) {
                throw new ObjetoExistenteException("ya existe una compra con ese prducto");
            }
        }
        compras.add(compra);
    }

}
