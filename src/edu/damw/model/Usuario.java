package edu.damw.model;

import edu.damw.exceptions.LibroNoDisponibleException;
import edu.damw.exceptions.LimitePrestamosExcedidoException;

/**
 * Clase que representa un usuario de la biblioteca.
 * 
 * Demuestra el uso de:
 * - IllegalArgumentException (excepción estándar) para validar datos en el
 * constructor
 * - Excepciones personalizadas (Checked) que deben manejarse con try-catch o
 * throws
 */
public class Usuario {
    private String nombre;
    private int edad;
    private int librosPrestados;
    private static final int LIMITE_PRESTAMOS = 3;

    /**
     * Constructor del usuario.
     * 
     * @param nombre El nombre del usuario (no puede ser nulo ni vacío)
     * @param edad   La edad del usuario (no puede ser negativa)
     * @throws IllegalArgumentException si los datos no son válidos
     */
    public Usuario(String nombre, int edad) {
        // ✅ Validación usando excepciones estándar de Java (Unchecked)
        // No es obligatorio usar try-catch, pero es recomendable
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa.");
        }
        this.nombre = nombre;
        this.edad = edad;
        this.librosPrestados = 0;
    }

    /**
     * Presta un libro al usuario.
     * 
     * @param libro El libro a prestar
     * @throws LimitePrestamosExcedidoException si el usuario ya tiene 3 libros
     *                                          prestados
     * @throws LibroNoDisponibleException       si el libro no está disponible
     */
    public void prestarLibro(Libro libro) throws LimitePrestamosExcedidoException, LibroNoDisponibleException {
        // ⚠️ Verificamos si el usuario ha alcanzado el límite de préstamos
        if (librosPrestados >= LIMITE_PRESTAMOS) {
            throw new LimitePrestamosExcedidoException(nombre, librosPrestados);
        }

        // ⚠️ Verificamos si el libro está disponible
        if (libro.getEstado() != EstadoLibro.DISPONIBLE) {
            throw new LibroNoDisponibleException(libro.getTitulo(), libro.getEstado().name());
        }

        // ✅ Si todo está bien, prestamos el libro
        libro.setEstado(EstadoLibro.PRESTADO);
        librosPrestados++;
        System.out.println("\n✅ Libro prestado con éxito. Total libros actuales: " + librosPrestados);
        System.out.println("   " + nombre + " ha pedido prestado: '" + libro.getTitulo() + "'");
    }

    /**
     * Devuelve un libro prestado.
     * 
     * @param libro El libro a devolver
     */
    public void devolverLibro(Libro libro) {
        if (librosPrestados > 0) {
            // ✅ Actualizamos el estado del libro a DISPONIBLE
            libro.setEstado(EstadoLibro.DISPONIBLE);
            librosPrestados--;
            System.out.println("\n✅ Libro devuelto con éxito. Libros restantes: " + librosPrestados);
            System.out.println("   " + nombre + " ha devuelto: '" + libro.getTitulo() + "'");
        } else {
            System.out.println("\n❌ No hay libros prestados para devolver.");
        }
    }

    // ==================== GETTERS ====================

    public int getLibrosPrestados() {
        return librosPrestados;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    /**
     * Representación en texto del usuario.
     */
    @Override
    public String toString() {
        return "👤 Usuario: " + nombre + " | Edad: " + edad + " | Libros prestados: " + librosPrestados;
    }
}
