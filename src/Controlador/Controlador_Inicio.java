/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Usuario;
import java.sql.Connection;
import Vista.Interfaz_Inicio;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class Controlador_Inicio implements MouseListener{
    private Interfaz_Inicio interfaz;
    private Usuario usuario;
    private Controlador_Usuarios controlador_usuarios;
    private Controlador_Login login;
    //añadiendo el controlador de bloques y aulass
    private Controlador_BloquesAulas controlador_bloque;
    
    public Controlador_Inicio(Connection con,Usuario usuario, Controlador_Login login) throws SQLException{
        this.interfaz=new Interfaz_Inicio();
        this.controlador_usuarios=new Controlador_Usuarios(con);
        //igual aqui añado el controlador de bloque
        this.controlador_bloque=new Controlador_BloquesAulas(con);
        this.usuario=usuario;
        this.login=login;
        this.interfaz.Btn_CerrarSesion.addMouseListener(this);
        this.generarPestañas();
    }

    private void generarPestañas() {
        if(this.usuario.getTipo().matches("Admin")){
            this.interfaz.jTabbedPane1.addTab("Usuarios",this.controlador_usuarios.getInterfaz() );
            this.interfaz.jTabbedPane1.addTab("Bloques", this.controlador_bloque.getPestaña());
        }
    }

    public Interfaz_Inicio getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Interfaz_Inicio interfaz) {
        this.interfaz = interfaz;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==this.interfaz.Btn_CerrarSesion){
            this.login.getInterfaz().setVisible(true);
            this.interfaz.dispose();
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
    
    
}
