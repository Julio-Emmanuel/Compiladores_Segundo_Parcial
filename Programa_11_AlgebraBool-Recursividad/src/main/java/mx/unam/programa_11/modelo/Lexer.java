package mx.unam.programa_11.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final List<Token> tokens = new ArrayList<>();

    public Lexer(String input) {

        Pattern pattern = Pattern.compile("(true|false|and|or|not|\\(|\\)|;|\\S)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String match = matcher.group();
            int pos = matcher.start();
            switch (match) {
                case "true":  tokens.add(new Token(TipoToken.TRUE, "true", pos)); break;
                case "false": tokens.add(new Token(TipoToken.FALSE, "false", pos)); break;
                case "and":   tokens.add(new Token(TipoToken.AND, "and", pos)); break;
                case "or":    tokens.add(new Token(TipoToken.OR, "or", pos)); break;
                case "not":   tokens.add(new Token(TipoToken.NOT, "not", pos)); break;
                case "(":     tokens.add(new Token(TipoToken.PAREN_IZQ, "(", pos)); break;
                case ")":     tokens.add(new Token(TipoToken.PAREN_DER, ")", pos)); break;
                case ";":     tokens.add(new Token(TipoToken.PUNTO_COMA, ";", pos)); break;
                default:
                    throw new RuntimeException("Carácter inválido '" + match + "' en la posición " + pos);
            }
        }

        tokens.add(new Token(TipoToken.FIN, "EOF", input.length()));
    }
    public List<Token> getTokens() { return tokens; }
}
