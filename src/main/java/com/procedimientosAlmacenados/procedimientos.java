package com.procedimientosAlmacenados;

import java.awt.HeadlessException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class procedimientos extends javax.swing.JFrame {

    Conexion instanciamsql = Conexion.getInstancia();

    public procedimientos() {
        initComponents();
        this.setTitle("Listando Datos con Procedimientos Almacandos");
    }

    private void listarDatos() {
        try {
            Connection conexion = instanciamsql.conectarBd();
            //llamamos al procedimiento almacenado
            String sql = "{call listarAlumnosDelA}";
            CallableStatement procedimientoAlmacenado = conexion.prepareCall(sql);
            ResultSet consulta = procedimientoAlmacenado.executeQuery();
            //mientras alla datos
            jTextArea1.setText("");
            while (consulta.next()) {
                jTextArea1.append(consulta.getInt(1) + "      ");

                jTextArea1.append(consulta.getString(2) + "      ");
                jTextArea1.append(consulta.getString(3) + "      ");
                jTextArea1.append(consulta.getString(4) + "      ");
                jTextArea1.append("\n");
            }
            procedimientoAlmacenado.close();
            instanciamsql.cerrarConexion(conexion);
        } catch (SQLException e) {
            System.out.println("Error al listar a los alumnos => " + e.toString());

        }
    }

    private boolean estanVacios() {
        return txtApellido.getText().trim().isEmpty()
                || txtNombre.getText().trim().isEmpty()
                || cbxSeccion.getSelectedItem().toString().equals("=Seleccionar=");

    }

    private void registrarAlumno() {
        if (estanVacios()) {
            JOptionPane.showMessageDialog(rootPane, "Por favor completar los campos");
        } else {
            try {
                Connection conexino = instanciamsql.conectarBd();

                String sql = "{call insertarAlumno(?,?,?)}";
                CallableStatement procedimientoAlmacenado = conexino.prepareCall(sql);

                String nombre, apellido, seccion;
                nombre = txtNombre.getText().trim();
                apellido = txtApellido.getText();
                seccion = cbxSeccion.getSelectedItem().toString();

                procedimientoAlmacenado.setString(1, nombre);
                procedimientoAlmacenado.setString(2, apellido);
                procedimientoAlmacenado.setString(3, seccion);

                procedimientoAlmacenado.executeUpdate();
                procedimientoAlmacenado.close();
                instanciamsql.cerrarConexion(conexino);

                JOptionPane.showMessageDialog(rootPane, "Alumno Registrado");
                this.limpiarCampos();
            } catch (SQLException ex) {
                System.out.println("Error al insertar un alumno => " + ex.toString());
            }
        }
    }

    private void actualizarDatos() {
        try {
            int id;
            id = Integer.parseInt(txtId.getText());
            Connection conexion = instanciamsql.conectarBd();
            CallableStatement procedimientoBuscar = conexion.prepareCall("{call buscarDatos (?)}");
            procedimientoBuscar.setString(1, txtId.getText().trim());
            ResultSet consulta = procedimientoBuscar.executeQuery();
            while (consulta.next()) {
                txtApellido.setText(consulta.getString("apellido"));
                txtNombre.setText(consulta.getString("nombre"));
                cbxSeccion.setSelectedItem(consulta.getString("seccion"));
            }

            instanciamsql.cerrarConexion(conexion);
        } catch (NumberFormatException | SQLException e) {
            System.out.println("Error al actualizar datos => " + e.getMessage());
        }

    }

    private void modificarDatos() {
        try {
            Connection conecion = instanciamsql.conectarBd();
            int id;
            String nombre, apellido, seccion;
            String sql = "{ call actualizarDatos (?,?,?,?)}";
            CallableStatement procedimientoModificar = conecion.prepareCall(sql);

            nombre = txtNombre.getText();
            apellido = txtApellido.getText();
            seccion = cbxSeccion.getSelectedItem().toString();
            id = Integer.parseInt(txtId.getText());

            procedimientoModificar.setString(1, nombre);
            procedimientoModificar.setString(2, apellido);
            procedimientoModificar.setString(3, seccion);
            procedimientoModificar.setInt(4, id);

            procedimientoModificar.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, "Registro Modificado");
            this.listarDatos();
            instanciamsql.cerrarConexion(conecion);

        } catch (SQLException ex) {
            System.out.println("Error al modificar => " + ex.getMessage());
        }
    }

    private void eliminarDato() {
        try {
            Connection coonexion = instanciamsql.conectarBd();
            int id;

            String sql = "{call eliminarDato(?)}";
            CallableStatement procedimientoEliminar = coonexion.prepareCall(sql);

            id = Integer.parseInt(txtId.getText());
            procedimientoEliminar.setInt(1, id);
            procedimientoEliminar.executeUpdate();
            listarDatos();
            JOptionPane.showMessageDialog(rootPane, "Registro eliminado");
            txtId.setText(null);

            instanciamsql.cerrarConexion(coonexion);
        } catch (HeadlessException | NumberFormatException | SQLException e) {
            System.out.println("Error al eliminar => " + e.toString());
        }

    }

    public void limpiarCampos() {
        txtApellido.setText(null);
        txtNombre.setText(null);
        cbxSeccion.setSelectedIndex(0);
        txtNombre.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        txtApellido = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        cbxSeccion = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jButton1.setText("Listar Alumnos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        txtApellido.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Apellido:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DialogInput", 1, 18))); // NOI18N
        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });

        txtNombre.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Nombre:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DialogInput", 1, 18))); // NOI18N
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        cbxSeccion.setBackground(new java.awt.Color(204, 204, 255));
        cbxSeccion.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        cbxSeccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSeccionActionPerformed(evt);
            }
        });

        jButton2.setText("Grabar Alumno");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtId.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Buscar ID:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DialogInput", 1, 18))); // NOI18N
        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar Id");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        jButton3.setText("Eliminar Registro");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxSeccion, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addGap(8, 8, 8)
                        .addComponent(cbxSeccion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(btnBuscar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(12, Short.MAX_VALUE)
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.listarDatos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        String seccion[] = {"=Seleccionar=", "A", "B"};
        for (int i = 0; i < seccion.length; i++) {
            cbxSeccion.addItem(seccion[i]);

        }
    }//GEN-LAST:event_formWindowOpened

    private void cbxSeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSeccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxSeccionActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.registrarAlumno();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        this.actualizarDatos();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        this.modificarDatos();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        this.eliminarDato();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(procedimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(procedimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(procedimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(procedimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new procedimientos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<String> cbxSeccion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
