package edu.damw.model;

/**
 * Clase que representa un libro en el sistema de biblioteca.
 * Utiliza IllegalArgumentException para validar datos de entrada.
 */
public class Libro {
    private String titulo;
    private EstadoLibro estado;

    /**
     * Constructor del libro.
     * 
     * @param titulo El título del libro (no puede ser nulo ni vacío)
     * @throws IllegalArgumentException si el título es nulo o vacío
     */
    public Libro(String titulo) {
        // ✅ Validación usando excepción estándar de Java
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título del libro no puede estar vacío.");
        }
        this.titulo = titulo;
        this.estado = EstadoLibro.DISPONIBLE; // Por defecto, el libro está disponible
    }

    public String getTitulo() {
        return titulo;
    }

    public EstadoLibro getEstado() {
        return estado;
    }

    public void setEstado(EstadoLibro nuevoEstado) {
        this.estado = nuevoEstado;
    }

    /**
     * Representación en texto del libro.
     * Útil para mostrar información en consola.
     */
    @Override
    public String toString() {
        return "📖 Libro: '" + titulo + "' | Estado: " + estado;
    }
}
