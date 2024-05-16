/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Clases.Docente;
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
public class Modelo_Docente {

    private Connection conn;

    public Modelo_Docente(Connection connection) {
        this.conn = connection;
    }

    public boolean agregarDocente(Docente docente) {
        String sql = "INSERT INTO docentes (id, nombre, correo) VALUES (?, ?, ?)";
        try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, docente.getId());
            pstmt.setString(2, docente.getNombre());
            pstmt.setString(3, docente.getEmail());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                JOptionPane.showMessageDialog(null, "Error: El ID de docente ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ArrayList<Docente> obtenerTodosLosDocentes() throws SQLException {
        ArrayList<Docente> docentes = new ArrayList<>();
        String sql = "SELECT * FROM docentes";
        try ( PreparedStatement pstmt = conn.prepareStatement(sql);  ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                docentes.add(new Docente(rs.getInt("id"), rs.getString("nombre"), rs.getString("correo")));
            }
        }
        return docentes;
    }

    public boolean actualizarDocente(Docente docenteModificado, Docente docenteAnterior) {
        String sql = "UPDATE docentes SET id = ?, nombre = ?, correo = ? WHERE id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, docenteModificado.getId());
            pstmt.setString(2, docenteModificado.getNombre());
            pstmt.setString(3, docenteModificado.getEmail());
            pstmt.setInt(4, docenteAnterior.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                JOptionPane.showMessageDialog(null, "Error: El ID de docente ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean eliminarDocente(Docente docente) {
        String sql = "DELETE FROM docentes WHERE id = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, docente.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
