package com.donoso.easyflight.controlador;

import com.donoso.easyflight.contexto.UsuarioHolder;
import com.donoso.easyflight.pojos.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public AnchorPane managePlane_Pane;
    @FXML
    public Button buttonAddPlane;
    @FXML
    public AnchorPane manageUsuarios_Pane;
    @FXML
    public Button buttonAddUsuario;
    public Button buttonAddBooking;
    public Button buttonAddOffer;
    public AnchorPane manageOffer_Pane;
    public AnchorPane manageBooking_Pane;
    @FXML
    private AnchorPane about_Pane;

    @FXML
    private Button buttonAbout;

    @FXML
    private Button buttonAddFlight;
    @FXML
    private Button button;

    @FXML
    private Button buttonHome;

    @FXML
    private AnchorPane main_Pane;

    @FXML
    private AnchorPane manageFlight_Pane;

    @FXML
    private Label username;

    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UsuarioHolder holder = UsuarioHolder.getInstance();
        loadUser(holder.getUsuario());
        if (holder.getLatestScreen() != null)
            enableScreen(holder.getLatestScreen());

    }

    public void switchScreen(ActionEvent event) throws IOException {
        UsuarioHolder holder = UsuarioHolder.getInstance();
        holder.setLatestScreen(event.getSource());
        //si permisos
        enableScreen(event.getSource());
    }



    public void enableScreen(Object screen) {
        if (((Button) screen).getId().equals(buttonHome.getId())) {
            main_Pane.setVisible(true);
            about_Pane.setVisible(false);
            manageFlight_Pane.setVisible(false);
            managePlane_Pane.setVisible(false);
            manageUsuarios_Pane.setVisible(false);
            manageOffer_Pane.setVisible(false);
        } else if (((Button) screen).getId().equals(buttonAddFlight.getId())) {
            main_Pane.setVisible(false);
            about_Pane.setVisible(false);
            manageFlight_Pane.setVisible(true);
            managePlane_Pane.setVisible(false);
            manageUsuarios_Pane.setVisible(false);
            manageOffer_Pane.setVisible(false);
        } else if (((Button) screen).getId().equals(buttonAbout.getId())) {
            main_Pane.setVisible(false);
            about_Pane.setVisible(true);
            manageFlight_Pane.setVisible(false);
            managePlane_Pane.setVisible(false);
            manageUsuarios_Pane.setVisible(false);
            manageOffer_Pane.setVisible(false);
        } else if (((Button) screen).getId().equals(buttonAddPlane.getId())) {
            main_Pane.setVisible(false);
            about_Pane.setVisible(false);
            manageFlight_Pane.setVisible(false);
            managePlane_Pane.setVisible(true);
            manageUsuarios_Pane.setVisible(false);
            manageOffer_Pane.setVisible(false);
        } else if (((Button) screen).getId().equals(buttonAddUsuario.getId())) {
            main_Pane.setVisible(false);
            about_Pane.setVisible(false);
            manageFlight_Pane.setVisible(false);
            managePlane_Pane.setVisible(false);
            manageUsuarios_Pane.setVisible(true);
            manageOffer_Pane.setVisible(false);
        } else if (((Button) screen).getId().equals(buttonAddOffer.getId())) {
            main_Pane.setVisible(false);
            about_Pane.setVisible(false);
            manageFlight_Pane.setVisible(false);
            managePlane_Pane.setVisible(false);
            manageUsuarios_Pane.setVisible(false);
            manageOffer_Pane.setVisible(true);
        }
    }

    public void logOut(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setContentText("¿Está seguro que desea salir?");
        Optional<ButtonType> option = alert.showAndWait();

        try {
            if (option.get().equals(ButtonType.OK)) {

                // cerramos la ventana actual
                Node node = (Node) actionEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

                // cargamos la nueva ventana

                Parent root = FXMLLoader.load(getClass().getResource("/com/donoso/easyflight/vista/Login.fxml"));
                Stage loginStage = new Stage();
                Scene escena = new Scene(root);
                loginStage.setScene(escena);
                loginStage.show();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void loadUser(Usuario usuario) {
        this.usuario = usuario;
        this.username.setText(usuario.getNombre().concat(" ").concat(usuario.getApellidos()));
    }

}
