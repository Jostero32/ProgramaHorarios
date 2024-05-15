/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Usuario;
import Vista.Interfaz_Login;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Usuario
 */
public class Controlador_Login implements MouseListener{
    private Interfaz_Login interfaz;
    
    public Controlador_Login(){
        this.interfaz=new Interfaz_Login();
        this.interfaz.BotonEntrar.addMouseListener(this);
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==this.interfaz.BotonEntrar){
            Usuario usuarioEntrante=new Usuario("us01","1234","Admin");
            
            
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public Interfaz_Login getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Interfaz_Login interfaz) {
        this.interfaz = interfaz;
    }
    
}
