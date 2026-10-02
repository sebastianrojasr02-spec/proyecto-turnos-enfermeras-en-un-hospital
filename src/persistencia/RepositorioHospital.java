package persistencia;

import java.util.Map;
import modelo.Enfermera;

/**
 * Abstracción de la persistencia utilizada por el gestor del hospital.
 * Permite cambiar el mecanismo de almacenamiento sin modificar la lógica
 * de negocio.
 */
public interface RepositorioHospital {

    /**
     * Carga el estado de las enfermeras y sus turnos.
     *
     * @return mapa con los datos recuperados.
     */
    Map<String, Enfermera> cargar();

    /**
     * Persiste el estado actual del sistema.
     *
     * @param enfermeras datos que serán almacenados.
     * @return true si el almacenamiento finaliza correctamente.
     */
    boolean guardar(Map<String, Enfermera> enfermeras);
}
