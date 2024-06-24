/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Clases.Aula;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS GAMER
 */
public class Modelo_Aulas {

    private Connection conn;

    public Modelo_Aulas(Connection conn) {
        this.conn = conn;
    }

public void crearAula(Aula aula, String nombreBloque) {
    // Verificar si ya existe un aula con el mismo nombre en el mismo bloque
    if (existeAulaEnBloque(aula.getNombre(), nombreBloque)) {
        JOptionPane.showMessageDialog(null, "No se pudo crear porque ya existe un aula con ese nombre en el mismo bloque.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String sql = "INSERT INTO aulas (nombre, tipo, capacidad) VALUES (?, ?, ?)";
    try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        pstmt.setString(1, aula.getNombre());
        pstmt.setString(2, aula.getTipo());
        pstmt.setInt(3, aula.getCapacidad());
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int aulaId = rs.getInt(1);
                String asociarSql = "INSERT INTO bloque_aula (bloque_id, aula_id) VALUES ((SELECT id FROM bloques WHERE nombre = ?), ?)";
                try (PreparedStatement asociarPstmt = conn.prepareStatement(asociarSql)) {
                    asociarPstmt.setString(1, nombreBloque);
                    asociarPstmt.setInt(2, aulaId);
                    int asociarResult = asociarPstmt.executeUpdate();
                    if (asociarResult > 0) {
                        JOptionPane.showMessageDialog(null, "Se ha creado una nueva Aula");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al asociar el Aula con el Bloque");
                }
            }
        }
    } catch (Exception e) {
   }
}

private boolean existeAulaEnBloque(String nombreAula, String nombreBloque) {
    String sql = "SELECT COUNT(*) AS count FROM aulas a " +
                 "INNER JOIN bloque_aula ba ON a.id = ba.aula_id " +
                 "INNER JOIN bloques b ON ba.bloque_id = b.id " +
                 "WHERE a.nombre = ? AND b.nombre = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, nombreAula);
        pstmt.setString(2, nombreBloque);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt("count");
            return count > 0; // Retorna true si hay al menos un registro con el nombre de aula especificado en el mismo bloque
        }
    } catch (Exception e) {
    }
    return false;
}


public void modificarAula(String nombreAula, String nuevoNombre, String tipo, int capacidad, String nombreBloque) {
    // Consulta para obtener el id del aula
    String sqlSelectId = "SELECT a.id FROM aulas a, bloque_aula ba, bloques b WHERE a.nombre = ? AND a.id = ba.aula_id AND ba.bloque_id = b.id AND b.nombre = ?";
    
    // Consulta para actualizar el aula usando el id obtenido
    String sqlUpdateAula = "UPDATE aulas SET nombre = ?, tipo = ?, capacidad = ? WHERE id = ?";

    try {
        conn.setAutoCommit(false); // Iniciar transacción
        
        // Obtener el ID del aula
        try (PreparedStatement pstmtSelectId = conn.prepareStatement(sqlSelectId)) {
            pstmtSelectId.setString(1, nombreAula);
            pstmtSelectId.setString(2, nombreBloque);
            ResultSet rs = pstmtSelectId.executeQuery();
            if (rs.next()) {
                int idAula = rs.getInt("id");

                // Actualizar el aula
                try (PreparedStatement pstmtUpdateAula = conn.prepareStatement(sqlUpdateAula)) {
                    pstmtUpdateAula.setString(1, nuevoNombre);
                    pstmtUpdateAula.setString(2, tipo);
                    pstmtUpdateAula.setInt(3, capacidad);
                    pstmtUpdateAula.setInt(4, idAula);
                    int rowsAffectedAula = pstmtUpdateAula.executeUpdate();

                    if (rowsAffectedAula > 0) {
                        conn.commit(); // Confirmar transacción
                        JOptionPane.showMessageDialog(null, "Se ha modificado el Aula");
                    } else {
                        conn.rollback(); // Revertir transacción si no se modificó ninguna fila
                        JOptionPane.showMessageDialog(null, "No se encontró el Aula a modificar");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el Aula o el Bloque especificado");
            }
        }
    } catch (Exception e) {
        try {
            conn.rollback(); // Revertir transacción en caso de excepción
        } catch (Exception ex) {
            // Manejar excepción al hacer rollback
        }
        JOptionPane.showMessageDialog(null, "Error al modificar el Aula: " + e.getMessage());
    } finally {
        try {
            conn.setAutoCommit(true); // Restaurar auto-commit por defecto
        } catch (Exception ex) {
            // Manejar excepción al restaurar auto-commit
        }
    }
}



    public void eliminarAula(String nombreAula, String nombreBloque) {
        String sql = "DELETE aulas FROM aulas INNER JOIN bloque_aula ba ON aulas.id = ba.aula_id INNER JOIN bloques b ON ba.bloque_id = b.id WHERE aulas.nombre = ? AND b.nombre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreAula);
            pstmt.setString(2, nombreBloque);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Se ha eliminado el Aula: " + nombreAula);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el Aula a eliminar: " + nombreAula);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el Aula: " + e.getMessage());
        }
    }

    public Aula verAula(String nombre) {
        String sql = "SELECT a.nombre, a.tipo, a.capacidad, b.nombre AS nombre_bloque "
                + "FROM aulas a INNER JOIN bloque_aula ba ON a.id = ba.aula_id "
                + "INNER JOIN bloques b ON ba.bloque_id = b.id "
                + "WHERE a.nombre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                int capacidad = rs.getInt("capacidad");
                String nombreBloque = rs.getString("nombre_bloque");
                return new Aula(nombreBloque, nombre, capacidad, tipo);
            }
        } catch (Exception e) {

        }
        return null;
    }

    public ArrayList<Aula> verAulasPorBloque(String nombreBloque) {
        String sql = "SELECT a.nombre, a.tipo, a.capacidad "
                + "FROM aulas a INNER JOIN bloque_aula ba ON a.id = ba.aula_id "
                + "INNER JOIN bloques b ON ba.bloque_id = b.id "
                + "WHERE b.nombre = ?";
        ArrayList<Aula> aulas = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreBloque);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                int capacidad = rs.getInt("capacidad");
                // Agregando el nombre del bloque al constructor de Aula
                Aula aula = new Aula(nombreBloque, nombre, capacidad, tipo);
                aulas.add(aula);
            }
        } catch (Exception e) {

        }
        return aulas;
    }
}