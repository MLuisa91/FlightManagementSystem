package com.donoso.easyflight.controlador;

import com.donoso.easyflight.contexto.UsuarioHolder;
import com.donoso.easyflight.pojos.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageRespaldoController implements Initializable {
    public TableView tableViewRespaldo;
    public TableColumn column_nombreRespaldo;
    public TableColumn column_fechaRespaldo;
    public TextField txtSearchRespaldo;
    public Button buttont_eliminarRespaldo;
    public Button buttont_restaurarRespaldo;

    public void buscar(KeyEvent keyEvent) {
    }

    public void eliminarRespaldo(ActionEvent event) {
    }

    public void restaurarRespaldo(ActionEvent event) {
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

    private void inicializaTableView() {
    }
}
