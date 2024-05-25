/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Docente;
import Clases.Materia;
import Modelo.Modelo_Reservas;
import Vista.Interfaz_Agregar_Horario;
import Vista.Pestaña_Reservas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Controlador_Reservas implements ActionListener {

    private Pestaña_Reservas interfaz;
    private Modelo_Reservas modelo;
    private String[][] horariosCalendario = new String[13][5];
    private Interfaz_Agregar_Horario interfazAgregar;

    public Controlador_Reservas(Connection con) {
        this.interfazAgregar = new Interfaz_Agregar_Horario();
        this.modelo = new Modelo_Reservas(con);
        this.interfaz = new Pestaña_Reservas();
        this.interfaz.boton_agregar.addActionListener(this);
        this.interfazAgregar.ComboBox_Docentes.addActionListener(this);
        this.agregarHorarios();
    }

    private void borrarMatrizHorarios() {
        for (int i = 0; i < horariosCalendario.length; i++) {
            for (int j = 0; i < horariosCalendario[i].length; j++) {
                horariosCalendario[i][j] = "";
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
        if (e.getSource() == this.interfazAgregar.ComboBox_Docentes) {
            this.actualizarMateriasDocente(Integer.parseInt(this.interfazAgregar.ComboBox_Docentes.getSelectedItem().toString().split("-")[0]));
        }
    }

    private void actualizarCamposInterfazAgregar() {
        this.ActualizarDocentes();
        
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

}
