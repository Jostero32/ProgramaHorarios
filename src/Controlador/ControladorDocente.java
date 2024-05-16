/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Docente;
import Modelo.ModeloDocente;
import java.util.ArrayList;

/**
 *
 * @author Bryan
 */
public class ControladorDocente {
    private ModeloDocente modeloDocente;

    public ControladorDocente() {
        this.modeloDocente = new ModeloDocente();
    }

    public boolean agregarDocente(Docente docente) {
        return this.modeloDocente.agregarDocente(docente);
    }

    public ArrayList<Docente> obtenerTodosLosDocentes() {
        return this.modeloDocente.obtenerTodosLosDocentes();
    }

    // Otros métodos para actualizar, eliminar, etc.
}
