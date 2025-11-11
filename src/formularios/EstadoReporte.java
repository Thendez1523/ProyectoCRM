package formularios;

import clases.ConexionBD;
import clases.Reporte;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.DefaultComboBoxModel; 

public class EstadoReporte extends javax.swing.JFrame {

    public EstadoReporte() {
        initComponents(); 
        cargarComboReportes(); 
        jComboBoxReporte.addActionListener(e -> actualizarCampos());
        
        }
    
    private void cargarComboReportes() {
    DefaultComboBoxModel<Reporte> model = new DefaultComboBoxModel<>();
    
    String sql = "SELECT codigo, cliente, nombreCliente FROM reporte";
    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            Reporte r = new Reporte();
            r.setCodigo(rs.getInt("codigo"));
            r.setCodigoCliente(rs.getInt("cliente"));
            r.setNombreCliente(rs.getString("nombreCliente"));
            model.addElement(r);
        }
        jComboBoxReporte.setModel(model);
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar reportes");
    }
    }
    
    private void actualizarCampos() {
    Reporte reporte = (Reporte) jComboBoxReporte.getSelectedItem();
    if (reporte != null) {
        jLabel6.setText(String.valueOf(reporte.getCodigoCliente()));
        jLabel7.setText(reporte.getNombreCliente());
    }
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxReporte = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Estado del Reporte");

        jLabel1.setText("Estado del Reporte");

        jLabel2.setText("Codigo Reporte");

        jLabel3.setText("Codigo Cliente");

        jLabel4.setText("Nombre Cliente");

        jLabel5.setText("Estado");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Solucionado", "Pendiente", "No Solucionado" }));

        jButton1.setText("Guardar Cambios");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setText(".");

        jLabel7.setText(".");

        jComboBoxReporte.setModel(new javax.swing.DefaultComboBoxModel<Reporte>());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jComboBoxReporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(143, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxReporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1)
                    .addComponent(jLabel5))
                .addGap(44, 44, 44)
                .addComponent(jButton1)
                .addGap(72, 72, 72))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Reporte reporte = (Reporte) jComboBoxReporte.getSelectedItem();
        String nuevoEstado = (String) jComboBox1.getSelectedItem();
    
    if (reporte != null) {
        Reporte r = new Reporte();
        boolean exito = r.actualizarEstadoReporte(reporte.getCodigo(), nuevoEstado);
        
        if (exito) {
            JOptionPane.showMessageDialog(this, "Estado actualizado correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar estado");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione un reporte");
    }
    }//GEN-LAST:event_jButton1ActionPerformed

   public static void main(String args[]) {
       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EstadoReporte().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<Reporte> jComboBoxReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
