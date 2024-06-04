/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class Mysql {


   private static final String URL = "jdbc:mysql://localhost:3306/programaHorarios?characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "081005";

    private Connection connection;

    public Mysql() {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos MySQL.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver de MySQL: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos MySQL: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

}
