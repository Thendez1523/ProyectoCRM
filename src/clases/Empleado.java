package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Time;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Empleado extends Persona {

    private int codigoEmpleado;
    private int departamento;
    private int puesto;
    private double salario;
    private Time horaEntrada;
    private Time horaSalida;

    // ======== Getters y Setters ========
    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public int getPuesto() {
        return puesto;
    }

    public void setPuesto(int puesto) {
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

    // ======== Constructor ========
    public Empleado() {}

    // ======== Crear empleado ========
    public void CrearEmpleado(Empleado empleado) {
        String sql = "INSERT INTO empleados (nombre, apellido, dpi, correo, telefono, direccion, "
                   + "fechaNacimiento, departamento, puesto, salario, horaIngreso, horaEgreso, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApellido());
            ps.setString(3, empleado.getDpi());
            ps.setString(4, empleado.getCorreo());
            ps.setString(5, empleado.getTelefono());
            ps.setString(6, empleado.getDireccion());
            ps.setDate(7, new java.sql.Date(empleado.getFechaNac().getTime()));
            ps.setInt(8, empleado.getDepartamento());
            ps.setInt(9, empleado.getPuesto());
            ps.setDouble(10, empleado.getSalario());
            ps.setTime(11, empleado.getHoraEntrada());
            ps.setTime(12, empleado.getHoraSalida());
            ps.setString(13, "Activo");

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "✅ Empleado registrado con éxito.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al registrar empleado: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // ======== Mostrar empleados en JTable ========
   

    // ======== Mostrar nombre completo en ComboBox ========
    @Override
    public String toString() {
        return codigoEmpleado + " - " + getNombre();
    }

    // ======== Llenar ComboBox con empleados ========
    public static void comboEmpleados(JComboBox<Empleado> comboBox) {
        DefaultComboBoxModel<Empleado> modelo = new DefaultComboBoxModel<>();
        comboBox.removeAllItems();

        String sql = "SELECT codigoEmpleado, nombre, apellido FROM empleados ORDER BY nombre ASC";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setCodigoEmpleado(rs.getInt("codigoEmpleado"));
                emp.setNombre(rs.getString("nombre"));
                emp.setApellido(rs.getString("apellido"));
                modelo.addElement(emp);
            }

            comboBox.setModel(modelo);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al cargar empleados: " + ex.getMessage());
        }
    }

    // ======== Obtener un empleado por ID ========
    public static Empleado obtenerEmpleadoPorId(int codigoEmpleado) {
    Empleado e = null;
    String sql = "SELECT codigoEmpleado, nombre, apellido, dpi, correo, telefono, direccion, " +
                 "fechaNacimiento, departamento, puesto, salario, horaIngreso, horaEgreso, estado " +
                 "FROM empleados WHERE codigoEmpleado = ?";

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql)) {

        ps.setInt(1, codigoEmpleado);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            e = new Empleado();
            e.setCodigoEmpleado(rs.getInt("codigoEmpleado"));
            e.setNombre(rs.getString("nombre"));
            e.setApellido(rs.getString("apellido"));
            e.setDpi(rs.getString("dpi"));
            e.setCorreo(rs.getString("correo"));
            e.setTelefono(rs.getString("telefono"));
            e.setDireccion(rs.getString("direccion"));

            // Puesto y Departamento
            e.setDepartamento(rs.getInt("departamento"));
            e.setPuesto(rs.getInt("puesto"));

            // ✅ Datos numéricos
            e.setSalario(rs.getDouble("salario"));

            // ✅ Manejo seguro de las horas (evita errores de tipo)
            java.sql.Time entrada = rs.getTime("horaIngreso");
            java.sql.Time salida = rs.getTime("horaEgreso");

            e.setHoraEntrada(entrada != null ? entrada : java.sql.Time.valueOf("00:00:00"));
            e.setHoraSalida(salida != null ? salida : java.sql.Time.valueOf("00:00:00"));
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "❌ Error al obtener empleado: " + ex.getMessage());
        ex.printStackTrace();
    }

    return e;
}

    // ======== Actualizar empleado ========
    public void actualizarEmpleado(Empleado e) {
        String sql = "UPDATE empleados SET nombre=?, apellido=?, dpi=?, correo=?, telefono=?, direccion=?, "
                   + "departamento=?, puesto=?, salario=?, horaIngreso=?, horaEgreso=? "
                   + "WHERE codigoEmpleado=?";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellido());
            ps.setString(3, e.getDpi());
            ps.setString(4, e.getCorreo());
            ps.setString(5, e.getTelefono());
            ps.setString(6, e.getDireccion());
            ps.setInt(7, e.getDepartamento());
            ps.setInt(8, e.getPuesto());
            ps.setDouble(9, e.getSalario());
            ps.setTime(10, e.getHoraEntrada());
            ps.setTime(11, e.getHoraSalida());
            ps.setInt(12, e.getCodigoEmpleado());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "✅ Empleado actualizado correctamente.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al actualizar: " + ex.getMessage());
        }
    }
    
   public static void registrarDespido(int codigoEmpleado, String motivo) {
    String sqlEmpleado = "UPDATE empleados SET estado = 'Despedido' WHERE codigoEmpleado = ?";
    String sqlDespido = "INSERT INTO despidos (codigoEmpleado, motivo, fechaDespido) VALUES (?, ?, NOW())";

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps1 = cx.prepareStatement(sqlEmpleado);
         PreparedStatement ps2 = cx.prepareStatement(sqlDespido)) {

        // 🔹 Actualizar estado del empleado
        ps1.setInt(1, codigoEmpleado);
        ps1.executeUpdate();

        // 🔹 Registrar motivo del despido
        ps2.setInt(1, codigoEmpleado);
        ps2.setString(2, motivo);
        ps2.executeUpdate();

        JOptionPane.showMessageDialog(null, "✅ Empleado despedido correctamente.");

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "❌ Error al despedir empleado: " + ex.getMessage());
        ex.printStackTrace();
    }
}
   
   //MOSTRAR EMPLEADOS ACTIVOS
    public DefaultTableModel TablaEmpleados() {
    // 🧱 Columnas de la tabla
    String[] columnas = {
        "ID", "Nombre", "Apellido", "DPI", "Fecha Nacimiento", "Correo",
        "Teléfono", "Dirección", "Departamento", "Puesto", "Salario",
        "Hora Ingreso", "Hora Egreso", "Estado"
    };

    DefaultTableModel modelo = new DefaultTableModel(null, columnas);

    // 🔹 Consulta con JOIN para mostrar nombres reales
  String sql = "SELECT e.codigoEmpleado, e.nombre, e.apellido, e.dpi, e.fechaNacimiento, "
           + "e.correo, e.telefono, e.direccion, "
           + "d.nombre AS departamento, p.nombre AS puesto, "
           + "e.salario, e.horaIngreso, e.horaEgreso, e.estado "
           + "FROM empleados e "
           + "LEFT JOIN departamentos d ON e.departamento = d.codigoDepartamento "
           + "LEFT JOIN puestos p ON e.puesto = p.codigoPuesto "
           + "WHERE e.estado != 'Despedido'";

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Object[] fila = new Object[14];

            fila[0] = rs.getInt("codigoEmpleado");
            fila[1] = rs.getString("nombre");
            fila[2] = rs.getString("apellido");
            fila[3] = rs.getString("dpi");
            String fechaStr = rs.getString("fechaNacimiento");
java.sql.Date fechaNacimiento = null;

try {
    if (fechaStr != null && !fechaStr.isEmpty()) {
        java.util.Date parsed = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
        fechaNacimiento = new java.sql.Date(parsed.getTime());
    }
} catch (Exception ex) {
    System.err.println("⚠️ No se pudo convertir la fecha: " + fechaStr);
}

fila[4] = fechaNacimiento;
            fila[5] = rs.getString("correo");
            fila[6] = rs.getString("telefono");
            fila[7] = rs.getString("direccion");
            fila[8] = rs.getString("departamento") != null ? rs.getString("departamento") : "Sin asignar";
            fila[9] = rs.getString("puesto") != null ? rs.getString("puesto") : "Sin asignar";
            fila[10] = rs.getDouble("salario");
            fila[11] = rs.getTime("horaIngreso");
            fila[12] = rs.getTime("horaEgreso");
            fila[13] = rs.getString("estado");

            modelo.addRow(fila);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "❌ Error al mostrar empleados: " + e.getMessage());
        e.printStackTrace();
    }

    return modelo;
}
}
