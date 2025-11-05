package clases;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ContratacionServicio {
    private int codigoContratacion;
    private int cliente; // FK
    private int servicio; // FK
    private Date fechaContratacion;
    private Date fechaFin;
    private String direccionInstalacion;
    private String horarioInstalacion;
    private String estado;
    private String observacion;

    // Variables auxiliares (para mostrar información)
    private int codigoProducto;
    private String nombreCliente;
    private String nombreServicio;

    // Variables adicionales para cancelación
    private Date fechaCancelacion;
    private String motivo;

    // --- Getters y Setters ---
    public int getCodigoContratacion() { return codigoContratacion; }
    public void setCodigoContratacion(int codigoContratacion) { this.codigoContratacion = codigoContratacion; }

    public int getCliente() { return cliente; }
    public void setCliente(int cliente) { this.cliente = cliente; }

    public int getServicio() { return servicio; }
    public void setServicio(int servicio) { this.servicio = servicio; }

    public Date getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(Date fechaContratacion) { this.fechaContratacion = fechaContratacion; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public String getDireccionInstalacion() { return direccionInstalacion; }
    public void setDireccionInstalacion(String direccionInstalacion) { this.direccionInstalacion = direccionInstalacion; }

    public String getHorarioInstalacion() { return horarioInstalacion; }
    public void setHorarioInstalacion(String horarioInstalacion) { this.horarioInstalacion = horarioInstalacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    public int getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(int codigoProducto) { this.codigoProducto = codigoProducto; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getNombreServicio() { return nombreServicio; }
    public void setNombreServicio(String nombreServicio) { this.nombreServicio = nombreServicio; }

    public Date getFechaCancelacion() { return fechaCancelacion; }
    public void setFechaCancelacion(Date fechaCancelacion) { this.fechaCancelacion = fechaCancelacion; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }


    // --- Método para registrar la contratación ---
    public boolean registrarContratacion() {
        String sql = """
            INSERT INTO contrataciones 
            (cliente, servicio, fechaContratacion, fechaFin, direccionInstalacion, 
             horarioInstalacion, estado, observacion) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection cx = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            if (this.cliente <= 0) {
                JOptionPane.showMessageDialog(null, "⚠️ Cliente no válido.");
                return false;
            }
            if (this.servicio <= 0) {
                JOptionPane.showMessageDialog(null, "⚠️ Servicio no válido.");
                return false;
            }

            ps.setInt(1, this.cliente);
            ps.setInt(2, this.servicio);
            ps.setDate(3, this.fechaContratacion);
            ps.setDate(4, this.fechaFin);
            ps.setString(5, this.direccionInstalacion);
            ps.setString(6, this.horarioInstalacion);
            ps.setString(7, this.estado != null ? this.estado : "ACTIVO");
            ps.setString(8, this.observacion);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,
                    "✅ Contratación registrada correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "❌ Error al registrar la contratación:\n" + ex.getMessage(),
                    "Error SQL",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return false;
        }
    }

    // --- Validar campos ---
    public static boolean validarCampos(ContratacionServicio c) {
        if (c.getCliente() <= 0 || c.getServicio() <= 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente y un servicio válidos.");
            return false;
        }
        if (c.getFechaContratacion() == null) {
            JOptionPane.showMessageDialog(null, "Ingrese una fecha de contratación válida.");
            return false;
        }
        if (c.getDireccionInstalacion() == null || c.getDireccionInstalacion().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese la dirección de instalación.");
            return false;
        }
        return true;
    }

    // --- Combo de contrataciones activas ---
    public static void comboContrataciones(JComboBox<ContratacionServicio> comboBox) {
    DefaultComboBoxModel<ContratacionServicio> modelo = new DefaultComboBoxModel<>();
    comboBox.removeAllItems();

    String sql = """
        SELECT c.codigoContratacion, c.direccionInstalacion, c.estado,
               cl.nombre AS nombreCliente, cl.apellido AS apellidoCliente,
               p.nombre AS nombreServicio
        FROM contrataciones c
        INNER JOIN cliente cl ON c.cliente = cl.codigoCliente
        INNER JOIN producto p ON c.servicio = p.codigoProducto
        WHERE c.estado = 'ACTIVO'
        ORDER BY cl.nombre ASC
    """;

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        int contador = 0;
        while (rs.next()) {
            contador++;
            ContratacionServicio cont = new ContratacionServicio();
            cont.setCodigoContratacion(rs.getInt("codigoContratacion"));
            cont.setDireccionInstalacion(rs.getString("direccionInstalacion"));
            cont.setNombreCliente(rs.getString("nombreCliente") + " " + rs.getString("apellidoCliente"));
            cont.setNombreServicio(rs.getString("nombreServicio"));
            modelo.addElement(cont);
        }

        comboBox.setModel(modelo);
        System.out.println("✅ Contrataciones ACTIVAS cargadas: " + contador);

        if (contador == 0) {
            JOptionPane.showMessageDialog(null, 
                "No se encontraron contrataciones activas", 
                "Información", JOptionPane.INFORMATION_MESSAGE);
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, 
            "❌ Error al cargar contrataciones: " + ex.getMessage());
        ex.printStackTrace();
    }
}

    // --- Cancelar servicio ---
    public boolean registrarCancelacion() {
    String sqlInsert = """
        INSERT INTO cancelaciones (contratacion, fechaCancelacion, motivo)
        VALUES (?, ?, ?)
    """;

    String sqlUpdate = """
        UPDATE contrataciones
        SET estado = 'Cancelado'
        WHERE codigoContratacion = ?
    """;

    Connection cx = null;
    try {
        cx = ConexionBD.getInstancia().conectar();
        cx.setAutoCommit(false); // Iniciar transacción

        try (PreparedStatement psUpdate = cx.prepareStatement(sqlUpdate)) {
            // PRIMERO actualizar la contratación
            psUpdate.setInt(1, this.codigoContratacion);
            int filasActualizadas = psUpdate.executeUpdate();
            
            if (filasActualizadas == 0) {
                JOptionPane.showMessageDialog(null,
                        "❌ No se encontró la contratación con código: " + this.codigoContratacion,
                        "Error", JOptionPane.ERROR_MESSAGE);
                cx.rollback();
                return false;
            }
        }

        try (PreparedStatement psInsert = cx.prepareStatement(sqlInsert)) {
            // LUEGO insertar en cancelaciones
            psInsert.setInt(1, this.codigoContratacion);
            psInsert.setDate(2, this.fechaCancelacion);
            psInsert.setString(3, this.motivo);
            psInsert.executeUpdate();
        }

        cx.commit(); // Confirmar ambas operaciones

        JOptionPane.showMessageDialog(null,
                "✅ Servicio cancelado correctamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        return true;

    } catch (SQLException ex) {
        try {
            if (cx != null) cx.rollback(); // Revertir en caso de error
        } catch (SQLException rbEx) {
            rbEx.printStackTrace();
        }
        
        JOptionPane.showMessageDialog(null,
                "❌ Error al cancelar servicio:\n" + ex.getMessage(),
                "Error SQL", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
        return false;
    } finally {
        try {
            if (cx != null) {
                cx.setAutoCommit(true);
                cx.close();
            }
        } catch (SQLException closeEx) {
            closeEx.printStackTrace();
        }
    }
}

    //MOSTRAR CONTRATACIONES ACTIVAS
    public static DefaultTableModel obtenerContratacionesActivas() {
    String[] columnas = {"No.", "Cliente", "Servicio", "Dirección", "Fecha Contratación", "Fecha Fin", "Estado"};
    DefaultTableModel modelo = new DefaultTableModel(null, columnas);

    String sql = """
        SELECT c.codigoContratacion, 
               CONCAT(cl.nombre, ' ', cl.apellido) AS cliente,
               p.nombre AS servicio,
               c.direccionInstalacion,
               c.fechaContratacion,
               c.fechaFin,
               c.estado
        FROM contrataciones c
        INNER JOIN cliente cl ON c.cliente = cl.codigoCliente
        INNER JOIN producto p ON c.servicio = p.codigoProducto
        WHERE c.estado = 'ACTIVO'
        ORDER BY c.fechaContratacion DESC
    """;

    try (Connection cx = ConexionBD.getInstancia().conectar();
         PreparedStatement ps = cx.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Object[] fila = new Object[7];
            fila[0] = rs.getInt("codigoContratacion");
            fila[1] = rs.getString("cliente");
            fila[2] = rs.getString("servicio");
            fila[3] = rs.getString("direccionInstalacion");
            fila[4] = rs.getDate("fechaContratacion");
            fila[5] = rs.getDate("fechaFin");
            fila[6] = rs.getString("estado");
            modelo.addRow(fila);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "❌ Error al obtener contrataciones activas: " + e.getMessage());
        e.printStackTrace();
    }

    return modelo;
}
    // --- Representación legible en el ComboBox ---
    @Override
    public String toString() {
        return nombreCliente + " - " + nombreServicio + " (" + direccionInstalacion + ")";
    }
}
