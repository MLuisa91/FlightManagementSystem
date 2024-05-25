package com.donoso.easyflight.controlador;

import com.donoso.easyflight.contexto.UsuarioHolder;
import com.donoso.easyflight.http.HttpClient;
import com.donoso.easyflight.pojos.Reserva;
import com.donoso.easyflight.pojos.Respaldo;
import com.donoso.easyflight.pojos.Usuario;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ManageRespaldoController implements Initializable {
    public TableView<Respaldo> tableViewRespaldo;

    public TextField txtSearchRespaldo;
    public Button buttonEliminarRespaldo;
    public Button buttonRestaurarRespaldo;
    public TableColumn<Respaldo, Integer> columnIdRespaldo;
    public TableColumn<Respaldo, String> columnNombreRespaldo;
    public TableColumn<Respaldo, LocalDate> columnFechaGeneradaRespaldo;
    public TableColumn<Respaldo, LocalDate> columnFechaRestauradaRespaldo;
    public TableColumn<Respaldo, Boolean> columnGeneradaRespaldo;

    public void buscar(KeyEvent keyEvent) throws Exception {
        String texto = txtSearchRespaldo.getText();
        HttpClient<Respaldo, Respaldo[]> client = new HttpClient<>(Respaldo[].class);
        if (texto.length() >= 3) {
            Respaldo respaldo = obtenenerCondicionesWhere(texto);

            Respaldo[] lista = client.execute(URLApi.API_RESPALDO_SEARCH, respaldo, "POST");
            ObservableList<Respaldo> respaldos = FXCollections.observableArrayList(lista);
            tableViewRespaldo.setItems(respaldos);
            columnIdRespaldo.setCellValueFactory(new PropertyValueFactory<>("id"));
            columnNombreRespaldo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            columnFechaGeneradaRespaldo.setCellValueFactory(new PropertyValueFactory<>("fechaGenerada"));
            columnFechaRestauradaRespaldo.setCellValueFactory(new PropertyValueFactory<>("fechaRestaurada"));
            columnGeneradaRespaldo.setCellValueFactory(new PropertyValueFactory<>("generada"));
        } else {
            inicializaTableView();
        }

    }

    private Respaldo obtenenerCondicionesWhere(String texto) {
        Integer id = null;
        LocalDate fecha = null;
        if(Utiles.validarSiNumero(texto)){
            id = Integer.parseInt(texto);
        }

        if(Utiles.validarSiFecha(texto)){
            fecha = Utiles.convertirADate(texto);
        }

        return new Respaldo(id, texto, fecha, fecha, null);
    }

    public void eliminarRespaldo(ActionEvent event) throws Exception {
        Respaldo respaldoSeleccionado = tableViewRespaldo.getSelectionModel().getSelectedItem();
        HttpClient<Respaldo, Respaldo> client = new HttpClient<>(Respaldo.class);
        if (respaldoSeleccionado != null) {
            try {
                Respaldo respaldo = client.execute(URLApi.API_RESPALDO_BY_ID.replace("{id}", respaldoSeleccionado.getId().toString()), null, "GET");
                if (respaldo != null) {
                    client.execute(URLApi.API_RESPALDO_DELETE.replace("{id}", respaldoSeleccionado.getId().toString()), respaldoSeleccionado, "DELETE");
                    mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
                    reloadScreenRespaldo(event);
                } else {
                    mostrarMensajes("Error", "Se ha producido un error en el borrado del respaldo seleccionado.", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        inicializaTableView();


    }

    public void restaurarRespaldo(ActionEvent event) throws Exception {
        Respaldo respaldoSeleccionado = tableViewRespaldo.getSelectionModel().getSelectedItem();
        HttpClient<Respaldo, Respaldo> client = new HttpClient<>(Respaldo.class);
        if (respaldoSeleccionado != null) {
            try {
                Respaldo respaldo = client.execute(URLApi.API_RESPALDO_BY_ID.replace("{id}", respaldoSeleccionado.getId().toString()), null, "GET");
                if (respaldo != null) {
                    client.execute(URLApi.API_RESPALDO_RESTORE.replace("{id}", respaldoSeleccionado.getId().toString()), respaldoSeleccionado, "DELETE");
                    mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
                    reloadScreenRespaldo(event);
                } else {
                    mostrarMensajes("Error", "Se ha producido un error al restaurar la base de datos.", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        inicializaTableView();
    }

    private void reloadScreenRespaldo(ActionEvent event) throws IOException {
        // cerrar la ventana actual
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        // Cargar la ventana de gestión de ofertas
        Parent root = FXMLLoader.load(getClass().getResource("/com/donoso/easyflight/vista/Main.fxml"));

        // Crear un nuevo Stage para la segunda ventana
        Stage segundaVentana = new Stage();
        segundaVentana.setTitle("Gestión de Respaldo");
        Scene escena = new Scene(root);
        segundaVentana.setScene(escena);

        // Obtener el controlador de la segunda ventana si es necesario

        // Mostrar la segunda ventana
        segundaVentana.show();

    }

    private void mostrarMensajes(String titulo, String mensaje, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UsuarioHolder holder = UsuarioHolder.getInstance();
        Usuario usuario = holder.getUsuario();

        try {
            inicializaTableView();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void inicializaTableView() throws Exception {
        HttpClient<Respaldo, Respaldo[]> client = new HttpClient<>(Respaldo[].class);
        Respaldo[] lista = client.execute(URLApi.API_RESPALDO_SEARCH, new Respaldo(), "POST");

        ObservableList<Respaldo> respaldos = FXCollections.observableArrayList(lista);
        tableViewRespaldo.setItems(respaldos);
        columnIdRespaldo.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNombreRespaldo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnFechaGeneradaRespaldo.setCellValueFactory(new PropertyValueFactory<>("fechaGenerada"));
        columnFechaRestauradaRespaldo.setCellValueFactory(new PropertyValueFactory<>("fechaRestaurada"));
        columnGeneradaRespaldo.setCellValueFactory(new PropertyValueFactory<>("generada"));
    }
}
