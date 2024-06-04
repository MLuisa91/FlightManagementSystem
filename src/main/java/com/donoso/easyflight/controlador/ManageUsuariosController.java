package com.donoso.easyflight.controlador;

import com.donoso.easyflight.contexto.UsuarioHolder;
import com.donoso.easyflight.http.HttpClient;
import com.donoso.easyflight.pojos.Pais;
import com.donoso.easyflight.pojos.Rol;
import com.donoso.easyflight.pojos.Usuario;
import com.donoso.easyflight.pojos.UsuarioRol;
import com.donoso.easyflight.utils.URLApi;
import com.donoso.easyflight.utils.Utiles;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
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
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ManageUsuariosController implements Initializable {

    public TextField txt_IdUsuario;
    public TextField txt_TelefonoUsuario;
    public TextField txt_EmailUsuario;
    public ComboBox<Pais> combo_PaisUsuairio;
    public TextField txt_ApellidosUsuario;
    public TextField txt_NombreUSuario;
    public TextField txt_User_Usuario;
    public PasswordField txt_PasswordUsuario;
    public CheckBox check_Admin;
    public CheckBox check_Oferta;
    public CheckBox check_Cliente;
    public CheckBox check_Reserva;

    public Button buttont_AddUser;
    public Button buttont_UpdateUser;
    public Button buttont_DeleteUser;
    public Button buttont_ClearUser;
    public TableView<Usuario> tableViewUsuarios;
    public TableColumn<Usuario, String> column_IdUsuario;
    public TableColumn<Usuario, String> column_Nombre;
    public TableColumn<Usuario, String> column_Apellidos;
    public TableColumn<Usuario, String> column_Pais;
    public TableColumn<Usuario, String> column_Email;
    public TableColumn<Usuario, String> column_Telefono;
    public TextField txtSearchUsuarios;
    public Group checkGroup;
    public CheckBox check_BDAdmin;
    Usuario usuarioSeleccionado;


    public void addUsuario(ActionEvent event) {
        Usuario usuario = recogerDatos(null);
        HttpClient<Usuario, Usuario> client = new HttpClient<>(Usuario.class);

        if (usuario != null) {
            try {
                if (client.execute(URLApi.API_USER_BY_ID.replace("{id}", usuario.getId().toString()), null, "GET") == null) {
                    client.execute(URLApi.API_USER_CREATE, usuario, "POST");
                    limpiarCampos();
                    mostrarMensajes("Información", "La operación se ha relizado correctamente.", Alert.AlertType.INFORMATION);
                    reloadScreenHome(event);
                } else {
                    mostrarMensajes("Advertencia", "El usuario ya existe en base de datos", Alert.AlertType.INFORMATION);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void handleRowSelection(MouseEvent mouseEvent) {
        usuarioSeleccionado = tableViewUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            txt_IdUsuario.setText(usuarioSeleccionado.getDni());
            txt_NombreUSuario.setText(usuarioSeleccionado.getNombre());
            txt_ApellidosUsuario.setText(usuarioSeleccionado.getApellidos());
            txt_EmailUsuario.setText(usuarioSeleccionado.getEmail());
            txt_TelefonoUsuario.setText(usuarioSeleccionado.getTelefono());
            txt_User_Usuario.setText(usuarioSeleccionado.getUser());
            txt_PasswordUsuario.setText(Utiles.desencriptarMD5(usuarioSeleccionado.getPassword()));
            combo_PaisUsuairio.setValue(usuarioSeleccionado.getPais());
            usuarioSeleccionado.getUsuarioRol().forEach(roles -> {
                if (roles.getRol().getNombre().contains("OFERTA")){
                    check_Oferta.setSelected(true);
                }else if (roles.getRol().getNombre().contains("ADMIN")){
                    check_Admin.setSelected(true);
                }else if (roles.getRol().getNombre().contains("RESERVA")){
                    check_Reserva.setSelected(true);
                }else if (roles.getRol().getNombre().contains("CLIENTE")){
                    check_Cliente.setSelected(true);
                }else if(roles.getRol().getUsuarioRol().contains("BD_ADMIN")){
                    check_BDAdmin.setSelected(true);
                }
            });


        }
    }

    public void updateUsuario(ActionEvent event) {
        usuarioSeleccionado = recogerDatos(usuarioSeleccionado);
        HttpClient<Usuario, Usuario> client = new HttpClient<>(Usuario.class);

        if (usuarioSeleccionado != null) {
            try {
                if (client.execute(URLApi.API_USER_BY_ID.replace("{id}", usuarioSeleccionado.getId().toString()), null, "GET") != null) {
                    client.execute(URLApi.API_USER_UPDATE, usuarioSeleccionado, "PUT");
                    mostrarMensajes("Información", "La operación se ha realizado correctamente.", Alert.AlertType.INFORMATION);
                    limpiarCampos();
                    reloadScreenHome(event);
                } else {
                    mostrarMensajes("Error", "Se ha producido un error en la actualización", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void cleanFields(ActionEvent event) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txt_TelefonoUsuario.clear();
        txt_User_Usuario.clear();
        txt_EmailUsuario.clear();
        txt_ApellidosUsuario.clear();
        txt_NombreUSuario.clear();
        txt_IdUsuario.clear();
        txt_PasswordUsuario.clear();
        combo_PaisUsuairio.valueProperty().set(null);
        check_Admin.setSelected(false);
        check_Reserva.setSelected(false);
        check_Oferta.setSelected(false);
        check_Cliente.setSelected(false);
        check_BDAdmin.setSelected(false);

    }

    public void deleteUsuario(ActionEvent event) {
        usuarioSeleccionado = tableViewUsuarios.getSelectionModel().getSelectedItem();
        HttpClient<Usuario, Usuario> client = new HttpClient<>(Usuario.class);
        if (usuarioSeleccionado != null) {
            try {
                if (client.execute(URLApi.API_USER_BY_ID.replace("{id}", usuarioSeleccionado.getId().toString()), null, "GET") != null) {
                    client.execute(URLApi.API_USER_DELETE.replace("{id}", usuarioSeleccionado.getId().toString()), usuarioSeleccionado, "DELETE");
                    mostrarMensajes("Información", "El usuario ha sido borrado de la base de datos", Alert.AlertType.INFORMATION);
                    limpiarCampos();
                    reloadScreenHome(event);
                } else {
                    mostrarMensajes("Advertencia", "El usuario no se encuentra en base de datos", Alert.AlertType.WARNING);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UsuarioHolder holder = UsuarioHolder.getInstance();
        Usuario usuario = holder.getUsuario();

        try {
            inicializaTableView();
            inicializaComboBoxPaises();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void inicializaComboBoxPaises() throws Exception {
        HttpClient<Pais, Pais[]> client = new HttpClient<>(Pais[].class);
        Pais[] lista = client.execute(URLApi.API_PAIS_SEARCH, new Pais(), "POST");

        combo_PaisUsuairio.setItems(FXCollections.observableArrayList(lista));
        combo_PaisUsuairio.setConverter(new Pais());

    }

    private void inicializaTableView() throws Exception {
        HttpClient<Usuario, Usuario[]> client = new HttpClient<>(Usuario[].class);
        Usuario[] lista = client.execute(URLApi.API_USER_SEARCH,new Usuario(), "POST");

        ObservableList<Usuario> usuarios = FXCollections.observableArrayList(lista);
        this.tableViewUsuarios.setItems(usuarios);
        column_IdUsuario.setCellValueFactory(new PropertyValueFactory<>("dni"));
        column_Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        column_Apellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        column_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        column_Telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        column_Pais.setCellValueFactory(paisStringCellDataFeatures -> new SimpleStringProperty(paisStringCellDataFeatures.getValue().getPais().getNombre()));

    }

    private Usuario recogerDatos(Usuario usuario) {
        boolean correcto = true;
        String errores = "Se han producido los siguientes errores:\n \n";
        Usuario nuevoUsuario = null;

        String dni = txt_IdUsuario.getText();
        if (dni.isEmpty()) {
            errores += "- El campo DNI es obligatorio.\n";
            correcto = false;
        } else {
            if (!Utiles.validarDNI(dni)) {
                errores += "- El formato del DNI no es correcto.\n";
                correcto = false;
            }
        }

        String nombre = txt_NombreUSuario.getText();
        if (nombre.isEmpty()) {
            errores += "- El campo nombre es obligatorio.\n";
            correcto = false;
        }

        String apellidos = txt_ApellidosUsuario.getText();
        if (apellidos.isEmpty()) {
            errores += "- El campo apellidos es obligatorio.\n";
            correcto = false;
        }

        String user = txt_User_Usuario.getText();
        if (user.isEmpty()) {
            errores += "- El campo user es obligatorio.\n";
            correcto = false;
        }

        String password = txt_PasswordUsuario.getText();
        if (password.isEmpty()) {
            errores += "- El campo contraseña es obligatorio.\n";
            correcto = false;
        }

        String email = txt_EmailUsuario.getText();
        if (email.isEmpty()) {
            errores += "- El campo email es obligatorio.\n";
            correcto = false;
        } else {
            if (!Utiles.validaEmail(email)) {
                errores += "- El formato del email no es correcto.\n";
                correcto = false;
            }
        }

        String telefono = txt_TelefonoUsuario.getText();
        if (telefono.isEmpty()) {
            errores += "- El campo telefono es obligatorio.\n";
            correcto = false;
        } else {
            if (!Utiles.validarTelefono(telefono)) {
                errores += "- El formato del teléfono no es correcto.\n";
                correcto = false;
            }
        }

        Pais pais = combo_PaisUsuairio.getValue();
        if (pais == null) {
            errores += "- El campo pais es obligatorio.\n";
            correcto = false;
        }

        Set<UsuarioRol> rol = new HashSet<>();
        if(usuario == null){
            if (check_Admin.isSelected()) {
                check_Reserva.setDisable(false);
                check_Oferta.setDisable(false);
                rol.add(new UsuarioRol(new Usuario(nuevoUsuario.getId()), new Rol(1)));
            }
            if (check_Cliente.isSelected()) {
                rol.add(new UsuarioRol(new Usuario(nuevoUsuario.getId()), new Rol(2)));
            }
            if (check_Oferta.isSelected()) {
                check_Admin.setDisable(false);
                rol.add(new UsuarioRol(new Usuario(nuevoUsuario.getId()), new Rol(3)));
            }
            if (check_Reserva.isSelected()) {
                check_Admin.setDisable(false);
                rol.add(new UsuarioRol(new Usuario(nuevoUsuario.getId()), new Rol(4)));
            }
            if(check_BDAdmin.isSelected()){
                rol.add(new UsuarioRol(new Usuario(nuevoUsuario.getId()), new Rol(5)));
            }
        }else if (usuario!=null){
            if (check_Admin.isSelected()) {
                check_Reserva.setDisable(false);
                check_Oferta.setDisable(false);
                rol.add(new UsuarioRol(new Usuario(usuario.getId()), new Rol(1)));
            }
            if (check_Cliente.isSelected()) {
                rol.add(new UsuarioRol(new Usuario(usuario.getId()), new Rol(2)));
            }
            if (check_Oferta.isSelected()) {
                check_Admin.setDisable(false);
                rol.add(new UsuarioRol(new Usuario(usuario.getId()), new Rol(3)));
            }
            if (check_Reserva.isSelected()) {
                check_Admin.setDisable(false);
                rol.add(new UsuarioRol(new Usuario(usuario.getId()), new Rol(4)));
            }
            if(check_BDAdmin.isSelected()){
                rol.add(new UsuarioRol(new Usuario(usuario.getId()), new Rol(5)));
            }
        }


        if (correcto) {
            Integer id = usuario ==null ? null : usuario.getId();
            nuevoUsuario = new Usuario(id, dni, nombre, apellidos, user, Utiles.encriptarAMD5(password), email, telefono, pais, rol);
        } else {
            mostrarMensajes("Error", errores, Alert.AlertType.ERROR);
        }

        return nuevoUsuario;

    }

    private void mostrarMensajes(String titulo, String mensaje, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
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

    public void buscar(KeyEvent keyEvent) throws Exception {
        String texto = txtSearchUsuarios.getText();
        HttpClient<Usuario, Usuario[]> client = new HttpClient<>(Usuario[].class);
        if (texto.length() >= 3) {
            Usuario usuario = obtenenerCondicionesWhere(texto);

            Usuario[] lista = client.execute(URLApi.API_USER_SEARCH, usuario, "POST");
            ObservableList<Usuario> usuariosFiltrados = FXCollections.observableArrayList(lista);

            tableViewUsuarios.setItems(usuariosFiltrados);
            column_IdUsuario.setCellValueFactory(new PropertyValueFactory<>("dni"));
            column_Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            column_Apellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
            column_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
            column_Telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            column_Pais.setCellValueFactory(paisStringCellDataFeatures -> new SimpleStringProperty(paisStringCellDataFeatures.getValue().getPais().getNombre()));
        } else {
            inicializaTableView();
        }


    }

    private Usuario obtenenerCondicionesWhere(String texto) {
        return new Usuario(null, texto, texto, texto, null, null, texto, texto, null, null);
    }
}
