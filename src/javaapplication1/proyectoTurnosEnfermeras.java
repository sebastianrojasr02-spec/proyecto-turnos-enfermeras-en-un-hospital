package javaapplication1;

import controlador.GestorHospital;
import vista.MenuConsola;
import vista.VentanaPrincipal;

import javax.swing.SwingUtilities;

public class JavaApplication1 {

    public static void main(String[] args) {

        GestorHospital gestor = new GestorHospital();

        // Lanzar interfaz gráfica Swing
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal(gestor);
            ventana.setVisible(true);
        });

        // Lanzar consola interactiva
        MenuConsola menu = new MenuConsola(gestor);
        menu.iniciar();
    }
}