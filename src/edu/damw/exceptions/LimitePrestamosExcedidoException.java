package edu.damw.exceptions;

public class LimitePrestamosExcedidoException extends Exception {
    private int librosPrestados;
    private static final int LIMITE_PRESTAMOS = 3;

    public LimitePrestamosExcedidoException(String usuario, int librosPrestados) {
        super("Usuario " + usuario + " ha intentado pedir prestado más de " + LIMITE_PRESTAMOS + " libros.");
        this.librosPrestados = librosPrestados;
    }

    public String sugerirSolucion() {
        return "Sugerencia: Devuelve algún libro antes de intentar un nuevo préstamo.";
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\nLibros prestados actualmente: " + librosPrestados + "\n" + sugerirSolucion();
    }
}
