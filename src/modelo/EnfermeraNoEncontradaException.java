package modelo;

/**
 * Excepción utilizada cuando una operación requiere una enfermera
 * que no se encuentra registrada en el sistema.
 */
public class EnfermeraNoEncontradaException extends Exception {

    public EnfermeraNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}