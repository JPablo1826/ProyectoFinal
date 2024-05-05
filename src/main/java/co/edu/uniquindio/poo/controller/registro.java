package co.edu.uniquindio.poo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class registro {

    @FXML
    private TextField cedulatf, emailtf, nombretf, telefonotf;

    @FXML
    private PasswordField contrasenapf;

    @FXML
    void registrarevent(ActionEvent event) {
        try {
            ModelFactoryController.getInstance().registrarCliente(cedulatf.getText(),nombretf.getText(), emailtf.getText(), telefonotf.getText(),contrasenapf.getText());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(AlertType.WARNING, "ALERTA NO SE PUEDE REGISTRAR").show();
        }
    }

}
