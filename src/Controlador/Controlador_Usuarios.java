/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Modelo_Usuarios;
import Vista.Pestaña_Usuarios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
class Controlador_Usuarios implements ActionListener {

    private Pestaña_Usuarios interfaz;
    private Modelo_Usuarios modelo;

    public Controlador_Usuarios(Connection conn) throws SQLException {
        this.interfaz = new Pestaña_Usuarios();
        this.modelo = new Modelo_Usuarios(conn);
        this.interfaz.Btn_Agregar_U.addActionListener(this);
        this.interfaz.Btn_Eliminar_U.addActionListener(this);
        this.interfaz.Btn_Modificar_U.addActionListener(this);
        this.interfaz.setUsuarios(this.modelo.obtenerTodosLosUsuarios());
        this.interfaz.ActualizarTablaUsuarios();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.interfaz.Btn_Agregar_U){
            
        }
    }

    public Pestaña_Usuarios getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Pestaña_Usuarios interfaz) {
        this.interfaz = interfaz;
    }

}
