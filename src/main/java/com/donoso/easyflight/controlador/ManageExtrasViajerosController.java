package com.donoso.easyflight.controlador;

import com.donoso.easyflight.contexto.UsuarioHolder;
import com.donoso.easyflight.http.HttpClient;
import com.donoso.easyflight.pojos.*;
import com.donoso.easyflight.utils.URLApi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageExtrasViajerosController implements Initializable {
    public TableView<Extra> tableViewExtras;
    public TableColumn<Extra, String> columnNombreExtra;
    public TableColumn<Extra, String> columnDescripcionExtra;
    public TableColumn<Extra, Double> columnPrecioExtra;
    public ComboBox<Extra> comboExtras;
    public TableView<Viajero> tableViewViajero;
    public TableColumn<Viajero, String> columnNombreViajero;
    public TableColumn<Viajero, String> columnApellidosViajero;
    public TableColumn<Viajero, String> columnDniViajero;
    public TextField txtNombreViajero;
    public TextField txtDniViajero;
    public TextField txtApellidosViajero;
    public Button buttonAddExtra;
    public Button buttonUpdateViajero;

    public void handleRowSelection(MouseEvent mouseEvent) {
    }

    public void addExtra(ActionEvent event) {
    }

    public void updateViajero(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        HttpClient<Viajero, Viajero[]> client = new HttpClient<>(Viajero[].class);
        Viajero[] lista = client.execute(URLApi.API_VIAJERO_SEARCH, new Viajero(), "POST");

        ObservableList<Viajero> viajeros = FXCollections.observableArrayList(lista);

        this.tableViewViajero.setItems(viajeros);
        columnNombreViajero.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellidosViajero.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnDniViajero.setCellValueFactory(new PropertyValueFactory<>("dni"));

    }

    private void inicializaComboExtras() throws Exception {
        HttpClient<Extra, Extra[]> client = new HttpClient<>(Extra[].class);
        Extra[] lista = client.execute(URLApi.API_EXTRA_SEARCH, new Extra(), "POST");
        ObservableList<Extra> aviones = FXCollections.observableArrayList(lista);

        comboExtras.setItems(aviones);
        comboExtras.setConverter(new Extra());

    }

    private void inicializaTableViewExtras() throws Exception {
        HttpClient<Extra, Extra[]> client = new HttpClient<>(Extra[].class);
        Extra[] lista = client.execute(URLApi.API_VIAJERO_SEARCH, new Extra(), "POST");

        ObservableList<Extra> vuelos = FXCollections.observableArrayList(lista);

        this.tableViewExtras.setItems(vuelos);
        columnNombreExtra.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnDescripcionExtra.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnPrecioExtra.setCellValueFactory(new PropertyValueFactory<>("coste"));
    }
}
