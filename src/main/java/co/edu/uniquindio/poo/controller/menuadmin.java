package co.edu.uniquindio.poo.controller;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.exceptions.ObjetoExistenteException;
import co.edu.uniquindio.poo.model.Evento;
import co.edu.uniquindio.poo.model.TipoEvento;
import co.edu.uniquindio.poo.model.TipoLocalidad;
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
    private TextField descripciontf, direcciontf, nombretf, numentradas, preciogeneraltf, precioviptf, cupon,
            capacidadgeneraltf, capacidadviptf;
    @FXML
    private DatePicker fechadp;
    @FXML
    private ComboBox<String> localidadcb, acogidacb, ciudadcb;
    @FXML
    private ComboBox<TipoEvento> tipocb;
    @FXML
    private TableView<Evento> tablaEstadisticas, tablaEstadisticas1, tablaciudad;

    @FXML
    void cancelarcompraevent(ActionEvent event) {

    }

    @FXML
    void crearevent(ActionEvent event) {
        try {
            ModelFactoryController.getInstance().crearEvento(  nombretf.getText(),descripciontf.getText(), direcciontf.getText(),   Double.parseDouble(preciogeneraltf.getText()),Double.parseDouble(precioviptf.getText()) , Integer.parseInt(capacidadgeneraltf.getText()), Integer.parseInt(capacidadviptf.getText()), fechadp.getValue(),tipocb.getValue(),ciudadcb.getValue());
        } catch (NumberFormatException e) {
            new Alert(AlertType.ERROR,"error").show();
        } catch (ObjetoExistenteException e) {
            new Alert(AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void eliminarevent(ActionEvent event) {

    }

    @FXML
    void modificarevent(ActionEvent event) {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ciudadcb.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().obtenerCiudades()));
        localidadcb.setItems(FXCollections.observableArrayList(TipoLocalidad.getValueStrings()));
        tipocb.setItems(FXCollections.observableArrayList(TipoEvento.values()));
        acogidacb.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().obtenerCiudades()));
        acogidacb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                tablaEstadisticas1.setItems(FXCollections
                        .observableArrayList(ModelFactoryController.getInstance().listarEventosCiudad(newValue)));
        });

        tablaEstadisticas
                .setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().listarEventos()));
        tablaEstadisticas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String resultado = "Porcentaje vendido VIP: "
                        + ModelFactoryController.getInstance().obtenerPorcentajeVIP(newValue)
                        + "\nPorcentaje Vendido General: "
                        + ModelFactoryController.getInstance().obtenerPorcentajeGeneral(newValue);
                new Alert(AlertType.CONFIRMATION, resultado).show();
            }
        });
        tablaciudad.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().listarEventos()));
       /* descripciongescol.setCellValueFactory(cellData -> cellData.getValue().getDescripcion());
        direcciongescol.setCellValueFactory(cellData -> cellData.getValue().getDireccion());
        fechagescol.setCellValueFactory(cellData -> cellData.getValue().getFecha());
        imagengescol.setCellValueFactory(cellData -> cellData.getValue().getImagen());
        nombrecol.setCellValueFactory(cellData -> cellData.getValue().getNombreEvento());
        nombreestcol.setCellValueFactory(cellData -> cellData.getValue().getNombreEvento());
        nombregescol.setCellValueFactory(cellData -> cellData.getValue().getNombreEvento());
        porcentajeestcol.setCellValueFactory(cellData -> cellData.getValue().getNombreEvento());
        tipoestcol.setCellValueFactory(cellData -> cellData.getValue().getTipoEvento());******/

    }

}
