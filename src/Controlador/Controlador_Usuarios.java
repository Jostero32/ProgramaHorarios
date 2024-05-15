/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.Pestaña_Usuarios;

/**
 *
 * @author Usuario
 */
class Controlador_Usuarios {
    private Pestaña_Usuarios interfaz;
    
    public Controlador_Usuarios (){
        this.interfaz=new Pestaña_Usuarios();
    }

    public Pestaña_Usuarios getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Pestaña_Usuarios interfaz) {
        this.interfaz = interfaz;
    }
    
}
