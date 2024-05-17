/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Clases.Materia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Bryan
 */
public class Modelo_Materia {

    private Connection conn;

    public Modelo_Materia(Connection connection) {
        this.conn = connection;
    }

    public boolean agregarMateria(Materia materia) {
        String sql = "INSERT INTO materias (id, nombre, codigo) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, materia.getId());
            pstmt.setString(2, materia.getNombre());
            pstmt.setString(3, materia.getCodigo());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                JOptionPane.showMessageDialog(null, "Error: El ID de materia ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ArrayList<Materia> obtenerTodasLasMaterias() throws SQLException {
        ArrayList<Materia> materias = new ArrayList<>();
        String sql = "SELECT * FROM materias";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                materias.add(new Materia(rs.getInt("id"), rs.getString("nombre"), rs.getString("codigo")));
            }
        }
        return materias;
    }

    public boolean actualizarMateria(Materia materiaModificada, Materia materiaAnterior) {
        String sql = "UPDATE materias SET id = ?, nombre = ?, codigo = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, materiaModificada.getId());
            pstmt.setString(2, materiaModificada.getNombre());
            pstmt.setString(3, materiaModificada.getCodigo());
            pstmt.setInt(4, materiaAnterior.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                JOptionPane.showMessageDialog(null, "Error: El ID de materia ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean eliminarMateria(Materia materia) {
        String sql = "DELETE FROM materias WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, materia.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
