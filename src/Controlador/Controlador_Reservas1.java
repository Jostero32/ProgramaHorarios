/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Bloque;
import Clases.Docente;
import Clases.Materia;
import Modelo.Modelo_Reservas;
import Vista.Interfaz_Agregar_Horario1;
import Vista.Pestaña_Reservas1;
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
public class Controlador_Reservas1 implements ActionListener, MouseListener, PropertyChangeListener {

    private Pestaña_Reservas1 interfaz;
    private Modelo_Reservas modelo;
    private String[][] horariosCalendario = new String[14][6];
    private Interfaz_Agregar_Horario1 interfazAgregar;
    private java.util.Date fecha_fin;
    private java.util.Date fecha_inicio;
    private java.util.Date fecha_seleccionada;

    public Controlador_Reservas1(Connection con) {
        this.interfazAgregar = new Interfaz_Agregar_Horario1();
        this.modelo = new Modelo_Reservas(con);
        this.interfaz = new Pestaña_Reservas1();
        this.interfaz.boton_agregar.addActionListener(this);
        this.interfaz.boton_eliminar.addActionListener(this);
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

    public Pestaña_Reservas1 getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Pestaña_Reservas1 interfaz) {
        this.interfaz = interfaz;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.interfaz.boton_agregar && this.interfaz.jTable1.getSelectedRow() != -1 && this.interfaz.jTable1.getSelectedColumn() != -1) {
            this.actualizarCamposInterfazAgregar();
            this.interfazAgregar.setVisible(true);
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
        if (e.getSource() == this.interfaz.jComboBox3 && this.fecha_inicio != null && this.fecha_fin != null && this.interfaz.jComboBox3.getSelectedItem() != null) {
            this.actualizarTablaHorario();
        }
        if (e.getSource() == this.interfaz.boton_eliminar && this.interfaz.jTable1.getSelectedRow() != -1 && this.interfaz.jTable1.getSelectedColumn() != -1) {
            try {
                String[] elementos=this.interfaz.jTable1.getValueAt(this.interfaz.jTable1.getSelectedRow(), this.interfaz.jTable1.getSelectedColumn()).toString().split("-");
                this.modelo.eliminarReserva(Integer.parseInt(elementos[elementos.length-1]));
                JOptionPane.showMessageDialog(null, "Registro Eliminado", "Exito", 1);
                this.actualizarTablaHorario();
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Reservas1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void actualizarCamposInterfazAgregar() {
        this.interfazAgregar.ComboBox_Horario.setSelectedIndex(this.interfaz.jTable1.getSelectedRow());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.fecha_inicio);
        calendar.add(Calendar.DAY_OF_YEAR, this.interfaz.jTable1.getSelectedColumn()-1);
        this.fecha_seleccionada = calendar.getTime();
        this.interfazAgregar.fecha_fin.setDate(this.fecha_seleccionada);
        this.interfazAgregar.fecha_inicio.setDate(this.fecha_seleccionada);
        this.interfazAgregar.ComboBox_Aulas.addItem(this.interfaz.jComboBox3.getSelectedItem().toString());
        this.interfazAgregar.ComboBox_Bloques.addItem(this.interfaz.jComboBox2.getSelectedItem().toString());
    }

    private void agregarHorarios() {
        ArrayList<String> horarios = this.modelo.ObtenerHorarios();
        if (horarios != null) {
            for (String h : horarios) {
                this.interfazAgregar.ComboBox_Horario.addItem(h);
            }
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.interfazAgregar.BTN_Agregar1) {
            try {
                Date fecha_inicio = new Date(this.interfazAgregar.fecha_inicio.getDate().getTime());
                Date fecha_fin = new Date(this.interfazAgregar.fecha_fin.getDate().getTime());
                String encargado = this.interfazAgregar.Encargado.getText();
                String descripcion = this.interfazAgregar.Descripcion.getText();
                String aulaStr = (String) this.interfazAgregar.ComboBox_Aulas.getSelectedItem();
                String horarioStr = (String) this.interfazAgregar.ComboBox_Horario.getSelectedItem();
                int aula_id = Integer.parseInt(aulaStr.split("-")[0]);
                int horario_id = Integer.parseInt(horarioStr.split("-")[0]);

                    this.modelo.insertarReserva(encargado,descripcion, aula_id, fecha_inicio, fecha_fin, null, horario_id);
                    JOptionPane.showMessageDialog(null, "Registro Agregado", "Exito", 1);
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
                    this.interfaz.jComboBox2.addItem(doc.getId() + "-" + doc.getNombre() + "/" + doc.getCedula());
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
                for (int i = 1; i < 6; i++) {
                    for (int j = 1; j < 14; j++) {
                        if (j < 13) {
                            this.interfaz.jTable1.setValueAt(this.horariosCalendario[j][i], j-1, i);
                        }
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Reservas1.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                this.horariosCalendario = this.modelo.getReservasEnRangoDeFechasYHorario(Integer.parseInt(this.interfaz.jComboBox3.getSelectedItem().toString().split("-")[0]), this.fecha_inicio, this.fecha_fin);
                for (int i = 1; i < 6; i++) {
                    for (int j = 0; j < 14; j++) {
                        if (!this.horariosCalendario[j][i].isEmpty()) {
                            if (j < 13) {

                                this.interfaz.jTable1.setValueAt(this.horariosCalendario[j][i], j-1, i);
                            }
                        }
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Reservas1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
