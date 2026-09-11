package controlador;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.Enfermera;
import modelo.Turno;
import modelo.TipoTurno;
import modelo.EstadoTurno;

public class GestorHospital implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String ARCHIVO_DATOS = "datos_hospital.dat";

    // SIA-4: Colección principal
    private Map<String, Enfermera> mapaEnfermeras;

    public GestorHospital() {
        this.mapaEnfermeras = new HashMap<>();
        if (!cargarDatos()) {
            cargarDatosIniciales();
        }
    }

    // Datos iniciales
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
        
        guardarDatos();
    }

    // =====================================================
    // SIA-5: SOBRECARGA
    // =====================================================

    public boolean agregarEnfermera(Enfermera nuevaEnfermera) {

        if (mapaEnfermeras.containsKey(nuevaEnfermera.getRut())) {
            return false;
        }

        mapaEnfermeras.put(
                nuevaEnfermera.getRut(),
                nuevaEnfermera
        );

        guardarDatos();
        return true;
    }

    public boolean agregarEnfermera(
            String rut,
            String nombre,
            String especialidad) {

        Enfermera nueva = new Enfermera(
                rut,
                nombre,
                especialidad
        );

        return agregarEnfermera(nueva);
    }

    // =====================================================
    // MOSTRAR
    // =====================================================

    public Map<String, Enfermera> getMapaEnfermeras() {
        return mapaEnfermeras;
    }

    // =====================================================
    // SIA-8: BUSCAR
    // =====================================================

    public Enfermera buscarEnfermera(String rut) {
        return mapaEnfermeras.get(rut);
    }

    // =====================================================
    // SIA-8: ELIMINAR
    // =====================================================

    public boolean eliminarEnfermera(String rut) {

        if (mapaEnfermeras.containsKey(rut)) {
            mapaEnfermeras.remove(rut);
            guardarDatos();
            return true;
        }

        return false;
    }

    // =====================================================
    // SIA-8: MODIFICAR
    // =====================================================

    public boolean modificarEnfermera(
            String rut,
            String nuevoNombre,
            String nuevaEspecialidad) {

        Enfermera enfermera = mapaEnfermeras.get(rut);

        if (enfermera != null) {

            if (nuevoNombre != null &&
                !nuevoNombre.trim().isEmpty()) {

                enfermera.setNombre(nuevoNombre);
            }

            if (nuevaEspecialidad != null &&
                !nuevaEspecialidad.trim().isEmpty()) {

                enfermera.setEspecialidad(nuevaEspecialidad);
            }

            guardarDatos();
            return true;
        }

        return false;
    }

    // =====================================================
    // TURNOS
    // =====================================================

    public Turno buscarTurnoDeEnfermera(
            String rutEnfermera,
            String idTurno) {

        Enfermera enfermera = buscarEnfermera(rutEnfermera);

        if (enfermera != null) {

            for (Turno turno : enfermera.getTurnosAsignados()) {

                if (turno.getIdTurno()
                        .equalsIgnoreCase(idTurno)) {

                    return turno;
                }
            }
        }

        return null;
    }

    // =====================================================
    // SIA-4 / SIA-9: FILTRAR POR ESPECIALIDAD
    // =====================================================

    public List<Enfermera> buscarPorEspecialidad(
            String especialidad) {

        List<Enfermera> resultado = new ArrayList<>();

        for (Enfermera enfermera : mapaEnfermeras.values()) {

            if (enfermera.getEspecialidad()
                    .equalsIgnoreCase(especialidad)) {

                resultado.add(enfermera);
            }
        }

        return resultado;
    }

    // =====================================================
    // ELIMINAR TURNO
    // =====================================================

    public boolean eliminarTurno(
            String rutEnfermera,
            String idTurno) {

        Enfermera enfermera =
                buscarEnfermera(rutEnfermera);

        if (enfermera != null) {

            Turno turno =
                    buscarTurnoDeEnfermera(
                            rutEnfermera,
                            idTurno
                    );

            if (turno != null) {

                enfermera.getTurnosAsignados()
                        .remove(turno);

                guardarDatos();
                return true;
            }
        }

        return false;
    }

    // =====================================================
    // MODIFICAR TURNO
    // =====================================================

    public boolean modificarTurno(
            String rutEnfermera,
            String idTurno,
            String nuevaFecha,
            TipoTurno nuevoTipo,
            EstadoTurno nuevoEstado) {

        Turno turno =
                buscarTurnoDeEnfermera(
                        rutEnfermera,
                        idTurno
                );

        if (turno != null) {

            if (nuevaFecha != null &&
                !nuevaFecha.trim().isEmpty()) {

                turno.setFecha(nuevaFecha);
            }

            if (nuevoTipo != null) {
                turno.setTipo(nuevoTipo);
            }

            if (nuevoEstado != null) {
                turno.setEstado(nuevoEstado);
            }

            guardarDatos();
            return true;
        }

        return false;
    }

    // =====================================================
    // SIA-9: ENFERMERAS DISPONIBLES
    // =====================================================

    public List<Enfermera> buscarEnfermerasDisponibles(
            String especialidad,
            String fechaSolicitada) {

        List<Enfermera> disponibles =
                new ArrayList<>();

        for (Enfermera enfermera :
                mapaEnfermeras.values()) {

            if (enfermera.getEspecialidad()
                    .equalsIgnoreCase(especialidad)) {

                boolean tieneTurno = false;

                for (Turno turno :
                        enfermera.getTurnosAsignados()) {

                    if (turno.getFecha()
                            .equals(fechaSolicitada)) {

                        tieneTurno = true;
                        break;
                    }
                }

                if (!tieneTurno) {
                    disponibles.add(enfermera);
                }
            }
        }

        return disponibles;
    }

    // =====================================================
    // PERSISTENCIA DE DATOS (SERIALIZACIÓN)
    // =====================================================

    public void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
            oos.writeObject(mapaEnfermeras);
            System.out.println("[SISTEMA] Datos guardados con éxito en " + ARCHIVO_DATOS);
        } catch (IOException e) {
            System.err.println("[ERROR] No se pudieron guardar los datos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public boolean cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);
        if (!archivo.exists()) {
            return false;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_DATOS))) {
            mapaEnfermeras = (Map<String, Enfermera>) ois.readObject();
            System.out.println("[SISTEMA] Datos cargados con éxito desde " + ARCHIVO_DATOS);
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[ERROR] Error al cargar archivo de datos: " + e.getMessage());
            return false;
        }
    }
}