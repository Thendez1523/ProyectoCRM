package clases;

import clases.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.JTable;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Venta {
    // ========== ATRIBUTOS DE LA ENTIDAD ==========
    private int codigoVenta;
    private int cliente;
    private int empleado;
    private double total;
    private Date fechaVenta;
    private String estado;
    private String direccionVenta;
    private String telefonoCliente;
    private String nombreCliente; // Para el ComboBox

    // ========== CONSTRUCTORES ==========
    public Venta() {
    }

    // Constructor para ComboBox
    public Venta(int codigoVenta, String nombreCliente, double total) {
        this.codigoVenta = codigoVenta;
        this.nombreCliente = nombreCliente;
        this.total = total;
    }

    // ========== GETTERS Y SETTERS ==========
    public int getCodigoVenta() { return codigoVenta; }
    public void setCodigoVenta(int codigoVenta) { this.codigoVenta = codigoVenta; }

    public int getCliente() { return cliente; }
    public void setCliente(int cliente) { this.cliente = cliente; }

    public int getEmpleado() { return empleado; }
    public void setEmpleado(int empleado) { this.empleado = empleado; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public Date getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(Date fechaVenta) { this.fechaVenta = fechaVenta; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getDireccionVenta() { return direccionVenta; }
    public void setDireccionVenta(String direccionVenta) { this.direccionVenta = direccionVenta; }

    public String getTelefonoCliente() { return telefonoCliente; }
    public void setTelefonoCliente(String telefonoCliente) { this.telefonoCliente = telefonoCliente; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }


// ========== MÉTODO toString PARA COMBOBOX ==========
    @Override
    public String toString() {
        return String.format("Venta #%d - %s - Q%,.2f", codigoVenta, nombreCliente, total);
    }

    // ========== FUNCIÓN 1: MOSTRAR PRODUCTO AGREGADO A LA TABLA ==========
    
    /**
     * Inicializa la tabla de productos para la venta
     */
    public void inicializarTablaProductos(JTable tabla) {
        String[] columnas = {
            "Código", "Producto", "Cantidad", "Precio", "Subtotal"
        };
        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.setModel(modelo);
    }

    /**
     * Agrega un producto a la tabla de venta
     */
    public void agregarProductoATabla(JTable tabla, int codigoProducto, String nombreProducto, int cantidad, double precio) {
        try {
            // Validaciones
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(null, "❌ La cantidad debe ser mayor a 0");
                return;
            }
            
            if (precio <= 0) {
                JOptionPane.showMessageDialog(null, "❌ El precio debe ser mayor a 0");
                return;
            }
            
            // Verificar stock en tiempo real
            int stockActual = obtenerStockActual(codigoProducto);
            if (stockActual <= 0) {
                JOptionPane.showMessageDialog(null, 
                    "❌ Producto sin stock disponible\n" +
                    "Producto: " + nombreProducto + "\n" +
                    "Stock actual: " + stockActual,
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            double subtotal = cantidad * precio;
            
            // Verificar si el producto ya existe en la tabla
            boolean productoExiste = false;
            for (int i = 0; i < modelo.getRowCount(); i++) {
                int codigoExistente = Integer.parseInt(modelo.getValueAt(i, 0).toString());
                if (codigoExistente == codigoProducto) {
                    // Verificar stock para la cantidad acumulada
                    int cantidadActual = Integer.parseInt(modelo.getValueAt(i, 2).toString());
                    int nuevaCantidad = cantidadActual + cantidad;
                    
                    if (nuevaCantidad > stockActual) {
                        JOptionPane.showMessageDialog(null, 
                            "❌ Stock insuficiente para la cantidad acumulada\n" +
                            "Producto: " + nombreProducto + "\n" +
                            "Cantidad actual en carrito: " + cantidadActual + "\n" +
                            "Nueva cantidad a agregar: " + cantidad + "\n" +
                            "Total solicitado: " + nuevaCantidad + "\n" +
                            "Stock disponible: " + stockActual,
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // Actualizar cantidad existente
                    double nuevoSubtotal = nuevaCantidad * precio;
                    modelo.setValueAt(nuevaCantidad, i, 2);
                    modelo.setValueAt(nuevoSubtotal, i, 4);
                    productoExiste = true;
                    JOptionPane.showMessageDialog(null, "✅ Cantidad actualizada correctamente");
                    break;
                }
            }
            
            // Si no existe, agregar nueva fila
            if (!productoExiste) {
                if (cantidad > stockActual) {
                    JOptionPane.showMessageDialog(null, 
                        "❌ Stock insuficiente\n" +
                        "Producto: " + nombreProducto + "\n" +
                        "Cantidad solicitada: " + cantidad + "\n" +
                        "Stock disponible: " + stockActual,
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Object[] fila = { 
                    codigoProducto,    // Código
                    nombreProducto,    // Producto
                    cantidad,          // Cantidad
                    precio,            // Precio
                    subtotal           // Subtotal
                };
                modelo.addRow(fila);
                JOptionPane.showMessageDialog(null, "✅ Producto agregado correctamente");
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al agregar producto: " + ex.getMessage());
        }
    }

    /**
     * Obtiene el stock actual desde la base de datos
     */
    private int obtenerStockActual(int codigoProducto) {
        String sql = "SELECT stock FROM producto WHERE codigoProducto = ?";
        
        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            
            ps.setInt(1, codigoProducto);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("stock");
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al verificar stock: " + ex.getMessage());
        }
        return 0;
    }

    /**
     * Elimina un producto de la tabla
     */
    public void eliminarProductoDeTabla(JTable tabla, int filaSeleccionada) {
        if (filaSeleccionada >= 0) {
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.removeRow(filaSeleccionada);
            JOptionPane.showMessageDialog(null, "✅ Producto eliminado");
        } else {
            JOptionPane.showMessageDialog(null, "❌ Seleccione un producto para eliminar");
        }
    }

    /**
     * Limpia todos los productos de la tabla
     */
    public void limpiarTablaProductos(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        this.total = 0;
    }

    // ========== GENERAR VENTA (CALCULAR TOTAL E INGRESAR A BD) ==========
    
    /**
     * Calcula el total de la venta
     */
    public double calcularTotal(JTable tabla) {
        double total = 0;
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        
        for (int i = 0; i < modelo.getRowCount(); i++) {
            try {
                Object valor = modelo.getValueAt(i, 4); // Columna subtotal
                if (valor != null) {
                    total += Double.parseDouble(valor.toString());
                }
            } catch (NumberFormatException e) {
                System.err.println("Error en fila " + i + ": valor no numérico en subtotal");
            }
        }
        
        this.total = total;
        return this.total;
    }

    /**
     * Método auxiliar para obtener el total
     */
    public double obtenerTotal() {
        return this.total;
    }

    /**
     * Genera la venta completa: calcula total y guarda en base de datos
     */
    public int generarVenta(int codigoCliente, int codigoEmpleado, String direccion, String telefono, JTable tabla) {
        if (tabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "❌ No hay productos en la venta");
            return 0;
        }
        
        calcularTotal(tabla);
        
        String sqlVenta = "INSERT INTO venta (codigoCliente, codigoEmpleado, fechaVenta, total, estado, direccion) " +
                         "VALUES (?, ?, NOW(), ?, 'PENDIENTE', ?)";
        
        Connection cx = null;
        try {
            cx = ConexionBD.getInstancia().conectar();
            cx.setAutoCommit(false);
            
            int codigoVenta = 0;
            
            // Insertar venta (cabecera)
            try (PreparedStatement ps = cx.prepareStatement(sqlVenta, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, codigoCliente);
                ps.setInt(2, codigoEmpleado);
                ps.setDouble(3, this.total);
                ps.setString(4, direccion);
                ps.executeUpdate();
                
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    codigoVenta = rs.getInt(1);
                    this.codigoVenta = codigoVenta;
                }
            }
            
            // Insertar detalles de la venta
            String sqlDetalle = "INSERT INTO detalleVenta (codigoVenta, codigoProducto, cantidad, precioIndividual, subtotal) " +
                               "VALUES (?, ?, ?, ?, ?)";
            
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            try (PreparedStatement psDetalle = cx.prepareStatement(sqlDetalle)) {
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    int codigoProducto = Integer.parseInt(modelo.getValueAt(i, 0).toString());
                    int cantidad = Integer.parseInt(modelo.getValueAt(i, 2).toString());
                    double precio = Double.parseDouble(modelo.getValueAt(i, 3).toString());
                    double subtotal = Double.parseDouble(modelo.getValueAt(i, 4).toString());
                    
                    psDetalle.setInt(1, codigoVenta);
                    psDetalle.setInt(2, codigoProducto);
                    psDetalle.setInt(3, cantidad);
                    psDetalle.setDouble(4, precio);
                    psDetalle.setDouble(5, subtotal);
                    psDetalle.addBatch();
                }
                psDetalle.executeBatch();
            }
            
            // Actualizar stock
            actualizarStock(cx, tabla);
            
            cx.commit();
            
            JOptionPane.showMessageDialog(null, 
                "✅ Venta generada exitosamente\n" +
                "Código de Venta: " + codigoVenta + "\n" +
                "Total: Q" + String.format("%.2f", this.total));
            
            return codigoVenta;
            
        } catch (SQLException ex) {
            try {
                if (cx != null) cx.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "❌ Error al generar venta: " + ex.getMessage());
            return 0;
            
        } finally {
            try {
                if (cx != null) {
                    cx.setAutoCommit(true);
                    cx.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Actualiza el stock de productos después de la venta
     */
    private void actualizarStock(Connection cx, JTable tabla) throws SQLException {
        String sql = "UPDATE producto SET stock = stock - ? WHERE codigoProducto = ?";
        
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        try (PreparedStatement ps = cx.prepareStatement(sql)) {
            for (int i = 0; i < modelo.getRowCount(); i++) {
                int codigoProducto = Integer.parseInt(modelo.getValueAt(i, 0).toString());
                int cantidad = Integer.parseInt(modelo.getValueAt(i, 2).toString());
                
                ps.setInt(1, cantidad);
                ps.setInt(2, codigoProducto);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    // ========== MÉTODOS PARA MOSTRAR VENTAS EN TABLAS ==========
    
    /**
     * Muestra ventas generales en tabla
     */
    public void mostrarVentasEnTabla(JTable tabla) {
        String[] columnas = {
            "Código Venta", "Cliente", "Empleado", "Fecha Venta", "Total", "Estado", "Dirección"
        };
        
        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        String sql = "SELECT v.codigoVenta, " +
                     "CONCAT(c.nombre, ' ', c.apellido) as nombreCliente, " +
                     "CONCAT(e.nombre, ' ', e.apellido) as nombreEmpleado, " +
                     "v.fechaVenta, v.total, v.estado, v.direccion " +
                     "FROM venta v " +
                     "INNER JOIN cliente c ON v.codigoCliente = c.codigoCliente " +
                     "INNER JOIN empleados e ON v.codigoEmpleado = e.codigoEmpleado " +
                     "ORDER BY v.fechaVenta DESC, v.codigoVenta DESC";
        
        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = new Object[columnas.length];
                fila[0] = rs.getInt("codigoVenta");
                fila[1] = rs.getString("nombreCliente");
                fila[2] = rs.getString("nombreEmpleado");
                fila[3] = rs.getDate("fechaVenta");
                fila[4] = rs.getDouble("total");
                fila[5] = rs.getString("estado");
                fila[6] = rs.getString("direccion");
                modelo.addRow(fila);
            }
            
            tabla.setModel(modelo);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al cargar ventas: " + ex.getMessage());
        }
    }

    /**
     * Muestra ventas detalladas con productos
     */
    public void mostrarVentasDetalladas(JTable tabla) {
        String[] columnas = {
            "Código Detalle", "No. Venta", "Cliente", "Producto", 
            "Cantidad", "Precio Unitario", "Subtotal", "Fecha Venta"
        };
        
        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: return Integer.class;
                    case 1: return Integer.class;
                    case 4: return Integer.class;
                    case 5: return Double.class;
                    case 6: return Double.class;
                    case 7: return Date.class;
                    default: return String.class;
                }
            }
        };
        
        String sql = "SELECT dv.codigoDetalle, v.codigoVenta, " +
                     "CONCAT(c.nombre, ' ', c.apellido) as nombreCliente, " +
                     "p.nombre as nombreProducto, dv.cantidad, " +
                     "dv.precioIndividual, dv.subtotal, v.fechaVenta " +
                     "FROM detalleVenta dv " +
                     "INNER JOIN venta v ON dv.codigoVenta = v.codigoVenta " +
                     "INNER JOIN producto p ON dv.codigoProducto = p.codigoProducto " +
                     "INNER JOIN cliente c ON v.codigoCliente = c.codigoCliente " +
                     "ORDER BY v.fechaVenta DESC, v.codigoVenta DESC";
        
        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = new Object[columnas.length];
                fila[0] = rs.getInt("codigoDetalle");
                fila[1] = rs.getInt("codigoVenta");
                fila[2] = rs.getString("nombreCliente");
                fila[3] = rs.getString("nombreProducto");
                fila[4] = rs.getInt("cantidad");
                fila[5] = rs.getDouble("precioIndividual");
                fila[6] = rs.getDouble("subtotal");
                fila[7] = rs.getTimestamp("fechaVenta");
                modelo.addRow(fila);
            }
            
            tabla.setModel(modelo);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al cargar ventas detalladas: " + ex.getMessage());
        }
    } 
    
    //MOSTRAR VENTA COMPLETA
      
   public void mostrarVentasCompletas(JTable tabla) {
    String[] columnas = {
        "No. Venta", "Cliente", "Dirección", "Producto",
        "Cantidad", "Total", "Estado"
    };

    DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    String sql = """
        SELECT v.codigoVenta,
               CONCAT(c.nombre, ' ', c.apellido) AS nombreCliente,
               v.direccion,
               p.nombre AS nombreProducto,
               dv.cantidad,
               v.total,
               v.estado
        FROM detalleVenta dv
        INNER JOIN venta v ON dv.codigoVenta = v.codigoVenta
        INNER JOIN producto p ON dv.codigoProducto = p.codigoProducto
        INNER JOIN cliente c ON v.codigoCliente = c.codigoCliente
        ORDER BY v.fechaVenta DESC, v.codigoVenta DESC
    """;

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Object[] fila = new Object[columnas.length];
            fila[0] = rs.getInt("codigoVenta");
            fila[1] = rs.getString("nombreCliente");
            fila[2] = rs.getString("direccion");
            fila[3] = rs.getString("nombreProducto");
            fila[4] = rs.getInt("cantidad");
            fila[5] = rs.getDouble("total");
            fila[6] = rs.getString("estado");
            modelo.addRow(fila);
        }

        tabla.setModel(modelo);

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "❌ Error al cargar ventas detalladas: " + ex.getMessage());
    }
}
   //Actualizar Estado Venta
   public static void actualizarEstadoVenta(int codigoVenta, JComboBox<String> jComboBoxEstado) {
    String sql = "UPDATE venta SET estado = ? WHERE codigoVenta = ?";

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql)) {

        // Obtener el valor seleccionado del ComboBox
        String nuevoEstado = jComboBoxEstado.getSelectedItem().toString();

        // Asignar los valores a la consulta
        ps.setString(1, nuevoEstado);
        ps.setInt(2, codigoVenta);

        // Ejecutar actualización
        int filasAfectadas = ps.executeUpdate();

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, 
                "✅ Estado actualizado correctamente a: " + nuevoEstado);
        } else {
            JOptionPane.showMessageDialog(null, 
                "⚠️ No se encontró la venta con código: " + codigoVenta);
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, 
            "❌ Error al actualizar estado: " + ex.getMessage());
    }
}

    // ========== MÉTODO PARA COMBOBOX DE VENTAS ==========
    
    /**
     * Llena un ComboBox con ventas (número de venta, cliente y total)
     */
    public static void comboVentas(JComboBox<Venta> comboBox) {
        DefaultComboBoxModel<Venta> model = new DefaultComboBoxModel<>();
        String sql = "SELECT v.codigoVenta, " +
                     "CONCAT(c.nombre, ' ', c.apellido) AS nombreCliente, " +
                     "v.total " +
                     "FROM venta v " +
                     "INNER JOIN cliente c ON v.codigoCliente = c.codigoCliente " +
                     "ORDER BY v.fechaVenta DESC, v.codigoVenta DESC";
        
        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                int codigoVenta = rs.getInt("codigoVenta");
                String nombreCliente = rs.getString("nombreCliente");
                double total = rs.getDouble("total");
                
                Venta venta = new Venta(codigoVenta, nombreCliente, total);
                model.addElement(venta);
            }
            
            comboBox.setModel(model);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al cargar ventas en ComboBox: " + ex.getMessage());
        }
    }

    // ========== MÉTODOS AUXILIARES ==========
    
    /**
     * Obtiene información de un producto por código
     * @param codigoProducto
     */
    public Object[] obtenerProductoPorCodigo(int codigoProducto) {
        String sql = "SELECT nombre, precio, stock FROM producto WHERE codigoProducto = ? AND estado = 'ACTIVO'";
        
        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {
            
            ps.setInt(1, codigoProducto);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new Object[]{
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                };
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al obtener producto: " + ex.getMessage());
        }
        return null;
    }
}