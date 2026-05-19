package mx.unam.programa_11.modelo;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Token ver() { return tokens.get(current); }
    private Token sig() { return tokens.get(current++); }

    private void match(TipoToken tipoEsperado) {
        if (ver().tipo == tipoEsperado) {
            sig();
        } else {
            throw new SintaxisException(
                    "Se esperaba '" + tipoEsperado + "' pero se encontró '" + ver().valor + "'",
                    ver().posicion
            );
        }
    }

    // S ::= (E ;)*
    public void s() {
        while (ver().tipo != TipoToken.FIN) {
            e();
            match(TipoToken.PUNTO_COMA);
        }
    }

    // E ::= T (or T)* 
    private void e() {
        t();
        while (ver().tipo == TipoToken.OR) {
            sig(); 
            t();       
        }
    }

    // T ::= F (and F)* 
    private void t() {
        f();
        while (ver().tipo == TipoToken.AND) {
            sig(); 
            f();       
        }
    }

    // F ::= not F | true | false | (E)
    private void f() {
        if (ver().tipo == TipoToken.NOT) {
            sig();
            f(); 
        } else if (ver().tipo == TipoToken.TRUE) {
            sig();
        } else if (ver().tipo == TipoToken.FALSE) {
            sig();
        } else if (ver().tipo == TipoToken.PAREN_IZQ) {
            sig();
            e();
            match(TipoToken.PAREN_DER);
        } else {
            throw new SintaxisException(
                    "Token inesperado: '" + ver().valor,
                    ver().posicion
            );
        }
    }
}
