/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

/**
 *
 * @author User
 */
public class Interfaz_Agregar_Horario extends javax.swing.JFrame {
    int xMouse, yMouse;
    /**
     * Creates new form Interfaz_Agregar_ProvCiu
     */
    public Interfaz_Agregar_Horario() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.cargarImagen(this.jLabel1, "/Recursos/Agregar_Fondo350-250.png");
        this.fecha_inicio.setDate(new java.util.Date());
        this.fecha_fin.setMinSelectableDate(this.fecha_inicio.getDate());
    }
    public void cargarImagen(javax.swing.JLabel jLabel,String src) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(src));
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(jLabel.getWidth(), jLabel.getHeight(), Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        jLabel.setIcon(imageIcon);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        BTN_Agregar1 = new javax.swing.JLabel();
        BTN_Cancelar = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ComboBox_Aulas = new javax.swing.JComboBox<>();
        ComboBox_Bloques = new javax.swing.JComboBox<>();
        ComboBox_Materias = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        ComboBox_Dia = new javax.swing.JComboBox<>();
        ComboBox_Horario = new javax.swing.JComboBox<>();
        ComboBox_Docentes = new javax.swing.JComboBox<>();
        fecha_inicio = new com.toedter.calendar.JDateChooser();
        fecha_fin = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BTN_Agregar1.setBackground(new java.awt.Color(159, 20, 24));
        BTN_Agregar1.setFont(new java.awt.Font("Yu Gothic Medium", 0, 12)); // NOI18N
        BTN_Agregar1.setForeground(new java.awt.Color(255, 255, 255));
        BTN_Agregar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BTN_Agregar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 0, 0), null, new java.awt.Color(255, 0, 0)));
        BTN_Agregar1.setOpaque(true);
        jPanel1.add(BTN_Agregar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 320, 90, 30));

        BTN_Cancelar.setBackground(new java.awt.Color(159, 20, 24));
        BTN_Cancelar.setFont(new java.awt.Font("Yu Gothic Medium", 0, 12)); // NOI18N
        BTN_Cancelar.setForeground(new java.awt.Color(255, 255, 255));
        BTN_Cancelar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BTN_Cancelar.setText("CANCELAR");
        BTN_Cancelar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 0, 0), null, new java.awt.Color(255, 0, 0)));
        BTN_Cancelar.setOpaque(true);
        BTN_Cancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BTN_CancelarMouseClicked(evt);
            }
        });
        jPanel1.add(BTN_Cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 90, 30));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Yu Gothic Medium", 0, 14)); // NOI18N
        jLabel9.setText("Bloque:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 200, 60, -1));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Yu Gothic Medium", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Aula:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, 60, -1));

        jPanel1.add(ComboBox_Aulas, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 260, 170, 30));

        jPanel1.add(ComboBox_Bloques, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 200, 170, 30));

        jPanel1.add(ComboBox_Materias, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 170, 30));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Yu Gothic Medium", 0, 14)); // NOI18N
        jLabel6.setText("Docente:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 60, 20));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Yu Gothic Medium", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Dia:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 60, -1));

        ComboBox_Dia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-----", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes" }));
        jPanel1.add(ComboBox_Dia, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 170, 30));

        jPanel1.add(ComboBox_Horario, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 170, 30));

        ComboBox_Docentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ComboBox_DocentesMouseClicked(evt);
            }
        });
        jPanel1.add(ComboBox_Docentes, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 170, 30));

        fecha_inicio.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                fecha_inicioPropertyChange(evt);
            }
        });
        jPanel1.add(fecha_inicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 140, 40));

        fecha_fin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                fecha_finPropertyChange(evt);
            }
        });
        jPanel1.add(fecha_fin, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 140, 40));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Yu Gothic Medium", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Horario:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 60, -1));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Yu Gothic Medium", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Materia:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 60, -1));

        jLabel3.setText("Fecha Inicio:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 70, 30));

        jLabel2.setText("Fecha Fin:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, 70, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Agregar_Fondo350-250.png"))); // NOI18N
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 370));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 530, 370));

        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.setOpaque(false);
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 530, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BTN_CancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTN_CancelarMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_BTN_CancelarMouseClicked

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jPanel2MouseDragged

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        // TODO add your handling code here:
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jPanel2MousePressed

    private void ComboBox_DocentesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboBox_DocentesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBox_DocentesMouseClicked

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
    }//GEN-LAST:event_formWindowLostFocus

    private void fecha_inicioPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_fecha_inicioPropertyChange
        // TODO add your handling code here:
        this.fecha_fin.setMinSelectableDate(this.fecha_inicio.getDate());
    }//GEN-LAST:event_fecha_inicioPropertyChange

    private void fecha_finPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_fecha_finPropertyChange
        // TODO add your handling code here:
        this.fecha_inicio.setMaxSelectableDate(this.fecha_fin.getDate());
    }//GEN-LAST:event_fecha_finPropertyChange

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz_Agregar_Horario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz_Agregar_Horario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz_Agregar_Horario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz_Agregar_Horario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz_Agregar_Horario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel BTN_Agregar1;
    public javax.swing.JLabel BTN_Cancelar;
    public javax.swing.JComboBox<String> ComboBox_Aulas;
    public javax.swing.JComboBox<String> ComboBox_Bloques;
    public javax.swing.JComboBox<String> ComboBox_Dia;
    public javax.swing.JComboBox<String> ComboBox_Docentes;
    public javax.swing.JComboBox<String> ComboBox_Horario;
    public javax.swing.JComboBox<String> ComboBox_Materias;
    public com.toedter.calendar.JDateChooser fecha_fin;
    public com.toedter.calendar.JDateChooser fecha_inicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}