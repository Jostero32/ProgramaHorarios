/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author ASUS GAMER
 */
public class Aula {

    private String nombreBloque;
    private String nombre;
    private int capacidad;
    private String tipo;

    public Aula(String nombreBloque, String nombre, int capacidad,String tipo) {
        this.nombreBloque = nombreBloque;
        this.nombre = nombre;
       
        this.capacidad = capacidad;
        this.tipo = tipo;
    }

    public String getNombreBloque() {
        return this.nombreBloque;
    }

    public void setNombreBloque(String nombreBloque) {
        this.nombreBloque = nombreBloque;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getCapacidad() {
        return this.capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
