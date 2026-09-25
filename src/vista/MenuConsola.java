package vista;

import controlador.ControladorConsola;
import java.util.List;
import java.util.Scanner;
import modelo.Enfermera;
import modelo.Turno;

/**
 * Interfaz de consola del sistema hospitalario.
 * Se encarga de recibir datos del usuario y mostrar resultados.
 */
public class MenuConsola {

    private ControladorConsola controlador;
    private Scanner scanner;

    public MenuConsola(ControladorConsola controlador, Scanner scanner) {
    this.controlador = controlador;
    this.scanner = scanner;
    }

    /**
     * Inicia el menú principal de la aplicación.
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

    /**
     * Muestra las opciones disponibles.
     */
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
        System.out.println("0. Guardar y salir");
        System.out.println("======================================");
    }

    /**
     * Ejecuta la operación seleccionada por el usuario.
     */
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
            case 0:
                controlador.guardarDatos();
                System.out.println("Datos guardados. Programa finalizado.");
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private void agregarEnfermera() {
        System.out.println();
        System.out.println("--- AGREGAR ENFERMERA ---");

        String rut = leerTexto("RUT: ");
        String nombre = leerTexto("Nombre: ");
        String especialidad = leerTexto("Especialidad: ");

        boolean agregada = controlador.agregarEnfermera(
                rut,
                nombre,
                especialidad
        );

        if (agregada) {
            System.out.println("Enfermera agregada correctamente.");
        } else {
            System.out.println("No se pudo agregar la enfermera.");
            System.out.println("Verifique los datos o si el RUT ya existe.");
        }
    }

    private void mostrarEnfermeras() {
        System.out.println();
        System.out.println("--- ENFERMERAS REGISTRADAS ---");

        List<Enfermera> enfermeras = controlador.obtenerEnfermeras();

        if (enfermeras.isEmpty()) {
            System.out.println("No existen enfermeras registradas.");
            return;
        }

        for (Enfermera enfermera : enfermeras) {
            System.out.println(enfermera.obtenerIdentificacion());
        }
    }

    private void buscarEnfermera() {
        System.out.println();
        System.out.println("--- BUSCAR ENFERMERA ---");

        String rut = leerTexto("RUT: ");
        Enfermera enfermera = controlador.buscarEnfermera(rut);

        if (enfermera == null) {
            System.out.println("No se encontró la enfermera.");
            return;
        }

        System.out.println("Enfermera encontrada:");
        System.out.println(enfermera.obtenerIdentificacion());
    }

    private void modificarEnfermera() {
        System.out.println();
        System.out.println("--- MODIFICAR ENFERMERA ---");

        String rut = leerTexto("RUT: ");

        Enfermera enfermera = controlador.buscarEnfermera(rut);

        if (enfermera == null) {
            System.out.println("No se encontró la enfermera.");
            return;
        }

        System.out.println("Datos actuales:");
        System.out.println(enfermera.obtenerIdentificacion());

        String nombre = leerTexto("Nuevo nombre: ");
        String especialidad = leerTexto("Nueva especialidad: ");

        boolean modificada = controlador.modificarEnfermera(
                rut,
                nombre,
                especialidad
        );

        if (modificada) {
            System.out.println("Enfermera modificada correctamente.");
        } else {
            System.out.println("No se pudo modificar la enfermera.");
        }
    }

    private void eliminarEnfermera() {
        System.out.println();
        System.out.println("--- ELIMINAR ENFERMERA ---");

        String rut = leerTexto("RUT: ");

        if (controlador.eliminarEnfermera(rut)) {
            System.out.println("Enfermera eliminada correctamente.");
        } else {
            System.out.println("No se encontró la enfermera.");
        }
    }

    private void asignarTurno() {
        System.out.println();
        System.out.println("--- ASIGNAR TURNO ---");

        String rut = leerTexto("RUT de la enfermera: ");
        String idTurno = leerTexto("ID del turno: ");
        String fecha = leerTexto("Fecha: ");

        mostrarTiposTurno();
        String tipo = leerTexto("Tipo: ");

        boolean asignado = controlador.agregarTurno(
                rut,
                idTurno,
                fecha,
                tipo
        );

        if (asignado) {
            System.out.println("Turno asignado correctamente.");
        } else {
            System.out.println("No se pudo asignar el turno.");
            System.out.println("Verifique la enfermera, el ID y el tipo ingresado.");
        }
    }

    private void mostrarTurnos() {
        System.out.println();
        System.out.println("--- TURNOS REGISTRADOS ---");

        List<Enfermera> enfermeras = controlador.obtenerEnfermeras();
        boolean existenTurnos = false;

        for (Enfermera enfermera : enfermeras) {
            if (!enfermera.getTurnosAsignados().isEmpty()) {
                System.out.println();
                System.out.println(enfermera.obtenerIdentificacion());

                for (Turno turno : enfermera.getTurnosAsignados()) {
                    System.out.println("  " + turno);
                    existenTurnos = true;
                }
            }
        }

        if (!existenTurnos) {
            System.out.println("No existen turnos registrados.");
        }
    }

    private void buscarTurno() {
        System.out.println();
        System.out.println("--- BUSCAR TURNO ---");

        String rut = leerTexto("RUT de la enfermera: ");
        String idTurno = leerTexto("ID del turno: ");

        Turno turno = controlador.buscarTurno(rut, idTurno);

        if (turno != null) {
            System.out.println("Turno encontrado:");
            System.out.println(turno);
        } else {
            System.out.println("No se encontró el turno.");
        }
    }

    private void modificarTurno() {
        System.out.println();
        System.out.println("--- MODIFICAR TURNO ---");

        String rut = leerTexto("RUT de la enfermera: ");
        String idTurno = leerTexto("ID del turno: ");

        Turno turno = controlador.buscarTurno(rut, idTurno);

        if (turno == null) {
            System.out.println("No se encontró el turno.");
            return;
        }

        System.out.println("Turno actual:");
        System.out.println(turno);

        String fecha = leerTexto("Nueva fecha: ");

        mostrarTiposTurno();
        String tipo = leerTexto("Nuevo tipo: ");

        mostrarEstadosTurno();
        String estado = leerTexto("Nuevo estado: ");

        boolean modificado = controlador.modificarTurno(
                rut,
                idTurno,
                fecha,
                tipo,
                estado
        );

        if (modificado) {
            System.out.println("Turno modificado correctamente.");
        } else {
            System.out.println("No se pudo modificar el turno.");
            System.out.println("Verifique el tipo y estado ingresados.");
        }
    }

    private void eliminarTurno() {
        System.out.println();
        System.out.println("--- ELIMINAR TURNO ---");

        String rut = leerTexto("RUT de la enfermera: ");
        String idTurno = leerTexto("ID del turno: ");

        if (controlador.eliminarTurno(rut, idTurno)) {
            System.out.println("Turno eliminado correctamente.");
        } else {
            System.out.println("No se encontró el turno.");
        }
    }

    private void buscarPorEspecialidad() {
        System.out.println();
        System.out.println("--- BUSCAR POR ESPECIALIDAD ---");

        String especialidad = leerTexto("Especialidad: ");

        List<Enfermera> resultado =
                controlador.buscarPorEspecialidad(especialidad);

        if (resultado.isEmpty()) {
            System.out.println("No se encontraron enfermeras.");
            return;
        }

        for (Enfermera enfermera : resultado) {
            System.out.println(enfermera.obtenerIdentificacion());
        }
    }

    private void buscarDisponibles() {
        System.out.println();
        System.out.println("--- ENFERMERAS DISPONIBLES ---");

        String especialidad = leerTexto("Especialidad: ");
        String fecha = leerTexto("Fecha: ");

        List<Enfermera> disponibles =
                controlador.buscarEnfermerasDisponibles(
                        especialidad,
                        fecha
                );

        if (disponibles.isEmpty()) {
            System.out.println("No hay enfermeras disponibles.");
            return;
        }

        for (Enfermera enfermera : disponibles) {
            System.out.println(enfermera.obtenerIdentificacion());
        }
    }

    private void mostrarTiposTurno() {
        System.out.println("Tipos disponibles:");
        System.out.println("- MANANA");
        System.out.println("- TARDE");
        System.out.println("- NOCHE");
    }

    private void mostrarEstadosTurno() {
        System.out.println("Estados disponibles:");
        System.out.println("- PENDIENTE");
        System.out.println("- CONFIRMADO");
        System.out.println("- CANCELADO");
        System.out.println("- COMPLETADO");
    }

    /**
     * Lee una línea de texto desde la consola.
     */
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    /**
     * Lee un número entero evitando que una entrada incorrecta
     * detenga la ejecución del programa.
     */
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