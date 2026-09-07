package controlador;

import java.util.HashMap;
import java.util.Map;
import modelo.Enfermera;
import modelo.Turno;

public class GestorHospital {
    
    // SIA-4: Primera colección es un Mapa (RUT -> Enfermera). 
    // La segunda colección está anidada dentro de cada Enfermera (List<Turno>).
    private Map<String, Enfermera> mapaEnfermeras;

    public GestorHospital() {
        this.mapaEnfermeras = new HashMap<>();
        cargarDatosIniciales();
    }

    // SIA-3: Datos iniciales en el código para permitir ejecución inmediata
    private void cargarDatosIniciales() {
        // Creamos enfermeras
        Enfermera enf1 = new Enfermera("11111111-1", "Ana Rojas", "Urgencias");
        Enfermera enf2 = new Enfermera("22222222-2", "Bárbara Silva", "Pediatría");
        
        // Creamos turnos y los anidamos en las enfermeras
        enf1.agregarTurno(new Turno("T001", "10-09-2026", "Mañana", "Confirmado"));
        enf2.agregarTurno(new Turno("T002", "11-09-2026", "Noche", "Pendiente"));

        // Guardamos en el Mapa
        mapaEnfermeras.put(enf1.getRut(), enf1);
        mapaEnfermeras.put(enf2.getRut(), enf2);
    }

    // =====================================================================
    // SIA-5: Sobrecarga de métodos (Mismo nombre, diferentes parámetros)
    // =====================================================================
    
    /**
     * Sobrecarga 1: Agrega recibiendo el objeto Enfermera ya instanciado.
     */
    public boolean agregarEnfermera(Enfermera nuevaEnfermera) {
        if (mapaEnfermeras.containsKey(nuevaEnfermera.getRut())) {
            return false; // El RUT ya existe, no se agrega
        }
        mapaEnfermeras.put(nuevaEnfermera.getRut(), nuevaEnfermera);
        return true;
    }

    /**
     * Sobrecarga 2: Agrega recibiendo los datos sueltos y crea el objeto internamente.
     */
    public boolean agregarEnfermera(String rut, String nombre, String especialidad) {
        Enfermera nueva = new Enfermera(rut, nombre, especialidad);
        return agregarEnfermera(nueva); // Reutiliza la sobrecarga 1
    }

    // Getter para obtener el mapa completo cuando se necesite mostrar
    public Map<String, Enfermera> getMapaEnfermeras() {
        return mapaEnfermeras;
    }
}