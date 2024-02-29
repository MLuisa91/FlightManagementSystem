package com.planemanagement.planemanagement.controlador;

import com.planemanagement.planemanagement.modelo.Avion;
import com.planemanagement.planemanagement.modelo.Vuelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class VuelosController implements Initializable {

    @FXML
    private AnchorPane about_Pane;

    @FXML
    private Button buttonAbout;

    @FXML
    private Button buttonAddFlight;

    @FXML
    private Button buttonHome;

    @FXML
    private Button buttonLogOut;

    @FXML
    private Button button_ManagementFlightAdd;

    @FXML
    private Button button_ManagementFlightClear;

    @FXML
    private Button button_ManagementFlightDelete;

    @FXML
    private Button button_ManagementFlightUpdate;

    @FXML
    private TableColumn<Vuelo, Avion> column_Avion;

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

    @FXML
    private Label main_AnnualTotalFlights;

    @FXML
    private AnchorPane main_Pane;

    @FXML
    private Label main_TotalFlights;

    @FXML
    private Label main_TotalMonthlyFlights;

    @FXML
    private AnchorPane manageFlight_Pane;

    @FXML
    private TableView<Vuelo> tableViewFlight;

    @FXML
    private TextField txtSearchFlight;

    @FXML
    private TextField txt_DestinoFlight;

    @FXML
    private TextField txt_HoraLlegadaFlight;

    @FXML
    private TextField txt_HoraSalidaFlight;

    @FXML
    private TextField txt_IdFlight;

    @FXML
    private TextField txt_OrigenFlight;

    @FXML
    private Label username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void switchScreen(ActionEvent event){

        if(event.getSource() == buttonHome){
            main_Pane.setVisible(true);
            about_Pane.setVisible(false);
            manageFlight_Pane.setVisible(false);
        }else if(event.getSource() == buttonAddFlight){
            main_Pane.setVisible(false);
            about_Pane.setVisible(false);
            manageFlight_Pane.setVisible(true);
        }else if(event.getSource() == buttonAbout){
            main_Pane.setVisible(false);
            about_Pane.setVisible(true);
            manageFlight_Pane.setVisible(false);
        }

    }

    public void logOut(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setContentText("¿Está seguro que desea salir?");
        Optional<ButtonType> option = alert.showAndWait();

        try {
            if (option.get().equals(ButtonType.OK)) {

                Parent root = FXMLLoader.load(getClass().getResource("/com/planemanagement/planemanagement/vista/login.fxml"));
                Stage stage = new Stage();
                Scene escena = new Scene(root);
                stage.setScene(escena);
                stage.show();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
