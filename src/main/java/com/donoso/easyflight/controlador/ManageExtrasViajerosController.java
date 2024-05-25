package com.donoso.easyflight.controlador;

import com.donoso.easyflight.contexto.ReservaHolder;
import com.donoso.easyflight.contexto.UsuarioHolder;
import com.donoso.easyflight.http.HttpClient;
import com.donoso.easyflight.pojos.*;
import com.donoso.easyflight.utils.URLApi;
import com.donoso.easyflight.utils.Utiles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class ManageExtrasViajerosController implements Initializable {
    public TableView<Extra> tableViewExtras;
    public TableColumn<Extra, String> columnNombreExtra;
    public TableColumn<Extra, String> columnDescripcionExtra;
    public TableColumn<Extra, Double> columnPrecioExtra;
    public ComboBox<Extra> comboExtras;
    public List<Extra> extrasLista;
    public List<Viajero> viajerosList;
    public TableView<Viajero> tableViewViajero;
    public TableColumn<Viajero, String> columnNombreViajero;
    public TableColumn<Viajero, String> columnApellidosViajero;
    public TableColumn<Viajero, String> columnDniViajero;
    public TextField txtNombreViajero;
    public TextField txtDniViajero;
    public TextField txtApellidosViajero;
    public Button buttonAddExtra;
    public Button buttonUpdateViajero;
    public Button button_Confirmar;
    public TableColumn<Viajero, LocalDate> columnFechaNacimientoViajero;
    public DatePicker dateFechaNacimiento;

    private Reserva reservaInicial;

    Viajero viajeroSeleccionado;

    public void handleRowSelection(MouseEvent mouseEvent) {
        viajeroSeleccionado = tableViewViajero.getSelectionModel().getSelectedItem();
        if(viajeroSeleccionado != null){
            txtApellidosViajero.setText(viajeroSeleccionado.getApellidos());
            txtDniViajero.setText(viajeroSeleccionado.getDni());
            txtNombreViajero.setText(viajeroSeleccionado.getNombre());
        }

    }

    public void addExtra(ActionEvent event) throws Exception {
        Extra extra = comboExtras.getValue();
        if(extra != null){
            extrasLista.add(extra);

        }
        cargaTableViewExtras();
    }

    public void updateViajero(ActionEvent event) throws Exception {
        Viajero viajero = recogerDatos();
        if(viajero != null){
            viajerosList.remove(viajeroSeleccionado);
            viajerosList.add(viajero);
        }
        cargaTableViewViajeros();
    }

    private Viajero recogerDatos() {
        Viajero viajero = null;
        Boolean correcto = true;
        String errores = "Se han producido los siguientes errores:\n";

        String dni = txtDniViajero.getText();
        if (dni.isEmpty()) {
            errores += "- El campo ID es obligatorio.\n";
            correcto = false;
        } else {
            if (!Utiles.validarDNI(dni)) {
                errores += "- El formato del DNI no es correcto.\n";
                correcto = false;
            }
        }

        String nombre = txtNombreViajero.getText();
        if (nombre.isEmpty()) {
            errores += "- El campo nombre es obligatorio.\n";
            correcto = false;
        }

        String apellidos = txtApellidosViajero.getText();
        if (apellidos.isEmpty()) {
            errores += "- El campo apellidos es obligatorio.\n";
            correcto = false;
        }

        LocalDate fechaNacimiento = dateFechaNacimiento.getValue();
        if (fechaNacimiento == null) {
            errores += "- El campo fecha de nacimiento es obligatorio.\n";
            correcto = false;
        }

        if (correcto) {
            viajero = new Viajero(null, dni, nombre, apellidos, fechaNacimiento);
        } else {
            mostrarMensajes("Error", errores, Alert.AlertType.ERROR);
        }

        return viajero;

    }

    private void mostrarMensajes(String titulo, String mensaje, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public Reserva getReservaActualizada(){
        return this.reservaInicial;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReservaHolder holderReserva = ReservaHolder.getInstance();
        reservaInicial = holderReserva.getReserva();

        UsuarioHolder holder = UsuarioHolder.getInstance();
        Usuario usuario = holder.getUsuario();


        try {
            inicializaTableViewExtras();
            inicializaTableViewViajeros();
            inicializaComboExtras();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void inicializaTableViewViajeros() throws Exception {
        Set<ReservaViajero> viajero = reservaInicial.getReservaViajeros();
        viajerosList = viajero.stream().map(v -> v.getViajero()).collect(Collectors.toList());
        cargaTableViewViajeros();
    }


    private void cargaTableViewViajeros(){
        ObservableList<Viajero> viajeros = FXCollections.observableArrayList(viajerosList);

        this.tableViewViajero.setItems(viajeros);
        columnNombreViajero.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellidosViajero.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnDniViajero.setCellValueFactory(new PropertyValueFactory<>("dni"));

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItemEliminar = new MenuItem("Eliminar");
        menuItemEliminar.setOnAction(event -> {
            tableViewViajero.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    Viajero viajeroSelect = tableViewViajero.getSelectionModel().getSelectedItem();
                    if (viajeroSelect != null) {
                        viajerosList.remove(viajeroSelect);
                        tableViewViajero.getItems().remove(viajeroSelect);
                    }
                    contextMenu.show(tableViewViajero, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                }
            });
        });
        contextMenu.getItems().add(menuItemEliminar);

        tableViewViajero.setContextMenu(contextMenu);

    }

    private void inicializaComboExtras() throws Exception {
        HttpClient<Extra, Extra[]> client = new HttpClient<>(Extra[].class);
        Extra[] lista = client.execute(URLApi.API_EXTRA_SEARCH, new Extra(), "POST");
        ObservableList<Extra> extras = FXCollections.observableArrayList(lista);

        comboExtras.setItems(extras);
        comboExtras.setConverter(new Extra());

    }

    private void inicializaTableViewExtras() throws Exception {
        Set<ReservaExtra> extras = reservaInicial.getReservaExtras();
        extrasLista = extras.stream().map(ex -> ex.getExtra()).collect(Collectors.toList());
        cargaTableViewExtras();
    }


    private void cargaTableViewExtras() throws Exception {

        ObservableList<Extra> extrasListaTabla = FXCollections.observableArrayList(this.extrasLista);

        this.tableViewExtras.setItems(extrasListaTabla);
        columnNombreExtra.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnDescripcionExtra.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnPrecioExtra.setCellValueFactory(new PropertyValueFactory<>("coste"));

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItemEliminar = new MenuItem("Eliminar");
        menuItemEliminar.setOnAction(event -> {
            tableViewExtras.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    Extra extra = tableViewExtras.getSelectionModel().getSelectedItem();
                    if (extra != null) {
                        this.extrasLista.remove(extra);
                        tableViewExtras.getItems().remove(extra);
                    }
                    contextMenu.show(tableViewExtras, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                }
            });
        });
        contextMenu.getItems().add(menuItemEliminar);

        tableViewExtras.setContextMenu(contextMenu);
    }

    public void confirmar(ActionEvent event) {
        Set<ReservaExtra> extras = new HashSet<>();
        for (Extra ex: extrasLista) {
            ReservaExtra nuevo = new ReservaExtra(new ReservaExtraPK(reservaInicial.getId(), ex.getId()), new Reserva(reservaInicial.getId()),ex);
            extras.add(nuevo);
        }
        reservaInicial.setReservaExtras(extras);

        Set<ReservaViajero> viajeros = new HashSet<>();
        for(Viajero v: viajerosList){
            ReservaViajero nuevo = new ReservaViajero(new ReservaViajeroPK(reservaInicial.getId(), v.getId()), new Reserva(reservaInicial.getId()), v);
            viajeros.add(nuevo);
        }
        reservaInicial.setReservaViajeros(viajeros);

        closeWindow(event);

    }

    private void closeWindow(ActionEvent event) {
        ReservaHolder holder = ReservaHolder.getInstance();
        holder.setReserva(reservaInicial);


        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

    }
}
