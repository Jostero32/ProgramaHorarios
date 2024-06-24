package Modelo;

import Clases.PeriodoAcademico;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Modelo_PeriodoAcademico {

    private Connection conn;

    public Modelo_PeriodoAcademico(Connection connection) {
        this.conn = connection;
    }

    public PeriodoAcademico obtenerPeriodoAcademico(String nombre) throws SQLException {
        String sql = "SELECT * FROM periodo_academico WHERE nombre = ?";
        PreparedStatement pstmt = this.conn.prepareStatement(sql);
        pstmt.setString(1, nombre);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return new PeriodoAcademico(rs.getString("nombre"), rs.getDate("fecha_inicio"), rs.getDate("fecha_fin"));
        }
        return null;
    }

    public boolean agregarPeriodoAcademico(PeriodoAcademico periodo) {
        String sql = "INSERT INTO periodo_academico (nombre, fecha_inicio, fecha_fin) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, periodo.getNombre());
            pstmt.setDate(2, Date.valueOf(periodo.getFecha_inicio().toString()));
            pstmt.setDate(3, Date.valueOf(periodo.getFecha_fin().toString()));
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<PeriodoAcademico> obtenerTodosLosPeriodosAcademicos() throws SQLException {
        ArrayList<PeriodoAcademico> periodos = new ArrayList<>();
        String sql = "SELECT * FROM periodo_academico";
        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                periodos.add(new PeriodoAcademico(rs.getString("nombre"), rs.getDate("fecha_inicio"), rs.getDate("fecha_fin")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener periodos académicos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return periodos;
    }

    public boolean actualizarPeriodoAcademico(PeriodoAcademico periodo, String nombreAnterior) {
        String sql = "UPDATE periodo_academico SET nombre = ?, fecha_inicio = ?, fecha_fin = ? WHERE nombre = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, periodo.getNombre());
            pstmt.setDate(2, Date.valueOf(periodo.getFecha_inicio().toString()));
            pstmt.setDate(3, Date.valueOf(periodo.getFecha_fin().toString()));
            pstmt.setString(4, nombreAnterior);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarPeriodoAcademico(PeriodoAcademico periodo) {
        String sql = "DELETE FROM periodo_academico WHERE nombre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, periodo.getNombre());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
