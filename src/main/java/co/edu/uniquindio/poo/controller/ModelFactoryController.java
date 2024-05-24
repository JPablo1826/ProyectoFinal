package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.exceptions.NoVerificadoException;
import co.edu.uniquindio.poo.exceptions.ObjetoExistenteException;
import co.edu.uniquindio.poo.exceptions.ObjetoNoExistenteException;
import co.edu.uniquindio.poo.exceptions.inicioFallidoException;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Compra;
import co.edu.uniquindio.poo.model.Cupon;
import co.edu.uniquindio.poo.model.Estado;
import co.edu.uniquindio.poo.model.Evento;
import co.edu.uniquindio.poo.model.Localidad;
import co.edu.uniquindio.poo.model.Resena;
import co.edu.uniquindio.poo.model.TipoEvento;
import co.edu.uniquindio.poo.model.TipoLocalidad;
import co.edu.uniquindio.poo.model.UniEventos;
import co.edu.uniquindio.poo.model.Usuario;
import co.edu.uniquindio.poo.utils.Serializacion;

import java.time.LocalDate;



import java.util.ArrayList;
import java.util.List;

public class ModelFactoryController { //la que comunica la logica con la vista 
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

    public List<Compra> listarComprasClientes(String idClient) {
        UniEventos uniEventos=Serializacion.obternerDatos();
        return uniEventos.listarComprasClientes(idClient);
    }

    public double obtenerPorcentaje(Evento newValue, TipoLocalidad tipo) {
        UniEventos unieventos = Serializacion.obternerDatos();
        return unieventos.obtenerPorcentajeLocalidad(tipo, newValue);
    }

    public void crearEvento(String nombre, String descripcion, String direccion, double precioGeneral, double precioVIP, int capacidadGeneral,int capacidadVIP, LocalDate fecha, String tipo, String ciudad ) throws Exception {
        UniEventos unieventos = Serializacion.obternerDatos();
        Localidad localidadGeneral =  Localidad.builder().precio(precioGeneral).tipo(TipoLocalidad.GENERAL).capacidad(capacidadGeneral).build();
        Localidad  localidadVIP = Localidad.builder().precio(precioVIP).tipo(TipoLocalidad.VIP).capacidad(capacidadVIP).build();
        Evento e = Evento.builder().NombreEvento(nombre).Descripcion(descripcion).Direccion(direccion).localidadGeneral(localidadGeneral).localidadVIP(localidadVIP).fecha(fecha).tipoEvento(TipoEvento.getTipoEvento(tipo)).ciudad(ciudad).build();
        unieventos.crearEvento(e);
        Serializacion.guardarDatos(unieventos);
        
    }
    public void modificarEvento(String idEvento, String nombre, String descripcion, String direccion, double precioGeneral, double precioVIP, int capacidadGeneral,int capacidadVIP, LocalDate fecha, String tipo, String ciudad ) throws ObjetoNoExistenteException {
        UniEventos unieventos = Serializacion.obternerDatos();
        Localidad localidadGeneral =  Localidad.builder().precio(precioGeneral).tipo(TipoLocalidad.GENERAL).capacidad(capacidadGeneral).build();
        Localidad  localidadVIP = Localidad.builder().precio(precioVIP).tipo(TipoLocalidad.VIP).capacidad(capacidadVIP).build();
        Evento evento = Evento.builder().NombreEvento(nombre).Descripcion(descripcion).Direccion(direccion).localidadGeneral(localidadGeneral).localidadVIP(localidadVIP).fecha(fecha).tipoEvento(TipoEvento.getTipoEvento(tipo)).ciudad(ciudad).build();
        unieventos.modificarEvento(idEvento, evento);
        Serializacion.guardarDatos(unieventos);
    }
    public double obtenerTotalGanadoEvento(Evento evento) {
        UniEventos unieventos = Serializacion.obternerDatos();
        return unieventos.obtenerTotalGanadoEvento(evento);
    }


     public boolean eliminarEventos(String idEvento) {
        UniEventos unieventos = Serializacion.obternerDatos();
        boolean sePudoEliminar = unieventos.eliminarEventos(idEvento);
        Serializacion.guardarDatos(unieventos);
        return sePudoEliminar;
     }

	public List<Evento> calcularRecaudacion(String newValue) {
        UniEventos unieventos = Serializacion.obternerDatos();
        return unieventos.calcularRecaudacion(newValue);
	}

    public void crearCupon(String codigo, String porcentaje) throws ObjetoExistenteException {
        Cupon cupon = Cupon.builder().codigo(codigo).cuponRegistro(false).porcentaje(Integer.parseInt(porcentaje)).estado(Estado.ACTIVO).build();
        UniEventos unieventos = Serializacion.obternerDatos();
        unieventos.crearCupon(cupon);
        Serializacion.guardarDatos(unieventos);
    }

    public void realizarCompra(Cliente cliente, String idEvento, String codigoCupon, TipoLocalidad tipo, String cantidad)
            throws Exception {
        UniEventos unieventos = Serializacion.obternerDatos();
        unieventos.realizarCompra(cliente, idEvento, codigoCupon, tipo, Integer.parseInt(cantidad));
        Serializacion.guardarDatos(unieventos);
    }

    public void guardarResena(Cliente cliente, Integer value, String text) {
        cliente.setResena(new Resena(text, value));
        UniEventos unieventos = Serializacion.obternerDatos();
        unieventos.actualizarCliente(cliente);
        Serializacion.guardarDatos(unieventos);
    }



}
