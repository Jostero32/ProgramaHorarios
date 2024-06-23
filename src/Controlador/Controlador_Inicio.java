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
public class Controlador_Inicio implements MouseListener {

    private Interfaz_Inicio interfaz;
    private Usuario usuario;
    private Controlador_Usuarios controlador_usuarios;
    private Controlador_Login login;

    //añadiendo el controlador de bloques y aulass
    private Controlador_Bloques controlador_bloque;
    private Controlador_Aulas controlador_aula;

    private Controlador_Docentes controlador_docentes;
    private Controlador_Materias controlador_materias;
    private Controlador_DocentesMaterias controlador_docentes_materias;
    private Controlador_Reservas controlador_reservas;
    private Controlador_Reservas1 controlador_reservas1;
    
    
    public Controlador_Inicio(Connection con,Usuario usuario, Controlador_Login login) throws SQLException{
        this.interfaz=new Interfaz_Inicio();
        this.controlador_usuarios=new Controlador_Usuarios(con);

        //igual aqui añado el controlador de bloque
        this.controlador_aula = new Controlador_Aulas(con);
        this.controlador_bloque = new Controlador_Bloques(con);

        this.controlador_docentes = new Controlador_Docentes(con);
        this.controlador_materias = new Controlador_Materias(con);
        this.controlador_docentes_materias = new Controlador_DocentesMaterias(con);
        this.controlador_reservas=new Controlador_Reservas(con);
        this.controlador_reservas1=new Controlador_Reservas1(con);
        this.usuario=usuario;
        this.login=login;
        this.interfaz.Btn_CerrarSesion.addMouseListener(this);
        this.generarPestañas();
        this.interfaz.jLabel3.setText("Bienvenido, "+this.usuario.getUsuario());
        this.interfaz.jLabel4.setText("Tipo: "+this.usuario.getTipo());
    }

    private void generarPestañas() {
        if (this.usuario.getTipo().matches("Admin")) {
            this.interfaz.jTabbedPane1.addTab("Usuarios", this.controlador_usuarios.getInterfaz());
            this.interfaz.jTabbedPane1.addTab("Bloques", this.controlador_bloque.getPestaña());
            this.interfaz.jTabbedPane1.addTab("Aulas", this.controlador_aula.getPestaña());
            this.interfaz.jTabbedPane1.addTab("Docentes", this.controlador_docentes.getInterfaz());
            this.interfaz.jTabbedPane1.addTab("Materias", this.controlador_materias.getInterfaz());
            this.interfaz.jTabbedPane1.addTab("DocentesMaterias", this.controlador_docentes_materias.getPestaña());

        }
        this.interfaz.jTabbedPane1.addTab("Horarios", this.controlador_reservas.getInterfaz());
        this.interfaz.jTabbedPane1.addTab("Reservas", this.controlador_reservas1.getInterfaz());
    }

    public Interfaz_Inicio getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Interfaz_Inicio interfaz) {
        this.interfaz = interfaz;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.interfaz.Btn_CerrarSesion) {
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
