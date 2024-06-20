package Controlador;

import Clases.DocenteMateria;
import Modelo.Modelo_DocenteMateria;
import Vista.Pestaña_Docentes_Materias;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador para la gestión de la relación entre docentes y materias.
 */
public class Controlador_DocentesMaterias implements ActionListener {

    private Modelo_DocenteMateria modelo;
    private Pestaña_Docentes_Materias pestaña;

    public Controlador_DocentesMaterias(Connection conn) {
        this.modelo = new Modelo_DocenteMateria(conn);
        this.pestaña = new Pestaña_Docentes_Materias();
        actualizarDocentes();
        actualizarMaterias();
        actualizarTablaDocentesMaterias();
        this.pestaña.jtbtnAgregar.addActionListener(this);
        this.pestaña.jtbtnEliminar.addActionListener(this);
        this.pestaña.jtbtnModificar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.pestaña.jtbtnAgregar) {
            agregarDocenteMateria();
        } else if (e.getSource() == this.pestaña.jtbtnModificar) {
            modificarDocenteMateria();
        } else if (e.getSource() == this.pestaña.jtbtnEliminar) {
            eliminarDocenteMateria();
        }
    }

    private void agregarDocenteMateria() {
        try {
            String docenteSeleccionado = (String) this.pestaña.jcbxDocente.getSelectedItem();
            String materiaSeleccionada = (String) this.pestaña.jcbxMateria.getSelectedItem();

            int docenteId = Integer.parseInt(docenteSeleccionado.split(" - ")[0]);
            int materiaId = Integer.parseInt(materiaSeleccionada.split(" - ")[0]);

            DocenteMateria docenteMateria = new DocenteMateria(docenteId, docenteSeleccionado.split(" - ")[1], materiaId, materiaSeleccionada.split(" - ")[1]);

            if (this.modelo.agregarDocenteMateria(docenteMateria)) {
                JOptionPane.showMessageDialog(null, "Relación docente-materia agregada con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                actualizarTablaDocentesMaterias();
                actualizarComboBox();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo agregar la relación docente-materia", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un docente y una materia válidos", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar la relación docente-materia", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarDocenteMateria() {
        try {
            int filaSeleccionada = this.pestaña.jtblTabla_Docentes_Materias.getSelectedRow();
            if (filaSeleccionada != -1) {
                String docenteAntiguoNombre = (String) this.pestaña.jtblTabla_Docentes_Materias.getValueAt(filaSeleccionada, 0);
                String materiaAntiguaNombre = (String) this.pestaña.jtblTabla_Docentes_Materias.getValueAt(filaSeleccionada, 1);

                int docenteAntiguo = obtenerIdDesdeNombre(docenteAntiguoNombre, modelo.obtenerDocentes());
                int materiaAntigua = obtenerIdDesdeNombre(materiaAntiguaNombre, modelo.obtenerMaterias());

                String docenteNuevo = (String) this.pestaña.jcbxDocente.getSelectedItem();
                String materiaNueva = (String) this.pestaña.jcbxMateria.getSelectedItem();

                int newDocenteId = Integer.parseInt(docenteNuevo.split(" - ")[0]);
                int newMateriaId = Integer.parseInt(materiaNueva.split(" - ")[0]);

                if (this.modelo.modificarDocenteMateria(docenteAntiguo, materiaAntigua, newDocenteId, newMateriaId)) {
                    JOptionPane.showMessageDialog(null, "Relación docente-materia modificada con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTablaDocentesMaterias();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo modificar la relación docente-materia", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila para modificar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un docente y una materia válidos", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar la relación docente-materia", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarDocenteMateria() {
        try {
            int filaSeleccionada = this.pestaña.jtblTabla_Docentes_Materias.getSelectedRow();
            if (filaSeleccionada != -1) {
                String docenteNombre = (String) this.pestaña.jtblTabla_Docentes_Materias.getValueAt(filaSeleccionada, 0);
                String materiaNombre = (String) this.pestaña.jtblTabla_Docentes_Materias.getValueAt(filaSeleccionada, 1);

                int docenteId = obtenerIdDesdeNombre(docenteNombre, modelo.obtenerDocentes());
                int materiaId = obtenerIdDesdeNombre(materiaNombre, modelo.obtenerMaterias());

                if (this.modelo.eliminarDocenteMateria(docenteId, materiaId)) {
                    JOptionPane.showMessageDialog(null, "Relación docente-materia eliminada con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTablaDocentesMaterias();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar la relación docente-materia", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la relación docente-materia", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la relación docente-materia", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTablaDocentesMaterias() {
    try {
        DefaultTableModel model = (DefaultTableModel) this.pestaña.jtblTabla_Docentes_Materias.getModel();
        model.setRowCount(0);

        for (DocenteMateria dm : this.modelo.obtenerTodasLasRelaciones()) {
            model.addRow(new Object[]{dm.getDocenteNombre(), dm.getMateriaNombre(), dm.getParalelo()});
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al cargar las relaciones docente-materia", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void actualizarDocentes() {
        try {
            ArrayList<String> docentes = modelo.obtenerDocentes();
            pestaña.actualizarDocentes(docentes);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los docentes", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarMaterias() {
        try {
            ArrayList<String> materias = modelo.obtenerMaterias();
            pestaña.actualizarMaterias(materias);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar las materias", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarComboBox() {
        try {
            this.pestaña.actualizarDocentes(modelo.obtenerDocentes());
            this.pestaña.actualizarMaterias(modelo.obtenerMaterias());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar ComboBox", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Pestaña_Docentes_Materias getPestaña() {
        return this.pestaña;
    }

    public void setPestaña(Pestaña_Docentes_Materias pestaña) {
        this.pestaña = pestaña;
    }

    private int obtenerIdDesdeNombre(String nombre, ArrayList<String> lista) {
        for (String item : lista) {
            if (item.contains(nombre)) {
                return Integer.parseInt(item.split(" - ")[0]);
            }
        }
        return -1; // O algún otro valor por defecto
    }
}
