/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc;

/**
 *
 * @author kingdomkaz
 */
public class JavaSymbol extends java_cup.runtime.Symbol {
  private int line;
  private int column;
  private String text;

  public JavaSymbol(int type, int line, int column, String text) {
    this(type, line, column, -1, -1, text, null);
  }

  public JavaSymbol(int type, int line, int column, String text, Object value) {
    this(type, line, column, -1, -1, text, value);
  }

  public JavaSymbol(int type, int line, int column, int left, int right, String text, Object value) {
    super(type, left, right, value);
    this.line = line;
    this.column = column;
    this.text = text;
  }

  public int getLine() {
    return line;
  }

  public int getColumn() {
    return column;
  }

  public String getText() {
    return text;
  }

  private String getTokenName(int token) {
    try {
      java.lang.reflect.Field [] classFields = sym.class.getFields();
      for (int i = 0; i < classFields.length; i++) {
        if (classFields[i].getInt(null) == token) {
          return classFields[i].getName();
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }

    return "UNKNOWN TOKEN";
  }

  @Override
  public String toString() {   
    return "line: "+line+" , column: "+column+ " , lexeme: '"+(getTokenName(sym)== "newline" ? "salto de linea": (text))+"'"+" , sym: "+getTokenName(sym)+(value == null ? ", no se esperaba: "+ text: (" , no se esperaba: '"+(getTokenName(sym)== "newline" ? "salto de linea": (value))+"'"));
  }
}
