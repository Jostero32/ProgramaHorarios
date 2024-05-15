/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Usuario;
import Modelo.Modelo_Usuarios;
import Vista.Interfaz_Inicio;
import Vista.Interfaz_Login;
import Vista.Pestaña_Usuarios;
import java.sql.Connection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class Controlador_Login implements MouseListener {

    private Interfaz_Login interfaz;
    private Modelo_Usuarios modeloUsuario;
    private Connection con;

    public Controlador_Login(Connection con) {
        this.interfaz = new Interfaz_Login();
        this.modeloUsuario = new Modelo_Usuarios(con);
        this.con=con;
        this.interfaz.BotonEntrar.addMouseListener(this);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.interfaz.BotonEntrar) {
            try {
                Usuario us = this.modeloUsuario.entrarLogin(this.interfaz.TextField_Usuario.getText(), this.interfaz.TextField_Clave.getText());
                if (us != null) {
                    Controlador_Inicio inicio=new Controlador_Inicio(this.con,us);
                    inicio.getInterfaz().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o Contraseña Incorrectos", "Error", 0);
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
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
