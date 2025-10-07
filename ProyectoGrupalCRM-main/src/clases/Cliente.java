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

import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.sql.ResultSet;

/**
 *
 * @author MarcosCano
 */
public class Cliente extends Persona {

    /**
     * @param codigoCliente the codigoCliente to set
     */
    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    /**
     * @return the codigoCliente
     */
    public int getCodigoCliente() {
        return codigoCliente;
    }
    private Date fechaRegistro;
    private String tipoCliente;
    private String nit;
    private int codigoCliente;

    // ✅ Constructor vacío
    public Cliente() {}

    // ✅ Constructor para usar en JComboBox
    public Cliente(int codigoCliente, String nombreCompleto, String telefono, String correo, String direccion) {
        this.setCodigoCliente(codigoCliente);
        this.setNombre(nombreCompleto);
        this.setTelefono(telefono);
        this.setCorreo(correo);
        this.setDireccion(direccion);
    }

    // ✅ Mostrar nombre en JComboBox
    @Override
    public String toString() {
        return getCodigoCliente() + " - " + getNombre();
    }

    // ================= GETTERS Y SETTERS ================= //

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    // ================= MÉTODOS ================= //

    //CREAR CLIENTE
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

        int opcion = JOptionPane.showConfirmDialog(
                null,
                mensaje,
                "Confirmar registro de cliente",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

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

                
                    JOptionPane.showMessageDialog(null,
                        "✅ Cliente registrado con éxito.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "❌ Error al registrar cliente: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "❌ Registro cancelado por el usuario.");
        }
    }

    public void actualizarCliente(Cliente cliente) {
        // Aquí implementas el UPDATE más adelante
    }

    

    // ✅ Método para llenar ComboBox con Clientes
   public static void comboCliente(JComboBox<Cliente> comboBox) {
        DefaultComboBoxModel<Cliente> model = new DefaultComboBoxModel<>();
        String sql = "SELECT codigoCliente, CONCAT(nombre, ' ', apellido) AS nombreCompleto, telefono, correo, direccion "
                   + "FROM cliente ORDER BY nombre ASC";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int codigo = rs.getInt("codigoCliente");
                String nombre = rs.getString("nombreCompleto");
                String telefono = rs.getString("telefono");
                String correo = rs.getString("correo");
                String direccion = rs.getString("direccion");

                Cliente cliente = new Cliente(codigo, nombre, telefono, correo, direccion);
                model.addElement(cliente);
            }

            comboBox.setModel(model);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al mostrar clientes: " + ex.getMessage());
        }
    }

}
