/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author ASUS GAMER
 */
public class Laboratorio extends Aula{
    private int numComputadoras;

    public Laboratorio(int numComputadoras, Bloque Bloque, String nombre, String piso, String capacidad) {
        super(Bloque, nombre, piso, capacidad);
        this.numComputadoras = numComputadoras;
    }

    public int getNumComputadoras() {
        return this.numComputadoras;
    }

    public void setNumComputadoras(int numComputadoras) {
        this.numComputadoras = numComputadoras;
    }
    
}
