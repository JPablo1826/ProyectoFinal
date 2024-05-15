package co.edu.uniquindio.poo.controller;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import co.edu.uniquindio.poo.model.Evento;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.model.Compra;

public class menucliente implements Initializable{

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

    
    @FXML
    void estrella2click(ActionEvent event) {

    }
    
    
    @FXML
    void estrella3click(ActionEvent event) {

    }

    @FXML
    void estrella4click(ActionEvent event) {
    }
    
    @FXML
    void estrella5click(ActionEvent event) {
    }
    
    @FXML
    void estrella1click(ActionEvent event) {

    }

    @FXML
    void guardarresenaevent(ActionEvent event) {
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ciudadcb.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().obtenerCiudades()));
        tablacompras.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().obtenerCompra(null)));
        tablaciudad.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().listarEventos()));
        nombrecol.setCellValueFactory(celda-> new ReadOnlyStringWrapper(celda.getValue().getNombreEvento()));
        tipocol.setCellValueFactory(celda-> new ReadOnlyStringWrapper(celda.getValue().getTipoEvento().name()));
        imagencol.setCellValueFactory(celda-> new ReadOnlyStringWrapper(celda.getValue().getImagen()));
        fechacol.setCellValueFactory(celda-> new ReadOnlyStringWrapper(celda.getValue().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        direccioncol.setCellValueFactory(celda-> new ReadOnlyStringWrapper(celda.getValue().getDireccion()));
        preciovipcol.setCellValueFactory(celda-> new ReadOnlyStringWrapper(String.format("%.2f", celda.getValue().getLocalidadVIP().getPrecio())));
        capacidadvipcol.setCellValueFactory(celda-> new ReadOnlyStringWrapper(String.valueOf(celda.getValue().getLocalidadVIP().getCapacidad())));
        preciogcol.setCellValueFactory(celda-> new ReadOnlyStringWrapper(String.format("%.2f", celda.getValue().getLocalidadGeneral().getPrecio())));
        capacidadgcol.setCellValueFactory(celda-> new ReadOnlyStringWrapper(String.valueOf(celda.getValue().getLocalidadGeneral().getCapacidad())));
        clientecocol.setCellValueFactory(celda-> new ReadOnlyStringWrapper(celda.getValue().getCliente().getNombre()));
        cuponcocol.setCellValueFactory(celda-> new ReadOnlyStringWrapper(celda.getValue().getCupon().getCodigo()));
        eventococol.setCellValueFactory(celda-> new ReadOnlyStringWrapper(celda.getValue().getIdCompra()));
        cantidadcocol.setCellValueFactory(celda-> new ReadOnlyStringWrapper(String.valueOf(celda.getValue().getCantidad())));
        totalcocol.setCellValueFactory(celda-> new ReadOnlyStringWrapper(String.format("%.2f", celda.getValue().getFactura().getTotal())));

        tablacompras.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null)
                descripcionta.setText(newValue.getEvento().getDescripcion());
        });
        ciudadcb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                
                tablaciudad.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().listarEventosCiudad(newValue)));
            }
        });
        
    }

}
