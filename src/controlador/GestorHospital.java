package controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.Enfermera;
import modelo.EstadoTurno;
import modelo.TipoTurno;
import modelo.Turno;
import persistencia.PersistenciaCSV;

/**
 * Gestiona las operaciones relacionadas con las enfermeras y sus turnos.
 * Contiene la lógica principal del sistema y delega la persistencia
 * de los datos a PersistenciaCSV.
 */
public class GestorHospital {

    private Map<String, Enfermera> mapaEnfermeras;
    private PersistenciaCSV persistencia;

    /**
     * Inicializa el gestor y recupera los datos almacenados.
     * Los datos iniciales se crean solamente en la primera ejecución.
     */
    public GestorHospital() {
        persistencia = new PersistenciaCSV();

        if (persistencia.existeArchivo()) {
            mapaEnfermeras = persistencia.cargar();
        } else {
            mapaEnfermeras = new HashMap<>();
            cargarDatosIniciales();
        }
    }

    /**
     * Crea datos iniciales para la primera ejecución del sistema.
     */
    private void cargarDatosIniciales() {
        Enfermera enf1 = new Enfermera("11111111-1", "Ana Rojas", "Urgencias");
        Enfermera enf2 = new Enfermera("22222222-2", "Bárbara Silva", "Pediatría");

        Turno turno1 = new Turno("T001", "10-09-2026", TipoTurno.MANANA,
                EstadoTurno.CONFIRMADO, "08:00", "16:00", "Urgencias", "Turno normal");

        Turno turno2 = new Turno("T002", "11-09-2026", TipoTurno.NOCHE,
                EstadoTurno.PENDIENTE, "22:00", "08:00", "Pediatría", "Turno nocturno");

        enf1.agregarTurno(turno1);
        enf2.agregarTurno(turno2);

        mapaEnfermeras.put(enf1.getRut(), enf1);
        mapaEnfermeras.put(enf2.getRut(), enf2);
    }

    /**
     * Agrega una enfermera si su RUT todavía no está registrado.
     *
     * @param nuevaEnfermera enfermera que se desea registrar.
     * @return true si fue agregada correctamente.
     */
    public boolean agregarEnfermera(Enfermera nuevaEnfermera) {
        if (nuevaEnfermera == null || !esTextoValido(nuevaEnfermera.getRut())) {
            return false;
        }

        String rut = nuevaEnfermera.getRut().trim();

        if (mapaEnfermeras.containsKey(rut)) {
            return false;
        }

        mapaEnfermeras.put(rut, nuevaEnfermera);
        return true;
    }

    /**
     * Sobrecarga que permite registrar una enfermera utilizando sus datos.
     */
    public boolean agregarEnfermera(String rut, String nombre, String especialidad) {
        if (!esTextoValido(rut) || !esTextoValido(nombre) || !esTextoValido(especialidad)) {
            return false;
        }

        Enfermera nuevaEnfermera = new Enfermera(
                rut.trim(), nombre.trim(), especialidad.trim());

        return agregarEnfermera(nuevaEnfermera);
    }

    /**
     * Busca una enfermera mediante su RUT.
     *
     * @return enfermera encontrada o null si no existe.
     */
    public Enfermera buscarEnfermera(String rut) {
        if (!esTextoValido(rut)) {
            return null;
        }

        return mapaEnfermeras.get(rut.trim());
    }

    /**
     * Elimina una enfermera mediante su RUT.
     *
     * @return true si existía y fue eliminada.
     */
    public boolean eliminarEnfermera(String rut) {
        if (!esTextoValido(rut)) {
            return false;
        }

        return mapaEnfermeras.remove(rut.trim()) != null;
    }

    /**
     * Modifica el nombre y especialidad de una enfermera.
     * Los campos vacíos se mantienen sin cambios.
     */
    public boolean modificarEnfermera(String rut, String nuevoNombre, String nuevaEspecialidad) {
        Enfermera enfermera = buscarEnfermera(rut);

        if (enfermera == null) {
            return false;
        }

        if (esTextoValido(nuevoNombre)) {
            enfermera.setNombre(nuevoNombre.trim());
        }

        if (esTextoValido(nuevaEspecialidad)) {
            enfermera.setEspecialidad(nuevaEspecialidad.trim());
        }

        return true;
    }

    /**
     * Busca enfermeras pertenecientes a una especialidad.
     */
    public List<Enfermera> buscarPorEspecialidad(String especialidad) {
        List<Enfermera> resultado = new ArrayList<>();

        if (!esTextoValido(especialidad)) {
            return resultado;
        }

        for (Enfermera enfermera : mapaEnfermeras.values()) {
            if (enfermera.getEspecialidad().equalsIgnoreCase(especialidad.trim())) {
                resultado.add(enfermera);
            }
        }

        return resultado;
    }

    /**
     * Busca un turno específico perteneciente a una enfermera.
     */
    public Turno buscarTurnoDeEnfermera(String rutEnfermera, String idTurno) {
        Enfermera enfermera = buscarEnfermera(rutEnfermera);

        if (enfermera == null || !esTextoValido(idTurno)) {
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
     */
    public boolean eliminarTurno(String rutEnfermera, String idTurno) {
        Enfermera enfermera = buscarEnfermera(rutEnfermera);
        Turno turno = buscarTurnoDeEnfermera(rutEnfermera, idTurno);

        if (enfermera == null || turno == null) {
            return false;
        }

        return enfermera.getTurnosAsignados().remove(turno);
    }

    /**
     * Modifica la fecha, tipo y estado de un turno existente.
     */
    public boolean modificarTurno(String rutEnfermera, String idTurno,
            String nuevaFecha, TipoTurno nuevoTipo, EstadoTurno nuevoEstado) {

        Turno turno = buscarTurnoDeEnfermera(rutEnfermera, idTurno);

        if (turno == null) {
            return false;
        }

        if (esTextoValido(nuevaFecha)) {
            turno.setFecha(nuevaFecha.trim());
        }

        if (nuevoTipo != null) {
            turno.setTipo(nuevoTipo);
        }

        if (nuevoEstado != null) {
            turno.setEstado(nuevoEstado);
        }

        return true;
    }

    /**
     * Busca enfermeras de una especialidad que no tengan un turno
     * asignado en la fecha solicitada.
     */
    public List<Enfermera> buscarEnfermerasDisponibles(
            String especialidad, String fechaSolicitada) {

        List<Enfermera> disponibles = new ArrayList<>();

        if (!esTextoValido(especialidad) || !esTextoValido(fechaSolicitada)) {
            return disponibles;
        }

        for (Enfermera enfermera : mapaEnfermeras.values()) {
            if (enfermera.getEspecialidad().equalsIgnoreCase(especialidad.trim())
                    && estaDisponible(enfermera, fechaSolicitada.trim())) {
                disponibles.add(enfermera);
            }
        }

        return disponibles;
    }

    /**
     * Determina si una enfermera no posee turnos en la fecha indicada.
     */
    private boolean estaDisponible(Enfermera enfermera, String fechaSolicitada) {
        for (Turno turno : enfermera.getTurnosAsignados()) {
            if (turno.getFecha().equalsIgnoreCase(fechaSolicitada)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Guarda el estado actual del sistema.
     */
    public void guardarDatos() {
        persistencia.guardar(mapaEnfermeras);
    }

    /**
     * Comprueba que un texto no sea nulo ni vacío.
     */
    private boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public Map<String, Enfermera> getMapaEnfermeras() {
        return mapaEnfermeras;
    }

    public void setMapaEnfermeras(Map<String, Enfermera> mapaEnfermeras) {
        if (mapaEnfermeras == null) {
            this.mapaEnfermeras = new HashMap<>();
        } else {
            this.mapaEnfermeras = mapaEnfermeras;
        }
    }
}