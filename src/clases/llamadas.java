package clases;

import clases.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.sql.ResultSet;

import java.util.Date;
import java.time.LocalDateTime;
import javax.swing.table.DefaultTableModel;


public class llamadas {

    private int codigo_cliente;
    private String nombre_cliente;
    private int codigo_empleado;
    private String tipo;
    private String nota;
    
    private int duracion;
    private LocalDateTime fecha_hora;
    
    
    public int getCodigo_cliente() {
        return codigo_cliente;
    }

    /**
     * @param codigo_cliente the codigo_cliente to set
     */
    public void setCodigo_cliente(int codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    /**
     * @return the nombre_cliente
     */
    public String getNombre_cliente() {
        return nombre_cliente;
    }

    /**
     * @param nombre_cliente the nombre_cliente to set
     */
    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    /**
     * @return the codigo_empleado
     */
    public int getCodigo_empleado() {
        return codigo_empleado;
    }

    /**
     * @param codigo_empleado the codigo_empleado to set
     */
    public void setCodigo_empleado(int codigo_empleado) {
        this.codigo_empleado = codigo_empleado;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the nota
     */
    public String getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(String nota) {
        this.nota = nota;
    }
    
    public int getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    
    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    /**
     * @param fecha_hora the fecha_hora to set
     */
    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }
    
    
    //metodo donde se encuentra mi insert 
    public void GuardarLLamada (llamadas llamada) {
    String mensaje = "¿Desea guardar esta llamada con los siguientes datos?\n\n"
            + "Codigo cliente: " + llamada.getCodigo_cliente() + "\n"
            + "Nombre de cliente: " + llamada.getNombre_cliente() + "\n"
            + "Codigo de empleado: " + llamada.getCodigo_empleado() + "\n"
            + "Tipo de llamada: " + llamada.getTipo() + "\n"
            + "Nota de la llamada: " + llamada.getNota() + "\n"
    
            + "Duracion (segundos): " + llamada.getDuracion() + "\n"
            + "Fecha y hora" + llamada.getFecha_hora();
    
    int opcion = JOptionPane.showConfirmDialog(
            null,
            mensaje,
            "Confirmar guardado de llamada",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (opcion == JOptionPane.YES_OPTION) {
        String sql = "INSERT INTO llamada (cliente, empleado, fechaHora, tipo, nota, duracion)"
                   + "VALUES (?, ?, NOW(), ?, ?, ?)";
    
try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setInt(1, llamada.getCodigo_cliente());
            ps.setInt(2, llamada.getCodigo_empleado());
            ps.setString(3, llamada.getTipo());
            ps.setString(4, llamada.getNota());
            ps.setInt(5, llamada.getDuracion());

            ps.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar la llamada: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "Registro cancelado.");
    }
}
    
    //metodo donde se encuentra mi tabla y mi select
public DefaultTableModel TablaLlamadas() {
    
    String[] nombresColumnas = {"No. Llamada", "Fecha/Hora", "Empleado", "Duracion", "Tipo", "Notas", "Venta"};
    DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);

    String sql = "SELECT codigoLlamada, fechaHora, cliente, empleado, duracion, tipo, nota FROM llamada"; 

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Object[] fila = new Object[7];
            
            fila[0] = rs.getInt("codigoLlamada"); 
            fila[1] = rs.getTimestamp("fechaHora"); 
            fila[2] = rs.getInt("empleado"); 
            fila[3] = rs.getInt("duracion");
            fila[4] = rs.getString("tipo");
            fila[5] = rs.getString("nota");
            
            fila[6] = "N/A"; 
            
            modelo.addRow(fila);
        }

    } catch (SQLException e) {
   
        System.err.println("Error al cargar la tabla");
        System.err.println("Mensaje: " + e.getMessage());
        
       
        return new DefaultTableModel(null, nombresColumnas);
    }
    
    return modelo;
}
}
