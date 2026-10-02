package servicio;

import excepciones.EnfermeraNoEncontradaException;
import excepciones.TurnoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import modelo.Enfermera;
import modelo.EstadoTurno;
import modelo.TipoTurno;
import modelo.Turno;
import modelo.Trabajador;
import persistencia.PersistenciaCSV;
import persistencia.RepositorioHospital;

/**
 * Servicio de dominio que administra enfermeras y turnos.
 * No depende de una implementación concreta de almacenamiento: recibe un
 * RepositorioHospital, lo que permite separar reglas de negocio y persistencia.
 */
public class GestorHospital {

    private final Map<String, Enfermera> mapaEnfermeras;
    private final RepositorioHospital persistencia;

    /**
     * Construye el gestor utilizando persistencia CSV por defecto.
     */
    public GestorHospital() {
        this(new PersistenciaCSV());
    }

    /**
     * Construye el gestor con una estrategia de persistencia inyectada.
     *
     * @param persistencia repositorio utilizado para cargar y guardar datos.
     */
    public GestorHospital(RepositorioHospital persistencia) {
        if (persistencia == null) {
            throw new IllegalArgumentException("El repositorio no puede ser nulo.");
        }

        this.persistencia = persistencia;
        mapaEnfermeras = new LinkedHashMap<>(persistencia.cargar());

        if (mapaEnfermeras.isEmpty()) {
            cargarDatosIniciales();
        }
    }

    private void cargarDatosIniciales() {
        Enfermera ana = new Enfermera(
                "11111111-1", "Ana Rojas", "Urgencias");
        Enfermera barbara = new Enfermera(
                "22222222-2", "Bárbara Silva", "Pediatría");

        ana.agregarTurno(new Turno(
                "T001", "10-09-2026", TipoTurno.MANANA,
                EstadoTurno.CONFIRMADO, "08:00", "16:00",
                "Urgencias", "Turno normal"));

        barbara.agregarTurno(new Turno(
                "T002", "11-09-2026", TipoTurno.NOCHE,
                EstadoTurno.PENDIENTE, "22:00", "08:00",
                "Pediatría", "Turno nocturno"));

        mapaEnfermeras.put(ana.getRut(), ana);
        mapaEnfermeras.put(barbara.getRut(), barbara);
    }

    /**
     * Registra una enfermera ya construida.
     *
     * @param nuevaEnfermera objeto que se desea registrar.
     * @return true cuando la inserción fue válida.
     */
    public boolean agregarEnfermera(Enfermera nuevaEnfermera) {
        if (nuevaEnfermera == null) {
            return false;
        }

        String rut = nuevaEnfermera.getRut();
        if (!esTextoValido(rut)) {
            return false;
        }

        rut = rut.trim();
        if (mapaEnfermeras.containsKey(rut)) {
            return false;
        }

        mapaEnfermeras.put(rut, nuevaEnfermera);
        return true;
    }

    /**
     * Sobrecarga para registrar una enfermera a partir de datos simples.
     *
     * @param rut identificador.
     * @param nombre nombre.
     * @param especialidad especialidad.
     * @return true cuando la inserción fue válida.
     */
    public boolean agregarEnfermera(
            String rut, String nombre, String especialidad) {
        if (!esTextoValido(rut)
                || !esTextoValido(nombre)
                || !esTextoValido(especialidad)) {
            return false;
        }

        return agregarEnfermera(new Enfermera(
                rut.trim(), nombre.trim(), especialidad.trim()));
    }

    /**
     * Obtiene todas las enfermeras registradas en orden de inserción.
     *
     * @return copia de la colección principal.
     */
    public List<Enfermera> obtenerEnfermeras() {
        return new ArrayList<>(mapaEnfermeras.values());
    }

    /**
     * Obtiene los turnos agrupados por enfermera para su posterior presentación.
     * La información retornada es una copia que no permite alterar el dominio.
     *
     * @return mapa enfermera -> turnos asignados.
     */
    public Map<Enfermera, List<Turno>> obtenerTurnosPorEnfermera() {
        Map<Enfermera, List<Turno>> resultado = new LinkedHashMap<>();

        for (Enfermera enfermera : mapaEnfermeras.values()) {
            List<Turno> turnos = enfermera.getTurnosAsignados();
            if (!turnos.isEmpty()) {
                resultado.put(enfermera,
                        Collections.unmodifiableList(turnos));
            }
        }

        return Collections.unmodifiableMap(resultado);
    }

    /**
     * Busca una enfermera por RUT.
     *
     * @param rut identificador buscado.
     * @return enfermera encontrada o null.
     */
    public Enfermera buscarEnfermera(String rut) {
        if (!esTextoValido(rut)) {
            return null;
        }
        return mapaEnfermeras.get(rut.trim());
    }

    /**
     * Elimina una enfermera por RUT.
     *
     * @param rut identificador.
     * @return true cuando fue eliminada.
     */
    public boolean eliminarEnfermera(String rut) {
        if (!esTextoValido(rut)) {
            return false;
        }
        return mapaEnfermeras.remove(rut.trim()) != null;
    }

    /**
     * Modifica el nombre y especialidad de una enfermera existente.
     */
    public boolean modificarEnfermera(
            String rut, String nuevoNombre, String nuevaEspecialidad) {
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
     * Busca enfermeras cuya especialidad coincide sin distinguir mayúsculas.
     */
    public List<Enfermera> buscarPorEspecialidad(String especialidad) {
        List<Enfermera> resultado = new ArrayList<>();
        if (!esTextoValido(especialidad)) {
            return resultado;
        }

        String consulta = especialidad.trim();
        for (Enfermera enfermera : mapaEnfermeras.values()) {
            if (enfermera.getEspecialidad() != null
                    && enfermera.getEspecialidad().equalsIgnoreCase(consulta)) {
                resultado.add(enfermera);
            }
        }
        return resultado;
    }

    /**
     * Asigna un nuevo turno a una enfermera.
     *
     * @throws EnfermeraNoEncontradaException si la enfermera no existe.
     * @throws TurnoException si el identificador ya pertenece a un turno.
     */
    public boolean agregarTurno(
            String rut, String idTurno, String fecha, TipoTurno tipo)
            throws EnfermeraNoEncontradaException, TurnoException {
        if (!esTextoValido(rut)
                || !esTextoValido(idTurno)
                || !esTextoValido(fecha)
                || tipo == null) {
            return false;
        }

        Enfermera enfermera = exigirEnfermera(rut);
        if (enfermera.buscarTurno(idTurno) != null) {
            throw new TurnoException(
                    "Ya existe el turno " + idTurno
                    + " para la enfermera indicada.");
        }

        enfermera.agregarTurno(new Turno(
                idTurno.trim(),
                fecha.trim(),
                tipo,
                EstadoTurno.PENDIENTE));
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
        return enfermera == null ? null : enfermera.buscarTurno(idTurno);
    }

    /**
     * Elimina un turno de una enfermera.
     *
     * @throws EnfermeraNoEncontradaException si la enfermera no existe.
     * @throws TurnoException si el turno no existe.
     */
    public boolean eliminarTurno(String rut, String idTurno)
            throws EnfermeraNoEncontradaException, TurnoException {
        Enfermera enfermera = exigirEnfermera(rut);
        Turno turno = enfermera.buscarTurno(idTurno);

        if (turno == null) {
            throw new TurnoException(
                    "No existe el turno " + idTurno
                    + " para la enfermera indicada.");
        }
        return enfermera.eliminarTurno(turno);
    }

    /**
     * Modifica los datos editables de un turno.
     *
     * @throws EnfermeraNoEncontradaException si la enfermera no existe.
     * @throws TurnoException si el turno no existe.
     */
    public boolean modificarTurno(
            String rut, String idTurno, String nuevaFecha,
            TipoTurno nuevoTipo, EstadoTurno nuevoEstado)
            throws EnfermeraNoEncontradaException, TurnoException {
        if (!esTextoValido(rut)
                || !esTextoValido(idTurno)
                || !esTextoValido(nuevaFecha)
                || nuevoTipo == null
                || nuevoEstado == null) {
            return false;
        }

        Enfermera enfermera = exigirEnfermera(rut);
        Turno turno = enfermera.buscarTurno(idTurno);
        if (turno == null) {
            throw new TurnoException(
                    "No existe el turno " + idTurno
                    + " para la enfermera indicada.");
        }

        turno.setFecha(nuevaFecha.trim());
        turno.setTipo(nuevoTipo);
        turno.setEstado(nuevoEstado);
        return true;
    }

    /**
     * Busca enfermeras de una especialidad que no poseen un turno activo
     * en la fecha solicitada.
     */
    public List<Enfermera> buscarEnfermerasDisponibles(
            String especialidad, String fecha) {
        List<Enfermera> disponibles = new ArrayList<>();
        if (!esTextoValido(especialidad) || !esTextoValido(fecha)) {
            return disponibles;
        }

        String especialidadBuscada = especialidad.trim();
        String fechaBuscada = fecha.trim();
        for (Enfermera enfermera : mapaEnfermeras.values()) {
            if (enfermera.getEspecialidad() != null
                    && enfermera.getEspecialidad().equalsIgnoreCase(
                            especialidadBuscada)
                    && !enfermera.tieneTurnoActivoEnFecha(fechaBuscada)) {
                disponibles.add(enfermera);
            }
        }
        return disponibles;
    }

    /**
     * Obtiene la carga horaria de una enfermera mediante su RUT.
     *
     * @throws EnfermeraNoEncontradaException cuando el RUT no existe.
     */
    public double calcularCargaHoraria(String rut)
            throws EnfermeraNoEncontradaException {
        Trabajador trabajador = exigirEnfermera(rut);
        return calcularCargaHoraria(trabajador);
    }

    /**
     * Calcula la carga horaria utilizando el contrato polimórfico de
     * Trabajador. Cada subclase decide cómo responder a la operación.
     *
     * @param trabajador trabajador cuya carga se desea consultar.
     * @return carga horaria calculada por el tipo concreto.
     */
    public double calcularCargaHoraria(Trabajador trabajador) {
        if (trabajador == null) {
            throw new IllegalArgumentException(
                    "El trabajador no puede ser nulo.");
        }
        return trabajador.calcularCargaHoraria();
    }

    /**
     * Guarda el estado actual mediante el repositorio configurado.
     *
     * @return true si la persistencia fue exitosa.
     */
    public boolean guardarDatos() {
        return persistencia.guardar(mapaEnfermeras);
    }

    private Enfermera exigirEnfermera(String rut)
            throws EnfermeraNoEncontradaException {
        Enfermera enfermera = buscarEnfermera(rut);
        if (enfermera == null) {
            throw new EnfermeraNoEncontradaException(
                    "No existe una enfermera registrada con el RUT " + rut + ".");
        }
        return enfermera;
    }

    private boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}
