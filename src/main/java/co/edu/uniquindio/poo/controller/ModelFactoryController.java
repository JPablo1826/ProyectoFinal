package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.UniEventos;
import co.edu.uniquindio.poo.utils.Serializacion;

public class ModelFactoryController {
    private static ModelFactoryController instance;

    public static ModelFactoryController getInstance() {
        if (instance == null) {
            instance = new ModelFactoryController();
        }
        return instance;
    }

    public void registrarCliente(String cedula, String nombre, String email, String telefono, String contrasena) throws Exception {
        UniEventos unieventos = Serializacion.obternerDatos();
        unieventos.registrarNuevoCliente(Cliente.builder().contaseña(contrasena).nombre(nombre).correo(email).telefono(telefono).ID(cedula).build());
        Serializacion.guardarDatos(unieventos);
    }
}

