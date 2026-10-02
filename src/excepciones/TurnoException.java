package excepciones;

/**
 * Indica que una operación sobre un turno no puede completarse por una
 * condición de negocio, como un identificador duplicado o inexistente.
 */
public class TurnoException extends Exception {

    public TurnoException(String mensaje) {
        super(mensaje);
    }
}
