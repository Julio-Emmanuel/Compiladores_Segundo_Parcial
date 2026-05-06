import java_cup.runtime.*;

%%

/* Configuración de JFlex */
%class Lexer
%unicode
%line
%column
%standalone
%public

/* Expresiones Regulares */
Digito       = [0-9]
Letra        = [a-zA-Z]
Espacio      = [ \t\r\n]

/*
   Identificador: Empieza con letra, seguido de letras o dígitos.
   Longitud máxima de 15 (usando {0,14} porque la primera letra ya cuenta como 1).
*/
Identificador = {Letra}({Letra}|{Digito}){0,14}

/* Número: Entero o Real */
Numero       = {Digito}+(\.{Digito}+)?

%%

/* Reglas Léxicas */

/* Palabras Reservadas */
"inicio"       { System.out.println("Token: inicio | Lexema: " + yytext()); }
"fin"          { System.out.println("Token: fin | Lexema: " + yytext()); }
"entero"       { System.out.println("Token: entero | Lexema: " + yytext()); }
"real"         { System.out.println("Token: real | Lexema: " + yytext()); }
"mientras"     { System.out.println("Token: mientras | Lexema: " + yytext()); }
"fin-mientras" { System.out.println("Token: finmientras | Lexema: " + yytext()); }
"si"           { System.out.println("Token: si | Lexema: " + yytext()); }
"sino"         { System.out.println("Token: sino | Lexema: " + yytext()); }

/* Operadores y Símbolos */
":="           { System.out.println("Token: := | Lexema: " + yytext()); }
";"            { System.out.println("Token: ; | Lexema: " + yytext()); }
","            { System.out.println("Token: , | Lexema: " + yytext()); }
"("            { System.out.println("Token: ( | Lexema: " + yytext()); }
")"            { System.out.println("Token: ) | Lexema: " + yytext()); }

/* Operadores Aritméticos */
"+" | "*" | "-" | "/" { System.out.println("Token: operadores | Lexema: " + yytext()); }

/* Comparaciones */
"=" | "!=" | ">" | ">=" | "<" | "<=" { System.out.println("Token: comparaciones | Lexema: " + yytext()); }

/* Componentes Dinámicos */
{Identificador} { System.out.println("Token: identificador | Lexema: " + yytext()); }
{Numero}        { System.out.println("Token: numero | Lexema: " + yytext()); }

/* Ignorar espacios en blanco */
{Espacio}       { /* Ignorar */ }

/* Error Léxico */
.               { System.err.println("Error Léxico: Carácter no reconocido '" + yytext() + "' en línea " + (yyline+1) + ", columna " + (yycolumn+1)); }