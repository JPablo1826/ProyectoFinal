package co.edu.uniquindio.poo.controller;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.exceptions.ObjetoExistenteException;
import co.edu.uniquindio.poo.exceptions.ObjetoNoExistenteException;
import co.edu.uniquindio.poo.model.Evento;
import co.edu.uniquindio.poo.model.TipoEvento;
import co.edu.uniquindio.poo.model.TipoLocalidad;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class menuadmin implements Initializable {
    @FXML
    private TableColumn<Evento, String> descripciongescol, direcciongescol, fechagescol, imagengescol, nombrecol,
            nombreestcol, nombregescol, porcentajeestcol, tipoestcol, tipogescol, totalganadocol, totalganadoestcol;

    @FXML
    private TextField descripciontf, direcciontf, nombretf, porcentaje, preciogeneraltf, precioviptf, codigo,
            capacidadgeneraltf, capacidadviptf;
    @FXML
    private DatePicker fechadp;
    @FXML
    private ComboBox<String> localidadcb, acogidacb, ciudadcb, tipocb;
    @FXML
    private TableView<Evento> tablaEstadisticas, tablaEstadisticas1, tablaciudad;

    @FXML
    void agregarcuponevent(ActionEvent event) {
        try {
            ModelFactoryController.getInstance().crearCupon(codigo.getText(),porcentaje.getText());
            new Alert(AlertType.INFORMATION, "Cupon creado con exito").show();
        } catch (ObjetoExistenteException e) {
            new Alert(AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void crearevent(ActionEvent event) {
        try {
            ModelFactoryController.getInstance().crearEvento(  nombretf.getText(),descripciontf.getText(), direcciontf.getText(),   Double.parseDouble(preciogeneraltf.getText()),Double.parseDouble(precioviptf.getText()) , Integer.parseInt(capacidadgeneraltf.getText()), Integer.parseInt(capacidadviptf.getText()), fechadp.getValue(),tipocb.getValue(),ciudadcb.getValue());
            new Alert(AlertType.INFORMATION, "Evento creado con exito").show();
            tablaEstadisticas.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().listarEventos()));//Actualizar las tablas 
            tablaEstadisticas1.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().listarEventos()));
            tablaciudad.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().listarEventos()));
            tablaEstadisticas.refresh();
            tablaEstadisticas1.refresh();
            tablaciudad.refresh();
            
        } catch (NumberFormatException e) {
            new Alert(AlertType.ERROR, "error").show();
        } catch (ObjetoExistenteException e) {
            new Alert(AlertType.ERROR, e.getMessage()).show();
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "No se pudo mandar el correo").show();
		}

    }

    @FXML
    void eliminarevent(ActionEvent event) {
        Evento evento = tablaciudad.getSelectionModel().getSelectedItem();
        if(evento==null) {
            new Alert(AlertType.ERROR, "Debe seleccionar un evento").show();
            return;
        }
        if(ModelFactoryController.getInstance().eliminarEventos(evento.getIdEvento())==true) {
            new Alert(AlertType.INFORMATION, "Evento eliminado con exito").show();
            if(localidadcb.getValue()!=null){
                tablaEstadisticas.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().listarEventos()));//Actualizar las tablas 
            }
            ciudadcb.setValue(null);
            acogidacb.setValue(null);
            tablaEstadisticas.refresh();
            tablaciudad.refresh();
        } else {
            new Alert(AlertType.ERROR, "No se pudo eliminar el evento").show();
        }            
    }

    @FXML
    void modificarevent(ActionEvent event) {
        Evento evento = tablaciudad.getSelectionModel().getSelectedItem();
        if(evento==null) {
            new Alert(AlertType.ERROR, "Debe seleccionar un evento").show();
            return;
        }
        try {
            ModelFactoryController.getInstance().modificarEvento(evento.getIdEvento(),  nombretf.getText(),descripciontf.getText(), direcciontf.getText(),   Double.parseDouble(preciogeneraltf.getText()),Double.parseDouble(precioviptf.getText()) , Integer.parseInt(capacidadgeneraltf.getText()), Integer.parseInt(capacidadviptf.getText()), fechadp.getValue(),tipocb.getValue(),ciudadcb.getValue());
            // si se modifica
            new Alert(AlertType.INFORMATION, "Evento modificado con exito").show();
            ciudadcb.setValue(null);
            localidadcb.setValue(null);
            acogidacb.setValue(null);
        } catch (NumberFormatException e) {
            new Alert(AlertType.ERROR,"error").show();
        } catch (ObjetoNoExistenteException e) {
            new Alert(AlertType.ERROR,e.getMessage()).show();
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ciudadcb.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().obtenerCiudades()));
        localidadcb.setItems(FXCollections.observableArrayList(TipoLocalidad.getValueStrings()));
        tipocb.setItems(FXCollections.observableArrayList(TipoEvento.stringValues()));
        acogidacb.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().obtenerCiudades()));
        acogidacb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                tablaEstadisticas1.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().calcularRecaudacion(newValue)));
                tablaEstadisticas1.refresh();
            }
        });

        tablaciudad.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, eventoSel) -> {
            if(eventoSel != null) {
                nombretf.setText(eventoSel.getNombreEvento());
                descripciontf.setText(eventoSel.getDescripcion());
                direcciontf.setText(eventoSel.getDireccion());
                preciogeneraltf.setText(eventoSel.getLocalidadGeneral().getPrecio() + "");
                precioviptf.setText(eventoSel.getLocalidadVIP().getPrecio() + "");
                capacidadgeneraltf.setText(eventoSel.getLocalidadGeneral().getCapacidad() + "");
                capacidadviptf.setText(eventoSel.getLocalidadVIP().getCapacidad() + "" );
                fechadp.setValue(eventoSel.getFecha());
                tipocb.setValue(eventoSel.getTipoEvento().getNombre());
                ciudadcb.setValue(eventoSel.getCiudad());
            }
        });

        tablaEstadisticas
                .setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().listarEventos()));
        tablaEstadisticas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String resultado = "Porcentaje vendido VIP: "
                        + ModelFactoryController.getInstance().obtenerPorcentaje(newValue, TipoLocalidad.VIP)
                        + "\nPorcentaje Vendido General: "
                        + ModelFactoryController.getInstance().obtenerPorcentaje(newValue, TipoLocalidad.GENERAL);
                new Alert(AlertType.CONFIRMATION, resultado).show();
            }
        });
        localidadcb.valueProperty().addListener((observable, oldValue, localidad) -> {
            if(localidad==null){
                tablaEstadisticas.setItems(FXCollections.observableArrayList());
                tablaEstadisticas.refresh();
            } else {
                tablaEstadisticas.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().listarEventos()));
                tablaEstadisticas.refresh();
                porcentajeestcol.setCellValueFactory(cellData ->new ReadOnlyStringWrapper(ModelFactoryController.getInstance().obtenerPorcentaje(cellData.getValue(), TipoLocalidad.getTipoLocalidad(localidad)) + ""));
            }
        });
        ciudadcb.valueProperty().addListener((observable, oldValue, filtro) -> {
            if(filtro==null){
                tablaciudad.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().listarEventos()));
                tablaciudad.refresh();
            } else {
                tablaciudad.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().listarEventosCiudad(filtro)));
                tablaciudad.refresh();
            }
        });
        tablaciudad.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().listarEventos()));
        descripciongescol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDescripcion()));
        direcciongescol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDireccion()));
        fechagescol.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        imagengescol.setCellValueFactory(cellData ->new ReadOnlyStringWrapper (cellData.getValue().getImagen()));
        nombrecol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getNombreEvento()));
        nombreestcol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getNombreEvento()));
        nombregescol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getNombreEvento()));
        tipoestcol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTipoEvento().getNombre()));
        tipogescol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTipoEvento().getNombre()));
        
        totalganadocol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(ModelFactoryController.getInstance().obtenerTotalGanadoEvento(cellData.getValue())+""));
        totalganadoestcol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(ModelFactoryController.getInstance().obtenerTotalGanadoEvento(cellData.getValue())+""));

        
    }

}
