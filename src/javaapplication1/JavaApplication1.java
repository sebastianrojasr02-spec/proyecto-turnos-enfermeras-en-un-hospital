package javaapplication1;

import controlador.ControladorConsola;
import controlador.ControladorVentana;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import servicio.GestorHospital;
import vista.MenuConsola;
import vista.VentanaPrincipal;

/**
 * Punto de entrada de la aplicación y selector de interfaz.
 */
public class JavaApplication1 {

    public static void main(String[] args) {
        GestorHospital gestor = new GestorHospital();
        Scanner scanner = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("   SISTEMA DE GESTIÓN HOSPITALARIA");
        System.out.println("======================================");
        System.out.println();
        System.out.println("Seleccione la interfaz que desea utilizar:");
        System.out.println("1. Consola");
        System.out.println("2. Ventana");
        System.out.print("Opción: ");

        String opcion = scanner.nextLine().trim();
        if (opcion.equals("1")) {
            iniciarConsola(gestor, scanner);
        } else if (opcion.equals("2")) {
            iniciarVentana(gestor);
        } else {
            System.out.println("Opción no válida. El programa finalizará.");
        }
    }

    private static void iniciarConsola(
            GestorHospital gestor, Scanner scanner) {
        MenuConsola menu = new MenuConsola(
                new ControladorConsola(gestor), scanner);
        menu.iniciar();
    }

    private static void iniciarVentana(GestorHospital gestor) {
        ControladorVentana controlador = new ControladorVentana(gestor);
        Runtime.getRuntime().addShutdownHook(
                new Thread(controlador::guardarDatos));

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal(controlador);
            ventana.setVisible(true);
        });
    }
}
