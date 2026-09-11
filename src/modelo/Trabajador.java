package modelo;

import java.io.Serializable;

public abstract class Trabajador implements Serializable {
    private static final long serialVersionUID = 1L;
    private String rut;
    private String nombre;

    // Constructor por defecto
    public Trabajador() {
    }

    // Constructor parametrizado
    public Trabajador(String rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Firma abstracta requerida para el polimorfismo SIA-6
    public abstract String obtenerIdentificacion();

    @Override
    public String toString() {
        return "RUT: " + rut + ", Nombre: " + nombre;
    }
}