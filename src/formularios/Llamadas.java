package formularios;

import clases.Cliente;
import clases.Empleado;
import clases.llamadas;
import formularios.HistorialLlamadas;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;


public class Llamadas extends javax.swing.JFrame {


    //aqui me muestra el combo box con los clientes
    public Llamadas() {
        initComponents();
        
        Cliente.comboCliente(jComboBoxCodigoCliente);
        
        jComboBoxCodigoCliente.addActionListener(e -> {
        Cliente seleccionado = (Cliente) jComboBoxCodigoCliente.getSelectedItem();
        if (seleccionado != null) {
            jLabelNombreCliente.setText(seleccionado.getNombre());
        }
    });
        
        Empleado.comboEmpleados(jComboBoxCodigoEmpleado);
        
        jComboBoxCodigoEmpleado.addActionListener(e -> {
        Empleado seleccionado = (Empleado) jComboBoxCodigoEmpleado.getSelectedItem();
        if (seleccionado != null) {
            jLabelNombreEmpleado.setText(seleccionado.getNombre());
        }
    });
        
    }
  
    //metodo para limpiar el formulario 
    
    private void limpiarFormulario() {
     
        jTextAreanota.setText("");
        jTextFieldDuracion1.setText("");

  
        jLabelNombreCliente.setText("");
        jLabelNombreEmpleado.setText("");

     
        jComboBoxCodigoCliente.setSelectedIndex(0);
        jComboBoxCodigoEmpleado.setSelectedIndex(0);
        jComboBoxTipo.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxTipo = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreanota = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxCodigoCliente = new javax.swing.JComboBox<>();
        jLabelNombreCliente = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldDuracion1 = new javax.swing.JTextField();
        jButtonCrono = new javax.swing.JButton();
        jComboBoxCodigoEmpleado = new javax.swing.JComboBox<>();
        jLabelNombreEmpleado = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nueva Llamada");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Llamadas");

        jLabel3.setText("Codigo Cliente:");

        jLabel4.setText("Codigo Empleado:");

        jComboBoxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Servicio al cliente", "Soporte", "Ventas", "Cobros" }));

        jTextAreanota.setColumns(20);
        jTextAreanota.setRows(5);
        jScrollPane2.setViewportView(jTextAreanota);

        jLabel7.setText("Tipo:");

        jLabel8.setText("Nota:");

        jButton5.setText("Historial");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setText("Nombre Cliente:");

        jComboBoxCodigoCliente.setModel(new javax.swing.DefaultComboBoxModel<Cliente>());
        jComboBoxCodigoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCodigoClienteActionPerformed(evt);
            }
        });

        jLabel9.setText("Duracion:");

        jTextFieldDuracion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDuracion1ActionPerformed(evt);
            }
        });

        jButtonCrono.setText("Cronometro");
        jButtonCrono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCronoActionPerformed(evt);
            }
        });

        jComboBoxCodigoEmpleado.setModel(new javax.swing.DefaultComboBoxModel<Empleado>());
        jComboBoxCodigoEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCodigoEmpleadoActionPerformed(evt);
            }
        });

        jLabel6.setText("Nombre Empleado:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(94, 94, 94)
                        .addComponent(jLabelNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(81, 81, 81)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxCodigoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldDuracion1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(76, 76, 76)
                                .addComponent(jLabelNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(113, 113, 113)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(jButtonCrono)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton5)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton2))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(115, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabelNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxCodigoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabelNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel9))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldDuracion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButtonCrono)
                    .addComponent(jButton5))
                .addGap(64, 64, 64))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       HistorialLlamadas hl= new HistorialLlamadas();
        hl.setVisible(true);       
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        //boton de guardado
try {
        llamadas nuevaLlamada = new llamadas();
        

        String itemClienteCompleto = jComboBoxCodigoCliente.getSelectedItem().toString();
        int indiceGuion = itemClienteCompleto.indexOf('-');

        if (indiceGuion > 0) {
            String codigoClienteStr = itemClienteCompleto.substring(0, indiceGuion).trim();
            nuevaLlamada.setCodigo_cliente(Integer.parseInt(codigoClienteStr));
        } else {
            throw new NumberFormatException("El formato del código de cliente no es válido.");
        }
        
        nuevaLlamada.setNombre_cliente(jLabelNombreCliente.getText());

        String itemEmpleadoCompleto = jComboBoxCodigoEmpleado.getSelectedItem().toString();
        int indiceGuionEmpleado = itemEmpleadoCompleto.indexOf('-');

        if (indiceGuionEmpleado > 0) {

            String codigoEmpleadoStr = itemEmpleadoCompleto.substring(0, indiceGuionEmpleado).trim();

            nuevaLlamada.setCodigo_empleado(Integer.parseInt(codigoEmpleadoStr)); 
        } else {
            throw new NumberFormatException("El formato del código de empleado no es válido o no se ha seleccionado.");
        }
        
        nuevaLlamada.setTipo(jComboBoxTipo.getSelectedItem().toString());

        nuevaLlamada.setNota(jTextAreanota.getText());
        
        String duracionStr = jTextFieldDuracion1.getText();
        int duracionTotalSegundos = 0;

        try {
            if (!duracionStr.isEmpty()) {
                String[] partes = duracionStr.split(":");
                if (partes.length == 3) {
                    int h = Integer.parseInt(partes[0].replaceAll("[^0-9]", ""));
                    int m = Integer.parseInt(partes[1].replaceAll("[^0-9]", ""));
                    int s = Integer.parseInt(partes[2].replaceAll("[^0-9]", ""));
                    
                    duracionTotalSegundos = (h * 3600) + (m * 60) + s;
                } else {
                    throw new NumberFormatException("Formato de duración del cronómetro inválido: " + duracionStr);
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear la duración del cronómetro. Se guardará 0. Detalle: " + e.getMessage());
            duracionTotalSegundos = 0;
        }
        
        nuevaLlamada.setDuracion(duracionTotalSegundos);
        
      
        nuevaLlamada.setFecha_hora(LocalDateTime.now());
        
        
llamadas ll = new llamadas();
        boolean guardadoExitoso = ll.GuardarLLamada(nuevaLlamada);

        if (guardadoExitoso) {
            JOptionPane.showMessageDialog(this, "Llamada registrada exitosamente.", "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        }
        
    } catch (NumberFormatException | NullPointerException e) {
        // Mejor manejo de errores para saber qué falló
        String mensajeError = "Error en la selección o formato de datos. Asegúrese de seleccionar un Cliente y un Empleado válidos. Detalle: " + e.getMessage();
        System.out.println(mensajeError);
        JOptionPane.showMessageDialog(this, mensajeError, "Error al Guardar", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
        System.out.println("Error al guardar llamada: " + ex.getMessage());
        JOptionPane.showMessageDialog(this,
            "Error al guardar la llamada: " + ex.getMessage(),
            "Error al Guardar",
            JOptionPane.ERROR_MESSAGE);
    }



        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBoxCodigoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCodigoClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCodigoClienteActionPerformed

    private void jTextFieldDuracion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDuracion1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDuracion1ActionPerformed

    private void jButtonCronoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCronoActionPerformed

    crono cr = new crono(jTextFieldDuracion1);
    cr.setVisible(true);
        
    }//GEN-LAST:event_jButtonCronoActionPerformed

    private void jComboBoxCodigoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCodigoEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCodigoEmpleadoActionPerformed

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
            java.util.logging.Logger.getLogger(Llamadas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Llamadas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Llamadas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Llamadas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Llamadas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonCrono;
    private javax.swing.JComboBox<Cliente> jComboBoxCodigoCliente;
    private javax.swing.JComboBox<Empleado> jComboBoxCodigoEmpleado;
    private javax.swing.JComboBox<String> jComboBoxTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelNombreCliente;
    private javax.swing.JLabel jLabelNombreEmpleado;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreanota;
    private javax.swing.JTextField jTextFieldDuracion1;
    // End of variables declaration//GEN-END:variables
}
