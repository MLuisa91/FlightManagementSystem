package com.donoso.easyflight.controlador;

import com.donoso.easyflight.contexto.UsuarioHolder;
import com.donoso.easyflight.crud.CrudAviones;
import com.donoso.easyflight.pojos.Avion;
import com.donoso.easyflight.pojos.Usuario;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private CrudAviones crudAviones;
    private ObservableList<Avion> listaAviones;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UsuarioHolder holder = UsuarioHolder.getInstance();
        Usuario usuario = holder.getUsuario();


        if (!usuario.getIsAdministrador()) {
            deshabilitarCampos();
        }

        inicializaTableView();
    }

    private void deshabilitarCampos() {
        button_ManagementPlaneAdd.setDisable(true);
        button_ManagementPlaneClear.setDisable(true);
        button_ManagementPlaneDelete.setDisable(true);
        button_ManagementPlaneUpdate.setDisable(true);
        txt_IdPlane.setEditable(false);
        txt_PlaneModel.setEditable(false);
        txt_PasengersNumber.setEditable(false);

    }

    private void inicializaTableView() {
        crudAviones = new CrudAviones();
        listaAviones = crudAviones.listAllObservable();
        List<Avion> lista = crudAviones.listAll();
        ObservableList<Avion> aviones = FXCollections.observableArrayList(lista);
        ;
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

    public void addPlane(ActionEvent actionEvent) throws IOException {
        Avion avion = recogerDatos();
        if (avion != null)
            if (crudAviones.findById(avion) == null)
                if (crudAviones.add(avion)) {
                    mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
                    reloadScreenVuelos(actionEvent);
                } else {
                    mostrarMensajes("Error", "Se ha producido un error al dar el alta el avion.", Alert.AlertType.ERROR);
                }
            else
                mostrarMensajes("Error", "El avión con id " + avion.getId() + " ya existe en base de datos", Alert.AlertType.ERROR);

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

    public void updatePlane(ActionEvent actionEvent) throws IOException {
        Avion avion = recogerDatos();
        if (avion != null && crudAviones.update(avion)) {
            mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
            reloadScreenVuelos(actionEvent);
        } else {
            mostrarMensajes("Error", "Se ha producido un error en la actualización del avion.", Alert.AlertType.ERROR);
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

    public void deletePlane(ActionEvent actionEvent) throws IOException {
        Avion avionSeleccionado = tableViewPlanes.getSelectionModel().getSelectedItem();
        if (avionSeleccionado != null && crudAviones.delete(avionSeleccionado)) {
            mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
            reloadScreenVuelos(actionEvent);
        } else {
            mostrarMensajes("Error", "Se ha producido un error en el borrado del avion. " +
                    "Compruebe que no está asociado a ningún vuelo.", Alert.AlertType.ERROR);
        }
        inicializaTableView();
    }

    private void mostrarMensajes(String titulo, String mensaje, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void reloadScreenVuelos(ActionEvent event) throws IOException {
        // cerrar la ventana actual
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        // Cargar la ventana de gestión de vuelos
        Parent root = FXMLLoader.load(getClass().getResource("/com/donoso/easyflight/vista/Main.fxml"));

        // Crear un nuevo Stage para la segunda ventana
        Stage segundaVentana = new Stage();
        segundaVentana.setTitle("Gestión de Vuelos");
        Scene escena = new Scene(root);
        segundaVentana.setScene(escena);

        // Obtener el controlador de la segunda ventana si es necesario

        // Mostrar la segunda ventana
        segundaVentana.show();

    }

    public void buscar(KeyEvent keyEvent) {
        String texto = txtSearchPlanes.getText();
        if (texto.length() >= 3) {
            Map<String, String> sqlWhere = obtenenerCondicionesWhere(texto);

            List<Avion> lista = crudAviones.searchAviones(sqlWhere);
            ObservableList<Avion> avionesFiltrados = FXCollections.observableArrayList(lista);
            ;
            tableViewPlanes.setItems(avionesFiltrados);
            column_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
            column_modelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
            column_pasajeros.setCellValueFactory(new PropertyValueFactory<>("pasajeros"));
        } else {
            tableViewPlanes.setItems(listaAviones);
        }
    }

    private Map<String, String> obtenenerCondicionesWhere(String texto) {
        Map<String, String> sqlWhere = new HashMap<>();
        sqlWhere.put("id", "%".concat(texto).concat("%"));
        sqlWhere.put("modelo", "%".concat(texto).concat("%"));
        sqlWhere.put("pasajeros", "%".concat(texto).concat("%"));

        return sqlWhere;
    }
}
