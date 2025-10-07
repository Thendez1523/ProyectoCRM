package clases;

import clases.ConexionBD;
import clases.ConexionBD;
import clases.ConexionBD;
import clases.ConexionBD;
import clases.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.sql.ResultSet;


public class llamadas {

    private int codigo_cliente;
    private String nombre_cliente;
    private int codigo_empleado;
    private String tipo;
    private String nota;
    
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
    
    
    
    public void GuardarLLamada (llamadas llamada) {
    String mensaje = "¿Desea guardar esta llamada con los siguientes datos?\n\n"
            + "Codigo cliente: " + llamada.getCodigo_cliente() + "\n"
            + "Nombre de cliente: " + llamada.getNombre_cliente() + "\n"
            + "Codigo de empleado: " + llamada.getCodigo_empleado() + "\n"
            + "Tipo de llamada: " + llamada.getTipo() + "\n"
            + "Nota de la llamada: " + llamada.getNota();
    
    int opcion = JOptionPane.showConfirmDialog(
            null,
            mensaje,
            "Confirmar guardado de llamada",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (opcion == JOptionPane.YES_OPTION) {
        String sql = "INSERT INTO llamada (cliente, empleado, tipo, nota)"
                   + "VALUES (?, ?, ?, ?)";
    
try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setInt(1, llamada.getCodigo_cliente());
            ps.setString(2, llamada.getNombre_cliente());
            ps.setInt(3, llamada.getCodigo_empleado());
            ps.setString(4, llamada.getTipo());
            ps.setString(5, llamada.getNota());


            ps.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar la llamada: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "Registro cancelado.");
    }
}
    
}
