package co.edu.uniquindio.poo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import co.edu.uniquindio.poo.exceptions.ObjetoExistenteException;
import co.edu.uniquindio.poo.exceptions.inicioFallidoException;
import co.edu.uniquindio.poo.model.Evento;
import co.edu.uniquindio.poo.model.UniEventos;
import co.edu.uniquindio.poo.model.Usuario;
import lombok.extern.java.Log;

@Log
public class AppTest {
    private UniEventos uniEventos;
    

    @Test
    public void test() throws Exception {
        log.info("Hola mundo");
       
    }
    /*
    @Test
    public void testIniciarSesionAdminSinCredenciales() {
        // Given: No administrator credentials provided
        String correo = "";
        String contrasena = "";

        // When: Iniciar sesión sin proporcionar credenciales
        Usuario usuario = null;
        try {
            usuario = uniEventos.iniciarSesion(correo, contrasena);
            fail("Debería lanzar excepción en este caso");
        } catch (Exception e) {
            // Then: Debe lanzar una excepción de inicio fallido
            assertTrue(e instanceof inicioFallidoException);
        }

        // Then: No debe devolver ningún usuario
        assertNull(usuario);
    }
    */
    
   /* @Test
    public void testRegistrarNuevoEvento_NonExistingEventId_AddsEvent() {
        String tipoEvento = "Teatro";
        uniEventos.registrarNuevoEvento(tipoEvento);

        assertTrue(uniEventos.eventos.contains(evento));
    }

    */









}


    
    



