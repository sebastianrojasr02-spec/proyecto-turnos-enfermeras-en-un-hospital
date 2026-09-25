package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.Enfermera;
import modelo.EstadoTurno;
import modelo.TipoTurno;
import modelo.Turno;

/**
 * Gestiona la carga y almacenamiento de los datos del hospital
 * utilizando un archivo CSV.
 */
public class PersistenciaCSV {

    private static final String ARCHIVO_DATOS = "datos_hospital.csv";

    /**
     * Guarda las enfermeras y sus turnos en el archivo CSV.
     *
     * @param enfermeras mapa de enfermeras que se desea almacenar.
     */
    public void guardar(Map<String, Enfermera> enfermeras) {
        if (enfermeras == null) {
            return;
        }

        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_DATOS))) {
            escritor.println("rut,nombre,especialidad,idTurno,fecha,tipo,estado,"
                    + "horaInicio,horaFin,sector,observaciones");

            for (Enfermera enfermera : enfermeras.values()) {
                guardarEnfermera(escritor, enfermera);
            }

            System.out.println("[SISTEMA] Datos guardados correctamente.");
        } catch (IOException e) {
            System.err.println("[ERROR] No se pudieron guardar los datos: " + e.getMessage());
        }
    }

    /**
     * Carga las enfermeras y sus turnos almacenados en el archivo CSV.
     *
     * @return mapa con las enfermeras recuperadas desde el archivo.
     */
    public Map<String, Enfermera> cargar() {
        Map<String, Enfermera> enfermeras = new HashMap<>();
        File archivo = new File(ARCHIVO_DATOS);

        if (!archivo.exists()) {
            return enfermeras;
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            lector.readLine();

            String linea;
            while ((linea = lector.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    procesarLinea(linea, enfermeras);
                }
            }

            System.out.println("[SISTEMA] Datos cargados correctamente.");
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("[ERROR] No se pudieron cargar los datos: " + e.getMessage());
            enfermeras.clear();
        }

        return enfermeras;
    }

    /**
     * Indica si existe un archivo de datos previamente creado.
     *
     * @return true si el archivo existe.
     */
    public boolean existeArchivo() {
        return new File(ARCHIVO_DATOS).exists();
    }

    /**
     * Guarda una enfermera. Si posee turnos, genera una fila por cada turno.
     */
    private void guardarEnfermera(PrintWriter escritor, Enfermera enfermera) {
        List<Turno> turnos = enfermera.getTurnosAsignados();

        if (turnos == null || turnos.isEmpty()) {
            escritor.println(crearDatosEnfermera(enfermera));
            return;
        }

        for (Turno turno : turnos) {
            escritor.println(crearDatosTurno(enfermera, turno));
        }
    }

    /**
     * Procesa una fila del CSV y crea los objetos correspondientes.
     */
    private void procesarLinea(String linea, Map<String, Enfermera> enfermeras) {
        String[] datos = separarCSV(linea);

        if (datos.length < 3) {
            return;
        }

        String rut = datos[0];
        String nombre = datos[1];
        String especialidad = datos[2];

        Enfermera enfermera = enfermeras.get(rut);

        if (enfermera == null) {
            enfermera = new Enfermera(rut, nombre, especialidad);
            enfermeras.put(rut, enfermera);
        }

        if (datos.length >= 11 && !datos[3].trim().isEmpty()) {
            Turno turno = crearTurno(datos);
            enfermera.agregarTurno(turno);
        }
    }

    /**
     * Crea un turno utilizando los campos obtenidos desde una fila CSV.
     */
    private Turno crearTurno(String[] datos) {
        return new Turno(
                datos[3],
                datos[4],
                TipoTurno.valueOf(datos[5]),
                EstadoTurno.valueOf(datos[6]),
                datos[7],
                datos[8],
                datos[9],
                datos[10]
        );
    }

    /**
     * Genera la representación CSV de una enfermera sin turnos.
     */
    private String crearDatosEnfermera(Enfermera enfermera) {
        return convertirCSV(enfermera.getRut()) + ","
                + convertirCSV(enfermera.getNombre()) + ","
                + convertirCSV(enfermera.getEspecialidad())
                + ",,,,,,,,";
    }

    /**
     * Genera la representación CSV de una enfermera junto con uno de sus turnos.
     */
    private String crearDatosTurno(Enfermera enfermera, Turno turno) {
        return convertirCSV(enfermera.getRut()) + ","
                + convertirCSV(enfermera.getNombre()) + ","
                + convertirCSV(enfermera.getEspecialidad()) + ","
                + convertirCSV(turno.getIdTurno()) + ","
                + convertirCSV(turno.getFecha()) + ","
                + convertirCSV(turno.getTipo().name()) + ","
                + convertirCSV(turno.getEstado().name()) + ","
                + convertirCSV(turno.getHoraInicio()) + ","
                + convertirCSV(turno.getHoraFin()) + ","
                + convertirCSV(turno.getSector()) + ","
                + convertirCSV(turno.getObservaciones());
    }

    /**
     * Escapa un texto para almacenarlo correctamente en formato CSV.
     */
    private String convertirCSV(String texto) {
        if (texto == null) {
            return "";
        }

        return "\"" + texto.replace("\"", "\"\"") + "\"";
    }

    /**
     * Separa una fila CSV respetando campos encerrados entre comillas.
     */
    private String[] separarCSV(String linea) {
        List<String> campos = new ArrayList<>();
        StringBuilder campo = new StringBuilder();
        boolean dentroDeComillas = false;

        for (int i = 0; i < linea.length(); i++) {
            char caracter = linea.charAt(i);

            if (caracter == '"') {
                if (dentroDeComillas && i + 1 < linea.length()
                        && linea.charAt(i + 1) == '"') {
                    campo.append('"');
                    i++;
                } else {
                    dentroDeComillas = !dentroDeComillas;
                }
            } else if (caracter == ',' && !dentroDeComillas) {
                campos.add(campo.toString());
                campo.setLength(0);
            } else {
                campo.append(caracter);
            }
        }

        campos.add(campo.toString());
        return campos.toArray(new String[0]);
    }
}