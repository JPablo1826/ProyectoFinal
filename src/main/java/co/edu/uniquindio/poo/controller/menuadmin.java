package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.Evento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class menuadmin {
    @FXML
    private TableColumn<Evento, String> descripciongescol,direcciongescol,fechagescol,imagengescol,nombrecol,nombreestcol,nombregescol,porcentajeestcol,tipoestcol,tipogescol,totalganadocol, totalganadoestcol;

    @FXML
    private TextField descripciontf, direcciontf,nombretf,numentradas,preciogeneraltf,precioviptf,cupon, capacidadgeneraltf,capacidadviptf;
    @FXML
    private DatePicker fechadp;
    @FXML
    private ComboBox<String> localidadcb,tipocb,acogidacb,ciudadcb;
    @FXML
    private TableView<Evento> tablaEstadisticas,tablaEstadisticas1,tablaciudad;

    @FXML
    void cancelarcompraevent(ActionEvent event) {

    }

    @FXML
    void crearevent(ActionEvent event) {

    }

    @FXML
    void eliminarevent(ActionEvent event) {

    }

    @FXML
    void modificarevent(ActionEvent event) {

    }

}
