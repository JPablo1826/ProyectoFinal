package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import co.edu.uniquindio.poo.exceptions.ObjetoExistenteException;

@Data
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=true)
public class Cliente extends Usuario {
    private String contasena;
    public String codigo;
    public final List<Compra> compras = new ArrayList<>();
    private EstrategiaDescuento estrategiaDescuento;

    public Cliente(String ID, String nombre, String telefono, String correo, String contasena) {
        super(ID, nombre, telefono, correo);
        this.contasena = contasena;
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


    public boolean estaVericado() {
        return codigo==null;
    }

}
