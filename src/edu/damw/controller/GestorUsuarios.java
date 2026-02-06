package edu.damw.controller;

import edu.damw.exceptions.UsuarioNoEncontradoException;
import edu.damw.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase controladora que gestiona la lista de usuarios de la biblioteca.
 * 
 * Demuestra el uso de:
 * - NullPointerException (excepción estándar) para validar objetos nulos
 * - UsuarioNoEncontradoException (excepción personalizada Checked)
 */
public class GestorUsuarios {
    private List<Usuario> usuarios;

    public GestorUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    /**
     * Agrega un usuario a la lista.
     * 
     * @param usuario El usuario a agregar (no puede ser nulo)
     * @throws NullPointerException si el usuario es nulo
     */
    public void agregarUsuario(Usuario usuario) {
        // ✅ Validación usando excepción estándar de Java (Unchecked)
        if (usuario == null) {
            throw new NullPointerException("El usuario no puede ser nulo.");
        }
        usuarios.add(usuario);
        System.out.println("✅ Usuario '" + usuario.getNombre() + "' agregado al sistema.");
    }

    /**
     * Busca un usuario por su nombre.
     * 
     * @param nombre El nombre del usuario a buscar
     * @return El usuario encontrado
     * @throws UsuarioNoEncontradoException si no existe un usuario con ese nombre
     */
    public Usuario buscarUsuarioPorNombre(String nombre) throws UsuarioNoEncontradoException {
        // Recorremos la lista buscando el usuario
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                return usuario; // ✅ Encontrado
            }
        }
        // ❌ Si llegamos aquí, no existe → lanzamos excepción personalizada
        throw new UsuarioNoEncontradoException("Usuario con nombre '" + nombre + "' no encontrado.");
    }

    /**
     * Lista todos los usuarios registrados en el sistema.
     */
    public void listarUsuarios() {
        System.out.println("\n📋 === LISTA DE USUARIOS ===");
        if (usuarios.isEmpty()) {
            System.out.println("   No hay usuarios registrados.");
        } else {
            for (Usuario usuario : usuarios) {
                System.out.println("   " + usuario);
            }
        }
        System.out.println("============================\n");
    }

    /**
     * Obtiene el número de usuarios registrados.
     * 
     * @return Cantidad de usuarios
     */
    public int getTotalUsuarios() {
        return usuarios.size();
    }
}
