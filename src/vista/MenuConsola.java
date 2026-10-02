package vista;

import controlador.ControladorConsola;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import modelo.Enfermera;
import modelo.Turno;

/**
 * Vista de consola. Su responsabilidad es leer entradas, presentar opciones
 * y mostrar resultados; las reglas del negocio se delegan al controlador.
 */
public class MenuConsola {

    private final ControladorConsola controlador;
    private final Scanner scanner;

    public MenuConsola(ControladorConsola controlador, Scanner scanner) {
        this.controlador = controlador;
        this.scanner = scanner;
    }

    /**
     * Ejecuta el ciclo principal del menú hasta seleccionar salir.
     */
    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            procesarOpcion(opcion);

            if (opcion != 0) {
                System.out.println();
                System.out.print("Presione Enter para volver al menú...");
                scanner.nextLine();
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("======================================");
        System.out.println("      GESTIÓN HOSPITALARIA");
        System.out.println("======================================");
        System.out.println("1. Agregar enfermera");
        System.out.println("2. Mostrar enfermeras");
        System.out.println("3. Buscar enfermera");
        System.out.println("4. Modificar enfermera");
        System.out.println("5. Eliminar enfermera");
        System.out.println("6. Asignar turno");
        System.out.println("7. Mostrar turnos");
        System.out.println("8. Buscar turno");
        System.out.println("9. Modificar turno");
        System.out.println("10. Eliminar turno");
        System.out.println("11. Buscar por especialidad");
        System.out.println("12. Buscar enfermeras disponibles");
        System.out.println("13. Consultar carga horaria");
        System.out.println("0. Guardar y salir");
        System.out.println("======================================");
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                agregarEnfermera();
                break;
            case 2:
                mostrarEnfermeras();
                break;
            case 3:
                buscarEnfermera();
                break;
            case 4:
                modificarEnfermera();
                break;
            case 5:
                eliminarEnfermera();
                break;
            case 6:
                asignarTurno();
                break;
            case 7:
                mostrarTurnos();
                break;
            case 8:
                buscarTurno();
                break;
            case 9:
                modificarTurno();
                break;
            case 10:
                eliminarTurno();
                break;
            case 11:
                buscarPorEspecialidad();
                break;
            case 12:
                buscarDisponibles();
                break;
            case 13:
                mostrarCargaHoraria();
                break;
            case 0:
                guardarYSalir();
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private void agregarEnfermera() {
        System.out.println("\n--- AGREGAR ENFERMERA ---");
        String rut = leerTexto("RUT: ");
        String nombre = leerTexto("Nombre: ");
        String especialidad = leerTexto("Especialidad: ");

        if (controlador.agregarEnfermera(rut, nombre, especialidad)) {
            System.out.println("Enfermera agregada correctamente.");
        } else {
            System.out.println("No se pudo agregar la enfermera. Verifique los datos o el RUT.");
        }
    }

    private void mostrarEnfermeras() {
        System.out.println("\n--- ENFERMERAS REGISTRADAS ---");
        List<Enfermera> enfermeras = controlador.obtenerEnfermeras();
        if (enfermeras.isEmpty()) {
            System.out.println("No existen enfermeras registradas.");
            return;
        }
        enfermeras.forEach(System.out::println);
    }

    private void buscarEnfermera() {
        System.out.println("\n--- BUSCAR ENFERMERA ---");
        Enfermera enfermera = controlador.buscarEnfermera(leerTexto("RUT: "));
        System.out.println(enfermera == null
                ? "No se encontró la enfermera."
                : "Enfermera encontrada:\n" + enfermera);
    }

    private void modificarEnfermera() {
        System.out.println("\n--- MODIFICAR ENFERMERA ---");
        String rut = leerTexto("RUT: ");
        Enfermera enfermera = controlador.buscarEnfermera(rut);
        if (enfermera == null) {
            System.out.println("No se encontró la enfermera.");
            return;
        }
        String nombre = leerTexto("Nuevo nombre: ");
        String especialidad = leerTexto("Nueva especialidad: ");
        System.out.println(controlador.modificarEnfermera(rut, nombre, especialidad)
                ? "Enfermera modificada correctamente."
                : "No se pudo modificar la enfermera.");
    }

    private void eliminarEnfermera() {
        System.out.println("\n--- ELIMINAR ENFERMERA ---");
        String rut = leerTexto("RUT: ");
        System.out.println(controlador.eliminarEnfermera(rut)
                ? "Enfermera eliminada correctamente."
                : "No se encontró la enfermera.");
    }

    private void asignarTurno() {
        System.out.println("\n--- ASIGNAR TURNO ---");
        String rut = leerTexto("RUT de la enfermera: ");
        String idTurno = leerTexto("ID del turno: ");
        String fecha = leerTexto("Fecha: ");
        mostrarTiposTurno();
        String tipo = leerTexto("Tipo: ");

        if (controlador.agregarTurno(rut, idTurno, fecha, tipo)) {
            System.out.println("Turno asignado correctamente.");
        } else {
            mostrarError("No se pudo asignar el turno.");
        }
    }

    private void mostrarTurnos() {
        System.out.println("\n--- TURNOS REGISTRADOS ---");
        Map<Enfermera, List<Turno>> turnos = controlador.obtenerTurnosPorEnfermera();
        if (turnos.isEmpty()) {
            System.out.println("No existen turnos registrados.");
            return;
        }

        turnos.forEach((enfermera, lista) -> {
            System.out.println(enfermera);
            lista.forEach(turno -> System.out.println("  " + turno));
        });
    }

    private void buscarTurno() {
        System.out.println("\n--- BUSCAR TURNO ---");
        String rut = leerTexto("RUT de la enfermera: ");
        String idTurno = leerTexto("ID del turno: ");
        Turno turno = controlador.buscarTurno(rut, idTurno);
        System.out.println(turno == null
                ? "No se encontró el turno."
                : "Turno encontrado:\n" + turno);
    }

    private void modificarTurno() {
        System.out.println("\n--- MODIFICAR TURNO ---");
        String rut = leerTexto("RUT de la enfermera: ");
        String idTurno = leerTexto("ID del turno: ");
        Turno turno = controlador.buscarTurno(rut, idTurno);
        if (turno == null) {
            System.out.println("No se encontró el turno.");
            return;
        }
        String fecha = leerTexto("Nueva fecha: ");
        mostrarTiposTurno();
        String tipo = leerTexto("Nuevo tipo: ");
        mostrarEstadosTurno();
        String estado = leerTexto("Nuevo estado: ");

        if (controlador.modificarTurno(rut, idTurno, fecha, tipo, estado)) {
            System.out.println("Turno modificado correctamente.");
        } else {
            mostrarError("No se pudo modificar el turno.");
        }
    }

    private void eliminarTurno() {
        System.out.println("\n--- ELIMINAR TURNO ---");
        String rut = leerTexto("RUT de la enfermera: ");
        String idTurno = leerTexto("ID del turno: ");
        if (controlador.eliminarTurno(rut, idTurno)) {
            System.out.println("Turno eliminado correctamente.");
        } else {
            mostrarError("No se pudo eliminar el turno.");
        }
    }

    private void buscarPorEspecialidad() {
        System.out.println("\n--- BUSCAR POR ESPECIALIDAD ---");
        List<Enfermera> resultado = controlador.buscarPorEspecialidad(
                leerTexto("Especialidad: "));
        if (resultado.isEmpty()) {
            System.out.println("No se encontraron enfermeras.");
            return;
        }
        resultado.forEach(System.out::println);
    }

    private void buscarDisponibles() {
        System.out.println("\n--- ENFERMERAS DISPONIBLES ---");
        String especialidad = leerTexto("Especialidad: ");
        String fecha = leerTexto("Fecha: ");
        List<Enfermera> disponibles = controlador.buscarEnfermerasDisponibles(
                especialidad, fecha);
        if (disponibles.isEmpty()) {
            System.out.println("No hay enfermeras disponibles.");
            return;
        }
        disponibles.forEach(System.out::println);
    }

    private void mostrarCargaHoraria() {
        System.out.println("\n--- CARGA HORARIA ---");
        String rut = leerTexto("RUT de la enfermera: ");
        double horas = controlador.calcularCargaHoraria(rut);
        if (horas < 0) {
            mostrarError("No se pudo calcular la carga horaria.");
            return;
        }
        System.out.printf("Carga horaria de la enfermera: %.1f horas%n", horas);
    }

    private void guardarYSalir() {
        System.out.println(controlador.guardarDatos()
                ? "Datos guardados. Programa finalizado."
                : "No se pudieron guardar los datos.");
    }

    private void mostrarTiposTurno() {
        System.out.println("Tipos disponibles: MANANA, TARDE, NOCHE");
    }

    private void mostrarEstadosTurno() {
        System.out.println(
                "Estados disponibles: PENDIENTE, CONFIRMADO, CANCELADO, COMPLETADO");
    }

    private void mostrarError(String mensaje) {
        if (!controlador.getUltimoError().isEmpty()) {
            System.out.println("Error: " + controlador.getUltimoError());
        } else {
            System.out.println(mensaje);
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }
}
