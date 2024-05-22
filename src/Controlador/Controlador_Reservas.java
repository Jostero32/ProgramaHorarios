/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.Pestaña_Reservas;

/**
 *
 * @author Usuario
 */
public class Controlador_Reservas {
    private Pestaña_Reservas interfaz;
    private String[][] horarios=new String[13][5];

    public Controlador_Reservas() {
        this.interfaz = new Pestaña_Reservas();
    }

    
    
    
    
    private void borrarMatrizHorarios(){
    for(int i=0;i<horarios.length;i++){
        for(int j=0;i<horarios[i].length;j++){
            horarios[i][j]="";
        }
    }
}
    public Pestaña_Reservas getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Pestaña_Reservas interfaz) {
        this.interfaz = interfaz;
    }
    
    
    
    
    
    
    
}
