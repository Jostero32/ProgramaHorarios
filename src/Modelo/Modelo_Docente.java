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
        String sql = "INSERT INTO docentes (nombre, cedula) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, docente.getNombre());
            pstmt.setInt(2, docente.getCedula());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                JOptionPane.showMessageDialog(null, "Error: El docente ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ArrayList<Docente> obtenerTodosLosDocentes() throws SQLException {
        ArrayList<Docente> docentes = new ArrayList<>();
        String sql = "SELECT * FROM docentes";
        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                docentes.add(new Docente(rs.getString("nombre"), rs.getInt("cedula")));
            }
        }
        return docentes;
    }

    public boolean actualizarDocente(Docente docenteModificado, String docenteAnterior) {
        String sql = "UPDATE docentes SET nombre = ?, cedula = ? WHERE nombre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, docenteModificado.getNombre());
            pstmt.setInt(2, docenteModificado.getCedula());
            pstmt.setString(3, docenteAnterior);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                JOptionPane.showMessageDialog(null, "Error: El nombre o cedula del docente ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean eliminarDocente(Docente docente) {
        String sql = "DELETE FROM docentes WHERE nombre = ? AND cedula = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, docente.getNombre());
            pstmt.setInt(2, docente.getCedula());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
