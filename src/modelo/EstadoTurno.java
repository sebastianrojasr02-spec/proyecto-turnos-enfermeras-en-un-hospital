package modelo;

import java.io.Serializable;

public enum EstadoTurno implements Serializable {
    PENDIENTE,
    CONFIRMADO,
    CANCELADO,
    COMPLETADO
}