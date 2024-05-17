/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Clases.Bloque;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;

/**
 *
 * @author ASUS GAMER
 */
public class Modelo_Bloques {

    private ArrayList<Bloque> bloques;
    private Connection conn;

    public Modelo_Bloques(Connection conn) {
        this.conn = conn;
        this.bloques = new ArrayList<>();
    }

    public boolean crearBloque(Bloque bloque) {
        String sql = "INSERT INTO bloques (nombre) VALUES (?)";
        try (PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)) {
            pstmt.setString(1, bloque.getNombre());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {

            return false;
        }
    }

    public boolean modificarBloque(String nombreAnterior, String nombreNuevo) {
        String sql = "UPDATE bloques SET nombre = ? WHERE nombre = ?";
        try (PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreNuevo);
            pstmt.setString(2, nombreAnterior);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {

            return false;
        }
    }

    public boolean eliminarBloque(String nombre) {
        String sql = "DELETE FROM bloques WHERE nombre = ?";
        try (PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {

            return false;
        }
    }

    public Bloque verBloque(int id) {
        String sql = "SELECT * FROM bloques WHERE id = ?";
        try (PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                return new Bloque(nombre);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public ArrayList<Bloque> verTodosLosBloques() {
        String sql = "SELECT * FROM bloques";
        try (Statement stmt = (Statement) conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                Bloque bloque = new Bloque(nombre);
                bloques.add(bloque);
            }
        } catch (Exception e) {
            return null;
        }
        return bloques;
    }
}
