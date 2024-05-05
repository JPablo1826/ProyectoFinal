package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import lombok.Data;

@Data
public class UniEventos {
    public List<Cliente> clientes = new ArrayList<>();
    public List<Evento> eventos = new ArrayList<>();
    public List<Cupon> cupones = new ArrayList<>();
    private List<Factura> facturas = new ArrayList<>();

    public void registrarNuevoCliente(Cliente cliente) throws ObjetoExistenteException {
        if (buscarClientePorEmail(cliente.getCorreo()) != null) {
            throw new ObjetoExistenteException("El cliente ya está registrado.");
        }

        clientes.add(cliente);
    }

    public Cliente buscarClientePorEmail(String email) {
        for (Cliente cliente : clientes) {
            if (cliente.getCorreo().equals(email)) {
                return cliente;
            }
        }
        return null;
    }

    // Registrar un evento

    public void registrarNuevoEvento(Evento evento) throws ObjetoExistenteException {
        if (buscarEventoPorIdEvento(evento.getIdEvento()) == true) {
            throw new ObjetoExistenteException("El evento ya se encuentra creado");
        }

        eventos.add(evento);
    }

    public boolean buscarEventoPorIdEvento(String idEvento) {
        for (Evento evento : eventos) {
            if (evento.getIdEvento().equals(idEvento)) {
                return true;
            }
        }
        return false;
    }

    // Modificar un evento

    public void modificarEvento(String idEvento, Evento eventoModificado) throws ObjetoNoExistenteException {

        // Recorrer la lista de eventos para encontrar el evento a modificar
        for (int i = 0; i < eventos.size(); i++) {
            Evento eventoExistente = eventos.get(i);
            if (eventoExistente.getIdEvento().equals(idEvento)) {
                // Modificar los atributos del evento existente con los del evento modificado
                eventoExistente.setNombreEvento(eventoModificado.getNombreEvento());
                eventoExistente.setCiudad(eventoModificado.getCiudad());
                eventoExistente.setDescripcion(eventoModificado.getDescripcion());
                // eventoExistente.setEvento(eventoModificado.getEvento());
                eventoExistente.setImagen(eventoModificado.getImagen());
                eventoExistente.setFecha(eventoModificado.getFecha());
                eventoExistente.setDireccion(eventoModificado.getDireccion());
                eventoExistente.setLocalidades(eventoModificado.getLocalidades());

                // Retornar true para indicar que la modificación fue exitosa
                return;
            }
        }
        // Retornar false si el evento a modificar no fue encontrado
        throw new ObjetoNoExistenteException("El evento no fue encontrado");
    }

    // Eliminar evento
    public boolean eliminarEventos(String idEvento) {

        // Utilizar removeIf para eliminar el evento con el ID especificado
        return eventos.removeIf(evento -> evento.getIdEvento().equals(idEvento));

    }

    public List<Evento> listarEventos() {
        return eventos;
    }

    public Evento buscarEventoPorId(String idEvento) {
        for (Evento evento : eventos) {
            if (evento.getIdEvento().equals(idEvento)) {
                return evento; // Devuelve el evento si el ID coincide
            }
        }
        return null;
    }
    // Devuelve null si no se encuentra ningún evento con ese ID

    // Iniciar Sesion

    public Usuario iniciarSesion(String correo, String contrasena) throws inicioFallidoException {
        if (esAdministrador(correo, contrasena)) {
            return Administrador.obtenerInstancia();
        }
        for (Cliente cliente : clientes) {
            if (cliente.getCorreo().equals(correo) && cliente.getContaseña().equals(contrasena)) {
                return cliente;
            }
        }
        throw new inicioFallidoException("Contraseña o correo incorrecto");

    }

    // Método para verificar si las credenciales corresponden a un administrador
    private boolean esAdministrador(String correo, String contrasena) {
        Administrador administrador = Administrador.obtenerInstancia();
        return correo.equals(administrador.getCorreo()) && contrasena.equals(administrador.getContrasena());
    }

    public void realizarCompra(Cliente cliente, Evento evento, Cupon cupon, Localidad localidad, int cantidad)
            throws ObjetoNoExistenteException {
        Cliente encontrado = buscarClientePorEmail(cliente.getCorreo());
        if (encontrado == null)
            throw new ObjetoNoExistenteException("El cliente no fue encontrado");
        Compra compra = Compra.builder().cliente(encontrado).cupon(cupon).evento(evento).localidad(localidad)
                .idCompra(UUID.randomUUID().toString()).build();
        Factura factura = Factura.builder().compra(compra).fechaCompra(LocalDate.now())
                .codigoFactura(compra.getIdCompra()).build();
    }

    public List<Evento> calcularRecaudacion(String ciudad) {
        List<Evento> eventosFiltro = eventos.stream().filter((evento) -> evento.getCiudad().equals(ciudad)).toList();

        HashMap<Evento, Double> eventosOrdenados = new HashMap<>();
        for (Evento evento : eventosFiltro) {
            eventosOrdenados.put(evento, 0d);
        }
        for (Factura factura : facturas) {
            if (eventosOrdenados.containsKey(factura.getCompra().getEvento())) {
                eventosOrdenados.put(factura.getCompra().getEvento(),
                        eventosOrdenados.get(factura.getCompra().getEvento()) + factura.getTotal());
            }
        }
        List<Map.Entry<Evento, Double>> eventosLstOrdenados = new ArrayList<Map.Entry<Evento, Double>>(
                eventosOrdenados.entrySet());
        Collections.sort(eventosLstOrdenados, new Comparator<Map.Entry<Evento, Double>>() {
            @Override
            public int compare(Map.Entry<Evento, Double> o1, Map.Entry<Evento, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return eventosLstOrdenados.stream().map(entry -> entry.getKey()).toList();
    }
}
