package modelo;

/**
 * Representa un administrador del hospital.
 */
public class Administrador extends Trabajador {

    private String cargo;

    public Administrador() {
        super();
        this.cargo = "Administrador";
    }

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
     * Implementa la identificación específica de un administrador.
     */
    @Override
    public String obtenerIdentificacion() {
        return "Administrador [" + getRut() + "] - "
                + getNombre() + " (" + cargo + ")";
    }
}