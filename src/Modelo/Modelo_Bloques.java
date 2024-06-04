/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Clases.Aula;
import Clases.Bloque;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ASUS GAMER
 */
public class Modelo_Bloques {

    private Modelo_Aulas modeloAulas;
    private ArrayList<Bloque> bloques;
    private Connection conn;

    public Modelo_Bloques(Connection conn) {
        this.conn = conn;
        this.bloques = new ArrayList<>();
        this.modeloAulas = new Modelo_Aulas(conn);
    }

    public boolean crearBloque(Bloque bloque) {
        String sql = "INSERT INTO bloques (nombre) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bloque.getNombre());
            int rowsAffected = pstmt.executeUpdate();

            // Crear aulas si el bloque tiene aulas
            if (!bloque.getAulas().isEmpty()) {
                for (Aula aula : bloque.getAulas()) {
                    modeloAulas.crearAula(aula, bloque.getNombre());
                }
            }

            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificarBloque(String nombreAnterior, String nombreNuevo) {
        String sql = "UPDATE bloques SET nombre = ? WHERE nombre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreNuevo);
            pstmt.setString(2, nombreAnterior);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarBloque(String nombre) {
        String sql = "DELETE FROM bloques WHERE nombre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Bloque verBloque(String nombre) {
        String sql = "SELECT * FROM bloques WHERE nombre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nombreBD = rs.getString("nombre");
                ArrayList<Aula> aulas = modeloAulas.verAulasPorBloque(nombreBD);
                return new Bloque(nombreBD, aulas);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public ArrayList<Bloque> verTodosLosBloques() {
        String sql = "SELECT * FROM bloques";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                ArrayList<Aula> aulas = modeloAulas.verAulasPorBloque(nombre);
                Bloque bloque = new Bloque(nombre, aulas);
                bloques.add(bloque);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bloques;
    }

    public ArrayList<Aula> obtenerAulasPorBloque(String nombreBloque) {
        String sql = "SELECT a.nombre, a.tipo, a.capacidad FROM aulas a JOIN bloque_aula ba ON a.id = ba.aula_id JOIN bloques b ON ba.bloque_id = b.id WHERE b.nombre = ?";
        ArrayList<Aula> aulas = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreBloque);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                int capacidad = rs.getInt("capacidad");
                // Ajustando la instancia de Aula para incluir el nombre del bloque
                Aula aula = new Aula(nombreBloque, nombre, capacidad, tipo);
                aulas.add(aula);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aulas;
    }

}
