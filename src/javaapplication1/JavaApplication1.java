package javaapplication1;

import controlador.ControladorConsola;
import controlador.ControladorVentana;
import controlador.GestorHospital;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import vista.MenuConsola;
import vista.VentanaPrincipal;

/**
 * Punto de entrada de la aplicación.
 * Permite seleccionar entre la interfaz de consola y la interfaz gráfica.
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
            System.out.println();
            System.out.println("Opción no válida.");
            System.out.println("El programa finalizará.");
        }
    }

    /**
     * Inicia la interfaz de consola.
     */
    private static void iniciarConsola(
            GestorHospital gestor,
            Scanner scanner) {

        ControladorConsola controlador =
                new ControladorConsola(gestor);

        MenuConsola menu =
                new MenuConsola(controlador, scanner);

        menu.iniciar();
    }

    /**
     * Inicia la interfaz gráfica.
     */
    private static void iniciarVentana(GestorHospital gestor) {
        ControladorVentana controlador =
                new ControladorVentana(gestor);

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> controlador.guardarDatos())
        );

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana =
                    new VentanaPrincipal(controlador);

            ventana.setVisible(true);
        });
    }
}