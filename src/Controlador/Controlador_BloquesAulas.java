/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Aula;
import Clases.Bloque;
import Modelo.Modelo_Aulas;
import Modelo.Modelo_Bloques;
import Vista.Pestaña_Bloques;
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
public class Controlador_BloquesAulas implements ActionListener, MouseListener {
    
    private ArrayList<Bloque> bloques;
    private Modelo_Bloques modeloBloque;
    private Modelo_Aulas modeloAula;
    private Pestaña_Bloques pestaña;
    
    public Controlador_BloquesAulas(Connection conn) {
        this.modeloBloque = new Modelo_Bloques(conn);
        this.modeloAula= new Modelo_Aulas(conn);
        this.pestaña = new Pestaña_Bloques();
        this.bloques = this.modeloBloque.verTodosLosBloques();
        this.pestaña.Btn_Agregar_Aula.addActionListener(this);
        this.pestaña.Btn_Agregar_Bloque.addActionListener(this);
        this.pestaña.Btn_Eliminar_Aula.addActionListener(this);
        this.pestaña.Btn_Eliminar_Bloque.addActionListener(this);
        this.pestaña.Btn_Modificar_Aula.addActionListener(this);
        this.pestaña.Btn_Modificar_Bloque.addActionListener(this);
        this.pestaña.jComboBoxBloque.addMouseListener(this);
        actualizarAulas();
        inicializarBloques();
    }
    
    private void inicializarBloques() {
        for (Bloque bloque : this.bloques) {
            this.pestaña.jComboBoxBloque.addItem(bloque.getNombre());
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==this.pestaña.Btn_Agregar_Bloque) {
            ArrayList<Aula> aulas=new ArrayList<>();
            String nombre=JOptionPane.showInputDialog(null,"Agregue el nombre del nuevo Bloque");
            while (JOptionPane.showConfirmDialog(null, "Quiere agregar aulas a "+nombre+"?","Agregar Aulas",JOptionPane.YES_NO_OPTION)==0) {
                Aula aula=new Aula(nombre, JOptionPane.showInputDialog(null, "Ingrese el nombre del Aula"), JOptionPane.showInputDialog(null, "Ingrese el piso del Aula"), JOptionPane.showInputDialog(null, "Ingrese la capacidad del Aula"), JOptionPane.showInputDialog(null, "Ingrese el tipo del Aula"));
            aulas.add(aula);
            }
            Bloque bloque=new Bloque(nombre, aulas);
           this.modeloBloque.crearBloque(bloque);
        }
        
        if (e.getSource()==this.pestaña.Btn_Modificar_Bloque) {
            String nombreAnterior=this.pestaña.jComboBoxBloque.getSelectedItem().toString();
            
           this.modeloBloque.modificarBloque(nombreAnterior, JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre del Bloque"));
        }
        if (e.getSource()==this.pestaña.Btn_Eliminar_Bloque) {
           String nombre=this.pestaña.jComboBoxBloque.getSelectedItem().toString();
            this.modeloBloque.eliminarBloque(nombre);
        }
        if (e.getSource()==this.pestaña.Btn_Agregar_Aula) {
           
        }
        if (e.getSource()==this.pestaña.Btn_Modificar_Aula) {
           
        }
        if (e.getSource()==this.pestaña.Btn_Eliminar_Aula) {
           
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.pestaña.jComboBoxBloque) {
            actualizarAulas();
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
            
        }
    }
    
    public Pestaña_Bloques getPestaña() {
        return this.pestaña;
    }
    
    public void setPestaña(Pestaña_Bloques pestaña) {
        this.pestaña = pestaña;
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
    
}
