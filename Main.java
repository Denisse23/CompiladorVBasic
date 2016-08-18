import java.io.*;

public class Main{

	static public void main(String argv[]){
		try{
		
			Lexer scanner = new Lexer( new FileReader("./basic.txt"));
			scanner.yylex();
		}catch (Exception e){
			System.out.println(e);
		}

}
}