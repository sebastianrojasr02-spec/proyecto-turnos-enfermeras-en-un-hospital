package modelo;

/**
 * Clase base para los trabajadores del hospital.
 * Mantiene los datos comunes y define una operación polimórfica relacionada
 * con la carga horaria que cada tipo de trabajador puede representar.
 */
public abstract class Trabajador {

    private String rut;
    private String nombre;

    /**
     * Construye un trabajador sin datos iniciales.
     */
    protected Trabajador() {
        rut = "Sin RUT";
        nombre = "Sin nombre";
    }

    /**
     * Construye un trabajador con sus datos básicos.
     *
     * @param rut identificador del trabajador.
     * @param nombre nombre del trabajador.
     */
    protected Trabajador(String rut, String nombre) {
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
     * Calcula la cantidad de horas de trabajo que representa el trabajador.
     * La implementación depende del tipo concreto.
     *
     * @return cantidad de horas asociadas al trabajador.
     */
    public abstract double calcularCargaHoraria();

    @Override
    public String toString() {
        return "RUT: " + rut + " | Nombre: " + nombre;
    }
}
