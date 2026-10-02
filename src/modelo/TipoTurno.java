package modelo;

/**
 * Tipos de turno disponibles para una enfermera.
 * Cada tipo define el horario estándar utilizado por el sistema para que
 * la creación y modificación de turnos mantenga una duración coherente.
 */
public enum TipoTurno {
    MANANA("08:00", "16:00"),
    TARDE("14:00", "22:00"),
    NOCHE("22:00", "08:00");

    private final String horaInicio;
    private final String horaFin;

    TipoTurno(String horaInicio, String horaFin) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }
}
