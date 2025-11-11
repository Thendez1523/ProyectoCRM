package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Categoria {

    private int codigoCategoria;
    private String nombre;
    private String descripcion;

    // --- Constructores ---
    public Categoria() {}

    public Categoria(int codigoCategoria, String nombre, String descripcion) {
        this.codigoCategoria = codigoCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // --- Getters y Setters ---
    public int getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(int codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
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
        return nombre; // Para que se muestre correctamente en el JComboBox
    }

    // ============================================================
    // 🟩 MÉTODO 1: Crear nueva categoría
    // ============================================================
    public void crearCategoria(Categoria categoria) {
        String mensaje = "¿Desea guardar esta categoría con los siguientes datos?\n\n"
                + "Nombre: " + categoria.getNombre() + "\n"
                + "Descripción: " + categoria.getDescripcion();

        int opcion = JOptionPane.showConfirmDialog(null, mensaje, 
                "Confirmar registro de categoría", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            String sql = "INSERT INTO categoria (nombre, descripcion) VALUES (?, ?)";

            try (Connection cx = ConexionBD.getInstancia().conectar();
                 PreparedStatement ps = cx.prepareStatement(sql)) {

                ps.setString(1, categoria.getNombre());
                ps.setString(2, categoria.getDescripcion());
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, 
                        "✅ Categoría registrada con éxito.", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, 
                        "❌ Error al registrar categoría: " + ex.getMessage(), 
                        "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "⚠️ Registro cancelado por el usuario.");
        }
    }

    // ============================================================
    // 🟨 MÉTODO 2: Actualizar categoría existente
    // ============================================================
    public void actualizarCategoria(Categoria categoria) {
        String mensaje = "¿Desea actualizar esta categoría?\n\n"
                + "Código: " + categoria.getCodigoCategoria() + "\n"
                + "Nuevo nombre: " + categoria.getNombre() + "\n"
                + "Nueva descripción: " + categoria.getDescripcion();

        int opcion = JOptionPane.showConfirmDialog(null, mensaje,
                "Confirmar actualización de categoría",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            String sql = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE codigoCategoria = ?";

            try (Connection cx = ConexionBD.getInstancia().conectar();
                 PreparedStatement ps = cx.prepareStatement(sql)) {

                ps.setString(1, categoria.getNombre());
                ps.setString(2, categoria.getDescripcion());
                ps.setInt(3, categoria.getCodigoCategoria());

                int filas = ps.executeUpdate();
                if (filas > 0) {
                    JOptionPane.showMessageDialog(null, 
                            "✅ Categoría actualizada correctamente.", 
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, 
                            "⚠️ No se encontró la categoría especificada.", 
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, 
                        "❌ Error al actualizar categoría: " + ex.getMessage(), 
                        "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "⚠️ Actualización cancelada por el usuario.");
        }
    }

    // ============================================================
    // 🟦 MÉTODO 3: Cargar todas las categorías en un JComboBox
    // ============================================================
   public static void comboCategoria(JComboBox<Categoria> comboBox) {
        DefaultComboBoxModel<Categoria> model = new DefaultComboBoxModel<>();
        String sql = "SELECT codigoCategoria, nombre, descripcion FROM categoria ORDER BY nombre ASC";

        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cx = ConexionBD.getInstancia().conectar();
            ps = cx.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Categoria c = new Categoria(
                        rs.getInt("codigoCategoria"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                model.addElement(c);
            }
            comboBox.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al llenar combo de categoria: " + e.getMessage());
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
}
