package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Enfermera extends Trabajador implements Serializable {
    private static final long serialVersionUID = 1L;

    private String especialidad;
    private List<Turno> turnosAsignados;

    // Constructor por defecto
    public Enfermera() {
        super();
        this.especialidad = "General";
        this.turnosAsignados = new ArrayList<>();
    }

    // Constructor con parámetros
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
        this.turnosAsignados = turnosAsignados;
    }

    // SIA-5: Sobrecarga

    public void agregarTurno(Turno turno) {
        this.turnosAsignados.add(turno);
    }

    public void agregarTurno(String idTurno, String fecha, String tipo) {

        TipoTurno tipoTurno = TipoTurno.MANANA;

        if (tipo.equalsIgnoreCase("Tarde")) {
            tipoTurno = TipoTurno.TARDE;
        } else if (tipo.equalsIgnoreCase("Noche")) {
            tipoTurno = TipoTurno.NOCHE;
        }

        Turno nuevoTurno = new Turno(
                idTurno,
                fecha,
                tipoTurno,
                EstadoTurno.PENDIENTE,
                "00:00",
                "00:00",
                "Sin asignar",
                "Sin observaciones"
        );

        this.turnosAsignados.add(nuevoTurno);
    }

    @Override
    public String obtenerIdentificacion() {
        return "Enfermera [" + getRut() + "] - "
                + getNombre() + " (" + especialidad + ")";
    }
}