package controlador;

import java.util.List;
import modelo.Enfermera;
import modelo.EstadoTurno;
import modelo.TipoTurno;
import modelo.Turno;

/**
 * Controla las operaciones solicitadas desde la interfaz gráfica.
 * Actúa como intermediario entre VentanaPrincipal y GestorHospital.
 */
public class ControladorVentana {

    private GestorHospital gestor;

    public ControladorVentana(GestorHospital gestor) {
        this.gestor = gestor;
    }

    public boolean agregarEnfermera(
            String rut,
            String nombre,
            String especialidad) {

        return gestor.agregarEnfermera(rut, nombre, especialidad);
    }

    public List<Enfermera> obtenerEnfermeras() {
        return gestor.obtenerEnfermeras();
    }

    public Enfermera buscarEnfermera(String rut) {
        return gestor.buscarEnfermera(rut);
    }

    public boolean modificarEnfermera(
            String rut,
            String nombre,
            String especialidad) {

        return gestor.modificarEnfermera(rut, nombre, especialidad);
    }

    public boolean eliminarEnfermera(String rut) {
        return gestor.eliminarEnfermera(rut);
    }

    public boolean agregarTurno(
            String rut,
            String idTurno,
            String fecha,
            String tipo) {

        TipoTurno tipoTurno = convertirTipoTurno(tipo);

        if (tipoTurno == null) {
            return false;
        }

        return gestor.agregarTurno(rut, idTurno, fecha, tipoTurno);
    }

    public Turno buscarTurno(String rut, String idTurno) {
        return gestor.buscarTurnoDeEnfermera(rut, idTurno);
    }

    public boolean modificarTurno(
            String rut,
            String idTurno,
            String fecha,
            String tipo,
            String estado) {

        TipoTurno tipoTurno = convertirTipoTurno(tipo);
        EstadoTurno estadoTurno = convertirEstadoTurno(estado);

        if (tipoTurno == null || estadoTurno == null) {
            return false;
        }

        return gestor.modificarTurno(
                rut,
                idTurno,
                fecha,
                tipoTurno,
                estadoTurno
        );
    }

    public boolean eliminarTurno(String rut, String idTurno) {
        return gestor.eliminarTurno(rut, idTurno);
    }

    public List<Enfermera> buscarPorEspecialidad(String especialidad) {
        return gestor.buscarPorEspecialidad(especialidad);
    }

    public List<Enfermera> buscarEnfermerasDisponibles(
            String especialidad,
            String fecha) {

        return gestor.buscarEnfermerasDisponibles(especialidad, fecha);
    }

    public void guardarDatos() {
        gestor.guardarDatos();
    }

    private TipoTurno convertirTipoTurno(String tipo) {
        if (tipo == null) {
            return null;
        }

        try {
            return TipoTurno.valueOf(tipo.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private EstadoTurno convertirEstadoTurno(String estado) {
        if (estado == null) {
            return null;
        }

        try {
            return EstadoTurno.valueOf(estado.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}