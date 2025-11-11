package formularios;

import clases.Cliente;
import clases.Reporte;
import clases.llamadas;
import clases.ServicioProducto;
import clases.llamadas;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;


public class Reportes extends javax.swing.JFrame {

    public Reportes() {
        initComponents();
        llamadas.llenarComboLlamadas(jComboBoxLlamadas);
         ServicioProducto.llenarComboProductos(jComboBox1);
}
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jComboBoxLlamadas = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        LlamadaComboItem1 = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextFieldCodigoCliente = new javax.swing.JLabel();
        jTextFieldTipo = new javax.swing.JLabel();
        jTextFieldNombreCliente = new javax.swing.JLabel();
        jTextFieldCodigoProducto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Crear Nuevo Reporte");

        jLabel2.setText("Codigo Cliente");

        jLabel3.setText("Codigo Producto/Servicio");

        jLabel4.setText("Tipo");

        jLabel5.setText("Nombre Cliente");

        jLabel6.setText("Telefono");

        jLabel7.setText("Nombre Producto/Servicio");

        jLabel8.setText("Motivo Reporte");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton3.setText("Generar Reporte");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel9.setText("Codigo Llamada");

        jLabel10.setText("Resolucion");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jComboBoxLlamadas.setModel(new javax.swing.DefaultComboBoxModel<llamadas>());
        jComboBoxLlamadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLlamadasActionPerformed(evt);
            }
        });

        jLabel11.setText("Estado");

        LlamadaComboItem1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Solucionado", "Pendiente", "No Solucionado" }));
        LlamadaComboItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LlamadaComboItem1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<ServicioProducto>());
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTextFieldCodigoCliente.setText(".");

        jTextFieldTipo.setText(".");

        jTextFieldNombreCliente.setText(".");

        jTextFieldCodigoProducto.setText(".");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(47, 47, 47)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jTextFieldCodigoProducto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, 154, Short.MAX_VALUE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jComboBoxLlamadas, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextFieldNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jTextFieldTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextFieldCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(174, 174, 174)
                                .addComponent(LlamadaComboItem1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBoxLlamadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldCodigoCliente))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldTipo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldNombreCliente))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6))
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldCodigoProducto, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(LlamadaComboItem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxLlamadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxLlamadasActionPerformed
    llamadas llamadaSeleccionada = (llamadas) jComboBoxLlamadas.getSelectedItem();
    if (llamadaSeleccionada == null) return;
    
    // PONER LOS DATOS EN LOS CAMPOS:
    jTextFieldCodigoCliente.setText(String.valueOf(llamadaSeleccionada.getCodigo_cliente()));
    jTextFieldTipo.setText(llamadaSeleccionada.getTipo());
    jTextFieldNombreCliente.setText(llamadaSeleccionada.getNombre_cliente());


    }//GEN-LAST:event_jComboBoxLlamadasActionPerformed

    private void LlamadaComboItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LlamadaComboItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LlamadaComboItem1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
            ServicioProducto productoSeleccionado = (ServicioProducto) jComboBox1.getSelectedItem();
    if (productoSeleccionado == null) return;
    
    // AUTOCOMPLETAR LOS CAMPOS:
    jTextFieldCodigoProducto.setText(String.valueOf(productoSeleccionado.getCodigoProducto()));
    jTextFieldCodigoProducto.setText(productoSeleccionado.getNombreProducto());
    
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    try {
        // Validar campos
        if (jComboBoxLlamadas.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una llamada", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (jComboBox1.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto/servicio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Obtener datos
        llamadas llamadaSeleccionada = (llamadas) jComboBoxLlamadas.getSelectedItem();
        ServicioProducto productoServicioSeleccionado = (ServicioProducto) jComboBox1.getSelectedItem();
        
        // CREAR REPORTE CON TODOS LOS CAMPOS
        Reporte nuevoReporte = new Reporte();
        
        nuevoReporte.setLlamada(llamadaSeleccionada.getCodigo_cliente());        
        nuevoReporte.setCodigoCliente(llamadaSeleccionada.getCodigo_cliente());  
        nuevoReporte.setTipo(llamadaSeleccionada.getTipo());                     
        nuevoReporte.setNombreCliente(llamadaSeleccionada.getNombre_cliente());  
        nuevoReporte.setTelefono(jTextField4.getText().trim());                 
        nuevoReporte.setCodigoProductoServicio(productoServicioSeleccionado.getCodigoProducto()); 
        nuevoReporte.setNombreProductoServicio(productoServicioSeleccionado.getNombreProducto()); 
        nuevoReporte.setMotivo(jTextArea1.getText().trim());                    
        nuevoReporte.setResolucion(jTextArea2.getText().trim());               
        
        // Guardar
        nuevoReporte.CrearReportes(nuevoReporte);
        
        // Limpiar
        limpiarFormulario();
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al generar reporte: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void limpiarFormulario() {
    jComboBoxLlamadas.setSelectedIndex(-1);
    jComboBox1.setSelectedIndex(-1);
    jTextField4.setText("");
    jTextArea1.setText("");
    jTextArea2.setText("");
    LlamadaComboItem1.setSelectedIndex(0);
    jTextFieldCodigoCliente.setText(".");
    jTextFieldTipo.setText(".");
    jTextFieldNombreCliente.setText(".");
    jTextFieldCodigoProducto.setText(".");
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reportes().setVisible(true);
            }
        });
        
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> LlamadaComboItem1;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<ServicioProducto> jComboBox1;
    private javax.swing.JComboBox<llamadas> jComboBoxLlamadas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel jTextFieldCodigoCliente;
    private javax.swing.JLabel jTextFieldCodigoProducto;
    private javax.swing.JLabel jTextFieldNombreCliente;
    private javax.swing.JLabel jTextFieldTipo;
    // End of variables declaration//GEN-END:variables
}
