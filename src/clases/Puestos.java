package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Puestos {

    private int codigoPuesto;
    private String nombre;
    private int departamento;

    public Puestos() {}

    public Puestos(int codigoPuesto, String nombre, int departamento) {
        this.codigoPuesto = codigoPuesto;
        this.nombre = nombre;
        this.departamento = departamento;
    }

    public int getCodigoPuesto() {
        return codigoPuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDepartamento() {
        return departamento;
    }

    @Override
    public String toString() {
        return nombre;
    }

    // ✅ Cargar puestos según el departamento seleccionado
    public static void comboPuestosPorDepartamento(JComboBox<Puestos> comboBox, int codigoDepartamento) {
        DefaultComboBoxModel<Puestos> model = new DefaultComboBoxModel<>();
        comboBox.removeAllItems();

        String sql = "SELECT codigoPuesto, nombre, departamento FROM puestos WHERE departamento = ?";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setInt(1, codigoDepartamento);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigoPuesto");
                String nombre = rs.getString("nombre");
                int departamento = rs.getInt("departamento");

                Puestos puesto = new Puestos(codigo, nombre, departamento);
                model.addElement(puesto);
            }

            comboBox.setModel(model);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al cargar puestos: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
