/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Clases;

import Controlador.Controlador_Login;

/**
 *
 * @author Usuario
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Mysql m=new Mysql();
        Controlador_Login a=new Controlador_Login(m.getConnection());
        a.getInterfaz().setVisible(true);
    }
    
}
