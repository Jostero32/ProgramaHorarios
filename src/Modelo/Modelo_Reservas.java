/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Clases.Docente;
import Clases.Materia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class Modelo_Reservas {

    private Connection conn;

    public Modelo_Reservas(Connection connection) {
        this.conn = connection;
    }

    public ArrayList<String> ObtenerHorarios() {
        String sql = "SELECT * FROM horarios";
        ArrayList<String> horarios = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet res = pstmt.executeQuery();
            horarios = new ArrayList<>();
            while (res.next()) {
                horarios.add(res.getInt("id") + "-" + res.getString("hora_inicio") + ":" + res.getString("hora_fin"));
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return horarios;
    }

    public ArrayList<Docente> obtenerDocentes() {
        String sql = "SELECT * FROM docentes";
        ArrayList<Docente> docentes = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet res = pstmt.executeQuery();
            docentes = new ArrayList<>();
            while (res.next()) {
                Docente doc = new Docente(res.getString("nombre"), res.getString("email"));
                doc.setId(res.getInt("id"));
                docentes.add(doc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return docentes;
    }

    public ArrayList<Materia> obtenerMateriasDocente(int id) {
        String sql = "SELECT * FROM materias WHERE id IN(SELECT materia_id FROM docente_materia WHERE docente_id=?)";
        ArrayList<Materia> materias = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet res = pstmt.executeQuery();
            materias = new ArrayList<>();
            while (res.next()) {
                Materia mat = new Materia(res.getString("nombre"), res.getString("codigo"));
                mat.setId(res.getInt("id"));
                materias.add(mat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materias;
    }

    public void insertarReserva(int materia_id, int aula_id, Date fecha_inicio, Date fecha_fin, String dia, int horario_id) throws SQLException {
        if (!validarReserva(aula_id, fecha_inicio, fecha_fin, dia, horario_id)) {
            throw new SQLException("Conflicto de reserva detectado.");
        }

        String insertQuery = "INSERT INTO reservas (materia_id, aula_id, fecha_inicio, fecha_fin, dia, horario_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmtInsert = conn.prepareStatement(insertQuery)) {
            stmtInsert.setInt(1, materia_id);
            stmtInsert.setInt(2, aula_id);
            stmtInsert.setDate(3, new java.sql.Date(fecha_inicio.getTime()));
            stmtInsert.setDate(4, new java.sql.Date(fecha_fin.getTime()));
            stmtInsert.setString(5, dia);
            stmtInsert.setInt(6, horario_id);
            stmtInsert.executeUpdate();
        }
    }

    private boolean validarReserva(int aula_id, Date fecha_inicio, Date fecha_fin, String dia, int horario_id) throws SQLException {
        String queryRangoCompleto = "SELECT COUNT(*) FROM reservas WHERE aula_id = ? AND ? <= fecha_fin AND ? >= fecha_inicio AND dia IS NULL";
        try (PreparedStatement stmtRangoCompleto = conn.prepareStatement(queryRangoCompleto)) {
            stmtRangoCompleto.setInt(1, aula_id);
            stmtRangoCompleto.setDate(2, new java.sql.Date(fecha_inicio.getTime()));
            stmtRangoCompleto.setDate(3, new java.sql.Date(fecha_fin.getTime()));
            try (ResultSet rsRangoCompleto = stmtRangoCompleto.executeQuery()) {
                if (rsRangoCompleto.next() && rsRangoCompleto.getInt(1) > 0) {
                    return false; // Conflicto con una reserva de rango completo
                }
            }
        }

        if (dia != null) {
            String queryDiasEspecificos = "SELECT COUNT(*) FROM reservas WHERE aula_id = ? AND ? <= fecha_fin AND ? >= fecha_inicio AND dia = ?";
            try (PreparedStatement stmtDiasEspecificos = conn.prepareStatement(queryDiasEspecificos)) {
                stmtDiasEspecificos.setInt(1, aula_id);
                stmtDiasEspecificos.setDate(2, new java.sql.Date(fecha_inicio.getTime()));
                stmtDiasEspecificos.setDate(3, new java.sql.Date(fecha_fin.getTime()));
                stmtDiasEspecificos.setString(4, dia);
                try (ResultSet rsDiasEspecificos = stmtDiasEspecificos.executeQuery()) {
                    if (rsDiasEspecificos.next() && rsDiasEspecificos.getInt(1) > 0) {
                        return false; // Conflicto con una reserva de días específicos
                    }
                }
            }
        }

        String queryHorarios = "SELECT COUNT(*) FROM reservas WHERE aula_id = ? AND ? <= fecha_fin AND ? >= fecha_inicio AND horario_id = ?";
        try (PreparedStatement stmtHorarios = conn.prepareStatement(queryHorarios)) {
            stmtHorarios.setInt(1, aula_id);
            stmtHorarios.setDate(2, new java.sql.Date(fecha_inicio.getTime()));
            stmtHorarios.setDate(3, new java.sql.Date(fecha_fin.getTime()));
            stmtHorarios.setInt(4, horario_id);
            try (ResultSet rsHorarios = stmtHorarios.executeQuery()) {
                if (rsHorarios.next() && rsHorarios.getInt(1) > 0) {
                    return false; // Conflicto de horarios
                }
            }
        }

        return true; // No hay conflictos, se puede insertar la reserva
    }
}
