/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Personal
 */
public final class Producto {
    private int codigoProducto;
    private int categoria;
    private String categoriaNombre;
    private int stock;
    private String nombre;
    private String descripcion;
    private String estado;
    private double costo;
    private double precio;

    /**
     * @return the categoriaNombre
     */
    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    /**
     * @param categoriaNombre the categoriaNombre to set
     */
    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }


    /**
     * @return the codigoProducto
     */
    public int getCodigoProducto() {
        return codigoProducto;
    }

    /**
     * @param codigoProducto the codigoProducto to set
     */
    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    /**
     * @return the categoria
     */
    public int getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(double costo) {
        this.costo = costo;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
        // ✅ Constructor vacío
    public Producto() {}

    // ✅ Constructor para usar en JComboBox
    public Producto(int codigoProducto, String nombre, String nombreCategoria, double precio, int stock) {
        this.setCodigoProducto(codigoProducto);
        this.setNombre(nombre);
        this.setCategoriaNombre(nombreCategoria);
        this.setPrecio(precio);
        this.setStock(stock);
    }
    
     @Override
    public String toString() {
        return codigoProducto + " - " + nombre + " - Q" + precio;
    }
 //::::::::FUNCIONES::::::::://
    public static void comboProductos(JComboBox<Producto> comboBox) {
        DefaultComboBoxModel<Producto> model = new DefaultComboBoxModel<>();
        String sql = "SELECT p.codigoProducto, p.nombre, p.precio, p.stock, c.nombre as categoriaNombre " +
                     "FROM producto p " +
                     "INNER JOIN categoria c ON p.categoria = c.codigoCategoria " +
                     "WHERE p.estado = 'ACTIVO' " +
                     "ORDER BY p.nombre ASC";

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int codigo = rs.getInt("codigoProducto");
                String nombre = rs.getString("nombre");
                String categoriaNombre = rs.getString("categoriaNombre");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock"); // ✅ Obtener stock

                Producto producto = new Producto(codigo, nombre, categoriaNombre, precio, stock);
                model.addElement(producto);
            }

            comboBox.setModel(model);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "❌ Error al mostrar productos: " + ex.getMessage());
        }
    }


}
