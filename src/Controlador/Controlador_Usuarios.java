/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Usuario;
import Modelo.Modelo_Usuarios;
import Vista.Pestaña_Usuarios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

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
        if (e.getSource() == this.interfaz.Btn_Agregar_U) {
            try {
                Usuario usuario = new Usuario(this.interfaz.jTextField1.getText(), String.valueOf(this.interfaz.Txt_Clave.getPassword()), this.interfaz.jComboBox1.getSelectedItem().toString());
                if (this.modelo.agregarUsuario(usuario)) {
                    this.interfaz.getUsuarios().add(usuario);
                    this.interfaz.ActualizarTablaUsuarios();
                    JOptionPane.showMessageDialog(null, "Se agrego con Exito", "Correcto", 0);
                } else {
                    JOptionPane.showMessageDialog(null, "No se agrego el usuario", "Error", 0);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Datos mal ingrsados", "Error", 0);
            }
        }
        if (e.getSource() == this.interfaz.Btn_Modificar_U) {
            try {
                int selectedRow = this.interfaz.Tabla_Usuarios.getSelectedRow();
                if (selectedRow != -1) {
                    String usuarioAnterior=this.interfaz.Tabla_Usuarios.getValueAt(this.interfaz.Tabla_Usuarios.getSelectedRow(), this.interfaz.Tabla_Usuarios.getSelectedColumn()).toString();
                    Usuario usuarioModificado = new Usuario(this.interfaz.jTextField1.getText(), String.valueOf(this.interfaz.Txt_Clave.getPassword()), this.interfaz.jComboBox1.getSelectedItem().toString());
                    if (this.modelo.actualizarUsuario(usuarioModificado,usuarioAnterior)) {
                        for (int i = 0; i < this.interfaz.getUsuarios().size(); i++) {
                            Usuario u = this.interfaz.getUsuarios().get(i);
                            if (u.getUsuario().matches(usuarioAnterior)) {
                                this.interfaz.getUsuarios().remove(u);
                                this.interfaz.getUsuarios().add(usuarioModificado);
                                break;
                            }
                        }
                        this.interfaz.ActualizarTablaUsuarios();
                        JOptionPane.showMessageDialog(null, "Usuario modificado con éxito", "Correcto", 0);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo modificar el usuario", "Error", 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un usuario para modificar", "Advertencia", 0);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar usuario", "Error", 0);
            }
        }

        if (e.getSource() == this.interfaz.Btn_Eliminar_U) {
            try {
                int selectedRow = this.interfaz.Tabla_Usuarios.getSelectedRow();
                if (selectedRow != -1) {
                    String username = this.interfaz.jTextField1.getText();
                    String password = String.valueOf(this.interfaz.Txt_Clave.getPassword());
                    String tipo = this.interfaz.jComboBox1.getSelectedItem().toString();

                    Usuario usuario = new Usuario(username, password, tipo);

                    if (this.modelo.eliminarUsuario(usuario)) {
                        for (int i = 0; i < this.interfaz.getUsuarios().size(); i++) {
                            Usuario u = this.interfaz.getUsuarios().get(i);
                            if (u.getUsuario().matches(usuario.getUsuario())) {
                                this.interfaz.getUsuarios().remove(u);
                                break;
                            }
                        }
                        this.interfaz.ActualizarTablaUsuarios();
                        this.interfaz.jTextField1.setText("");
                        this.interfaz.Txt_Clave.setText("");
                        JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito", "Correcto", 0);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar el usuario", "Error", 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un usuario para eliminar", "Advertencia", 0);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar usuario", "Error", 0);
            }
        }

    }

    public Pestaña_Usuarios getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Pestaña_Usuarios interfaz) {
        this.interfaz = interfaz;
    }

}
