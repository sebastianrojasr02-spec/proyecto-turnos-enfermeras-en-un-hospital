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
            System.out.println("3. Asignar Turno a Enfermera");
            System.out.println("4. Buscar Enfermeras por Especialidad");
            System.out.println("5. Eliminar Enfermera");
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
                        asignarTurno();
                        break;
                    case 4:
                        buscarPorEspecialidad();
                        break;
                    case 5:
                        eliminarEnfermera();
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
            System.out.println("Error: Ya existe una enfermera registrada con ese RUT.");
        }
    }

    private void listarEnfermeras() {
        System.out.println("\n--- LISTA DE ENFERMERAS ---");
        for (Enfermera enf : gestor.getMapaEnfermeras().values()) {
            System.out.println(enf.obtenerIdentificacion() + " | Turnos: " + enf.getTurnosAsignados().size());
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

        System.out.print("ID del Turno (ej: T003): ");
        String idTurno = scanner.nextLine();
        System.out.print("Fecha (DD-MM-AAAA): ");
        String fecha = scanner.nextLine();
        System.out.print("Tipo (Mañana / Tarde / Noche): ");
        String tipo = scanner.nextLine();

        enf.agregarTurno(idTurno, fecha, tipo);
        gestor.guardarDatos();
        System.out.println("Turno asignado correctamente.");
    }

    private void buscarPorEspecialidad() {
        System.out.print("Especialidad: ");
        String esp = scanner.nextLine();
        List<Enfermera> lista = gestor.buscarPorEspecialidad(esp);
        System.out.println("\n--- RESULTADOS ---");
        for (Enfermera e : lista) {
            System.out.println(e.obtenerIdentificacion());
        }
    }

    private void eliminarEnfermera() {
        System.out.print("RUT de la Enfermera a eliminar: ");
        String rut = scanner.nextLine();
        if (gestor.eliminarEnfermera(rut)) {
            System.out.println("Enfermera eliminada exitosamente.");
        } else {
            System.out.println("Error: No se encontró la enfermera.");
        }
    }
}