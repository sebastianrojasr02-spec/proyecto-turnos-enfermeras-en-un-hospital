package modelo;

/**
 * Representa un administrador del hospital.
 * Los administradores no reciben turnos de enfermería, por lo que su carga
 * horaria de turnos es cero.
 */
public class Administrador extends Trabajador {

    private String cargo;

    /**
     * Construye un administrador con valores por defecto.
     */
    public Administrador() {
        super();
        cargo = "Administrador";
    }

    /**
     * Construye un administrador con sus datos.
     *
     * @param rut identificador.
     * @param nombre nombre completo.
     * @param cargo función administrativa.
     */
    public Administrador(String rut, String nombre, String cargo) {
        super(rut, nombre);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Un administrador no posee turnos de enfermería en este sistema.
     *
     * @return 0 horas de turnos.
     */
    @Override
    public double calcularCargaHoraria() {
        return 0.0;
    }

    @Override
    public String toString() {
        return "Administrador [" + getRut() + "] - "
                + getNombre() + " (" + cargo + ")";
    }
}
