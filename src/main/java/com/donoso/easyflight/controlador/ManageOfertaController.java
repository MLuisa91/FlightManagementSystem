package com.donoso.easyflight.controlador;

import com.donoso.easyflight.contexto.UsuarioHolder;
import com.donoso.easyflight.http.HttpClient;
import com.donoso.easyflight.pojos.Oferta;
import com.donoso.easyflight.pojos.Usuario;
import com.donoso.easyflight.pojos.Vuelo;
import com.donoso.easyflight.utils.URLApi;
import com.donoso.easyflight.utils.Utiles;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ManageOfertaController implements Initializable {
    public TableView<Oferta> tableViewOfertas;
    public TableColumn<Oferta, Integer> column_IdOferta;
    public TableColumn<Oferta, String> column_NombreOferta;
    public TableColumn<Oferta, String> column_Descripcion;
    public TableColumn<Oferta, Double> column_Descuento;
    public TableColumn<Oferta, LocalDate> column_FechaInicio;
    public TableColumn<Oferta, LocalDate> column_FechaFinal;
    public TextField txtSearchOferta;
    public Button buttont_AddOferta;
    public Button buttont_UpdateOferta;
    public Button buttont_ClearOferta;
    public Button buttont_DeleteOferta;
    public TextField txt_NombreOferta;
    public TextArea txt_DescripcionOferta;
    public TextField txt_DescuentoOferta;
    public DatePicker date_FechaInicio;
    public DatePicker date_FechaFinal;
    public TextField txt_IdOferta;
    public ComboBox<Vuelo> combo_VueloOferta;
    public TableColumn<Oferta, String> column_Vuelo;

    public void addOferta(ActionEvent event) throws Exception {
        Oferta oferta = recogerDatos();
        HttpClient<Oferta, Oferta> client = new HttpClient<>(Oferta.class);
        if(oferta != null){
            try {
                if (client.execute(URLApi.API_OFERTA_BY_ID.replace("{id}", oferta.getId().toString()), null, "GET") == null) {
                    client.execute(URLApi.API_OFERTA_CREATE, oferta, "POST");
                    mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
                    limpiarCampos();
                    reloadScreenOfertas(event);
                } else {
                    mostrarMensajes("Error", "Se ha producido un error en la creación de la nueva oferta", Alert.AlertType.ERROR);
                }
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        inicializaTableView();

    }

    public void updateOferta(ActionEvent event) throws Exception {
        Oferta oferta = recogerDatos();
        HttpClient<Oferta, Oferta> client = new HttpClient<>(Oferta.class);
        try {
            if (client.execute(URLApi.API_OFERTA_BY_ID.replace("{id}", oferta.getId().toString()), null, "GET") != null) {
                client.execute(URLApi.API_OFERTA_UPDATE, oferta, "PUT");
                mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
                limpiarCampos();
                reloadScreenOfertas(event);
            } else {
                mostrarMensajes("Error", "Se ha producido un error en la actualización de la oferta.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        inicializaTableView();
    }

    private Oferta recogerDatos() {
        boolean correcto = true;
        String errores = "Se han producido los siguientes errores:\n \n";
        Oferta oferta = null;

        Integer id = Integer.parseInt(txt_IdOferta.getText());
        if (id == 0) {
            errores += "- El campo ID es obligatorio.\n";
            correcto = false;
        }

        String nombre = txt_NombreOferta.getText();
        if (nombre.isEmpty()) {
            errores += "- El campo nombre es obligatorio.\n";
            correcto = false;
        }

        String descripcion = txt_DescripcionOferta.getText();
        if (descripcion.isEmpty()) {
            errores += "- El campo descripción es obligatorio.\n";
            correcto = false;
        }

        Double descuento = Double.parseDouble(txt_DescuentoOferta.getText());
        if(descuento == 0){
            errores += "El campo descuento es obligatorio.\n";
            correcto = false;
        }

        LocalDate fechaInicio = date_FechaInicio.getValue();
        if(fechaInicio == null){
            errores += "El campo fecha inicio es obligatorio.\n";
            correcto = false;
        }else{
            if(!Utiles.validarFecha(fechaInicio)){
                errores += "La fecha no debe ser anterior a la actual\n";
                correcto = false;
            }
        }

        LocalDate fechaFinal = date_FechaFinal.getValue();
        if(fechaFinal == null){
            errores += "El campo fecha final es obligatorio.\n";
            correcto = false;
        }

        if (fechaInicio != null && fechaFinal != null)
            if (!Utiles.compararFechas(fechaInicio, fechaFinal)) {
                errores += "La fecha de inicio no debe ser poserior a la final. \n";
                correcto = false;
            }

        Vuelo vuelo = combo_VueloOferta.getValue();
        if (vuelo == null) {
            errores += "- El campo Vuelo es obligatorio.";
            correcto = false;
        }


        if (correcto) {
            oferta = new Oferta(id, nombre, descripcion, descuento, fechaInicio, fechaFinal, vuelo);
        } else {
            mostrarMensajes("Error", errores, Alert.AlertType.ERROR);
        }

        return oferta;

    }

    public void cleanFields(ActionEvent event) {
        limpiarCampos();
    }

    public void deleteOferta(ActionEvent event) throws Exception {
        Oferta ofertaSeleccionada = tableViewOfertas.getSelectionModel().getSelectedItem();
        HttpClient<Oferta, Oferta> client = new HttpClient<>(Oferta.class);
        if (ofertaSeleccionada != null) {
            try {
                Oferta oferta = client.execute(URLApi.API_OFERTA_BY_ID.replace("{id}", ofertaSeleccionada.getId().toString()), null, "GET");
                if (oferta != null) {
                    client.execute(URLApi.API_OFERTA_DELETE.replace("{id}", ofertaSeleccionada.getId().toString()), ofertaSeleccionada, "DELETE");
                    mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
                    limpiarCampos();
                    reloadScreenOfertas(event);
                } else {
                    mostrarMensajes("Error", "Se ha producido un error en el borrado de la ofeta.", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        inicializaTableView();

    }


    private void limpiarCampos() {
        txt_IdOferta.clear();
        txt_NombreOferta.clear();
        txt_DescripcionOferta.clear();
        txt_DescuentoOferta.clear();
        date_FechaFinal.setValue(null);
        date_FechaInicio.setValue(null);
        combo_VueloOferta.setValue(null);
    }

    private void mostrarMensajes(String titulo, String mensaje, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void reloadScreenOfertas(ActionEvent event) throws IOException {
        // cerrar la ventana actual
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        // Cargar la ventana de gestión de ofertas
        Parent root = FXMLLoader.load(getClass().getResource("/com/donoso/easyflight/vista/Main.fxml"));

        // Crear un nuevo Stage para la segunda ventana
        Stage segundaVentana = new Stage();
        segundaVentana.setTitle("Gestión de Ofertas");
        Scene escena = new Scene(root);
        segundaVentana.setScene(escena);

        // Obtener el controlador de la segunda ventana si es necesario

        // Mostrar la segunda ventana
        segundaVentana.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UsuarioHolder holder = UsuarioHolder.getInstance();
        Usuario usuario = holder.getUsuario();


        try {
            inicializaTableView();
            inicializaComboVuelos();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void inicializaComboVuelos() throws Exception {
        HttpClient<Vuelo, Vuelo[]> client = new HttpClient<>(Vuelo[].class);
        Vuelo[] lista = client.execute(URLApi.API_VUELO_SEARCH, new Vuelo(), "POST");
        ObservableList<Vuelo> vuelos = FXCollections.observableArrayList(lista);
        combo_VueloOferta.setItems(vuelos);
        combo_VueloOferta.setConverter(new Vuelo());
    }

    private void inicializaTableView() throws Exception {
        HttpClient<Oferta, Oferta[]> client = new HttpClient<>(Oferta[].class);
        Oferta[] lista = client.execute(URLApi.API_OFERTA_SEARCH, new Oferta(), "POST");

        ObservableList<Oferta> ofertas = FXCollections.observableArrayList(lista);
        tableViewOfertas.setItems(ofertas);
        column_IdOferta.setCellValueFactory(new PropertyValueFactory<>("id"));
        column_NombreOferta.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        column_Descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        column_Descuento.setCellValueFactory(new PropertyValueFactory<>("descuento"));
        column_FechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        column_FechaFinal.setCellValueFactory(new PropertyValueFactory<>("fechaFinal"));
        column_Vuelo.setCellValueFactory(ofertaStringCellDataFeatures -> new SimpleStringProperty(ofertaStringCellDataFeatures.getValue().getVuelo().getId()));

    }

    public void handleRowSelection(MouseEvent mouseEvent) {
        Oferta ofertaSeleccionada = tableViewOfertas.getSelectionModel().getSelectedItem();
        if (ofertaSeleccionada != null) {
            txt_IdOferta.setText(ofertaSeleccionada.getId().toString());
            txt_NombreOferta.setText(ofertaSeleccionada.getNombre());
            txt_DescripcionOferta.setText(ofertaSeleccionada.getDescripcion());
            txt_DescuentoOferta.setText(ofertaSeleccionada.getDescuento().toString());
            date_FechaInicio.setValue(ofertaSeleccionada.getFechaInicio());
            date_FechaFinal.setValue(ofertaSeleccionada.getFechaFinal());
            combo_VueloOferta.setValue(ofertaSeleccionada.getVuelo());
        }
    }

    public void buscar(KeyEvent keyEvent) throws Exception {
        String texto = txtSearchOferta.getText();
        HttpClient<Oferta, Oferta[]> client = new HttpClient<>(Oferta[].class);
        if (texto.length() >= 3) {
            Oferta oferta = obtenenerCondicionesWhere(texto);

            Oferta[] lista = client.execute(URLApi.API_OFERTA_SEARCH, oferta, "POST");
            ObservableList<Oferta> ofertasFiltradas = FXCollections.observableArrayList(lista);

            tableViewOfertas.setItems(ofertasFiltradas);
            column_IdOferta.setCellValueFactory(new PropertyValueFactory<>("id"));
            column_NombreOferta.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            column_Descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            column_Descuento.setCellValueFactory(new PropertyValueFactory<>("descuento"));
            column_FechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
            column_FechaFinal.setCellValueFactory(new PropertyValueFactory<>("fechaFinal"));
        } else {
            inicializaTableView();
        }
    }

    private Oferta obtenenerCondicionesWhere(String texto) {
        Integer id = null;

        if(Utiles.validarSiNumero(texto)){
            id = Integer.parseInt(texto);
        }

        return new Oferta(id,texto,texto);
    }
}
