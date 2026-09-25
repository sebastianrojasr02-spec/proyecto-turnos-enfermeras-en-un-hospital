package controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.Enfermera;
import modelo.EnfermeraNoEncontradaException;
import modelo.EstadoTurno;
import modelo.TipoTurno;
import modelo.Turno;
import modelo.TurnoException;
import persistencia.PersistenciaCSV;

/**
 * Gestiona la lógica principal relacionada con enfermeras y turnos.
 * La persistencia de los datos se delega a PersistenciaCSV.
 */
public class GestorHospital {

    private Map<String, Enfermera> mapaEnfermeras;
    private PersistenciaCSV persistencia;

    public GestorHospital() {
        persistencia = new PersistenciaCSV();
        mapaEnfermeras = persistencia.cargar();

        if (mapaEnfermeras.isEmpty()) {
            cargarDatosIniciales();
        }
    }

    /**
     * Carga datos iniciales cuando no existen enfermeras almacenadas.
     */
    private void cargarDatosIniciales() {
        Enfermera enf1 = new Enfermera(
                "11111111-1",
                "Ana Rojas",
                "Urgencias"
        );

        Enfermera enf2 = new Enfermera(
                "22222222-2",
                "Bárbara Silva",
                "Pediatría"
        );

        Turno turno1 = new Turno(
                "T001",
                "10-09-2026",
                TipoTurno.MANANA,
                EstadoTurno.CONFIRMADO,
                "08:00",
                "16:00",
                "Urgencias",
                "Turno normal"
        );

        Turno turno2 = new Turno(
                "T002",
                "11-09-2026",
                TipoTurno.NOCHE,
                EstadoTurno.PENDIENTE,
                "22:00",
                "08:00",
                "Pediatría",
                "Turno nocturno"
        );

        enf1.agregarTurno(turno1);
        enf2.agregarTurno(turno2);

        mapaEnfermeras.put(enf1.getRut(), enf1);
        mapaEnfermeras.put(enf2.getRut(), enf2);
    }

    /**
     * Agrega una enfermera previamente creada.
     */
    public boolean agregarEnfermera(Enfermera nuevaEnfermera) {
        if (nuevaEnfermera == null) {
            return false;
        }

        String rut = nuevaEnfermera.getRut();

        if (!esTextoValido(rut) || mapaEnfermeras.containsKey(rut)) {
            return false;
        }

        mapaEnfermeras.put(rut, nuevaEnfermera);
        return true;
    }

    /**
     * Sobrecarga que permite registrar una enfermera utilizando sus datos.
     */
    public boolean agregarEnfermera(String rut, String nombre, String especialidad) {
        if (!esTextoValido(rut)
                || !esTextoValido(nombre)
                || !esTextoValido(especialidad)) {
            return false;
        }

        Enfermera nuevaEnfermera = new Enfermera(
                rut.trim(),
                nombre.trim(),
                especialidad.trim()
        );

        return agregarEnfermera(nuevaEnfermera);
    }

    /**
     * Retorna todas las enfermeras registradas.
     */
    public List<Enfermera> obtenerEnfermeras() {
        return new ArrayList<>(mapaEnfermeras.values());
    }

    /**
     * Busca una enfermera mediante su RUT.
     */
    public Enfermera buscarEnfermera(String rut) {
        if (!esTextoValido(rut)) {
            return null;
        }

        return mapaEnfermeras.get(rut.trim());
    }

    /**
     * Elimina una enfermera mediante su RUT.
     */
    public boolean eliminarEnfermera(String rut) {
        if (!esTextoValido(rut)) {
            return false;
        }

        return mapaEnfermeras.remove(rut.trim()) != null;
    }

    /**
     * Modifica los datos de una enfermera existente.
     */
    public boolean modificarEnfermera(
            String rut,
            String nuevoNombre,
            String nuevaEspecialidad) {

        if (!esTextoValido(rut)
                || !esTextoValido(nuevoNombre)
                || !esTextoValido(nuevaEspecialidad)) {
            return false;
        }

        Enfermera enfermera = buscarEnfermera(rut);

        if (enfermera == null) {
            return false;
        }

        enfermera.setNombre(nuevoNombre.trim());
        enfermera.setEspecialidad(nuevaEspecialidad.trim());

        return true;
    }

    /**
     * Busca enfermeras según su especialidad.
     */
    public List<Enfermera> buscarPorEspecialidad(String especialidad) {
        List<Enfermera> resultado = new ArrayList<>();

        if (!esTextoValido(especialidad)) {
            return resultado;
        }

        for (Enfermera enfermera : mapaEnfermeras.values()) {
            if (enfermera.getEspecialidad() != null
                    && enfermera.getEspecialidad().equalsIgnoreCase(
                            especialidad.trim())) {
                resultado.add(enfermera);
            }
        }

        return resultado;
    }

    /**
     * Asigna un nuevo turno a una enfermera.
     *
     * @throws EnfermeraNoEncontradaException si el RUT no corresponde
     * a una enfermera registrada.
     * @throws TurnoException si el ID del turno ya está asignado
     * a la enfermera.
     */
    public boolean agregarTurno(
            String rut,
            String idTurno,
            String fecha,
            TipoTurno tipo)
            throws EnfermeraNoEncontradaException, TurnoException {

        if (!esTextoValido(rut)
                || !esTextoValido(idTurno)
                || !esTextoValido(fecha)
                || tipo == null) {
            return false;
        }

        Enfermera enfermera = buscarEnfermera(rut);

        if (enfermera == null) {
            throw new EnfermeraNoEncontradaException(
                    "No existe una enfermera registrada con el RUT " + rut + "."
            );
        }

        if (buscarTurnoDeEnfermera(rut, idTurno) != null) {
            throw new TurnoException(
                    "Ya existe el turno " + idTurno
                    + " para la enfermera indicada."
            );
        }

        Turno nuevoTurno = new Turno(
                idTurno.trim(),
                fecha.trim(),
                tipo,
                EstadoTurno.PENDIENTE,
                "00:00",
                "00:00",
                "Sin asignar",
                "Sin observaciones"
        );

        enfermera.agregarTurno(nuevoTurno);
        return true;
    }

    /**
     * Busca un turno perteneciente a una enfermera.
     */
    public Turno buscarTurnoDeEnfermera(String rut, String idTurno) {
        if (!esTextoValido(rut) || !esTextoValido(idTurno)) {
            return null;
        }

        Enfermera enfermera = buscarEnfermera(rut);

        if (enfermera == null) {
            return null;
        }

        for (Turno turno : enfermera.getTurnosAsignados()) {
            if (turno.getIdTurno().equalsIgnoreCase(idTurno.trim())) {
                return turno;
            }
        }

        return null;
    }

    /**
     * Elimina un turno perteneciente a una enfermera.
     *
     * @throws EnfermeraNoEncontradaException si la enfermera no existe.
     * @throws TurnoException si el turno no existe.
     */
    public boolean eliminarTurno(String rut, String idTurno)
            throws EnfermeraNoEncontradaException, TurnoException {

        if (!esTextoValido(rut) || !esTextoValido(idTurno)) {
            return false;
        }

        Enfermera enfermera = buscarEnfermera(rut);

        if (enfermera == null) {
            throw new EnfermeraNoEncontradaException(
                    "No existe una enfermera registrada con el RUT " + rut + "."
            );
        }

        Turno turno = buscarTurnoDeEnfermera(rut, idTurno);

        if (turno == null) {
            throw new TurnoException(
                    "No existe el turno " + idTurno
                    + " para la enfermera indicada."
            );
        }

        return enfermera.getTurnosAsignados().remove(turno);
    }

    /**
     * Modifica los datos principales de un turno.
     *
     * @throws EnfermeraNoEncontradaException si la enfermera no existe.
     * @throws TurnoException si el turno no existe.
     */
    public boolean modificarTurno(
            String rut,
            String idTurno,
            String nuevaFecha,
            TipoTurno nuevoTipo,
            EstadoTurno nuevoEstado)
            throws EnfermeraNoEncontradaException, TurnoException {

        if (!esTextoValido(rut)
                || !esTextoValido(idTurno)
                || !esTextoValido(nuevaFecha)
                || nuevoTipo == null
                || nuevoEstado == null) {
            return false;
        }

        Enfermera enfermera = buscarEnfermera(rut);

        if (enfermera == null) {
            throw new EnfermeraNoEncontradaException(
                    "No existe una enfermera registrada con el RUT " + rut + "."
            );
        }

        Turno turno = buscarTurnoDeEnfermera(rut, idTurno);

        if (turno == null) {
            throw new TurnoException(
                    "No existe el turno " + idTurno
                    + " para la enfermera indicada."
            );
        }

        turno.setFecha(nuevaFecha.trim());
        turno.setTipo(nuevoTipo);
        turno.setEstado(nuevoEstado);

        return true;
    }

    /**
     * Busca enfermeras disponibles para una especialidad y fecha.
     */
    public List<Enfermera> buscarEnfermerasDisponibles(
            String especialidad,
            String fecha) {

        List<Enfermera> disponibles = new ArrayList<>();

        if (!esTextoValido(especialidad) || !esTextoValido(fecha)) {
            return disponibles;
        }

        for (Enfermera enfermera : mapaEnfermeras.values()) {
            if (enfermera.getEspecialidad() != null
                    && enfermera.getEspecialidad().equalsIgnoreCase(
                            especialidad.trim())
                    && estaDisponible(enfermera, fecha.trim())) {
                disponibles.add(enfermera);
            }
        }

        return disponibles;
    }

    /**
     * Determina si una enfermera se encuentra disponible en una fecha.
     */
    private boolean estaDisponible(Enfermera enfermera, String fecha) {
        for (Turno turno : enfermera.getTurnosAsignados()) {
            if (turno.getFecha().equalsIgnoreCase(fecha)
                    && turno.getEstado() != EstadoTurno.CANCELADO) {
                return false;
            }
        }

        return true;
    }

    /**
     * Guarda los datos actuales mediante la capa de persistencia.
     */
    public void guardarDatos() {
        persistencia.guardar(mapaEnfermeras);
    }

    /**
     * Comprueba que un texto posea contenido válido.
     */
    private boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}