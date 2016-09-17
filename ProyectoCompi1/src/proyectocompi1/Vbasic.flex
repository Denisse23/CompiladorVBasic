package proyectocompi1;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java_cup.runtime.*;
%%
%class Lexer
%line
%cup
%int
%column
%caseless
%ignorecase
%unicode

SUB = Sub
ABRIRPARENTESIS = "("
CERRARPARENTESIS = ")"
LINE = Line
WRITE = Write
READ = Read
CONSOLE = Console
PUNTO = "."
TYPE = Type
FUNCTION = Function
TIPOPARAMETRO = ByVal|ByRef
COMA = ","
RETURN = Return
BEGIN = Begin
END = End
IF = If
THEN =  Then
ELSE = Else
FOR = For
TO = To
NEXT = Next
DO = Do
WHILE = While
LOOP = Loop
UNTIL = Until
DIM = Dim
AS = As
INTEGER = Integer
STRING = String
BOOLEAN = Boolean
TRUEFALSE = True|False
NOT = Not
OPLOG = And|Or|Xor
OPREL = ">"|"<"|">="|"<="|"<>"
ASIGNACION = "="
OPMULT = "*"|"/"
OPSUM = "+"|"-"
DIGITO = [0-9]
NUMERO = {DIGITO}+
LETRA = [a-zA-Z]
IDENTIFICADOR = {LETRA}(_|{DIGITO}|{LETRA})*
ESPACIO = " "
NEWLINE = \n+
TAB = "\t"
CADENA = \"[^\n\"]*\"({ESPACIO}"&_"{NEWLINE}({ESPACIO}|\t)*\"[^\n\"]*\")*
COMENTARIO = "'"[^\n]* 

%{
     
    private String extraerCadenaReal() {
        String[] lista = yytext().split(" &_\n");
        String cadena = "";
            for(int i=0; i<lista.length;i++){
                boolean comilla =false;
                int iniciocomilla = 0;
                int finalcomilla = 0;
                for(int j=0; j<lista[i].length();j++){
                    if(comilla){
                        iniciocomilla = j;
                        break;
                    }
                    if(lista[i].charAt(j)=='"'){
                        comilla=true;
                    }
                }
                comilla = false;
                for(int j=lista[i].length()-1; j>=0;j--){
                    if(lista[i].charAt(j)=='"'){
                        comilla=true;
                    }
                    if(comilla){
                        finalcomilla = j;
                        break;
                    }
                }
                cadena+= lista[i].substring(iniciocomilla, finalcomilla);

            }
        return cadena;
    }

    private String revisionPalabraClave(String name){
        String respuesta = "";
        Pattern pat = Pattern.compile("[A-Z][a-z]+");
        Matcher mat = pat.matcher(yytext());
        if (mat.matches()) {
            respuesta = "<"+name+", '"+yytext()+"'>";
        } else {
            respuesta = "Palabra clave->"+yytext().substring(0,1).toUpperCase()
                         +yytext().substring(1,yytext().length()).toLowerCase()+". Error->Linea: "+yyline+", columna: "+yycolumn;
        }
        return respuesta;
    }

    private String revisionPalabraClave2(String name){
        String respuesta = "";
        switch (yytext()) {
 
        case "ByVal":
        respuesta = "<"+name+", '"+yytext()+"'>";
        break;
        case "ByRef":
        respuesta = "<"+name+", '"+yytext()+"'>";
        break;
        default:
        respuesta = "Palabra clave->By"+yytext().substring(2,3).toUpperCase()
                         +yytext().substring(3,yytext().length()).toLowerCase()+". Error->Linea: "+yyline+", columna: "+yycolumn;
        break;
 
        }

        return respuesta;
    }

   
%}

%%
<YYINITIAL> {
        {SUB}  			{return new Symbol(sym.SUB,0,0);}
        {ABRIRPARENTESIS}  	{return new Symbol(sym.ABRIRPARENTESIS,0,0);}
	{CERRARPARENTESIS}  	{return new Symbol(sym.CERRARPARENTESIS,0,0);}
        {LINE}                  {return new Symbol(sym.LINE,0,0);}
        {WRITE}                 {return new Symbol(sym.WRITE,0,0);}
        {READ}                  {return new Symbol(sym.READ,0,0);}
        {CONSOLE}               {return new Symbol(sym.CONSOLE,0,0);}
        {PUNTO}                 {return new Symbol(sym.PUNTO,0,0);}
        {TYPE}                  {return new Symbol(sym.TYPE,0,0);}
        {FUNCTION}  		{return new Symbol(sym.FUNCTION,0,0);}
        {TIPOPARAMETRO} 	{return new Symbol(sym.TIPOPARAMETRO,0,0);}
        {COMA}  		{return new Symbol(sym.COMA,0,0);}
	{RETURN}  		{return new Symbol(sym.RETURN,0,0);}
	{BEGIN}			{return new Symbol(sym.BEGIN,0,0);}
	{END}			{return new Symbol(sym.END,0,0);}
	{IF} 			{return new Symbol(sym.IF,0,0);}
        {THEN}			{return new Symbol(sym.THEN,0,0);}
        {ELSE} 			{return new Symbol(sym.ELSE,0,0);}
	{FOR} 			{return new Symbol(sym.FOR,0,0);}
        {TO} 			{return new Symbol(sym.TO,0,0);}
        {NEXT} 			{return new Symbol(sym.NEXT,0,0);}
	{DO}			{return new Symbol(sym.DO,0,0);}
	{WHILE} 		{return new Symbol(sym.WHILE,0,0);}
        {LOOP} 			{return new Symbol(sym.LOOP,0,0);}
        {UNTIL}                 {return new Symbol(sym.UNTIL,0,0);}
        {DIM}  			{return new Symbol(sym.DIM,0,0);}
        {AS}  			{return new Symbol(sym.AS,0,0);}
	{INTEGER}  		{return new Symbol(sym.INTEGER,0,0);}
	{STRING}  		{return new Symbol(sym.STRING,0,0);}
        {BOOLEAN}  		{return new Symbol(sym.BOOLEAN,0,0);}
        {TRUEFALSE}  		{return new Symbol(sym.TRUEFALSE,0,0);}
	{NOT} 			{return new Symbol(sym.NOT,0,0);}
	{OPLOG}  		{return new Symbol(sym.OPLOG,0,0);}
	{OPREL} 		{return new Symbol(sym.OPREL,0,0);}
        {ASIGNACION}  		{return new Symbol(sym.ASIGNACION,0,0);}
        {OPMULT}  		{return new Symbol(sym.OPMULT,0,0);}
	{OPSUM}	 		{return new Symbol(sym.OPSUM,0,0);}
	{NUMERO}  		{return new Symbol(sym.NUMERO,0,0);}
	{IDENTIFICADOR} 	{return new Symbol(sym.IDENTIFICADOR,0,0,yytext());}
        {ESPACIO}               {}
        {NEWLINE}               {return new Symbol(sym.NEWLINE,0,0);}
        {TAB}                   {}
	{CADENA}		{return new Symbol(sym.CADENA,0,0,yytext());}
        {COMENTARIO}            {return new Symbol(sym.COMENTARIO,0,0,yytext().substring(1));}
        .			{System.out.println("No se reconoce el token: "+yytext()+". Error->Linea: "+yyline+", columna: "+yycolumn);}
        }
