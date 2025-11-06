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

    public Departamento(int codigoDepartamento, String nombre, String descripcion) {
        this.codigoDepartamento = codigoDepartamento;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return nombre;
    }

    // ✅ Cargar todos los departamentos
    public static void cargarDepartamentos(JComboBox<Departamento> comboBox) {
        DefaultComboBoxModel<Departamento> model = new DefaultComboBoxModel<>();
        comboBox.removeAllItems();

        String sql = "SELECT codigoDepartamento, nombre, descripcion FROM departamentos";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int codigo = rs.getInt("codigoDepartamento");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");

                Departamento depto = new Departamento(codigo, nombre, descripcion);
                model.addElement(depto);
            }

            comboBox.setModel(model);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al cargar departamentos: " + ex.getMessage());
        }
    }
}
