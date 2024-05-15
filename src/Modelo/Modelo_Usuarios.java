/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Clases.Usuario;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author Usuario
 */
public class Modelo_Usuarios {

    private Connection connection;

    public Modelo_Usuarios(Connection connection) {
        this.connection = connection;
    }

    public Usuario entrarLogin(String usuario,String clave) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND clave = ?";
        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, usuario);
        pstmt.setString(2, clave);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return new Usuario(rs.getString("usuario"), rs.getString("clave"), rs.getString("tipo"));
        }
        return null;

    }

}
