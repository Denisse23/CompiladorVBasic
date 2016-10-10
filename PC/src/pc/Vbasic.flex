package pc;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java_cup.runtime.*;
import java_cup.runtime.Symbol;
%%
%class Lexer
%line
%cup
%int
%column
%caseless
%ignorecase
%unicode
%extends sym
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
     
  StringBuffer string = new StringBuffer();
  
  private Symbol symbol(int type) {
    return new JavaSymbol(type, yyline+1, yycolumn+1, yytext());
  }

  private Symbol symbol(int type, Object value) {
    return new JavaSymbol(type, yyline+1, yycolumn+1, yytext(), value);
  }

  public String current_lexeme(){
    int l = yyline+1;
    int c = yycolumn+1;
    return " line: "+l+" , column: "+c+" , lexema: '"+yytext()+"'";
  }

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
        {SUB}  			{return symbol(sub);}
        {ABRIRPARENTESIS}  	{return symbol(abrirparentesis);}
	{CERRARPARENTESIS}  	{return symbol(cerrarparentesis);}
        {WRITE}                 {return symbol(write);}
        {READ}                  {return symbol(read);}
        {CONSOLE}               {return symbol(console);}
        {PUNTO}                 {return symbol(punto);}
        {TYPE}                  {return symbol(type);}
        {FUNCTION}  		{return symbol(function);}
        {TIPOPARAMETRO} 	{return symbol(tipoparametro, yytext());}
        {COMA}  		{return symbol(coma);}
	{RETURN}  		{return symbol(Return);}
	{END}			{return symbol(end);}
	{IF} 			{return symbol(If);}
        {THEN}			{return symbol(then);}
        {ELSE} 			{return symbol(Else);}
	{FOR} 			{return symbol(For);}
        {TO} 			{return symbol(to);}
        {NEXT} 			{return symbol(next);}
	{DO}			{return symbol(Do);}
	{WHILE} 		{return symbol(While);}
        {LOOP} 			{return symbol(loop);}
        {DIM}  			{return symbol(dim);}
        {AS}  			{return symbol(as);}
	{TIPOVAR}  		{return symbol(tipovar, yytext());}
        {TRUEFALSE}  		{return symbol(truefalse, yytext());}
	{NOT} 			{return symbol(not);}
	{OPLOG}  		{return symbol(oplog, yytext());}
	{OPREL} 		{return symbol(oprel, yytext());}
        {ASIGNACION}  		{return symbol(asignacion);}
        {OPMULT}  		{return symbol(opmult, yytext());}
	{OPSUM}	 		{return symbol(opsum, yytext());}
	{NUMERO}  		{return symbol(numero, yytext());}
	{IDENTIFICADOR} 	{return symbol(identificador,yytext());}
        {ESPACIO}               {return symbol(espacio, yytext());}
        {NEWLINE}               {return symbol(newline, yytext());}
        {TAB}                   {return symbol(tab, yytext());}
	{CADENA}		{return symbol(cadena,extraerCadenaReal());}
        {COMENTARIO}            {return symbol(comentario,yytext().substring(1));}  
      .			{ System.err.println("\nNo se reconoce el token: "+" Error-> "+current_lexeme()); }   
}

