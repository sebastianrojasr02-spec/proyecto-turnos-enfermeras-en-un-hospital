package vista;

import controlador.GestorHospital;
import modelo.Enfermera;
import modelo.Turno;
import modelo.TipoTurno;
import modelo.EstadoTurno;

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

            System.out.println();
            System.out.println("==================================");
            System.out.println("    GESTOR DE TURNOS HOSPITAL");
            System.out.println("==================================");
            System.out.println();
            System.out.println("----- ENFERMERAS -----");
            System.out.println("1. Agregar Enfermera");
            System.out.println("2. Mostrar Enfermeras");
            System.out.println("3. Buscar Enfermera");
            System.out.println("4. Modificar Enfermera");
            System.out.println("5. Eliminar Enfermera");
            System.out.println();
            System.out.println("----- TURNOS -----");
            System.out.println("6. Agregar Turno");
            System.out.println("7. Mostrar Turnos");
            System.out.println("8. Buscar Turno");
            System.out.println("9. Modificar Turno");
            System.out.println("10. Eliminar Turno");
            System.out.println();
            System.out.println("----- FUNCIONALIDADES -----");
            System.out.println("11. Buscar por Especialidad");
            System.out.println("12. Buscar Enfermeras Disponibles");
            System.out.println();
            System.out.println("0. Salir");
            System.out.println();

            System.out.print("Seleccione una opción: ");

            try {

                opcion =
                        Integer.parseInt(
                                scanner.nextLine()
                        );

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
                        agregarTurno();
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
                        System.out.println(
                                "Saliendo del sistema..."
                        );
                        break;

                    default:
                        System.out.println(
                                "Opción no válida."
                        );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Error: debe ingresar un número."
                );
            }

        } while (opcion != 0);
    }

    // =====================================================
    // ENFERMERAS
    // =====================================================

    private void agregarEnfermera() {

        System.out.println();
        System.out.println("--- AGREGAR ENFERMERA ---");

        System.out.print("RUT: ");
        String rut = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();

        if (gestor.agregarEnfermera(
                rut,
                nombre,
                especialidad
        )) {

            System.out.println(
                    "Enfermera agregada correctamente."
            );

        } else {

            System.out.println(
                    "Ya existe una enfermera con ese RUT."
            );
        }
    }

    private void mostrarEnfermeras() {

        System.out.println();
        System.out.println("--- ENFERMERAS ---");

        if (gestor.getMapaEnfermeras().isEmpty()) {

            System.out.println(
                    "No existen enfermeras registradas."
            );

            return;
        }

        for (Enfermera enfermera :
                gestor.getMapaEnfermeras().values()) {

            System.out.println(
                    "RUT: "
                    + enfermera.getRut()
                    + " | Nombre: "
                    + enfermera.getNombre()
                    + " | Especialidad: "
                    + enfermera.getEspecialidad()
            );
        }
    }

    private void buscarEnfermera() {

        System.out.println();
        System.out.println("--- BUSCAR ENFERMERA ---");

        System.out.print("RUT: ");
        String rut = scanner.nextLine();

        Enfermera enfermera =
                gestor.buscarEnfermera(rut);

        if (enfermera != null) {

            System.out.println(
                    "Enfermera encontrada:"
            );

            System.out.println(
                    "RUT: "
                    + enfermera.getRut()
            );

            System.out.println(
                    "Nombre: "
                    + enfermera.getNombre()
            );

            System.out.println(
                    "Especialidad: "
                    + enfermera.getEspecialidad()
            );

        } else {

            System.out.println(
                    "No se encontró la enfermera."
            );
        }
    }

    private void modificarEnfermera() {

        System.out.println();
        System.out.println("--- MODIFICAR ENFERMERA ---");

        System.out.print("RUT: ");
        String rut = scanner.nextLine();

        Enfermera enfermera =
                gestor.buscarEnfermera(rut);

        if (enfermera == null) {

            System.out.println(
                    "No se encontró la enfermera."
            );

            return;
        }

        System.out.print(
                "Nuevo nombre: "
        );

        String nombre =
                scanner.nextLine();

        System.out.print(
                "Nueva especialidad: "
        );

        String especialidad =
                scanner.nextLine();

        if (gestor.modificarEnfermera(
                rut,
                nombre,
                especialidad
        )) {

            System.out.println(
                    "Enfermera modificada correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo modificar la enfermera."
            );
        }
    }

    private void eliminarEnfermera() {

        System.out.println();
        System.out.println("--- ELIMINAR ENFERMERA ---");

        System.out.print("RUT: ");
        String rut = scanner.nextLine();

        if (gestor.eliminarEnfermera(rut)) {

            System.out.println(
                    "Enfermera eliminada correctamente."
            );

        } else {

            System.out.println(
                    "No se encontró la enfermera."
            );
        }
    }

    // =====================================================
    // TURNOS
    // =====================================================

    private void agregarTurno() {

        System.out.println();
        System.out.println("--- AGREGAR TURNO ---");

        System.out.print(
                "RUT de la enfermera: "
        );

        String rut =
                scanner.nextLine();

        Enfermera enfermera =
                gestor.buscarEnfermera(rut);

        if (enfermera == null) {

            System.out.println(
                    "No se encontró la enfermera."
            );

            return;
        }

        System.out.print(
                "ID del turno: "
        );

        String idTurno =
                scanner.nextLine();

        System.out.print(
                "Fecha (DD-MM-AAAA): "
        );

        String fecha =
                scanner.nextLine();

        System.out.print(
                "Tipo (MANANA, TARDE, NOCHE): "
        );

        String tipo =
                scanner.nextLine();

        enfermera.agregarTurno(
                idTurno,
                fecha,
                tipo
        );

        System.out.println(
                "Turno agregado correctamente."
        );
    }

    private void mostrarTurnos() {

        System.out.println();
        System.out.println("--- TURNOS ---");

        boolean hayTurnos = false;

        for (Enfermera enfermera :
                gestor.getMapaEnfermeras().values()) {

            for (Turno turno :
                    enfermera.getTurnosAsignados()) {

                hayTurnos = true;

                System.out.println(
                        "Enfermera: "
                        + enfermera.getNombre()
                        + " | RUT: "
                        + enfermera.getRut()
                );

                System.out.println(
                        "Turno: "
                        + turno
                );

                System.out.println();
            }
        }

        if (!hayTurnos) {

            System.out.println(
                    "No existen turnos registrados."
            );
        }
    }

    private void buscarTurno() {

        System.out.println();
        System.out.println("--- BUSCAR TURNO ---");

        System.out.print(
                "RUT de la enfermera: "
        );

        String rut =
                scanner.nextLine();

        System.out.print(
                "ID del turno: "
        );

        String idTurno =
                scanner.nextLine();

        Turno turno =
                gestor.buscarTurnoDeEnfermera(
                        rut,
                        idTurno
                );

        if (turno != null) {

            System.out.println(
                    "Turno encontrado:"
            );

            System.out.println(
                    turno
            );

        } else {

            System.out.println(
                    "No se encontró el turno."
            );
        }
    }

    private void modificarTurno() {

        System.out.println();
        System.out.println("--- MODIFICAR TURNO ---");

        System.out.print(
                "RUT de la enfermera: "
        );

        String rut =
                scanner.nextLine();

        System.out.print(
                "ID del turno: "
        );

        String idTurno =
                scanner.nextLine();

        Turno turno =
                gestor.buscarTurnoDeEnfermera(
                        rut,
                        idTurno
                );

        if (turno == null) {

            System.out.println(
                    "No se encontró el turno."
            );

            return;
        }

        System.out.print(
                "Nueva fecha: "
        );

        String fecha =
                scanner.nextLine();

        System.out.print(
                "Nuevo tipo (MANANA, TARDE, NOCHE): "
        );

        String tipoTexto =
                scanner.nextLine();

        System.out.print(
                "Nuevo estado (PENDIENTE, CONFIRMADO, "
                + "CANCELADO, COMPLETADO): "
        );

        String estadoTexto =
                scanner.nextLine();

        try {

            TipoTurno tipo =
                    TipoTurno.valueOf(
                            tipoTexto.toUpperCase()
                    );

            EstadoTurno estado =
                    EstadoTurno.valueOf(
                            estadoTexto.toUpperCase()
                    );

            if (gestor.modificarTurno(
                    rut,
                    idTurno,
                    fecha,
                    tipo,
                    estado
            )) {

                System.out.println(
                        "Turno modificado correctamente."
                );

            } else {

                System.out.println(
                        "No se pudo modificar el turno."
                );
            }

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Tipo o estado no válido."
            );
        }
    }

    private void eliminarTurno() {

        System.out.println();
        System.out.println("--- ELIMINAR TURNO ---");

        System.out.print(
                "RUT de la enfermera: "
        );

        String rut =
                scanner.nextLine();

        System.out.print(
                "ID del turno: "
        );

        String idTurno =
                scanner.nextLine();

        if (gestor.eliminarTurno(
                rut,
                idTurno
        )) {

            System.out.println(
                    "Turno eliminado correctamente."
            );

        } else {

            System.out.println(
                    "No se encontró el turno."
            );
        }
    }

    // =====================================================
    // SIA-9
    // =====================================================

    private void buscarPorEspecialidad() {

        System.out.println();
        System.out.println(
                "--- BUSCAR POR ESPECIALIDAD ---"
        );

        System.out.print(
                "Especialidad: "
        );

        String especialidad =
                scanner.nextLine();

        List<Enfermera> resultado =
                gestor.buscarPorEspecialidad(
                        especialidad
                );

        if (resultado.isEmpty()) {

            System.out.println(
                    "No se encontraron enfermeras."
            );

            return;
        }

        System.out.println(
                "Enfermeras encontradas:"
        );

        for (Enfermera enfermera :
                resultado) {

            System.out.println(
                    enfermera.obtenerIdentificacion()
            );
        }
    }

    private void buscarDisponibles() {

        System.out.println();
        System.out.println(
                "--- ENFERMERAS DISPONIBLES ---"
        );

        System.out.print(
                "Especialidad: "
        );

        String especialidad =
                scanner.nextLine();

        System.out.print(
                "Fecha (DD-MM-AAAA): "
        );

        String fecha =
                scanner.nextLine();

        List<Enfermera> disponibles =
                gestor.buscarEnfermerasDisponibles(
                        especialidad,
                        fecha
                );

        if (disponibles.isEmpty()) {

            System.out.println(
                    "No hay enfermeras disponibles."
            );

            return;
        }

        System.out.println(
                "Enfermeras disponibles:"
        );

        for (Enfermera enfermera :
                disponibles) {

            System.out.println(
                    enfermera.obtenerIdentificacion()
            );
        }
    }
}

