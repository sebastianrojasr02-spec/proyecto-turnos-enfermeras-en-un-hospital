package excepciones;

/**
 * Indica que una operación requiere una enfermera que no está registrada.
 */
public class EnfermeraNoEncontradaException extends Exception {

    public EnfermeraNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
