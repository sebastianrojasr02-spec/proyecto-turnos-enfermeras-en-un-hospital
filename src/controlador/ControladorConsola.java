package controlador;

import excepciones.EnfermeraNoEncontradaException;
import excepciones.TurnoException;
import java.util.List;
import java.util.Map;
import modelo.Enfermera;
import modelo.EstadoTurno;
import modelo.TipoTurno;
import modelo.Turno;
import servicio.ConversorTurnos;
import servicio.GestorHospital;

/**
 * Adapta las entradas y resultados del menú de consola al servicio de dominio.
 */
public class ControladorConsola {

    private final GestorHospital gestor;
    private String ultimoError;

    public ControladorConsola(GestorHospital gestor) {
        this.gestor = gestor;
        ultimoError = "";
    }

    /** Registra una enfermera a partir de los datos entregados por la interfaz. */
    public boolean agregarEnfermera(
            String rut, String nombre, String especialidad) {
        limpiarError();
        return gestor.agregarEnfermera(rut, nombre, especialidad);
    }

    /** Obtiene una copia de las enfermeras registradas para su presentación. */
    public List<Enfermera> obtenerEnfermeras() {
        return gestor.obtenerEnfermeras();
    }

    /** Obtiene los turnos agrupados por enfermera sin exponer el estado interno. */
    public Map<Enfermera, List<Turno>> obtenerTurnosPorEnfermera() {
        return gestor.obtenerTurnosPorEnfermera();
    }

    /** Busca una enfermera mediante su identificador. */
    public Enfermera buscarEnfermera(String rut) {
        return gestor.buscarEnfermera(rut);
    }

    /** Modifica los datos editables de una enfermera. */
    public boolean modificarEnfermera(
            String rut, String nombre, String especialidad) {
        limpiarError();
        return gestor.modificarEnfermera(rut, nombre, especialidad);
    }

    /** Elimina una enfermera y registra en ultimoError cualquier error de negocio. */
    public boolean eliminarEnfermera(String rut) {
        limpiarError();
        return gestor.eliminarEnfermera(rut);
    }

    /** Solicita la asignación de un turno y traduce el tipo desde texto. */
    public boolean agregarTurno(
            String rut, String idTurno, String fecha, String tipo) {
        limpiarError();
        TipoTurno tipoTurno = ConversorTurnos.aTipoTurno(tipo);

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

    /** Busca un turno asociado a una enfermera. */
    public Turno buscarTurno(String rut, String idTurno) {
        return gestor.buscarTurnoDeEnfermera(rut, idTurno);
    }

    /** Modifica un turno y convierte sus valores de interfaz a enums del dominio. */
    public boolean modificarTurno(
            String rut, String idTurno, String fecha,
            String tipo, String estado) {
        limpiarError();
        TipoTurno tipoTurno = ConversorTurnos.aTipoTurno(tipo);
        EstadoTurno estadoTurno = ConversorTurnos.aEstadoTurno(estado);

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
                    rut, idTurno, fecha, tipoTurno, estadoTurno);
        } catch (EnfermeraNoEncontradaException | TurnoException e) {
            ultimoError = e.getMessage();
            return false;
        }
    }

    /** Elimina un turno y conserva el mensaje de la excepción de negocio. */
    public boolean eliminarTurno(String rut, String idTurno) {
        limpiarError();
        try {
            return gestor.eliminarTurno(rut, idTurno);
        } catch (EnfermeraNoEncontradaException | TurnoException e) {
            ultimoError = e.getMessage();
            return false;
        }
    }

    /** Ejecuta la consulta de enfermeras por especialidad. */
    public List<Enfermera> buscarPorEspecialidad(String especialidad) {
        return gestor.buscarPorEspecialidad(especialidad);
    }

    /** Ejecuta la consulta de disponibilidad por especialidad y fecha. */
    public List<Enfermera> buscarEnfermerasDisponibles(
            String especialidad, String fecha) {
        return gestor.buscarEnfermerasDisponibles(especialidad, fecha);
    }

    /** Solicita el cálculo de carga horaria de una enfermera. */
    public double calcularCargaHoraria(String rut) {
        limpiarError();
        try {
            return gestor.calcularCargaHoraria(rut);
        } catch (EnfermeraNoEncontradaException e) {
            ultimoError = e.getMessage();
            return -1.0;
        }
    }

    /** Solicita el guardado del estado actual mediante el servicio. */
    public boolean guardarDatos() {
        return gestor.guardarDatos();
    }

    /** Devuelve el último mensaje de error registrado por el controlador. */
    public String getUltimoError() {
        return ultimoError;
    }

    private void limpiarError() {
        ultimoError = "";
    }
}
