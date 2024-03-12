package com.procedimientosAlmacenados;

import java.sql.*;

public class Conexion {

    //constructor de tipo privado
    private Conexion() {

    }
    private static final String url = "jdbc:mysql://localhost:3306/escuela";
    private static final String user = "root";
    private static final String pas = "1234";

    private static Conexion instancia;
    private static Connection conexion;

    public Connection conectarBd() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, user, pas);
            return conexion;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar => " + e.toString());
        }

        return conexion;
    }

    public void cerrarResultado(ResultSet resultado) {
        try {
            resultado.close();

        } catch (SQLException e) {
            System.out.println("Error al cerrar resultado => " + e.toString());
        }
    }

    public void cerrarConexion(Connection conexion) {
        try {
            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error al cerar la conexion => " + e.toString());
        }
    }

    public void cerrarConsultaPreparada(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar preparedStatement.close(); =>  " + e.toString());
        }
    }

    //PATRON SINGLETON
    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

}
