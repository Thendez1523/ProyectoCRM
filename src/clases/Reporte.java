/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import clases.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Reporte{

    private int codigoCliente;
    private int telefono;
    private int codigoProductoServicio;
    private String tipo;
    private String nombreCliente;
    private String nombreProductoServicio;
    private String motivo;
    
    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getCodigoProductoServicio() {
        return codigoProductoServicio;
    }

    public void setCodigoPrductoServicio(int codigoProductoServicio) {
        this.codigoProductoServicio = codigoProductoServicio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreProductoServicio() {
        return nombreProductoServicio;
    }

    public void setNombreProductoServicio(String nombreProductoServicio) {
        this.nombreProductoServicio = nombreProductoServicio;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    public void CrearReportes(Reporte reportes) {
    String mensaje = "¿Desea generar este Reporte?\n\n"
            + "Código del Cliente: " + reportes.getCodigoCliente() + "\n"
            + "Tipo: " + reportes.getTipo() + "\n"
            + "Nombre del Cliente: " + reportes.getNombreCliente() + "\n"
            + "Teléfono: " + reportes.getTelefono() + "\n"
            + "Código del Producto o Servicio: " + reportes.getCodigoProductoServicio() + "\n"
            + "Nombre del Producto o Servicio: " + reportes.getNombreProductoServicio() + "\n"
            + "Motivo del Reporte: " + reportes.getMotivo() + "\n";

    int opcion = JOptionPane.showConfirmDialog(
            null,
            mensaje,
            "Confirmar creacion de Producto",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (opcion == JOptionPane.YES_OPTION) {
        String sql = "INSERT INTO Producto (codigoCliente, tipo, nombreCliente, telefono, codigoProductoServicio, nombreProductoServicio) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setInt(1, reportes.getCodigoCliente());
            ps.setString(2, reportes.getTipo());
            ps.setString(3, reportes.getNombreCliente());
            ps.setInt(4, reportes.getTelefono());
            ps.setInt(5, reportes.getCodigoProductoServicio());
            ps.setString(6, reportes.getNombreProductoServicio());
            ps.setString(7, reportes.getMotivo());
           

            ps.executeUpdate();

            // ✅ Solo este mensaje se muestra
                JOptionPane.showMessageDialog(null, 
                    "✅ Producto creado con éxito.\n",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
           

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al crear producto: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "❌ Creación cancelada por el usuario.");
}
}

    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}