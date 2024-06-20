/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.DocenteMateria;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Pestaña_Docentes_Materias extends javax.swing.JPanel {

    /**
     * Creates new form NewJPanel
     */
    public Pestaña_Docentes_Materias() {
        initComponents();
        String columnas[] = {"Docente", "Materia"};
        this.tabla.setColumnIdentifiers(columnas);
        this.jtblTabla_Docentes_Materias.setModel(tabla);
        this.jtblTabla_Docentes_Materias.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = jtblTabla_Docentes_Materias.getSelectedRow();
                if (filaSeleccionada != -1) {
                    actualizarCampos();
                }
            }
        }
    });
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtbtnAgregar = new javax.swing.JButton();
        jtbtnModificar = new javax.swing.JButton();
        jtbtnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblTabla_Docentes_Materias = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jcbxDocente = new javax.swing.JComboBox<>();
        jcbxMateria = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(730, 250));
        setPreferredSize(new java.awt.Dimension(930, 490));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtbtnAgregar.setText("Agregar");
        jtbtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbtnAgregarActionPerformed(evt);
            }
        });
        add(jtbtnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 350, 130, 40));

        jtbtnModificar.setText("Modificar");
        add(jtbtnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 350, 130, 40));

        jtbtnEliminar.setText("Eliminar");
        add(jtbtnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 350, 130, 40));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        jtblTabla_Docentes_Materias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Usuario"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblTabla_Docentes_Materias.setGridColor(new java.awt.Color(102, 102, 102));
        jtblTabla_Docentes_Materias.setRowHeight(30);
        jtblTabla_Docentes_Materias.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jtblTabla_Docentes_Materias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblTabla_Docentes_MateriasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtblTabla_Docentes_Materias);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 370, 350));

        jLabel2.setText("Docentes");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 110, 30));

        jLabel3.setText("Materia");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 140, 110, 30));

        jcbxDocente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "User" }));
        add(jcbxDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 100, 270, -1));

        jcbxMateria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "User" }));
        add(jcbxMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 180, 270, -1));
    }// </editor-fold>//GEN-END:initComponents

    public void actualizarTablaDocentesMaterias(ArrayList<DocenteMateria> docentesMaterias) {
        tabla.setRowCount(0);
        for (DocenteMateria dm : docentesMaterias) {
            tabla.addRow(new Object[]{dm.getDocenteId(), dm.getMateriaId()});
        }
    }

    private void actualizarCampos() {
    int selectedRow = jtblTabla_Docentes_Materias.getSelectedRow();
    if (selectedRow != -1) {
        // Estas líneas asumen que las columnas contienen nombres en el formato "nombre" directamente
        String docenteNombre = (String) jtblTabla_Docentes_Materias.getValueAt(selectedRow, 0);
        String materiaNombre = (String) jtblTabla_Docentes_Materias.getValueAt(selectedRow, 1);

        // Asigna directamente los nombres sin intentar convertir de String a Integer
        jcbxDocente.setSelectedItem(docenteNombre);
        jcbxMateria.setSelectedItem(materiaNombre);
    }
}

    private int findIdByName(String name, ArrayList<String> list) {
    for (String item : list) {
        if (item.contains(name)) {
            return Integer.parseInt(item.split(" - ")[0]);
        }
    }
    return -1;  // Considera manejar mejor los casos donde no se encuentra el ID.
}



    public void actualizarDocentes(ArrayList<String> docentes) {
    jcbxDocente.removeAllItems();
    if (docentes != null && !docentes.isEmpty()) {
        for (String docente : docentes) {
            jcbxDocente.addItem(docente);
        }
    } else {
        jcbxDocente.addItem("No hay docentes disponibles"); 
    }
}

public void actualizarMaterias(ArrayList<String> materias) {
    jcbxMateria.removeAllItems();
    if (materias != null && !materias.isEmpty()) {
        for (String materia : materias) {
            jcbxMateria.addItem(materia);
        }
    } else {
        jcbxMateria.addItem("No hay materias disponibles");  
    }
}

    private void jtbtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbtnAgregarActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jtbtnAgregarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void jtblTabla_Docentes_MateriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblTabla_Docentes_MateriasMouseClicked
        int selectedRow = jtblTabla_Docentes_Materias.getSelectedRow();
    if (selectedRow != -1) {
        // Obtén los nombres directamente desde la tabla
        String docenteNombre = (String) jtblTabla_Docentes_Materias.getValueAt(selectedRow, 0);
        String materiaNombre = (String) jtblTabla_Docentes_Materias.getValueAt(selectedRow, 1);

        // Encuentra el índice del docente en el combobox
        int cantDocentes = jcbxDocente.getItemCount();
        int indiceDocente = -1;
        for (int i = 0; i < cantDocentes; i++) {
            String docenteItem = (String) jcbxDocente.getItemAt(i);
            if (docenteItem.contains(docenteNombre)) {
                indiceDocente = i;
                break;
            }
        }
        if (indiceDocente != -1) {
            jcbxDocente.setSelectedIndex(indiceDocente);
        }

        // Encuentra el índice de la materia en el combobox
        int cantMaterias = jcbxMateria.getItemCount();
        int indiceMateria = -1;
        for (int i = 0; i < cantMaterias; i++) {
            String materiaItem = (String) jcbxMateria.getItemAt(i);
            if (materiaItem.contains(materiaNombre)) {
                indiceMateria = i;
                break;
            }
        }
        if (indiceMateria != -1) {
            jcbxMateria.setSelectedIndex(indiceMateria);
        }
    }
    }//GEN-LAST:event_jtblTabla_Docentes_MateriasMouseClicked

    private DefaultTableModel tabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Hacer todas las celdas no editables
        }
    };
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JComboBox<String> jcbxDocente;
    public javax.swing.JComboBox<String> jcbxMateria;
    public javax.swing.JTable jtblTabla_Docentes_Materias;
    public javax.swing.JButton jtbtnAgregar;
    public javax.swing.JButton jtbtnEliminar;
    public javax.swing.JButton jtbtnModificar;
    // End of variables declaration//GEN-END:variables

}
