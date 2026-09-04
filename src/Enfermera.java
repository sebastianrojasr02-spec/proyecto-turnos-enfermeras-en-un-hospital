package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa al personal de enfermería y contiene sus turnos asignados.
 */
public class Enfermera {
    private String rut;
    private String nombre;
    private String especialidad;
    
    // SIA-4: Colección anidada. Una enfermera tiene una lista de Turnos en su interior.
    private List<Turno> turnosAsignados;

    public Enfermera(String rut, String nombre, String especialidad) {
        this.rut = rut;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.turnosAsignados = new ArrayList<>(); // Se inicializa vacía
    }

    public String getRut() {
        return rut;
    }
    public void setRut(String rut) { 
        this.rut = rut;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public List<Turno> getTurnosAsignados() {
        return turnosAsignados;
    }
    public void setTurnosAsignados(List<Turno> turnosAsignados) {
        this.turnosAsignados = turnosAsignados;
    }
    
    /**
     * Método para agregar un turno a la lista de esta enfermera.
     */
    public void agregarTurno(Turno turno) {
        this.turnosAsignados.add(turno);
    }
    
    @Override
    public String toString() {
        return "Enfermera: " + nombre + " (RUT: " + rut + ") - Especialidad: " + especialidad;
    }
}