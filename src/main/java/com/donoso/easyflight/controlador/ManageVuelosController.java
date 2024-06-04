package com.donoso.easyflight.controlador;

import com.donoso.easyflight.contexto.UsuarioHolder;
import com.donoso.easyflight.http.HttpClient;
import com.donoso.easyflight.pojos.Aeropuerto;
import com.donoso.easyflight.pojos.Avion;
import com.donoso.easyflight.pojos.Usuario;
import com.donoso.easyflight.pojos.Vuelo;
import com.donoso.easyflight.utils.URLApi;
import com.donoso.easyflight.utils.Utiles;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ManageVuelosController implements Initializable {

    public ComboBox<Aeropuerto> combo_OrigenFlight;
    public ComboBox<Aeropuerto> combo_DestinoFlight;
    public TableColumn<Vuelo, Double> column_Precio;
    @FXML
    private TextField txt_PrecioFlight;

    @FXML
    private TableView<Vuelo> tableViewFlight;

    @FXML
    private TextField txtSearchFlight;

    @FXML
    private TextField txt_HoraLlegadaFlight;

    @FXML
    private TextField txt_HoraSalidaFlight;

    @FXML
    private TextField txt_IdFlight;

    @FXML
    private TableColumn<Vuelo, String> column_Avion;

    @FXML
    private TableColumn<Vuelo, String> column_Destino;

    @FXML
    private TableColumn<Vuelo, LocalDate> column_Fecha;

    @FXML
    private TableColumn<Vuelo, Time> column_HoraLlegada;

    @FXML
    private TableColumn<Vuelo, Time> column_HoraSalida;

    @FXML
    private TableColumn<Vuelo, String> column_Id;

    @FXML
    private TableColumn<Vuelo, String> column_Origen;

    @FXML
    private ComboBox<Avion> combo_AvionFlight;

    @FXML
    private DatePicker date_FechaSalidaFlight;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UsuarioHolder holder = UsuarioHolder.getInstance();
        Usuario usuario = holder.getUsuario();

        try {
            inicializaTableView();
            inicializaComboBoxAviones();
            inicializaComboBoxAeropuertos();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void inicializaComboBoxAeropuertos() throws Exception {
        HttpClient<Aeropuerto, Aeropuerto[]> client = new HttpClient<>(Aeropuerto[].class);
        Aeropuerto[] lista = client.execute(URLApi.API_AEROPUERTO_SEARCH, new Aeropuerto(), "POST");
        ObservableList<Aeropuerto> aeropuertos = FXCollections.observableArrayList(lista);
        combo_OrigenFlight.setItems(aeropuertos);
        combo_OrigenFlight.setConverter(new Aeropuerto());
        combo_DestinoFlight.setItems(aeropuertos);
        combo_DestinoFlight.setConverter(new Aeropuerto());

    }

    public void inicializaComboBoxAviones() throws Exception {
        HttpClient<Avion, Avion[]> client = new HttpClient<>(Avion[].class);
        Avion[] lista = client.execute(URLApi.API_AVION_SEARCH, new Avion(), "POST");
        ObservableList<Avion> aviones = FXCollections.observableArrayList(lista);

        combo_AvionFlight.setItems(aviones);
        combo_AvionFlight.setConverter(new Avion());
    }

    private void inicializaTableView() throws Exception {
        HttpClient<Vuelo, Vuelo[]> client = new HttpClient<>(Vuelo[].class);
        Vuelo[] lista = client.execute(URLApi.API_VUELO_SEARCH, new Vuelo(), "POST");

        ObservableList<Vuelo> vuelos = FXCollections.observableArrayList(lista);

        this.tableViewFlight.setItems(vuelos);
        column_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        column_Origen.setCellValueFactory(origenStringCellDataFeatures -> new SimpleStringProperty(origenStringCellDataFeatures.getValue().getOrigen().getNombre()));
        column_Destino.setCellValueFactory(origenStringCellDataFeatures -> new SimpleStringProperty(origenStringCellDataFeatures.getValue().getDestino().getNombre()));
        column_Fecha.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        column_HoraSalida.setCellValueFactory(new PropertyValueFactory<>("horaSalida"));
        column_HoraLlegada.setCellValueFactory(new PropertyValueFactory<>("horaLlegada"));
        column_Avion.setCellValueFactory(vueloStringCellDataFeatures -> new SimpleStringProperty(vueloStringCellDataFeatures.getValue().getAvion().getId()));
        column_Precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
    }

    public void addFlight(ActionEvent actionEvent) throws Exception {
        Vuelo vuelo = recogerDatos();
        HttpClient<Vuelo, Vuelo> client = new HttpClient<>(Vuelo.class);

        if (vuelo != null) {
            try {
                if (client.execute(URLApi.API_VUELO_BY_ID.replace("{id}", vuelo.getId()), null, "GET") == null) {
                    client.execute(URLApi.API_VUELO_CREATE, vuelo, "POST");
                    limpiarCampos();
                    mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
                    reloadScreenHome(actionEvent);
                } else {
                    mostrarMensajes("Advertencia", "El vuelo ya existe en base de datos", Alert.AlertType.INFORMATION);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }

    private Vuelo recogerDatos() {
        boolean correcto = true;
        String errores = "Se han producido los siguientes errores:\n \n";
        Vuelo vuelo = null;

        String id = txt_IdFlight.getText();

        if (id.isEmpty()) {
            errores += "- El campo ID es obligatorio.\n";
            correcto = false;
        } else {
            if (!Utiles.validarIdVuelo(id)) {
                errores += "- El formato del id no es correcto.\n";
                correcto = false;
            }
        }
        Aeropuerto origen = combo_OrigenFlight.getSelectionModel().getSelectedItem();
        if (origen == null) {
            errores += "- El campo Origen es obligatorio.\n";
            correcto = false;
        }
        Aeropuerto destino = combo_DestinoFlight.getSelectionModel().getSelectedItem();
        if (destino == null) {
            errores += "- El campo Destino es obligatorio.\n";
            correcto = false;
        }
        if (origen != null && destino != null)
            if (origen.equals(destino)) {
                errores += "- El origen y el destino no deben ser iguales. \n";
                correcto = false;
            }

        LocalDate fechaSalida = date_FechaSalidaFlight.getValue();
        if (fechaSalida == null) {
            errores += "- El campo Fecha salida es obligatorio.\n";
            correcto = false;
        } else if (!Utiles.validarFecha(fechaSalida)) {
            errores += "- La fecha de Salida no es válida, no debe ser anterior a la actual.\n";
            correcto = false;
        }
        String horaSalida = txt_HoraSalidaFlight.getText();
        LocalTime horaSalidaTime = null;
        if (horaSalida.isEmpty()) {
            errores += "- El campo Hora Salida es obligatorio.\n";
            correcto = false;
        } else {
            if (!Utiles.validarHora(horaSalida)) {
                errores += "- El formato Hora de salida no es correcto HH:mm:ss .\n";
                correcto = false;
            } else {
                horaSalidaTime = Utiles.convertirATime(horaSalida);
            }
        }
        String horaLlegada = txt_HoraLlegadaFlight.getText();
        LocalTime horaLlegadaTime = null;
        if (horaLlegada.isEmpty()) {
            errores += "- El campo Hora de llegada es obligatorio.\n";
            correcto = false;
        } else {
            if (!Utiles.validarHora(horaLlegada)) {
                errores += "- El formato hora de llegada no es correcto HH:mm:ss . \n";
                correcto = false;
            } else {
                horaLlegadaTime = Utiles.convertirATime(horaLlegada);
            }
        }
        if (horaLlegadaTime != null && horaSalidaTime != null)
            if (!Utiles.validarHoras(horaSalidaTime, horaLlegadaTime)) {
                errores += "- La hora de salida no debe ser posterior a la hora de llegada. \n";
                correcto = false;
            }
        Avion avion = combo_AvionFlight.getValue();
        if (avion == null) {
            errores += "- El campo Avion es obligatorio. \n";
            correcto = false;
        }

        Double precio = Double.parseDouble(txt_PrecioFlight.getText());
        if(precio == 0){
            errores += "- El campo precio es obligatorio y debe ser superior a 0";
            correcto = false;
        }

        if (correcto) {
            vuelo = new Vuelo(id, origen, destino,
                    fechaSalida, horaSalidaTime, horaLlegadaTime, avion, precio);
        } else {
            mostrarMensajes("Error", errores, Alert.AlertType.ERROR);
        }

        return vuelo;
    }

    private void mostrarMensajes(String titulo, String mensaje, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void updateFlight(ActionEvent actionEvent) throws Exception {
        Vuelo vuelo = recogerDatos();
        HttpClient<Vuelo, Vuelo> client = new HttpClient<>(Vuelo.class);
        try {
            if (client.execute(URLApi.API_VUELO_BY_ID.replace("{id}", vuelo.getId()), null, "GET") != null) {
                client.execute(URLApi.API_VUELO_UPDATE, vuelo, "PUT");
                mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
                limpiarCampos();
                reloadScreenHome(actionEvent);
            } else {
                mostrarMensajes("Error", "Se ha producido un error en la actualización.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        inicializaTableView();
    }

    @FXML
    private void clearFlieds(ActionEvent actionEvent) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txt_IdFlight.clear();
        txt_HoraSalidaFlight.clear();
        txt_HoraLlegadaFlight.clear();
        combo_DestinoFlight.setValue(null);
        combo_OrigenFlight.setValue(null);
        date_FechaSalidaFlight.setValue(null);
        combo_AvionFlight.setValue(null);
        txt_PrecioFlight.clear();
    }

    @FXML
    private void deleteFlight(ActionEvent actionEvent) throws Exception {
        Vuelo vueloSeleccionado = tableViewFlight.getSelectionModel().getSelectedItem();
        HttpClient<Vuelo, Vuelo> client = new HttpClient<>(Vuelo.class);
        if (vueloSeleccionado != null) {
            try {
                Vuelo vuelo = client.execute(URLApi.API_VUELO_BY_ID.replace("{id}", vueloSeleccionado.getId()), null, "GET");
                if (vuelo != null) {
                    client.execute(URLApi.API_VUELO_DELETE.replace("{id}", vueloSeleccionado.getId()), vueloSeleccionado, "DELETE");
                    mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
                    limpiarCampos();
                    reloadScreenHome(actionEvent);
                } else {
                    mostrarMensajes("Error", "Se ha producido un error en el borrado del vuelo.", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        inicializaTableView();
    }

    @FXML
    private void handleRowSelection(MouseEvent mouseEvent) {
        Vuelo vueloSeleccionado = tableViewFlight.getSelectionModel().getSelectedItem();
        if (vueloSeleccionado != null) {
            txt_IdFlight.setText(vueloSeleccionado.getId());
            txt_IdFlight.setEditable(false);
            combo_OrigenFlight.setValue(vueloSeleccionado.getOrigen());
            combo_DestinoFlight.setValue(vueloSeleccionado.getDestino());
            txt_HoraLlegadaFlight.setText(vueloSeleccionado.getHoraLlegada().toString());
            txt_HoraSalidaFlight.setText(vueloSeleccionado.getHoraSalida().toString());
            date_FechaSalidaFlight.setValue(vueloSeleccionado.getFechaSalida());
            combo_AvionFlight.setValue(vueloSeleccionado.getAvion());
            txt_PrecioFlight.setText(vueloSeleccionado.getPrecio().toString());
        }
    }

    @FXML
    private void buscar(KeyEvent keyEvent) throws Exception {
        String texto = txtSearchFlight.getText();
        HttpClient<Vuelo, Vuelo[]> client = new HttpClient<>(Vuelo[].class);

        if (texto.length() >= 3) {
            Vuelo vuelo = obtenenerCondicionesWhere(texto);

            Vuelo[] lista = client.execute(URLApi.API_VUELO_SEARCH, vuelo, "POST");
            ObservableList<Vuelo> vuelosFiltrados = FXCollections.observableArrayList(lista);
            tableViewFlight.setItems(vuelosFiltrados);
            column_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
            column_Origen.setCellValueFactory(origenStringCellDataFeatures -> new SimpleStringProperty(origenStringCellDataFeatures.getValue().getOrigen().getNombre()));
            column_Destino.setCellValueFactory(origenStringCellDataFeatures -> new SimpleStringProperty(origenStringCellDataFeatures.getValue().getDestino().getNombre()));
            column_Fecha.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
            column_HoraSalida.setCellValueFactory(new PropertyValueFactory<>("horaSalida"));
            column_HoraLlegada.setCellValueFactory(new PropertyValueFactory<>("horaLlegada"));
            column_Avion.setCellValueFactory(vueloStringCellDataFeatures -> new SimpleStringProperty(vueloStringCellDataFeatures.getValue().getAvion().getId()));
            column_Precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        } else {
            inicializaTableView();
        }
    }

    private Vuelo obtenenerCondicionesWhere(String texto) {
        LocalDate fecha = null;
        LocalTime hora = null;
        Double precio = null;
        if (Utiles.validarSiFecha(texto)) {
            fecha = Utiles.convertirADate(texto);
        }
        if (Utiles.validarHora(texto)) {
            hora = Utiles.convertirATime(texto);
        }
        if (Utiles.validarSiDouble(texto)){
            precio = Double.parseDouble(texto);
        }

        return new Vuelo(texto, new Aeropuerto(null, texto), new Aeropuerto(null, texto), fecha, hora, hora, new Avion(null, texto, null), precio);
    }

    private void reloadScreenHome(ActionEvent event) throws IOException {
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
}
