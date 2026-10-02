package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una enfermera y los turnos que tiene asignados.
 */
public class Enfermera extends Trabajador {

    private String especialidad;
    private List<Turno> turnosAsignados;

    /**
     * Construye una enfermera con valores por defecto.
     */
    public Enfermera() {
        super();
        especialidad = "General";
        turnosAsignados = new ArrayList<>();
    }

    /**
     * Construye una enfermera con sus datos básicos.
     *
     * @param rut identificador.
     * @param nombre nombre completo.
     * @param especialidad área de especialización.
     */
    public Enfermera(String rut, String nombre, String especialidad) {
        super(rut, nombre);
        this.especialidad = especialidad;
        turnosAsignados = new ArrayList<>();
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * Entrega una copia de los turnos para preservar el encapsulamiento.
     *
     * @return copia de la colección interna.
     */
    public List<Turno> getTurnosAsignados() {
        return new ArrayList<>(turnosAsignados);
    }

    /**
     * Reemplaza la colección de turnos por una copia independiente.
     *
     * @param turnosAsignados nuevos turnos asociados.
     */
    public void setTurnosAsignados(List<Turno> turnosAsignados) {
        this.turnosAsignados = turnosAsignados == null
                ? new ArrayList<>()
                : new ArrayList<>(turnosAsignados);
    }

    /**
     * Agrega un turno previamente construido.
     *
     * @param turno turno que será asociado.
     */
    public void agregarTurno(Turno turno) {
        if (turno != null) {
            turnosAsignados.add(turno);
        }
    }

    /**
     * Sobrecarga que construye y agrega un turno a partir de datos básicos.
     *
     * @param idTurno identificador del turno.
     * @param fecha fecha del turno.
     * @param tipo texto con el tipo de turno.
     * @throws IllegalArgumentException cuando el tipo no corresponde al enum.
     */
    public void agregarTurno(String idTurno, String fecha, String tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de turno no puede ser nulo.");
        }

        TipoTurno tipoTurno = TipoTurno.valueOf(tipo.trim().toUpperCase());
        Turno nuevoTurno = new Turno(
                idTurno,
                fecha,
                tipoTurno,
                EstadoTurno.PENDIENTE
        );
        agregarTurno(nuevoTurno);
    }

    /**
     * Busca un turno por su identificador dentro de esta enfermera.
     *
     * @param idTurno identificador que se desea buscar.
     * @return turno encontrado o null cuando no existe.
     */
    public Turno buscarTurno(String idTurno) {
        if (idTurno == null || idTurno.trim().isEmpty()) {
            return null;
        }

        for (Turno turno : turnosAsignados) {
            if (turno.getIdTurno().equalsIgnoreCase(idTurno.trim())) {
                return turno;
            }
        }
        return null;
    }

    /**
     * Indica si la enfermera posee un turno activo en una fecha.
     * Un turno cancelado no bloquea la disponibilidad.
     *
     * @param fecha fecha que se desea consultar.
     * @return true cuando existe un turno activo ese día.
     */
    public boolean tieneTurnoActivoEnFecha(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return false;
        }

        for (Turno turno : turnosAsignados) {
            if (turno.getFecha().equalsIgnoreCase(fecha.trim())
                    && turno.esActivo()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina un turno de la colección de esta enfermera.
     *
     * @param turno turno que se desea eliminar.
     * @return true cuando el turno estaba presente y fue eliminado.
     */
    public boolean eliminarTurno(Turno turno) {
        return turno != null && turnosAsignados.remove(turno);
    }

    /**
     * Calcula las horas de los turnos no cancelados.
     * Esta implementación concreta aporta el comportamiento polimórfico
     * definido en Trabajador.
     *
     * @return horas de trabajo de sus turnos activos.
     */
    @Override
    public double calcularCargaHoraria() {
        double horas = 0.0;

        for (Turno turno : turnosAsignados) {
            if (turno.esActivo()) {
                horas += turno.calcularDuracionHoras();
            }
        }
        return horas;
    }

    @Override
    public String toString() {
        return "Enfermera [" + getRut() + "] - "
                + getNombre() + " (" + especialidad + ")";
    }
}
