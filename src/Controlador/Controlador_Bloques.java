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
            model = new DefaultTableModel(new Object[]{bloque.getNombre()}, 0);
        }

        pestaña.jTableBloques.setModel(model);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.pestaña.Btn_Agregar_Bloque) {
            String nombreBloque = JOptionPane.showInputDialog(null, "Agregue el nombre del nuevo Bloque");

            Bloque bloque = new Bloque(nombreBloque, null);
            this.modeloBloque.crearBloque(bloque);

        }

        if (e.getSource() == this.pestaña.Btn_Modificar_Bloque) {
            String nombreAnterior = this.pestaña.jComboBoxBloque.getSelectedItem().toString();
            this.modeloBloque.modificarBloque(nombreAnterior, JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre del Bloque"));

        }
        if (e.getSource() == this.pestaña.Btn_Eliminar_Bloque) {
            String nombre = this.pestaña.jComboBoxBloque.getSelectedItem().toString();
            this.modeloBloque.eliminarBloque(nombre);

        }
   
    }

    public Pestaña_Bloques getPestaña() {
        return this.pestaña;
    }

    public void setPestaña(Pestaña_Bloques pestaña) {
        this.pestaña = pestaña;
    }
}
