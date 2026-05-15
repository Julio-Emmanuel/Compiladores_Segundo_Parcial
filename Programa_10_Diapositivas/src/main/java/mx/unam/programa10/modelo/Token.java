package mx.unam.programa10.modelo;

public class Token {

    TipoToken tipo;
    String lexema;

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
    }

    @Override
    public String toString() {
        return tipo + " -> " + lexema;
    }
}
