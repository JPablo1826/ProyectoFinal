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
    private static final long serialVersionUID = 5489636150873788781L;
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

    public List<String> obtenerCiudades() {
        ArrayList<String> ciudades = new ArrayList<>();
        ciudades.add("Armenia");
        ciudades.add("Bogota");
        ciudades.add("Medellin");
        return ciudades;
    }

    public void registrarNuevoCliente(Cliente cliente) throws Exception {
        cliente.setCodigo(generarCodigo());
        
        if (buscarClientePorEmail(cliente.getCorreo()) != null) {
            throw new ObjetoExistenteException("El cliente ya está registrado.");
        }
        clientes.add(cliente);
        agregarObservador(new ObservadorCorreo(cliente.getCorreo(), cliente.getNombre()));
        Correo.enviarCorreoRegistro(cliente.getCorreo(), cliente.getNombre(), cliente.getCodigo());

    }

    public void notificarNuevoCliente(Cliente cliente) {
        String mensaje = "¡Nuevo evento disponible!";
        for (Observador observador : observadores) {
            observador.actualizar(mensaje);
        }
    }

    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    public void crearCuponCliente(Cliente cliente) throws Exception {
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

    public void crearEvento(Evento evento) throws  Exception {
        evento.setIdEvento(UUID.randomUUID().toString());
        if (evento.getNombreEvento()==null|| evento.getDireccion()==null){
            throw new NullPointerException ("El nombre del evento no puede ser nulo");
        }
        
        if (evento.getLocalidadGeneral().getPrecio()<1||evento.getLocalidadGeneral().getCapacidad()<1||evento.getLocalidadVIP().getCapacidad()<1||evento.getLocalidadVIP().getPrecio()<1  ){
            throw new  IllegalArgumentException("El precio de la localidad no puede ser negativo");
            
        }
        if (buscarEventoPorIdEvento(evento.getIdEvento())) {
            throw new ObjetoExistenteException("El evento ya se encuentra creado");
        }
        System.out.println(clientes);
        Correo.enviarCorreoNuevoEvento(clientes,evento);
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

    public Evento buscarEventoPorId(String idEvento) throws ObjetoNoExistenteException {
      
        for (Evento evento : eventos) {
            if (evento.getIdEvento().equals(idEvento)) {
                System.out.println(evento);
                return evento; // Devuelve el evento si el ID coincide
            }
        }
        throw new ObjetoNoExistenteException ("El evento con el ID proporcionado no existe");
    }
   

    // Iniciar Sesion

    public Usuario iniciarSesion(String correo, String contrasena)
            throws inicioFallidoException, NoVerificadoException {
        if (correo.equals("")||contrasena.equals("")){
            throw new inicioFallidoException("Credenciales no proporcionadas");
        }

        if (esAdministrador(correo, contrasena)) {
            return Administrador.obtenerInstancia();
        }

    
        System.out.println(clientes);
        for (Cliente cliente : clientes) {
            String cor = cliente.getCorreo();
            String contra = cliente.getContasena();
            System.out.println("Correo cliente " + cor);
            System.out.println("Contra cliente " + contra);
            if (cor.equals(correo) && contra.equals(contrasena)) {
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
        if (evento == null)
            throw new ObjetoNoExistenteException("El evento no fue encontrado");
        Compra compra = Compra.builder().cliente(encontrado).evento(evento).localidad(tipo)
                .idCompra(UUID.randomUUID().toString()).cantidad(cantidad).build();
        if (!compra.verificarCapacidadEvento())
            throw new CapacidadException("La cantidad supera la capacidad solicitada");
        double total = (tipo == TipoLocalidad.GENERAL ? evento.getLocalidadGeneral().getPrecio()
                : evento.getLocalidadVIP().getPrecio()) * cantidad;
       
        Factura factura = Factura.builder().compra(compra).fechaCompra(LocalDate.now())
                .codigoFactura(compra.getIdCompra()).total(total).build();
        compra.setFactura(factura);
        boolean dctoPrimera = false;
        if (encontrado.getCompras().size() == 0) {
            String codigo = UUID.randomUUID().toString();
            crearCupon(Cupon.builder().cuponRegistro(true).codigo(codigo).estado(Estado.ACTIVO)
                    .porcentaje(10).build());

            cliente.setEstrategiaDescuento(new DescuentoPrimerCompra());

            total = cliente.getEstrategiaDescuento().aplicarDescuento(total);
        }
        Cupon cuponGeneral = buscarCuponCodigo(codigoCupon);
        String porcentaje = "0%";
        if (cuponGeneral != null) {
            if (dctoPrimera)
                cliente.setEstrategiaDescuento(new DescuentoCupon(cuponGeneral));

            total = cliente.getEstrategiaDescuento().aplicarDescuento(total);
        }
        compra.setCupon(cuponGeneral);
        Correo.enviarCorreoFactura(factura);
        cliente.agregarCompra(compra);
        actualizarCliente(cliente);
        facturas.add(factura);
    }

    public double obtenerPorcentajeLocalidad(TipoLocalidad tipoLocalidad, Evento evento) {
        double ocupacion = 0;
        for (Factura factura : facturas) {
            if (factura.getCompra().getLocalidad() == tipoLocalidad) {
                if (factura.getCompra().getEvento().getIdEvento().equals(evento.getIdEvento())) {
                    ocupacion += factura.getCompra().getCantidad();
                }
            }
        }
        double resultado;
        if (tipoLocalidad == TipoLocalidad.GENERAL) {

            resultado = (ocupacion / evento.getLocalidadGeneral().getCapacidad());
        } else {
            resultado = ocupacion / evento.getLocalidadVIP().getCapacidad();
        }
        return resultado * 100;
    }

    public double obtenerTotalGanado() {
        double total = 0;
        for (Factura factura : facturas) {
            total += factura.getTotal();
        }
        return total;
    }
    public double obtenerTotalGanadoEvento(Evento evento) {
        double total = 0;
        for (Factura factura : facturas) {
            if(factura.getCompra().getEvento().getIdEvento().equals(evento.getIdEvento())) {
                total += factura.getTotal();
            }
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
        if (cupon==null) throw new NullPointerException ("El cupón no puede ser nulo");
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
                c.setResena(cliente.getResena());
                c.setCompras(cliente.getCompras());

                return;
            }
        }

    }

    public ArrayList<Compra> obtenerCompras(Cliente cliente) {
        ArrayList<Compra> compras = new ArrayList<>();
        for (Factura factura : facturas) {
            if (factura.getCompra().getCliente().getCorreo().equals(cliente.getCorreo())) {
                compras.add(factura.getCompra());
            }
        }
        return compras;

    }

    public List<Evento> listarEventosCiudad(String ciudad) {
        return eventos.stream().filter((evento) -> evento.getCiudad().equals(ciudad)).toList();
    }


    

}