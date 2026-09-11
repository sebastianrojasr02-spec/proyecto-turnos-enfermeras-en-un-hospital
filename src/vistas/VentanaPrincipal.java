package vista;

import controlador.GestorHospital;
import modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private GestorHospital gestor;

    private JTextField txtRut, txtNombre, txtEspecialidad;
    private JTable tablaEnfermeras;
    private DefaultTableModel modeloTablaEnf;

    private JTextField txtRutTurno, txtIdTurno, txtFechaTurno;
    private JComboBox<String> cmbTipoTurno;

    public VentanaPrincipal(GestorHospital gestor) {
        this.gestor = gestor;

        setTitle("Sistema de Gestión Hospitalaria (SIA)");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Enfermeras", crearPanelEnfermeras());
        tabbedPane.addTab("Asignación de Turnos", crearPanelTurnos());

        add(tabbedPane);
        actualizarTabla();
    }

    private JPanel crearPanelEnfermeras() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar / Modificar Enfermera"));

        panelForm.add(new JLabel("RUT:"));
        txtRut = new JTextField();
        panelForm.add(txtRut);

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Especialidad:"));
        txtEspecialidad = new JTextField();
        panelForm.add(txtEspecialidad);

        JButton btnAgregar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        panelForm.add(btnAgregar);
        panelForm.add(btnEliminar);

        panel.add(panelForm, BorderLayout.NORTH);

        modeloTablaEnf = new DefaultTableModel(new String[]{"RUT", "Nombre", "Especialidad", "N° Turnos"}, 0);
        tablaEnfermeras = new JTable(modeloTablaEnf);
        panel.add(new JScrollPane(tablaEnfermeras), BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> {
            boolean exito = gestor.agregarEnfermera(txtRut.getText(), txtNombre.getText(), txtEspecialidad.getText());
            if (!exito) {
                gestor.modificarEnfermera(txtRut.getText(), txtNombre.getText(), txtEspecialidad.getText());
            }
            actualizarTabla();
            limpiarCampos();
        });

        btnEliminar.addActionListener(e -> {
            gestor.eliminarEnfermera(txtRut.getText());
            actualizarTabla();
            limpiarCampos();
        });

        return panel;
    }

    private JPanel crearPanelTurnos() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Asignar Nuevo Turno"));

        panel.add(new JLabel("RUT Enfermera:"));
        txtRutTurno = new JTextField();
        panel.add(txtRutTurno);

        panel.add(new JLabel("ID Turno:"));
        txtIdTurno = new JTextField();
        panel.add(txtIdTurno);

        panel.add(new JLabel("Fecha (DD-MM-AAAA):"));
        txtFechaTurno = new JTextField();
        panel.add(txtFechaTurno);

        panel.add(new JLabel("Tipo:"));
        cmbTipoTurno = new JComboBox<>(new String[]{"Manana", "Tarde", "Noche"});
        panel.add(cmbTipoTurno);

        JButton btnAsignar = new JButton("Asignar Turno");
        panel.add(btnAsignar);

        btnAsignar.addActionListener(e -> {
            Enfermera enf = gestor.buscarEnfermera(txtRutTurno.getText());
            if (enf != null) {
                enf.agregarTurno(txtIdTurno.getText(), txtFechaTurno.getText(), (String) cmbTipoTurno.getSelectedItem());
                gestor.guardarDatos();
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Turno asignado con éxito.");
            } else {
                JOptionPane.showMessageDialog(this, "Enfermera no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private void actualizarTabla() {
        modeloTablaEnf.setRowCount(0);
        for (Enfermera e : gestor.getMapaEnfermeras().values()) {
            modeloTablaEnf.addRow(new Object[]{
                    e.getRut(),
                    e.getNombre(),
                    e.getEspecialidad(),
                    e.getTurnosAsignados().size()
            });
        }
    }

    private void limpiarCampos() {
        txtRut.setText("");
        txtNombre.setText("");
        txtEspecialidad.setText("");
    }
}