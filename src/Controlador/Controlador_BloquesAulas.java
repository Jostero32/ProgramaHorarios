/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Aula;
import Clases.Bloque;
import Modelo.Modelo_Aulas;
import Modelo.Modelo_Bloques;
import Vista.Pestaña_BloquesAulas;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS GAMER
 */
public class Controlador_BloquesAulas implements ActionListener {

    private ArrayList<Bloque> bloques;
    private Modelo_Bloques modeloBloque;
    private Modelo_Aulas modeloAula;
    private Pestaña_BloquesAulas pestaña;

    public Controlador_BloquesAulas(Connection conn) {
        this.modeloBloque = new Modelo_Bloques(conn);
        this.modeloAula = new Modelo_Aulas(conn);
        this.pestaña = new Pestaña_BloquesAulas();
        this.bloques = this.modeloBloque.verTodosLosBloques();
        this.pestaña.Btn_Agregar_Aula.addActionListener(this);
        this.pestaña.Btn_Agregar_Bloque.addActionListener(this);
        this.pestaña.Btn_Eliminar_Aula.addActionListener(this);
        this.pestaña.Btn_Eliminar_Bloque.addActionListener(this);
        this.pestaña.Btn_Modificar_Aula.addActionListener(this);
        this.pestaña.Btn_Modificar_Bloque.addActionListener(this);
        this.pestaña.jComboBoxBloque.addActionListener(this);
        actualizarAulas();
        inicializarBloques();
    }

    private void inicializarBloques() {
        this.pestaña.jComboBoxBloque.removeAllItems();
        for (Bloque bloque : this.bloques) {
            this.pestaña.jComboBoxBloque.addItem(bloque.getNombre());
        }
        actualizarAulas();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.pestaña.jComboBoxBloque) {
            
            actualizarAulas();
        }
        if (e.getSource() == this.pestaña.Btn_Agregar_Bloque) {
            ArrayList<Aula> aulas = new ArrayList<>();
            String nombreBloque = JOptionPane.showInputDialog(null, "Agregue el nombre del nuevo Bloque");
            while (JOptionPane.showConfirmDialog(null, "Quiere agregar aulas a " + nombreBloque + "?", "Agregar Aulas", JOptionPane.YES_NO_OPTION) == 0) {
                Aula aula = new Aula(nombreBloque, JOptionPane.showInputDialog(null, "Ingrese el nombre del Aula"), JOptionPane.showInputDialog(null, "Ingrese el piso del Aula"), Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la capacidad del Aula")), JOptionPane.showInputDialog(null, "Ingrese el tipo del Aula"));
                aulas.add(aula);
            }
            Bloque bloque = new Bloque(nombreBloque, aulas);
            this.modeloBloque.crearBloque(bloque);
        }

        if (e.getSource() == this.pestaña.Btn_Modificar_Bloque) {
            String nombreAnterior = this.pestaña.jComboBoxBloque.getSelectedItem().toString();

            this.modeloBloque.modificarBloque(nombreAnterior, JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre del Bloque"));
        inicializarBloques();
        }
        if (e.getSource() == this.pestaña.Btn_Eliminar_Bloque) {
            String nombre = this.pestaña.jComboBoxBloque.getSelectedItem().toString();
            this.modeloBloque.eliminarBloque(nombre);
            inicializarBloques();
        }
        if (e.getSource() == this.pestaña.Btn_Agregar_Aula) {
            String nombreBloque = this.pestaña.jComboBoxBloque.getSelectedItem().toString();
            Aula aula = new Aula(nombreBloque, JOptionPane.showInputDialog(null, "Ingrese el nombre del Aula"), JOptionPane.showInputDialog(null, "Ingrese el piso del Aula"), Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la capacidad del Aula")), JOptionPane.showInputDialog(null, "Ingrese el tipo del Aula"));

            this.modeloAula.crearAula(aula, nombreBloque);
        }
        if (e.getSource() == this.pestaña.Btn_Modificar_Aula) {
            String nombreBloque = this.pestaña.jComboBoxBloque.getSelectedItem().toString();

            Aula aula = new Aula(nombreBloque,JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre del Aula"), JOptionPane.showInputDialog(null, "Ingrese el nuevo piso del Aula"),  Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la nueva capacidad del Aula")), JOptionPane.showInputDialog(null, "Ingrese el nuevo tipo de Aula"));
            this.modeloAula.modificarAula(aula);
        }
        if (e.getSource() == this.pestaña.Btn_Eliminar_Aula) {
            String nombre = this.pestaña.jComboBoxAula.getSelectedItem().toString();
            this.modeloAula.eliminarAula(nombre);
        }
    }

  

    private void actualizarAulas() {
        String bloqueSeleccionado = (String) pestaña.jComboBoxBloque.getSelectedItem();

        if (bloqueSeleccionado != null) {
            ArrayList<Aula> aulas = this.modeloBloque.obtenerAulasPorBloque(bloqueSeleccionado);

            // Limpiar el JComboBox de aulas antes de agregar nuevas aulas
            this.pestaña.jComboBoxAula.removeAllItems();

            // Limpiar la JTable antes de agregar nuevas filas
            DefaultTableModel model = (DefaultTableModel) this.pestaña.jTableAulas.getModel();
            model.setRowCount(0);

            // Agregar las aulas al JComboBox de aulas y a la JTable
            for (Aula aula : aulas) {
                this.pestaña.jComboBoxAula.addItem(aula.getNombre());
                model.addRow(new Object[]{aula.getNombre(), aula.getTipo(), aula.getCapacidad()});
            }
this.pestaña.jTableAulas.setModel(model);
        }
    }

    public Pestaña_BloquesAulas getPestaña() {
        return this.pestaña;
    }

    public void setPestaña(Pestaña_BloquesAulas pestaña) {
        this.pestaña = pestaña;
    }

   

}
