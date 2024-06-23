/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Aula;
import Clases.Bloque;
import Modelo.Modelo_Aulas;
import Modelo.Modelo_Bloques;
import Vista.Pestaña_Aulas;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS GAMER
 */
public class Controlador_Aulas implements ActionListener {

    private ArrayList<Bloque> bloques;
    private Modelo_Bloques modeloBloque;
    private Modelo_Aulas modeloAula;
    private Pestaña_Aulas pestaña;

    public Controlador_Aulas(Connection conn) {
        this.modeloBloque = new Modelo_Bloques(conn);
        this.modeloAula = new Modelo_Aulas(conn);
        this.pestaña = new Pestaña_Aulas();
        this.bloques = this.modeloBloque.verTodosLosBloques();
        this.pestaña.Btn_Agregar_Aula.addActionListener(this);
        this.pestaña.Btn_Modificar_Aula.addActionListener(this);
        this.pestaña.Btn_Eliminar_Aula.addActionListener(this);
        this.pestaña.jComboBoxBloque.addActionListener(this);
        inicializarBloques();
        inicializarTabla();
        actualizarAulas();
        actualizarBloques();
        // Añadir ListSelectionListener a la tabla
        this.pestaña.jTableAulas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    actualizarCamposTexto();
                }
            }
        });
    }

    // Método para inicializar el JComboBox de bloques
    private void inicializarBloques() {
        this.pestaña.jComboBoxBloque.removeAllItems();
        for (Bloque bloque : this.bloques) {
            this.pestaña.jComboBoxBloque.addItem(bloque.getNombre());
        }
    }

    // Método para actualizar el JComboBox de bloques
    private void actualizarBloques() {
        this.pestaña.jComboBoxBloque.removeAllItems();
        this.bloques = this.modeloBloque.verTodosLosBloques(); // Obtener los bloques de la base de datos
        for (Bloque bloque : this.bloques) {
            this.pestaña.jComboBoxBloque.addItem(bloque.getNombre());
        }
    }

    private void inicializarTabla() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre", "Tipo", "Capacidad"}, 0);
        pestaña.jTableAulas.setModel(model);
    }

    private void limpiarCamposTexto() {
        this.pestaña.txtNmbreAula.setText("");
        this.pestaña.txtTipo.setText("");
        this.pestaña.txtCapacidad.setText("");
    }

    private void actualizarCamposTexto() {
        int selectedRow = pestaña.jTableAulas.getSelectedRow();
        if (selectedRow != -1) {
            String nombre = (String) pestaña.jTableAulas.getValueAt(selectedRow, 0);
            String tipo = (String) pestaña.jTableAulas.getValueAt(selectedRow, 1);
            int capacidad = (Integer) pestaña.jTableAulas.getValueAt(selectedRow, 2);

            pestaña.txtNmbreAula.setText(nombre);
            pestaña.txtTipo.setText(tipo);
            pestaña.txtCapacidad.setText(String.valueOf(capacidad));
        }
    }

     @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.pestaña.jComboBoxBloque) {
        limpiarCamposTexto();
        actualizarAulas();
    }

    if (e.getSource() == this.pestaña.Btn_Agregar_Aula) {
        String nombreBloque = this.pestaña.jComboBoxBloque.getSelectedItem().toString();
        Aula aula = new Aula(nombreBloque, this.pestaña.txtNmbreAula.getText(), Integer.parseInt(this.pestaña.txtCapacidad.getText()), this.pestaña.txtTipo.getText());
        this.modeloAula.crearAula(aula, nombreBloque);
        limpiarCamposTexto();
        actualizarAulas();
    }

    if (e.getSource() == this.pestaña.Btn_Modificar_Aula) {
        int selectedRow = this.pestaña.jTableAulas.getSelectedRow();
        if (selectedRow != -1) {
            String nombreAula = this.pestaña.jTableAulas.getValueAt(selectedRow, 0).toString();
            String nuevoNombre = this.pestaña.txtNmbreAula.getText();
            String tipo = this.pestaña.txtTipo.getText();
            int capacidad = Integer.parseInt(this.pestaña.txtCapacidad.getText());
            String nombreBloque = this.pestaña.jComboBoxBloque.getSelectedItem().toString();

            this.modeloAula.modificarAula(nombreAula, nuevoNombre, tipo, capacidad, nombreBloque);
            limpiarCamposTexto();
            actualizarAulas();
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un Aula para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    if (e.getSource() == this.pestaña.Btn_Eliminar_Aula) {
        int selectedRow = this.pestaña.jTableAulas.getSelectedRow();
        if (selectedRow != -1) {
            String nombreAula = this.pestaña.jTableAulas.getValueAt(selectedRow, 0).toString();
            String nombreBloque = this.pestaña.jComboBoxBloque.getSelectedItem().toString();
            this.modeloAula.eliminarAula(nombreAula, nombreBloque);
            limpiarCamposTexto();
            actualizarAulas();
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un Aula para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}

    private void actualizarAulas() {
        String bloqueSeleccionado = (String) pestaña.jComboBoxBloque.getSelectedItem();
        this.pestaña.jLabelNombreAula.setText("Aulas del Bloque: " + bloqueSeleccionado);
        if (bloqueSeleccionado != null) {
            ArrayList<Aula> aulas = this.modeloBloque.obtenerAulasPorBloque(bloqueSeleccionado);

            // Limpiar la JTable antes de agregar nuevas filas
            DefaultTableModel model = (DefaultTableModel) this.pestaña.jTableAulas.getModel();
            model.setRowCount(0);

            // Agregar las aulas al JComboBox de aulas y a la JTable
            for (Aula aula : aulas) {

                model.addRow(new Object[]{aula.getNombre(), aula.getTipo(), aula.getCapacidad()});
            }
        }
    }

    public Pestaña_Aulas getPestaña() {
        return this.pestaña;
    }

    public void setPestaña(Pestaña_Aulas pestaña) {
        this.pestaña = pestaña;
    }
}
