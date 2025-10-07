/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class Persona {
    private int codigo;
    private String dpi;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String direccion;
    private Date fechaNac;
    private int edad;

    // ======= Getters y Setters =======
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getDpi() { return dpi; }
    public void setDpi(String dpi) { this.dpi = dpi; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Date getFechaNac() { return fechaNac; }
    public void setFechaNac(Date fechaNac) { 
        this.fechaNac = fechaNac; 
        this.edad = calcularEdad(); // recalcular al asignar fecha
    }

    public int getEdad() { return edad; }

    // ======= Métodos =======
    public int calcularEdad() {
        if (fechaNac == null) return 0;
        LocalDate fechaNacimiento = fechaNac.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate hoy = LocalDate.now();
        return Period.between(fechaNacimiento, hoy).getYears();
    }

    
}
