/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Aula;
import Clases.Bloque;
import Modelo.Modelo_Bloques;
import Vista.Pestaña_Bloques;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 *
 * @author ASUS GAMER
 */
public class Controlador_Bloques implements ActionListener, MouseListener {

    private ArrayList<Bloque> bloques;
    private Modelo_Bloques modeloBloque;
    private Pestaña_Bloques pestaña;

    public Controlador_Bloques(Connection conn) {
        this.modeloBloque = new Modelo_Bloques((com.mysql.jdbc.Connection) conn);
        this.pestaña = new Pestaña_Bloques();
        this.bloques = this.modeloBloque.verTodosLosBloques();

        this.pestaña.jComboBoxBloque.addMouseListener(this);

        inicializarBloques();
    }

    private void inicializarBloques() {
        for (Bloque bloque : this.bloques) {
            this.pestaña.jComboBoxBloque.addItem(bloque.getNombre());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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

            this.pestaña.jComboBoxAula.removeAllItems();

            for (Aula aula : aulas) {
                this.pestaña.jComboBoxAula.addItem(aula.getNombre());
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
