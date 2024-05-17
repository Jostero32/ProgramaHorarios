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
        String sql = "INSERT INTO materias (nombre, codigo) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, materia.getNombre());
            pstmt.setString(2, materia.getCodigo());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                JOptionPane.showMessageDialog(null, "Error: La materia ya existe.", "Error", 0);
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
                materias.add(new Materia(rs.getString("nombre"), rs.getString("codigo")));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return materias;
    }

    public boolean actualizarMateria(Materia materia, String materiaAnterior) {
        String sql = "UPDATE materias SET nombre = ?, codigo = ? WHERE nombre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, materia.getNombre());
            pstmt.setString(2, materia.getCodigo());
            pstmt.setString(3, materiaAnterior);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                JOptionPane.showMessageDialog(null, "Error: La materia ya existe.", "Error", 0);
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean eliminarMateria(Materia materia) {
        String sql = "DELETE FROM materias WHERE nombre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, materia.getNombre());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
