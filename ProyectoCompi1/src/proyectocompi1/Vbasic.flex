%%
%class Lexer
%line
%int
%column
%unicode

BEGIN = Begin
END = End
IF = If
FOR = For
DO = Do
WHILE = While
TIPO = Integer|String|Boolean
TRUEFALSE = True|False
AS = As
ELSE = Else
STRUCTURE = Structure
NOT = Not
OPLOG = And|Or|Xor
DIM = Dim
LOOP = Loop
FUNCTION = Function
RETURN = Return
TIPOPARAMETRO = ByVal|ByRef
THEN =  Then
SUB = Sub
ABRIRPARENTESIS = "("
CERRARPARENTESIS = ")"
OPREL = ">"|"<"|">="|"<="|"<>"
OPSUM = "+"|"-"
OPMULT = "*"|"/"
ASIGNACION = "="
DIGITO = [0-9]
NUMERO = {DIGITO}*
LETRA = [a-zA-Z]
IDENTIFICADOR = {LETRA}*({DIGITO}|{LETRA})*
CADENA = \".*\"
CADENAESPACIOS = (\s|\t|\n)*



%%

<YYINITIAL> {
	{BEGIN}			{System.out.println("<BEGIN>");}
	{END}			{System.out.println("<END>");}
	{IF} 			{System.out.println("<IF>");}
	{FOR} 			{System.out.println("<FOR>");}
	{DO}			{System.out.println("<DO>");}
	{WHILE} 		{System.out.println("<WHILE>");}
	{TIPO}  		{System.out.println("<TIPO,"+"'"+yytext()+"'>");}
	{TRUEFALSE}  		{System.out.println("<TRUEFALSE,"+"'"+yytext()+"'>");}
	{AS}  			{System.out.println("<AS>");}
	{ELSE} 			{System.out.println("<ELSE>");}
	{STRUCTURE} 		{System.out.println("<STRUCTURE>");}
	{NOT} 			{System.out.println("<NOT>");}
	{OPLOG}  		{System.out.println("<OPLOG,"+"'"+yytext()+"'>");}
	{DIM}  			{System.out.println("<DIM>");}
	{LOOP} 			{System.out.println("<LOOP>");}
	{FUNCTION}  		{System.out.println("<FUNCTION>");}
	{RETURN}  		{System.out.println("<RETURN>");}
	{TIPOPARAMETRO} 	{System.out.println("<TIPOPARAMETRO,"+"'"+yytext()+"'>");}
	{THEN}			{System.out.println("<THEN>");}
	{SUB}  			{System.out.println("<SUB>");}
	{ABRIRPARENTESIS}  	{System.out.println("<ABRIRPARENTESIS>");}
	{CERRARPARENTESIS}  	{System.out.println("<CERRARPARENTESIS>");}
	{OPREL} 		{System.out.println("<OPREL,"+"'"+yytext()+"'>");}
	{OPSUM}	 		{System.out.println("<OPSUM,"+"'"+yytext()+"'>");}
	{OPMULT}  		{System.out.println("<OPMULT,"+"'"+yytext()+"'>");}
	{ASIGNACION}  		{System.out.println("<ASIGNACION>");}
	{NUMERO}  		{System.out.println("<NUMERO,"+"'"+yytext()+"'>");}
	{IDENTIFICADOR} 	{System.out.println("<IDENTIFICADOR,"+"'"+yytext()+"'>");}
	{CADENA}		{System.out.println("<CADENA,"+"'"+yytext()+"'>");}
	{CADENAESPACIOS} 	{}
	.			{}
}

<<EOF>> {
	return 0;
}