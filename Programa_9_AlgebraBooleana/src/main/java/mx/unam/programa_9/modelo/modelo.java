package mx.unam.programa_9.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class modelo {

    enum TokenType {
        TRUE, FALSE, AND, OR, NOT, PAREN_IZQ, PAREN_DER, PUNTO_COMA, FIN
    }

    static class Token {
        TokenType tipo;
        String valor;
        int posicion;

        public Token(TokenType tipo, String valor, int posicion) {
            this.tipo = tipo;
            this.valor = valor;
            this.posicion = posicion;
        }
    }

    public static class Lexer {
        private final List<Token> tokens = new ArrayList<>();

        public Lexer(String input) {

            Pattern pattern = Pattern.compile("(true|false|and|or|not|\\(|\\)|;|\\S)");
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()) {
                String match = matcher.group();
                int pos = matcher.start();
                switch (match) {
                    case "true":  tokens.add(new Token(TokenType.TRUE, "true", pos)); break;
                    case "false": tokens.add(new Token(TokenType.FALSE, "false", pos)); break;
                    case "and":   tokens.add(new Token(TokenType.AND, "and", pos)); break;
                    case "or":    tokens.add(new Token(TokenType.OR, "or", pos)); break;
                    case "not":   tokens.add(new Token(TokenType.NOT, "not", pos)); break;
                    case "(":     tokens.add(new Token(TokenType.PAREN_IZQ, "(", pos)); break;
                    case ")":     tokens.add(new Token(TokenType.PAREN_DER, ")", pos)); break;
                    case ";":     tokens.add(new Token(TokenType.PUNTO_COMA, ";", pos)); break;
                    default:
                        throw new RuntimeException("Carácter inválido '" + match + "' en la posición " + pos);
                }
            }

            tokens.add(new Token(TokenType.FIN, "EOF", input.length()));
        }
        public List<Token> getTokens() { return tokens; }
    }


    public static class Parser {
        private final List<Token> tokens;
        private int current = 0;

        public Parser(List<Token> tokens) { this.tokens = tokens; }

        private Token peek() { return tokens.get(current); }
        private Token advance() { return tokens.get(current++); }

        private void match(TokenType tipoEsperado) {
            if (peek().tipo == tipoEsperado) {
                advance();
            } else {
                throw new RuntimeException("Error en pos " + peek().posicion +
                        ": Se esperaba '" + tipoEsperado + "' pero se encontró '" + peek().valor + "'");
            }
        }


        //GRAMATICA 

        // S ::= E ;
        public void s() {
            e();
            match(TokenType.PUNTO_COMA);
        }

        // E ::= T or E | T
        private void e() {
            t();
            if (peek().tipo == TokenType.OR) {
                advance();
                e();
            }
        }

        // T ::= F and T | F
        private void t() {
            f();
            if (peek().tipo == TokenType.AND) {
                advance();
                t();
            }
        }

        // F ::= not F | true | false | (E)
        private void f() {
            if (peek().tipo == TokenType.NOT) {
                advance();
                f();
            } else if (peek().tipo == TokenType.TRUE) {
                advance();
            } else if (peek().tipo == TokenType.FALSE) {
                advance();
            } else if (peek().tipo == TokenType.PAREN_IZQ) {
                advance();
                e();
                match(TokenType.PAREN_DER);
            } else {
                throw new RuntimeException("Token inesperado: " + peek().tipo);
            }
        }
    }

}
