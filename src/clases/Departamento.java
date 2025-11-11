package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Departamento {
    private int codigoDepartamento;
    private String nombre;
    private String descripcion;

    // 🔹 Constructor vacío
    public Departamento() {
    }

    // 🔹 Constructor con parámetros
    public Departamento(int codigoDepartamento, String nombre, String descripcion) {
        this.codigoDepartamento = codigoDepartamento;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // 🔹 Constructor alternativo (útil para insertar)
    public Departamento(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // --- Getters y Setters ---
    public int getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(int codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return getNombre();
    }

    // --- ✅ Método para registrar un departamento (con parámetro) ---
    public void crearDepartamento(Departamento departamento) {
        String mensaje = "¿Desea guardar este departamento con los siguientes datos?\n\n"
                + "Nombre: " + departamento.getNombre() + "\n"
                + "Descripción: " + departamento.getDescripcion();

        int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Confirmar registro de departamento",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            String sql = "INSERT INTO departamentos (nombre, descripcion) VALUES (?, ?)";
            try (Connection cx = ConexionBD.getInstancia().conectar();
                 PreparedStatement ps = cx.prepareStatement(sql)) {

                ps.setString(1, departamento.getNombre());
                ps.setString(2, departamento.getDescripcion());

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "✅ Departamento registrado con éxito.");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "❌ Error al registrar departamento: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "⚠️ Registro cancelado por el usuario.");
        }
    }

    // --- 🔹 Cargar todos los departamentos en ComboBox ---
    public static void cargarDepartamentos(JComboBox<Departamento> comboBox) {
        DefaultComboBoxModel<Departamento> model = new DefaultComboBoxModel<>();
        comboBox.removeAllItems();

        String sql = "SELECT codigoDepartamento, nombre, descripcion FROM departamentos";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Departamento depto = new Departamento(
                    rs.getInt("codigoDepartamento"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                model.addElement(depto);
            }

            comboBox.setModel(model);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al cargar departamentos: " + ex.getMessage());
        }
    }
}
