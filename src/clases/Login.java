package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Login {
    private int codigoLogin;
    private int codigoEmpleado;
    private String contraseña;
    private String tipo;

    // ✅ Variable estática para mantener la sesión activa
    private static Login sesionActiva = null;

    // ====== Constructores ======
    public Login() {}
    public Login(int codigoEmpleado, String contraseña) {
        this.codigoEmpleado = codigoEmpleado;
        this.contraseña = contraseña;
    }

    // ====== Getters y Setters ======
    public int getCodigoLogin() { return codigoLogin; }
    public void setCodigoLogin(int codigoLogin) { this.codigoLogin = codigoLogin; }

    public int getCodigoEmpleado() { return codigoEmpleado; }
    public void setCodigoEmpleado(int codigoEmpleado) { this.codigoEmpleado = codigoEmpleado; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    // ==========================================================
    // ✅ Crear usuario
    // ==========================================================
    public boolean crearUsuario() {
        String sql = "INSERT INTO login (codigoEmpleado, contraseña, tipo) VALUES (?, ?, 'Empleado')";
        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setInt(1, this.codigoEmpleado);
            ps.setString(2, this.contraseña);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "❌ Error al crear usuario: " + ex.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ==========================================================
    // ✅ Verificar login y guardar sesión activa
    // ==========================================================
    public static boolean verificarLogin(int codigoEmpleado, String contraseña) {
        String sql = "SELECT * FROM login WHERE codigoEmpleado = ? AND contraseña = ?";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setInt(1, codigoEmpleado);
            ps.setString(2, contraseña);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // 🔹 Crear sesión activa con los datos del empleado
                sesionActiva = new Login();
                sesionActiva.setCodigoEmpleado(codigoEmpleado);
                sesionActiva.setContraseña(contraseña);
                sesionActiva.setTipo(rs.getString("tipo"));
                return true;
            }

            return false;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                    "❌ Error al verificar login: " + ex.getMessage());
            return false;
        }
    }

    // ==========================================================
    // ✅ Métodos para manejar la sesión global
    // ==========================================================
    public static Login getSesionActiva() {
        return sesionActiva;
    }

    public static void cerrarSesion() {
        sesionActiva = null;
    }

    public static boolean haySesionActiva() {
        return sesionActiva != null;
    }
}
