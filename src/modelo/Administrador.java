package modelo;

import java.io.Serializable;

public class Administrador extends Trabajador implements Serializable {
    private static final long serialVersionUID = 1L;
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

    @Override
    public String obtenerIdentificacion() {
        return "Administrador [" + getRut() + "] - " + getNombre();
    }
}