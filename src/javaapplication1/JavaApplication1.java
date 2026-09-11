package javaapplication1;

import controlador.GestorHospital;
import vista.MenuConsola;
import vista.VentanaPrincipal;

import javax.swing.SwingUtilities;
import java.util.Scanner;

public class JavaApplication1 {

    public static void main(String[] args) {

        GestorHospital gestor =
                new GestorHospital();

        Scanner scanner =
                new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("   SISTEMA DE GESTIÓN HOSPITALARIA");
        System.out.println("======================================");
        System.out.println();
        System.out.println("Seleccione la interfaz que desea utilizar:");
        System.out.println("1. Consola");
        System.out.println("2. Ventana");
        System.out.print("Opción: ");

        String opcion = scanner.nextLine();

        if (opcion.equals("1")) {

            MenuConsola menu =
                    new MenuConsola(gestor);

            menu.iniciar();

            // SIA-11: guardar todos los datos al salir.
            gestor.guardarDatos();

        } else if (opcion.equals("2")) {

            /*
             * SIA-11:
             * Los datos se guardan cuando se cierra
             * la aplicación.
             */
            Runtime.getRuntime().addShutdownHook(
                    new Thread(() -> {
                        gestor.guardarDatos();
                    })
            );

            SwingUtilities.invokeLater(() -> {

                VentanaPrincipal ventana =
                        new VentanaPrincipal(gestor);

                ventana.setVisible(true);
            });

        } else {

            System.out.println();
            System.out.println("Opción no válida.");
            System.out.println("El programa finalizará.");
        }
    }
}
