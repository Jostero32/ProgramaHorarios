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
 * @autor Bryan
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
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Docente agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo agregar el docente.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                JOptionPane.showMessageDialog(null, "Error: El docente ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener los docentes.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Docente actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el docente.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                JOptionPane.showMessageDialog(null, "Error: El nombre o cédula del docente ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
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
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Docente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el docente.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return false;
    }
}
