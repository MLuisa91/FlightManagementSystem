package com.donoso.easyflight.controlador;

import com.donoso.easyflight.contexto.UsuarioHolder;
import com.donoso.easyflight.http.HttpClient;
import com.donoso.easyflight.pojos.Oferta;
import com.donoso.easyflight.pojos.Reserva;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ManageBookingController implements Initializable {
    public TableView<Reserva> tableViewReservas;
    public TableColumn<Reserva, String> column_IdReserva;
    public TableColumn<Reserva, String> column_Usuario;
    public TableColumn<Reserva, String> column_Oferta;
    public TableColumn<Reserva, String> column_Vuelo;
    public TableColumn<Reserva, LocalDate> column_FechaReserva;
    public TableColumn<Reserva, String> column_Viajeros;
    public TableColumn<Reserva, String> column_Extras;
    public TableColumn<Reserva, Double> column_Total;
    public TextField txt_IdReserva;
    public TextField txt_TotalReserva;
    public TextField txt_OfertaReserva;
    public TextField txt_FechaReserva;
    public TextField txt_UsuarioReserva;
    public TextField txt_VueloReserva;
    public TextField txtSearchReservas;


    public void handleRowSelection(MouseEvent mouseEvent) {
        Reserva reservaSeleccionada = tableViewReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            txt_IdReserva.setText(reservaSeleccionada.getId());
            txt_UsuarioReserva.setText(reservaSeleccionada.getUsuario().getId().toString());
            txt_VueloReserva.setText(reservaSeleccionada.getVuelo().getId());
            txt_OfertaReserva.setText(reservaSeleccionada.getOferta().getNombre());
            txt_FechaReserva.setText(reservaSeleccionada.getFechaReserva().toString());
            txt_TotalReserva.setText(reservaSeleccionada.getTotal().toString());
        }
    }

    public void buscar(KeyEvent keyEvent) throws Exception {
        String texto = txtSearchReservas.getText();
        HttpClient<Reserva, Reserva[]> client = new HttpClient<>(Reserva[].class);
        if (texto.length() >= 3) {
            Reserva reserva = obtenenerCondicionesWhere(texto);

            Reserva[] lista = client.execute(URLApi.API_RESERVA_SEARCH, reserva, "POST");
            ObservableList<Reserva> reservas = FXCollections.observableArrayList(lista);
            tableViewReservas.setItems(reservas);
            column_IdReserva.setCellValueFactory(new PropertyValueFactory<>("id"));
            column_Usuario.setCellValueFactory(usuariosStringCellDataFeatures -> new SimpleStringProperty(usuariosStringCellDataFeatures.getValue().getUsuario().getId().toString()));
            column_Oferta.setCellValueFactory(ofertasStringCellDataFeatures -> new SimpleStringProperty(ofertasStringCellDataFeatures.getValue().getOferta().getNombre()));
            column_FechaReserva.setCellValueFactory(new PropertyValueFactory<>("fechaReserva"));
            column_Extras.setCellValueFactory(extrasStringCellDataFeatures -> new SimpleStringProperty(extrasStringCellDataFeatures.getValue().getReservaExtras().toString()));
            column_Viajeros.setCellValueFactory(viajerosStringCellDataFeatures -> new SimpleStringProperty(viajerosStringCellDataFeatures.getValue().getReservaViajeros().toString()));
            column_Total.setCellValueFactory(new PropertyValueFactory<>("total"));
        } else {
            inicializaTableView();
        }
    }

    private Reserva obtenenerCondicionesWhere(String texto) {
        Integer id = null;
        Double total = null;
        LocalDate fecha = null;
        if(Utiles.validarSiNumero(texto)){
            id = Integer.parseInt(texto);
        }
        if(Utiles.validarSiNumero(texto)){
            total = Double.parseDouble(texto);
        }
        if(Utiles.validarSiFecha(texto)){
            fecha = Utiles.convertirADate(texto);
        }

        return new Reserva(texto,new Usuario(Integer.parseInt(texto)), new Vuelo(texto), new Oferta(id, null, null),null, total, null, fecha, null);
    }

    public void updateReserva(ActionEvent event) throws Exception {
        Reserva reserva = recogerDatos();
        HttpClient<Reserva, Reserva> client = new HttpClient<>(Reserva.class);
        try {
            if (client.execute(URLApi.API_RESERVA_BY_ID.replace("{id}", reserva.getId()), null, "GET") != null) {
                client.execute(URLApi.API_RESERVA_UPDATE, reserva, "PUT");
                mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
                limpiarCampos();
                reloadScreenReservas(event);
            } else {
                mostrarMensajes("Error", "Se ha producido un error en la actualización.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        inicializaTableView();


    }

    private Reserva recogerDatos() {
        Reserva reserva = null;
        String errores = "Se han producido los siguientes errores: \n";
        Boolean correcto = true;
        String id = txt_IdReserva.getText();
        if (id.isEmpty()) {
            errores += "El campo id es obligatorio\n";
            correcto = false;
        } else {
            if (!Utiles.verificarCodigoReserva(id)) {
                errores += "El código de la reserva no es correcto, por favor, compruebe su formato.\n";
                correcto = false;
            }
        }

        String usuario = txt_UsuarioReserva.getText();
        if (usuario.isEmpty()) {
            errores += "El campo usuario es obligatorio\n";
            correcto = false;
        } else {
            if (!Utiles.validarDNI(usuario)) {
                errores += "El usuario no es correcto, por favor, compruebe su formato.\n";
                correcto = false;
            }
        }

        String vuelo = txt_VueloReserva.getText();
        if (vuelo.isEmpty()) {
            errores += "El campo vuelo es obligatorio\n";
            correcto = false;
        } else {
            if (!Utiles.validarIdVuelo(vuelo)) {
                errores += "El vuelo no es correcto, por favor, compruebe su formato.\n";
                correcto = false;
            }
        }

        String oferta = txt_OfertaReserva.getText();
        if (oferta.isEmpty()) {
            errores += "El campo oferta es obligatorio\n";
            correcto = false;
        }

        String fecha = txt_FechaReserva.getText();
        LocalDate fechaReserva = null;
        if (fecha.isEmpty()) {
            errores += "El campo fecha reserva es obligatorio\n";
            correcto = false;
        } else {
            if (!Utiles.validarSiFecha(fecha)) {
                errores += "El formato de la fecha no es correcto.\n";
                correcto = false;
            } else {
                fechaReserva = Utiles.convertirADate(fecha);
            }
        }

        String total = txt_TotalReserva.getText();
        if (total.isEmpty()) {
            errores += "El campo total es obligatorio.\n";
            correcto = false;
        } else {
            if (!Utiles.validarSiNumero(total)) {
                errores += "Compruebe que está introduciendo carácteres numéricos.\n";
                correcto = false;
            }
        }

        if (correcto) {
            reserva = new Reserva(id, new Usuario(Integer.parseInt(usuario)), new Vuelo(vuelo), new Oferta(null, oferta, null), fechaReserva, Double.parseDouble(total));
        } else {
            mostrarMensajes("Error", errores, Alert.AlertType.ERROR);
        }

        return reserva;
    }

    public void cleanFields(ActionEvent event) {
        limpiarCampos();
    }

    public void deleteReserva(ActionEvent event) throws Exception {
        Reserva reservaSeleccionada = tableViewReservas.getSelectionModel().getSelectedItem();
        HttpClient<Reserva, Reserva> client = new HttpClient<>(Reserva.class);
        if (reservaSeleccionada != null) {
            try {
                Reserva reserva = client.execute(URLApi.API_RESERVA_BY_ID.replace("{id}", reservaSeleccionada.getId()), null, "GET");
                if (reserva != null) {
                    client.execute(URLApi.API_RESERVA_DELETE.replace("{id}", reservaSeleccionada.getId()), reservaSeleccionada, "DELETE");
                    mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
                    limpiarCampos();
                    reloadScreenReservas(event);
                } else {
                    mostrarMensajes("Error", "Se ha producido un error en el borrado de la reserva.", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        inicializaTableView();
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
        HttpClient<Reserva, Reserva[]> client = new HttpClient<>(Reserva[].class);
        Reserva[] lista = client.execute(URLApi.API_RESERVA_SEARCH, new Reserva(), "POST");

        ObservableList<Reserva> reservas = FXCollections.observableArrayList(lista);
        tableViewReservas.setItems(reservas);
        column_IdReserva.setCellValueFactory(new PropertyValueFactory<>("id"));
        column_Usuario.setCellValueFactory(usuariosStringCellDataFeatures -> new SimpleStringProperty(usuariosStringCellDataFeatures.getValue().getUsuario().getId().toString()));
        column_Oferta.setCellValueFactory(ofertasStringCellDataFeatures -> new SimpleStringProperty(ofertasStringCellDataFeatures.getValue().getOferta().getNombre()));
        column_FechaReserva.setCellValueFactory(new PropertyValueFactory<>("fechaReserva"));
        column_Extras.setCellValueFactory(extrasStringCellDataFeatures -> new SimpleStringProperty(extrasStringCellDataFeatures.getValue().getReservaExtras().toString()));
        column_Viajeros.setCellValueFactory(viajerosStringCellDataFeatures -> new SimpleStringProperty(viajerosStringCellDataFeatures.getValue().getReservaViajeros().toString()));
        column_Total.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private void limpiarCampos() {
        txt_IdReserva.clear();
        txt_UsuarioReserva.clear();
        txt_OfertaReserva.clear();
        txt_FechaReserva.clear();
        txt_VueloReserva.clear();
        txt_TotalReserva.clear();

    }

    private void mostrarMensajes(String titulo, String mensaje, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void reloadScreenReservas(ActionEvent event) throws IOException {
        // cerrar la ventana actual
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        // Cargar la ventana de gestión de ofertas
        Parent root = FXMLLoader.load(getClass().getResource("/com/donoso/easyflight/vista/Main.fxml"));

        // Crear un nuevo Stage para la segunda ventana
        Stage segundaVentana = new Stage();
        segundaVentana.setTitle("Gestión de Reservas");
        Scene escena = new Scene(root);
        segundaVentana.setScene(escena);

        // Obtener el controlador de la segunda ventana si es necesario

        // Mostrar la segunda ventana
        segundaVentana.show();

    }

    public void openManageViajerosExtra(MouseEvent mouseEvent) throws IOException {
        try {
            // Cargar la ventana de gestión de viajeros y extras
            Parent root = FXMLLoader.load(getClass().getResource("/com/donoso/easyflight/vista/ManageExtrasViajeros.fxml"));

            // Crear un nuevo Stage para la segunda ventana
            Stage segundaVentana = new Stage();
            segundaVentana.setTitle("Gestión de Extras y Viajeros");
            Scene escena = new Scene(root);
            segundaVentana.setScene(escena);

            // Mostrar la segunda ventana
            segundaVentana.show();
        } catch (Exception e) {
            mostrarMensajes("Error", "Error al cargar la segunda ventana: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
