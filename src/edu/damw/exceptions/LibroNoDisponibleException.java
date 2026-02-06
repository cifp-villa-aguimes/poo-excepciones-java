package edu.damw.exceptions;

public class LibroNoDisponibleException extends Exception {
    private String estadoLibro;

    public LibroNoDisponibleException(String tituloLibro, String estadoLibro) {
        super("El libro '" + tituloLibro + "' no está disponible. Estado actual: " + estadoLibro);
        this.estadoLibro = estadoLibro;
    }

    public String sugerirAlternativa() {
        return estadoLibro.equalsIgnoreCase("PRESTADO")
                ? "Sugerencia: Espera a que el libro sea devuelto o intenta reservarlo."
                : "Sugerencia: Consulta con la biblioteca sobre su disponibilidad.";
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\n" + sugerirAlternativa();
    }
}
