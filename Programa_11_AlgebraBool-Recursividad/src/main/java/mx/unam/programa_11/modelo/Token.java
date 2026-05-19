package mx.unam.programa_11.modelo;

public class Token {
    TipoToken tipo;
    String valor;
    int posicion;

    public Token(TipoToken tipo, String valor, int posicion) {
        this.tipo = tipo;
        this.valor = valor;
        this.posicion = posicion;
    }
}
