package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConexionBD {

    private static ConexionBD instancia;
    private Connection cx;
    private final String bd = "crm";
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String user = "root";
    private final String password = "root";
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final int MAX_INTENTOS = 3;

    private ConexionBD() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, "Driver no encontrado", ex);
            JOptionPane.showMessageDialog(null, "Error: Driver de MySQL no encontrado");
        }
    }

    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    // MÉTODO CONECTAR CORREGIDO
    public Connection conectar() {
        try {
            // Si la conexión es nula o está cerrada, crear una nueva
            if (cx == null || cx.isClosed()) {
                int intentos = 0;
                while (intentos < MAX_INTENTOS) {
                    try {
                        cx = DriverManager.getConnection(url + bd + "?useSSL=false&serverTimezone=UTC", user, password);
                        System.out.println("✅ CONEXIÓN EXITOSA");
                        break;
                    } catch (SQLException ex) {
                        intentos++;
                        if (intentos == MAX_INTENTOS) {
                            JOptionPane.showMessageDialog(null, "No se pudo conectar después de " + MAX_INTENTOS + " intentos");
                            return null;
                        }
                        try { 
                            Thread.sleep(1000); 
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
            return cx;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error verificando estado de conexión: " + ex.getMessage());
            return null;
        }
    }

    // MÉTODO DESCONECTAR MEJORADO
    public void desconectar() {
        try {
            if (cx != null && !cx.isClosed()) {
                cx.close();
                System.out.println("✅ Conexión cerrada correctamente");
            }
        } catch (SQLException ex) {
            System.err.println("Error al cerrar conexión: " + ex.getMessage());
        }
    }

    // VERIFICAR ESTADO DE LA CONEXIÓN
    public boolean estaConectado() {
        try {
            return cx != null && !cx.isClosed();
        } catch (SQLException ex) {
            return false;
        }
    }
}