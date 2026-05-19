package mx.unam.programa_11.modelo;

public class SintaxisException extends RuntimeException {
    private final int posicion;

    public SintaxisException(String mensaje, int posicion) {
        super(mensaje);
        this.posicion = posicion;
    }
}