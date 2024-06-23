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
        String paraleloSeleccionado = (String) this.pestaña.jcbxParalelo.getSelectedItem();

        if (docenteSeleccionado == null || docenteSeleccionado.isEmpty() ||
            materiaSeleccionada == null || materiaSeleccionada.isEmpty() ||
            paraleloSeleccionado == null || paraleloSeleccionado.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un docente, una materia y un paralelo válidos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int docenteId = Integer.parseInt(docenteSeleccionado.split(" - ")[0]);
        int materiaId = Integer.parseInt(materiaSeleccionada.split(" - ")[0]);
        char paralelo = paraleloSeleccionado.charAt(0);

        DocenteMateria docenteMateria = new DocenteMateria(docenteId, docenteSeleccionado.split(" - ")[1], materiaId, materiaSeleccionada.split(" - ")[1], paralelo);

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
        JOptionPane.showMessageDialog(null, "Error al agregar la relación docente-materia: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}



private void modificarDocenteMateria() {
    try {
        int filaSeleccionada = this.pestaña.jtblTabla_Docentes_Materias.getSelectedRow();
        if (filaSeleccionada != -1) {
            // Obtener los valores de la tabla como objetos
            Object docenteObj = this.pestaña.jtblTabla_Docentes_Materias.getValueAt(filaSeleccionada, 0);
            Object materiaObj = this.pestaña.jtblTabla_Docentes_Materias.getValueAt(filaSeleccionada, 1);
            Object paraleloObj = this.pestaña.jtblTabla_Docentes_Materias.getValueAt(filaSeleccionada, 2);

            // Convertir los valores obtenidos a los tipos correctos
            String docenteAntiguo = docenteObj.toString().trim();
            String materiaAntigua = materiaObj.toString().trim();
            char paraleloAntiguo = paraleloObj.toString().charAt(0);

            // Obtener los nuevos valores seleccionados
            String docenteNuevo = (String) this.pestaña.jcbxDocente.getSelectedItem();
            String materiaNueva = (String) this.pestaña.jcbxMateria.getSelectedItem();
            String paraleloSeleccionado = (String) this.pestaña.jcbxParalelo.getSelectedItem();

            if (docenteNuevo == null || docenteNuevo.isEmpty() ||
                materiaNueva == null || materiaNueva.isEmpty() ||
                paraleloSeleccionado == null || paraleloSeleccionado.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un docente, una materia y un paralelo válidos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener los IDs correspondientes a los nombres seleccionados
            int newDocenteId = this.modelo.obtenerIdDocentePorNombre(docenteNuevo.split(" - ")[1].trim());
            int newMateriaId = this.modelo.obtenerIdMateriaPorNombre(materiaNueva.split(" - ")[1].trim());
            char newParalelo = paraleloSeleccionado.charAt(0);

            if (newDocenteId == -1 || newMateriaId == -1) {
                JOptionPane.showMessageDialog(null, "Error al obtener los IDs del docente o materia. Verifique los nombres seleccionados.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener los IDs correspondientes a los nombres antiguos
            int oldDocenteId = this.modelo.obtenerIdDocentePorNombre(docenteAntiguo);
            int oldMateriaId = this.modelo.obtenerIdMateriaPorNombre(materiaAntigua);

            if (oldDocenteId == -1 || oldMateriaId == -1) {
                JOptionPane.showMessageDialog(null, "Error al obtener los IDs del docente o materia antiguos. Verifique los datos en la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear un objeto DocenteMateria con los nuevos valores
            DocenteMateria nuevaRelacion = new DocenteMateria(newDocenteId, docenteNuevo.split(" - ")[1], newMateriaId, materiaNueva.split(" - ")[1], newParalelo);

            // Llamar al método del modelo para modificar la relación
            if (this.modelo.modificarDocenteMateria(oldDocenteId, oldMateriaId, paraleloAntiguo, nuevaRelacion)) {
                JOptionPane.showMessageDialog(null, "Relación docente-materia modificada con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                actualizarTablaDocentesMaterias();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo modificar la relación docente-materia", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila para modificar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error al modificar la relación docente-materia: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        DefaultTableModel model = (DefaultTableModel) this.pestaña.jtblTabla_Docentes_Materias.getModel();
        model.setRowCount(0);
        for (DocenteMateria dm : this.modelo.obtenerTodasLasRelaciones()) {
            model.addRow(new Object[]{dm.getDocenteNombre(), dm.getMateriaNombre(), dm.getParalelo()});
        }
}


    private void actualizarDocentes() {
        ArrayList<String> docentes = modelo.obtenerDocentes();
        pestaña.actualizarDocentes(docentes);
    }

    private void actualizarMaterias() {
        ArrayList<String> materias = modelo.obtenerMaterias();
        pestaña.actualizarMaterias(materias);
    }

    private void actualizarComboBox() {
        this.pestaña.actualizarDocentes(modelo.obtenerDocentes());
        this.pestaña.actualizarMaterias(modelo.obtenerMaterias());
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
