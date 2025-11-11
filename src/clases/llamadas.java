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
    
    //getters y setters
    
    
    public int getCodigo_cliente() {
        return codigo_cliente;
    }


    public void setCodigo_cliente(int codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }


    public String getNombre_cliente() {
        return nombre_cliente;
    }


    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }


    public int getCodigo_empleado() {
        return codigo_empleado;
    }


    public void setCodigo_empleado(int codigo_empleado) {
        this.codigo_empleado = codigo_empleado;
    }

    
    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getNota() {
        return nota;
    }


    public void setNota(String nota) {
        this.nota = nota;
    }
    
    public int getDuracion() {
        return duracion;
    }


    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    
    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }


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
    
        //metodo para mi clase reportes
public static class LlamadaComboItem {
    private int codigoLlamada;
    private int codigoCliente;
    private String nombreCliente;
    private String tipo;
    
    public LlamadaComboItem(int codigoLlamada, int codigoCliente, String nombreCliente, String tipo) {
        this.codigoLlamada = codigoLlamada;
        this.codigoCliente = codigoCliente;
        this.nombreCliente = nombreCliente;
        this.tipo = tipo;
    }
    
    
    public int getCodigoLlamada() { return codigoLlamada; }
    public int getCodigoCliente() { return codigoCliente; }
    public String getNombreCliente() { return nombreCliente; }
    public String getTipo() { return tipo; }
    
    @Override
    public String toString() {
        return String.format("Llamada #%d - %s - %s", codigoLlamada, nombreCliente, tipo);
    }
}
    
    public static DefaultComboBoxModel<String> getModeloLlamadasParaComboBox() {
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
    String sql = "SELECT l.codigoLlamada, l.cliente, l.tipo, c.nombre as nombreCliente " +
                 "FROM llamada l " +
                 "LEFT JOIN cliente c ON l.cliente = c.codigoCliente " +
                 "ORDER BY l.codigoLlamada DESC";

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            String item = "Llamada #" + rs.getInt("codigoLlamada") + " - " + 
                         rs.getString("nombreCliente") + " - " + 
                         rs.getString("tipo");
            model.addElement(item);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "❌ Error al cargar llamadas: " + e.getMessage());
    }
    return model;
}
    
    public static void llenarComboLlamadas(javax.swing.JComboBox<llamadas> comboBox) {
    javax.swing.DefaultComboBoxModel<llamadas> model = new javax.swing.DefaultComboBoxModel<>();
    String sql = "SELECT l.codigoLlamada, l.cliente, l.tipo, c.nombre as nombreCliente " +
                 "FROM llamada l " +
                 "LEFT JOIN cliente c ON l.cliente = c.codigoCliente " +
                 "ORDER BY l.codigoLlamada DESC";

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            llamadas llamada = new llamadas();
            llamada.setCodigo_cliente(rs.getInt("cliente"));
            llamada.setNombre_cliente(rs.getString("nombreCliente"));
            llamada.setTipo(rs.getString("tipo"));
            model.addElement(llamada);
        }
        comboBox.setModel(model);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "❌ Error al cargar llamadas: " + e.getMessage());
    }
}

@Override
public String toString() {
    return String.valueOf(this.getCodigo_cliente()); // Muestra solo: "1", "2", "3"
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
