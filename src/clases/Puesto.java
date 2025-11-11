package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Puesto {

    private int codigoPuesto;
    private String nombre;
    private Departamento departamento; // ✅ Se cambia de int a objeto Departamento

    public Puesto() {}

    public Puesto(int codigoPuesto, String nombre, Departamento departamento) {
        this.codigoPuesto = codigoPuesto;
        this.nombre = nombre;
        this.departamento = departamento;
    }

    // ✅ Getters y Setters
    public int getCodigoPuesto() {
        return codigoPuesto;
    }

    public void setCodigoPuesto(int codigoPuesto) {
        this.codigoPuesto = codigoPuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return nombre; // ✅ Para que se muestre el nombre en el JComboBox
    }

    // ✅ Crear un nuevo puesto en la BD
    public void crearPuesto(Puesto puesto) {
        if (puesto.getDepartamento() == null) {
            JOptionPane.showMessageDialog(null, "⚠️ Debe seleccionar un departamento antes de guardar el puesto.");
            return;
        }

        String mensaje = "¿Desea guardar este puesto con los siguientes datos?\n\n"
                + "Nombre: " + puesto.getNombre() + "\n"
                + "Departamento: " + puesto.getDepartamento().getNombre();

        int opcion = JOptionPane.showConfirmDialog(null, mensaje,
                "Confirmar registro de puesto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            String sql = "INSERT INTO puestos (nombre, codigoDepartamento) VALUES (?, ?)";

            try (Connection cx = ConexionBD.getInstancia().conectar();
                 PreparedStatement ps = cx.prepareStatement(sql)) {

                ps.setString(1, puesto.getNombre());
                ps.setInt(2, puesto.getDepartamento().getCodigoDepartamento());

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "✅ Puesto registrado con éxito.");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "❌ Error al registrar puesto: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "⚠️ Registro cancelado por el usuario.");
        }
    }

    // ✅ Cargar puestos según el departamento seleccionado
    public static void comboPuestosPorDepartamento(JComboBox<Puesto> comboBox, int codigoDepartamento) {
        DefaultComboBoxModel<Puesto> model = new DefaultComboBoxModel<>();
        comboBox.removeAllItems();

        String sql = """
            SELECT codigoPuesto, nombre, codigoDepartamento 
            FROM puestos 
            WHERE codigoDepartamento = ?
            ORDER BY nombre ASC
        """;

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setInt(1, codigoDepartamento);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigoPuesto");
                String nombre = rs.getString("nombre");
                int dep = rs.getInt("codigoDepartamento");

                Departamento departamento = new Departamento();
                departamento.setCodigoDepartamento(dep);

                Puesto puesto = new Puesto(codigo, nombre, departamento);
                model.addElement(puesto);
            }

            comboBox.setModel(model);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al cargar puestos por departamento: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
