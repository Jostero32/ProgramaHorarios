/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Bloque;
import Clases.Docente;
import Clases.Materia;
import Modelo.Modelo_Reservas;
import Vista.Interfaz_Agregar_Horario;
import Vista.Pestaña_Reservas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class Controlador_Reservas implements ActionListener, MouseListener, PropertyChangeListener {

    private Pestaña_Reservas interfaz;
    private Modelo_Reservas modelo;
    private String[][] horariosCalendario = new String[14][6];
    private Interfaz_Agregar_Horario interfazAgregar;
    private java.util.Date fecha_fin;
    private java.util.Date fecha_inicio;

    public Controlador_Reservas(Connection con) {
        this.interfazAgregar = new Interfaz_Agregar_Horario();
        this.modelo = new Modelo_Reservas(con);
        this.interfaz = new Pestaña_Reservas();
        this.interfaz.boton_agregar.addActionListener(this);
        this.interfaz.boton_editar.addActionListener(this);
        this.interfaz.boton_eliminar.addActionListener(this);
        this.interfazAgregar.ComboBox_Docentes.addActionListener(this);
        this.interfazAgregar.ComboBox_Bloques.addActionListener(this);
        this.interfazAgregar.BTN_Agregar1.addMouseListener(this);
        this.interfaz.jDateChooser1.addPropertyChangeListener(this);
        this.interfaz.jComboBox1.addActionListener(this);
        this.interfaz.jComboBox2.addActionListener(this);
        this.interfaz.jComboBox3.addActionListener(this);
        this.agregarHorarios();
        this.actualizarCombosFiltro();
        this.interfaz.jDateChooser1.setDate(new java.util.Date());
    }

    private void borrarMatrizHorarios() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 14; j++) {
                this.horariosCalendario[j][i] = "";
                if (i > 0 && j > 0 && j < 13) {
                    this.interfaz.jTable1.setValueAt("", j, i);
                }
            }
        }
    }

    public Pestaña_Reservas getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Pestaña_Reservas interfaz) {
        this.interfaz = interfaz;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.interfaz.boton_agregar) {
            this.interfazAgregar.BTN_Agregar1.setText("AGREGAR");
            this.actualizarCamposInterfazAgregar();
            this.interfazAgregar.setVisible(true);
        }
        if (e.getSource() == this.interfaz.boton_editar) {
            this.interfazAgregar.BTN_Agregar1.setText("EDITAR");
            this.actualizarCamposInterfazAgregar();
            this.seleccionarCamposEditar();
            this.interfazAgregar.setVisible(true);
        }
        if (e.getSource() == this.interfazAgregar.ComboBox_Docentes && this.interfazAgregar.ComboBox_Docentes.getSelectedItem() != null) {
            this.actualizarMateriasDocente(Integer.parseInt(this.interfazAgregar.ComboBox_Docentes.getSelectedItem().toString().split("-")[0]));
        }
        if (e.getSource() == this.interfazAgregar.ComboBox_Bloques && this.interfazAgregar.ComboBox_Bloques.getSelectedItem() != null) {
            this.actualizarAulasBloque(Integer.parseInt(this.interfazAgregar.ComboBox_Bloques.getSelectedItem().toString().split("-")[0]));
        }
        if (e.getSource() == this.interfaz.jComboBox2 && this.interfaz.jComboBox2.getSelectedItem() != null) {
            this.interfaz.jComboBox3.removeAllItems();
            int id = Integer.parseInt(this.interfaz.jComboBox2.getSelectedItem().toString().split("-")[0]);
            if (this.interfaz.jComboBox1.getSelectedItem().toString().matches("Docentes/Materias")) {
                ArrayList<Materia> materias = this.modelo.obtenerMateriasDocente(id);
                if (materias != null) {
                    for (Materia mat : materias) {
                        this.interfaz.jComboBox3.addItem(mat.getId() + "-" + mat.getNombre() + "/" + mat.getCodigo());

                    }
                }
            } else {
                ArrayList<String> materias = this.modelo.obtenerAulasBloque(id);
                if (materias != null) {
                    for (String mat : materias) {
                        this.interfaz.jComboBox3.addItem(mat);
                    }
                }
            }
        }
        if (e.getSource() == this.interfaz.jComboBox1) {
            this.actualizarCombosFiltro();
        }
        if (e.getSource() == this.interfaz.jComboBox3 && this.fecha_inicio != null && this.fecha_fin != null && this.interfaz.jComboBox3.getSelectedItem() != null) {
            this.actualizarTablaHorario();
        }
        if (e.getSource() == this.interfaz.boton_eliminar && this.interfaz.jTable1.getSelectedRow() != -1 && this.interfaz.jTable1.getSelectedColumn() != -1) {
            try {
                this.modelo.eliminarReserva(Integer.parseInt(this.interfaz.jTable1.getValueAt(this.interfaz.jTable1.getSelectedRow(), this.interfaz.jTable1.getSelectedColumn()).toString().split("-")[5]));
                JOptionPane.showMessageDialog(null, "Registro Eliminado", "Exito", 1);
                this.actualizarTablaHorario();
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Reservas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void actualizarCamposInterfazAgregar() {
        this.ActualizarDocentes();
        this.ActualizarBloques();
    }

    private void agregarHorarios() {
        ArrayList<String> horarios = this.modelo.ObtenerHorarios();
        if (horarios != null) {
            for (String h : horarios) {
                this.interfazAgregar.ComboBox_Horario.addItem(h);
            }
        }
    }

    private void ActualizarDocentes() {
        ArrayList<Docente> docentes = this.modelo.obtenerDocentes();
        this.interfazAgregar.ComboBox_Docentes.removeAllItems();
        if (docentes != null) {
            for (Docente doc : docentes) {
                this.interfazAgregar.ComboBox_Docentes.addItem(doc.getId() + "-" + doc.getNombre() + "/" + doc.getEmail());

            }
        }
    }

    private void actualizarMateriasDocente(int id) {
        ArrayList<Materia> materias = this.modelo.obtenerMateriasDocente(id);
        this.interfazAgregar.ComboBox_Materias.removeAllItems();
        if (materias != null) {
            for (Materia mat : materias) {
                this.interfazAgregar.ComboBox_Materias.addItem(mat.getId() + "-" + mat.getNombre() + "/" + mat.getCodigo());

            }
        }
    }

    private void ActualizarBloques() {
        ArrayList<String> bloques = this.modelo.obtenerBloques();
        this.interfazAgregar.ComboBox_Bloques.removeAllItems();
        if (bloques != null) {
            for (String doc : bloques) {
                this.interfazAgregar.ComboBox_Bloques.addItem(doc);

            }
        }
    }

    private void actualizarAulasBloque(int id) {
        ArrayList<String> materias = this.modelo.obtenerAulasBloque(id);
        this.interfazAgregar.ComboBox_Aulas.removeAllItems();
        if (materias != null) {
            for (String mat : materias) {
                this.interfazAgregar.ComboBox_Aulas.addItem(mat);

            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.interfazAgregar.BTN_Agregar1) {
            try {
                Date fecha_inicio = new Date(this.interfazAgregar.fecha_inicio.getDate().getTime());
                Date fecha_fin = new Date(this.interfazAgregar.fecha_fin.getDate().getTime());
                String materiaStr = (String) this.interfazAgregar.ComboBox_Materias.getSelectedItem();
                String aulaStr = (String) this.interfazAgregar.ComboBox_Aulas.getSelectedItem();
                String dia = (String) this.interfazAgregar.ComboBox_Dia.getSelectedItem();
                String horarioStr = (String) this.interfazAgregar.ComboBox_Horario.getSelectedItem();
                int materia_id = Integer.parseInt(materiaStr.split("-")[0]);
                int aula_id = Integer.parseInt(aulaStr.split("-")[0]);
                int horario_id = Integer.parseInt(horarioStr.split("-")[0]);
                if ("-----".equals(dia)) {
                    dia = null;
                }
                if (this.interfazAgregar.BTN_Agregar1.getText().matches("AGREGAR")) {
                    this.modelo.insertarReserva(materia_id, aula_id, fecha_inicio, fecha_fin, dia, horario_id);
                    JOptionPane.showMessageDialog(null, "Registro Agregado", "Exito", 1);
                } else {
                    this.modelo.actualizarReserva(materia_id, materia_id, aula_id, fecha_inicio, fecha_fin, dia, horario_id);
                    JOptionPane.showMessageDialog(null, "Registro Modificado", "Exito", 1);
                }
                this.interfazAgregar.dispose();
                this.actualizarTablaHorario();
            } catch (SQLException ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(null, "Error en la base de datos", "Error", 0);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Seleccione fechas", "Error", 0);
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void seleccionarCamposEditar() {
        if (this.interfaz.jTable1.getSelectedRow() != -1 && this.interfaz.jTable1.getSelectedColumn() != -1) {
            try {
                String[] campos = this.interfaz.jTable1.getValueAt(this.interfaz.jTable1.getSelectedRow(), this.interfaz.jTable1.getSelectedColumn()).toString().split("-");
                this.interfazAgregar.ComboBox_Dia.setSelectedIndex(this.interfaz.jTable1.getSelectedColumn());
                this.interfazAgregar.ComboBox_Horario.setSelectedIndex(this.interfaz.jTable1.getSelectedColumn() - 1);
                for (int i = 0; i < this.interfazAgregar.ComboBox_Docentes.getItemCount(); i++) {
                    if (this.interfazAgregar.ComboBox_Docentes.getItemAt(i).contains(campos[2])) {
                        this.interfazAgregar.ComboBox_Docentes.setSelectedIndex(i);
                        break;
                    }
                }
                for (int i = 0; i < this.interfazAgregar.ComboBox_Materias.getItemCount(); i++) {
                    if (this.interfazAgregar.ComboBox_Materias.getItemAt(i).split("-")[0].contains(campos[0])) {
                        this.interfazAgregar.ComboBox_Materias.setSelectedIndex(i);
                        break;
                    }
                }
            } catch (Exception e) {

            }
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof java.util.Date) {
            // Obtener la nueva fecha seleccionada
            java.util.Date selectedDate = (java.util.Date) evt.getNewValue();

            // Calcular el primer y último día de la semana para la fecha seleccionada
            Calendar cal = Calendar.getInstance();
            cal.setTime(selectedDate);
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

            java.util.Date startOfWeek = cal.getTime();

            cal.add(Calendar.DAY_OF_WEEK, 6);
            java.util.Date endOfWeek = cal.getTime();

            // Mostrar las fechas en la consola (o realizar otra acción)
            this.fecha_fin = endOfWeek;
            this.fecha_inicio = startOfWeek;
            this.actualizarTablaHorario();
        }

    }

    private void actualizarCombosFiltro() {
        this.interfaz.jComboBox2.removeAllItems();
        if (this.interfaz.jComboBox1.getSelectedItem().toString().matches("Docentes/Materias")) {
            ArrayList<Docente> docentes = this.modelo.obtenerDocentes();
            if (docentes != null) {
                for (Docente doc : docentes) {
                    this.interfaz.jComboBox2.addItem(doc.getId() + "-" + doc.getNombre() + "/" + doc.getEmail());
                }
            }
        } else {
            ArrayList<String> bloques = this.modelo.obtenerBloques();
            if (bloques != null) {
                for (String doc : bloques) {
                    this.interfaz.jComboBox2.addItem(doc);

                }
            }
        }
    }

    private void actualizarTablaHorario() {
        this.borrarMatrizHorarios();
        if (this.interfaz.jComboBox1.getSelectedItem().toString().matches("Docentes/Materias")) {
            try {
                this.horariosCalendario = this.modelo.getReservasPorMateriaYRangoDeFechas(Integer.parseInt(this.interfaz.jComboBox3.getSelectedItem().toString().split("-")[0]), this.fecha_inicio, this.fecha_fin);
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 14; j++) {
                        if (j < 13) {
                            this.interfaz.jTable1.setValueAt(this.horariosCalendario[j][i], j-1, i);
                        }
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Reservas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                this.horariosCalendario = this.modelo.getReservasEnRangoDeFechasYHorario(Integer.parseInt(this.interfaz.jComboBox3.getSelectedItem().toString().split("-")[0]), this.fecha_inicio, this.fecha_fin);
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 14; j++) {
                        if (!this.horariosCalendario[j][i].isEmpty()) {
                            if (j < 13) {

                                this.interfaz.jTable1.setValueAt(this.horariosCalendario[j][i], j-1, i);
                            }
                        }
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Reservas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
