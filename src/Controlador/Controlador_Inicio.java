/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Usuario;
import java.sql.Connection;
import Vista.Interfaz_Inicio;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class Controlador_Inicio {
    private Interfaz_Inicio interfaz;
    private Usuario usuario;
    private Controlador_Usuarios controlador_usuarios;
    
    
    public Controlador_Inicio(Connection con,Usuario usuario) throws SQLException{
        this.interfaz=new Interfaz_Inicio();
        this.controlador_usuarios=new Controlador_Usuarios(con);
        this.usuario=usuario;
        this.generarPestañas();
    }

    private void generarPestañas() {
        if(this.usuario.getTipo().matches("Admin")){
            this.interfaz.jTabbedPane1.addTab("Usuarios",this.controlador_usuarios.getInterfaz() );
        }
    }

    public Interfaz_Inicio getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Interfaz_Inicio interfaz) {
        this.interfaz = interfaz;
    }
    
    
}
