package controlador;

import java.util.List;
import modelo.Enfermera;
import modelo.EstadoTurno;
import modelo.TipoTurno;
import modelo.Turno;

/**
 * Controla las operaciones solicitadas desde la interfaz de consola.
 * Actúa como intermediario entre MenuConsola y GestorHospital.
 */
public class ControladorConsola {

    private GestorHospital gestor;

    public ControladorConsola(GestorHospital gestor) {
        this.gestor = gestor;
    }

    /**
     * Solicita al gestor registrar una nueva enfermera.
     */
    public boolean agregarEnfermera(
            String rut,
            String nombre,
            String especialidad) {

        return gestor.agregarEnfermera(rut, nombre, especialidad);
    }

    /**
     * Obtiene todas las enfermeras registradas.
     */
    public List<Enfermera> obtenerEnfermeras() {
        return gestor.obtenerEnfermeras();
    }

    /**
     * Busca una enfermera mediante su RUT.
     */
    public Enfermera buscarEnfermera(String rut) {
        return gestor.buscarEnfermera(rut);
    }

    /**
     * Solicita la modificación de una enfermera.
     */
    public boolean modificarEnfermera(
            String rut,
            String nombre,
            String especialidad) {

        return gestor.modificarEnfermera(rut, nombre, especialidad);
    }

    /**
     * Solicita la eliminación de una enfermera.
     */
    public boolean eliminarEnfermera(String rut) {
        return gestor.eliminarEnfermera(rut);
    }

    /**
     * Convierte el tipo recibido desde la consola y solicita
     * al gestor la asignación del turno.
     */
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

    /**
     * Busca un turno perteneciente a una enfermera.
     */
    public Turno buscarTurno(String rut, String idTurno) {
        return gestor.buscarTurnoDeEnfermera(rut, idTurno);
    }

    /**
     * Convierte los datos recibidos desde la consola y solicita
     * al gestor la modificación del turno.
     */
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

    /**
     * Solicita la eliminación de un turno.
     */
    public boolean eliminarTurno(String rut, String idTurno) {
        return gestor.eliminarTurno(rut, idTurno);
    }

    /**
     * Busca enfermeras según su especialidad.
     */
    public List<Enfermera> buscarPorEspecialidad(String especialidad) {
        return gestor.buscarPorEspecialidad(especialidad);
    }

    /**
     * Busca enfermeras disponibles para una especialidad y fecha.
     */
    public List<Enfermera> buscarEnfermerasDisponibles(
            String especialidad,
            String fecha) {

        return gestor.buscarEnfermerasDisponibles(especialidad, fecha);
    }

    /**
     * Solicita guardar los datos actuales.
     */
    public void guardarDatos() {
        gestor.guardarDatos();
    }

    /**
     * Convierte el texto ingresado desde la vista al enum TipoTurno.
     */
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

    /**
     * Convierte el texto ingresado desde la vista al enum EstadoTurno.
     */
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