/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Usuario;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Pestaña_Bloques extends javax.swing.JPanel {

    /**
     * Creates new form NewJPanel
     */
    public Pestaña_Bloques() {
        initComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBloques = new javax.swing.JTable();
        Btn_Agregar_Bloque = new javax.swing.JButton();
        Btn_Modificar_Bloque = new javax.swing.JButton();
        Btn_Eliminar_Bloque = new javax.swing.JButton();
        jComboBoxBloque = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txtNombrebloque = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(730, 250));
        setPreferredSize(new java.awt.Dimension(600, 300));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableBloques.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableBloques.setEnabled(false);
        jTableBloques.setFocusable(false);
        jScrollPane1.setViewportView(jTableBloques);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 360, 200));

        Btn_Agregar_Bloque.setBackground(new java.awt.Color(153, 255, 153));
        Btn_Agregar_Bloque.setForeground(new java.awt.Color(255, 255, 255));
        Btn_Agregar_Bloque.setText("Agregar");
        Btn_Agregar_Bloque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Agregar_BloqueActionPerformed(evt);
            }
        });
        add(Btn_Agregar_Bloque, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 110, 30));

        Btn_Modificar_Bloque.setText("Modificar");
        add(Btn_Modificar_Bloque, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, 110, 30));

        Btn_Eliminar_Bloque.setBackground(new java.awt.Color(204, 0, 0));
        Btn_Eliminar_Bloque.setForeground(new java.awt.Color(255, 255, 255));
        Btn_Eliminar_Bloque.setText("Eliminar");
        add(Btn_Eliminar_Bloque, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 220, 110, 30));

        add(jComboBoxBloque, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 130, 100, -1));

        jLabel1.setText("Nombre:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, -1, -1));
        add(txtNombrebloque, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 140, -1));
    }// </editor-fold>//GEN-END:initComponents

    

    
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void Btn_Agregar_BloqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Agregar_BloqueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Btn_Agregar_BloqueActionPerformed

    public DefaultTableModel tabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Hacer todas las celdas no editables
        }
    };
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton Btn_Agregar_Bloque;
    public javax.swing.JButton Btn_Eliminar_Bloque;
    public javax.swing.JButton Btn_Modificar_Bloque;
    public javax.swing.JComboBox<String> jComboBoxBloque;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTableBloques;
    public javax.swing.JTextField txtNombrebloque;
    // End of variables declaration//GEN-END:variables

}
