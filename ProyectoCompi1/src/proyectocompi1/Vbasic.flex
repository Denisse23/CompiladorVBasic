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
DIM = Dim
AS = As
TIPOVAR = Integer|String|Boolean
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
NEWLINE = "\n"+
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

    private boolean revisionPalabraClave(){
        boolean respuesta = false;
        Pattern pat = Pattern.compile("[A-Z][a-z]+");
        Matcher mat = pat.matcher(yytext());
        if (mat.matches()) {
            respuesta = true;
        } else {
            System.out.println("Palabra clave->"+yytext().substring(0,1).toUpperCase()
                         +yytext().substring(1,yytext().length()).toLowerCase()+". Error->Linea: "+yyline+", columna: "+yycolumn);
        }
        return respuesta;
    }

    private boolean revisionPalabraClave2(){
        boolean respuesta = false;
        switch (yytext()) {
 
        case "ByVal":
        respuesta = true;
        break;
        case "ByRef":
        respuesta = true;
        break;
        default:
        System.out.println("Palabra clave->By"+yytext().substring(2,3).toUpperCase()
                         +yytext().substring(3,yytext().length()).toLowerCase()+". Error->Linea: "+yyline+", columna: "+yycolumn);
        break;
 
        }

        return respuesta;
    }

   
%}

%%
<YYINITIAL> {
        {SUB}  			{if(revisionPalabraClave()){return new Symbol(sym.sub,0,0);}}
        {ABRIRPARENTESIS}  	{return new Symbol(sym.abrirparentesis,0,0);}
	{CERRARPARENTESIS}  	{return new Symbol(sym.cerrarparentesis,0,0);}
        {LINE}                  {if(revisionPalabraClave()){return new Symbol(sym.line,0,0);}}
        {WRITE}                 {if(revisionPalabraClave()){return new Symbol(sym.write,0,0);}}
        {READ}                  {if(revisionPalabraClave()){return new Symbol(sym.read,0,0);}}
        {CONSOLE}               {if(revisionPalabraClave()){return new Symbol(sym.console,0,0);}}
        {PUNTO}                 {return new Symbol(sym.punto,0,0);}
        {TYPE}                  {if(revisionPalabraClave()){return new Symbol(sym.type,0,0);}}
        {FUNCTION}  		{if(revisionPalabraClave()){return new Symbol(sym.function,0,0);}}
        {TIPOPARAMETRO} 	{if(revisionPalabraClave2()){return new Symbol(sym.tipoparametro,0,0, yytext());}}
        {COMA}  		{return new Symbol(sym.coma,0,0);}
	{RETURN}  		{if(revisionPalabraClave()){return new Symbol(sym.Return,0,0);}}
	{BEGIN}			{if(revisionPalabraClave()){return new Symbol(sym.begin,0,0);}}
	{END}			{if(revisionPalabraClave()){return new Symbol(sym.end,0,0);}}
	{IF} 			{if(revisionPalabraClave()){return new Symbol(sym.If,0,0);}}
        {THEN}			{if(revisionPalabraClave()){return new Symbol(sym.then,0,0);}}
        {ELSE} 			{if(revisionPalabraClave()){return new Symbol(sym.Else,0,0);}}
	{FOR} 			{if(revisionPalabraClave()){return new Symbol(sym.For,0,0);}}
        {TO} 			{if(revisionPalabraClave()){return new Symbol(sym.to,0,0);}}
        {NEXT} 			{if(revisionPalabraClave()){return new Symbol(sym.next,0,0);}}
	{DO}			{if(revisionPalabraClave()){return new Symbol(sym.Do,0,0);}}
	{WHILE} 		{if(revisionPalabraClave()){return new Symbol(sym.While,0,0);}}
        {LOOP} 			{if(revisionPalabraClave()){return new Symbol(sym.loop,0,0);}}
        {DIM}  			{if(revisionPalabraClave()){return new Symbol(sym.dim,0,0);}}
        {AS}  			{if(revisionPalabraClave()){return new Symbol(sym.as,0,0);}}
	{TIPOVAR}  		{if(revisionPalabraClave()){return new Symbol(sym.tipovar,0,0, yytext());}}
        {TRUEFALSE}  		{if(revisionPalabraClave()){return new Symbol(sym.truefalse,0,0, yytext());}}
	{NOT} 			{if(revisionPalabraClave()){return new Symbol(sym.not,0,0);}}
	{OPLOG}  		{return new Symbol(sym.oplog,0,0, yytext());}
	{OPREL} 		{return new Symbol(sym.oprel,0,0, yytext());}
        {ASIGNACION}  		{return new Symbol(sym.asignacion,0,0);}
        {OPMULT}  		{return new Symbol(sym.opmult,0,0, yytext());}
	{OPSUM}	 		{return new Symbol(sym.opsum,0,0, yytext());}
	{NUMERO}  		{return new Symbol(sym.numero,0,0, Integer.parseInt(yytext()));}
	{IDENTIFICADOR} 	{return new Symbol(sym.identificador,0,0,yytext());}
        {ESPACIO}               {return new Symbol(sym.espacio,0,0, yytext());}
        {NEWLINE}               {return new Symbol(sym.newline,0,0, yytext());}
        {TAB}                   {return new Symbol(sym.tab,0,0, yytext());}
	{CADENA}		{return new Symbol(sym.cadena,0,0,extraerCadenaReal());}
        {COMENTARIO}            {return new Symbol(sym.comentario,0,0,yytext().substring(1));}
        .			{System.out.println("No se reconoce el token: "+yytext()+". Error->Linea: "+yyline+", columna: "+yycolumn);}
        }
