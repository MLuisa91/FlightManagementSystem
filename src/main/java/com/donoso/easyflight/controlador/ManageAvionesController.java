package com.donoso.easyflight.controlador;

import com.donoso.easyflight.contexto.UsuarioHolder;
import com.donoso.easyflight.http.HttpClient;
import com.donoso.easyflight.pojos.Avion;
import com.donoso.easyflight.pojos.Usuario;
import com.donoso.easyflight.utils.URLApi;
import com.donoso.easyflight.utils.Utiles;
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
import java.util.ResourceBundle;

public class ManageAvionesController implements Initializable {
    public TextField txt_IdPlane;
    public TextField txt_PasengersNumber;
    public TextField txt_PlaneModel;
    public Button button_ManagementPlaneAdd;
    public Button button_ManagementPlaneUpdate;
    public Button button_ManagementPlaneClear;
    public Button button_ManagementPlaneDelete;
    public TextField txtSearchPlanes;
    public TableColumn<Avion, String> column_Id;
    public TableColumn<Avion, String> column_modelo;
    public TableColumn<Avion, Integer> column_pasajeros;
    public TableView<Avion> tableViewPlanes;

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
        HttpClient<Avion, Avion[]> client = new HttpClient<>(Avion[].class);
        Avion[] lista = client.execute(URLApi.API_AVION_SEARCH,new Avion(), "POST");

        ObservableList<Avion> aviones = FXCollections.observableArrayList(lista);
        this.tableViewPlanes.setItems(aviones);
        column_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        column_modelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        column_pasajeros.setCellValueFactory(new PropertyValueFactory<>("pasajeros"));
    }

    public void handleRowSelection(MouseEvent mouseEvent) {
        Avion avionSeleccionado = tableViewPlanes.getSelectionModel().getSelectedItem();
        if (avionSeleccionado != null) {
            txt_IdPlane.setText(avionSeleccionado.getId());
            txt_PlaneModel.setText(avionSeleccionado.getModelo());
            txt_PasengersNumber.setText(avionSeleccionado.getPasajeros().toString());
        }
    }

    public void addPlane(ActionEvent actionEvent) throws Exception {
        Avion avion = recogerDatos();
        HttpClient<Avion, Avion[]> client = new HttpClient<>(Avion[].class);
        if (avion != null)
            try {
                if (client.execute(URLApi.API_AVION_BY_ID.replace("{id}", avion.getId()), null, "GET") == null) {
                    client.execute(URLApi.API_AVION_CREATE, avion, "POST");
                    mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
                    limpiarCampos();
                    reloadScreenAviones(actionEvent);
                } else {
                    mostrarMensajes("Error", "El avión con id " + avion.getId() + " ya existe en base de datos", Alert.AlertType.ERROR);
                }
            }catch (Exception e) {
                throw new RuntimeException(e);
            }


        inicializaTableView();

    }

    private Avion recogerDatos() {
        boolean correcto = true;
        String errores = "Se han producido los siguientes errores:\n \n";
        Avion avion = null;

        String id = txt_IdPlane.getText();
        if (id.isEmpty()) {
            errores += "- El campo ID es obligatorio.\n";
            correcto = false;
        } else {
            if (!Utiles.validarIdAvion(id)) {
                errores += "- El formato del id no es correcto.\n";
                correcto = false;
            }
        }

        String modelo = txt_PlaneModel.getText();
        if (modelo.isEmpty()) {
            errores += "- El campo modelo es obligatorio.\n";
            correcto = false;
        }

        String pasajeros = txt_PasengersNumber.getText();
        if (pasajeros.isEmpty()) {
            errores += "- El campo pasajeros es obligatorio.";
            correcto = false;
        }

        if (correcto) {
            avion = new Avion(id, modelo, Integer.parseInt(pasajeros));
        } else {
            mostrarMensajes("Error", errores, Alert.AlertType.ERROR);
        }

        return avion;

    }

    public void updatePlane(ActionEvent actionEvent) throws Exception {
        Avion avion = recogerDatos();
        HttpClient<Avion, Avion> client = new HttpClient<>(Avion.class);
        try {
            if (client.execute(URLApi.API_AVION_BY_ID.replace("{id}", avion.getId()), null, "GET") != null) {
                client.execute(URLApi.API_AVION_UPDATE, avion, "PUT");
                mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
                limpiarCampos();
                reloadScreenAviones(actionEvent);
            } else {
                mostrarMensajes("Error", "Se ha producido un error en la actualización del avion.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        inicializaTableView();

    }

    public void clearPlane(ActionEvent actionEvent) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txt_IdPlane.clear();
        txt_PlaneModel.clear();
        txt_PasengersNumber.clear();

    }

    public void deletePlane(ActionEvent actionEvent) throws Exception {
        Avion avionSeleccionado = tableViewPlanes.getSelectionModel().getSelectedItem();
        HttpClient<Avion, Avion> client = new HttpClient<>(Avion.class);
        if (avionSeleccionado != null) {
            try {
                Avion avion = client.execute(URLApi.API_AVION_BY_ID.replace("{id}", avionSeleccionado.getId()), null, "GET");
                if (avion != null) {
                    client.execute(URLApi.API_AVION_DELETE.replace("{id}", avionSeleccionado.getId()), avionSeleccionado, "DELETE");
                    mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
                    limpiarCampos();
                    reloadScreenAviones(actionEvent);
                } else {
                    mostrarMensajes("Error", "Se ha producido un error en el borrado del avion. " +
                            "Compruebe que no está asociado a ningún vuelo.", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        inicializaTableView();
    }

    private void mostrarMensajes(String titulo, String mensaje, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void reloadScreenAviones(ActionEvent event) throws IOException {
        // cerrar la ventana actual
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        // Cargar la ventana de gestión de aviones
        Parent root = FXMLLoader.load(getClass().getResource("/com/donoso/easyflight/vista/Main.fxml"));

        // Crear un nuevo Stage para la segunda ventana
        Stage segundaVentana = new Stage();
        segundaVentana.setTitle("Gestión de Aviones");
        Scene escena = new Scene(root);
        segundaVentana.setScene(escena);

        // Obtener el controlador de la segunda ventana si es necesario

        // Mostrar la segunda ventana
        segundaVentana.show();

    }

    public void buscar(KeyEvent keyEvent) throws Exception {
        String texto = txtSearchPlanes.getText();
        HttpClient<Avion, Avion[]> client = new HttpClient<>(Avion[].class);
        if (texto.length() >= 3) {
            Avion avion = obtenenerCondicionesWhere(texto);

            Avion[] lista = client.execute(URLApi.API_AVION_SEARCH, avion, "POST");
            ObservableList<Avion> avionesFiltrados = FXCollections.observableArrayList(lista);

            tableViewPlanes.setItems(avionesFiltrados);
            column_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
            column_modelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
            column_pasajeros.setCellValueFactory(new PropertyValueFactory<>("pasajeros"));
        } else {
            inicializaTableView();
        }
    }

    private Avion obtenenerCondicionesWhere(String texto) {
        Integer pasajeros= null;
        if(Utiles.validarSiNumero(texto))
            pasajeros = Integer.parseInt(texto);

        return new Avion(texto,texto,pasajeros);
    }
}
