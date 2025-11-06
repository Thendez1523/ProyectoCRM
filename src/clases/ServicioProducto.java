package clases;

import clases.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

public class ServicioProducto {

    private int codigoProducto;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String categoria;
    private String estado;
    private double costo;
    private double precio;
    private int stock;

    // ✅ Constructor vacío
    public ServicioProducto() {}
    public ServicioProducto(int codigoProducto, String nombre, String categoria, double precio, int stock) {
    this.codigoProducto = codigoProducto;
    this.nombre = nombre;
    this.categoria = categoria;
    this.precio = precio;
    this.stock = stock;
}
    
    public String toString() {
    return codigoProducto + " - " + nombre + " - Q" + precio;
}

    // ================= GETTERS Y SETTERS ================= //
    public int getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(int codigoProducto) { this.codigoProducto = codigoProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    
       public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    //COMBOBOX
      public static void comboProductos(JComboBox<ServicioProducto> comboBox) {
    DefaultComboBoxModel<ServicioProducto> model = new DefaultComboBoxModel<>();

    String sql = "SELECT p.codigoProducto, p.nombre, p.precio, p.stock, p.categoria AS categoriaNombre " +
                 "FROM producto p " +
                 "WHERE p.estado = 'ACTIVO' AND p.categoria = 'Producto' " +
                 "ORDER BY p.nombre ASC";

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            int codigo = rs.getInt("codigoProducto");
            String nombre = rs.getString("nombre");
            String categoriaNombre = rs.getString("categoriaNombre");
            double precio = rs.getDouble("precio");
            int stock = rs.getInt("stock");

            ServicioProducto producto = new ServicioProducto(codigo, nombre, categoriaNombre, precio, stock);
            model.addElement(producto);
        }

        comboBox.setModel(model);

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "❌ Error al mostrar productos: " + ex.getMessage());
        ex.printStackTrace();
    }
}
    
     public static void comboServicios(JComboBox<ServicioProducto> comboBox) {
    DefaultComboBoxModel<ServicioProducto> modelo = new DefaultComboBoxModel<>();
    comboBox.removeAllItems();

    String sql = "SELECT codigoProducto, nombre FROM producto WHERE categoria = 'Servicio'";

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            ServicioProducto sp = new ServicioProducto();
            sp.setCodigoProducto(rs.getInt("codigoProducto"));
            sp.setNombre(rs.getString("nombre"));
            modelo.addElement(sp);
        }

        comboBox.setModel(modelo);

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "❌ Error al cargar servicios: " + ex.getMessage());
        ex.printStackTrace();
    }
}

    // crear producto
        public void CrearProducto(ServicioProducto producto) {
        String sql = "INSERT INTO producto (nombre, descripcion, categoria, estado, costo, precio, stock) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?)";  // 7 parámetros ahora

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql)) {

        ps.setString(1, producto.getNombre());
        ps.setString(2, producto.getDescripcion());
        ps.setString(3, producto.getCategoria()); 
        ps.setString(4, producto.getEstado());
        ps.setDouble(5, producto.getCosto());
        ps.setDouble(6, producto.getPrecio());
        ps.setInt(7, producto.getStock());

        ps.executeUpdate();

        JOptionPane.showMessageDialog(null,
            "✅ Producto o Servicio registrado con éxito.",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "❌ Error al registrar: " + ex.getMessage());
        ex.printStackTrace();
    }
}
    
    public DefaultTableModel TablaProductos() {

        String[] nombresColumnas = {"ID", "Nombre", "Descripción", "Categoría", "Estado", "Costo", "Precio", "Stock"};
        DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);

        String sql = "SELECT codigoProducto, nombre, descripcion, categoria, estado, costo, precio, stock FROM producto";

        try (Connection cx = ConexionBD.getInstancia().conectar(); PreparedStatement ps = cx.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            System.out.println("Conexión establecida: " + (cx != null));
            System.out.println("Ejecutando consulta: " + sql);

            while (rs.next()) {
                Object[] fila = new Object[8];

                fila[0] = rs.getInt("codigoProducto");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("descripcion");
                fila[3] = rs.getString("categoria");
                fila[4] = rs.getString("estado");
                fila[5] = rs.getDouble("costo");
                fila[6] = rs.getDouble("precio");
                fila[7] = rs.getInt("stock");

                modelo.addRow(fila);
                System.out.println("Agregado: " + fila[1]); // imprime el nombre del producto
            }

        } catch (SQLException e) {
            System.err.println("Error al cargar la tabla de productos");
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
            return new DefaultTableModel(null, nombresColumnas);
        }

        return modelo;
        
    }
    
public void ActualizarProductoServicio(ServicioProducto productoservicio) {
    String mensaje = "¿Deseas actualizar este producto o servicio?\n\n"
            + "Código: " + productoservicio.getCodigoProducto() + "\n"
            + "Nombre: " + productoservicio.getNombre() + "\n"
            + "Descripción: " + productoservicio.getDescripcion() + "\n"
            + "Categoría: " + productoservicio.getCategoria() + "\n"
            + "Estado: " + productoservicio.getEstado() + "\n"
            + "Costo: " + productoservicio.getCosto() + "\n"
            + "Precio: " + productoservicio.getPrecio() + "\n"
            + "Stock: " + productoservicio.getStock() + "\n";

    int opcion = JOptionPane.showConfirmDialog(
            null,
            mensaje,
            "Confirmar actualización de Producto/Servicio",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (opcion == JOptionPane.YES_OPTION) {
        try (Connection cx = ConexionBD.getInstancia().conectar()) {

            // se verifica si existe o no
            String verificarSQL = "SELECT COUNT(*) FROM producto WHERE codigoProducto = ?";
            boolean existe = false;

            try (PreparedStatement psCheck = cx.prepareStatement(verificarSQL)) {
                psCheck.setInt(1, productoservicio.getCodigoProducto());
                ResultSet rs = psCheck.executeQuery();
                if (rs.next()) {
                    existe = rs.getInt(1) > 0;
                }
            }

            if (existe) {
                // si existe se actualiza
                String sqlUpdate = "UPDATE producto SET "
                        + "nombre = ?, descripcion = ?, categoria = ?, estado = ?, "
                        + "costo = ?, precio = ?, stock = ? "
                        + "WHERE codigoProducto = ?";

                try (PreparedStatement psUpdate = cx.prepareStatement(sqlUpdate)) {
                    psUpdate.setString(1, productoservicio.getNombre());
                    psUpdate.setString(2, productoservicio.getDescripcion());
                    psUpdate.setString(3, productoservicio.getCategoria());
                    psUpdate.setString(4, productoservicio.getEstado());
                    psUpdate.setDouble(5, productoservicio.getCosto());
                    psUpdate.setDouble(6, productoservicio.getPrecio());
                    psUpdate.setInt(7, productoservicio.getStock());
                    psUpdate.setInt(8, productoservicio.getCodigoProducto());

                    int filas = psUpdate.executeUpdate();
                    if (filas > 0) {
                        // ✅ MENSAJE DE ÉXITO - NO LO QUITES
                        JOptionPane.showMessageDialog(null, "✅ Producto/Servicio actualizado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "⚠️ No se pudo actualizar el producto.");
                    }
                }

            } else {
                // si no existe, se agrega
                String sqlInsert = "INSERT INTO producto (codigoProducto, nombre, descripcion, categoria, estado, costo, precio, stock) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement psInsert = cx.prepareStatement(sqlInsert)) {
                    psInsert.setInt(1, productoservicio.getCodigoProducto());
                    psInsert.setString(2, productoservicio.getNombre());
                    psInsert.setString(3, productoservicio.getDescripcion());
                    psInsert.setString(4, productoservicio.getCategoria());
                    psInsert.setString(5, productoservicio.getEstado());
                    psInsert.setDouble(6, productoservicio.getCosto());
                    psInsert.setDouble(7, productoservicio.getPrecio());
                    psInsert.setInt(8, productoservicio.getStock());

                    psInsert.executeUpdate();
                    // ✅ MENSAJE DE ÉXITO PARA INSERCIÓN
                    JOptionPane.showMessageDialog(null, "✅ Producto/Servicio creado exitosamente.");
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al actualizar producto/servicio: " + ex.getMessage());
            ex.printStackTrace();
        }

    } else {
        JOptionPane.showMessageDialog(null, "❌ Operación cancelada por el usuario.");
    }
}
    
    public boolean eliminarProductoServicio(int codigo) {
    String sql = "DELETE FROM producto WHERE codigoProducto = ?";
    
    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql)) {
        
        ps.setInt(1, codigo);
        int filasAfectadas = ps.executeUpdate();
        
        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, 
                "✅ Producto/Servicio eliminado correctamente.",
                "Eliminación Exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, 
                "❌ No se encontró ningún producto/servicio con ese código.",
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, 
            "❌ Error al eliminar producto/servicio: " + ex.getMessage(),
            "Error de Base de Dato  s", 
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
        return false;
    }
}
    
}