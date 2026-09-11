package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
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

    // SIA-11: archivo utilizado para la persistencia
    private static final String ARCHIVO_DATOS =
            "datos_hospital.csv";

    // SIA-4: colección principal
    private Map<String, Enfermera> mapaEnfermeras;

    public GestorHospital() {

        mapaEnfermeras = new HashMap<>();

        // SIA-11: cargar datos al iniciar
        if (!cargarDatos()) {
            cargarDatosIniciales();
        }
    }

    // =====================================================
    // DATOS INICIALES
    // =====================================================

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

        mapaEnfermeras.put(
                enf1.getRut(),
                enf1
        );

        mapaEnfermeras.put(
                enf2.getRut(),
                enf2
        );
    }

    // =====================================================
    // SIA-5: SOBRECARGA
    // =====================================================

    public boolean agregarEnfermera(
            Enfermera nuevaEnfermera) {

        if (mapaEnfermeras.containsKey(
                nuevaEnfermera.getRut())) {

            return false;
        }

        mapaEnfermeras.put(
                nuevaEnfermera.getRut(),
                nuevaEnfermera
        );

        return true;
    }

    public boolean agregarEnfermera(
            String rut,
            String nombre,
            String especialidad) {

        Enfermera nueva =
                new Enfermera(
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
    // SIA-8: BUSCAR ENFERMERA
    // =====================================================

    public Enfermera buscarEnfermera(String rut) {

        return mapaEnfermeras.get(rut);
    }

    // =====================================================
    // SIA-8: ELIMINAR ENFERMERA
    // =====================================================

    public boolean eliminarEnfermera(String rut) {

        if (mapaEnfermeras.containsKey(rut)) {

            mapaEnfermeras.remove(rut);

            return true;
        }

        return false;
    }

    // =====================================================
    // SIA-8: MODIFICAR ENFERMERA
    // =====================================================

    public boolean modificarEnfermera(
            String rut,
            String nuevoNombre,
            String nuevaEspecialidad) {

        Enfermera enfermera =
                mapaEnfermeras.get(rut);

        if (enfermera != null) {

            if (nuevoNombre != null
                    && !nuevoNombre.trim().isEmpty()) {

                enfermera.setNombre(nuevoNombre);
            }

            if (nuevaEspecialidad != null
                    && !nuevaEspecialidad.trim().isEmpty()) {

                enfermera.setEspecialidad(
                        nuevaEspecialidad
                );
            }

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

        Enfermera enfermera =
                buscarEnfermera(rutEnfermera);

        if (enfermera != null) {

            for (Turno turno :
                    enfermera.getTurnosAsignados()) {

                if (turno.getIdTurno()
                        .equalsIgnoreCase(idTurno)) {

                    return turno;
                }
            }
        }

        return null;
    }

    // =====================================================
    // SIA-9: FILTRAR POR ESPECIALIDAD
    // =====================================================

    public List<Enfermera> buscarPorEspecialidad(
            String especialidad) {

        List<Enfermera> resultado =
                new ArrayList<>();

        for (Enfermera enfermera :
                mapaEnfermeras.values()) {

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

            if (nuevaFecha != null
                    && !nuevaFecha.trim().isEmpty()) {

                turno.setFecha(nuevaFecha);
            }

            if (nuevoTipo != null) {
                turno.setTipo(nuevoTipo);
            }

            if (nuevoEstado != null) {
                turno.setEstado(nuevoEstado);
            }

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
    // SIA-11: GUARDAR DATOS EN CSV
    // =====================================================

    public void guardarDatos() {

        try (
                PrintWriter escritor =
                        new PrintWriter(
                                new FileWriter(
                                        ARCHIVO_DATOS
                                )
                        )
        ) {

            escritor.println(
                    "rut,nombre,especialidad,idTurno,fecha,"
                    + "tipo,estado,horaInicio,horaFin,sector,"
                    + "observaciones"
            );

            for (Enfermera enfermera :
                    mapaEnfermeras.values()) {

                List<Turno> turnos =
                        enfermera.getTurnosAsignados();

                if (turnos.isEmpty()) {

                    escritor.println(
                            convertirCSV(
                                    enfermera.getRut()
                            )
                            + ","
                            + convertirCSV(
                                    enfermera.getNombre()
                            )
                            + ","
                            + convertirCSV(
                                    enfermera.getEspecialidad()
                            )
                    );

                } else {

                    for (Turno turno : turnos) {

                        escritor.println(
                                convertirCSV(
                                        enfermera.getRut()
                                )
                                + ","
                                + convertirCSV(
                                        enfermera.getNombre()
                                )
                                + ","
                                + convertirCSV(
                                        enfermera.getEspecialidad()
                                )
                                + ","
                                + convertirCSV(
                                        turno.getIdTurno()
                                )
                                + ","
                                + convertirCSV(
                                        turno.getFecha()
                                )
                                + ","
                                + convertirCSV(
                                        turno.getTipo().name()
                                )
                                + ","
                                + convertirCSV(
                                        turno.getEstado().name()
                                )
                                + ","
                                + convertirCSV(
                                        turno.getHoraInicio()
                                )
                                + ","
                                + convertirCSV(
                                        turno.getHoraFin()
                                )
                                + ","
                                + convertirCSV(
                                        turno.getSector()
                                )
                                + ","
                                + convertirCSV(
                                        turno.getObservaciones()
                                )
                        );
                    }
                }
            }

            System.out.println(
                    "[SISTEMA] Datos guardados en "
                    + ARCHIVO_DATOS
            );

        } catch (IOException e) {

            System.err.println(
                    "[ERROR] No se pudieron guardar "
                    + "los datos: "
                    + e.getMessage()
            );
        }
    }

    // =====================================================
    // SIA-11: CARGAR DATOS DESDE CSV
    // =====================================================

    public boolean cargarDatos() {

        File archivo =
                new File(ARCHIVO_DATOS);

        if (!archivo.exists()) {
            return false;
        }

        try (
                BufferedReader lector =
                        new BufferedReader(
                                new FileReader(archivo)
                        )
        ) {

            // Saltar encabezado
            lector.readLine();

            String linea;

            while ((linea = lector.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos =
                        separarCSV(linea);

                if (datos.length < 3) {
                    continue;
                }

                String rut =
                        datos[0];

                String nombre =
                        datos[1];

                String especialidad =
                        datos[2];

                Enfermera enfermera =
                        mapaEnfermeras.get(rut);

                if (enfermera == null) {

                    enfermera =
                            new Enfermera(
                                    rut,
                                    nombre,
                                    especialidad
                            );

                    mapaEnfermeras.put(
                            rut,
                            enfermera
                    );
                }

                if (datos.length < 11
                        || datos[3].trim().isEmpty()) {

                    continue;
                }

                Turno turno =
                        new Turno(
                                datos[3],
                                datos[4],
                                TipoTurno.valueOf(
                                        datos[5]
                                ),
                                EstadoTurno.valueOf(
                                        datos[6]
                                ),
                                datos[7],
                                datos[8],
                                datos[9],
                                datos[10]
                        );

                enfermera.agregarTurno(turno);
            }

            System.out.println(
                    "[SISTEMA] Datos cargados desde "
                    + ARCHIVO_DATOS
            );

            return true;

        } catch (
                IOException
                | IllegalArgumentException e
        ) {

            System.err.println(
                    "[ERROR] No se pudieron cargar "
                    + "los datos: "
                    + e.getMessage()
            );

            mapaEnfermeras.clear();

            return false;
        }
    }

    // =====================================================
    // UTILIDAD PARA CSV
    // =====================================================

    private String convertirCSV(String texto) {

        if (texto == null) {
            return "";
        }

        return "\""
                + texto.replace(
                        "\"",
                        "\"\""
                )
                + "\"";
    }

    private String[] separarCSV(String linea) {

        List<String> campos =
                new ArrayList<>();

        StringBuilder campo =
                new StringBuilder();

        boolean dentroDeComillas = false;

        for (int i = 0;
                i < linea.length();
                i++) {

            char caracter =
                    linea.charAt(i);

            if (caracter == '"') {

                if (dentroDeComillas
                        && i + 1 < linea.length()
                        && linea.charAt(i + 1) == '"') {

                    campo.append('"');
                    i++;

                } else {

                    dentroDeComillas =
                            !dentroDeComillas;
                }

            } else if (
                    caracter == ','
                    && !dentroDeComillas) {

                campos.add(
                        campo.toString()
                );

                campo.setLength(0);

            } else {

                campo.append(caracter);
            }
        }

        campos.add(
                campo.toString()
        );

        return campos.toArray(
                new String[0]
        );
    }
}

