package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import modelo.Enfermera;
import modelo.EstadoTurno;
import modelo.TipoTurno;
import modelo.Turno;

/**
 * Implementa la persistencia del hospital mediante un archivo CSV.
 * Esta clase se limita a transformar datos entre objetos y registros CSV;
 * no contiene reglas de negocio ni mensajes de interfaz.
 */
public class PersistenciaCSV implements RepositorioHospital {

    private static final String ARCHIVO_DATOS = "datos_hospital.csv";

    /**
     * Guarda todas las enfermeras y sus turnos en el archivo CSV.
     *
     * @param enfermeras mapa de enfermeras que se desea almacenar.
     * @return true si el archivo pudo escribirse completo.
     */
    @Override
    public boolean guardar(Map<String, Enfermera> enfermeras) {
        if (enfermeras == null) {
            return false;
        }

        try (PrintWriter escritor = new PrintWriter(
                new FileWriter(ARCHIVO_DATOS))) {
            escritor.println("rut,nombre,especialidad,idTurno,fecha,tipo,estado,"
                    + "horaInicio,horaFin,sector,observaciones");

            for (Enfermera enfermera : enfermeras.values()) {
                guardarEnfermera(escritor, enfermera);
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Carga el estado almacenado en el CSV y reconstruye el modelo.
     *
     * @return mapa con las enfermeras recuperadas; vacío cuando no existe
     * el archivo o el contenido no puede reconstruirse.
     */
    @Override
    public Map<String, Enfermera> cargar() {
        Map<String, Enfermera> enfermeras = new LinkedHashMap<>();
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
        } catch (IOException | IllegalArgumentException e) {
            enfermeras.clear();
        }

        return enfermeras;
    }

    /**
     * Indica si existe un archivo de datos persistido.
     *
     * @return true cuando el CSV está presente.
     */
    public boolean existeArchivo() {
        return new File(ARCHIVO_DATOS).exists();
    }

    private void guardarEnfermera(PrintWriter escritor, Enfermera enfermera) {
        List<Turno> turnos = enfermera.getTurnosAsignados();

        if (turnos.isEmpty()) {
            escritor.println(crearDatosEnfermera(enfermera));
            return;
        }

        for (Turno turno : turnos) {
            escritor.println(crearDatosTurno(enfermera, turno));
        }
    }

    private void procesarLinea(String linea, Map<String, Enfermera> enfermeras) {
        String[] datos = separarCSV(linea);

        if (datos.length < 3) {
            return;
        }

        String rut = datos[0].trim();
        String nombre = datos[1].trim();
        String especialidad = datos[2].trim();
        Enfermera enfermera = enfermeras.get(rut);

        if (enfermera == null) {
            enfermera = new Enfermera(rut, nombre, especialidad);
            enfermeras.put(rut, enfermera);
        }

        if (datos.length >= 11 && !datos[3].trim().isEmpty()) {
            enfermera.agregarTurno(crearTurno(datos));
        }
    }

    private Turno crearTurno(String[] datos) {
        TipoTurno tipo = TipoTurno.valueOf(datos[5].trim());
        Turno turno = new Turno(
                datos[3].trim(),
                datos[4].trim(),
                tipo,
                EstadoTurno.valueOf(datos[6].trim()),
                datos[7].trim(),
                datos[8].trim(),
                datos[9].trim(),
                datos[10].trim()
        );

        // Compatibilidad con registros creados por versiones anteriores,
        // donde los turnos nuevos podían guardarse como 00:00 - 00:00.
        if ("00:00".equals(turno.getHoraInicio())
                && "00:00".equals(turno.getHoraFin())) {
            turno.setTipo(tipo);
        }

        return turno;
    }

    private String crearDatosEnfermera(Enfermera enfermera) {
        return convertirCSV(enfermera.getRut()) + ","
                + convertirCSV(enfermera.getNombre()) + ","
                + convertirCSV(enfermera.getEspecialidad()) + ",,,,,,,,";
    }

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

    private String convertirCSV(String texto) {
        if (texto == null) {
            return "";
        }

        return "\"" + texto.replace("\"", "\"\"") + "\"";
    }

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
