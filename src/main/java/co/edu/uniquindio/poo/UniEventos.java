package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UniEventos {
    public List<Cliente> clientes = new ArrayList<>();
    public List<Evento> eventos = new ArrayList<>();

    public void registrarNuevoCliente(Cliente cliente) {
        if (buscarClientePorEmail(cliente.getCorreo()) == true) {
            System.out.println("El cliente ya está registrado.");
            return;
        }

        clientes.add(cliente);
        System.out.println("Cliente registrado correctamente.");
    }

    public boolean buscarClientePorEmail(String email) {
        for (Cliente cliente : clientes) {
            if (cliente.getCorreo().equals(email)) {
                return true;
            }
        }
        return false;
    }

    // Registrar un evento

    public void registrarNuevoEvento(Evento evento) {
        if (buscarEventoPorIdEvento(evento.getIdEvento()) == true) {
            System.out.println("El evento ya se encuentra creado");
            return;
        }

        eventos.add(evento);
        System.out.println("Evento registrado correctamente.");
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

    public boolean modificarEvento(String idEvento, Evento eventoModificado) {

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
                return true;
            }
        }
        // Retornar false si el evento a modificar no fue encontrado
        return false;
    }

    // Eliminar evento
    public boolean eliminarEventos(String idEvento) {

        // Utilizar removeIf para eliminar el evento con el ID especificado
        return eventos.removeIf(evento -> evento.getIdEvento().equals(idEvento));

    }

    /*
     * Para despues
     * public void listarEventos() {
     *
     * }
     */

    // Iniciar Sesion

<<<<<<< HEAD
    


    public List<Evento> buscarEventos() {

    public Evento buscarEventoPorId(String idEvento) {
        for (Evento evento : eventos) {
            if (evento.getIdEvento().equals(idEvento)) {
                return evento; // Devuelve el evento si el ID coincide
            }
        }
        return null; // Devuelve null si no se encuentra ningún evento con ese ID


    // Iniciar Sesion

    public boolean iniciarSesion(String correo, String contrasena) {
        // Verificar si las credenciales corresponden a un administrador
        if (esAdministrador(correo, contrasena)) {
            System.out.println("Inicio de sesión exitoso como administrador");
            // Aquí puedes agregar la lógica para el administrador si es necesario
<<<<<<< HEAD

=======
>>>>>>> 8e0a81d4c6dd84e5006a04461e4b5240b9cfbd00
            return true;
        } else {
            System.out.println("Inicio de sesión exitoso como cliente");
            // Aquí puedes agregar la lógica para el cliente si es necesario
            return false;
<<<<<<< HEAD
=======

>>>>>>> 8e0a81d4c6dd84e5006a04461e4b5240b9cfbd00
        }
    }

            // Método para verificar si las credenciales corresponden a un administrador
    private boolean esAdministrador(String correo, String contrasena) {
        Administrador administrador = Administrador.obtenerInstancia();
        return correo.equals(administrador.getCorreo()) && contrasena.equals(administrador.getContrasena());
    }

}

}

