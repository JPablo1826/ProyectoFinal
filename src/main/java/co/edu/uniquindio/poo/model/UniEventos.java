package co.edu.uniquindio.poo.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import co.edu.uniquindio.poo.exceptions.CapacidadException;
import co.edu.uniquindio.poo.exceptions.ObjetoExistenteException;
import co.edu.uniquindio.poo.exceptions.ObjetoNoExistenteException;
import co.edu.uniquindio.poo.exceptions.inicioFallidoException;
import co.edu.uniquindio.poo.utils.Correo;

import lombok.Getter;

@Getter
public class UniEventos implements Serializable {
    private List<Observador> observadores = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Evento> eventos = new ArrayList<>();
    private List<Cupon> cupones = new ArrayList<>();
    private List<Factura> facturas = new ArrayList<>();
    private static UniEventos instanciaUnica;

    public static UniEventos obtenerInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new UniEventos();
        }
        return instanciaUnica;
    }

    private static String generarCodigo() {
        Random random = new Random();
        String codigo = "";
        for (int i = 0; i < 6; i++) {
            codigo += random.nextInt(10);
        }
        return codigo;
    }

    public void registrarNuevoCliente(Cliente cliente) throws Exception {
        cliente.setCodigo(generarCodigo());
        if (buscarClientePorEmail(cliente.getCorreo()) != null) {
            throw new ObjetoExistenteException("El cliente ya está registrado.");
        }
        System.out.println(cliente.getCorreo());
        clientes.add(cliente);
        agregarObservador(new ObservadorCorreo(cliente.getCorreo(),cliente.getNombre()));
        Correo.enviarCorreoRegistro(cliente.getCorreo(), cliente.getNombre(), cliente.getCodigo());

    }

    private void notificarNuevoCliente(Cliente cliente) {
        String mensaje = "¡Nuevo evento disponible!";
        for (Observador observador : observadores) {
            observador.actualizar(mensaje);
        }
    }

    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    public void crearCupon(Cliente cliente) throws Exception {
        String codigo = UUID.randomUUID().toString();
        crearCupon(Cupon.builder().cuponRegistro(true).codigo(codigo).estado(Estado.ACTIVO)
                .porcentaje(15).build());
        Correo.enviarCorreoCupon(cliente.getCorreo(), cliente.getNombre(), codigo, "15%");
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
    public void registrarNuevoEvento(String tipoEvento) throws ObjetoExistenteException, ObjetoNoExistenteException {
        // Suponiendo que el ID del evento es generado o manejado de alguna manera aquí
        Evento evento = crearEvento(tipoEvento);
        if (buscarEventoPorIdEvento(evento.getIdEvento())) {
            throw new ObjetoExistenteException("El evento ya se encuentra creado");
        }
        eventos.add(evento);
    }

    public Evento crearEvento(String tipoEvento) throws ObjetoNoExistenteException {
        switch (tipoEvento) {
            case "teatro":
                return new TeatroFactory().crearEvento();
            case "deportivo":
                return new DeporteFactory().crearEvento();
            case "festival":
                return new FestivalFactory().crearEvento();
            case "concierto":
                return new ConciertoFactory().crearEvento();
            default:
                throw new ObjetoNoExistenteException("Tipo de evento desconocido: " + tipoEvento);
        }
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
                eventoExistente.setLocalidadGeneral(eventoModificado.getLocalidadGeneral());
                eventoExistente.setLocalidadVIP(eventoModificado.getLocalidadVIP());

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

    public void eliminarCompra(Compra comprabuscar, Cliente cliente) throws ObjetoNoExistenteException {
        if (cliente.getCompras().remove(comprabuscar) == false)
            throw new ObjetoNoExistenteException("No se encotro la compra");
    }

    public List<Compra> listarComprasClientes(String idClient) {
        List<Compra> compras = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (cliente.getID().equals(idClient)) {
                compras.addAll(cliente.getCompras());
            }
        }
        return compras;

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

    public Usuario iniciarSesion(String correo, String contrasena)
            throws inicioFallidoException, NoVerificadoException {
        if (esAdministrador(correo, contrasena)) {
            return Administrador.obtenerInstancia();
        }
        for (Cliente cliente : clientes) {
            if (cliente.getCorreo().equals(correo) && cliente.getContasena().equals(contrasena)) {
                if (!cliente.estaVericado())
                    throw new NoVerificadoException("El cliente no esta verificado");
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

    public Cupon buscarCuponCodigo(String codigo) {
        for (Cupon cupon : cupones) {
            if (cupon.getCodigo().equals(codigo)) {
                return cupon;
            }
        }
        return null;
    }

    public void realizarCompra(Cliente cliente, String idEvento, String codigoCupon, TipoLocalidad tipo, int cantidad)
            throws Exception {

        Cliente encontrado = buscarClientePorEmail(cliente.getCorreo());
        if (encontrado == null)
            throw new ObjetoNoExistenteException("El cliente no fue encontrado");
        Evento evento = buscarEventoPorId(idEvento);
        Cupon cupon = buscarCuponCodigo(codigoCupon);
        if (evento == null)
            throw new ObjetoNoExistenteException("El evento no fue encontrado");
        Compra compra = Compra.builder().cliente(encontrado).cupon(cupon).evento(evento).localidad(tipo)
                .idCompra(UUID.randomUUID().toString()).cantidad(cantidad).build();
        if (!compra.verificarCapacidadEvento())
            throw new CapacidadException("La cantidad supera la capacidad solicitada");
        double total = (tipo == TipoLocalidad.GENERAL ? evento.getLocalidadGeneral().getPrecio()
                : evento.getLocalidadVIP().getPrecio()) * cantidad;
        redimirCupon(cupon);

        // TODO Falta actualizar la capacidad del evento
        Factura factura = Factura.builder().compra(compra).fechaCompra(LocalDate.now())
                .codigoFactura(compra.getIdCompra()).total(total).build();

        if (encontrado.getCompras().size() == 0) {
            String codigo = UUID.randomUUID().toString();
            crearCupon(Cupon.builder().cuponRegistro(true).codigo(codigo).estado(Estado.ACTIVO)
                    .porcentaje(10).build());
            // First, apply the discount strategy to the client (assuming this method does
            // something relevant that doesn't directly affect the email sending)
            cliente.setEstrategiaDescuento(new DescuentoPrimerCompra());

            // Assuming you have a method or a way to get the coupon code and discount
            // percentage after applying the discount strategy
                                                     // applying the discount strategy
            String porcentajeDescuento = "10%"; // This should be replaced with the actual discount percentage

            // Now, send the email with the correct parameters
            Correo.enviarCorreoCupon(cliente.getCorreo(), cliente.getNombre(), codigoCupon, porcentajeDescuento);
        }
        cliente.agregarCompra(compra);
        facturas.add(factura);
    }

    public double obtenerPorcentajeLocalidad(TipoLocalidad tipoLocalidad) {
        List<Evento> eventos = new ArrayList<>();
        List<Integer> ocupacion = new ArrayList<>();
        for (Factura factura : facturas) {
            if (factura.getCompra().getLocalidad() == tipoLocalidad) {
                int indice = eventos.indexOf(factura.getCompra().getEvento());
                if (indice != -1) {
                    ocupacion.set(indice, ocupacion.get(indice) + factura.getCompra().getCantidad());
                } else {
                    eventos.add(factura.getCompra().getEvento());
                    ocupacion.add(factura.getCompra().getCantidad());
                }
            }
        }
        return ocupacion.stream().mapToInt(e -> e).average().orElse(0);
    }

    public double obtenerTotalGanado() {
        double total = 0;
        for (Factura factura : facturas) {
            total += factura.getTotal();
        }
        return total;
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

    public void crearCupon(Cupon cupon) throws ObjetoExistenteException {
        for (Cupon cuponc : cupones) {
            if (cupon.getCodigo().equals(cuponc.getCodigo())) {
                throw new ObjetoExistenteException("El cupon ya se encuentra creado");
            }
        }
        cupones.add(cupon);
    }

    public void redimirCupon(Cupon cupon) throws ObjetoNoExistenteException {
        for (Cupon cuponc : cupones) {
            if (cupon.getCodigo().equals(cuponc.getCodigo())) {
                cupones.remove(cuponc);
            }
        }
        throw new ObjetoNoExistenteException("No se encontro el cupon");
    }

    public void verificarUsuario(String usuario, String codigo) throws ObjetoNoExistenteException {
        Cliente clienteActualizado = buscarClientePorEmail(usuario);
        if (clienteActualizado != null) {
            if (!clienteActualizado.getCodigo().equals(codigo)) {
                throw new ObjetoNoExistenteException("El codigo no coincide");
            }
            clienteActualizado.setCodigo(null);
            actualizarCliente(clienteActualizado);
        } else {

            throw new ObjetoNoExistenteException("No se encontro el cliente");
        }
    }

    public void actualizarCliente(Cliente cliente) {
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            if (c.getCorreo().equals(cliente.getCorreo())) {
                c.setContasena(cliente.getContasena());
                c.setTelefono(cliente.getTelefono());
                c.setCodigo(cliente.getCodigo());

                return;
            }
        }

    }

}