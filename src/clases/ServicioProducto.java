package clases;

import clases.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class ServicioProducto {

    private int codigoProducto;
    private String nombre;
    private String descripcion;
    private int categoria;
    private String estado;
    private double costo;
    private double precio;
    private int stock;

    // ✅ Constructor vacío
    public ServicioProducto() {}

    // ================= GETTERS Y SETTERS ================= //
    public int getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(int codigoProducto) { this.codigoProducto = codigoProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getCategoria() { return categoria; }
    public void setCategoria(int categoria) { this.categoria = categoria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    // ================= MÉTODO CREAR PRODUCTO ================= //
    public void CrearProducto(ServicioProducto producto) {
        String sql = "INSERT INTO producto (nombre, descripcion, categoria, estado, costo, precio, stock) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setInt(3, producto.getCategoria());
            ps.setString(4, producto.getEstado());
            ps.setDouble(5, producto.getCosto());
            ps.setDouble(6, producto.getPrecio());
            ps.setInt(7, producto.getStock());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null,
                "✅ Producto registrado con éxito.",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al registrar producto: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public DefaultTableModel TablaProductos() {
    
        String[] nombresColumnas = {"No. Servicio", "Servicio/Producto", "Descripcion", "Categoria", "Estado", "Costo", "Precio", "Stock"};
        DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);

        String sql = "SELECT codigoProdudo, nombre, description, categoria, estado, costo, precio, stock FROM producto"; 

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[8];
                
                fila[0] = rs.getInt("codigoProdudo"); 
                fila[1] = rs.getString("nombre"); 
                fila[2] = rs.getString("description"); 
                fila[3] = rs.getInt("categoria");
                fila[4] = rs.getString("estado");
                fila[5] = rs.getDouble("costo");
                fila[6] = rs.getDouble("precio");
                fila[7] = rs.getInt("stock");
                
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            System.err.println("Error al cargar la tabla de productos");
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
            return new DefaultTableModel(null, nombresColumnas);
        }
        
        return modelo;
}
    
}