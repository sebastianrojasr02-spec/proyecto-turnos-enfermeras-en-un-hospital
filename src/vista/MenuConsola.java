package vista;

import controlador.GestorHospital;
import modelo.*;

import java.util.List;
import java.util.Scanner;

public class MenuConsola {

    private GestorHospital gestor;
    private Scanner scanner;

    public MenuConsola(GestorHospital gestor) {
        this.gestor = gestor;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;

        do {
            System.out.println("\n==================================");
            System.out.println("    GESTOR DE TURNOS HOSPITAL");
            System.out.println("==================================");
            System.out.println("1. Registrar Enfermera");
            System.out.println("2. Listar Enfermeras");
            System.out.println("3. Buscar Enfermera");
            System.out.println("4. Modificar Enfermera");
            System.out.println("5. Eliminar Enfermera");
            System.out.println("6. Asignar Turno");
            System.out.println("7. Buscar Turno");
            System.out.println("8. Modificar Turno");
            System.out.println("9. Eliminar Turno");
            System.out.println("10. Buscar por Especialidad");
            System.out.println("11. Buscar Enfermeras Disponibles");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {

                    case 1:
                        registrarEnfermera();
                        break;

                    case 2:
                        listarEnfermeras();
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
                        buscarTurno();
                        break;

                    case 8:
                        modificarTurno();
                        break;

                    case 9:
                        eliminarTurno();
                        break;

                    case 10:
                        buscarPorEspecialidad();
                        break;

                    case 11:
                        buscarDisponibles();
                        break;

                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }

        } while (opcion != 0);
    }

    private void registrarEnfermera() {

        System.out.print("RUT: ");
        String rut = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();

        if (gestor.agregarEnfermera(rut, nombre, especialidad)) {
            System.out.println("Enfermera registrada correctamente.");
        } else {
            System.out.println("Error: Ya existe una enfermera con ese RUT.");
        }
    }

    private void listarEnfermeras() {

        System.out.println("\n--- LISTA DE ENFERMERAS ---");

        for (Enfermera enf : gestor.getMapaEnfermeras().values()) {

            System.out.println(
                    enf.obtenerIdentificacion()
                    + " | Turnos: "
                    + enf.getTurnosAsignados().size()
            );
        }
    }

    private void buscarEnfermera() {

        System.out.print("RUT de la enfermera: ");
        String rut = scanner.nextLine();

        Enfermera enfermera = gestor.buscarEnfermera(rut);

        if (enfermera != null) {

            System.out.println("\n--- ENFERMERA ENCONTRADA ---");
            System.out.println("RUT: " + enfermera.getRut());
            System.out.println("Nombre: " + enfermera.getNombre());
            System.out.println("Especialidad: " + enfermera.getEspecialidad());
            System.out.println("Turnos: " + enfermera.getTurnosAsignados().size());

        } else {

            System.out.println("No se encontró una enfermera con ese RUT.");
        }
    }

    private void modificarEnfermera() {

        System.out.print("RUT de la enfermera: ");
        String rut = scanner.nextLine();

        Enfermera enfermera = gestor.buscarEnfermera(rut);

        if (enfermera == null) {
            System.out.println("No se encontró la enfermera.");
            return;
        }

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Nueva especialidad: ");
        String especialidad = scanner.nextLine();

        if (gestor.modificarEnfermera(rut, nombre, especialidad)) {
            System.out.println("Enfermera modificada correctamente.");
        } else {
            System.out.println("No se pudo modificar la enfermera.");
        }
    }

    private void eliminarEnfermera() {

        System.out.print("RUT de la enfermera a eliminar: ");
        String rut = scanner.nextLine();

        if (gestor.eliminarEnfermera(rut)) {
            System.out.println("Enfermera eliminada exitosamente.");
        } else {
            System.out.println("No se encontró la enfermera.");
        }
    }

    private void asignarTurno() {

        System.out.print("RUT de la Enfermera: ");
        String rut = scanner.nextLine();

        Enfermera enf = gestor.buscarEnfermera(rut);

        if (enf == null) {
            System.out.println("Error: Enfermera no encontrada.");
            return;
        }

        System.out.print("ID del Turno: ");
        String idTurno = scanner.nextLine();

        System.out.print("Fecha (DD-MM-AAAA): ");
        String fecha = scanner.nextLine();

        System.out.print("Tipo (Manana / Tarde / Noche): ");
        String tipo = scanner.nextLine();

        enf.agregarTurno(idTurno, fecha, tipo);
        gestor.guardarDatos();

        System.out.println("Turno asignado correctamente.");
    }

    private void buscarTurno() {

        System.out.print("RUT de la enfermera: ");
        String rut = scanner.nextLine();

        System.out.print("ID del turno: ");
        String idTurno = scanner.nextLine();

        Turno turno = gestor.buscarTurnoDeEnfermera(rut, idTurno);

        if (turno != null) {

            System.out.println("\n--- TURNO ENCONTRADO ---");
            System.out.println(turno);

        } else {

            System.out.println("No se encontró el turno.");
        }
    }

    private void modificarTurno() {

        System.out.print("RUT de la enfermera: ");
        String rut = scanner.nextLine();

        System.out.print("ID del turno: ");
        String idTurno = scanner.nextLine();

        Turno turno = gestor.buscarTurnoDeEnfermera(rut, idTurno);

        if (turno == null) {
            System.out.println("No se encontró el turno.");
            return;
        }

        System.out.print("Nueva fecha: ");
        String fecha = scanner.nextLine();

        System.out.print("Nuevo tipo (Manana / Tarde / Noche): ");
        String tipoTexto = scanner.nextLine();

        System.out.print("Nuevo estado (PENDIENTE / CONFIRMADO / CANCELADO / COMPLETADO): ");
        String estadoTexto = scanner.nextLine();

        try {

            TipoTurno tipo = TipoTurno.valueOf(tipoTexto.toUpperCase());
            EstadoTurno estado = EstadoTurno.valueOf(estadoTexto.toUpperCase());

            if (gestor.modificarTurno(
                    rut,
                    idTurno,
                    fecha,
                    tipo,
                    estado)) {

                System.out.println("Turno modificado correctamente.");

            } else {

                System.out.println("No se pudo modificar el turno.");
            }

        } catch (IllegalArgumentException e) {

            System.out.println("Tipo o estado no válido.");
        }
    }

    private void eliminarTurno() {

        System.out.print("RUT de la enfermera: ");
        String rut = scanner.nextLine();

        System.out.print("ID del turno: ");
        String idTurno = scanner.nextLine();

        if (gestor.eliminarTurno(rut, idTurno)) {

            System.out.println("Turno eliminado correctamente.");

        } else {

            System.out.println("No se encontró el turno.");
        }
    }

    private void buscarPorEspecialidad() {

        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();

        List<Enfermera> lista =
                gestor.buscarPorEspecialidad(especialidad);

        System.out.println("\n--- RESULTADOS ---");

        if (lista.isEmpty()) {
            System.out.println("No se encontraron enfermeras.");
        }

        for (Enfermera enfermera : lista) {
            System.out.println(enfermera.obtenerIdentificacion());
        }
    }

    private void buscarDisponibles() {

        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();

        System.out.print("Fecha solicitada (DD-MM-AAAA): ");
        String fecha = scanner.nextLine();

        List<Enfermera> disponibles =
                gestor.buscarEnfermerasDisponibles(
                        especialidad,
                        fecha
                );

        System.out.println("\n--- ENFERMERAS DISPONIBLES ---");

        if (disponibles.isEmpty()) {
            System.out.println("No hay enfermeras disponibles.");
        }

        for (Enfermera enfermera : disponibles) {
            System.out.println(enfermera.obtenerIdentificacion());
        }
    }
}

