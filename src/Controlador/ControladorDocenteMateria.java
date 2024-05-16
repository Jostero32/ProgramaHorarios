/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.DocenteMateria;
import Modelo.ModeloDocenteMateria;

/**
 *
 * @author Bryan
 */
public class ControladorDocenteMateria {
    private ModeloDocenteMateria modeloDocenteMateria;

    public ControladorDocenteMateria() {
        this.modeloDocenteMateria = new ModeloDocenteMateria();
    }

    public boolean asignarMateriaADocente(DocenteMateria docenteMateria) {
        return this.modeloDocenteMateria.asignarMateriaADocente(docenteMateria);
    }

    // Otros métodos para obtener, actualizar, eliminar, etc.
}
