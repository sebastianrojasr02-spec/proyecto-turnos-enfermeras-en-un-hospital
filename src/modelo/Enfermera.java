package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una enfermera del hospital y los turnos que tiene asignados.
 */
public class Enfermera extends Trabajador {

    private String especialidad;
    private List<Turno> turnosAsignados;

    public Enfermera() {
        super();
        this.especialidad = "General";
        this.turnosAsignados = new ArrayList<>();
    }

    public Enfermera(String rut, String nombre, String especialidad) {
        super(rut, nombre);
        this.especialidad = especialidad;
        this.turnosAsignados = new ArrayList<>();
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public List<Turno> getTurnosAsignados() {
        return turnosAsignados;
    }

    public void setTurnosAsignados(List<Turno> turnosAsignados) {
        if (turnosAsignados == null) {
            this.turnosAsignados = new ArrayList<>();
        } else {
            this.turnosAsignados = turnosAsignados;
        }
    }

    /**
     * Agrega un turno previamente creado a la enfermera.
     */
    public void agregarTurno(Turno turno) {
        if (turno != null) {
            turnosAsignados.add(turno);
        }
    }

    /**
     * Sobrecarga que permite crear y agregar un turno mediante sus datos básicos.
     */
    public void agregarTurno(String idTurno, String fecha, String tipo) {
        TipoTurno tipoTurno = convertirTipoTurno(tipo);

        Turno nuevoTurno = new Turno(idTurno, fecha, tipoTurno,
                EstadoTurno.PENDIENTE, "00:00", "00:00",
                "Sin asignar", "Sin observaciones");

        agregarTurno(nuevoTurno);
    }

    /**
     * Convierte el nombre de un tipo de turno al valor correspondiente del enum.
     */
    private TipoTurno convertirTipoTurno(String tipo) {
        if (tipo != null && tipo.equalsIgnoreCase("Tarde")) {
            return TipoTurno.TARDE;
        }

        if (tipo != null && tipo.equalsIgnoreCase("Noche")) {
            return TipoTurno.NOCHE;
        }

        return TipoTurno.MANANA;
    }

    /**
     * Implementa la identificación específica de una enfermera.
     */
    @Override
    public String obtenerIdentificacion() {
        return "Enfermera [" + getRut() + "] - "
                + getNombre() + " (" + especialidad + ")";
    }
}