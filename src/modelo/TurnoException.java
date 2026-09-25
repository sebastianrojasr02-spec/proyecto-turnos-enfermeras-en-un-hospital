package modelo;

/**
 * Excepción utilizada cuando una operación relacionada con un turno
 * no puede realizarse correctamente.
 */
public class TurnoException extends Exception {

    public TurnoException(String mensaje) {
        super(mensaje);
    }
}