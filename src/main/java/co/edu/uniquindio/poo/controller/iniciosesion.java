package co.edu.uniquindio.poo.controller;

import java.io.IOException;

import co.edu.uniquindio.poo.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class iniciosesion {

    @FXML
    private PasswordField contrasenapas;

    @FXML
    private TextField usuariotf;

    @FXML
    void continuaraction(ActionEvent event) {

    }

    @FXML
    void registrarseaction(ActionEvent event) {
        try {
            App.setRoot("registro");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
