/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Bryan
 */


public class DocenteMateria {
    private int docenteId;
    private String docenteNombre;
    private int materiaId;
    private String materiaNombre;

    public DocenteMateria(int docenteId, String docenteNombre, int materiaId, String materiaNombre) {
        this.docenteId = docenteId;
        this.docenteNombre = docenteNombre;
        this.materiaId = materiaId;
        this.materiaNombre = materiaNombre;
    }

    public int getDocenteId() {
        return docenteId;
    }

    public String getDocenteNombre() {
        return docenteNombre;
    }

    public int getMateriaId() {
        return materiaId;
    }

    public String getMateriaNombre() {
        return materiaNombre;
    }
}

