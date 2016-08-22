package proyectocompi1;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
%%
%class Lexer
%line
%int
%column
%unicode

SUB = (S|s)(U|u)(B|b)
MAIN = (M|m)(A|a)(I|i)(N|n)
ABRIRPARENTESIS = "("
CERRARPARENTESIS = ")"
TYPE = (T\t)(Y|y)(P|p)(E|e)
FUNCTION = (F|f)(U|u)(N|n)(C|c)(T|t)(I|i)(O|o)(N|n)
TIPOPARAMETRO = (B|b)(Y|y)(V|v)(A|a)(L|l)|(B|b)(Y|y)(R|r)(E|e)(F|f)
COMA = ","
RETURN = (R|r)(E|e)(T|t)(U|u)(R|r)(N|n)
BEGIN = (B|b)(E|e)(G|g)(I|i)(N|n)
END = (E|e)(N|n)(D|d)
IF = (I|i)(F|f)
THEN =  (T|t)(H|h)(E|e)(N|n)
ELSE = (E|e)(L|l)(S|s)(E|e)
FOR = (F|f)(O|o)(R|r)
TO = (T|t)(O|o)
NEXT = (N|n)(E|e)(X|x)(T|t)
DO = (D|d)(O|o)
WHILE = (W|w)(H|h)(I|i)(L|l)(E|e)
LOOP = (L|l)(O|o)(O|o)(P|p)
DIM = (D|d)(I|i)(M|m)
AS = (A|a)(S|s)
TIPOVAR = (I|i)(N|n)(T|t)(E|e)(G|g)(E|e)(R|r)|(S|s)(T|t)(R|r)(I|i)(N|n)(G|g)|(B|b)(O|o)(O|o)(L|l)(E|e)(A|a)(N|n)
TRUEFALSE = (T|t)(R|r)(U|u)(E|e)|(F|f)(A|a)(L|l)(S|s)(E|e)
NOT = (N|n)(O|o)(T|t)
OPLOG = (A|a)(N|n)(D|d)|(O|o)(R|r)|(X|x)(O|o)(R|r)
OPREL = ">"|"<"|">="|"<="|"<>"
ASIGNACION = "="
OPMULT = "*"|"/"
OPSUM = "+"|"-"
DIGITO = [0-9]
NUMERO = {DIGITO}+
LETRA = [a-zA-Z]
IDENTIFICADOR = {LETRA}({DIGITO}|{LETRA})*
ESPACIO = " "
NEWLINE = \n
TAB = "\t"
CADENA = \"[^\n]*\"({ESPACIO}"&_"{NEWLINE}({ESPACIO}|\t)*\"[^\n]*\")*
COMENTARIO = "'"[^\n]* 
OPEN = (O|o)(P|p)(E|e)(N|n)
CLOSE = (C|c)(L|l)(O|o)(S|s)(E|e)
INPUT = (I|i)(N|n)(P|p)(U|u)(T|t)
LINE = (L|l)(I|i)(N|n)(E|e)
EOF = (E|e)(O|o)(F|f)
Write = (W|w)(R|r)(I|i)(T|t)(E|e)
Print = (P|p)(R|r)(I|i)(N|n)(T|t)
FreeFile = (F|f)(R|r)(E|e)(E|e)(F|f)(I|i)(L|l)(E|e)
CONSOLEWRITE = (C|c)(O|o)(N|n)(S|s)(O|o)(L|l)(E|e).(W|w)(R|r)(I|i)(T|t)(E|e)
CONSOLEREAD = (C|c)(O|o)(N|n)(S|s)(O|o)(L|l)(E|e).(R|r)(E|e)(A|a)(D|d)
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
            respuesta = "Quizá se refiere a la palabra clave: "+yytext().substring(0,1).toUpperCase()
                         +yytext().substring(1,yytext().length()).toLowerCase()+"\nLinea: "+yyline+", columna: "+yycolumn;
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
        {OPEN}                  {System.out.println(revisionPalabraClave("OPEN"));}
        {CLOSE}                 {System.out.println(revisionPalabraClave("CLOSE"));}
        {INPUT}                 {System.out.println(revisionPalabraClave("INPUT"));}
        {LINE}                  {System.out.println(revisionPalabraClave("LINE"));}
        {EOF}                   {System.out.println(revisionPalabraClave("EOF"));}
        {Write}                 {System.out.println(revisionPalabraClave("Write"));}
        {Print}                 {System.out.println(revisionPalabraClave("Print"));}
        {FreeFile}              {System.out.println(revisionPalabraClave("FreeFile"));}
        {CONSOLEWRITE}          {System.out.println(revisionPalabraClave("CONSOLEWRITE"));}
        {CONSOLEREAD}           {System.out.println(revisionPalabraClave("CONSOLEREAD"));}
	{NUMERO}  		{System.out.println("<NUMERO, '"+yytext()+"'>");}
	{IDENTIFICADOR} 	{System.out.println("<IDENTIFICADOR, '"+yytext()+"'>");}
        {ESPACIO}               {System.out.println("<ESPACIO>, '"+yytext()+"'>");}
        {NEWLINE}               {System.out.println("<NEWLINE>");}
        {TAB}                   {System.out.println("<TAB>");}
	{CADENA}		{System.out.println("<CADENA, '"+extraerCadenaReal()+"'>");}
        {COMENTARIO}            {System.out.println("<COMENTARIO, '"+yytext().substring(1)+"'>");}
        .			{System.out.println("No se reconoce el token: "+yytext()+"\nLinea: "+yyline+", columna: "+yycolumn);}
        }

<<EOF>> {
	return 0;
}