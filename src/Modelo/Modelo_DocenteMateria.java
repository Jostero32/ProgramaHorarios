package Modelo;

import Clases.DocenteMateria;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Modelo_DocenteMateria {

    private Connection conn;

    public Modelo_DocenteMateria(Connection connection) {
        this.conn = connection;
    }

    public boolean agregarDocenteMateria(DocenteMateria docenteMateria) throws SQLException {
    String sql = "INSERT INTO docente_materia (docente_id, materia_id, paralelo) VALUES (?, ?, ?)";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, docenteMateria.getDocenteId());
        pstmt.setInt(2, docenteMateria.getMateriaId());
        pstmt.setString(3, String.valueOf(docenteMateria.getParalelo())); // Añadir el paralelo
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        if (e.getSQLState().equals("23000")) {
            JOptionPane.showMessageDialog(null, "Error: La relación docente-materia ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            throw e;
        }
    }
    return false;
}


    public ArrayList<DocenteMateria> obtenerTodasLasRelaciones() throws SQLException {
    String sql = "SELECT dm.docente_id, d.nombre as docente_nombre, dm.materia_id, m.nombre as materia_nombre, dm.paralelo " +
                 "FROM docente_materia dm " +
                 "JOIN docentes d ON dm.docente_id = d.id " +
                 "JOIN materias m ON dm.materia_id = m.id";
    ArrayList<DocenteMateria> lista = new ArrayList<>();
    try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            int docenteId = rs.getInt("docente_id");
            String docenteNombre = rs.getString("docente_nombre");
            int materiaId = rs.getInt("materia_id");
            String materiaNombre = rs.getString("materia_nombre");
            String paraleloStr = rs.getString("paralelo");
            char paralelo = paraleloStr != null && !paraleloStr.isEmpty() ? paraleloStr.charAt(0) : ' '; // Manejar null o cadena vacía
            lista.add(new DocenteMateria(docenteId, docenteNombre, materiaId, materiaNombre, paralelo));
        }
    }
    return lista;
}





    public boolean eliminarDocenteMateria(int docenteId, int materiaId) throws SQLException {
        String sql = "DELETE FROM docente_materia WHERE docente_id = ? AND materia_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, docenteId);
            pstmt.setInt(2, materiaId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean modificarDocenteMateria(int oldDocenteId, int oldMateriaId, char oldParalelo, DocenteMateria nuevaRelacion) throws SQLException {
    String sql = "UPDATE docente_materia SET docente_id = ?, materia_id = ?, paralelo = ? WHERE docente_id = ? AND materia_id = ? AND paralelo = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, nuevaRelacion.getDocenteId());
        pstmt.setInt(2, nuevaRelacion.getMateriaId());
        pstmt.setString(3, String.valueOf(nuevaRelacion.getParalelo())); // Convertir char a String
        pstmt.setInt(4, oldDocenteId);
        pstmt.setInt(5, oldMateriaId);
        pstmt.setString(6, String.valueOf(oldParalelo)); 
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;
    }
}


public int obtenerIdDocentePorNombre(String nombreDocente) {
    String sql = "SELECT id FROM docentes WHERE nombre = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, nombreDocente.trim());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al obtener el ID del docente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return -1; // Devuelve -1 si no se encuentra el ID
}

public int obtenerIdMateriaPorNombre(String nombreMateria) {
    String sql = "SELECT id FROM materias WHERE nombre = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, nombreMateria.trim());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al obtener el ID de la materia: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return -1; // Devuelve -1 si no se encuentra el ID
}

    public ArrayList<String> obtenerDocentes() throws SQLException {
        String sql = "SELECT id, nombre FROM docentes";
        ArrayList<String> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(rs.getInt("id") + " - " + rs.getString("nombre"));
            }
        }
        return lista;
    }

    public ArrayList<String> obtenerMaterias() throws SQLException {
        String sql = "SELECT id, nombre FROM materias";
        ArrayList<String> lista = new ArrayList<>();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(rs.getInt("id") + " - " + rs.getString("nombre"));
            }
        }
        return lista;
    }
}
