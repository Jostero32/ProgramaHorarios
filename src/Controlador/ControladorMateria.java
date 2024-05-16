/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Materia;
import Modelo.ModeloMateria;
import java.util.ArrayList;
/**
 *
 * @author Bryan
 */
public class ControladorMateria {
    private ModeloMateria modeloMateria;

    public ControladorMateria() {
        this.modeloMateria = new ModeloMateria();
    }

    public boolean agregarMateria(Materia materia) {
        return this.modeloMateria.agregarMateria(materia);
    }

    public ArrayList<Materia> obtenerTodasLasMaterias() {
        return this.modeloMateria.obtenerTodasLasMaterias();
    }

    // Otros métodos para actualizar, eliminar, etc.
}
