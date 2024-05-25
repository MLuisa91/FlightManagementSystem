package com.donoso.easyflight.controlador;

import com.donoso.easyflight.contexto.UsuarioHolder;
import com.donoso.easyflight.http.HttpClient;
import com.donoso.easyflight.pojos.Usuario;
import com.donoso.easyflight.utils.EnumRoles;
import com.donoso.easyflight.utils.URLApi;
import com.donoso.easyflight.utils.Utiles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class LoginController {

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    private String user;
    private String password;



    @FXML
    void aceptar(ActionEvent event) {
        //this.llamarAPI();
        user = txtUser.getText();
        password = txtPassword.getText();
        //CrudUsuarios crudUsuarios = new CrudUsuarios();
        //Usuario u = crudUsuarios.searchUserByUserAndPassword(new Usuario(null, null, null, user, password, null));

        Usuario u = this.getUsuariosByUserAndPass(user, Utiles.encriptarAMD5(password));

        if (validarAcceso(u)) {
            // Si el usuario existe en la aplicación accedemos
            try {
                // cargar en el contexto el usuario logado
                UsuarioHolder holder = UsuarioHolder.getInstance();
                holder.setUsuario(u);

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
            } catch (Exception e) {
                mostrarAlertError("Error al cargar la segunda ventana: " + e.getMessage());
            }
        }


    }

    private boolean validarAcceso(Usuario usuario){
        if(usuario == null){
            mostrarAlertError("El usuario no se encuentra en nuestra Base de Datos. Consulte con un administrador.");
            return false;
        }
        if(usuario.getUsuarioRol().size() == 1 && usuario.getUsuarioRol().stream().filter(rol -> rol.getRol().getNombre().equals(EnumRoles.CLIENTE.name())).count() >= 1){
            mostrarAlertError("No tiene permiso de acceso a esta aplicación");
            return false;
        }
        return true;
    }

    @FXML
    private void mostrarAlertError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * LLamada de ejemplo para la api rest
     */
    private Usuario getUsuariosByUserAndPass(String user, String password) {
        HttpClient<Usuario, Usuario> client = new HttpClient<>(Usuario.class);

        try {
            //Ejecutar y obtener la respuesta
            return client.execute(URLApi.API_USER_BY_ID_PASSWORD.concat(user)
                    .concat("/")
                    .concat(password), null, "GET");

            // TODO: PETICIÓN POST como enviar la información
            /*JsonConverter<Usuarios> converter = new JsonConverter<>(Usuarios.class);
            client.execute("usuarios/insertar", converter.jsonToObject(result), "POST");*/

        } catch (ParseException | IOException e) {
            System.out.println("-> " + e.getClass().getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;

    }

}
