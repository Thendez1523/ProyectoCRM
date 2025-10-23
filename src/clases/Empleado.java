package clases;

import clases.ConexionBD;
import clases.ConexionBD;
import clases.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import javax.swing.JOptionPane;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class Empleado extends Persona {
    private String departamento;
    private String puesto;
    private double salario;
    private Time horaEntrada;
    private Time horaSalida;


    public Empleado() {}


    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Time getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Time horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Time getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }


    public void CrearEmpleado(Empleado empleado) {
        String sql = "INSERT INTO empleados (nombre, apellido, DPI, correo, telefono, direccion, "
                   + "fechaNacimiento, departamento, puesto, salario, HoraIngreso, HoraEgreso, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {


            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApellido());
            ps.setInt(3, Integer.parseInt(empleado.getDpi()));
            ps.setString(4, empleado.getCorreo());
            ps.setInt(5, Integer.parseInt(empleado.getTelefono()));
            ps.setString(6, empleado.getDireccion());
            ps.setDate(7, new java.sql.Date(empleado.getFechaNac().getTime()));
            
            ps.setString(8, empleado.getDepartamento());
            ps.setString(9, empleado.getPuesto());
            ps.setDouble(10, empleado.getSalario());
            ps.setTime(11, empleado.getHoraEntrada());
            ps.setTime(12, empleado.getHoraSalida());
            ps.setBoolean(13, true);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null,
                "Empleado registrado con éxito.",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar empleado: " + ex.getMessage());
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error: valores invalidos");
        }
    }
    
public DefaultTableModel TablaEmpleados() {
    
    String[] nombresColumnas = {"ID", "Nombre", "Apellido", "DPI", "Fecha de Nacimiento", "E-mail", "Teléfono", "Dirección", "Puesto", "Departamento", "Salario", "Horario de Ingreso", "Hora de Egreso", "Estado"};
    DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);

    String sql = "SELECT codigoEmpleado, nombre, apellido, DPI, fechaNacimiento, correo, telefono, direccion, puesto, departamento, salario, HoraIngreso, HoraEgreso, estado FROM empleados"; 

    try (Connection cx = ConexionBD.getInstancia().conectar();  
         PreparedStatement ps = cx.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) { 

        while (rs.next()) {
            Object[] fila = new Object[14]; 
            
            fila[0] = rs.getInt("codigoEmpleado"); 
            fila[1] = rs.getString("nombre"); 
            fila[2] = rs.getString("apellido"); 
            fila[3] = rs.getInt("DPI");
            fila[4] = rs.getDate("fechaNacimiento");
            fila[5] = rs.getString("correo");
            fila[6] = rs.getInt("telefono");
            fila[7] = rs.getString("direccion");
            fila[8] = rs.getString("puesto");
            fila[9] = rs.getString("departamento");
            fila[10] = rs.getDouble("salario");
            fila[11] = rs.getTime("HoraIngreso");
            fila[12] = rs.getTime("HoraEgreso");
            fila[13] = rs.getBoolean("estado") ? "Activo" : "Inactivo";
            
            modelo.addRow(fila);
        }

    } catch (SQLException e) {
        System.err.println("Error al cargar la tabla de empleados");
        System.err.println("Mensaje: " + e.getMessage());
        e.printStackTrace();
        return new DefaultTableModel(null, nombresColumnas);
    }
    
    return modelo;
}
    
}