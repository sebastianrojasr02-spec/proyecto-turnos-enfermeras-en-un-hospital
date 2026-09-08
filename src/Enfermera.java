package modelo;

import java.util.ArrayList;
import java.util.List;

public class Enfermera extends Trabajador {
    private String especialidad;
    private List<Turno> turnosAsignados;

    // 1. Constructor por defecto
    public Enfermera() {
        super(); // Llama al constructor por defecto de Trabajador
        this.especialidad = "General";
        this.turnosAsignados = new ArrayList<>(); 
    }

    // 2. Constructor con parámetros
    public Enfermera(String rut, String nombre, String especialidad) {
        super(rut, nombre); 
        this.especialidad = especialidad;
        this.turnosAsignados = new ArrayList<>(); 
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public List<Turno> getTurnosAsignados() { return turnosAsignados; }
    public void setTurnosAsignados(List<Turno> turnosAsignados) { this.turnosAsignados = turnosAsignados; }
    
    // =====================================================================
    // SIA-5: Sobrecarga de Métodos
    // =====================================================================
    
    // Método 1: Recibe el objeto Turno ya instanciado
    public void agregarTurno(Turno turno) {
        this.turnosAsignados.add(turno);
    }

    // Método 2 (Sobrecarga): Recibe los datos primitivos, lo instancia y lo agrega, 
    // replicando el ejemplo de 'depositar' de la ayudantía.
    public void agregarTurno(String idTurno, String fecha, String tipo) {
        Turno nuevoTurno = new Turno(idTurno, fecha, tipo, "Asignado");
        this.turnosAsignados.add(nuevoTurno);
    }

    @Override
    public String obtenerIdentificacion() {
        return "Enfermera [" + super.getRut() + "] - " + super.getNombre() + " (" + especialidad + ")";
    }
}