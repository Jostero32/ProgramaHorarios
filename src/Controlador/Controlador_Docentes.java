package Controlador;

import Clases.Docente;
import Modelo.Modelo_Docente;
import Vista.Pestaña_Docentes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Controlador_Docentes implements ActionListener {

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
            String cedulaStr = this.interfaz.jtxtCedulaDocente.getText();

            // Verificar que la cédula tenga 10 números
            if (cedulaStr.length() != 10) {
                JOptionPane.showMessageDialog(null, "La cédula debe tener 10 números", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int cedula = Integer.parseInt(cedulaStr);
                Docente docente = new Docente(nombre, cedula);

                if (this.modelo.agregarDocente(docente)) {
                    this.interfaz.getDocentes().add(docente);
                    this.interfaz.actualizarTablaDocentes();
                    JOptionPane.showMessageDialog(null, "Docente agregado con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    // Limpiar los campos de texto
                    this.interfaz.jtxtNombreDocente.setText("");
                    this.interfaz.jtxtCedulaDocente.setText("");
                    // Habilitar la edición del campo de cédula
                    this.interfaz.jtxtCedulaDocente.setEditable(true);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo agregar el docente", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "La cédula debe contener solo números", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar el docente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    if (e.getSource() == this.interfaz.jtbtnModificar) {
        try {
            int selectedRow = this.interfaz.jtblTabla_Docentes.getSelectedRow();
            if (selectedRow != -1) {
                String nombreAnterior = this.interfaz.jtblTabla_Docentes.getValueAt(this.interfaz.jtblTabla_Docentes.getSelectedRow(), 0).toString();
                String cedulaStr = this.interfaz.jtxtCedulaDocente.getText();

                // Verificar que la cédula tenga 10 números
                if (cedulaStr.length() != 10) {
                    JOptionPane.showMessageDialog(null, "La cédula debe tener 10 números", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int cedula = Integer.parseInt(cedulaStr);
                    Docente docenteModificado = new Docente(this.interfaz.jtxtNombreDocente.getText(), cedula);

                    if (this.modelo.actualizarDocente(docenteModificado, nombreAnterior)) {
                        for (int i = 0; i < this.interfaz.getDocentes().size(); i++) {
                            Docente d = this.interfaz.getDocentes().get(i);
                            if (d.getNombre().matches(nombreAnterior)) {
                                this.interfaz.getDocentes().remove(d);
                                this.interfaz.getDocentes().add(docenteModificado);
                                break;
                            }
                        }
                        this.interfaz.actualizarTablaDocentes();
                        JOptionPane.showMessageDialog(null, "Docente modificado con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                        // Limpiar los campos de texto
                        this.interfaz.jtxtNombreDocente.setText("");
                        this.interfaz.jtxtCedulaDocente.setText("");
                        // Habilitar la edición del campo de cédula
                        this.interfaz.jtxtCedulaDocente.setEditable(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar el docente", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "La cédula debe contener solo números", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un docente para modificar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar el docente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    if (e.getSource() == this.interfaz.jtbtnEliminar) {
        try {
            int selectedRow = this.interfaz.jtblTabla_Docentes.getSelectedRow();
            if (selectedRow != -1) {
                String nombre = this.interfaz.jtxtNombreDocente.getText();
                int cedula = Integer.parseInt(this.interfaz.jtxtCedulaDocente.getText());

                Docente docente = new Docente(nombre, cedula);

                if (this.modelo.eliminarDocente(docente)) {
                    for (int i = 0; i < this.interfaz.getDocentes().size(); i++) {
                        Docente d = this.interfaz.getDocentes().get(i);
                        if (d.getNombre().equals(nombre) && d.getCedula() == cedula) {
                            this.interfaz.getDocentes().remove(d);
                            break;
                        }
                    }
                    this.interfaz.actualizarTablaDocentes();
                    this.interfaz.jtxtNombreDocente.setText("");
                    this.interfaz.jtxtCedulaDocente.setText("");
                    // Habilitar la edición del campo de cédula
                    this.interfaz.jtxtCedulaDocente.setEditable(true);
                    JOptionPane.showMessageDialog(null, "Docente eliminado con éxito", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el docente", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un docente para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el docente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
