package vista;

import controlador.GestorHospital;
import modelo.Enfermera;
import modelo.Turno;
import modelo.TipoTurno;
import modelo.EstadoTurno;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private GestorHospital gestor;

    private JTextField txtRut;
    private JTextField txtNombre;
    private JTextField txtEspecialidad;

    private JTextField txtRutTurno;
    private JTextField txtIdTurno;
    private JTextField txtFechaTurno;

    private JComboBox<String> cmbTipoTurno;
    private JComboBox<String> cmbEstadoTurno;

    private JTextArea areaResultados;

    public VentanaPrincipal(GestorHospital gestor) {

        this.gestor = gestor;

        setTitle("Sistema de Gestión Hospitalaria");

        setSize(
                800,
                650
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        setLayout(
                new BorderLayout()
        );

        JTabbedPane pestañas =
                new JTabbedPane();

        pestañas.addTab(
                "Enfermeras",
                crearPanelEnfermeras()
        );

        pestañas.addTab(
                "Turnos",
                crearPanelTurnos()
        );

        pestañas.addTab(
                "Búsquedas",
                crearPanelBusquedas()
        );

        add(
                pestañas,
                BorderLayout.CENTER
        );

        areaResultados =
                new JTextArea();

        areaResultados.setEditable(false);

        areaResultados.setRows(6);

        add(
                new JScrollPane(
                        areaResultados
                ),
                BorderLayout.SOUTH
        );
    }

    // =====================================================
    // PANEL ENFERMERAS
    // =====================================================

    private JPanel crearPanelEnfermeras() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        20,
                        15,
                        20
                )
        );

        // =================================================
        // TITULO
        // =================================================

        JLabel titulo =
                new JLabel(
                        "Gestión de Enfermeras"
                );

        titulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        20
                )
        );

        titulo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        panel.add(
                titulo,
                BorderLayout.NORTH
        );

        // =================================================
        // FORMULARIO
        // =================================================

        JPanel formulario =
                new JPanel(
                        new GridBagLayout()
                );

        formulario.setBorder(
                BorderFactory.createTitledBorder(
                        "Datos de la Enfermera"
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        8,
                        10,
                        8,
                        10
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 0;

        // RUT

        gbc.gridx = 0;
        gbc.gridy = 0;

        formulario.add(
                new JLabel("RUT:"),
                gbc
        );

        txtRut =
                new JTextField();

        txtRut.setPreferredSize(
                new Dimension(
                        300,
                        30
                )
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        formulario.add(
                txtRut,
                gbc
        );

        // NOMBRE

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;

        formulario.add(
                new JLabel("Nombre:"),
                gbc
        );

        txtNombre =
                new JTextField();

        txtNombre.setPreferredSize(
                new Dimension(
                        300,
                        30
                )
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        formulario.add(
                txtNombre,
                gbc
        );

        // ESPECIALIDAD

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;

        formulario.add(
                new JLabel("Especialidad:"),
                gbc
        );

        txtEspecialidad =
                new JTextField();

        txtEspecialidad.setPreferredSize(
                new Dimension(
                        300,
                        30
                )
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        formulario.add(
                txtEspecialidad,
                gbc
        );

        panel.add(
                formulario,
                BorderLayout.CENTER
        );

        // =================================================
        // BOTONES
        // =================================================

        JPanel botones =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                10,
                                10
                        )
                );

        botones.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        0,
                        0,
                        0
                )
        );

        JButton btnAgregar =
                new JButton(
                        "Agregar / Modificar"
                );

        JButton btnEliminar =
                new JButton(
                        "Eliminar"
                );

        JButton btnMostrar =
                new JButton(
                        "Mostrar Enfermeras"
                );

        JButton btnBuscar =
                new JButton(
                        "Buscar Enfermera"
                );

        botones.add(
                btnAgregar
        );

        botones.add(
                btnEliminar
        );

        botones.add(
                btnMostrar
        );

        botones.add(
                btnBuscar
        );

        panel.add(
                botones,
                BorderLayout.SOUTH
        );

        // =================================================
        // AGREGAR / MODIFICAR
        // =================================================

        btnAgregar.addActionListener(e -> {

            String rut =
                    txtRut.getText().trim();

            String nombre =
                    txtNombre.getText().trim();

            String especialidad =
                    txtEspecialidad
                            .getText()
                            .trim();

            if (rut.isEmpty()
                    || nombre.isEmpty()
                    || especialidad.isEmpty()) {

                mostrarMensaje(
                        "Debe completar todos los campos."
                );

                return;
            }

            Enfermera existente =
                    gestor.buscarEnfermera(rut);

            if (existente == null) {

                if (gestor.agregarEnfermera(
                        rut,
                        nombre,
                        especialidad
                )) {

                    mostrarMensaje(
                            "Enfermera agregada correctamente."
                    );

                    limpiarCamposEnfermera();

                } else {

                    mostrarMensaje(
                            "No se pudo agregar la enfermera."
                    );
                }

            } else {

                if (gestor.modificarEnfermera(
                        rut,
                        nombre,
                        especialidad
                )) {

                    mostrarMensaje(
                            "Enfermera modificada correctamente."
                    );

                    limpiarCamposEnfermera();

                } else {

                    mostrarMensaje(
                            "No se pudo modificar la enfermera."
                    );
                }
            }
        });

        // =================================================
        // ELIMINAR
        // =================================================

        btnEliminar.addActionListener(e -> {

            String rut =
                    txtRut.getText().trim();

            if (rut.isEmpty()) {

                mostrarMensaje(
                        "Ingrese el RUT de la enfermera."
                );

                return;
            }

            int respuesta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Está seguro de eliminar "
                            + "esta enfermera?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION
                    );

            if (respuesta ==
                    JOptionPane.YES_OPTION) {

                if (gestor.eliminarEnfermera(rut)) {

                    mostrarMensaje(
                            "Enfermera eliminada correctamente."
                    );

                    limpiarCamposEnfermera();

                } else {

                    mostrarMensaje(
                            "No se encontró la enfermera."
                    );
                }
            }
        });

        // =================================================
        // MOSTRAR
        // =================================================

        btnMostrar.addActionListener(e -> {

            StringBuilder texto =
                    new StringBuilder();

            texto.append(
                    "--- ENFERMERAS REGISTRADAS ---\n\n"
            );

            if (gestor.getMapaEnfermeras()
                    .isEmpty()) {

                texto.append(
                        "No existen enfermeras registradas."
                );

            } else {

                for (Enfermera enfermera :
                        gestor.getMapaEnfermeras()
                                .values()) {

                    texto.append(
                            "RUT: "
                    ).append(
                            enfermera.getRut()
                    ).append(
                            "\nNombre: "
                    ).append(
                            enfermera.getNombre()
                    ).append(
                            "\nEspecialidad: "
                    ).append(
                            enfermera.getEspecialidad()
                    ).append(
                            "\n\n"
                    );
                }
            }

            mostrarResultado(
                    texto.toString()
            );
        });

        // =================================================
        // BUSCAR
        // =================================================

        btnBuscar.addActionListener(e -> {

            String rut =
                    txtRut.getText().trim();

            if (rut.isEmpty()) {

                mostrarMensaje(
                        "Ingrese el RUT de la enfermera."
                );

                return;
            }

            Enfermera enfermera =
                    gestor.buscarEnfermera(rut);

            if (enfermera != null) {

                txtNombre.setText(
                        enfermera.getNombre()
                );

                txtEspecialidad.setText(
                        enfermera.getEspecialidad()
                );

                mostrarResultado(
                        "Enfermera encontrada:\n\n"
                        + "RUT: "
                        + enfermera.getRut()
                        + "\nNombre: "
                        + enfermera.getNombre()
                        + "\nEspecialidad: "
                        + enfermera.getEspecialidad()
                );

            } else {

                mostrarMensaje(
                        "No se encontró la enfermera."
                );
            }
        });

        return panel;
    }

    // =====================================================
    // PANEL TURNOS
    // =====================================================

    private JPanel crearPanelTurnos() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        20,
                        15,
                        20
                )
        );

        JLabel titulo =
                new JLabel(
                        "Gestión de Turnos"
                );

        titulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        20
                )
        );

        titulo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        panel.add(
                titulo,
                BorderLayout.NORTH
        );

        JPanel formulario =
                new JPanel(
                        new GridBagLayout()
                );

        formulario.setBorder(
                BorderFactory.createTitledBorder(
                        "Datos del Turno"
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        8,
                        10,
                        8,
                        10
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // RUT

        gbc.gridx = 0;
        gbc.gridy = 0;

        formulario.add(
                new JLabel(
                        "RUT Enfermera:"
                ),
                gbc
        );

        txtRutTurno =
                new JTextField();

        gbc.gridx = 1;
        gbc.weightx = 1;

        formulario.add(
                txtRutTurno,
                gbc
        );

        // ID

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;

        formulario.add(
                new JLabel(
                        "ID Turno:"
                ),
                gbc
        );

        txtIdTurno =
                new JTextField();

        gbc.gridx = 1;
        gbc.weightx = 1;

        formulario.add(
                txtIdTurno,
                gbc
        );

        // FECHA

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;

        formulario.add(
                new JLabel(
                        "Fecha:"
                ),
                gbc
        );

        txtFechaTurno =
                new JTextField();

        gbc.gridx = 1;
        gbc.weightx = 1;

        formulario.add(
                txtFechaTurno,
                gbc
        );

        // TIPO

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;

        formulario.add(
                new JLabel(
                        "Tipo:"
                ),
                gbc
        );

        cmbTipoTurno =
                new JComboBox<>(
                        new String[]{
                                "MANANA",
                                "TARDE",
                                "NOCHE"
                        }
                );

        gbc.gridx = 1;
        gbc.weightx = 1;

        formulario.add(
                cmbTipoTurno,
                gbc
        );

        // ESTADO

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;

        formulario.add(
                new JLabel(
                        "Estado:"
                ),
                gbc
        );

        cmbEstadoTurno =
                new JComboBox<>(
                        new String[]{
                                "PENDIENTE",
                                "CONFIRMADO",
                                "CANCELADO",
                                "COMPLETADO"
                        }
                );

        gbc.gridx = 1;
        gbc.weightx = 1;

        formulario.add(
                cmbEstadoTurno,
                gbc
        );

        panel.add(
                formulario,
                BorderLayout.CENTER
        );

        JPanel botones =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                10,
                                10
                        )
                );

        JButton btnAsignar =
                new JButton(
                        "Asignar"
                );

        JButton btnBuscar =
                new JButton(
                        "Buscar"
                );

        JButton btnModificar =
                new JButton(
                        "Modificar"
                );

        JButton btnEliminar =
                new JButton(
                        "Eliminar"
                );

        botones.add(
                btnAsignar
        );

        botones.add(
                btnBuscar
        );

        botones.add(
                btnModificar
        );

        botones.add(
                btnEliminar
        );

        panel.add(
                botones,
                BorderLayout.SOUTH
        );

        // =================================================
        // ASIGNAR
        // =================================================

        btnAsignar.addActionListener(e -> {

            if (txtRutTurno.getText()
                    .trim().isEmpty()
                    || txtIdTurno.getText()
                    .trim().isEmpty()
                    || txtFechaTurno.getText()
                    .trim().isEmpty()) {

                mostrarMensaje(
                        "Complete todos los campos."
                );

                return;
            }

            Enfermera enfermera =
                    gestor.buscarEnfermera(
                            txtRutTurno.getText()
                    );

            if (enfermera == null) {

                mostrarMensaje(
                        "No se encontró la enfermera."
                );

                return;
            }

            enfermera.agregarTurno(
                    txtIdTurno.getText(),
                    txtFechaTurno.getText(),
                    (String)
                            cmbTipoTurno
                                    .getSelectedItem()
            );

            mostrarMensaje(
                    "Turno asignado correctamente."
            );
        });

        // =================================================
        // BUSCAR
        // =================================================

        btnBuscar.addActionListener(e -> {

            Turno turno =
                    gestor.buscarTurnoDeEnfermera(
                            txtRutTurno.getText(),
                            txtIdTurno.getText()
                    );

            if (turno != null) {

                mostrarResultado(
                        "Turno encontrado:\n\n"
                        + turno
                );

            } else {

                mostrarMensaje(
                        "No se encontró el turno."
                );
            }
        });

        // =================================================
        // MODIFICAR
        // =================================================

        btnModificar.addActionListener(e -> {

            try {

                TipoTurno tipo =
                        TipoTurno.valueOf(
                                ((String)
                                        cmbTipoTurno
                                                .getSelectedItem())
                                        .toUpperCase()
                        );

                EstadoTurno estado =
                        EstadoTurno.valueOf(
                                ((String)
                                        cmbEstadoTurno
                                                .getSelectedItem())
                                        .toUpperCase()
                        );

                boolean modificado =
                        gestor.modificarTurno(
                                txtRutTurno.getText(),
                                txtIdTurno.getText(),
                                txtFechaTurno.getText(),
                                tipo,
                                estado
                        );

                if (modificado) {

                    mostrarMensaje(
                            "Turno modificado correctamente."
                    );

                } else {

                    mostrarMensaje(
                            "No se encontró el turno."
                    );
                }

            } catch (
                    IllegalArgumentException ex
            ) {

                mostrarMensaje(
                        "Tipo o estado no válido."
                );
            }
        });

        // =================================================
        // ELIMINAR
        // =================================================

        btnEliminar.addActionListener(e -> {

            boolean eliminado =
                    gestor.eliminarTurno(
                            txtRutTurno.getText(),
                            txtIdTurno.getText()
                    );

            if (eliminado) {

                mostrarMensaje(
                        "Turno eliminado correctamente."
                );

            } else {

                mostrarMensaje(
                        "No se encontró el turno."
                );
            }
        });

        return panel;
    }

    // =====================================================
    // PANEL BÚSQUEDAS
    // =====================================================

    private JPanel crearPanelBusquedas() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        JLabel titulo =
                new JLabel(
                        "Consultas del Sistema"
                );

        titulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        20
                )
        );

        titulo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        panel.add(
                titulo,
                BorderLayout.NORTH
        );

        JPanel botones =
                new JPanel(
                        new GridLayout(
                                2,
                                1,
                                10,
                                10
                        )
                );

        JButton btnEspecialidad =
                new JButton(
                        "Buscar por Especialidad"
                );

        JButton btnDisponibles =
                new JButton(
                        "Buscar Enfermeras Disponibles"
                );

        botones.add(
                btnEspecialidad
        );

        botones.add(
                btnDisponibles
        );

        panel.add(
                botones,
                BorderLayout.CENTER
        );

        // =================================================
        // BUSCAR POR ESPECIALIDAD
        // =================================================

        btnEspecialidad.addActionListener(e -> {

            String especialidad =
                    JOptionPane.showInputDialog(
                            this,
                            "Ingrese la especialidad:"
                    );

            if (especialidad == null) {
                return;
            }

            java.util.List<Enfermera> resultado =
                    gestor.buscarPorEspecialidad(
                            especialidad
                    );

            StringBuilder texto =
                    new StringBuilder();

            texto.append(
                    "--- RESULTADOS ---\n\n"
            );

            if (resultado.isEmpty()) {

                texto.append(
                        "No se encontraron enfermeras."
                );

            } else {

                for (Enfermera enfermera :
                        resultado) {

                    texto.append(
                            enfermera.obtenerIdentificacion()
                    ).append(
                            "\n"
                    );
                }
            }

            mostrarResultado(
                    texto.toString()
            );
        });

        // =================================================
        // DISPONIBLES
        // =================================================

        btnDisponibles.addActionListener(e -> {

            JTextField txtEspecialidad =
                    new JTextField();

            JTextField txtFecha =
                    new JTextField();

            JPanel formulario =
                    new JPanel(
                            new GridLayout(
                                    0,
                                    1,
                                    5,
                                    5
                            )
                    );

            formulario.add(
                    new JLabel(
                            "Especialidad:"
                    )
            );

            formulario.add(
                    txtEspecialidad
            );

            formulario.add(
                    new JLabel(
                            "Fecha:"
                    )
            );

            formulario.add(
                    txtFecha
            );

            int opcion =
                    JOptionPane.showConfirmDialog(
                            this,
                            formulario,
                            "Buscar Disponibilidad",
                            JOptionPane.OK_CANCEL_OPTION
                    );

            if (opcion !=
                    JOptionPane.OK_OPTION) {

                return;
            }

            java.util.List<Enfermera> disponibles =
                    gestor.buscarEnfermerasDisponibles(
                            txtEspecialidad.getText(),
                            txtFecha.getText()
                    );

            StringBuilder texto =
                    new StringBuilder();

            texto.append(
                    "--- ENFERMERAS DISPONIBLES ---\n\n"
            );

            if (disponibles.isEmpty()) {

                texto.append(
                        "No hay enfermeras disponibles."
                );

            } else {

                for (Enfermera enfermera :
                        disponibles) {

                    texto.append(
                            enfermera.obtenerIdentificacion()
                    ).append(
                            "\n"
                    );
                }
            }

            mostrarResultado(
                    texto.toString()
            );
        });

        return panel;
    }

    // =====================================================
    // LIMPIAR CAMPOS
    // =====================================================

    private void limpiarCamposEnfermera() {

        txtRut.setText("");
        txtNombre.setText("");
        txtEspecialidad.setText("");
    }

    // =====================================================
    // MOSTRAR RESULTADO
    // =====================================================

    private void mostrarResultado(
            String texto) {

        areaResultados.setText(
                texto
        );
    }

    // =====================================================
    // MOSTRAR MENSAJE
    // =====================================================

    private void mostrarMensaje(
            String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Sistema Hospitalario",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}