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

    public Bloque(String nombre) {
        this.nombre = nombre;
        this.aulas = new ArrayList<>();
        this.laboratorios = new ArrayList<>();
    }

}
