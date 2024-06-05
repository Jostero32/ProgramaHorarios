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
        inicializarBloques();
        inicializarTabla();
        actualizarBloques();

    }

    private void inicializarBloques() {
        this.pestaña.jComboBoxBloque.removeAllItems();
        for (Bloque bloque : this.bloques) {
            this.pestaña.jComboBoxBloque.addItem(bloque.getNombre());
        }
    }

    private void inicializarTabla() {
        DefaultTableModel model = new DefaultTableModel();

        for (Bloque bloque : bloques) {
            model = new DefaultTableModel(new Object[]{"Nombre"}, 0);
        }

        pestaña.jTableBloques.setModel(model);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.pestaña.Btn_Agregar_Bloque) {
            Bloque bloque = new Bloque(this.pestaña.txtNombrebloque.getText());
            this.modeloBloque.crearBloque(bloque);
            actualizarBloques();
        }

        if (e.getSource() == this.pestaña.Btn_Modificar_Bloque) {
            String nombreAnterior = this.pestaña.jComboBoxBloque.getSelectedItem().toString();

            //igual aqui hacer una interfaz aparte que se bloquee para que modifique
            this.modeloBloque.modificarBloque(nombreAnterior, this.pestaña.txtNombrebloque.getText());
            actualizarBloques();
        }
        if (e.getSource() == this.pestaña.Btn_Eliminar_Bloque) {
            String nombre = this.pestaña.jComboBoxBloque.getSelectedItem().toString();
            this.modeloBloque.eliminarBloque(nombre);
            actualizarBloques();
        }

    }

    private void actualizarBloques() {
        String bloqueSeleccionado = (String) this.pestaña.jComboBoxBloque.getSelectedItem();

        if (bloqueSeleccionado != null) {

            // Limpiar el JComboBox de aulas antes de agregar nuevas aulas
            this.pestaña.jComboBoxBloque.removeAllItems();

            // Limpiar la JTable antes de agregar nuevas filas
            DefaultTableModel model = (DefaultTableModel) this.pestaña.jTableBloques.getModel();
            model.setRowCount(0);
            this.bloques=this.modeloBloque.verTodosLosBloques();
            // Agregar las aulas al JComboBox de aulas y a la JTable
            for (Bloque bloque : bloques) {
                this.pestaña.jComboBoxBloque.addItem(bloque.getNombre());
                model.addRow(new Object[]{bloque.getNombre()});
            }
        }
    }

    public Pestaña_Bloques getPestaña() {
        return this.pestaña;
    }

    public void setPestaña(Pestaña_Bloques pestaña) {
        this.pestaña = pestaña;
    }
}
