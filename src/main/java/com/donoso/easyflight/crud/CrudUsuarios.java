package com.donoso.easyflight.crud;


import com.donoso.easyflight.pojos.Usuario;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUsuarios implements CRUD<Usuario> {

    public Usuario searchUserByUserAndPassword(Usuario element) {
        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();

        String sql = "SELECT * FROM usuario WHERE user = ? AND password = ?";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Usuario usuario = null;

        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, element.getUser());
            sentencia.setString(2, element.getPassword());
            resultado = sentencia.executeQuery();

            if (resultado.next()) {
                /*usuario = new Usuario(resultado.getInt("id"), resultado.getString("nombre"), resultado.getString("apellido"),
                        resultado.getString("user"), resultado.getString("password"), resultado.getBoolean("administrador"));*/

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (sentencia != null) {
                    sentencia.close();
                }
                cbd.cerrarConexion();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return usuario;
    }

    @Override
    public boolean add(Usuario element) {
        return false;
    }

    @Override
    public Usuario search(Usuario element) {
        return null;
    }

    @Override
    public boolean update(Usuario element) {
        return false;
    }

    @Override
    public boolean delete(Usuario element) {
        return false;
    }

    @Override
    public ObservableList<Usuario> listAll() {
        return null;
    }
}
