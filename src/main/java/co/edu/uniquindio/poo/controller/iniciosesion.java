package co.edu.uniquindio.poo.controller;

import java.io.IOException;

import co.edu.uniquindio.poo.App;
import co.edu.uniquindio.poo.exceptions.ObjetoNoExistenteException;
import co.edu.uniquindio.poo.exceptions.inicioFallidoException;
import co.edu.uniquindio.poo.model.NoVerificadoException;
import co.edu.uniquindio.poo.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

public class iniciosesion {

    @FXML
    private PasswordField contrasenapas;

    @FXML
    private TextField usuariotf;

    @FXML
    void continuaraction(ActionEvent event) throws IOException {
        try {
            Usuario usuario = ModelFactoryController.getInstance().iniciarSesion(usuariotf.getText(), contrasenapas.getText());
            DatosguardadosController.getInstance().setUsuarioActual(usuario);
            if(DatosguardadosController.getInstance().verificarIsAdmi()==true){
                App.setRoot("menuAdmin");
            } else {
                App.setRoot("menuClientes");
            }
        } catch (inicioFallidoException e) {
            new Alert(AlertType.WARNING, e.getMessage()).show();
        } catch (NoVerificadoException e) {
            TextInputDialog inputdialog = new TextInputDialog("");
            inputdialog.setContentText("Ingresa el codigo: ");
            inputdialog.setHeaderText("codigo");
            inputdialog.showAndWait();
            try {
                ModelFactoryController.getInstance().verificarUsuario( usuariotf.getText(),inputdialog.getEditor().getText());

            } catch (ObjetoNoExistenteException e1) {
                e1.printStackTrace();
            }
        }
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
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
