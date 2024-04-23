package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.List;

public class UniEventos {
    public List<Cliente> clientes = new ArrayList<>();

    public void registrarNuevoCliente(Cliente clientes) {
        if (buscarClientePorEmail(clientes.getCorreo()) == true) {
            System.out.println("El cliente ya está registrado.");
            return;
        }

        clientes.add(clientes);
        System.out.println("Cliente registrado correctamente.");
    }

    public boolean buscarClientePorEmail(String email) {
        for (Clientes clientes : clientes) {
            if (clientes.getCorreo().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public List<Clientes> getClientes() {
        return clientes;
    }

    public List<Evento> eventos = new ArrayList<>();

    public void registrarNuevoEvento(Evento evento) {
        if (buscarClientePorEmail(evento.getIdEvento()) == true) {
            System.out.println("El evento ya se encuentra creado");
            return;
        }

        eventos.add(evento);
        System.out.println("Cliente registrado correctamente.");
    }

    public boolean buscarEventoPorIdEvento(String idEvento) {
        for (Evento evento : eventos) {
            if (evento.getIdEvento().equals(idEvento)) {
                return true;
            }
        }
        return false;
    }

    public List<Evento> getIdEventos() {
        return eventos;
    }

    public boolean modificarEvento(String idEvento, Evento eventoModificado) {

        // Recorrer la lista de eventos para encontrar el evento a modificar
        for (int i = 0; i < eventos.size(); i++) {
            Evento eventoExistente = eventos.get(i);
            if (eventoExistente.getIdEvento().equals(idEvento)) {
                // Modificar los atributos del evento existente con los del evento modificado
                eventoExistente.setNombreEvento(eventoModificado.getNombreEvento());
                eventoExistente.setCiudad(eventoModificado.getCiudad());
                eventoExistente.setDescripcion(eventoModificado.getDescripcion());
                eventoExistente.setEvento(eventoModificado.getEvento());
                eventoExistente.setImagen(eventoModificado.getImagen());
                eventoExistente.setFecha(eventoModificado.getFecha());
                eventoExistente.setDireccion(eventoModificado.getDireccion());
                eventoExistente.setLocalidades(eventoModificado.getLocalidades());

                // Retornar true para indicar que la modificación fue exitosa
                return true;
            }
        }
        // Retornar false si el evento a modificar no fue encontrado
        return false;
    }

    public boolean eliminarEventos(String idEvento) {

        // Utilizar removeIf para eliminar el evento con el ID especificado
        return eventos.removeIf(evento -> evento.getIdEvento().equals(idEvento));

    }

    public void listarEventos() {

    }

    /*public List<Evento> buscarEventos() {

    public Evento buscarEventoPorId(String idEvento) {
        for (Evento evento : eventos) {
            if (evento.getIdEvento().equals(idEvento)) {
                return evento; // Devuelve el evento si el ID coincide
            }
        }
        return null; // Devuelve null si no se encuentra ningún evento con ese ID

    }*/
}
