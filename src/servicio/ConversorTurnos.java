package servicio;

import modelo.EstadoTurno;
import modelo.TipoTurno;

/**
 * Centraliza la conversión de texto proveniente de las interfaces a los
 * enums utilizados por el dominio.
 */
public final class ConversorTurnos {

    private ConversorTurnos() {
    }

    /**
     * Convierte un texto en TipoTurno sin propagar errores de entrada.
     *
     * @param texto valor recibido desde la interfaz.
     * @return enum correspondiente o null cuando no es válido.
     */
    public static TipoTurno aTipoTurno(String texto) {
        if (texto == null) {
            return null;
        }

        try {
            return TipoTurno.valueOf(texto.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Convierte un texto en EstadoTurno sin propagar errores de entrada.
     *
     * @param texto valor recibido desde la interfaz.
     * @return enum correspondiente o null cuando no es válido.
     */
    public static EstadoTurno aEstadoTurno(String texto) {
        if (texto == null) {
            return null;
        }

        try {
            return EstadoTurno.valueOf(texto.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
