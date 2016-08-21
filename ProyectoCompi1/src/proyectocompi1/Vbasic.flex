package proyectocompi1;
%%
%class Lexer
%line
%int
%column
%unicode

SUB = Sub
MAIN = Main
ABRIRPARENTESIS = "("
CERRARPARENTESIS = ")"
STRUCTURE = Structure
FUNCTION = Function
TIPOPARAMETRO = ByVal|ByRef
RETURN = Return
BEGIN = Begin
END = End
IF = If
THEN =  Then
ELSE = Else
FOR = For
TO = to
DO = Do
WHILE = While
LOOP = Loop
DIM = Dim
AS = As
TIPO = Integer|String|Boolean
TRUEFALSE = True|False
NOT = Not
OPLOG = And|Or|Xor
ASIGNACION = "="
OPREL = ">"|"<"|">="|"<="|"<>"
OPMULT = "*"|"/"
OPSUM = "+"|"-"
DIGITO = [0-9]
NUMERO = {DIGITO}*
LETRA = [a-zA-Z]
IDENTIFICADOR = {LETRA}*({DIGITO}|{LETRA})*
ESPACIO = " "
NEWLINE = \n
CADENA = \".*\"
COMENTARIO = "'"[^\n]* 

%%
<YYINITIAL> {
        {SUB}  			{System.out.println("<SUB>");}
        {MAIN}                  {System.out.println("<MAIN>");}
        {ABRIRPARENTESIS}  	{System.out.println("<ABRIRPARENTESIS>");}
	{CERRARPARENTESIS}  	{System.out.println("<CERRARPARENTESIS>");}
        {STRUCTURE} 		{System.out.println("<STRUCTURE>");}
        {FUNCTION}  		{System.out.println("<FUNCTION>");}
        {TIPOPARAMETRO} 	{System.out.println("<TIPOPARAMETRO, '"+yytext()+"'>");}
	{RETURN}  		{System.out.println("<RETURN>");}
	{BEGIN}			{System.out.println("<BEGIN>");}
	{END}			{System.out.println("<END>");}
	{IF} 			{System.out.println("<IF>");}
        {THEN}			{System.out.println("<THEN>");}
        {ELSE} 			{System.out.println("<ELSE>");}
	{FOR} 			{System.out.println("<FOR>");}
        {TO} 			{System.out.println("<TO>");}
	{DO}			{System.out.println("<DO>");}
	{WHILE} 		{System.out.println("<WHILE>");}
        {LOOP} 			{System.out.println("<LOOP>");}
        {DIM}  			{System.out.println("<DIM>");}
        {AS}  			{System.out.println("<AS>");}
	{TIPO}  		{System.out.println("<TIPO, '"+yytext()+"'>");}
	{TRUEFALSE}  		{System.out.println("<TRUEFALSE, '"+yytext()+"'>");}
	{NOT} 			{System.out.println("<NOT>");}
	{OPLOG}  		{System.out.println("<OPLOG, '"+yytext()+"'>");}
	{ASIGNACION}  		{System.out.println("<ASIGNACION>");}
	{OPREL} 		{System.out.println("<OPREL, '"+yytext()+"'>");}
        {OPMULT}  		{System.out.println("<OPMULT, '"+yytext()+"'>");}
	{OPSUM}	 		{System.out.println("<OPSUM, '"+yytext()+"'>");}
	{NUMERO}  		{System.out.println("<NUMERO, '"+yytext()+"'>");}
	{IDENTIFICADOR} 	{System.out.println("<IDENTIFICADOR, '"+yytext()+"'>");}
        {ESPACIO}               {System.out.println("<ESPACIO>");}
        {NEWLINE}               {System.out.println("<NEWLINE>");}
	{CADENA}		{System.out.println("<CADENA, '"+yytext()+"'>");}
        {COMENTARIO}            {System.out.println("<COMENTARIO, '"+yytext()+"'>");}
	.			{System.out.println("El analizador léxico no reconoce el token: "+yytext()+"\nLinea: "+yyline+", columna: "+yycolumn);}
}

<<EOF>> {
	return 0;
}