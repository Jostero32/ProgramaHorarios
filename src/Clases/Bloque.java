/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.ArrayList;

/**
 *
 * @author ASUS GAMER
 */
public class Bloque {

    private String nombre;
    ArrayList<Aula> aulas;
    ArrayList<Laboratorio> laboratorios;

    public Bloque(String nombre,ArrayList<Aula> aulas,ArrayList<Laboratorio> laboratorios) {
        this.nombre = nombre;
        this.aulas = aulas;
        this.laboratorios = laboratorios;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Aula> getAulas() {
        return this.aulas;
    }

    public void setAulas(ArrayList<Aula> aulas) {
        this.aulas = aulas;
    }

    public ArrayList<Laboratorio> getLaboratorios() {
        return this.laboratorios;
    }

    public void setLaboratorios(ArrayList<Laboratorio> laboratorios) {
        this.laboratorios = laboratorios;
    }

}
