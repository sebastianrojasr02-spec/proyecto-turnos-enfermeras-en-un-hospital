package modelo;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Representa un turno de trabajo asignable a una enfermera.
 */
public class Turno {

    private static final DateTimeFormatter FORMATO_HORA =
            DateTimeFormatter.ofPattern("HH:mm");

    private String idTurno;
    private String fecha;
    private TipoTurno tipo;
    private EstadoTurno estado;
    private String horaInicio;
    private String horaFin;
    private String sector;
    private String observaciones;

    /**
     * Construye un turno con valores por defecto.
     */
    public Turno() {
        idTurno = "Sin ID";
        fecha = "00-00-0000";
        tipo = TipoTurno.MANANA;
        estado = EstadoTurno.PENDIENTE;
        horaInicio = tipo.getHoraInicio();
        horaFin = tipo.getHoraFin();
        sector = "Sin asignar";
        observaciones = "Sin observaciones";
    }

    /**
     * Construye un turno usando el horario estándar definido por su tipo.
     * Esta variante se utiliza cuando la interfaz entrega sólo los datos
     * básicos de la jornada.
     *
     * @param idTurno identificador del turno.
     * @param fecha fecha asignada.
     * @param tipo tipo de jornada y fuente del horario estándar.
     * @param estado estado inicial del turno.
     */
    public Turno(String idTurno, String fecha, TipoTurno tipo,
            EstadoTurno estado) {
        if (tipo == null) {
            throw new IllegalArgumentException(
                    "El tipo de turno no puede ser nulo.");
        }

        this.idTurno = idTurno;
        this.fecha = fecha;
        this.tipo = tipo;
        this.estado = estado;
        this.horaInicio = tipo.getHoraInicio();
        this.horaFin = tipo.getHoraFin();
        this.sector = "Sin asignar";
        this.observaciones = "Sin observaciones";
    }

    /**
     * Construye un turno con todos sus datos.
     */
    public Turno(String idTurno, String fecha, TipoTurno tipo,
            EstadoTurno estado, String horaInicio, String horaFin,
            String sector, String observaciones) {
        this.idTurno = idTurno;
        this.fecha = fecha;
        this.tipo = tipo;
        this.estado = estado;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.sector = sector;
        this.observaciones = observaciones;
    }

    public String getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public TipoTurno getTipo() {
        return tipo;
    }

    public void setTipo(TipoTurno tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException(
                    "El tipo de turno no puede ser nulo.");
        }

        this.tipo = tipo;
        this.horaInicio = tipo.getHoraInicio();
        this.horaFin = tipo.getHoraFin();
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Determina si el turno bloquea la disponibilidad de la enfermera.
     *
     * @return false para turnos cancelados y true para el resto.
     */
    public boolean esActivo() {
        return estado != EstadoTurno.CANCELADO;
    }

    /**
     * Calcula la duración del turno considerando también turnos nocturnos
     * que terminan al día siguiente.
     *
     * @return duración en horas.
     * @throws IllegalStateException cuando una hora no tiene formato HH:mm.
     */
    public double calcularDuracionHoras() {
        try {
            LocalTime inicio = LocalTime.parse(horaInicio, FORMATO_HORA);
            LocalTime fin = LocalTime.parse(horaFin, FORMATO_HORA);

            long minutos = Duration.between(inicio, fin).toMinutes();

            if (minutos < 0) {
                minutos += 24 * 60;
            }

            return minutos / 60.0;
        } catch (DateTimeParseException e) {
            throw new IllegalStateException(
                    "Las horas del turno deben usar el formato HH:mm.", e);
        }
    }

    public void confirmar() {
        estado = EstadoTurno.CONFIRMADO;
    }

    public void cancelar() {
        estado = EstadoTurno.CANCELADO;
    }

    public void completar() {
        estado = EstadoTurno.COMPLETADO;
    }

    @Override
    public String toString() {
        return "Turno [" + idTurno + "]"
                + " - Fecha: " + fecha
                + " | Tipo: " + tipo
                + " | Estado: " + estado
                + " | Horario: " + horaInicio + " - " + horaFin
                + " | Sector: " + sector
                + " | Observaciones: " + observaciones;
    }
}
