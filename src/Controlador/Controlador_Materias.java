package Controlador;

import Clases.Materia;
import Modelo.Modelo_Materia;
import Vista.Pestaña_Materias;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

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
                String nombre = this.interfaz.jtxtNombreMateria.getText();
                String codigo = this.interfaz.jtxtCodigoMateria.getText();

                if (codigo.length() > 5) {
                    JOptionPane.showMessageDialog(null, "El código de la materia no puede tener más de 5 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Materia materia = new Materia(nombre, codigo);

                if (this.modelo.agregarMateria(materia)) {
                    this.interfaz.getMaterias().add(materia);
                    this.interfaz.actualizarTablaMaterias();
                    JOptionPane.showMessageDialog(null, "Materia agregada con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    // Limpiar los campos de texto
                    this.interfaz.limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo agregar la materia", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al agregar la materia", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == this.interfaz.jtbtnModificar) {
            try {
                int selectedRow = this.interfaz.jtblTabla_Materias.getSelectedRow();
                if (selectedRow != -1) {
                    String nombreAnterior = this.interfaz.jtblTabla_Materias.getValueAt(this.interfaz.jtblTabla_Materias.getSelectedRow(), 0).toString();
                    Materia materiaModificada = new Materia(this.interfaz.jtxtNombreMateria.getText(), this.interfaz.jtxtCodigoMateria.getText());
                    if (this.modelo.actualizarMateria(materiaModificada, nombreAnterior)) {
                        for (int i = 0; i < this.interfaz.getMaterias().size(); i++) {
                            Materia m = this.interfaz.getMaterias().get(i);
                            if (m.getNombre().matches(nombreAnterior)) {
                                this.interfaz.getMaterias().remove(m);
                                this.interfaz.getMaterias().add(materiaModificada);
                                break;
                            }
                        }
                        this.interfaz.actualizarTablaMaterias();
                        JOptionPane.showMessageDialog(null, "Materia modificada con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                        // Limpiar los campos de texto
                        this.interfaz.limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar la materia", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una materia para modificar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar la materia", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == this.interfaz.jtbtnEliminar) {
            try {
                int selectedRow = this.interfaz.jtblTabla_Materias.getSelectedRow();
                if (selectedRow != -1) {
                    String nombre = this.interfaz.jtxtNombreMateria.getText();
                    String codigo = this.interfaz.jtxtCodigoMateria.getText();

                    Materia materia = new Materia(nombre, codigo);

                    if (this.modelo.eliminarMateria(materia)) {
                        for (int i = 0; i < this.interfaz.getMaterias().size(); i++) {
                            Materia m = this.interfaz.getMaterias().get(i);
                            if (m.getNombre().equals(nombre) && m.getCodigo().equals(codigo)) {
                                this.interfaz.getMaterias().remove(m);
                                break;
                            }
                        }
                        this.interfaz.actualizarTablaMaterias();
                        this.interfaz.limpiarCampos();
                        JOptionPane.showMessageDialog(null, "Materia eliminada con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar la materia", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una materia para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
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
