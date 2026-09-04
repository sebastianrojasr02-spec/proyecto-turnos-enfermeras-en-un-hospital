package modelo;

/**
 * Representa un turno de trabajo en el hospital.
 */
public class Turno {
    // SIA-3: Todos los atributos son privados
    private String idTurno;
    private String fecha; 
    private String tipo;  
    private String estado; 

    public Turno(String idTurno, String fecha, String tipo, String estado) {
        this.idTurno = idTurno;
        this.fecha = fecha;
        this.tipo = tipo;
        this.estado = estado;
    }

    // SIA-3: Métodos de lectura (getters) y escritura (setters)
    public String getIdTurno() {
        return idTurno;
    }
    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return "Turno [" + idTurno + "] - Fecha: " + fecha + " | Tipo: " + tipo + " | Estado: " + estado;
    }
}