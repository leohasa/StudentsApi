package com.ipc2.studentsapi.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static Connection conexion = null;
    private static final String url = "jdbc:mariadb://localhost:3306/studentsdb";
    private static final String user = "root";
    private static final String password = "Mysql123";

    private Conexion() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conexion = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al registrar el driver de MySQL: " + e);
        }
    }

    public static Connection obtenerConexion() {
        if(conexion == null) {
            new Conexion();
            System.out.println("Conexión exitosa");
        }
        return conexion;
    }
}
