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
import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;

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
                Docente doc = new Docente(res.getString("nombre"), res.getInt("cedula"));
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
        if (!validarReserva(materia_id, aula_id, fecha_inicio, fecha_fin, dia, horario_id, -1)) {
            throw new SQLException("Conflicto de reserva detectado.");
        }

        String insertQuery = "INSERT INTO reservas (materia_id, aula_id, fecha_inicio, fecha_fin, dia, horario_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmtInsert = conn.prepareStatement(insertQuery)) {
            stmtInsert.setInt(1, materia_id);
            stmtInsert.setInt(2, aula_id);
            stmtInsert.setDate(3, new Date(fecha_inicio.getTime()));
            stmtInsert.setDate(4, new Date(fecha_fin.getTime()));
            stmtInsert.setString(5, dia);
            stmtInsert.setInt(6, horario_id);
            stmtInsert.executeUpdate();
        }
    }

    public void eliminarReserva(int reserva_id) throws SQLException {
        String deleteQuery = "DELETE FROM reservas WHERE id = ?";
        try (PreparedStatement stmtDelete = conn.prepareStatement(deleteQuery)) {
            stmtDelete.setInt(1, reserva_id);
            stmtDelete.executeUpdate();
        }
    }

    public void actualizarReserva(int reserva_id, int materia_id, int aula_id, Date fecha_inicio, Date fecha_fin, String dia, int horario_id) throws SQLException {
        if (!validarReserva(materia_id, aula_id, fecha_inicio, fecha_fin, dia, horario_id, reserva_id)) {
            throw new SQLException("Conflicto de reserva detectado.");
        }

        String updateQuery = "UPDATE reservas SET materia_id = ?, aula_id = ?, fecha_inicio = ?, fecha_fin = ?, dia = ?, horario_id = ? WHERE id = ?";
        try (PreparedStatement stmtUpdate = conn.prepareStatement(updateQuery)) {
            stmtUpdate.setInt(1, materia_id);
            stmtUpdate.setInt(2, aula_id);
            stmtUpdate.setDate(3, new Date(fecha_inicio.getTime()));
            stmtUpdate.setDate(4, new Date(fecha_fin.getTime()));
            stmtUpdate.setString(5, dia);
            stmtUpdate.setInt(6, horario_id);
            stmtUpdate.setInt(7, reserva_id);
            stmtUpdate.executeUpdate();
        }
    }

    
    
        public void insertarReserva(String encargado,String descripcion, int aula_id, Date fecha_inicio, Date fecha_fin, String dia, int horario_id) throws SQLException {
        if (!validarReserva(19000, aula_id, fecha_inicio, fecha_fin, dia, horario_id, -1)) {
            throw new SQLException("Conflicto de reserva detectado.");
        }

        String insertQuery = "INSERT INTO reservas (persona_encargada,descripcion, aula_id, fecha_inicio, fecha_fin, dia, horario_id) VALUES (?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement stmtInsert = conn.prepareStatement(insertQuery)) {
            stmtInsert.setString(1, encargado);
            stmtInsert.setString(2, descripcion);
            stmtInsert.setInt(3, aula_id);
            stmtInsert.setDate(4, new Date(fecha_inicio.getTime()));
            stmtInsert.setDate(5, new Date(fecha_fin.getTime()));
            stmtInsert.setString(6, dia);
            stmtInsert.setInt(7, horario_id);
            stmtInsert.executeUpdate();
        }
    }
    
        public void actualizarReserva(int reserva_id,String encargado,String descripcion, int aula_id, Date fecha_inicio, Date fecha_fin, String dia, int horario_id) throws SQLException {
        if (!validarReserva(1900000, aula_id, fecha_inicio, fecha_fin, dia, horario_id, reserva_id)) {
            throw new SQLException("Conflicto de reserva detectado.");
        }

        String updateQuery = "UPDATE reservas SET persona_encargada = ?,descripcion = ?, aula_id = ?, fecha_inicio = ?, fecha_fin = ?, dia = ?, horario_id = ? WHERE id = ?";
        try (PreparedStatement stmtUpdate = conn.prepareStatement(updateQuery)) {
            stmtUpdate.setString(1, encargado);
            stmtUpdate.setString(2, descripcion);
            stmtUpdate.setInt(3, aula_id);
            stmtUpdate.setDate(4, new Date(fecha_inicio.getTime()));
            stmtUpdate.setDate(5, new Date(fecha_fin.getTime()));
            stmtUpdate.setString(6, dia);
            stmtUpdate.setInt(7, horario_id);
            stmtUpdate.setInt(8, reserva_id);
            stmtUpdate.executeUpdate();
        }
    }

    
    
    private boolean validarReserva(int materia_id, int aula_id, Date fecha_inicio, Date fecha_fin, String dia, int horario_id, int reserva_id) throws SQLException {
        // Regla 1: Conflictos de Rango Completo incluyendo el horario_id y el día (solo para reservas sin día específico)
        if (dia == null) {
            String queryRangoCompleto = "SELECT COUNT(*) FROM reservas WHERE aula_id = ? AND ? <= fecha_fin AND ? >= fecha_inicio AND dia IS NULL AND horario_id = ? AND (? = -1 OR id != ?)";
            try (PreparedStatement stmtRangoCompleto = conn.prepareStatement(queryRangoCompleto)) {
                stmtRangoCompleto.setInt(1, aula_id);
                stmtRangoCompleto.setDate(2, new Date(fecha_inicio.getTime()));
                stmtRangoCompleto.setDate(3, new Date(fecha_fin.getTime()));
                stmtRangoCompleto.setInt(4, horario_id);
                stmtRangoCompleto.setInt(5, reserva_id);
                stmtRangoCompleto.setInt(6, reserva_id);
                try (ResultSet rsRangoCompleto = stmtRangoCompleto.executeQuery()) {
                    if (rsRangoCompleto.next() && rsRangoCompleto.getInt(1) > 0) {
                        return false;
                    }
                }
            }
        }

        // Regla 2: Conflictos de Días Específicos y Horarios
        if (dia != null) {
            String queryDiasEspecificos = "SELECT COUNT(*) FROM reservas WHERE aula_id = ? AND materia_id = ? AND ? <= fecha_fin AND ? >= fecha_inicio AND dia = ? AND horario_id = ? AND (? = -1 OR id != ?)";
            try (PreparedStatement stmtDiasEspecificos = conn.prepareStatement(queryDiasEspecificos)) {
                stmtDiasEspecificos.setInt(1, aula_id);
                stmtDiasEspecificos.setInt(2, materia_id);
                stmtDiasEspecificos.setDate(3, new Date(fecha_inicio.getTime()));
                stmtDiasEspecificos.setDate(4, new Date(fecha_fin.getTime()));
                stmtDiasEspecificos.setString(5, dia);
                stmtDiasEspecificos.setInt(6, horario_id);
                stmtDiasEspecificos.setInt(7, reserva_id);
                stmtDiasEspecificos.setInt(8, reserva_id);
                try (ResultSet rsDiasEspecificos = stmtDiasEspecificos.executeQuery()) {
                    if (rsDiasEspecificos.next() && rsDiasEspecificos.getInt(1) > 0) {
                        return false;
                    }
                }
            }
        }

        // Regla 3: Horarios Superpuestos dentro del Mismo Día
        if (dia != null) {
            String queryHorarios = "SELECT COUNT(*) FROM reservas WHERE aula_id = ? AND materia_id = ? AND ? <= fecha_fin AND ? >= fecha_inicio AND horario_id = ? AND dia = ? AND (? = -1 OR id != ?)";
            try (PreparedStatement stmtHorarios = conn.prepareStatement(queryHorarios)) {
                stmtHorarios.setInt(1, aula_id);
                stmtHorarios.setInt(2, materia_id);
                stmtHorarios.setDate(3, new Date(fecha_inicio.getTime()));
                stmtHorarios.setDate(4, new Date(fecha_fin.getTime()));
                stmtHorarios.setInt(5, horario_id);
                stmtHorarios.setString(6, dia);
                stmtHorarios.setInt(7, reserva_id);
                stmtHorarios.setInt(8, reserva_id);
                try (ResultSet rsHorarios = stmtHorarios.executeQuery()) {
                    if (rsHorarios.next() && rsHorarios.getInt(1) > 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public ArrayList<String> obtenerBloques() {
        String sql = "SELECT * FROM bloques";
        ArrayList<String> bloques = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet res = pstmt.executeQuery();
            bloques = new ArrayList<>();
            while (res.next()) {
                bloques.add(res.getInt("id") + "-" + res.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bloques;
    }

    public ArrayList<String> obtenerAulasBloque(int id) {
        String sql = "SELECT * FROM aulas WHERE id IN(SELECT aula_id FROM bloque_aula WHERE bloque_id=?)";
        ArrayList<String> materias = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet res = pstmt.executeQuery();
            materias = new ArrayList<>();
            while (res.next()) {
                materias.add(res.getInt("id") + "-" + res.getString("nombre") + "/" + res.getString("tipo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materias;
    }

    public String[][] getReservasPorMateriaYRangoDeFechas(int materiaId, java.util.Date fechaInicio, java.util.Date fechaFin) throws SQLException {
        String query = "SELECT reservas.*, materias.id AS id_materia, materias.nombre AS nombre_materia, "
                + "docentes.nombre AS nombre_docente, aulas.id AS id_aula, aulas.nombre AS nombre_aula, "
                + "horarios.hora_inicio, horarios.hora_fin "
                + "FROM reservas "
                + "JOIN materias ON reservas.materia_id = materias.id "
                + "JOIN docente_materia ON materias.id = docente_materia.materia_id "
                + "JOIN docentes ON docente_materia.docente_id = docentes.id "
                + "JOIN aulas ON reservas.aula_id = aulas.id "
                + "JOIN horarios ON reservas.horario_id = horarios.id "
                + "WHERE reservas.materia_id = ? "
                + "AND ? <= reservas.fecha_fin "
                + "AND ? >= reservas.fecha_inicio";

        String[][] horarioVector = new String[14][6];
        for (String[] row : horarioVector) {
            Arrays.fill(row, ""); // Inicializar con cadenas vacías
        }

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, materiaId);
            stmt.setDate(2, new Date(fechaInicio.getTime()));
            stmt.setDate(3, new Date(fechaFin.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int horarioId = rs.getInt("horario_id");
                    String dia = rs.getString("dia");
                    int idMateria = rs.getInt("id_materia");
                    int idAula = rs.getInt("id_aula");
                    String nombreMateria = rs.getString("nombre_materia");
                    String nombreDocente = rs.getString("nombre_docente");
                    String nombreAula = rs.getString("nombre_aula");

                    String reservaInfo = idMateria + "-" + nombreMateria
                            + "-" + nombreDocente
                            + "-" + idAula + "-" + nombreAula + "-" + rs.getInt("id");

                    if (dia != null) {
                        int diaIndex = getDiaIndex(dia);
                        horarioVector[horarioId][diaIndex] = reservaInfo;
                    } else {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(rs.getDate("fecha_inicio"));
                        int day1 = calendar.get(Calendar.DAY_OF_WEEK)-1;
                        calendar.setTime(rs.getDate("fecha_fin"));
                        int day2 = calendar.get(Calendar.DAY_OF_WEEK)-1;
                        // Si el día es null, colocar la reserva en todos los días del rango
                        for (int i = 0; i < 6; i++) {
                            if (horarioId >= 1 && horarioId <= 14) {
                                if (i >= day1 && i <= day2) {
                                    horarioVector[horarioId][i] = reservaInfo;
                                }
                            }
                        }
                    }
                }
            }
        }

        return horarioVector;
    }

    public String[][] getReservasEnRangoDeFechasYHorario(int aulaId, java.util.Date fechaInicio, java.util.Date fechaFin) throws SQLException {
        String query = "SELECT reservas.*, materias.id AS materia_id, materias.nombre AS nombre_materia, "
                + "docentes.nombre AS nombre_docente, aulas.id AS aula_id, aulas.nombre AS nombre_aula, "
                + "horarios.hora_inicio, horarios.hora_fin "
                + "FROM reservas "
                + "JOIN materias ON reservas.materia_id = materias.id "
                + "JOIN docente_materia ON materias.id = docente_materia.materia_id "
                + "JOIN docentes ON docente_materia.docente_id = docentes.id "
                + "JOIN aulas ON reservas.aula_id = aulas.id "
                + "JOIN horarios ON reservas.horario_id = horarios.id "
                + "WHERE reservas.aula_id = ? "
                + "AND ? <= reservas.fecha_fin "
                + "AND ? >= reservas.fecha_inicio";

        String[][] horarioVector = new String[14][6];
        for (String[] row : horarioVector) {
            Arrays.fill(row, ""); // Inicializar con cadenas vacías
        }

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, aulaId);
            stmt.setDate(2, new Date(fechaInicio.getTime()));
            stmt.setDate(3, new Date(fechaFin.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int horarioId = rs.getInt("horario_id");
                    String dia = rs.getString("dia");
                    int idMateria = rs.getInt("materia_id");
                    int idAula = rs.getInt("aula_id");
                    String nombreMateria = rs.getString("nombre_materia");
                    String nombreDocente = rs.getString("nombre_docente");
                    String nombreAula = rs.getString("nombre_aula");

                    String reservaInfo = idMateria + "-" + nombreMateria
                            + "-" + nombreDocente
                            + "-" + idAula + "-" + nombreAula + "-" + rs.getInt("id");

                    if (dia != null) {
                        int diaIndex = getDiaIndex(dia);
                        horarioVector[horarioId][diaIndex] = reservaInfo;
                    } else {
                        // Si el día es null, colocar la reserva en todos los días del rango
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(rs.getDate("fecha_inicio"));
                        int day1 = calendar.get(Calendar.DAY_OF_WEEK)-1;
                        calendar.setTime(rs.getDate("fecha_fin"));
                        int day2 = calendar.get(Calendar.DAY_OF_WEEK)-1;
                        for (int i = 0; i < 6; i++) {
                            if (horarioId >= 1 && horarioId <= 14) {

                                if (i >= day1 && i <= day2) {
                                    horarioVector[horarioId][i] = reservaInfo;
                                }
                            }
                        }
                    }
                }
            }
        }

        return horarioVector;
    }

    private int getDiaIndex(String dia) {
        switch (dia) {
            case "Lunes":
                return 1;
            case "Martes":
                return 2;
            case "Miercoles":
                return 3;
            case "Jueves":
                return 4;
            case "Viernes":
                return 5;
            default:
                return -1;
        }
    }
}
