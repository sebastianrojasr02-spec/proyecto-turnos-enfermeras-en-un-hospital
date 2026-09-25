package modelo;

/**
 * Representa los datos y comportamientos comunes de los trabajadores
 * que forman parte del sistema del hospital.
 */
public abstract class Trabajador {

    private String rut;
    private String nombre;

    public Trabajador() {
    }

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

    /**
     * Retorna una identificación descriptiva según el tipo de trabajador.
     */
    public abstract String obtenerIdentificacion();

    @Override
    public String toString() {
        return "RUT: " + rut + ", Nombre: " + nombre;
    }
}