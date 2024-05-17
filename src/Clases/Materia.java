/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author ASUS GAMER
 */
public class Materia {

    private String nombre;
    private Object aula;
    private Object profesor;

    public Materia(String nombre, Object aula, Object profesor) {
        this.nombre = nombre;
        this.aula = aula;
        this.profesor = profesor;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Object getAula() {
        return this.aula;
    }

    public void setAula(Object aula) {
        this.aula = aula;
    }

    public Object getProfesor() {
        return this.profesor;
    }

    public void setProfesor(Object profesor) {
        this.profesor = profesor;
    }
}
