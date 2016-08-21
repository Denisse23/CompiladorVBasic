package proyectocompi1;

/* The following code was generated by JFlex 1.6.1 */

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.6.1
 * from the specification file <tt>C:/Users/kingdomkaz/Documents/Analisis de Algoritmos/ProyectoCompi1/build/classes/proyectocompi1/Vbasic.flex</tt>
 */
class Lexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\56\1\55\1\55\1\55\1\55\22\0\1\56\1\0\1\54"+
    "\5\0\1\43\1\44\1\51\1\50\1\0\1\50\1\0\1\51\12\52"+
    "\2\0\1\46\1\47\1\45\2\0\1\27\1\1\1\53\1\15\1\6"+
    "\1\12\2\53\1\10\2\53\1\35\1\53\1\31\1\32\2\53\1\37"+
    "\1\22\1\24\1\53\1\41\1\16\1\33\2\53\6\0\1\23\1\42"+
    "\1\30\1\7\1\2\1\11\1\3\1\17\1\4\2\53\1\20\1\34"+
    "\1\5\1\13\1\36\1\53\1\14\1\26\1\21\1\25\3\53\1\40"+
    "\1\53\12\0\1\55\32\0\1\56\u15df\0\1\56\u097f\0\13\56\35\0"+
    "\1\55\1\55\5\0\1\56\57\0\1\56\u0fa0\0\1\56\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\ud00f\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\1\1\2\17\3\1\4\1\5\2\6\1\7\1\10"+
    "\1\11\1\1\2\2\6\3\1\12\4\3\1\13\6\3"+
    "\1\14\1\3\1\15\2\3\1\6\1\0\1\16\4\3"+
    "\1\17\2\3\1\20\2\3\1\21\2\3\1\22\2\3"+
    "\1\23\6\3\1\24\5\3\1\25\1\26\1\27\1\3"+
    "\1\30\1\3\1\31\2\3\1\32\6\3\1\33\1\3"+
    "\1\34\2\3\1\35\1\3\1\36";

  private static int [] zzUnpackAction() {
    int [] result = new int[106];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\57\0\136\0\215\0\274\0\353\0\u011a\0\u0149"+
    "\0\u0178\0\u01a7\0\u01d6\0\u0205\0\u0234\0\u0263\0\u0292\0\u02c1"+
    "\0\u02f0\0\57\0\57\0\u031f\0\u034e\0\57\0\57\0\57"+
    "\0\u037d\0\u03ac\0\u03db\0\u040a\0\u0439\0\u0468\0\u0497\0\u04c6"+
    "\0\u04f5\0\215\0\u0524\0\u0553\0\u0582\0\u05b1\0\215\0\u05e0"+
    "\0\u060f\0\u063e\0\u066d\0\u069c\0\u06cb\0\215\0\u06fa\0\215"+
    "\0\u0729\0\u0758\0\57\0\u03ac\0\u03ac\0\u0787\0\u07b6\0\u07e5"+
    "\0\u0814\0\215\0\u0843\0\u0872\0\215\0\u08a1\0\u08d0\0\215"+
    "\0\u08ff\0\u092e\0\215\0\u095d\0\u098c\0\215\0\u09bb\0\u09ea"+
    "\0\u0a19\0\u0a48\0\u0a77\0\u0aa6\0\215\0\u0ad5\0\u0b04\0\u0b33"+
    "\0\u0b62\0\u0b91\0\215\0\215\0\215\0\u0bc0\0\215\0\u0bef"+
    "\0\215\0\u0c1e\0\u0c4d\0\215\0\u0c7c\0\u0cab\0\u0cda\0\u0d09"+
    "\0\u0d38\0\u0d67\0\215\0\u0d96\0\215\0\u0dc5\0\u0df4\0\215"+
    "\0\u0e23\0\215";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[106];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\4\4\1\5\1\4\1\6\1\4\1\7"+
    "\2\4\1\10\1\11\3\4\1\12\1\4\1\13\2\4"+
    "\1\14\1\4\1\15\1\16\1\17\1\4\1\20\1\4"+
    "\1\21\3\4\1\22\1\23\1\24\1\25\1\26\1\27"+
    "\1\30\1\31\1\4\1\32\2\33\60\0\1\4\1\34"+
    "\10\4\1\35\24\4\1\36\2\4\7\0\2\4\4\0"+
    "\42\4\7\0\2\4\4\0\4\4\1\37\12\4\1\40"+
    "\22\4\7\0\2\4\4\0\4\4\1\41\3\4\1\42"+
    "\31\4\7\0\2\4\4\0\12\4\1\43\7\4\1\44"+
    "\1\4\1\45\15\4\7\0\2\4\4\0\3\4\1\46"+
    "\6\4\1\47\27\4\7\0\2\4\4\0\16\4\1\50"+
    "\23\4\7\0\2\4\4\0\20\4\1\51\3\4\1\52"+
    "\15\4\7\0\2\4\4\0\13\4\1\53\2\4\1\54"+
    "\23\4\7\0\2\4\4\0\4\4\1\55\20\4\1\56"+
    "\14\4\7\0\2\4\4\0\12\4\1\57\27\4\7\0"+
    "\2\4\4\0\13\4\1\60\26\4\7\0\2\4\4\0"+
    "\12\4\1\16\27\4\7\0\2\4\4\0\12\4\1\61"+
    "\27\4\7\0\2\4\4\0\1\4\1\62\40\4\7\0"+
    "\2\4\52\0\1\63\54\0\1\63\1\0\1\63\10\0"+
    "\42\4\7\0\1\31\1\4\3\0\54\64\1\65\1\0"+
    "\1\64\55\0\2\33\1\0\2\4\1\66\37\4\7\0"+
    "\2\4\4\0\12\4\1\67\27\4\7\0\2\4\4\0"+
    "\36\4\1\70\1\4\1\71\1\4\7\0\2\4\4\0"+
    "\6\4\1\72\33\4\7\0\2\4\4\0\25\4\1\73"+
    "\14\4\7\0\2\4\4\0\20\4\1\74\21\4\7\0"+
    "\2\4\4\0\13\4\1\75\26\4\7\0\2\4\4\0"+
    "\17\4\1\76\22\4\7\0\2\4\4\0\4\4\1\77"+
    "\35\4\7\0\2\4\4\0\33\4\1\100\6\4\7\0"+
    "\2\4\4\0\3\4\1\101\36\4\7\0\2\4\4\0"+
    "\13\4\1\102\26\4\7\0\2\4\4\0\41\4\1\103"+
    "\7\0\2\4\4\0\24\4\1\104\15\4\7\0\2\4"+
    "\4\0\1\4\1\105\40\4\7\0\2\4\4\0\6\4"+
    "\1\60\33\4\7\0\2\4\4\0\20\4\1\106\21\4"+
    "\7\0\2\4\4\0\12\4\1\107\27\4\7\0\2\4"+
    "\4\0\20\4\1\110\21\4\7\0\2\4\4\0\3\4"+
    "\1\111\36\4\7\0\2\4\4\0\17\4\1\112\22\4"+
    "\7\0\2\4\4\0\1\4\1\113\40\4\7\0\2\4"+
    "\4\0\22\4\1\114\17\4\7\0\2\4\4\0\1\4"+
    "\1\115\40\4\7\0\2\4\4\0\1\4\1\116\40\4"+
    "\7\0\2\4\4\0\25\4\1\104\14\4\7\0\2\4"+
    "\4\0\27\4\1\117\12\4\7\0\2\4\4\0\17\4"+
    "\1\120\22\4\7\0\2\4\4\0\3\4\1\121\20\4"+
    "\1\122\15\4\7\0\2\4\4\0\1\4\1\123\40\4"+
    "\7\0\2\4\4\0\4\4\1\124\35\4\7\0\2\4"+
    "\4\0\35\4\1\125\4\4\7\0\2\4\4\0\24\4"+
    "\1\126\15\4\7\0\2\4\4\0\4\4\1\127\35\4"+
    "\7\0\2\4\4\0\1\4\1\130\40\4\7\0\2\4"+
    "\4\0\10\4\1\131\31\4\7\0\2\4\4\0\17\4"+
    "\1\131\22\4\7\0\2\4\4\0\2\4\1\132\37\4"+
    "\7\0\2\4\4\0\20\4\1\133\21\4\7\0\2\4"+
    "\4\0\1\4\1\134\40\4\7\0\2\4\4\0\4\4"+
    "\1\135\35\4\7\0\2\4\4\0\27\4\1\136\12\4"+
    "\7\0\2\4\4\0\13\4\1\137\26\4\7\0\2\4"+
    "\4\0\22\4\1\140\17\4\7\0\2\4\4\0\1\4"+
    "\1\141\40\4\7\0\2\4\4\0\3\4\1\142\36\4"+
    "\7\0\2\4\4\0\2\4\1\143\37\4\7\0\2\4"+
    "\4\0\20\4\1\144\21\4\7\0\2\4\4\0\4\4"+
    "\1\145\35\4\7\0\2\4\4\0\4\4\1\143\35\4"+
    "\7\0\2\4\4\0\13\4\1\143\26\4\7\0\2\4"+
    "\4\0\12\4\1\146\27\4\7\0\2\4\4\0\24\4"+
    "\1\147\15\4\7\0\2\4\4\0\4\4\1\150\35\4"+
    "\7\0\2\4\4\0\13\4\1\151\26\4\7\0\2\4"+
    "\4\0\1\4\1\152\40\4\7\0\2\4\3\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[3666];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\1\1\11\17\1\2\11\2\1\3\11\32\1\1\11"+
    "\1\0\66\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[106];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;
  
  /** 
   * The number of occupied positions in zzBuffer beyond zzEndRead.
   * When a lead/high surrogate has been read from the input stream
   * into the final zzBuffer position, this will have a value of 1;
   * otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Lexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x110000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 210) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException("Reader returned 0 characters. See JFlex examples for workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      /* If numRead == requested, we might have requested to few chars to
         encode a full Unicode character. We assume that a Reader would
         otherwise never return half characters. */
      if (numRead == requested) {
        if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    zzFinalHighSurrogate = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public int yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
              {
                return 0;
              }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { System.out.println("<NUMERO,"+"'"+yytext()+"'>");
            }
          case 31: break;
          case 2: 
            { 
            }
          case 32: break;
          case 3: 
            { System.out.println("<IDENTIFICADOR,"+"'"+yytext()+"'>");
            }
          case 33: break;
          case 4: 
            { System.out.println("<ABRIRPARENTESIS>");
            }
          case 34: break;
          case 5: 
            { System.out.println("<CERRARPARENTESIS>");
            }
          case 35: break;
          case 6: 
            { System.out.println("<OPREL,"+"'"+yytext()+"'>");
            }
          case 36: break;
          case 7: 
            { System.out.println("<ASIGNACION>");
            }
          case 37: break;
          case 8: 
            { System.out.println("<OPSUM,"+"'"+yytext()+"'>");
            }
          case 38: break;
          case 9: 
            { System.out.println("<OPMULT,"+"'"+yytext()+"'>");
            }
          case 39: break;
          case 10: 
            { System.out.println("<IF>");
            }
          case 40: break;
          case 11: 
            { System.out.println("<DO>");
            }
          case 41: break;
          case 12: 
            { System.out.println("<AS>");
            }
          case 42: break;
          case 13: 
            { System.out.println("<OPLOG,"+"'"+yytext()+"'>");
            }
          case 43: break;
          case 14: 
            { System.out.println("<CADENA,"+"'"+yytext()+"'>");
            }
          case 44: break;
          case 15: 
            { System.out.println("<END>");
            }
          case 45: break;
          case 16: 
            { System.out.println("<FOR>");
            }
          case 46: break;
          case 17: 
            { System.out.println("<DIM>");
            }
          case 47: break;
          case 18: 
            { System.out.println("<SUB>");
            }
          case 48: break;
          case 19: 
            { System.out.println("<NOT>");
            }
          case 49: break;
          case 20: 
            { System.out.println("<ELSE>");
            }
          case 50: break;
          case 21: 
            { System.out.println("<TRUEFALSE,"+"'"+yytext()+"'>");
            }
          case 51: break;
          case 22: 
            { System.out.println("<THEN>");
            }
          case 52: break;
          case 23: 
            { System.out.println("<LOOP>");
            }
          case 53: break;
          case 24: 
            { System.out.println("<BEGIN>");
            }
          case 54: break;
          case 25: 
            { System.out.println("<TIPOPARAMETRO,"+"'"+yytext()+"'>");
            }
          case 55: break;
          case 26: 
            { System.out.println("<WHILE>");
            }
          case 56: break;
          case 27: 
            { System.out.println("<TIPO,"+"'"+yytext()+"'>");
            }
          case 57: break;
          case 28: 
            { System.out.println("<RETURN>");
            }
          case 58: break;
          case 29: 
            { System.out.println("<FUNCTION>");
            }
          case 59: break;
          case 30: 
            { System.out.println("<STRUCTURE>");
            }
          case 60: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
