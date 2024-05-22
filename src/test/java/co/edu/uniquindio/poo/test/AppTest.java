package co.edu.uniquindio.poo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import co.edu.uniquindio.poo.exceptions.ObjetoNoExistenteException;
import co.edu.uniquindio.poo.exceptions.inicioFallidoException;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Evento;
import co.edu.uniquindio.poo.model.Localidad;
import co.edu.uniquindio.poo.model.NoVerificadoException;
import co.edu.uniquindio.poo.model.TipoLocalidad;
import co.edu.uniquindio.poo.model.UniEventos;
import co.edu.uniquindio.poo.model.Usuario;

public class AppTest {

    @Test
    public void testIniciarSesionAdminSinCredenciales() throws NoVerificadoException {
        UniEventos uniEventos = UniEventos.obtenerInstancia();

        String correo = "";
        String contrasena = "";

        // When & Then: Intentar iniciar sesión sin credenciales debe lanzar una
        // excepción
        Exception exception = assertThrows(inicioFallidoException.class, () -> {
            uniEventos.iniciarSesion(correo, contrasena);
        });

        // Verificar que el mensaje de la excepción es el esperado
        String expectedMessage = "Credenciales no proporcionadas";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // Verificar que no se ha devuelto ningún usuario
        Usuario usuario = null;
        try {
            usuario = uniEventos.iniciarSesion(correo, contrasena);
        } catch (inicioFallidoException e) {
            // Esto es esperado, no necesitamos hacer nada aquí
        }
        assertNull(usuario, "El usuario debe ser nulo cuando las credenciales no son proporcionadas");
    }

    @Test
    public void testBuscarEventoPorIdEventoNoExistente() {
        UniEventos uniEventos = UniEventos.obtenerInstancia();
        String idEvento = UUID.randomUUID().toString();

        ObjetoNoExistenteException exception = assertThrows(ObjetoNoExistenteException.class, () -> {
            uniEventos.buscarEventoPorId(idEvento);
        });

        // Verificar que el mensaje de la excepción es el esperado (si aplicable)
        String expectedMessage = "El evento con el ID proporcionado no existe";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testObserverNotAddedForExistingClientEmail() {
        UniEventos uniEventos = UniEventos.obtenerInstancia();
        Cliente cliente1 = new Cliente("123", "John Doe", "1234567890", "johndoe@example.com", "1234567890");
        Cliente cliente2 = new Cliente("456", "Jane Doe", "9876543210", "johndoe@example.com", "9876543210");
        int cantidadObservadoresAntes = uniEventos.getObservadores().size();

        try {
            uniEventos.registrarNuevoCliente(cliente1);
        } catch (Exception e) {
            fail("No se esperaba una excepción al registrar el primer cliente.");
        }

        Exception exception = null;
        try {
            uniEventos.registrarNuevoCliente(cliente2);
        } catch (Exception e) {
            exception = e;
        }

        // Assert
        int cantidadObservadoresDespues = uniEventos.getObservadores().size();

        assertEquals("El  ya está registrado.", exception.getMessage());
        assertEquals(cantidadObservadoresAntes + 1, cantidadObservadoresDespues);
    }

    @Test
    public void testCuponCreation_NullCupon_ExceptionThrown() {
        UniEventos uniEventos=  UniEventos.obtenerInstancia();
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            uniEventos.crearCupon(null);
        });

        // Verificar que el mensaje de la excepción es el esperado (si aplicable)
        String expectedMessage = "El cupón no puede ser nulo";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testCrearNuevoEventoPrecioNegativoExceptionThrown() {
        UniEventos uniEventos = UniEventos.obtenerInstancia();
        Localidad localidadGeneral = new Localidad(-30, TipoLocalidad.GENERAL, 60);
        Localidad localidadVIP = new Localidad(-50, TipoLocalidad.VIP, 40);

        Evento evento = Evento.builder()
                .IdEvento(UUID.randomUUID().toString())
                .NombreEvento("Test event")
                .Descripcion("Test event")
                .fecha(LocalDate.now().plusDays(10))
                .localidadGeneral(localidadGeneral)
                .localidadVIP(localidadVIP)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            uniEventos.crearEvento(evento);
        });

        // Verificar que el mensaje de la excepción es el esperado
        String expectedMessage = "El precio de la localidad no puede ser negativo";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    public void testCapacidadMaximoNegativoExceptionThrown() {
        UniEventos uniEventos = UniEventos.obtenerInstancia();
        Localidad localidadGeneral = new Localidad(10, TipoLocalidad.GENERAL, -60);
        Localidad localidadVIP = new Localidad(30, TipoLocalidad.VIP, -40);
        Evento evento = Evento.builder()
                .IdEvento(UUID.randomUUID().toString())
                .NombreEvento("Test event")
                .Descripcion("Test event")
                .fecha(LocalDate.now().plusDays(10))
                .localidadGeneral(localidadGeneral)
                .localidadVIP(localidadVIP)
                .build();

        assertThrows(IllegalArgumentException.class, () -> uniEventos.crearEvento(evento));
    }

    @Test
    public void testCrearNuevoEvento_NombreNulo_ExceptionThrown() {
        UniEventos uniEventos = UniEventos.obtenerInstancia();
        Evento evento = Evento.builder()
                .IdEvento(UUID.randomUUID().toString())
                .NombreEvento(null)
                .Descripcion(null)
                .fecha(LocalDate.now().plusDays(10))
                .build();

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            uniEventos.crearEvento(evento);
        });

        // Verificar que el mensaje de la excepción es el esperado
        String expectedMessage = "El nombre del evento no puede ser nulo";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}


