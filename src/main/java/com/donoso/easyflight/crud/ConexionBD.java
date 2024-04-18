package com.donoso.easyflight.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionBD implements Configuracion {
    Connection conexion;
    String usuario, password;

    public ConexionBD() {
        this.usuario = "root";
        this.password = "";

    }

    public ConexionBD(String usuario, String password) {
        // super();
        this.usuario = usuario;
        this.password = password;
    }

    public Connection abrirConexion() {
        try {

            //Cargamos el driver
            //Class.forName("com.mysql.jdbc.Driver");//Ojo!! Antiguo driver mysql-connector-java-5.1.37-bin.jar
            Class.forName("com.mysql.cj.jdbc.Driver");//actual mysql-connector-j-8.0.31.jar

            try {
                conexion = (Connection) DriverManager.getConnection("jdbc:mysql://" + DBHOST + ":3306/" + DBNAME,
                        this.usuario, this.password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return conexion;

    }

    public boolean cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
