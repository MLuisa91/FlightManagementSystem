package com.donoso.easyflight.crud;

import com.donoso.easyflight.pojos.Avion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrudAviones implements CRUD<Avion> {
    @Override
    public boolean add(Avion element) {
        ConexionBD cbd = new ConexionBD();

        Connection conexion = cbd.abrirConexion();

        String sql = "INSERT INTO `avion` (`id`, `modelo`, `pasajeros`) VALUES (?, ?, ?)";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, element.getId());
            sentencia.setString(2, element.getModelo());
            sentencia.setInt(3, element.getPasajeros());

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;  // Devuelve true si se insertó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (sentencia != null) {
                    sentencia.close();
                }
                cbd.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public Avion findById(Avion element) {
        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();

        String sql = "SELECT * FROM avion WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, element.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Avion(resultSet.getString("id"), resultSet.getString("modelo"), resultSet.getInt("pasajeros"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Avion search(Avion element) {
        return null;
    }

    @Override
    public boolean update(Avion element) {
        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();

        String sql = "UPDATE avion SET modelo = ?, pasajeros = ? WHERE id = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, element.getModelo());
            sentencia.setInt(2, element.getPasajeros());
            sentencia.setString(3, element.getId());

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (sentencia != null) {
                    sentencia.close();
                }
                cbd.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        //return false;
    }

    @Override
    public boolean delete(Avion element) {
        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();

        if (tieneVuelosAsociados(conexion, element.getId())) {
            return false;
        }

        String sql = "DELETE FROM avion WHERE id = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, element.getId());

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (sentencia != null) {
                    sentencia.close();
                }
                cbd.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        //return false;
    }

    private boolean tieneVuelosAsociados(Connection conexion, String avionID) {
        String sql = "SELECT COUNT(*) FROM vuelo WHERE avion = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, avionID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<Avion> listAll() {
        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();
        ResultSet result;
        List<Avion> lista = new ArrayList<>();
        String sql = "SELECT * FROM avion";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sql);
            result = sentencia.executeQuery();

            while (result.next()) {
                lista.add(new Avion(result.getString("id"),
                        result.getString("modelo"),
                        result.getInt("pasajeros")));
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

    public List<Avion> searchAviones(Map<String, String> campos) {
        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();

        StringBuilder sql = new StringBuilder("SELECT * " +
                "FROM avion WHERE ");
        List<String> condiciones = new ArrayList<>();

        for (Map.Entry<String, String> entry : campos.entrySet()) {
            condiciones.add(entry.getKey() + " like ?");
        }

        sql.append(String.join(" OR ", condiciones));

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Avion> avionesEncontrados = new ArrayList<>();

        try {
            sentencia = conexion.prepareStatement(sql.toString());

            int index = 1;
            for (String valor : campos.values()) {
                sentencia.setString(index++, valor);
            }

            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Avion avion = new Avion();
                avion.setId(resultado.getString("id"));
                avion.setModelo(resultado.getString("modelo"));
                avion.setPasajeros(resultado.getInt("pasajeros"));

                avionesEncontrados.add(avion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return avionesEncontrados;
    }

    public ObservableList<Avion> listAllObservable() {
        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();
        ResultSet result;
        ObservableList<Avion> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM avion";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sql);
            result = sentencia.executeQuery();

            while (result.next()) {
                lista.add(new Avion(result.getString("id"),
                        result.getString("modelo"),
                        result.getInt("pasajeros")));
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
