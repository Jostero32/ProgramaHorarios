/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Bloque {
    private String nombre;
    private ArrayList<Object> listaAulas;

    public Bloque(String nombre) {
        this.nombre = nombre;
        this.listaAulas=new ArrayList<>();
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Object> getListaAulas() {
        return this.listaAulas;
    }

    public void setListaAulas(ArrayList<Object> listaAulas) {
        this.listaAulas = listaAulas;
    }
    
    
    
}
