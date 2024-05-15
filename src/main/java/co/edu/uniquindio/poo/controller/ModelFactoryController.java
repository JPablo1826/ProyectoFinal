package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.exceptions.ObjetoExistenteException;
import co.edu.uniquindio.poo.exceptions.ObjetoNoExistenteException;
import co.edu.uniquindio.poo.exceptions.inicioFallidoException;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Compra;
import co.edu.uniquindio.poo.model.Evento;
import co.edu.uniquindio.poo.model.Localidad;
import co.edu.uniquindio.poo.model.NoVerificadoException;
import co.edu.uniquindio.poo.model.TipoEvento;
import co.edu.uniquindio.poo.model.TipoLocalidad;
import co.edu.uniquindio.poo.model.UniEventos;
import co.edu.uniquindio.poo.model.Usuario;
import co.edu.uniquindio.poo.utils.Serializacion;
import javafx.util.Callback;
import java.time.LocalDate;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModelFactoryController {
    private static ModelFactoryController instance;

    public static ModelFactoryController getInstance() {
        if (instance == null) {
            instance = new ModelFactoryController();
        }
        return instance;
    }

    public void registrarCliente(String cedula, String nombre, String email, String telefono, String contrasena)
            throws Exception {
        UniEventos unieventos = Serializacion.obternerDatos();

        Cliente c = new Cliente(cedula, nombre, telefono, email, contrasena);
        unieventos.registrarNuevoCliente(c);
        Serializacion.guardarDatos(unieventos);
    }

    public Usuario iniciarSesion(String usuario, String contrasena)
            throws inicioFallidoException, NoVerificadoException {
        UniEventos unieventos = Serializacion.obternerDatos();
        return unieventos.iniciarSesion(usuario, contrasena);
    }

    public void verificarUsuario(String usuario, String codigo) throws ObjetoNoExistenteException {
        UniEventos unieventos = Serializacion.obternerDatos();
        unieventos.verificarUsuario(usuario, codigo);
        Serializacion.guardarDatos(unieventos);

    }

    public ArrayList<String> obtenerCiudades() {
        UniEventos unieventos = Serializacion.obternerDatos();
        return (ArrayList<String>) unieventos.obtenerCiudades();
    }

    public ArrayList<Compra> obtenerCompra(Cliente cliente) {
        UniEventos unieventos = Serializacion.obternerDatos();
        return (ArrayList<Compra>) unieventos.obtenerCompras(cliente);
    }

    public List<Evento> listarEventos() {
        UniEventos unieventos = Serializacion.obternerDatos();
        return unieventos.listarEventos();
    }

    public List<Evento> listarEventosCiudad(String ciudad) {
        UniEventos unieventos = Serializacion.obternerDatos();
        return unieventos.listarEventosCiudad(ciudad);
    }

    public double obtenerPorcentajeVIP(Evento newValue) {
        UniEventos unieventos = Serializacion.obternerDatos();
        return unieventos.obtenerPorcentajeVIP(newValue);
    }

    public double obtenerPorcentajeGeneral(Evento newValue) {
        UniEventos unieventos = Serializacion.obternerDatos();
        return unieventos.obtenerPorcentajeGeneral(newValue);
    }

    public void crearEvento(String nombre, String descripcion, String direccion, double precioGeneral, double precioVIP, int capacidadGeneral,int capacidadVIP, LocalDate fecha, TipoEvento tipo, String ciudad ) throws ObjetoExistenteException {
        UniEventos unieventos = Serializacion.obternerDatos();
        Localidad localidadGeneral =  Localidad.builder().precio(precioGeneral).tipo(TipoLocalidad.GENERAL).capacidad(capacidadGeneral).build();
        Localidad  localidadVIP = Localidad.builder().precio(precioVIP).tipo(TipoLocalidad.VIP).capacidad(capacidadVIP).build();
        Evento e = Evento.builder().NombreEvento(nombre).Descripcion(descripcion).Direccion(direccion).localidadGeneral(localidadGeneral).localidadVIP(localidadVIP).fecha(fecha).tipoEvento(tipo).ciudad(ciudad).build();
        unieventos.crearEvento(e);
        Serializacion.guardarDatos(unieventos);
        
    }

 
}
