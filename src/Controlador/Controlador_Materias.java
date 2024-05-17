/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Materia;
import Modelo.Modelo_Materia;
import Vista.Pestaña_Materias;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Bryan
 */
public class Controlador_Materias implements ActionListener {

    private Pestaña_Materias interfaz;
    private Modelo_Materia modelo;

    public Controlador_Materias(Connection conn) throws SQLException {
        this.interfaz = new Pestaña_Materias();
        this.modelo = new Modelo_Materia(conn);
        this.interfaz.jtbtnAgregar.addActionListener(this);
        this.interfaz.jtbtnEliminar.addActionListener(this);
        this.interfaz.jtbtnModificar.addActionListener(this);
        this.interfaz.setMaterias(this.modelo.obtenerTodasLasMaterias());
        this.interfaz.actualizarTablaMaterias();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.interfaz.jtbtnAgregar) {
            try {
                int id = Integer.parseInt(this.interfaz.jtxtIdMateria.getText());
                String nombre = this.interfaz.jtxtNombreMateria.getText();
                String codigo = this.interfaz.jtxtCodigoMateria.getText();

                Materia materia = new Materia(id, nombre, codigo);

                if (this.modelo.agregarMateria(materia)) {
                    this.interfaz.getMaterias().add(materia);
                    this.interfaz.actualizarTablaMaterias();
                    JOptionPane.showMessageDialog(null, "Materia agregada con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo agregar la materia", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID de materia inválido", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al agregar la materia", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == this.interfaz.jtbtnModificar) {
            try {
                int selectedRow = this.interfaz.jtblTabla_Materias.getSelectedRow();
                if (selectedRow != -1) {
                    int idAnterior = Integer.parseInt(this.interfaz.jtxtIdMateria.getText());
                    String nombreAnterior = this.interfaz.jtblTabla_Materias.getValueAt(selectedRow, 1).toString();
                    String codigoAnterior = this.interfaz.jtblTabla_Materias.getValueAt(selectedRow, 2).toString();

                    int idModificado = Integer.parseInt(this.interfaz.jtxtIdMateria.getText());
                    String nombreModificado = this.interfaz.jtxtNombreMateria.getText();
                    String codigoModificado = this.interfaz.jtxtCodigoMateria.getText();

                    Materia materiaAnterior = new Materia(idAnterior, nombreAnterior, codigoAnterior);
                    Materia materiaModificada = new Materia(idModificado, nombreModificado, codigoModificado);

                    if (this.modelo.actualizarMateria(materiaModificada, materiaAnterior)) {
                        for (int i = 0; i < this.interfaz.getMaterias().size(); i++) {
                            Materia m = this.interfaz.getMaterias().get(i);
                            if (m.getId() == idAnterior) {
                                this.interfaz.getMaterias().remove(i);
                                this.interfaz.getMaterias().add(materiaModificada);
                                break;
                            }
                        }
                        this.interfaz.actualizarTablaMaterias();
                        JOptionPane.showMessageDialog(null, "Materia modificada con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar la materia", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una materia para modificar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID de materia inválido", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar la materia", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == this.interfaz.jtbtnEliminar) {
            try {
                int selectedRow = this.interfaz.jtblTabla_Materias.getSelectedRow();
                if (selectedRow != -1) {
                    int id = Integer.parseInt(this.interfaz.jtxtIdMateria.getText());
                    String nombre = this.interfaz.jtxtNombreMateria.getText();
                    String codigo = this.interfaz.jtxtCodigoMateria.getText();

                    Materia materia = new Materia(id, nombre, codigo);

                    if (this.modelo.eliminarMateria(materia)) {
                        for (int i = 0; i < this.interfaz.getMaterias().size(); i++) {
                            Materia m = this.interfaz.getMaterias().get(i);
                            if (m.getId() == id) {
                                this.interfaz.getMaterias().remove(i);
                                break;
                            }
                        }
                        this.interfaz.actualizarTablaMaterias();
                        this.interfaz.jtxtIdMateria.setText("");
                        this.interfaz.jtxtNombreMateria.setText("");
                        this.interfaz.jtxtCodigoMateria.setText("");
                        JOptionPane.showMessageDialog(null, "Materia eliminada con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar la materia", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una materia para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID de materia inválido", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar la materia", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public Pestaña_Materias getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Pestaña_Materias interfaz) {
        this.interfaz = interfaz;
    }
}
