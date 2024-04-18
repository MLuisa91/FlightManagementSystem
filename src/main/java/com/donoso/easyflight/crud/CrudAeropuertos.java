package com.donoso.easyflight.crud;

import com.donoso.easyflight.pojos.Aeropuerto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudAeropuertos implements CRUD<Aeropuerto> {
    @Override
    public boolean add(Aeropuerto element) {
        return false;
    }

    @Override
    public Aeropuerto search(Aeropuerto element) {
        return null;
    }

    @Override
    public boolean update(Aeropuerto element) {
        return false;
    }

    @Override
    public boolean delete(Aeropuerto element) {
        return false;
    }

    @Override
    public ObservableList<Aeropuerto> listAll() {
        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();
        ResultSet result;
        ObservableList<Aeropuerto> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM aeropuerto";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sql);
            result = sentencia.executeQuery();

            while (result.next()) {
                lista.add(new Aeropuerto(result.getInt("id"),
                        result.getString("nombre")));
            }

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (sentencia != null) {
                    sentencia.close();
                }
                cbd.cerrarConexion();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
