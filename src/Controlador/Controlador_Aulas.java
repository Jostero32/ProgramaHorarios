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
    }

    private void inicializarBloques() {
        this.pestaña.jComboBoxBloque.removeAllItems();
        for (Bloque bloque : this.bloques) {
            this.pestaña.jComboBoxBloque.addItem(bloque.getNombre());
        }
    }

    private void inicializarTabla() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre", "Tipo", "Capacidad"}, 0);
        pestaña.jTableAulas.setModel(model);

       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.pestaña.jComboBoxBloque) {
            actualizarAulas();
        }
    
        if (e.getSource() == this.pestaña.Btn_Agregar_Aula) {
            String nombreBloque = this.pestaña.jComboBoxBloque.getSelectedItem().toString();
            Aula aula = new Aula(nombreBloque,this.pestaña.txtNmbreAula.getText(), Integer.parseInt(this.pestaña.txtCapacidad.getText()),this.pestaña.txtTipo.getText());
            this.modeloAula.crearAula(aula, nombreBloque);
            actualizarAulas();
        }
        if (e.getSource() == this.pestaña.Btn_Modificar_Aula) {
            String nombreBloque = this.pestaña.jComboBoxBloque.getSelectedItem().toString();
            
            
            //aqui recordar que toca hacer una ventana aparte donde aparezca y no pueda volver a la anterior ventana
            Aula aula = new Aula(nombreBloque,this.pestaña.txtNmbreAula.getText(), Integer.parseInt(this.pestaña.txtCapacidad.getText()),this.pestaña.txtTipo.getText());
          
            
            
            
            actualizarAulas();
        }
        if (e.getSource() == this.pestaña.Btn_Eliminar_Aula) {
            String nombre = this.pestaña.jComboBoxAula.getSelectedItem().toString();
            this.modeloAula.eliminarAula(nombre);
            actualizarAulas();
        }
    }

    private void actualizarAulas() {
        String bloqueSeleccionado = (String) pestaña.jComboBoxBloque.getSelectedItem();
        this.pestaña.jLabelNombreAula.setText("Aulas del Bloque: "+bloqueSeleccionado);
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
        }
    }

 
 
    public Pestaña_Aulas getPestaña() {
        return this.pestaña;
    }

    public void setPestaña(Pestaña_Aulas pestaña) {
        this.pestaña = pestaña;
    }
}
