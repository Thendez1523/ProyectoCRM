package clases;

import clases.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;


public class ServicioProducto {

    private int codigoProducto;
    private String nombreProducto;
    private String descripcion;
    private String tipo;
    private String categoria;
    private String estado;
    private double costo;
    private double precio;
    private int stock;

    // ✅ Constructor vacío
    public ServicioProducto() {}

    // ================= GETTERS Y SETTERS ================= //
    public int getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(int codigoProducto) { this.codigoProducto = codigoProducto; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombre) { this.nombreProducto = nombre; }

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
    

    // crear producto
        public void CrearProducto(ServicioProducto producto) {
        String sql = "INSERT INTO producto (nombre, descripcion, categoria, estado, costo, precio, stock) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?)";  // 7 parámetros ahora
        
        

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql)) {

        ps.setString(1, producto.getNombreProducto());
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
    
    public static void llenarComboProductos(javax.swing.JComboBox<ServicioProducto> comboBox) {
    javax.swing.DefaultComboBoxModel<ServicioProducto> model = new javax.swing.DefaultComboBoxModel<>();
    String sql = "SELECT codigoProducto, nombre, descripcion, categoria, precio FROM producto ORDER BY nombre ASC";

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            ServicioProducto producto = new ServicioProducto();
            producto.setCodigoProducto(rs.getInt("codigoProducto"));
            producto.setNombreProducto(rs.getString("nombre"));
            model.addElement(producto);
        }
        comboBox.setModel(model);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "❌ Error al cargar productos: " + e.getMessage());
    }
}
    
    @Override
public String toString() {
    return this.getCodigoProducto() + " - " + this.getNombreProducto();
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
            "Error de Base de Datos", 
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
        return false;
    }
}
    
    public ServicioProducto obtenerProductoPorCodigo(int codigo) {
    String sql = "SELECT * FROM producto WHERE codigoProducto = ?";
    
    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql)) {
        
        ps.setInt(1, codigo);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            ServicioProducto producto = new ServicioProducto();
            producto.setCodigoProducto(rs.getInt("codigoProducto"));
            producto.setNombreProducto(rs.getString("nombre"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setCategoria(rs.getString("categoria"));
            producto.setEstado(rs.getString("estado"));
            producto.setCosto(rs.getDouble("costo"));
            producto.setPrecio(rs.getDouble("precio"));
            producto.setStock(rs.getInt("stock"));
            return producto;
        }
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "❌ Error al buscar producto: " + ex.getMessage());
        ex.printStackTrace();
    }
    
    return null;
}

public void ActualizarProductoServicioSelectivo(ServicioProducto productoservicio) {
    try {
        // Primero obtenemos el producto actual de la base de datos
        ServicioProducto productoActual = obtenerProductoPorCodigo(productoservicio.getCodigoProducto());
        
        if (productoActual == null) {
            JOptionPane.showMessageDialog(null, "❌ No se encontró el producto con código: " + productoservicio.getCodigoProducto());
            return;
        }

        // Construimos la consulta SQL dinámicamente
        StringBuilder sql = new StringBuilder("UPDATE producto SET ");
        List<Object> parametros = new ArrayList<>();
        boolean hayCambios = false;

        // Solo actualizamos los campos que tienen valores diferentes a los predeterminados
        if (productoservicio.getNombreProducto() != null && !productoservicio.getNombreProducto().isEmpty()) {
            sql.append("nombre = ?, ");
            parametros.add(productoservicio.getNombreProducto());
            hayCambios = true;
        } else {
            // Mantenemos el valor actual
            sql.append("nombre = ?, ");
            parametros.add(productoActual.getNombreProducto());
        }

        if (productoservicio.getDescripcion() != null && !productoservicio.getDescripcion().isEmpty()) {
            sql.append("descripcion = ?, ");
            parametros.add(productoservicio.getDescripcion());
            hayCambios = true;
        } else {
            sql.append("descripcion = ?, ");
            parametros.add(productoActual.getDescripcion());
        }

        if (productoservicio.getCategoria() != null && !productoservicio.getCategoria().isEmpty()) {
            sql.append("categoria = ?, ");
            parametros.add(productoservicio.getCategoria());
            hayCambios = true;
        } else {
            sql.append("categoria = ?, ");
            parametros.add(productoActual.getCategoria());
        }

        if (productoservicio.getEstado() != null && !productoservicio.getEstado().isEmpty()) {
            sql.append("estado = ?, ");
            parametros.add(productoservicio.getEstado());
            hayCambios = true;
        } else {
            sql.append("estado = ?, ");
            parametros.add(productoActual.getEstado());
        }

        // Para campos numéricos, usamos -1 como indicador de "no cambiar"
        if (productoservicio.getCosto() >= 0) {
            sql.append("costo = ?, ");
            parametros.add(productoservicio.getCosto());
            hayCambios = true;
        } else {
            sql.append("costo = ?, ");
            parametros.add(productoActual.getCosto());
        }

        if (productoservicio.getPrecio() >= 0) {
            sql.append("precio = ?, ");
            parametros.add(productoservicio.getPrecio());
            hayCambios = true;
        } else {
            sql.append("precio = ?, ");
            parametros.add(productoActual.getPrecio());
        }

        if (productoservicio.getStock() >= 0) {
            sql.append("stock = ?, ");
            parametros.add(productoservicio.getStock());
            hayCambios = true;
        } else {
            sql.append("stock = ?, ");
            parametros.add(productoActual.getStock());
        }

        // Eliminar la última coma y espacio
        sql.setLength(sql.length() - 2);
        
        // Agregar la condición WHERE
        sql.append(" WHERE codigoProducto = ?");
        parametros.add(productoservicio.getCodigoProducto());

        if (!hayCambios) {
            JOptionPane.showMessageDialog(null, "ℹ️ No hay cambios para actualizar.");
            return;
        }

        // Mostrar confirmación
        String mensaje = "¿Deseas actualizar este producto o servicio?\n\n"
                + "Código: " + productoservicio.getCodigoProducto() + "\n"
                + "Nombre: " + (productoservicio.getNombreProducto() != null && !productoservicio.getNombreProducto().isEmpty() ? productoservicio.getNombreProducto() : productoActual.getNombreProducto()) + "\n"
                + "Descripción: " + (productoservicio.getDescripcion() != null && !productoservicio.getDescripcion().isEmpty() ? productoservicio.getDescripcion() : productoActual.getDescripcion()) + "\n"
                + "Categoría: " + (productoservicio.getCategoria() != null && !productoservicio.getCategoria().isEmpty() ? productoservicio.getCategoria() : productoActual.getCategoria()) + "\n"
                + "Estado: " + (productoservicio.getEstado() != null && !productoservicio.getEstado().isEmpty() ? productoservicio.getEstado() : productoActual.getEstado()) + "\n"
                + "Costo: " + (productoservicio.getCosto() >= 0 ? productoservicio.getCosto() : productoActual.getCosto()) + "\n"
                + "Precio: " + (productoservicio.getPrecio() >= 0 ? productoservicio.getPrecio() : productoActual.getPrecio()) + "\n"
                + "Stock: " + (productoservicio.getStock() >= 0 ? productoservicio.getStock() : productoActual.getStock()) + "\n";

        int opcion = JOptionPane.showConfirmDialog(
                null,
                mensaje,
                "Confirmar actualización de Producto/Servicio",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            try (Connection cx = ConexionBD.getInstancia().conectar();
                 PreparedStatement ps = cx.prepareStatement(sql.toString())) {
                
                // Establecer parámetros
                for (int i = 0; i < parametros.size(); i++) {
                    ps.setObject(i + 1, parametros.get(i));
                }
                
                int filas = ps.executeUpdate();
                if (filas > 0) {
                    JOptionPane.showMessageDialog(null, "✅ Producto/Servicio actualizado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "⚠️ No se pudo actualizar el producto.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "❌ Operación cancelada por el usuario.");
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "❌ Error al actualizar producto/servicio: " + ex.getMessage());
        ex.printStackTrace();
    }
}
    
}