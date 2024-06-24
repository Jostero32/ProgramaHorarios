package Controlador;

import Clases.PeriodoAcademico;
import Modelo.Modelo_PeriodoAcademico;
import Vista.Pestaña_PeriodoAcademico;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Controlador_PeriodoAcademico implements ActionListener {

    private Pestaña_PeriodoAcademico interfaz;
    private Modelo_PeriodoAcademico modelo;

    public Controlador_PeriodoAcademico(Connection conn) throws SQLException {
        this.interfaz = new Pestaña_PeriodoAcademico();
        this.modelo = new Modelo_PeriodoAcademico(conn);
        this.interfaz.Btn_Agregar_U.addActionListener(this);
        this.interfaz.Btn_Eliminar_U.addActionListener(this);
        this.interfaz.Btn_Modificar_U.addActionListener(this);
        this.interfaz.setPeriodosAcademicos(this.modelo.obtenerTodosLosPeriodosAcademicos());
        this.interfaz.ActualizarTablaPeriodos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.interfaz.Btn_Agregar_U) {
            try {
                PeriodoAcademico periodo = new PeriodoAcademico(this.interfaz.jTextField1.getText(), 
                    new java.sql.Date(this.interfaz.jDateChooser1.getDate().getTime()), 
                    new java.sql.Date(this.interfaz.jDateChooser2.getDate().getTime()));
                if (this.modelo.agregarPeriodoAcademico(periodo)) {
                    this.interfaz.getPeriodosAcademicos().add(periodo);
                    this.interfaz.ActualizarTablaPeriodos();
                    JOptionPane.showMessageDialog(null, "Periodo académico creado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al crear periodo académico. El periodo puede ya existir o hubo un problema en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Datos mal ingresados", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == this.interfaz.Btn_Modificar_U) {
            try {
                int selectedRow = this.interfaz.Tabla_PeriodosAcademicos.getSelectedRow();
                if (selectedRow != -1) {
                    String periodoAnterior = this.interfaz.Tabla_PeriodosAcademicos.getValueAt(selectedRow, 0).toString();
                    PeriodoAcademico periodoModificado = new PeriodoAcademico(this.interfaz.jTextField1.getText(), 
                        new java.sql.Date(this.interfaz.jDateChooser1.getDate().getTime()), 
                        new java.sql.Date(this.interfaz.jDateChooser2.getDate().getTime()));
                    if (this.modelo.actualizarPeriodoAcademico(periodoModificado, periodoAnterior)) {
                        for (int i = 0; i < this.interfaz.getPeriodosAcademicos().size(); i++) {
                            PeriodoAcademico p = this.interfaz.getPeriodosAcademicos().get(i);
                            if (p.getNombre().matches(periodoAnterior)) {
                                this.interfaz.getPeriodosAcademicos().set(i, periodoModificado);
                                break;
                            }
                        }
                        this.interfaz.ActualizarTablaPeriodos();
                        JOptionPane.showMessageDialog(null, "Periodo académico actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar periodo académico. El periodo puede ya existir o hubo un problema en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un periodo académico para modificar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar periodo académico", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == this.interfaz.Btn_Eliminar_U) {
            try {
                int selectedRow = this.interfaz.Tabla_PeriodosAcademicos.getSelectedRow();
                if (selectedRow != -1) {
                    String nombrePeriodo = this.interfaz.Tabla_PeriodosAcademicos.getValueAt(selectedRow, 0).toString();
                    PeriodoAcademico periodo = new PeriodoAcademico(nombrePeriodo, null, null);

                    if (this.modelo.eliminarPeriodoAcademico(periodo)) {
                        for (int i = 0; i < this.interfaz.getPeriodosAcademicos().size(); i++) {
                            PeriodoAcademico p = this.interfaz.getPeriodosAcademicos().get(i);
                            if (p.getNombre().matches(periodo.getNombre())) {
                                this.interfaz.getPeriodosAcademicos().remove(i);
                                break;
                            }
                        }
                        this.interfaz.ActualizarTablaPeriodos();
                        this.interfaz.jTextField1.setText("");
                        JOptionPane.showMessageDialog(null, "Periodo académico eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar periodo académico. Puede que el periodo no exista o hubo un problema en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un periodo académico para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar periodo académico", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public Pestaña_PeriodoAcademico getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Pestaña_PeriodoAcademico interfaz) {
        this.interfaz = interfaz;
    }
}
