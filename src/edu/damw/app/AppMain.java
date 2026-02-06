package edu.damw.app;

import edu.damw.controller.GestorUsuarios;
import edu.damw.exceptions.LibroNoDisponibleException;
import edu.damw.exceptions.LimitePrestamosExcedidoException;
import edu.damw.exceptions.UsuarioNoEncontradoException;
import edu.damw.model.Libro;
import edu.damw.model.Usuario;

/**
 * Clase principal que demuestra el uso de excepciones en Java.
 * 
 * Este ejemplo muestra:
 * 1. Excepciones estándar de Java (IllegalArgumentException,
 * NullPointerException)
 * 2. Excepciones personalizadas (Checked) que heredan de Exception
 * 3. Uso de bloques try-catch para manejar errores
 * 4. Multi-catch para capturar varias excepciones en un solo bloque
 */
public class AppMain {
    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║     📚 SISTEMA DE BIBLIOTECA - MANEJO DE EXCEPCIONES 📚     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // ================================================================
        // PARTE 1: Creación de libros con validación
        // ================================================================
        System.out.println("\n📖 === PARTE 1: CREACIÓN DE LIBROS ===");

        Libro libro1 = crearLibro("El Señor de los Anillos");
        Libro libro2 = crearLibro("Cien años de soledad");
        Libro libro3 = crearLibro("Don Quijote de la Mancha");
        Libro libro4 = crearLibro("El Principito");

        // ❌ Intentamos crear un libro sin título → IllegalArgumentException
        Libro libroInvalido = crearLibro("");

        // ================================================================
        // PARTE 2: Creación de usuarios con validación
        // Demuestra el uso de IllegalArgumentException (Unchecked)
        // ================================================================
        System.out.println("\n👤 === PARTE 2: CREACIÓN DE USUARIOS ===");
        System.out.println("Probando validación con IllegalArgumentException...\n");

        // ❌ Intentamos crear usuarios con datos inválidos
        Usuario usuarioInvalido1 = crearUsuario("", 25); // Nombre vacío
        Usuario usuarioInvalido2 = crearUsuario("Ana", -5); // Edad negativa

        // ✅ Creamos un usuario válido
        Usuario juan = crearUsuario("Juan", 25);

        // ================================================================
        // PARTE 3: Préstamo de libros
        // Demuestra excepciones personalizadas (Checked)
        // ================================================================
        System.out.println("\n📚 === PARTE 3: PRÉSTAMO DE LIBROS ===");
        System.out.println("Probando LibroNoDisponibleException y LimitePrestamosExcedidoException...\n");

        if (juan != null) {
            // Bloque try-catch con multi-catch (Java 7+)
            try {
                juan.prestarLibro(libro1); // ✅ Éxito
                juan.prestarLibro(libro1); // ❌ Error: ya está prestado
            } catch (LimitePrestamosExcedidoException | LibroNoDisponibleException e) {
                System.out.println("\n❌ Error: " + e.getMessage());
            }

            try {
                juan.prestarLibro(libro2); // ✅ Éxito (libro 2)
                juan.prestarLibro(libro3); // ✅ Éxito (libro 3) - límite alcanzado
                juan.prestarLibro(libro4); // ❌ Error: límite de 3 libros excedido
            } catch (LimitePrestamosExcedidoException | LibroNoDisponibleException e) {
                System.out.println("\n❌ Error: " + e.getMessage());
            }
        }

        // ================================================================
        // PARTE 4: Devolución de libros
        // ================================================================
        System.out.println("\n📥 === PARTE 4: DEVOLUCIÓN DE LIBROS ===");

        if (juan != null && juan.getLibrosPrestados() > 0) {
            juan.devolverLibro(libro1);
            juan.devolverLibro(libro3);
            juan.devolverLibro(libro2);
            juan.devolverLibro(libro4); // ❌ No tiene más libros prestados
        }

        // ================================================================
        // PARTE 5: Gestión de usuarios
        // Demuestra NullPointerException y UsuarioNoEncontradoException
        // ================================================================
        System.out.println("\n🔧 === PARTE 5: GESTIÓN DE USUARIOS ===");
        System.out.println("Probando NullPointerException y UsuarioNoEncontradoException...\n");

        GestorUsuarios gestor = new GestorUsuarios();

        // ❌ Intentamos agregar un usuario nulo → NullPointerException
        try {
            gestor.agregarUsuario(null);
        } catch (NullPointerException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }

        // ✅ Agregamos usuarios válidos
        if (juan != null) {
            gestor.agregarUsuario(juan);
        }
        gestor.agregarUsuario(new Usuario("María", 30));
        gestor.agregarUsuario(new Usuario("Carlos", 22));

        // Mostramos la lista de usuarios
        gestor.listarUsuarios();

        // ================================================================
        // PARTE 6: Búsqueda de usuarios
        // ================================================================
        System.out.println("🔍 === PARTE 6: BÚSQUEDA DE USUARIOS ===");

        try {
            // ✅ Buscamos un usuario que sí existe
            Usuario encontrado = gestor.buscarUsuarioPorNombre("Juan");
            System.out.println("✅ Usuario encontrado: " + encontrado);

            // ❌ Buscamos un usuario que NO existe → UsuarioNoEncontradoException
            gestor.buscarUsuarioPorNombre("Pedro");

        } catch (UsuarioNoEncontradoException e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }

        // ================================================================
        // FIN DEL PROGRAMA
        // ================================================================
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              ✅ FIN DE LA DEMOSTRACIÓN ✅                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

    // ==================== MÉTODOS AUXILIARES ====================

    /**
     * Método auxiliar para crear un usuario con manejo de excepciones.
     * Captura IllegalArgumentException si los datos son inválidos.
     */
    private static Usuario crearUsuario(String nombre, int edad) {
        try {
            Usuario usuario = new Usuario(nombre, edad);
            System.out.println("✅ Usuario creado: " + usuario);
            return usuario;
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error al crear usuario: " + e.getMessage());
            return null;
        }
    }

    /**
     * Método auxiliar para crear un libro con manejo de excepciones.
     * Captura IllegalArgumentException si el título es inválido.
     */
    private static Libro crearLibro(String titulo) {
        try {
            Libro libro = new Libro(titulo);
            System.out.println("✅ " + libro);
            return libro;
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error al crear libro: " + e.getMessage());
            return null;
        }
    }
}
