package mx.unam.programa10.modelo;

public class Lexer {

    private String entrada;
    private int pos = 0;

    public Lexer(String entrada) {
        this.entrada = entrada;
    }

    public Token getToken() {

        while (pos < entrada.length() &&
                Character.isWhitespace(entrada.charAt(pos))) {
            pos++;
        }

        if (pos >= entrada.length()) {
            return new Token(TipoToken.EOF, "");
        }

        char c = entrada.charAt(pos);

        // NUMEROS
        if (Character.isDigit(c)) {

            StringBuilder sb = new StringBuilder();

            while (pos < entrada.length() &&
                    Character.isDigit(entrada.charAt(pos))) {

                sb.append(entrada.charAt(pos));
                pos++;
            }

            return new Token(TipoToken.NUM, sb.toString());
        }

        // IDENTIFICADORES
        if (Character.isLetter(c)) {

            StringBuilder sb = new StringBuilder();

            while (pos < entrada.length() &&
                    Character.isLetterOrDigit(entrada.charAt(pos))) {

                sb.append(entrada.charAt(pos));
                pos++;
            }

            String palabra = sb.toString().toUpperCase();

            switch (palabra) {
                case "DIV":
                    return new Token(TipoToken.DIV, palabra);

                case "MOD":
                    return new Token(TipoToken.MOD, palabra);

                case "AND":
                    return new Token(TipoToken.AND, palabra);

                case "OR":
                    return new Token(TipoToken.OR, palabra);

                case "NOT":
                    return new Token(TipoToken.NOT, palabra);

                default:
                    return new Token(TipoToken.ID, palabra);
            }
        }

        pos++;

        switch (c) {

            case '+':
                return new Token(TipoToken.MAS, "+");

            case '-':
                return new Token(TipoToken.MENOS, "-");

            case '*':
                return new Token(TipoToken.POR, "*");

            case '/':
                return new Token(TipoToken.DIVISION, "/");

            case '(':
                return new Token(TipoToken.AB_PAR, "(");

            case ')':
                return new Token(TipoToken.CE_PAR, ")");

            case ';':
                return new Token(TipoToken.PUNTOYCOMA, ";");

            case '=':
                return new Token(TipoToken.IGUAL, "=");

            case '<':

                if (pos < entrada.length() &&
                        entrada.charAt(pos) == '=') {

                    pos++;
                    return new Token(TipoToken.MEI, "<=");
                }

                return new Token(TipoToken.MENOR, "<");

            case '>':

                if (pos < entrada.length() &&
                        entrada.charAt(pos) == '=') {

                    pos++;
                    return new Token(TipoToken.MAI, ">=");
                }

                return new Token(TipoToken.MAYOR, ">");

            case '!':

                if (pos < entrada.length() &&
                        entrada.charAt(pos) == '=') {

                    pos++;
                    return new Token(TipoToken.DIST, "!=");
                }
        }

        return null;
    }
}