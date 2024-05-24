package co.edu.uniquindio.poo.controller;

import java.time.format.DateTimeFormatter;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import co.edu.uniquindio.poo.model.Evento;
import co.edu.uniquindio.poo.exceptions.CapacidadException;
import co.edu.uniquindio.poo.exceptions.ObjetoNoExistenteException;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.TipoLocalidad;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.model.Compra;

public class menucliente implements Initializable {

    @FXML
    private TabPane tabPane;
    @FXML
    private TextArea infota, descripcionta, descripcionresenata;

    @FXML
    private ComboBox<String> ciudadcb, localidadcomb;

    @FXML
    private TextField cupon, numentradas;

    @FXML
    private Button finalizarbutt;

    @FXML
    private TableView<Evento> tablaciudad;

    @FXML
    private TableView<Compra> tablacompras;

    @FXML
    private TableColumn<Evento, String> nombrecol, tipocol, imagencol, fechacol, direccioncol, preciovipcol,
            capacidadvipcol, preciogcol, capacidadgcol;

    @FXML
    private TableColumn<Compra, String> clientecocol, cuponcocol, eventococol, cantidadcocol, totalcocol;

    @FXML
    private SVGPath estrella1, estrella2, estrella3, estrella4, estrella5;

    private SimpleObjectProperty<Integer> propEstrella = new SimpleObjectProperty<Integer>(0);

    @FXML
    void cancelarcompraevent(ActionEvent event) {
        tabPane.getSelectionModel().select(0);
    }

    @FXML
    void realizarcompraevent(ActionEvent event) {
        if (tablaciudad.getSelectionModel().getSelectedItem() != null) {
            tabPane.getSelectionModel().select(1);
        } else {
            new Alert(AlertType.WARNING, "No se ha seleccionado ningun evento").show();
        }
    }

    @FXML
    void finalizarcompraevent(ActionEvent event) {
        Evento evento = tablaciudad.getSelectionModel().getSelectedItem();
        Cliente cliente = (Cliente) DatosguardadosController.getInstance().getUsuarioActual().getValue();
        try {
            ModelFactoryController.getInstance().realizarCompra(cliente, evento.getIdEvento(), cupon.getText(),
                    TipoLocalidad.getTipoLocalidad(localidadcomb.getValue()), numentradas.getText());
            new Alert(AlertType.CONFIRMATION, "Su compra fue exitosa").show();
        } catch (ObjetoNoExistenteException | CapacidadException e) {
            new Alert(AlertType.WARNING, e.getMessage()).show();
        } catch (NumberFormatException e) {
            new Alert(AlertType.WARNING, "Escribe bien el numero de entradas").show();
        } catch (Exception e) {
            new Alert(AlertType.WARNING, "No se pudieron mandar los correos").show();
        }

    }

    @FXML
    void estrella1click(MouseEvent event) {
        propEstrella.setValue(1);
    }

    @FXML
    void estrella2click(MouseEvent event) {
        propEstrella.setValue(2);
    }

    @FXML
    void estrella3click(MouseEvent event) {
        propEstrella.setValue(3);
    }

    @FXML
    void estrella4click(MouseEvent event) {
        propEstrella.setValue(4);
    }

    @FXML
    void estrella5click(MouseEvent event) {
        propEstrella.setValue(5);
    }

    @FXML
    void guardarresenaevent(ActionEvent event) {
        Cliente cliente = (Cliente) DatosguardadosController.getInstance().getUsuarioActual().getValue();
        ModelFactoryController.getInstance().guardarResena(cliente, propEstrella.getValue(),
                descripcionresenata.getText());
        new Alert(AlertType.CONFIRMATION, "Su resena fue guardada").show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Cliente cliente = (Cliente) DatosguardadosController.getInstance().getUsuarioActual().getValue();
        ciudadcb.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().obtenerCiudades()));
        localidadcomb.setItems(FXCollections.observableArrayList(TipoLocalidad.getValueStrings()));
        tablacompras.setItems(
                FXCollections.observableArrayList(ModelFactoryController.getInstance().obtenerCompra(cliente)));
        tablaciudad.setItems(FXCollections.observableArrayList(ModelFactoryController.getInstance().listarEventos()));
        nombrecol.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getNombreEvento()));
        tipocol.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getTipoEvento().name()));
        imagencol.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getImagen()));
        fechacol.setCellValueFactory(celda -> new ReadOnlyStringWrapper(
                celda.getValue().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        direccioncol.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getDireccion()));
        preciovipcol.setCellValueFactory(celda -> new ReadOnlyStringWrapper(
                String.format("%.2f", celda.getValue().getLocalidadVIP().getPrecio())));
        capacidadvipcol.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(String.valueOf(celda.getValue().getLocalidadVIP().getCapacidad())));
        preciogcol.setCellValueFactory(celda -> new ReadOnlyStringWrapper(
                String.format("%.2f", celda.getValue().getLocalidadGeneral().getPrecio())));
        capacidadgcol.setCellValueFactory(celda -> new ReadOnlyStringWrapper(
                String.valueOf(celda.getValue().getLocalidadGeneral().getCapacidad())));
        clientecocol.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getCliente().getNombre()));
        cuponcocol.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getCupon().getCodigo()));
        eventococol.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getIdCompra()));
        cantidadcocol.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(String.valueOf(celda.getValue().getCantidad())));
        totalcocol.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(String.format("%.2f", celda.getValue().getFactura().getTotal())));

        tablaciudad.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, event) -> {
            if (event != null)
                descripcionta.setText(event.getDescripcion());
        });
        ciudadcb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tablaciudad.setItems(FXCollections
                        .observableArrayList(ModelFactoryController.getInstance().listarEventosCiudad(newValue)));
            } else {
                tablaciudad.setItems(
                        FXCollections.observableArrayList(ModelFactoryController.getInstance().listarEventos()));
            }

        });

        tablacompras.setItems(FXCollections
                .observableArrayList(ModelFactoryController.getInstance().listarComprasClientes(cliente.getID())));

        clientecocol.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getCliente().getNombre()));
        cuponcocol.setCellValueFactory(celda -> {
            if (celda.getValue().getCupon()==null) {
                return new ReadOnlyStringWrapper("");
            }
            return new ReadOnlyStringWrapper(celda.getValue().getCupon().getCodigo());           
        });
        eventococol.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getIdCompra()));
        cantidadcocol.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(String.valueOf(celda.getValue().getCantidad())));
        totalcocol.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(String.format("%.2f", celda.getValue().getFactura().getTotal())));

        localidadcomb.valueProperty().addListener((observable, oldValue, localidad) -> {
            if (localidad == null) {
                infota.setText("");
            } else {
                Evento evento = tablaciudad.getSelectionModel().getSelectedItem();
                int capacidadGeneral = evento.getLocalidadGeneral().getCapacidad();
                int capacidadVIP = evento.getLocalidadVIP().getCapacidad();
                double precioGeneral = evento.getLocalidadGeneral().getPrecio();
                double precioVIP = evento.getLocalidadVIP().getPrecio();
                String info = "Localidad General:\n- Precio: " + precioGeneral + "\n- Capacidad max:" + capacidadGeneral
                        + "\n";
                String info2 = "Localidad VIP: \n- Precio: " + precioVIP + "\n- Capacidad max:" + capacidadVIP;
                infota.setText(info + info2);
            }

        });
        propEstrella.addListener((observable, oldValue, cantidad) -> {
            switch (cantidad) {
                case 1:
                    estrella1.setFill(Color.YELLOW);
                    estrella2.setFill(Color.WHITE);
                    estrella3.setFill(Color.WHITE);
                    estrella4.setFill(Color.WHITE);
                    estrella5.setFill(Color.WHITE);
                    break;
                case 2:
                    estrella1.setFill(Color.YELLOW);
                    estrella2.setFill(Color.YELLOW);
                    estrella3.setFill(Color.WHITE);
                    estrella4.setFill(Color.WHITE);
                    estrella5.setFill(Color.WHITE);
                    break;
                case 3:
                    estrella1.setFill(Color.YELLOW);
                    estrella2.setFill(Color.YELLOW);
                    estrella3.setFill(Color.YELLOW);
                    estrella4.setFill(Color.WHITE);
                    estrella5.setFill(Color.WHITE);
                    break;
                case 4:
                    estrella1.setFill(Color.YELLOW);
                    estrella2.setFill(Color.YELLOW);
                    estrella3.setFill(Color.YELLOW);
                    estrella4.setFill(Color.YELLOW);
                    estrella5.setFill(Color.WHITE);
                    break;
                case 5:
                    estrella1.setFill(Color.YELLOW);
                    estrella2.setFill(Color.YELLOW);
                    estrella3.setFill(Color.YELLOW);
                    estrella4.setFill(Color.YELLOW);
                    estrella5.setFill(Color.YELLOW);
                    break;
                default:
            }
        });
        // propEstrella.setValue(cliente.getResena().getPuntaje());
        // descripcionresenata.setText(cliente.getResena().getDescripcion());

    }
}
