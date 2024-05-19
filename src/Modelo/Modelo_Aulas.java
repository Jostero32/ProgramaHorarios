/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Clases.Aula;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author ASUS GAMER
 */
public class Modelo_Aulas {
    
    private ArrayList<Aula> aulas;
    private Connection conn;

    public Modelo_Aulas(Connection conn) {
        this.conn = conn;
        this.aulas = new ArrayList<>();
    }

    public boolean crearAula(Aula aula) {
        String sql = "INSERT INTO aulas (nombre, piso, capacidad, tipo, nombre_bloque) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt =  conn.prepareStatement(sql)) {
            pstmt.setString(1, aula.getNombre());
            pstmt.setString(2, aula.getPiso());
            pstmt.setString(3, aula.getCapacidad());
            pstmt.setString(4, aula.getTipo());
            pstmt.setString(5, aula.getNombreBloque());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificarAula(Aula aula) {
        String sql = "UPDATE aulas SET piso = ?, capacidad = ?, tipo = ?, nombre_bloque = ? WHERE nombre = ?";
        try (PreparedStatement pstmt =  conn.prepareStatement(sql)) {
            pstmt.setString(1, aula.getPiso());
            pstmt.setString(2, aula.getCapacidad());
            pstmt.setString(3, aula.getTipo());
            pstmt.setString(4, aula.getNombreBloque());
            pstmt.setString(5, aula.getNombre());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarAula(String nombre) {
        String sql = "DELETE FROM aulas WHERE nombre = ?";
        try (PreparedStatement pstmt =  conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Aula verAula(String nombre) {
        String sql = "SELECT * FROM aulas WHERE nombre = ?";
        try (PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String piso = rs.getString("piso");
                String capacidad = rs.getString("capacidad");
                String tipo = rs.getString("tipo");
                String nombreBloque = rs.getString("nombre_bloque");
                return new Aula(nombreBloque, nombre, piso, capacidad, tipo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public ArrayList<Aula> verAulasPorBloque(String nombreBloque) {
        String sql = "SELECT * FROM aulas WHERE nombre_bloque = ?";
        ArrayList<Aula> aulas = new ArrayList<>();
        try (PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreBloque);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String piso = rs.getString("piso");
                String capacidad = rs.getString("capacidad");
                String tipo = rs.getString("tipo");
                Aula aula = new Aula(nombreBloque, nombre, piso, capacidad, tipo);
                aulas.add(aula);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aulas;
    }
}
