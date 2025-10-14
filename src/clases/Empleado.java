/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;
import clases.ConexionBD;
import clases.ConexionBD;
import clases.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pucho-PC
 */
public class Empleado extends Persona {
    
    private double salario;
    private String departamento;
    private String puesto;
    private String horaing;
    private String horaeg;
    private String estado;
    

    /**
     * @return the salario
     */
    public double getSalario() {
        return salario;
    }

    /**
     * @param salario the salario to set
     */
    public void setSalario(double salario) {
        this.salario = salario;
    }

    /**
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the puesto
     */
    public String getPuesto() {
        return puesto;
    }

    /**
     * @param puesto the puesto to set
     */
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    /**
     * @return the horaing
     */
    public String getHoraing() {
        return horaing;
    }

    /**
     * @param horaing the horaing to set
     */
    public void setHoraing(String horaing) {
        this.horaing = horaing;
    }

    /**
     * @return the horaeg
     */
    public String getHoraeg() {
        return horaeg;
    }

    /**
     * @param horaeg the horaeg to set
     */
    public void setHoraeg(String horaeg) {
        this.horaeg = horaeg;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
  public void CrearEmpleado (Empleado empleado) 
  {
      String mensaje = "¿Desea guardar este cliente con los siguientes datos?\n\n"
            + "Nombre: " + empleado.getNombre() + "\n"
            + "Apellido: " + empleado.getApellido() + "\n"
            + "DPI: " + empleado.getDpi() + "\n"
            + "Correo: " + empleado.getCorreo() + "\n"
            + "Teléfono: " + empleado.getTelefono() + "\n"
            + "Dirección: " + empleado.getDireccion() + "\n"
            + "Fecha Nacimiento: " + empleado.getFechaNac() + "\n"
            + "Edad: " + empleado.getEdad() + "\n"
            + "Salario: " + empleado.getSalario() + "\n"
            + "Departamento: " + empleado.getDepartamento() + "\n"
            + "Hora de Entrada: " + empleado.getHoraing() + "\n"
            + "Hora de Egreso: " + empleado.getHoraeg() + "\n"
            + "Estado: " + empleado.getEstado() + "\n"
            + "Puesto: " + empleado.getPuesto() + "\n"
            + "Fecha Registro: " + empleado.getFechaRegistro();

    int opcion = JOptionPane.showConfirmDialog(
            null,
            mensaje,
            "Confirmar registro de cliente",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (opcion == JOptionPane.YES_OPTION) {
        String sql = "INSERT INTO empleados (nombre, apellido, dpi, correo, telefono, direccion, fechaNacimiento, edad, salario, departamento, horaentrada, horasalida, estado, puesto, fechaRegistro) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());  //prepared statment 
            ps.setString(2, empleado.getApellido());
            ps.setString(3, empleado.getDpi());
            ps.setString(4, empleado.getCorreo());
            ps.setString(5, empleado.getTelefono());
            ps.setString(6, empleado.getDireccion());
            ps.setDate(7, new java.sql.Date(empleado.getFechaNac().getTime()));
            ps.setInt(8, empleado.getEdad());
            ps.setDouble(9, empleado.getSalario());
            ps.setString(10, empleado.getDepartamento());
            ps.setString(11, empleado.getHoraing());
            ps.setString(12, empleado.getHoraeg());
            ps.setString(13, empleado.getEstado());
            ps.setString(14, empleado.getPuesto());
            ps.setDate(15, new java.sql.Date(empleado.getFechaRegistro().getTime()));

            ps.executeUpdate();
            int codigoEmpleado = 0;

            // ✅ Solo este mensaje se muestra
            if (codigoEmpleado != -1) {
                JOptionPane.showMessageDialog(null, 
                    "✅ Cliente registrado con éxito.\nCódigo Cliente: " + codigoEmpleado,
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al registrar Empleado: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "❌ Registro cancelado por el usuario.");
    }
  }  
  
  public void mostrarEmpleados(JTable tabla) {
    String[] columnas = {
        "Código", "Nombre", "Apellido", "DPI", "E-mail", 
        "Teléfono", "Dirección", "Fecha de Nacimiento", 
        "Edad", "Salario", "Departamento", "Hora de Ingreso", 
        "Hora de Egreso", "Estado", "Puesto", "Fecha de Registro"
    };

    DefaultTableModel modelo = new DefaultTableModel(null, columnas);

    String sql = "SELECT codigoEmpleado, nombre, apellido, dpi, correo, telefono, direccion, fechaNacimiento, edad, salario, departamento, horaentrada, horasalida, estado, puesto, fechaRegistro FROM empleados";

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql);
         var rs = ps.executeQuery()) {

        while (rs.next()) {
            Object[] fila = new Object[columnas.length];
            fila[0] = rs.getInt("codigoEmpleado");
            fila[1] = rs.getString("nombre");
            fila[2] = rs.getString("apellido");
            fila[3] = rs.getString("dpi");
            fila[4] = rs.getString("correo");
            fila[5] = rs.getString("telefono");
            fila[6] = rs.getString("direccion");
            fila[7] = rs.getDate("fechaNacimiento");
            fila[8] = rs.getInt("edad");
            fila[9] = rs.getDouble("salario");
            fila[10] = rs.getString("departamento");
            fila[11] = rs.getString("horaentrada");
            fila[12] = rs.getString("horasalida");
            fila[13] = rs.getString("estado");
            fila[14] = rs.getString("puesto");
            fila[15] = rs.getDate("fechaRegistro");
            modelo.addRow(fila);
        }

        tabla.setModel(modelo);

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "❌ Error al mostrar empleados: " + ex.getMessage());
    }
}
}

