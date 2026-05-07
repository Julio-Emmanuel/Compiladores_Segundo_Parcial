// -*- mode: jflex; -*-
package fes.aragon.tablerointerprete.analizador;

import java_cup.runtime.Symbol;
import fes.aragon.tablerointerprete.analizador.sym;

%%

%class AnalizadorLexico
%public
%unicode
%cup
%line
%column
%ignorecase

%{
    private void error(String mensaje) {
        System.err.println("Error léxico en línea " + (yyline+1) + ", columna " + (yycolumn+1) + ": " + mensaje);
    }
%}

%init{
    yybegin(YYINITIAL);
%init}

/* Definiciones regulares */
Letra = [a-zA-Z_]
Digito = [0-9]
Entero = {Digito}+
Identificador = {Letra}({Letra}|{Digito})*
Espacio = [ \t\r\n]+
Comentario = "//".*

%%

/* Palabras reservadas */
"Inicio"             { return new Symbol(sym.INICIO, yyline+1, yycolumn+1, yytext()); }
"fin"                { return new Symbol(sym.FIN, yyline+1, yycolumn+1, yytext()); }
"arriba"             { return new Symbol(sym.ARRIBA, yyline+1, yycolumn+1, yytext()); }
"abajo"              { return new Symbol(sym.ABAJO, yyline+1, yycolumn+1, yytext()); }
"izquierda"          { return new Symbol(sym.IZQUIERDA, yyline+1, yycolumn+1, yytext()); }
"derecha"            { return new Symbol(sym.DERECHA, yyline+1, yycolumn+1, yytext()); }
"mover"              { return new Symbol(sym.MOVER, yyline+1, yycolumn+1, yytext()); }
"repite"             { return new Symbol(sym.REPITE, yyline+1, yycolumn+1, yytext()); }
"hasta"              { return new Symbol(sym.HASTA, yyline+1, yycolumn+1, yytext()); }

/* Operadores */
"="                  { return new Symbol(sym.IGUAL, yyline+1, yycolumn+1, yytext()); }

/* Símbolos */
","                  { return new Symbol(sym.COMA, yyline+1, yycolumn+1, yytext()); }

/* Números y variables */
{Entero}             { return new Symbol(sym.ENTERO, yyline+1, yycolumn+1, Integer.parseInt(yytext())); }
{Identificador}      { return new Symbol(sym.IDENTIFICADOR, yyline+1, yycolumn+1, yytext()); }

/* Comentarios */
{Comentario}         { /* Ignorar comentarios */ }

/* Espacios en blanco */
{Espacio}            { /* Ignorar espacios */ }

/* Error */
[^]                  { error("Caracter no reconocido: " + yytext()); }