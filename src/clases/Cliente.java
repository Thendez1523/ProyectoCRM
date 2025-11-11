package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Cliente extends Persona {

    private Date fechaRegistro;
    private String tipoCliente;
    private String nit;
    private int codigoCliente;

    // ================= CONSTRUCTORES =================
    public Cliente() {}

    public Cliente(int codigoCliente, String nombreCompleto, String telefono, String correo, String direccion) {
        this.codigoCliente = codigoCliente;
        this.setNombre(nombreCompleto);
        this.setTelefono(telefono);
        this.setCorreo(correo);
        this.setDireccion(direccion);
    }

    // ================= GETTERS Y SETTERS =================
    public int getCodigoCliente() { return codigoCliente; }
    public void setCodigoCliente(int codigoCliente) { this.codigoCliente = codigoCliente; }
    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public String getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(String tipoCliente) { this.tipoCliente = tipoCliente; }
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    @Override
    public String toString() {
        return codigoCliente + " - " + getNombre();
    }

    // ================= MÉTODOS =================

    // ✅ Crear cliente
    public void CrearCliente(Cliente cliente) {
        String mensaje = "¿Desea guardar este cliente con los siguientes datos?\n\n"
                + "Nombre: " + cliente.getNombre() + "\n"
                + "Apellido: " + cliente.getApellido() + "\n"
                + "NIT: " + cliente.getNit() + "\n"
                + "DPI: " + cliente.getDpi() + "\n"
                + "Correo: " + cliente.getCorreo() + "\n"
                + "Teléfono: " + cliente.getTelefono() + "\n"
                + "Dirección: " + cliente.getDireccion() + "\n"
                + "Fecha Nacimiento: " + cliente.getFechaNac() + "\n"
                + "Edad: " + cliente.getEdad() + "\n"
                + "Tipo Cliente: " + cliente.getTipoCliente() + "\n"
                + "Fecha Registro: " + cliente.getFechaRegistro();

        int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Confirmar registro de cliente",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            String sql = "INSERT INTO cliente (nombre, apellido, nit, dpi, correo, telefono, direccion, fechaNacimiento, edad, tipoCliente, fechaRegistro) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection cx = ConexionBD.getInstancia().conectar();
                 PreparedStatement ps = cx.prepareStatement(sql)) {

                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getApellido());
                ps.setString(3, cliente.getNit());
                ps.setString(4, cliente.getDpi());
                ps.setString(5, cliente.getCorreo());
                ps.setString(6, cliente.getTelefono());
                ps.setString(7, cliente.getDireccion());
                ps.setDate(8, new java.sql.Date(cliente.getFechaNac().getTime()));
                ps.setInt(9, cliente.getEdad());
                ps.setString(10, cliente.getTipoCliente());
                ps.setDate(11, new java.sql.Date(cliente.getFechaRegistro().getTime()));

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "✅ Cliente registrado con éxito.");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "❌ Error al registrar cliente: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "❌ Registro cancelado por el usuario.");
        }
    }

    // ✅ Llenar ComboBox con clientes (código + nombre)
    public static void comboCliente(JComboBox<Cliente> comboBox) {
        DefaultComboBoxModel<Cliente> model = new DefaultComboBoxModel<>();
        String sql = "SELECT codigoCliente, CONCAT(nombre, ' ', apellido) AS nombreCompleto, telefono, correo, direccion FROM cliente ORDER BY nombre ASC";

        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cx = ConexionBD.getInstancia().conectar();
            ps = cx.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getInt("codigoCliente"),
                        rs.getString("nombreCompleto"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("direccion")
                );
                model.addElement(c);
            }
            comboBox.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al llenar combo de clientes: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (cx != null) cx.close();
            } catch (SQLException e) {
                System.out.println("⚠ Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    // ✅ Actualizar cliente
    public void actualizarCliente(Cliente cliente) {
        String mensaje = "¿Desea actualizar este cliente con los siguientes datos?\n\n"
                + "Código: " + cliente.getCodigoCliente() + "\n"
                + "Nombre: " + cliente.getNombre() + "\n"
                + "Apellido: " + cliente.getApellido() + "\n"
                + "NIT: " + cliente.getNit() + "\n"
                + "Correo: " + cliente.getCorreo() + "\n"
                + "Teléfono: " + cliente.getTelefono() + "\n"
                + "Dirección: " + cliente.getDireccion() + "\n"
                + "Tipo Cliente: " + cliente.getTipoCliente();

        int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Confirmar actualización de cliente",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            String sql = "UPDATE cliente SET nombre = ?, apellido = ?, nit = ?, correo = ?, telefono = ?, direccion = ?, tipoCliente = ? WHERE codigoCliente = ?";
            try (Connection cx = ConexionBD.getInstancia().conectar();
                 PreparedStatement ps = cx.prepareStatement(sql)) {

                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getApellido());
                ps.setString(3, cliente.getNit());
                ps.setString(4, cliente.getCorreo());
                ps.setString(5, cliente.getTelefono());
                ps.setString(6, cliente.getDireccion());
                ps.setString(7, cliente.getTipoCliente());
                ps.setInt(8, cliente.getCodigoCliente());
                int filas = ps.executeUpdate();

                if (filas > 0) {
                    JOptionPane.showMessageDialog(null, "✅ Cliente actualizado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "⚠ No se encontró el cliente a actualizar.");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "❌ Error al actualizar cliente: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "❌ Actualización cancelada.");
        }
    }

    // ✅ Mostrar clientes en JTable
    public void mostrarCliente(JTable tabla) {
        String[] columnas = {"nit", "codigoCliente", "nombre", "apellido", "direccion", "telefono", "correo", "tipoCliente"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        String sql = "SELECT nit, codigoCliente, nombre, apellido, direccion, telefono, correo, tipoCliente FROM cliente";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("nit"),
                    rs.getInt("codigoCliente"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("tipoCliente")
                });
            }

            tabla.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al cargar clientes: " + ex.getMessage());
        }
    }
    
    // ================== ELIMINAR CLIENTE ================== //
public void eliminarCliente(int codigoCliente) {

    int opcion = JOptionPane.showConfirmDialog(
            null,
            "¿Seguro que desea eliminar este cliente?\n\nCódigo: " + codigoCliente,
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
    );

    if (opcion == JOptionPane.YES_OPTION) {
        String sql = "DELETE FROM cliente WHERE codigoCliente = ?";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setInt(1, codigoCliente);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null,
                        "✅ Cliente eliminado correctamente.",
                        "Eliminación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "⚠ No se encontró el cliente a eliminar.",
                        "Sin cambios",
                        JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "❌ Error al eliminar cliente: " + ex.getMessage(),
                    "Error SQL",
                    JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(null, "❌ Eliminación cancelada.");
    }
}

}
