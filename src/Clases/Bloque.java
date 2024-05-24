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

    public Bloque(String nombre, ArrayList<Aula> aulas) {
        this.nombre = nombre;
        this.aulas = aulas;

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

}
