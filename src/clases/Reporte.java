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
import java.util.Arrays;

public class Reporte{
    
    private int codigoCliente;
    private int llamada; 
    private String resolucion;
    private int codigoProductoServicio;
    private String tipo;
    private String nombreCliente;
    private String motivo;
    private String telefono;
    private String nombreProductoServicio;
    private int codigo;
    
    public int getCodigo() {
    return codigo;
}

public void setCodigo(int codigo) {
    this.codigo = codigo;
}

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

    public String getTelefono() {
    return telefono;
    }

    public void setTelefono(String telefono) {
    this.telefono = telefono;
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
    
    public void setCodigoProductoServicio(int codigoProductoServicio) {
    this.codigoProductoServicio = codigoProductoServicio;
}
    
    public void CrearReportes(Reporte reportes) {
            String mensaje = "¿Desea generar este Reporte?\n\n"
        + "Código Llamada: " + reportes.getLlamada() + "\n"
        + "Código Cliente: " + reportes.getCodigoCliente() + "\n"
        + "Tipo: " + reportes.getTipo() + "\n"
        + "Nombre Cliente: " + reportes.getNombreCliente() + "\n"
        + "Teléfono: " + reportes.getTelefono() + "\n"
        + "Código Producto: " + reportes.getCodigoProductoServicio() + "\n"
        + "Nombre Producto: " + reportes.getNombreProductoServicio() + "\n"
        + "Motivo: " + reportes.getMotivo() + "\n"
        + "Resolución: " + reportes.getResolucion() + "\n";

    int opcion = JOptionPane.showConfirmDialog(
            null,
            mensaje,
            "Confirmar creación de Reporte",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (opcion == JOptionPane.YES_OPTION) {
        // CORREGIDO: usa "cliente" que es el nombre real de tu columna
        String sql = "INSERT INTO reporte (llamada, cliente, tipo, productoServicio, motivo, resolucion, nombreCliente, telefono, fecha, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setInt(1, reportes.getLlamada());           // llamada
            ps.setInt(2, reportes.getCodigoCliente());     // cliente
            ps.setString(3, reportes.getTipo());           // tipo
            ps.setInt(4, reportes.getCodigoProductoServicio()); // productoServicio
            ps.setString(5, reportes.getMotivo());         // motivo
            ps.setString(6, reportes.getResolucion());     // resolucion
            ps.setString(7, reportes.getNombreCliente());  // nombreCliente
            ps.setString(8, reportes.getTelefono());       // telefono
            ps.setString(9, "Activo");                     // estado

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "✅ Reporte creado con éxito.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al crear reporte: " + ex.getMessage());
            ex.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(null, "❌ Creación cancelada por el usuario.");
    }
}

    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");

    }
   
 
        public DefaultTableModel TablaReportes() {
            String[] nombresColumnas = {"Código", "Llamada", "Cliente", "Tipo", "Nombre Cliente", "Teléfono", "Producto/Servicio", "Motivo", "Resolución", "Fecha", "Estado"};
    DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);

   
    String sql = "SELECT codigo, llamada, cliente, tipo, productoServicio, motivo, resolucion, fecha, estado, nombreCliente, telefono FROM reporte";

    try (Connection cx = ConexionBD.getInstancia().conectar(); 
         PreparedStatement ps = cx.prepareStatement(sql); 
         ResultSet rs = ps.executeQuery()) {

        System.out.println("🔍 EJECUTANDO CONSULTA: " + sql);

        while (rs.next()) {
            Object[] fila = new Object[11];

            fila[0] = rs.getInt("codigo");
            fila[1] = rs.getInt("llamada");
            fila[2] = rs.getInt("cliente");        // cliente
            fila[3] = rs.getString("tipo");
            fila[4] = rs.getString("nombreCliente");
            fila[5] = rs.getString("telefono");
            fila[6] = rs.getInt("productoServicio"); // productoServicio
            fila[7] = rs.getString("motivo");
            fila[8] = rs.getString("resolucion");
            fila[9] = rs.getString("fecha");
            fila[10] = rs.getString("estado");

            modelo.addRow(fila);
            System.out.println("✅ FILA AGREGADA: " + Arrays.toString(fila));
        }

    } catch (SQLException e) {
        System.err.println("❌ Error al cargar la tabla de reportes: " + e.getMessage());
        e.printStackTrace();
        return new DefaultTableModel(null, nombresColumnas);
    }

    return modelo;
}

        
public boolean eliminarReporte(int codigo) {
    String sql = "DELETE FROM reporte WHERE codigo = ?"; // CORREGIDO: "reporte" no "producto"
    
    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql)) {
        
        ps.setInt(1, codigo);
        int filasAfectadas = ps.executeUpdate();
        
        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, 
                "✅ Reporte eliminado correctamente.",
                "Eliminación Exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, 
                "❌ No se encontró ningún Reporte con ese código.",
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, 
            "❌ Error al eliminar Reporte: " + ex.getMessage(),
            "Error de Base de Datos", 
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
        return false;
        }
    }
    
    public boolean actualizarEstadoReporte(int codigoReporte, String nuevoEstado) {
    String sql = "UPDATE reporte SET estado = ? WHERE codigo = ?";
    
    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql)) {
        
        ps.setString(1, nuevoEstado);
        ps.setInt(2, codigoReporte);
        
        int filasAfectadas = ps.executeUpdate();
        return filasAfectadas > 0;
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al actualizar estado: " + e.getMessage());
        return false;
    }
    
}
    
@Override
public String toString() {
    return codigo + " - " + nombreCliente;
}

 }      
        
        
       
        
  
        
      
        

    
    
    
