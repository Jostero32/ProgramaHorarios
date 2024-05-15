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
    private Bloque Bloque;
    private String nombre;
    private String piso;
    private String capacidad;

    public Aula(Bloque Bloque, String nombre, String piso, String capacidad) {
        this.Bloque = Bloque;
        this.nombre = nombre;
        this.piso = piso;
        this.capacidad = capacidad;
    }

    public Bloque getBloque() {
        return this.Bloque;
    }

    public void setBloque(Bloque Bloque) {
        this.Bloque = Bloque;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPiso() {
        return this.piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getCapacidad() {
        return this.capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }
    
}