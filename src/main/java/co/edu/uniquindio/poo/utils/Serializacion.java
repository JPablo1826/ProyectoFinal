package co.edu.uniquindio.poo.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import co.edu.uniquindio.poo.model.UniEventos;

public class Serializacion {

    private static final String RUTA = "datos.dat";

    public static void guardarDatos(UniEventos uniEventos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA))) {
            oos.writeObject(uniEventos);
        } catch (Exception e) {
        }
    }

    public static UniEventos obternerDatos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA))) {
            return (UniEventos) ois.readObject();
        } catch (Exception e) {
            UniEventos uni = new UniEventos();
            guardarDatos(uni);
            return uni;
        }
    }
}