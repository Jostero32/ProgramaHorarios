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

public class Controlador_Usuarios implements ActionListener {

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
                    JOptionPane.showMessageDialog(null, "Usuario creado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al crear usuario. El usuario puede ya existir o hubo un problema en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Datos mal ingresados", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == this.interfaz.Btn_Modificar_U) {
            try {
                int selectedRow = this.interfaz.Tabla_Usuarios.getSelectedRow();
                if (selectedRow != -1) {
                    String usuarioAnterior = this.interfaz.Tabla_Usuarios.getValueAt(selectedRow, 0).toString();
                    Usuario usuarioModificado = new Usuario(this.interfaz.jTextField1.getText(), String.valueOf(this.interfaz.Txt_Clave.getPassword()), this.interfaz.jComboBox1.getSelectedItem().toString());
                    if (this.modelo.actualizarUsuario(usuarioModificado, usuarioAnterior)) {
                        for (int i = 0; i < this.interfaz.getUsuarios().size(); i++) {
                            Usuario u = this.interfaz.getUsuarios().get(i);
                            if (u.getUsuario().matches(usuarioAnterior)) {
                                this.interfaz.getUsuarios().set(i, usuarioModificado);
                                break;
                            }
                        }
                        this.interfaz.ActualizarTablaUsuarios();
                        JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar usuario. El usuario puede ya existir o hubo un problema en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un usuario para modificar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == this.interfaz.Btn_Eliminar_U) {
            try {
                int selectedRow = this.interfaz.Tabla_Usuarios.getSelectedRow();
                if (selectedRow != -1) {
                    String username = this.interfaz.Tabla_Usuarios.getValueAt(selectedRow, 0).toString();
                    Usuario usuario = new Usuario(username, "", "");

                    if (this.modelo.eliminarUsuario(usuario)) {
                        for (int i = 0; i < this.interfaz.getUsuarios().size(); i++) {
                            Usuario u = this.interfaz.getUsuarios().get(i);
                            if (u.getUsuario().matches(usuario.getUsuario())) {
                                this.interfaz.getUsuarios().remove(i);
                                break;
                            }
                        }
                        this.interfaz.ActualizarTablaUsuarios();
                        this.interfaz.jTextField1.setText("");
                        this.interfaz.Txt_Clave.setText("");
                        JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar usuario. Puede que el usuario no exista o hubo un problema en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un usuario para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar usuario", "Error", JOptionPane.ERROR_MESSAGE);
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
