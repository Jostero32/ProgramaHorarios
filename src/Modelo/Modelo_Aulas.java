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

/**
 *
 * @author ASUS GAMER
 */
public class Modelo_Aulas {

    private Connection conn;

    public Modelo_Aulas(Connection conn) {
        this.conn = conn;
    }

    public boolean crearAula(Aula aula, String nombreBloque) {
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
                        return asociarResult > 0;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return false;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificarAula(Aula aula) {
        String sqlUpdateAula = "UPDATE aulas SET tipo = ?, capacidad = ? WHERE nombre = ?";

        try (PreparedStatement pstmtUpdateAula = conn.prepareStatement(sqlUpdateAula);) {

            // Actualizar los datos de la aula
            pstmtUpdateAula.setString(1, aula.getTipo());
            pstmtUpdateAula.setInt(2, aula.getCapacidad());
            pstmtUpdateAula.setString(3, aula.getNombre());
            int rowsAffectedAula = pstmtUpdateAula.executeUpdate();


            // Devolver true si al menos una fila se vio afectada en cualquiera de las actualizaciones
            return rowsAffectedAula > 0 ;
        } catch (Exception e) {
            System.out.println("Error al modificar el aula: " + e);
            return false;
        }
    }

    public boolean eliminarAula(String nombre) {
        String sql = "DELETE FROM aulas WHERE nombre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return aulas;
    }
}
