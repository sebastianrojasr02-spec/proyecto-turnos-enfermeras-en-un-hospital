package vista;

import controlador.GestorHospital;
import modelo.Enfermera;
import modelo.Turno;
import modelo.TipoTurno;
import modelo.EstadoTurno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private GestorHospital gestor;

    // =========================================================
    // ENFERMERAS
    // =========================================================

    private JTextField txtRut;
    private JTextField txtNombre;
    private JTextField txtEspecialidad;

    private JTable tablaEnfermeras;
    private DefaultTableModel modeloTablaEnfermeras;

    // =========================================================
    // TURNOS
    // =========================================================

    private JTextField txtRutTurno;
    private JTextField txtIdTurno;
    private JTextField txtFechaTurno;

    private JComboBox<String> cmbTipoTurno;
    private JComboBox<String> cmbEstadoTurno;

    private JTextArea areaTurnos;

    // =========================================================
    // CONSULTAS
    // =========================================================

    private JTextField txtEspecialidadConsulta;
    private JTextArea areaConsulta;

    // =========================================================
    // DISPONIBILIDAD
    // =========================================================

    private JTextField txtEspecialidadDisponible;
    private JTextField txtFechaDisponible;

    private JTable tablaDisponibles;
    private DefaultTableModel modeloTablaDisponibles;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public VentanaPrincipal(GestorHospital gestor) {

        this.gestor = gestor;

        setTitle("Sistema de Gestión Hospitalaria - SIA");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestanas = new JTabbedPane();

        pestanas.addTab(
                "Enfermeras",
                crearPanelEnfermeras()
        );

        pestanas.addTab(
                "Turnos",
                crearPanelTurnos()
        );

        pestanas.addTab(
                "Consultas",
                crearPanelConsultas()
        );

        pestanas.addTab(
                "Disponibilidad",
                crearPanelDisponibilidad()
        );

        add(pestanas);

        actualizarTablaEnfermeras();
    }

    // =========================================================
    // PANEL ENFERMERAS
    // =========================================================

    private JPanel crearPanelEnfermeras() {

        JPanel panel = new JPanel(
                new BorderLayout(10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        // -----------------------------------------------------
        // FORMULARIO
        // -----------------------------------------------------

        JPanel formulario = new JPanel(
                new GridLayout(3, 2, 8, 8)
        );

        formulario.setBorder(
                BorderFactory.createTitledBorder(
                        "Datos de la Enfermera"
                )
        );

        formulario.add(new JLabel("RUT:"));

        txtRut = new JTextField();
        formulario.add(txtRut);

        formulario.add(new JLabel("Nombre:"));

        txtNombre = new JTextField();
        formulario.add(txtNombre);

        formulario.add(new JLabel("Especialidad:"));

        txtEspecialidad = new JTextField();
        formulario.add(txtEspecialidad);

        panel.add(
                formulario,
                BorderLayout.NORTH
        );

        // -----------------------------------------------------
        // TABLA
        // -----------------------------------------------------

        modeloTablaEnfermeras =
                new DefaultTableModel(
                        new Object[]{
                                "RUT",
                                "Nombre",
                                "Especialidad",
                                "N° Turnos"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int fila,
                            int columna) {

                        return false;
                    }
                };

        tablaEnfermeras =
                new JTable(modeloTablaEnfermeras);

        tablaEnfermeras.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tablaEnfermeras.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int fila =
                                tablaEnfermeras
                                        .getSelectedRow();

                        if (fila >= 0) {

                            txtRut.setText(
                                    modeloTablaEnfermeras
                                            .getValueAt(
                                                    fila, 0
                                            )
                                            .toString()
                            );

                            txtNombre.setText(
                                    modeloTablaEnfermeras
                                            .getValueAt(
                                                    fila, 1
                                            )
                                            .toString()
                            );

                            txtEspecialidad.setText(
                                    modeloTablaEnfermeras
                                            .getValueAt(
                                                    fila, 2
                                            )
                                            .toString()
                            );
                        }
                    }
                });

        panel.add(
                new JScrollPane(tablaEnfermeras),
                BorderLayout.CENTER
        );

        // -----------------------------------------------------
        // BOTONES
        // -----------------------------------------------------

        JPanel botones = new JPanel();

        JButton btnAgregar =
                new JButton("Agregar");

        JButton btnModificar =
                new JButton("Modificar");

        JButton btnEliminar =
                new JButton("Eliminar");

        JButton btnBuscar =
                new JButton("Buscar");

        JButton btnLimpiar =
                new JButton("Limpiar");

        botones.add(btnAgregar);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        botones.add(btnBuscar);
        botones.add(btnLimpiar);

        panel.add(
                botones,
                BorderLayout.SOUTH
        );

        // =====================================================
        // AGREGAR
        // =====================================================

        btnAgregar.addActionListener(e -> {

            if (camposEnfermeraVacios()) {

                mostrarAdvertencia(
                        "Debe completar todos los campos."
                );

                return;
            }

            boolean agregado =
                    gestor.agregarEnfermera(
                            txtRut.getText(),
                            txtNombre.getText(),
                            txtEspecialidad.getText()
                    );

            if (agregado) {

                mostrarMensaje(
                        "Enfermera agregada correctamente."
                );

                actualizarTablaEnfermeras();
                limpiarCamposEnfermera();

            } else {

                mostrarAdvertencia(
                        "Ya existe una enfermera con ese RUT."
                );
            }
        });

        // =====================================================
        // MODIFICAR
        // =====================================================

        btnModificar.addActionListener(e -> {

            if (txtRut.getText().trim().isEmpty()) {

                mostrarAdvertencia(
                        "Ingrese el RUT de la enfermera."
                );

                return;
            }

            boolean modificado =
                    gestor.modificarEnfermera(
                            txtRut.getText(),
                            txtNombre.getText(),
                            txtEspecialidad.getText()
                    );

            if (modificado) {

                mostrarMensaje(
                        "Enfermera modificada correctamente."
                );

                actualizarTablaEnfermeras();
                limpiarCamposEnfermera();

            } else {

                mostrarError(
                        "No se encontró una enfermera con ese RUT."
                );
            }
        });

        // =====================================================
        // ELIMINAR
        // =====================================================

        btnEliminar.addActionListener(e -> {

            String rut =
                    txtRut.getText().trim();

            if (rut.isEmpty()) {

                mostrarAdvertencia(
                        "Ingrese el RUT de la enfermera."
                );

                return;
            }

            int respuesta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Está seguro de eliminar esta enfermera?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION
                    );

            if (respuesta ==
                    JOptionPane.YES_OPTION) {

                boolean eliminada =
                        gestor.eliminarEnfermera(rut);

                if (eliminada) {

                    mostrarMensaje(
                            "Enfermera eliminada correctamente."
                    );

                    actualizarTablaEnfermeras();
                    limpiarCamposEnfermera();

                } else {

                    mostrarError(
                            "No se encontró la enfermera."
                    );
                }
            }
        });

        // =====================================================
        // BUSCAR
        // =====================================================

        btnBuscar.addActionListener(e -> {

            String rut =
                    txtRut.getText().trim();

            if (rut.isEmpty()) {

                mostrarAdvertencia(
                        "Ingrese un RUT para buscar."
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

                seleccionarEnfermeraTabla(rut);

            } else {

                mostrarError(
                        "No se encontró una enfermera con ese RUT."
                );
            }
        });

        // =====================================================
        // LIMPIAR
        // =====================================================

        btnLimpiar.addActionListener(e ->
                limpiarCamposEnfermera()
        );

        return panel;
    }

    // =========================================================
    // PANEL TURNOS
    // =========================================================

    private JPanel crearPanelTurnos() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        JPanel formulario =
                new JPanel(
                        new GridLayout(5, 2, 8, 8)
                );

        formulario.setBorder(
                BorderFactory.createTitledBorder(
                        "Gestión de Turnos"
                )
        );

        formulario.add(
                new JLabel("RUT Enfermera:")
        );

        txtRutTurno =
                new JTextField();

        formulario.add(txtRutTurno);

        formulario.add(
                new JLabel("ID Turno:")
        );

        txtIdTurno =
                new JTextField();

        formulario.add(txtIdTurno);

        formulario.add(
                new JLabel("Fecha (DD-MM-AAAA):")
        );

        txtFechaTurno =
                new JTextField();

        formulario.add(txtFechaTurno);

        formulario.add(
                new JLabel("Tipo:")
        );

        cmbTipoTurno =
                new JComboBox<>(
                        new String[]{
                                "Manana",
                                "Tarde",
                                "Noche"
                        }
                );

        formulario.add(cmbTipoTurno);

        formulario.add(
                new JLabel("Estado:")
        );

        cmbEstadoTurno =
                new JComboBox<>(
                        new String[]{
                                "PENDIENTE",
                                "CONFIRMADO",
                                "CANCELADO"
                        }
                );

        formulario.add(cmbEstadoTurno);

        panel.add(
                formulario,
                BorderLayout.NORTH
        );

        // -----------------------------------------------------
        // AREA DE INFORMACION
        // -----------------------------------------------------

        areaTurnos =
                new JTextArea();

        areaTurnos.setEditable(false);

        areaTurnos.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        14
                )
        );

        panel.add(
                new JScrollPane(areaTurnos),
                BorderLayout.CENTER
        );

        // -----------------------------------------------------
        // BOTONES
        // -----------------------------------------------------

        JPanel botones =
                new JPanel();

        JButton btnAsignar =
                new JButton("Asignar");

        JButton btnBuscar =
                new JButton("Buscar");

        JButton btnModificar =
                new JButton("Modificar");

        JButton btnEliminar =
                new JButton("Eliminar");

        JButton btnLimpiar =
                new JButton("Limpiar");

        botones.add(btnAsignar);
        botones.add(btnBuscar);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);

        panel.add(
                botones,
                BorderLayout.SOUTH
        );

        // =====================================================
        // ASIGNAR TURNO
        // =====================================================

        btnAsignar.addActionListener(e -> {

            if (txtRutTurno.getText()
                    .trim().isEmpty()
                    || txtIdTurno.getText()
                    .trim().isEmpty()
                    || txtFechaTurno.getText()
                    .trim().isEmpty()) {

                mostrarAdvertencia(
                        "Complete todos los campos."
                );

                return;
            }

            Enfermera enfermera =
                    gestor.buscarEnfermera(
                            txtRutTurno.getText()
                    );

            if (enfermera == null) {

                mostrarError(
                        "No se encontró la enfermera."
                );

                return;
            }

            enfermera.agregarTurno(
                    txtIdTurno.getText(),
                    txtFechaTurno.getText(),
                    (String) cmbTipoTurno
                            .getSelectedItem()
            );

            gestor.guardarDatos();

            mostrarMensaje(
                    "Turno asignado correctamente."
            );

            mostrarTurnos(enfermera);
            actualizarTablaEnfermeras();
        });

        // =====================================================
        // BUSCAR TURNO
        // =====================================================

        btnBuscar.addActionListener(e -> {

            String rut =
                    txtRutTurno.getText().trim();

            String id =
                    txtIdTurno.getText().trim();

            Turno turno =
                    gestor.buscarTurnoDeEnfermera(
                            rut,
                            id
                    );

            if (turno != null) {

                areaTurnos.setText(
                        turno.toString()
                );

            } else {

                mostrarError(
                        "No se encontró el turno."
                );
            }
        });

        // =====================================================
        // MODIFICAR TURNO
        // =====================================================

        btnModificar.addActionListener(e -> {

            String rut =
                    txtRutTurno.getText().trim();

            String id =
                    txtIdTurno.getText().trim();

            TipoTurno tipo =
                    obtenerTipoTurno();

            EstadoTurno estado =
                    obtenerEstadoTurno();

            boolean modificado =
                    gestor.modificarTurno(
                            rut,
                            id,
                            txtFechaTurno.getText(),
                            tipo,
                            estado
                    );

            if (modificado) {

                mostrarMensaje(
                        "Turno modificado correctamente."
                );

                Enfermera enfermera =
                        gestor.buscarEnfermera(rut);

                if (enfermera != null) {
                    mostrarTurnos(enfermera);
                }

            } else {

                mostrarError(
                        "No se encontró el turno."
                );
            }
        });

        // =====================================================
        // ELIMINAR TURNO
        // =====================================================

        btnEliminar.addActionListener(e -> {

            String rut =
                    txtRutTurno.getText().trim();

            String id =
                    txtIdTurno.getText().trim();

            int respuesta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Eliminar este turno?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION
                    );

            if (respuesta !=
                    JOptionPane.YES_OPTION) {

                return;
            }

            boolean eliminado =
                    gestor.eliminarTurno(
                            rut,
                            id
                    );

            if (eliminado) {

                mostrarMensaje(
                        "Turno eliminado correctamente."
                );

                Enfermera enfermera =
                        gestor.buscarEnfermera(rut);

                if (enfermera != null) {
                    mostrarTurnos(enfermera);
                }

                actualizarTablaEnfermeras();

            } else {

                mostrarError(
                        "No se encontró el turno."
                );
            }
        });

        // =====================================================
        // LIMPIAR
        // =====================================================

        btnLimpiar.addActionListener(e -> {

            txtRutTurno.setText("");
            txtIdTurno.setText("");
            txtFechaTurno.setText("");

            areaTurnos.setText("");
        });

        return panel;
    }

    // =========================================================
    // PANEL CONSULTAS
    // =========================================================

    private JPanel crearPanelConsultas() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        JPanel superior =
                new JPanel(
                        new GridLayout(1, 3, 8, 8)
                );

        superior.setBorder(
                BorderFactory.createTitledBorder(
                        "Buscar por Especialidad"
                )
        );

        superior.add(
                new JLabel("Especialidad:")
        );

        txtEspecialidadConsulta =
                new JTextField();

        superior.add(
                txtEspecialidadConsulta
        );

        JButton btnBuscar =
                new JButton("Buscar");

        superior.add(btnBuscar);

        panel.add(
                superior,
                BorderLayout.NORTH
        );

        areaConsulta =
                new JTextArea();

        areaConsulta.setEditable(false);

        areaConsulta.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        14
                )
        );

        panel.add(
                new JScrollPane(areaConsulta),
                BorderLayout.CENTER
        );

        btnBuscar.addActionListener(e -> {

            String especialidad =
                    txtEspecialidadConsulta
                            .getText()
                            .trim();

            if (especialidad.isEmpty()) {

                mostrarAdvertencia(
                        "Ingrese una especialidad."
                );

                return;
            }

            areaConsulta.setText("");

            for (Enfermera enfermera :
                    gestor.buscarPorEspecialidad(
                            especialidad)) {

                areaConsulta.append(
                        "RUT: "
                        + enfermera.getRut()
                        + "\n"
                );

                areaConsulta.append(
                        "Nombre: "
                        + enfermera.getNombre()
                        + "\n"
                );

                areaConsulta.append(
                        "Especialidad: "
                        + enfermera.getEspecialidad()
                        + "\n"
                );

                areaConsulta.append(
                        "Turnos: "
                        + enfermera
                                .getTurnosAsignados()
                                .size()
                        + "\n"
                );

                areaConsulta.append(
                        "-----------------------------\n"
                );
            }

            if (areaConsulta.getText().isEmpty()) {

                areaConsulta.setText(
                        "No se encontraron enfermeras."
                );
            }
        });

        return panel;
    }

    // =========================================================
    // PANEL DISPONIBILIDAD
    // =========================================================

    private JPanel crearPanelDisponibilidad() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        JPanel formulario =
                new JPanel(
                        new GridLayout(1, 4, 8, 8)
                );

        formulario.setBorder(
                BorderFactory.createTitledBorder(
                        "Buscar Enfermeras Disponibles"
                )
        );

        formulario.add(
                new JLabel("Especialidad:")
        );

        txtEspecialidadDisponible =
                new JTextField();

        formulario.add(
                txtEspecialidadDisponible
        );

        formulario.add(
                new JLabel("Fecha:")
        );

        txtFechaDisponible =
                new JTextField();

        formulario.add(
                txtFechaDisponible
        );

        panel.add(
                formulario,
                BorderLayout.NORTH
        );

        modeloTablaDisponibles =
                new DefaultTableModel(
                        new Object[]{
                                "RUT",
                                "Nombre",
                                "Especialidad"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int fila,
                            int columna) {

                        return false;
                    }
                };

        tablaDisponibles =
                new JTable(
                        modeloTablaDisponibles
                );

        panel.add(
                new JScrollPane(tablaDisponibles),
                BorderLayout.CENTER
        );

        JButton btnBuscar =
                new JButton(
                        "Buscar Disponibles"
                );

        panel.add(
                btnBuscar,
                BorderLayout.SOUTH
        );

        btnBuscar.addActionListener(e -> {

            String especialidad =
                    txtEspecialidadDisponible
                            .getText()
                            .trim();

            String fecha =
                    txtFechaDisponible
                            .getText()
                            .trim();

            if (especialidad.isEmpty()
                    || fecha.isEmpty()) {

                mostrarAdvertencia(
                        "Complete especialidad y fecha."
                );

                return;
            }

            modeloTablaDisponibles
                    .setRowCount(0);

            for (Enfermera enfermera :
                    gestor.buscarEnfermerasDisponibles(
                            especialidad,
                            fecha
                    )) {

                modeloTablaDisponibles.addRow(
                        new Object[]{
                                enfermera.getRut(),
                                enfermera.getNombre(),
                                enfermera.getEspecialidad()
                        }
                );
            }
        });

        return panel;
    }

    // =========================================================
    // MOSTRAR TURNOS
    // =========================================================

    private void mostrarTurnos(
            Enfermera enfermera) {

        areaTurnos.setText("");

        areaTurnos.append(
                "RUT: "
                + enfermera.getRut()
                + "\n"
        );

        areaTurnos.append(
                "Nombre: "
                + enfermera.getNombre()
                + "\n"
        );

        areaTurnos.append(
                "Especialidad: "
                + enfermera.getEspecialidad()
                + "\n\n"
        );

        areaTurnos.append(
                "TURNOS ASIGNADOS\n"
        );

        areaTurnos.append(
                "=============================\n"
        );

        for (Turno turno :
                enfermera.getTurnosAsignados()) {

            areaTurnos.append(
                    turno.toString()
                    + "\n"
            );

            areaTurnos.append(
                    "-----------------------------\n"
            );
        }

        if (enfermera
                .getTurnosAsignados()
                .isEmpty()) {

            areaTurnos.append(
                    "No tiene turnos asignados.\n"
            );
        }
    }

    // =========================================================
    // ACTUALIZAR TABLA
    // =========================================================

    private void actualizarTablaEnfermeras() {

        if (modeloTablaEnfermeras == null) {
            return;
        }

        modeloTablaEnfermeras
                .setRowCount(0);

        for (Enfermera enfermera :
                gestor.getMapaEnfermeras()
                        .values()) {

            modeloTablaEnfermeras.addRow(
                    new Object[]{
                            enfermera.getRut(),
                            enfermera.getNombre(),
                            enfermera.getEspecialidad(),
                            enfermera
                                    .getTurnosAsignados()
                                    .size()
                    }
            );
        }
    }

    // =========================================================
    // SELECCIONAR ENFERMERA EN TABLA
    // =========================================================

    private void seleccionarEnfermeraTabla(
            String rut) {

        for (int i = 0;
                i < modeloTablaEnfermeras
                        .getRowCount();
                i++) {

            if (modeloTablaEnfermeras
                    .getValueAt(i, 0)
                    .toString()
                    .equals(rut)) {

                tablaEnfermeras
                        .setRowSelectionInterval(
                                i,
                                i
                        );

                break;
            }
        }
    }

    // =========================================================
    // OBTENER TIPO DE TURNO
    // =========================================================

    private TipoTurno obtenerTipoTurno() {

        String tipo =
                (String) cmbTipoTurno
                        .getSelectedItem();

        if (tipo.equals("Tarde")) {
            return TipoTurno.TARDE;
        }

        if (tipo.equals("Noche")) {
            return TipoTurno.NOCHE;
        }

        return TipoTurno.MANANA;
    }

    // =========================================================
    // OBTENER ESTADO DE TURNO
    // =========================================================

    private EstadoTurno obtenerEstadoTurno() {

        String estado =
                (String) cmbEstadoTurno
                        .getSelectedItem();

        if (estado.equals("CONFIRMADO")) {
            return EstadoTurno.CONFIRMADO;
        }

        if (estado.equals("CANCELADO")) {
            return EstadoTurno.CANCELADO;
        }

        return EstadoTurno.PENDIENTE;
    }

    // =========================================================
    // VALIDAR CAMPOS
    // =========================================================

    private boolean camposEnfermeraVacios() {

        return txtRut.getText()
                .trim().isEmpty()

                || txtNombre.getText()
                .trim().isEmpty()

                || txtEspecialidad.getText()
                .trim().isEmpty();
    }

    // =========================================================
    // LIMPIAR ENFERMERA
    // =========================================================

    private void limpiarCamposEnfermera() {

        txtRut.setText("");
        txtNombre.setText("");
        txtEspecialidad.setText("");

        if (tablaEnfermeras != null) {
            tablaEnfermeras.clearSelection();
        }
    }

    // =========================================================
    // MENSAJES
    // =========================================================

    private void mostrarMensaje(
            String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Sistema Hospitalario",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void mostrarAdvertencia(
            String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void mostrarError(
            String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}