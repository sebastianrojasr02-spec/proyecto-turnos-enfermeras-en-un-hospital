package modelo;

public abstract class Trabajador {
    private String rut;
    private String nombre;

    // 1. Constructor por defecto 
    public Trabajador() {
        this.rut = "Sin RUT";
        this.nombre = "Sin Nombre";
    }

    // 2. Constructor con parámetros
    public Trabajador(String rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
    }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public abstract String obtenerIdentificacion();
}