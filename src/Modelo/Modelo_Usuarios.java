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
 * @author Usuario
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
        String sql = "INSERT INTO usuarios (username, password, tipo) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, usuario.getUsuario());
            pstmt.setString(2, usuario.getClave());
            pstmt.setString(3, usuario.getTipo());
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                JOptionPane.showMessageDialog(null, "Error: El usuario ya existe.", "Error", 0);
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ArrayList<Usuario> obtenerTodosLosUsuarios() throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(new Usuario(rs.getString("usuario"), rs.getString("clave"), rs.getString("tipo")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET username = ?, password = ?, tipo = ? ";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getUsuario());
            pstmt.setString(2, usuario.getClave());
            pstmt.setString(3, usuario.getTipo());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                JOptionPane.showMessageDialog(null, "Error: El username ya existe.", "Error", 0);
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean eliminarUsuario(Usuario U) {
        String sql = "DELETE FROM usuarios WHERE usuario = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, U.getUsuario());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
