package mx.unam.programa10.modelo;

public class Parser {

    private Lexer lexer;
    private Token token;

    public Parser(String entrada) {

        lexer = new Lexer(entrada);
        token = lexer.getToken();
    }

    private void getToken() {
        token = lexer.getToken();
    }

    public String analizar() {

        try {

            secuencia();

            if (token.tipo != TipoToken.EOF) {
                return "Error: tokens sobrantes";
            }

            return "VÁLIDA";

        } catch (Exception e) {

            return "INVÁLIDA -> " + e.getMessage();
        }
    }

    private void secuencia() {
        do {
            expresion();
            while (token.tipo != TipoToken.PUNTOYCOMA &&
                    token.tipo != TipoToken.EOF) {
                throw new RuntimeException(
                        "Se esperaba ';' al final de la expresión"
                );
            }
            if (token.tipo == TipoToken.PUNTOYCOMA) {
                getToken();
            }
        } while (token.tipo != TipoToken.EOF);
    }

    private void expresion() {
        exprSimple();
        if (token.tipo == TipoToken.IGUAL ||
                token.tipo == TipoToken.DIST ||
                token.tipo == TipoToken.MEI ||
                token.tipo == TipoToken.MAI ||
                token.tipo == TipoToken.MENOR ||
                token.tipo == TipoToken.MAYOR) {
            getToken();
            exprSimple();
        }
    }
    private void exprSimple() {
        if (token.tipo == TipoToken.MAS ||
                token.tipo == TipoToken.MENOS) {
            getToken();
        }
        termino();
        while (token.tipo == TipoToken.MAS ||
                token.tipo == TipoToken.MENOS ||
                token.tipo == TipoToken.OR) {
            getToken();
            termino();
        }
    }
    private void termino() {
        factor();
        while (token.tipo == TipoToken.POR ||
                token.tipo == TipoToken.DIVISION ||
                token.tipo == TipoToken.DIV ||
                token.tipo == TipoToken.MOD ||
                token.tipo == TipoToken.AND) {
            getToken();
            factor();
        }
    }

    private void factor() {
        switch (token.tipo) {
            case ID:
            case NUM:
                getToken();
                break;
            case NOT:
                getToken();
                factor();
                break;
            case AB_PAR:
                getToken();

                expresion();
                if (token.tipo != TipoToken.CE_PAR) {
                    throw new RuntimeException(
                            "Falta paréntesis de cierre ')'"
                    );
                }
                getToken();
                break;
            default:
                throw new RuntimeException(
                        "Token inesperado: " + token.lexema
                );

        }
    }
}