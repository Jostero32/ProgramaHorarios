/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Clases.Usuario;
import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @autor Usuario
 */
public class Modelo_Usuarios {

    private Connection conn;

    public Modelo_Usuarios(Connection connection) {
        this.conn = connection;
    }

    public Usuario entrarLogin(String usuario, String clave) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND clave = ?";
        PreparedStatement pstmt = this.conn.prepareStatement(sql);
        pstmt.setString(1, usuario);
        pstmt.setString(2, clave);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return new Usuario(rs.getString("usuario"), rs.getString("clave"), rs.getString("tipo"));
        }
        return null;
    }

    public boolean agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (usuario, clave, tipo) VALUES (?, ?, ?)";
        try ( PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, usuario.getUsuario());
            pstmt.setString(2, usuario.getClave());
            pstmt.setString(3, usuario.getTipo());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Usuario> obtenerTodosLosUsuarios() throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try ( PreparedStatement pstmt = conn.prepareStatement(sql);  ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(new Usuario(rs.getString("usuario"), rs.getString("clave"), rs.getString("tipo")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return usuarios;
    }

    public boolean actualizarUsuario(Usuario usuario, String usuarioAnterior) {
        String sql = "UPDATE usuarios SET usuario = ?, clave = ?, tipo = ? WHERE usuario = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, usuario.getUsuario());
            pstmt.setString(2, usuario.getClave());
            pstmt.setString(3, usuario.getTipo());
            pstmt.setString(4, usuarioAnterior);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarUsuario(Usuario usuario) {
        String sql = "DELETE FROM usuarios WHERE usuario = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getUsuario());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
