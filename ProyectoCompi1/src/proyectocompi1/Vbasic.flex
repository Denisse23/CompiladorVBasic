package proyectocompi1;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
%%
%class Lexer
%line
%int
%column
%caseless
%ignorecase
%unicode

SUB = Sub
MAIN = Main
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
        Pattern pat = Pattern.compile("[A-Z][a-z]+(.[A-Z][a-z])*");
        Matcher mat = pat.matcher(yytext());
        if (mat.matches()) {
            respuesta = "<"+name+", '"+yytext()+"'>";
        } else {
            respuesta = "Palabra clave->"+yytext().substring(0,1).toUpperCase()
                         +yytext().substring(1,yytext().length()).toLowerCase()+". Error->Linea: "+yyline+", columna: "+yycolumn;
        }
        return respuesta;
    }

   
%}

%%
<YYINITIAL> {
        {SUB}  			{System.out.println(revisionPalabraClave("SUB"));}
        {MAIN}                  {System.out.println(revisionPalabraClave("MAIN"));}
        {ABRIRPARENTESIS}  	{System.out.println("<ABRIRPARENTESIS>, '"+yytext()+"'>");}
	{CERRARPARENTESIS}  	{System.out.println("<CERRARPARENTESIS>, '"+yytext()+"'>");}
        {LINE}                  {System.out.println(revisionPalabraClave("LINE"));}
        {WRITE}                 {System.out.println(revisionPalabraClave("WRITE"));}
        {READ}                  {System.out.println(revisionPalabraClave("READ"));}
        {CONSOLE}               {System.out.println(revisionPalabraClave("CONSOLE"));}
        {PUNTO}                 {System.out.println("<PUNTO>, '"+yytext()+"'>");}
        {TYPE}                  {System.out.println(revisionPalabraClave("TYPE"));}
        {FUNCTION}  		{System.out.println(revisionPalabraClave("FUNCTION"));}
        {TIPOPARAMETRO} 	{System.out.println("<TIPOPARAMETRO, '"+yytext()+"'>");}
        {COMA}  		{System.out.println("<COMA>, '"+yytext()+"'>");}
	{RETURN}  		{System.out.println(revisionPalabraClave("RETURN"));}
	{BEGIN}			{System.out.println(revisionPalabraClave("BEGIN"));}
	{END}			{System.out.println(revisionPalabraClave("END"));}
	{IF} 			{System.out.println(revisionPalabraClave("IF"));}
        {THEN}			{System.out.println(revisionPalabraClave("THEN"));}
        {ELSE} 			{System.out.println(revisionPalabraClave("ELSE"));}
	{FOR} 			{System.out.println(revisionPalabraClave("FOR"));}
        {TO} 			{System.out.println(revisionPalabraClave("TO"));}
        {NEXT} 			{System.out.println(revisionPalabraClave("NEXT"));}
	{DO}			{System.out.println(revisionPalabraClave("DO"));}
	{WHILE} 		{System.out.println(revisionPalabraClave("WHILE"));}
        {LOOP} 			{System.out.println(revisionPalabraClave("LOOP"));}
        {UNTIL}                 {System.out.println(revisionPalabraClave("UNTIL"));}
        {DIM}  			{System.out.println(revisionPalabraClave("DIM"));}
        {AS}  			{System.out.println(revisionPalabraClave("AS"));}
	{TIPOVAR}  		{System.out.println(revisionPalabraClave("TIPOVAR"));}
	{TRUEFALSE}  		{System.out.println(revisionPalabraClave("TRUEFALSE"));}
	{NOT} 			{System.out.println(revisionPalabraClave("NOT"));}
	{OPLOG}  		{System.out.println(revisionPalabraClave("OPLOG"));}
	{OPREL} 		{System.out.println("<OPREL, '"+yytext()+"'>");}
        {ASIGNACION}  		{System.out.println("<ASIGNACION>, '"+yytext()+"'>");}
        {OPMULT}  		{System.out.println("<OPMULT, '"+yytext()+"'>");}
	{OPSUM}	 		{System.out.println("<OPSUM, '"+yytext()+"'>");}
	{NUMERO}  		{System.out.println("<NUMERO, '"+yytext()+"'>");}
	{IDENTIFICADOR} 	{System.out.println("<IDENTIFICADOR, '"+yytext()+"'>");}
        {ESPACIO}               {System.out.println("<ESPACIO>, '"+yytext()+"'>");}
        {NEWLINE}               {System.out.println("<NEWLINE>");}
        {TAB}                   {System.out.println("<TAB>");}
	{CADENA}		{System.out.println("<CADENA, '"+extraerCadenaReal()+"'>");}
        {COMENTARIO}            {System.out.println("<COMENTARIO, '"+yytext().substring(1)+"'>");}
        .			{System.out.println("No se reconoce el token: "+yytext()+". Error->Linea: "+yyline+", columna: "+yycolumn);}
        }

<<EOF>> {
	return 0;
}