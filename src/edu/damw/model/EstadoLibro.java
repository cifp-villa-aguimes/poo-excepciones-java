package edu.damw.model;

/**
 * Enum que representa los posibles estados de un libro.
 * - DISPONIBLE: El libro está listo para ser prestado
 * - PRESTADO: El libro está en manos de un usuario
 * - RESERVADO: El libro está reservado para otro usuario
 */
public enum EstadoLibro {
    DISPONIBLE, PRESTADO, RESERVADO
}
