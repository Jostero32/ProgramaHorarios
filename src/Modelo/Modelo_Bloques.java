/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Clases.Aula;
import Clases.Bloque;
import Clases.Laboratorio;
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            return false;
        }
    }

    public Bloque verBloque(String nombre) {
        String sql = "SELECT * FROM bloques WHERE nombre = ?";
        try (PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nombreBD = rs.getString("nombre");
                ArrayList<Aula> aulas = obtenerAulasPorBloque(nombreBD);
                ArrayList<Laboratorio> laboratorios = obtenerLaboratoriosPorBloque(nombreBD);
                return new Bloque(nombreBD, aulas, laboratorios);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public ArrayList<Bloque> verTodosLosBloques() {
        String sql = "SELECT * FROM bloques";
        try (Statement stmt = (Statement) conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                ArrayList<Aula> aulas = obtenerAulasPorBloque(nombre);
                ArrayList<Laboratorio> laboratorios = obtenerLaboratoriosPorBloque(nombre);
                Bloque bloque = new Bloque(nombre, aulas, laboratorios);
                bloques.add(bloque);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bloques;
    }

    private ArrayList<Aula> obtenerAulasPorBloque(String nombreBloque) {
        String sql = "SELECT a.nombre, a.piso, a.capacidad FROM aulas a JOIN bloques b ON a.bloque_id = b.id WHERE b.nombre = ?";
        ArrayList<Aula> aulas = new ArrayList<>();
        try (PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreBloque);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String aulaNombre = rs.getString("nombre");
                String piso = rs.getString("piso");
                String capacidad = rs.getString("capacidad");
                Aula aula = new Aula(nombreBloque, aulaNombre, piso, capacidad);
                aulas.add(aula);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aulas;
    }

    private ArrayList<Laboratorio> obtenerLaboratoriosPorBloque(String nombreBloque) {
        String sql = "SELECT l.nombre, l.piso, l.capacidad, l.num_computadoras FROM laboratorios l JOIN bloques b ON l.bloque_id = b.id WHERE b.nombre = ?";
        ArrayList<Laboratorio> laboratorios = new ArrayList<>();
        try (PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreBloque);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String labNombre = rs.getString("nombre");
                String piso = rs.getString("piso");
                String capacidad = rs.getString("capacidad");
                int numComputadoras = rs.getInt("num_computadoras");
                Laboratorio laboratorio = new Laboratorio(numComputadoras, nombreBloque, labNombre, piso, capacidad);
                laboratorios.add(laboratorio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return laboratorios;
    }
}
