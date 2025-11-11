package formularios; 

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;


public class crono extends javax.swing.JFrame {

    private Timer mTimer;
    private JTextField campoDuracionLlamadas;
 
    private int horas = 0;
    private int minutos = 0;
    private int segundos = 0;
    private int milisegundos = 0;
    
    public crono(JTextField campo) {
        this.campoDuracionLlamadas = campo;
        initComponents();
        
        mTimer = new Timer(10, (ActionEvent e) -> {
            ActualizarTiempo();
            ActualizarCrono();
        });
    }

    private void ActualizarTiempo() {
        milisegundos++;
        
        if (milisegundos == 100) {
            milisegundos = 0;
            segundos++;
        }

        if (segundos == 60) {
            segundos = 0;
            minutos++;
        }

        if (minutos == 60) {
            minutos = 0;
            horas++;
        }


    }
    
        private void ActualizarCrono() {
        String cronometro = horas + "h:" + minutos + "m:" + segundos + "s";
        
        jLabelcrono.setText(cronometro);
        jLabelmili.setText("." + String.format("%02d", milisegundos));
        
        if (campoDuracionLlamadas != null) {
        campoDuracionLlamadas.setText(cronometro); 
        }
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonIni = new javax.swing.JButton();
        jButtonRei = new javax.swing.JButton();
        jButtonDet = new javax.swing.JButton();
        jLabelcrono = new javax.swing.JLabel();
        jLabelmili = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cronometro");

        jButtonIni.setText("Iniciar");
        jButtonIni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIniActionPerformed(evt);
            }
        });

        jButtonRei.setText("Reinicar");
        jButtonRei.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReiActionPerformed(evt);
            }
        });

        jButtonDet.setText("Detener");
        jButtonDet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDetActionPerformed(evt);
            }
        });

        jLabelcrono.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabelcrono.setText("0h: 0m: 0s");

        jLabelmili.setText(".000");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonIni)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRei))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelcrono)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelmili)))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelcrono, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(jLabelmili))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonIni)
                    .addComponent(jButtonRei)
                    .addComponent(jButtonDet))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDetActionPerformed

        mTimer.stop();
        jButtonIni.setEnabled(true);  

    }//GEN-LAST:event_jButtonDetActionPerformed

    private void jButtonIniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIniActionPerformed

        mTimer.start();
        jButtonIni.setEnabled(false);

    }//GEN-LAST:event_jButtonIniActionPerformed

    private void jButtonReiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReiActionPerformed

        if (mTimer.isRunning()) {
            mTimer.stop();
        }
        
        jButtonIni.setEnabled(true);
        
        horas = 0;
        minutos = 0;
        segundos = 0;
        milisegundos = 0;

        ActualizarCrono();
    }//GEN-LAST:event_jButtonReiActionPerformed

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
            java.util.logging.Logger.getLogger(crono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(crono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(crono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(crono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton jButtonDet;
    private javax.swing.JButton jButtonIni;
    private javax.swing.JButton jButtonRei;
    private javax.swing.JLabel jLabelcrono;
    private javax.swing.JLabel jLabelmili;
    // End of variables declaration//GEN-END:variables
}
