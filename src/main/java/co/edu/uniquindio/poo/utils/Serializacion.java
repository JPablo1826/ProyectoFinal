package co.edu.uniquindio.poo.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import co.edu.uniquindio.poo.model.UniEventos;

public class Serializacion {

    private static final String RUTA = "datos.dat";

    public static void guardarDatos(UniEventos uniEventos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA))) {
            oos.writeObject(uniEventos);
            System.out.println("Guardado exitoso de datos");
        } catch (IOException e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }

    public static UniEventos obternerDatos() {
        UniEventos uniEventos = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA))) {
            uniEventos = (UniEventos) ois.readObject();
            System.out.println("Datos cargados exitosamente");
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de datos");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer datos: " + e.getMessage());
        }
        if (uniEventos == null) {
            uniEventos = new UniEventos();
            guardarDatos(uniEventos); // Si no se encontraron datos, se crea una nueva instancia y se guarda
        }
        return uniEventos;
    }}