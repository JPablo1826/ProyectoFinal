package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.exceptions.ObjetoNoExistenteException;
import co.edu.uniquindio.poo.exceptions.inicioFallidoException;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.NoVerificadoException;
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
        Cliente c = Cliente.builder().contasena(contrasena).nombre(nombre).correo(email).telefono(telefono).ID(cedula).build();
        System.out.println(c);
        unieventos.registrarNuevoCliente(c);
        Serializacion.guardarDatos(unieventos);
    }

    public void iniciarSesion(String usuario, String contrasena) throws inicioFallidoException, NoVerificadoException {
        UniEventos unieventos = Serializacion.obternerDatos();
        unieventos.iniciarSesion(usuario, contrasena);
        Serializacion.guardarDatos(unieventos);
    }

    public void verificarUsuario(String usuario, String codigo) throws ObjetoNoExistenteException  {
        UniEventos unieventos = Serializacion.obternerDatos();
        unieventos.verificarUsuario(usuario, codigo);
        Serializacion.guardarDatos(unieventos);

    }
    }

