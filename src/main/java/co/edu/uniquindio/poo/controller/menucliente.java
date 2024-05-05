package co.edu.uniquindio.poo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import co.edu.uniquindio.poo.Evento;
import co.edu.uniquindio.poo.Compra;

public class menucliente {

    @FXML
    private ComboBox<String> ciudadcb;

    @FXML
    private TextField cupon, numentradas;

    @FXML
    private TableView<Evento> tablaciudad;

    @FXML   
    private TableView<Compra> tablacompras;

    @FXML
    private TableColumn<Evento, String> nombrecol, tipocol, imagencol, fechacol, direccioncol, preciovipcol,
            capacidadvipcol, preciogcol, capacidadgcol;

    @FXML
    private TableColumn<Compra, String> clientecocol, cuponcocol, eventococol,cantidadcocol, totalcocol;

    @FXML
    private TextArea descripcionta;

    @FXML
    void cancelarcompraevent(ActionEvent event) {

    }

    @FXML
    void finalizarcompraevent(ActionEvent event) {

    }

    @FXML
    void volverevent(ActionEvent event) {

    }

}
