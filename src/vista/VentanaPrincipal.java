package vista;

import controlador.ControladorVentana;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import modelo.Enfermera;
import modelo.Turno;

/**
 * Interfaz gráfica principal del sistema hospitalario.
 * Se encarga de recibir datos del usuario y mostrar resultados.
 */
public class VentanaPrincipal extends JFrame {

    private ControladorVentana controlador;

    private JTextField txtRut;
    private JTextField txtNombre;
    private JTextField txtEspecialidad;

    private JTextField txtRutTurno;
    private JTextField txtIdTurno;
    private JTextField txtFechaTurno;

    private JComboBox<String> cmbTipoTurno;
    private JComboBox<String> cmbEstadoTurno;

    private JTextArea areaResultados;

    public VentanaPrincipal(ControladorVentana controlador) {
        this.controlador = controlador;

        setTitle("Sistema de Gestión Hospitalaria");
        setSize(800, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Enfermeras", crearPanelEnfermeras());
        pestanas.addTab("Turnos", crearPanelTurnos());
        pestanas.addTab("Búsquedas", crearPanelBusquedas());

        add(pestanas, BorderLayout.CENTER);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setRows(7);
        areaResultados.setLineWrap(true);
        areaResultados.setWrapStyleWord(true);

        add(new JScrollPane(areaResultados), BorderLayout.SOUTH);
    }

    private JPanel crearPanelEnfermeras() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titulo = crearTitulo("Gestión de Enfermeras");
        panel.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(
                BorderFactory.createTitledBorder("Datos de la Enfermera")
        );

        GridBagConstraints gbc = crearRestricciones();

        agregarCampo(formulario, gbc, 0, "RUT:",
                txtRut = new JTextField());

        agregarCampo(formulario, gbc, 1, "Nombre:",
                txtNombre = new JTextField());

        agregarCampo(formulario, gbc, 2, "Especialidad:",
                txtEspecialidad = new JTextField());

        panel.add(formulario, BorderLayout.CENTER);

        JPanel botones = new JPanel(new GridLayout(2, 2, 10, 10));

        JButton btnAgregar = new JButton("Agregar / Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnMostrar = new JButton("Mostrar Enfermeras");
        JButton btnBuscar = new JButton("Buscar Enfermera");

        botones.add(btnAgregar);
        botones.add(btnEliminar);
        botones.add(btnMostrar);
        botones.add(btnBuscar);

        panel.add(botones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarOModificarEnfermera());
        btnEliminar.addActionListener(e -> eliminarEnfermera());
        btnMostrar.addActionListener(e -> mostrarEnfermeras());
        btnBuscar.addActionListener(e -> buscarEnfermera());

        return panel;
    }

    private JPanel crearPanelTurnos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titulo = crearTitulo("Gestión de Turnos");
        panel.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(
                BorderFactory.createTitledBorder("Datos del Turno")
        );

        GridBagConstraints gbc = crearRestricciones();

        agregarCampo(formulario, gbc, 0, "RUT Enfermera:",
                txtRutTurno = new JTextField());

        agregarCampo(formulario, gbc, 1, "ID Turno:",
                txtIdTurno = new JTextField());

        agregarCampo(formulario, gbc, 2, "Fecha:",
                txtFechaTurno = new JTextField());

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        formulario.add(new JLabel("Tipo:"), gbc);

        cmbTipoTurno = new JComboBox<>(
                new String[]{"MANANA", "TARDE", "NOCHE"}
        );

        gbc.gridx = 1;
        gbc.weightx = 1;
        formulario.add(cmbTipoTurno, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        formulario.add(new JLabel("Estado:"), gbc);

        cmbEstadoTurno = new JComboBox<>(
                new String[]{
                    "PENDIENTE",
                    "CONFIRMADO",
                    "CANCELADO",
                    "COMPLETADO"
                }
        );

        gbc.gridx = 1;
        gbc.weightx = 1;
        formulario.add(cmbEstadoTurno, gbc);

        panel.add(formulario, BorderLayout.CENTER);

        JPanel botones = new JPanel(new GridLayout(1, 5, 8, 8));

        JButton btnAsignar = new JButton("Asignar");
        JButton btnMostrar = new JButton("Mostrar Turnos");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");

        botones.add(btnAsignar);
        botones.add(btnMostrar);
        botones.add(btnBuscar);
        botones.add(btnModificar);
        botones.add(btnEliminar);

        panel.add(botones, BorderLayout.SOUTH);

        btnAsignar.addActionListener(e -> asignarTurno());
        btnMostrar.addActionListener(e -> mostrarTurnos());
        btnBuscar.addActionListener(e -> buscarTurno());
        btnModificar.addActionListener(e -> modificarTurno());
        btnEliminar.addActionListener(e -> eliminarTurno());

        return panel;
    }

    private JPanel crearPanelBusquedas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = crearTitulo("Consultas del Sistema");
        panel.add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(2, 1, 10, 10));

        JButton btnEspecialidad = new JButton("Buscar por Especialidad");
        JButton btnDisponibles = new JButton("Buscar Enfermeras Disponibles");

        botones.add(btnEspecialidad);
        botones.add(btnDisponibles);

        panel.add(botones, BorderLayout.CENTER);

        btnEspecialidad.addActionListener(e -> buscarPorEspecialidad());
        btnDisponibles.addActionListener(e -> buscarDisponibles());

        return panel;
    }

    private void agregarOModificarEnfermera() {
        String rut = txtRut.getText().trim();
        String nombre = txtNombre.getText().trim();
        String especialidad = txtEspecialidad.getText().trim();

        if (rut.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
            mostrarMensaje("Debe completar todos los campos.");
            return;
        }

        Enfermera existente = controlador.buscarEnfermera(rut);

        if (existente == null) {
            if (controlador.agregarEnfermera(rut, nombre, especialidad)) {
                mostrarMensaje("Enfermera agregada correctamente.");
                limpiarCamposEnfermera();
            } else {
                mostrarMensaje("No se pudo agregar la enfermera.");
            }
        } else {
            if (controlador.modificarEnfermera(
                    rut, nombre, especialidad)) {

                mostrarMensaje("Enfermera modificada correctamente.");
                limpiarCamposEnfermera();
            } else {
                mostrarMensaje("No se pudo modificar la enfermera.");
            }
        }
    }

    private void eliminarEnfermera() {
        String rut = txtRut.getText().trim();

        if (rut.isEmpty()) {
            mostrarMensaje("Ingrese el RUT de la enfermera.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar esta enfermera?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        if (controlador.eliminarEnfermera(rut)) {
            mostrarMensaje("Enfermera eliminada correctamente.");
            limpiarCamposEnfermera();
        } else {
            mostrarMensaje("No se encontró la enfermera.");
        }
    }

    private void mostrarEnfermeras() {
        List<Enfermera> enfermeras = controlador.obtenerEnfermeras();

        StringBuilder texto = new StringBuilder();
        texto.append("--- ENFERMERAS REGISTRADAS ---\n\n");

        if (enfermeras.isEmpty()) {
            texto.append("No existen enfermeras registradas.");
        } else {
            for (Enfermera enfermera : enfermeras) {
                texto.append(enfermera.obtenerIdentificacion()).append("\n");
            }
        }

        mostrarResultado(texto.toString());
    }

    private void buscarEnfermera() {
        String rut = txtRut.getText().trim();

        if (rut.isEmpty()) {
            mostrarMensaje("Ingrese el RUT de la enfermera.");
            return;
        }

        Enfermera enfermera = controlador.buscarEnfermera(rut);

        if (enfermera == null) {
            mostrarMensaje("No se encontró la enfermera.");
            return;
        }

        txtNombre.setText(enfermera.getNombre());
        txtEspecialidad.setText(enfermera.getEspecialidad());

        mostrarResultado(
                "Enfermera encontrada:\n\n"
                + enfermera.obtenerIdentificacion()
        );
    }

    private void asignarTurno() {
        String rut = txtRutTurno.getText().trim();
        String idTurno = txtIdTurno.getText().trim();
        String fecha = txtFechaTurno.getText().trim();
        String tipo = (String) cmbTipoTurno.getSelectedItem();

        if (rut.isEmpty() || idTurno.isEmpty() || fecha.isEmpty()) {
            mostrarMensaje("Complete todos los campos.");
            return;
        }

        boolean asignado = controlador.agregarTurno(
                rut, idTurno, fecha, tipo
        );

        if (asignado) {
            mostrarMensaje("Turno asignado correctamente.");
            limpiarCamposTurno();
        } else if (!controlador.getUltimoError().isEmpty()) {
            mostrarMensaje(controlador.getUltimoError());
        } else {
            mostrarMensaje(
                    "No se pudo asignar el turno. "
                    + "Verifique los datos ingresados."
            );
        }
    }

    private void mostrarTurnos() {
        List<Enfermera> enfermeras = controlador.obtenerEnfermeras();

        StringBuilder texto = new StringBuilder();
        texto.append("--- TURNOS REGISTRADOS ---\n");

        boolean existenTurnos = false;

        for (Enfermera enfermera : enfermeras) {
            if (!enfermera.getTurnosAsignados().isEmpty()) {
                texto.append("\n");
                texto.append(enfermera.obtenerIdentificacion()).append("\n");

                for (Turno turno : enfermera.getTurnosAsignados()) {
                    texto.append("  ").append(turno).append("\n");
                    existenTurnos = true;
                }
            }
        }

        if (!existenTurnos) {
            texto.append("\nNo existen turnos registrados.");
        }

        mostrarResultado(texto.toString());
    }

    private void buscarTurno() {
        String rut = txtRutTurno.getText().trim();
        String idTurno = txtIdTurno.getText().trim();

        if (rut.isEmpty() || idTurno.isEmpty()) {
            mostrarMensaje("Ingrese el RUT y el ID del turno.");
            return;
        }

        Turno turno = controlador.buscarTurno(rut, idTurno);

        if (turno == null) {
            mostrarMensaje("No se encontró el turno.");
            return;
        }

        txtFechaTurno.setText(turno.getFecha());
        cmbTipoTurno.setSelectedItem(turno.getTipo().name());
        cmbEstadoTurno.setSelectedItem(turno.getEstado().name());

        mostrarResultado("Turno encontrado:\n\n" + turno);
    }

    private void modificarTurno() {
        String rut = txtRutTurno.getText().trim();
        String idTurno = txtIdTurno.getText().trim();
        String fecha = txtFechaTurno.getText().trim();

        String tipo = (String) cmbTipoTurno.getSelectedItem();
        String estado = (String) cmbEstadoTurno.getSelectedItem();

        if (rut.isEmpty() || idTurno.isEmpty() || fecha.isEmpty()) {
            mostrarMensaje("Complete todos los campos.");
            return;
        }

        boolean modificado = controlador.modificarTurno(
                rut, idTurno, fecha, tipo, estado
        );

        if (modificado) {
            mostrarMensaje("Turno modificado correctamente.");
        } else if (!controlador.getUltimoError().isEmpty()) {
            mostrarMensaje(controlador.getUltimoError());
        } else {
            mostrarMensaje("No se pudo modificar el turno.");
        }
    }

    private void eliminarTurno() {
        String rut = txtRutTurno.getText().trim();
        String idTurno = txtIdTurno.getText().trim();

        if (rut.isEmpty() || idTurno.isEmpty()) {
            mostrarMensaje("Ingrese el RUT y el ID del turno.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este turno?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        if (controlador.eliminarTurno(rut, idTurno)) {
            mostrarMensaje("Turno eliminado correctamente.");
            limpiarCamposTurno();
        } else if (!controlador.getUltimoError().isEmpty()) {
            mostrarMensaje(controlador.getUltimoError());
        } else {
            mostrarMensaje("No se pudo eliminar el turno.");
        }
    }

    private void buscarPorEspecialidad() {
        String especialidad = JOptionPane.showInputDialog(
                this,
                "Ingrese la especialidad:"
        );

        if (especialidad == null || especialidad.trim().isEmpty()) {
            return;
        }

        List<Enfermera> resultado =
                controlador.buscarPorEspecialidad(especialidad);

        StringBuilder texto = new StringBuilder();
        texto.append("--- RESULTADOS ---\n\n");

        if (resultado.isEmpty()) {
            texto.append("No se encontraron enfermeras.");
        } else {
            for (Enfermera enfermera : resultado) {
                texto.append(enfermera.obtenerIdentificacion()).append("\n");
            }
        }

        mostrarResultado(texto.toString());
    }

    private void buscarDisponibles() {
        JTextField campoEspecialidad = new JTextField();
        JTextField campoFecha = new JTextField();

        JPanel formulario = new JPanel(new GridLayout(0, 1, 5, 5));
        formulario.add(new JLabel("Especialidad:"));
        formulario.add(campoEspecialidad);
        formulario.add(new JLabel("Fecha:"));
        formulario.add(campoFecha);

        int opcion = JOptionPane.showConfirmDialog(
                this,
                formulario,
                "Buscar Disponibilidad",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        List<Enfermera> disponibles =
                controlador.buscarEnfermerasDisponibles(
                        campoEspecialidad.getText(),
                        campoFecha.getText()
                );

        StringBuilder texto = new StringBuilder();
        texto.append("--- ENFERMERAS DISPONIBLES ---\n\n");

        if (disponibles.isEmpty()) {
            texto.append("No hay enfermeras disponibles.");
        } else {
            for (Enfermera enfermera : disponibles) {
                texto.append(enfermera.obtenerIdentificacion()).append("\n");
            }
        }

        mostrarResultado(texto.toString());
    }

    private JLabel crearTitulo(String texto) {
        JLabel titulo = new JLabel(texto);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        return titulo;
    }

    private GridBagConstraints crearRestricciones() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void agregarCampo(
            JPanel panel,
            GridBagConstraints gbc,
            int fila,
            String etiqueta,
            JTextField campo) {

        campo.setPreferredSize(new Dimension(300, 30));

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0;
        panel.add(new JLabel(etiqueta), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(campo, gbc);
    }

    private void limpiarCamposEnfermera() {
        txtRut.setText("");
        txtNombre.setText("");
        txtEspecialidad.setText("");
    }

    private void limpiarCamposTurno() {
        txtRutTurno.setText("");
        txtIdTurno.setText("");
        txtFechaTurno.setText("");
        cmbTipoTurno.setSelectedIndex(0);
        cmbEstadoTurno.setSelectedIndex(0);
    }

    private void mostrarResultado(String texto) {
        areaResultados.setText(texto);
        areaResultados.setCaretPosition(0);
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Sistema Hospitalario",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}