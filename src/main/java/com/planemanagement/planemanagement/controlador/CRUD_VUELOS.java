package com.planemanagement.planemanagement.controlador;


import com.planemanagement.planemanagement.modelo.Avion;
import com.planemanagement.planemanagement.modelo.Vuelo;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CRUD_VUELOS implements CRUD<Vuelo> {
    @Override
    public boolean add(Vuelo element) {
        ConexionBD cbd = new ConexionBD();

        Connection conexion = cbd.abrirConexion();

        String sql = "INSERT INTO `vuelo` (`id`, `origen`, `destino`, `fechaSalida`, `horaSalida`, `horaLlegada`, `avion`) VALUES" +
                "(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, element.getId());
            sentencia.setString(2, element.getOrigen());
            sentencia.setString(3, element.getDestino());
            sentencia.setDate(4, Date.valueOf(element.getFechaSalida()));
            sentencia.setTime(5, Time.valueOf(element.getHoraSalida().toLocalTime()));
            sentencia.setTime(6, Time.valueOf(element.getHoraLlegada().toLocalTime()));
            sentencia.setString(7, element.getAvion().getId());

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;  // Devuelve true si se insertó al menos una fila
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

    @Override
    public Vuelo search(Vuelo element) {
        return null;
    }

    public List<Vuelo> buscarPorCampos(Map<String, String> campos) {
        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();

        StringBuilder sql = new StringBuilder("SELECT * FROM vuelo WHERE ");
        List<String> condiciones = new ArrayList<>();

        for (Map.Entry<String, String> entry : campos.entrySet()) {
            condiciones.add(entry.getKey() + " = ?");
        }

        sql.append(String.join(" AND ", condiciones));

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Vuelo> vuelosEncontrados = new ArrayList<>();

        try {
            sentencia = conexion.prepareStatement(sql.toString());

            int index = 1;
            for (String valor : campos.values()) {
                sentencia.setString(index++, valor);
            }

            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Vuelo vuelo = new Vuelo();
                vuelo.setId(resultado.getString("id"));
                vuelo.setOrigen(resultado.getString("origen"));
                vuelo.setDestino(resultado.getString("destino"));
                vuelo.setFechaSalida(resultado.getDate("fechaSalida").toLocalDate());
                vuelo.setHoraSalida(Time.valueOf(resultado.getTime("horaSalida").toLocalTime()));
                vuelo.setHoraLlegada(Time.valueOf(resultado.getTime("horaLlegada").toLocalTime()));
                // Obtener el avion por su id
                Avion avion = obtenerAvionPorId(resultado.getString("avion"));
                vuelo.setAvion(avion);

                vuelosEncontrados.add(vuelo);
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

        return vuelosEncontrados;
    }

    @Override
    public boolean update(Vuelo element) {
        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();

        String sql = "UPDATE vuelo SET origen = ?, destino = ?, fechaSalida = ?, horaSalida = ?, horaLlegada = ?, avion = ? WHERE id = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, element.getOrigen());
            sentencia.setString(2, element.getDestino());
            sentencia.setDate(3, Date.valueOf(element.getFechaSalida()));
            sentencia.setTime(4, Time.valueOf(element.getHoraSalida().toLocalTime()));
            sentencia.setTime(5, Time.valueOf(element.getHoraLlegada().toLocalTime()));
            sentencia.setString(6, element.getAvion().getId());
            sentencia.setString(7, element.getId());

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;
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
        //return false;
    }

    @Override
    public boolean delete(Vuelo element) {

        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();

        String sql = "DELETE FROM vuelo WHERE id = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, element.getId());

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;
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
        //return false;
    }

    @Override
    public ObservableList<Vuelo> listAll() {
        return null;
    }

    public Avion obtenerAvionPorId(String idAvion) {
        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();

        String sql = "SELECT * FROM avion WHERE id = ?";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Avion avion = null;

        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, idAvion);
            resultado = sentencia.executeQuery();

            if (resultado.next()) {
                avion = new Avion();
                avion.setId(resultado.getString("id"));
                avion.setModelo(resultado.getString("modelo"));
                avion.setPasajeros(resultado.getInt("pasajeros"));
                // Puedes agregar más campos según la estructura de tu tabla avion
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

        return avion;
    }
}
