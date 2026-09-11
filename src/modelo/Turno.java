package modelo;

import java.io.Serializable;

/**
 * Representa un turno de trabajo en el hospital.
 */
public class Turno implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idTurno;
    private String fecha;
    private TipoTurno tipo;
    private EstadoTurno estado;
    private String horaInicio;
    private String horaFin;
    private String sector;
    private String observaciones;

    // Constructor por defecto
    public Turno() {
        this.idTurno = "Sin ID";
        this.fecha = "00-00-0000";
        this.tipo = TipoTurno.MANANA;
        this.estado = EstadoTurno.PENDIENTE;
        this.horaInicio = "00:00";
        this.horaFin = "00:00";
        this.sector = "Sin asignar";
        this.observaciones = "Sin observaciones";
    }

    // Constructor con parámetros
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
        this.tipo = tipo;
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

    public void confirmar() {
        this.estado = EstadoTurno.CONFIRMADO;
    }

    public void cancelar() {
        this.estado = EstadoTurno.CANCELADO;
    }

    public void completar() {
        this.estado = EstadoTurno.COMPLETADO;
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