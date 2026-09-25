package controlador;

import java.util.List;
import modelo.Enfermera;
import modelo.EnfermeraNoEncontradaException;
import modelo.EstadoTurno;
import modelo.TipoTurno;
import modelo.Turno;
import modelo.TurnoException;

/**
 * Controla las operaciones solicitadas desde la interfaz gráfica.
 * Actúa como intermediario entre VentanaPrincipal y GestorHospital.
 */
public class ControladorVentana {

    private GestorHospital gestor;
    private String ultimoError;

    public ControladorVentana(GestorHospital gestor) {
        this.gestor = gestor;
        this.ultimoError = "";
    }

    public boolean agregarEnfermera(String rut, String nombre, String especialidad) {
        limpiarError();
        return gestor.agregarEnfermera(rut, nombre, especialidad);
    }

    public List<Enfermera> obtenerEnfermeras() {
        return gestor.obtenerEnfermeras();
    }

    public Enfermera buscarEnfermera(String rut) {
        return gestor.buscarEnfermera(rut);
    }

    public boolean modificarEnfermera(String rut, String nombre, String especialidad) {
        limpiarError();
        return gestor.modificarEnfermera(rut, nombre, especialidad);
    }

    public boolean eliminarEnfermera(String rut) {
        limpiarError();
        return gestor.eliminarEnfermera(rut);
    }

    public boolean agregarTurno(String rut, String idTurno, String fecha, String tipo) {
        limpiarError();

        TipoTurno tipoTurno = convertirTipoTurno(tipo);

        if (tipoTurno == null) {
            ultimoError = "El tipo de turno ingresado no es válido.";
            return false;
        }

        try {
            return gestor.agregarTurno(rut, idTurno, fecha, tipoTurno);
        } catch (EnfermeraNoEncontradaException | TurnoException e) {
            ultimoError = e.getMessage();
            return false;
        }
    }

    public Turno buscarTurno(String rut, String idTurno) {
        return gestor.buscarTurnoDeEnfermera(rut, idTurno);
    }

    public boolean modificarTurno(String rut, String idTurno, String fecha,
            String tipo, String estado) {

        limpiarError();

        TipoTurno tipoTurno = convertirTipoTurno(tipo);
        EstadoTurno estadoTurno = convertirEstadoTurno(estado);

        if (tipoTurno == null) {
            ultimoError = "El tipo de turno ingresado no es válido.";
            return false;
        }

        if (estadoTurno == null) {
            ultimoError = "El estado del turno ingresado no es válido.";
            return false;
        }

        try {
            return gestor.modificarTurno(
                    rut,
                    idTurno,
                    fecha,
                    tipoTurno,
                    estadoTurno
            );
        } catch (EnfermeraNoEncontradaException | TurnoException e) {
            ultimoError = e.getMessage();
            return false;
        }
    }

    public boolean eliminarTurno(String rut, String idTurno) {
        limpiarError();

        try {
            return gestor.eliminarTurno(rut, idTurno);
        } catch (EnfermeraNoEncontradaException | TurnoException e) {
            ultimoError = e.getMessage();
            return false;
        }
    }

    public List<Enfermera> buscarPorEspecialidad(String especialidad) {
        return gestor.buscarPorEspecialidad(especialidad);
    }

    public List<Enfermera> buscarEnfermerasDisponibles(
            String especialidad, String fecha) {

        return gestor.buscarEnfermerasDisponibles(especialidad, fecha);
    }

    public void guardarDatos() {
        gestor.guardarDatos();
    }

    public String getUltimoError() {
        return ultimoError;
    }

    private void limpiarError() {
        ultimoError = "";
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