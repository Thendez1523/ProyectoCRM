package clases;

import clases.ConexionBD;
import clases.llamadas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Reporte{
    
    private int codigoCliente;
    private int llamada; 
    private String resolucion;
    private int telefono;
    private int codigoProductoServicio;
    private String tipo;
    private String nombreCliente;
    private String nombreProductoServicio;
    private String motivo;
    

    public int getLlamada() {
        return llamada;
    }

    public void setLlamada(int llamada) {
        this.llamada = llamada;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

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

                //Solo este mensaje se muestra
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
        throw new UnsupportedOperationException("Not supported yet.");

    }
    
      
 
        public DefaultTableModel TablaReportes() {
    String[] nombresColumnas = {"Código del Cliente", "Llamada", "Nombre del Cliente", "Tipo", "Producto o Servicio", "Motivo", "Resolución", "Fecha", "Estado"};
    DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);

    String sql = "SELECT codigo, llamada, diente, tipo, productoServicio, motivo, resolution, fecha, estado FROM reporte";

    try (Connection cx = ConexionBD.getInstancia().conectar(); 
         PreparedStatement ps = cx.prepareStatement(sql); 
         ResultSet rs = ps.executeQuery()) {

        System.out.println("Conexión establecida: " + (cx != null));
        System.out.println("Ejecutando consulta: " + sql);

        while (rs.next()) {
            Object[] fila = new Object[9];

            fila[0] = rs.getInt("codigo");
            fila[1] = rs.getInt("llamada");
            fila[2] = rs.getString("diente");
            fila[3] = rs.getString("tipo");
            fila[4] = rs.getInt("productoServicio");
            fila[5] = rs.getString("motivo");
            fila[6] = rs.getString("resolution");
            fila[7] = rs.getString("fecha");
            fila[8] = rs.getString("estado");

            modelo.addRow(fila);
            System.out.println("Reporte agregado: " + fila[0]); // imprime el código del reporte
        }

    } catch (SQLException e) {
        System.err.println("Error al cargar la tabla de reportes");
        System.err.println("Mensaje: " + e.getMessage());
        e.printStackTrace();
        return new DefaultTableModel(null, nombresColumnas);
    }

    return modelo;
}
  
        
      
        
}