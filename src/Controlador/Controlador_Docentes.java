/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Docente;
import Modelo.Modelo_Docente;
import Vista.Pestaña_Docentes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Bryan
 */
class Controlador_Docentes implements ActionListener {

    private Pestaña_Docentes interfaz;
    private Modelo_Docente modelo;

    public Controlador_Docentes(Connection conn) throws SQLException {
        this.interfaz = new Pestaña_Docentes();
        this.modelo = new Modelo_Docente(conn);
        this.interfaz.jtbtnAgregar.addActionListener(this);
        this.interfaz.jtbtnEliminar.addActionListener(this);
        this.interfaz.jtbtnModificar.addActionListener(this);
        this.interfaz.setDocentes(this.modelo.obtenerTodosLosDocentes());
        this.interfaz.actualizarTablaDocentes();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.interfaz.jtbtnAgregar) {
            try {
                String nombre = this.interfaz.jtxtNombreDocente.getText();
                String correo = this.interfaz.jtxtCorreoDocente.getText();

                Docente docente = new Docente(nombre, correo);

                if (this.modelo.agregarDocente(docente)) {
                    this.interfaz.getDocentes().add(docente);
                    this.interfaz.actualizarTablaDocentes();
                    JOptionPane.showMessageDialog(null, "Docente agregado con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo agregar el docente", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al agregar el docente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == this.interfaz.jtbtnModificar) {
            try {
                int selectedRow = this.interfaz.jtblTabla_Docentes.getSelectedRow();
                if (selectedRow != -1) {
                    String nombreAnterior = this.interfaz.jtblTabla_Docentes.getValueAt(selectedRow, 0).toString();
                    String correoAnterior = this.interfaz.jtblTabla_Docentes.getValueAt(selectedRow, 1).toString();

                    String nombreModificado = this.interfaz.jtxtNombreDocente.getText();
                    String correoModificado = this.interfaz.jtxtCorreoDocente.getText();

                    Docente docenteAnterior = new Docente(nombreAnterior, correoAnterior);
                    Docente docenteModificado = new Docente(nombreModificado, correoModificado);

                    if (this.modelo.actualizarDocente(docenteModificado, docenteAnterior)) {
                        for (int i = 0; i < this.interfaz.getDocentes().size(); i++) {
                            Docente d = this.interfaz.getDocentes().get(i);
                            if (d.getNombre().equals(nombreAnterior) && d.getEmail().equals(correoAnterior)) {
                                this.interfaz.getDocentes().remove(i);
                                this.interfaz.getDocentes().add(docenteModificado);
                                break;
                            }
                        }
                        this.interfaz.actualizarTablaDocentes();
                        JOptionPane.showMessageDialog(null, "Docente modificado con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar el docente", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un docente para modificar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar el docente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == this.interfaz.jtbtnEliminar) {
            try {
                int selectedRow = this.interfaz.jtblTabla_Docentes.getSelectedRow();
                if (selectedRow != -1) {
                    String nombre = this.interfaz.jtblTabla_Docentes.getValueAt(selectedRow, 0).toString();
                    String correo = this.interfaz.jtblTabla_Docentes.getValueAt(selectedRow, 1).toString();

                    Docente docente = new Docente(nombre, correo);

                    if (this.modelo.eliminarDocente(docente)) {
                        for (int i = 0; i < this.interfaz.getDocentes().size(); i++) {
                            Docente d = this.interfaz.getDocentes().get(i);
                            if (d.getNombre().equals(nombre) && d.getEmail().equals(correo)) {
                                this.interfaz.getDocentes().remove(i);
                                break;
                            }
                        }
                        this.interfaz.actualizarTablaDocentes();
                        this.interfaz.jtxtNombreDocente.setText("");
                        this.interfaz.jtxtCorreoDocente.setText("");
                        JOptionPane.showMessageDialog(null, "Docente eliminado con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar el docente", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un docente para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar el docente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public Pestaña_Docentes getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Pestaña_Docentes interfaz) {
        this.interfaz = interfaz;
    }
}