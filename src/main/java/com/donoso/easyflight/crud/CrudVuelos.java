package com.donoso.easyflight.crud;


import com.donoso.easyflight.pojos.Aeropuerto;
import com.donoso.easyflight.pojos.Avion;
import com.donoso.easyflight.pojos.Vuelo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrudVuelos implements CRUD<Vuelo> {
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
            sentencia.setInt(2, element.getOrigen().getId());
            sentencia.setInt(3, element.getDestino().getId());
            sentencia.setDate(4, Date.valueOf(element.getFechaSalida()));
            sentencia.setTime(5, Time.valueOf(element.getHoraSalida()));
            sentencia.setTime(6, Time.valueOf(element.getHoraLlegada()));
            sentencia.setString(7, element.getAvion().getId());

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

    @Override
    public Vuelo search(Vuelo element) {
        return null;
    }

    public List<Vuelo> searchVuelos(Map<String, String> campos) {
        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();

        // **** PODEMOS OBTENER LOS AVIONES HACIENDO UN INNER JOIN
        StringBuilder sql = new StringBuilder("SELECT * " +
                "FROM vuelo v " +
                "inner join avion a on v.avion = a.id " +
                "inner join aeropuerto aeo on v.origen = aeo.id " +
                "inner join aeropuerto aed on v.destino = aed.id  WHERE ");
        List<String> condiciones = new ArrayList<>();

        for (Map.Entry<String, String> entry : campos.entrySet()) {
            condiciones.add(entry.getKey() + " like ?");
        }

        sql.append(String.join(" OR ", condiciones));

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
                vuelo.setId(resultado.getString("v.id"));
                vuelo.setOrigen(new Aeropuerto(resultado.getInt("v.origen"), resultado.getString("aeo.nombre")));
                vuelo.setDestino(new Aeropuerto(resultado.getInt("v.destino"), resultado.getString("aed.nombre")));
                vuelo.setFechaSalida(resultado.getDate("v.fechaSalida").toLocalDate());
                vuelo.setHoraSalida(resultado.getTime("v.horaSalida").toLocalTime());
                vuelo.setHoraLlegada(resultado.getTime("v.horaLlegada").toLocalTime());
                vuelo.setAvion(new Avion(resultado.getString("a.id"), resultado.getString("a.modelo"), resultado.getInt("a.pasajeros")));

                vuelosEncontrados.add(vuelo);
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
            sentencia.setInt(1, element.getOrigen().getId());
            sentencia.setInt(2, element.getDestino().getId());
            sentencia.setDate(3, Date.valueOf(element.getFechaSalida()));
            sentencia.setTime(4, Time.valueOf(element.getHoraSalida()));
            sentencia.setTime(5, Time.valueOf(element.getHoraLlegada()));
            sentencia.setString(6, element.getAvion().getId());
            sentencia.setString(7, element.getId());

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
    public ObservableList<Vuelo> listAll() {

        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();
        ResultSet result;
        ObservableList<Vuelo> lista = FXCollections.observableArrayList();
        String sql = "SELECT * " +
                "FROM vuelo v " +
                "inner join avion a on v.avion = a.id " +
                "inner join aeropuerto aeo on v.origen = aeo.id " +
                "inner join aeropuerto aed on v.destino = aed.id;";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sql);
            result = sentencia.executeQuery();

            while (result.next()) {
                lista.add(new Vuelo(result.getString("v.id"),
                        new Aeropuerto(result.getInt("origen"), result.getString("aeo.nombre")),
                        new Aeropuerto(result.getInt("destino"), result.getString("aed.nombre")),
                        result.getDate("fechaSalida").toLocalDate(),
                        result.getTime("horaSalida").toLocalTime(),
                        result.getTime("horaLlegada").toLocalTime(),
                        new Avion(result.getString("avion"), result.getString("a.modelo"), result.getInt("a.pasajeros"))));
            }

            return lista;
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

        return avion;
    }

    public Integer vuelosTotales() {
        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();
        ObservableList<Vuelo> lista = FXCollections.observableArrayList();

        String sql = "SELECT COUNT(*) AS total_vuelos FROM vuelo";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            sentencia = conexion.prepareStatement(sql);

            resultado = sentencia.executeQuery();

            if (resultado.next()) {
                return resultado.getInt("total_vuelos");
            } else {
                return 0;
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
    }

    public Integer vuelosTotalesAnioActual() {
        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();

        String sql = "SELECT COUNT(*) AS total_vuelos_año_actual FROM vuelo " +
                "WHERE YEAR(fechaSalida) = YEAR(CURDATE())";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            sentencia = conexion.prepareStatement(sql);
            resultado = sentencia.executeQuery();

            if (resultado.next()) {
                return resultado.getInt("total_vuelos_año_actual");
            } else {
                return 0;
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
    }

    public Integer vuelosTotalesMesActual() {
        ConexionBD cbd = new ConexionBD();
        Connection conexion = cbd.abrirConexion();

        String sql = "SELECT COUNT(*) AS total_vuelos_mes_actual FROM vuelo WHERE YEAR" +
                "(fechaSalida) = YEAR(CURDATE()) AND MONTH(fechaSalida) = MONTH(CURDATE())";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            sentencia = conexion.prepareStatement(sql);
            resultado = sentencia.executeQuery();

            if (resultado.next()) {
                return resultado.getInt("total_vuelos_mes_actual");
            } else {
                return 0;
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
    }

}
