package com.planemanagement.planemanagement.controlador;

import com.planemanagement.planemanagement.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    private String user;
    private String password;

    @FXML
    void aceptar(ActionEvent event) {
        user = txtUser.getText();
        password = txtPassword.getText();
        if (buscarAdmin(user, password)) {
            //Si el usuario es administrador pasamos a la siguiente pantalla
            try {
                // Cargar la ventana de gestión de vuelos
               // FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aviones/vista/GestionVuelos.fxml"));
                Parent root = FXMLLoader.load(getClass().getResource("/com/planemanagement/planemanagement/vista/Vuelos.fxml"));

                //GestionVuelosController controller = loader.getController();

                // Crear un nuevo Stage para la segunda ventana
                Stage segundaVentana = new Stage();
                segundaVentana.setTitle("Gestión de Vuelos");
                Scene escena = new Scene(root);
                segundaVentana.setScene(escena);

                // Obtener el controlador de la segunda ventana si es necesario


                // Mostrar la segunda ventana
                segundaVentana.show();
            } catch (Exception e) {
                mostrarAlertError("Error al cargar la segunda ventana: " + e.getMessage());
            }
        } else {
            mostrarAlertError("El usuario no se encuentra en nuestra Base de Datos. Consulte con un administrador.");
        }
    }

    @FXML
    private void mostrarAlertError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static boolean buscarAdmin(String user, String password) {
        ConexionBD cbd = new ConexionBD();
        try (Connection conexion = cbd.abrirConexion()) {
            // Consulta SQL para obtener el campo administrador del usuario
            String consultaSQL = "SELECT administrador FROM usuario WHERE user = ? AND password = ?";
            try (PreparedStatement declaracion = conexion.prepareStatement(consultaSQL)) {
                declaracion.setString(1, user);
                declaracion.setString(2, password);


                try (ResultSet resultado = declaracion.executeQuery()) {
                    if (resultado.next()) {
                        // Si el resultado.next() devuelve verdadero, significa que hay un usuario con esos credenciales
                        // Obtener el valor del campo administrador
                        boolean esAdmin = resultado.getBoolean("administrador");
                        return esAdmin;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbd.cerrarConexion();
        // Si algo sale mal o no se encuentra el usuario, devolver falso
        return false;

    }

}
