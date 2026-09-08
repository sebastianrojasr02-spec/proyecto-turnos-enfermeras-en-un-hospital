package modelo;

import java.util.ArrayList;
import java.util.List;

public class Enfermera extends Trabajador {
    private String especialidad;
    private List<Turno> turnosAsignados;

    public Enfermera(String rut, String nombre, String especialidad) {
        super(rut, nombre); // Invocación al constructor de la superclase
        this.especialidad = especialidad;
        this.turnosAsignados = new ArrayList<>(); 
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public List<Turno> getTurnosAsignados() { return turnosAsignados; }
    public void setTurnosAsignados(List<Turno> turnosAsignados) { this.turnosAsignados = turnosAsignados; }
    
    public void agregarTurno(Turno turno) {
        this.turnosAsignados.add(turno);
    }

    // Cumplimiento perfecto del SIA-6
    @Override
    public String obtenerIdentificacion() {
        return "Enfermera [" + super.getRut() + "] - " + super.getNombre() + " (" + especialidad + ")";
    }
}