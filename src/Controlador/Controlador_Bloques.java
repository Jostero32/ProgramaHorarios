/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Bloque;
import Modelo.Modelo_Aulas;
import Modelo.Modelo_Bloques;
import Vista.Pestaña_Bloques;
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
public class Controlador_Bloques implements ActionListener {

    private ArrayList<Bloque> bloques;
    private Modelo_Bloques modeloBloque;
    private Modelo_Aulas modeloAula;
    private Pestaña_Bloques pestaña;

    public Controlador_Bloques(Connection conn) {
        this.modeloBloque = new Modelo_Bloques(conn);
        this.modeloAula = new Modelo_Aulas(conn);
        this.pestaña = new Pestaña_Bloques();
        this.bloques = this.modeloBloque.verTodosLosBloques();
        this.pestaña.Btn_Agregar_Bloque.addActionListener(this);
        this.pestaña.Btn_Eliminar_Bloque.addActionListener(this);
        this.pestaña.Btn_Modificar_Bloque.addActionListener(this);
        actualizarBloques();

        // Añadir ListSelectionListener a la tabla
        this.pestaña.jTableBloques.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    actualizarCamposTexto();
                }
            }
        });
    }

    private void actualizarCamposTexto() {
        int selectedRow = pestaña.jTableBloques.getSelectedRow();
        if (selectedRow != -1) {
            String nombre = (String) pestaña.jTableBloques.getValueAt(selectedRow, 0);
            pestaña.txtNombrebloque.setText(nombre);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.pestaña.Btn_Agregar_Bloque) {
            String nombreNuevo = this.pestaña.txtNombrebloque.getText();
            if (nombreBloqueExiste(nombreNuevo)) {
                JOptionPane.showMessageDialog(null, "El nombre del bloque ya está ocupado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Bloque bloque = new Bloque(nombreNuevo);
            this.modeloBloque.crearBloque(bloque);
            actualizarBloques();
        }

        if (e.getSource() == this.pestaña.Btn_Modificar_Bloque) {
            int selectedRow = this.pestaña.jTableBloques.getSelectedRow();
            if (selectedRow != -1) {
                String nombreAnterior = this.pestaña.jTableBloques.getValueAt(selectedRow, 0).toString();
                String nombreNuevo = this.pestaña.txtNombrebloque.getText();
                if (!nombreAnterior.equals(nombreNuevo) && nombreBloqueExiste(nombreNuevo)) {
                    JOptionPane.showMessageDialog(null, "El nombre del bloque ya está ocupado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                this.modeloBloque.modificarBloque(nombreAnterior, nombreNuevo);
                actualizarBloques();
            }
        }

        if (e.getSource() == this.pestaña.Btn_Eliminar_Bloque) {
            int selectedRow = this.pestaña.jTableBloques.getSelectedRow();
            if (selectedRow != -1) {
                String nombre = this.pestaña.jTableBloques.getValueAt(selectedRow, 0).toString();
                this.modeloBloque.eliminarBloque(nombre);
                actualizarBloques();
            }
        }
    }

    private boolean nombreBloqueExiste(String nombre) {
        for (Bloque bloque : bloques) {
            if (bloque.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    private void actualizarBloques() {
        this.bloques = this.modeloBloque.verTodosLosBloques();

        // Crear un nuevo modelo para la tabla
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer todas las celdas no editables
            }
        };

        // Añadir los datos al nuevo modelo
        for (Bloque bloque : bloques) {
            model.addRow(new Object[]{bloque.getNombre()});
        }

        // Establecer el nuevo modelo en la tabla
        this.pestaña.jTableBloques.setModel(model);
    }

    public Pestaña_Bloques getPestaña() {
        return this.pestaña;
    }

    public void setPestaña(Pestaña_Bloques pestaña) {
        this.pestaña = pestaña;
    }
}