/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.codigofinal;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import pc.CodigoIntermedio.CuadruploRow;
import pc.CodigoIntermedio.Cuadruplos;
import pc.Tipos.Funcion;
import pc.tabla.TablasDeSimbolos;
import pc.tabla.Token;

/**
 *
 * @author Denisse
 */
public class GenerarCodigoFinal {

    TablasDeSimbolos tds;
    Cuadruplos cuadruplos;
    File codigofinal;
    ArrayList<String> lineas;
    ArrayList<Token> variablesglobales;
    ArrayList<String> mensajes;
    int numlineasigfun = 0;
    String[] temps;
    String[] reg_s;
    String[] reg_a;
    String[] reg_v;
    String funcionactual = "";
    int offsetvarlocales = 0;
    int spactual = 0;
    int contadorcallfunction = -1;
    String retguardado = "";

    ArrayList<Stack<String>> almacenar_registros_s = new ArrayList();

    public GenerarCodigoFinal(TablasDeSimbolos tds, Cuadruplos cuadruplos) {
        this.tds = tds;
        this.cuadruplos = cuadruplos;
        lineas = new ArrayList();
        variablesglobales = tds.get_variables_globales();
        mensajes = cuadruplos.getMensajes();
        temps = new String[10];
        reg_s = new String[8];
        reg_a = new String[4];
        reg_v = new String[2];
        initRegistros(temps);
        initRegistros(reg_s);
        initRegistros(reg_a);
        initRegistros(reg_v);
        initPilaRegistros();
    }

    private void initPilaRegistros() {
        for (int i = 0; i < 8; i++) {
            almacenar_registros_s.add(new Stack<String>());
        }
    }

    private void initRegistros(String[] r) {
        for (int i = 0; i < r.length; i++) {
            r[i] = "";
        }
    }

    private void generarData() {
        lineas.add("        .data");
        for (Token var : variablesglobales) {
            if (var.getTipo().toString().equals("Integer")) {
                lineas.add("_" + var.getId() + ":        .word 0");
            }
            if (var.getTipo().toString().equals("Boolean")) {
                lineas.add("_" + var.getId() + ":        .byte 0");
            }
        }
        int contador = 1;
        for (String mesgs : mensajes) {
            lineas.add("_mesg" + contador + ":        .asciiz " + mesgs);
            contador++;

        }
        lineas.add("_true:        .asciiz \"true\\n\"");
        lineas.add("_false:        .asciiz \"false\\n\"");
        lineas.add("bufferboolean:   .space 8");
        lineas.add("");
        lineas.add("");

    }

    private void generarText() {
        lineas.add("        .text");
        lineas.add("        .globl main");
        lineas.add("");
        lineas.add("");

        lineas.add("readboolean:");
        lineas.add("        sw $fp, -4($sp)");
        lineas.add("        sw $ra, -8($sp)");
        lineas.add("        move $fp, $sp");
        lineas.add("        sub $sp, $sp, 12");
        lineas.add("        li $v0, 8");
        lineas.add("        la $a0, bufferboolean");
        lineas.add("        li $a1, 7");
        lineas.add("        move $t1, $a0");
        lineas.add("        syscall");
        lineas.add("        la $t2, _true");
        lineas.add("        li $t0, 1");
        lineas.add("loop:");
        lineas.add("        lb $t3, ($t1)");
        lineas.add("        lb $t4, ($t2)");
        lineas.add("        bne $t3, $t4 missmatch");
        lineas.add("        beq $t3,$zero,checkt2");
        lineas.add("        addi $t1,$t1,1");
        lineas.add("        addi $t2,$t2,1");
        lineas.add("        j loop");
        lineas.add("missmatch:");
        lineas.add("        beq $t0, $zero ninguna");
        lineas.add("        li $t0, 0");
        lineas.add("        add $t1,$zero,$t1");
        lineas.add("        la $t2, _false");
        lineas.add("        j loop");
        lineas.add("ninguna:");
        lineas.add("        li $v0, 10");
        lineas.add("        syscall");
        lineas.add("checkt2:");
        lineas.add("        beq $t0,$zero, etiquef");
        lineas.add("        li $v0, 1");
        lineas.add("        b etiquefinalreadboolean");
        lineas.add("etiquef:");
        lineas.add("        li $v0, 0");
        lineas.add("etiquefinalreadboolean:");
        lineas.add("        move $sp, $fp");
        lineas.add("        lw $fp, -4($sp)");
        lineas.add("        lw $ra, -8($sp)");
        lineas.add("        jr $ra");
        lineas.add("");
        lineas.add("");

        lineas.add("imprimirboolean:");
        lineas.add("        sw $fp, -4($sp)");
        lineas.add("        sw $ra, -8($sp)");
        lineas.add("        move $fp, $sp");
        lineas.add("        sub $sp, $sp, 12");
        lineas.add("        beqz $a0, etiquefalsa");
        lineas.add("        li $v0, 4");
        lineas.add("        la $a0, _true");
        lineas.add("        syscall");
        lineas.add("        b etiquefinalimprimirboolean");
        lineas.add("etiquefalsa:");
        lineas.add("        li $v0, 4");
        lineas.add("        la $a0, _false");
        lineas.add("        syscall");
        lineas.add("etiquefinalimprimirboolean:");
        lineas.add("        move $sp, $fp");
        lineas.add("        lw $fp, -4($sp)");
        lineas.add("        lw $ra, -8($sp)");
        lineas.add("        jr $ra");
        lineas.add("");
        lineas.add("");
        generarFunciones();

    }

    private void generarFunciones() {
        while (numlineasigfun < cuadruplos.getRows().size() - 1) {
            numlineasigfun = cuadruplos.get_num_linea_sig_func(numlineasigfun);
            String name = cuadruplos.getRows().get(numlineasigfun).getArgumento1();
            funcionactual = cuadruplos.getRows().get(numlineasigfun).getArgumento1().substring(10);
            if (cuadruplos.getRows().get(numlineasigfun).getArgumento1().toUpperCase().equals("ETIQUEFUNCMAIN")) {
                initRegistros(temps);
                initRegistros(reg_s);
                initRegistros(reg_a);
                offsetvarlocales = 4;
                lineas.add("main:");
                lineas.add("        move $fp, $sp");
                lineas.add("");
                insertVaribalesLocales();

                for (int i = numlineasigfun + 1; i < cuadruplos.get_num_linea_fin_func(name); i++) {
                    if (cuadruplos.getRows().get(i).getOperador().equals("PRINT")) {
                        insertPrint(cuadruplos.getRows().get(i));
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("READ")) {
                        insertRead(cuadruplos.getRows().get(i));
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("=")) {
                        insertAsignacion(cuadruplos.getRows().get(i));
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("+")) {
                        insertOperacionAritmetica(cuadruplos.getRows().get(i), "add");
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("-")) {
                        insertOperacionAritmetica(cuadruplos.getRows().get(i), "sub");
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("*")) {
                        insertOperacionAritmetica(cuadruplos.getRows().get(i), "mul");
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("/")) {
                        insertOperacionAritmetica(cuadruplos.getRows().get(i), "div");
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("ETIQUE")) {
                        lineas.add("");
                        lineas.add("_" + cuadruplos.getRows().get(i).getArgumento1() + ":");
                    }
                    if (cuadruplos.getRows().get(i).getOperador().contains("IF")) {
                        String oprel = cuadruplos.getRows().get(i).getOperador().substring(2);
                        if (oprel.equals("<")) {
                            insertCondition(cuadruplos.getRows().get(i), "blt");
                        } else if (oprel.equals("<=")) {
                            insertCondition(cuadruplos.getRows().get(i), "ble");
                        } else if (oprel.equals(">")) {
                            insertCondition(cuadruplos.getRows().get(i), "bgt");
                        } else if (oprel.equals(">=")) {
                            insertCondition(cuadruplos.getRows().get(i), "bge");
                        } else if (oprel.equals("=")) {
                            insertCondition(cuadruplos.getRows().get(i), "beq");
                        } else if (oprel.equals("<>")) {
                            insertCondition(cuadruplos.getRows().get(i), "bne");
                        }
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("GOTO")) {
                        lineas.add("        b _" + cuadruplos.getRows().get(i).getArgumento1());
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("PARAM")) {
                        insertParam(cuadruplos.getRows().get(i));
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("CALL")) {
                        initRegistros(reg_a);

                        if (contadorcallfunction > -1) {
                            String t = getTempDisponible();
                            lineas.add("        move $" + t + ", $v0");
                            temps[Integer.parseInt(t.substring(1))] = retguardado;
                        }
                        guardarTemporalesvivos();
                        lineas.add("        jal _" + cuadruplos.getRows().get(i).getArgumento1());
                        restaurarTemporales();
                        if (!((Funcion) tds.getTabla("Principal").get_id(cuadruplos.getRows().get(i).getArgumento1()).getTipo()).getRetorno().toString().equals("Void")) {
                            contadorcallfunction++;
                        }
                        retguardado = "RET" + i;

                    }

                }
                lineas.add("        li $v0, 10");
                lineas.add("        syscall");
                numlineasigfun = cuadruplos.get_num_linea_fin_func(name);
            } else {
                contadorcallfunction = -1;
                lineas.add("_" + funcionactual + ":");
                lineas.add("        sw $fp, -4($sp)");
                lineas.add("        sw $ra, -8($sp)");
                offsetvarlocales = 12;
                insertParametros();
                insertVaribalesLocales();
                lineas.add("");
                for (int i = numlineasigfun + 1; i < cuadruplos.get_num_linea_fin_func(name); i++) {
                    if (cuadruplos.getRows().get(i).getOperador().equals("PRINT")) {
                        insertPrint(cuadruplos.getRows().get(i));
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("READ")) {
                        insertRead(cuadruplos.getRows().get(i));
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("=")) {
                        insertAsignacion(cuadruplos.getRows().get(i));
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("+")) {
                        insertOperacionAritmetica(cuadruplos.getRows().get(i), "add");
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("-")) {
                        insertOperacionAritmetica(cuadruplos.getRows().get(i), "sub");
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("*")) {
                        insertOperacionAritmetica(cuadruplos.getRows().get(i), "mul");
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("/")) {
                        insertOperacionAritmetica(cuadruplos.getRows().get(i), "div");
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("ETIQUE")) {
                        lineas.add("");
                        lineas.add("_" + cuadruplos.getRows().get(i).getArgumento1() + ":");
                    }
                    if (cuadruplos.getRows().get(i).getOperador().contains("IF")) {
                        String oprel = cuadruplos.getRows().get(i).getOperador().substring(2);
                        if (oprel.equals("<")) {
                            insertCondition(cuadruplos.getRows().get(i), "blt");
                        } else if (oprel.equals("<=")) {
                            insertCondition(cuadruplos.getRows().get(i), "ble");
                        } else if (oprel.equals(">")) {
                            insertCondition(cuadruplos.getRows().get(i), "bgt");
                        } else if (oprel.equals(">=")) {
                            insertCondition(cuadruplos.getRows().get(i), "bge");
                        } else if (oprel.equals("=")) {
                            insertCondition(cuadruplos.getRows().get(i), "beq");
                        } else if (oprel.equals("<>")) {
                            insertCondition(cuadruplos.getRows().get(i), "bne");
                        }
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("GOTO")) {
                        lineas.add("        b _" + cuadruplos.getRows().get(i).getArgumento1());
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("PARAM")) {
                        insertParam(cuadruplos.getRows().get(i));
                    }
                    if (cuadruplos.getRows().get(i).getOperador().equals("CALL")) {
                        initRegistros(reg_a);

                        if (contadorcallfunction > -1) {
                            String t = getTempDisponible();
                            lineas.add("        move $" + t + ", $v0");
                            temps[Integer.parseInt(t.substring(1))] = retguardado;
                        }

                        guardarTemporalesvivos();
                        lineas.add("        jal _" + cuadruplos.getRows().get(i).getArgumento1());
                        restaurarTemporales();
                        if (!((Funcion) tds.getTabla("Principal").get_id(cuadruplos.getRows().get(i).getArgumento1()).getTipo()).getRetorno().equals("Void")) {
                            contadorcallfunction++;
                        }
                        retguardado = "RET" + i;
                    }

                    if (cuadruplos.getRows().get(i).getOperador().equals("RET")) {
                        insertReturn(cuadruplos.getRows().get(i));
                    }

                }
                restablecerFrame();
                numlineasigfun = cuadruplos.get_num_linea_fin_func(name);
            }

        }
    }

    private void restablecerFrame() {
        lineas.add("        move $sp, $fp");
        ArrayList<Token> parametros = tds.get_parametros(funcionactual);
        for (int i = parametros.size() - 1; i >= 0; i--) {
            offsetvarlocales -= 4;
            lineas.add("        lw $s" + i + ", -" + offsetvarlocales + "($sp)");
            reg_s[i] = almacenar_registros_s.get(i).pop();

        }
        lineas.add("        lw $ra, -8($sp)");
        lineas.add("        lw $fp, -4($sp)");
        lineas.add("        jr $ra");
        lineas.add("");
        initRegistros(temps);

    }

    private void insertParametros() {
        ArrayList<Token> parametros = tds.get_parametros(funcionactual);
        for (int i = 0; i < parametros.size(); i++) {
            lineas.add("        sw $s" + i + ", -" + offsetvarlocales + "($sp)");
            lineas.add("        move $s" + i + ", $a" + i);
            almacenar_registros_s.get(i).push(reg_s[i]);
            reg_s[i] = parametros.get(i).getId();
            offsetvarlocales += 4;
        }
        lineas.add("        move $fp, $sp");
        if (parametros.size() > 0) {
            lineas.add("        sub $sp, $sp, " + (offsetvarlocales));
        }
        spactual = offsetvarlocales;

    }
    String[] contenidotemps = new String[10];

    private void restaurarTemporales() {
        for (int i = 9; i >= 0; i--) {
            if (!contenidotemps[i].equals("")) {
                lineas.add("        lw $t" + i + ", -" + (spactual - 4) + "($fp)");
                lineas.add("        add $sp, $sp, " + 4);
                temps[i] = contenidotemps[i];
                spactual -= 4;
            }
            contenidotemps[i] = "";
        }
    }

    private void guardarTemporalesvivos() {
        for (int i = 0; i < temps.length; i++) {
            contenidotemps[i] = "";
            if (!temps[i].equals("")) {
                contenidotemps[i] = temps[i];
                lineas.add("        sub $sp, $sp, " + 4);
                lineas.add("        sw $t" + i + ", -" + spactual + "($fp)");
                spactual += 4;
            }

        }

    }

    private void insertVaribalesLocales() {
        ArrayList<Token> varlocales = tds.get_variables_locales(funcionactual);
        int offto = 0;
        if (varlocales.size() > 0) {
            offto = offsetvarlocales + varlocales.get(varlocales.size() - 1).getOffset() + varlocales.get(varlocales.size() - 1).getTipo().getTamano();
            while (offto % 4 != 0) {
                offto++;
            }
        }
            lineas.add("        sub $sp, $sp, " + offto);
            lineas.add("");
            spactual =offto;
        
    }

    private void insertReturn(CuadruploRow cr) {
        if (cr.getArgumento1().matches("[0-9]+")) {
            lineas.add("        li $v0, " + cr.getArgumento1());
        } else if (cr.getArgumento1().toLowerCase().equals("true") || cr.getArgumento1().toLowerCase().equals("false")) {
            String bool = "0";
            if (cr.getArgumento1().toLowerCase().equals("true")) {
                bool = "1";
            }
            lineas.add("        li $v0, " + bool);
        } else if (isInVariablesGlobales(cr.getArgumento1()) != -1) {
            int index = isInVariablesGlobales(cr.getArgumento1());
            if (variablesglobales.get(index).getTipo().toString().equals("Integer")) {
                lineas.add("       lw $v0, _" + cr.getArgumento1());
            } else if (variablesglobales.get(index).getTipo().toString().equals("Boolean")) {
                lineas.add("       lb $v0, _" + cr.getArgumento1());
            }

        } else if (isInVariablesLocales(cr.getArgumento1()) != null) {
            Token vartoken = isInVariablesLocales(cr.getArgumento1());
            if (vartoken.getTipo().toString().equals("Integer")) {
                lineas.add("       lw $v0, -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
            } else if (vartoken.getTipo().toString().equals("Boolean")) {
                lineas.add("       lb $v0, -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
            }
        } else if (isInParametros(cr.getArgumento1()) != null) {
            String s = buscarEnS(cr.getArgumento1());
            lineas.add("       move $v0, $" + s);
        } else {
            String temp = buscarTemp(cr.getArgumento1());
            lineas.add("       move $v0, $" + temp);
        }
    }

    private void insertAsignacion(CuadruploRow cr) {
        if (isInVariablesGlobales(cr.getRespuesta()) != -1) {
            int index = isInVariablesGlobales(cr.getRespuesta());
            String forma = "sw";
            if (variablesglobales.get(index).getTipo().toString().equals("Boolean")) {
                forma = "sb";
            }
            if (buscarTemp(cr.getArgumento1()) != null) {
                String temp = buscarTemp(cr.getArgumento1());
                lineas.add("        " + forma + " $" + temp + ", _" + variablesglobales.get(index).getId());
                setTempDisponible(temp);
                lineas.add("");
            } else if (cr.getArgumento1().matches("RET[0-9]+")) {
                contadorcallfunction--;
                lineas.add("        " + forma + " $v0, _" + variablesglobales.get(index).getId());
                lineas.add("");
            } else if (isInParametros(cr.getArgumento1()) != null) {
                String s = buscarEnS(cr.getArgumento1());
                lineas.add("        " + forma + " $" + s + ", _" + variablesglobales.get(index).getId());
                lineas.add("");
            }
        } else if (isInVariablesLocales(cr.getRespuesta()) != null) {
            Token vartoken = isInVariablesLocales(cr.getRespuesta());
            String forma = "sw";
            if (vartoken.getTipo().toString().equals("Boolean")) {
                forma = "sb";
            }
            if (buscarTemp(cr.getArgumento1()) != null) {
                String temp = buscarTemp(cr.getArgumento1());
                lineas.add("        " + forma + " $" + temp + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
                setTempDisponible(temp);
                lineas.add("");
            } else if (cr.getArgumento1().matches("RET[0-9]+")) {
                contadorcallfunction--;
                lineas.add("        " + forma + " $v0, -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
                lineas.add("");
            } else if (isInParametros(cr.getArgumento1()) != null) {
                String s = buscarEnS(cr.getArgumento1());
                lineas.add("        " + forma + " $" + s + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
                lineas.add("");
            }
        } else if (isInParametros(cr.getRespuesta()) != null) {
            String s = buscarEnS(cr.getRespuesta());
            if (buscarTemp(cr.getArgumento1()) != null) {
                String temp = buscarTemp(cr.getArgumento1());
                lineas.add("        move $" + s + ", $" + temp);
                setTempDisponible(temp);
                lineas.add("");
            } else if (cr.getArgumento1().matches("RET[0-9]+")) {
                contadorcallfunction--;
                lineas.add("        move $" + s + ", $v0");
                lineas.add("");
            } else if (isInParametros(cr.getArgumento1()) != null) {
                String s1 = buscarEnS(cr.getArgumento1());
                lineas.add("        move $" + s1 + ", $" + s);
                lineas.add("");
            }

        } else {
            String temp = getTempDisponible();
            if (isInVariablesGlobales(cr.getArgumento1()) != -1) {
                int index = isInVariablesGlobales(cr.getArgumento1());
                if (variablesglobales.get(index).getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp + ", _" + variablesglobales.get(index).getId());
                } else if (variablesglobales.get(index).getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp + ", _" + variablesglobales.get(index).getId());
                }
                temps[Integer.parseInt(temp.substring(1))] = cr.getRespuesta();
            } else if (isInVariablesLocales(cr.getArgumento1()) != null) {
                Token vartoken = isInVariablesLocales(cr.getArgumento1());
                if (vartoken.getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
                } else if (vartoken.getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
                }
                temps[Integer.parseInt(temp.substring(1))] = cr.getRespuesta();
            } else if (isInParametros(cr.getArgumento1()) != null) {
                String s = buscarEnS(cr.getArgumento1());
                lineas.add("        move $" + temp + ", $" + s);
            } else if (cr.getArgumento1().matches("[0-9]+")) {
                lineas.add("        li $" + temp + ", " + cr.getArgumento1());
                temps[Integer.parseInt(temp.substring(1))] = cr.getRespuesta();
            } else if (cr.getArgumento1().toLowerCase().equals("true") || cr.getArgumento1().toLowerCase().equals("false")) {
                String bool = "0";
                if (cr.getArgumento1().equals("true")) {
                    bool = "1";
                }
                lineas.add("        li $" + temp + ", " + bool);
                temps[Integer.parseInt(temp.substring(1))] = cr.getRespuesta();
            }
            lineas.add("");
        }
    }

    private void insertRead(CuadruploRow cr) {
        if (isInVariablesGlobales(cr.getArgumento1()) != -1) {
            int index = isInVariablesGlobales(cr.getArgumento1());
            if (variablesglobales.get(index).getTipo().toString().equals("Integer")) {
                lineas.add("        li $v0, 5");
                lineas.add("        syscall");
                lineas.add("        sw $v0, " + "_" + variablesglobales.get(index).getId());
            } else if (variablesglobales.get(index).getTipo().toString().equals("Boolean")) {
                lineas.add("        jal readboolean");
                lineas.add("        sb $v0, _" + variablesglobales.get(index).getId());
            }
            lineas.add("");
        } else if (isInVariablesLocales(cr.getArgumento1()) != null) {
            Token vartoken = isInVariablesLocales(cr.getArgumento1());
            int offset = offsetvarlocales + vartoken.getOffset();
            if (vartoken.getTipo().toString().equals("Integer")) {
                lineas.add("        li $v0, 5");
                lineas.add("        syscall");
                lineas.add("        sw $v0, -" + offset + "($fp)");

            } else if (vartoken.getTipo().toString().equals("Boolean")) {
                lineas.add("        jal readboolean");
                lineas.add("        sb $v0, -" + offset + "($fp)");
            }

            lineas.add("");
        } else if (isInParametros(cr.getArgumento1()) != null) {
            String s = buscarEnS(cr.getArgumento1());
            Token vartoken = isInParametros(cr.getArgumento1());
            if (vartoken.getTipo().toString().equals("Integer")) {
                lineas.add("        li $v0, 5");
                lineas.add("        syscall");
                lineas.add("        move $" + s + ", $v0");

            } else if (vartoken.getTipo().toString().equals("Boolean")) {
                lineas.add("        jal readboolean");
                lineas.add("        move $" + s + ", $v0");
            }

            lineas.add("");
        }

    }

    private void insertPrint(CuadruploRow cr) {
        if (cr.getArgumento1().contains("\"")) {
            int men = mensajes.indexOf(cr.getArgumento1()) + 1;
            lineas.add("        li $v0, 4");
            lineas.add("        la $a0, _mesg" + men);
            lineas.add("        syscall");
            lineas.add("");
        } else if (cr.getArgumento1().matches("[0-9]+")) {
            lineas.add("        li $v0, 1");
            lineas.add("        li $a0, " + cr.getArgumento1());
            lineas.add("        syscall");
            lineas.add("");
        } else if (cr.getArgumento1().toLowerCase().equals("true") || cr.getArgumento1().toLowerCase().equals("false")) {
            String bool = "0";
            if (cr.getArgumento1().equals("true")) {
                bool = "1";
            }
            lineas.add("        li $a0, " + bool);
            lineas.add("        jal imprimirboolean");
            lineas.add("");
        } else if (isInVariablesGlobales(cr.getArgumento1()) != -1) {
            int index = isInVariablesGlobales(cr.getArgumento1());
            if (variablesglobales.get(index).getTipo().toString().equals("Integer")) {
                lineas.add("        li $v0, 1");
                lineas.add("        lw $a0, " + "_" + variablesglobales.get(index).getId());
                lineas.add("        syscall");
            } else if (variablesglobales.get(index).getTipo().toString().equals("Boolean")) {
                lineas.add("        lb $a0, _" + variablesglobales.get(index).getId());
                lineas.add("        jal imprimirboolean");

            }
            lineas.add("");
        } else if (isInVariablesLocales(cr.getArgumento1()) != null) {
            Token vartoken = isInVariablesLocales(cr.getArgumento1());
            if (funcionactual.toLowerCase().equals("main")) {
                int offset = offsetvarlocales + vartoken.getOffset();
                if (vartoken.getTipo().toString().equals("Integer")) {
                    lineas.add("        li $v0, 1");
                    lineas.add("        lw $a0, -" + offset + "($fp)");
                    lineas.add("        syscall");
                } else if (vartoken.getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $a0, -" + offset + "($fp)");
                    lineas.add("        jal imprimirboolean");
                }

                lineas.add("");
            }
        } else if (buscarTemp(cr.getArgumento1()) != null) {
            String temp = buscarTemp(cr.getArgumento1());
            lineas.add("        li $v0, 1");
            lineas.add("        move $a0, $" + temp);
            lineas.add("        syscall");
            lineas.add("");
            setTempDisponible(temp);
        } else if (cr.getArgumento1().matches("RET[0-9]+")) {
            contadorcallfunction--;
            String temp = getTempDisponible();
            lineas.add("        move $" + temp + ", $v0");
            lineas.add("        li $v0, 1");
            lineas.add("        move $a0, $" + temp);
            lineas.add("        syscall");
            lineas.add("");
        } else if (isInParametros(cr.getArgumento1()) != null) {
            String s = buscarEnS(cr.getArgumento1());
            Token vartoken = isInParametros(cr.getArgumento1());
            if (vartoken.getTipo().toString().equals("Integer")) {
                lineas.add("        li $v0, 1");
                lineas.add("        move $a0, $" + s);
                lineas.add("        syscall");
            } else if (vartoken.getTipo().toString().equals("Boolean")) {
                lineas.add("        move $a0, $" + s);
                lineas.add("        jal imprimirboolean");
            }

            lineas.add("");
        }
    }

    private void insertOperacionAritmetica(CuadruploRow cr, String op) {
        String temp = getTempDisponible();
        temps[Integer.parseInt(temp.substring(1))] = cr.getRespuesta();
        if (cr.getArgumento1().matches("[0-9]+")) {
            boolean isreg = false;
            String temp1 = getTempDisponible();
            lineas.add("        li $" + temp1 + ", " + cr.getArgumento1());
            temps[Integer.parseInt(temp1.substring(1))] = cr.getArgumento1();
            String temp2 = getTempDisponible();
            if (cr.getArgumento2().matches("[0-9]+")) {
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        li $" + temp2 + ", " + cr.getArgumento2());
            } else if (isInVariablesGlobales(cr.getArgumento2()) != -1) {
                int index = isInVariablesGlobales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        lw $" + temp2 + ", _" + cr.getArgumento2());
            } else if (isInVariablesLocales(cr.getArgumento2()) != null) {
                Token vartoken = isInVariablesLocales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        lw $" + temp2 + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");

            } else if (isInParametros(cr.getArgumento2()) != null) {
                String s = buscarEnS(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + s);

            } else if (cr.getArgumento2().matches("RET[0-9]+")) {
                isreg = true;
                if (buscarTemp(cr.getArgumento2()) != null) {
                    String t = buscarTemp(cr.getArgumento2());
                    lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + t);
                    setTempDisponible(t);
                    contadorcallfunction--;
                } else {
                    contadorcallfunction--;
                    lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $v0");
                }
            } else {
                String tem = buscarTemp(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + tem);
                setTempDisponible(tem);
            }
            if (!isreg) {
                lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + temp2);
            }
            setTempDisponible(temp1);
            setTempDisponible(temp2);
        } else if (isInVariablesGlobales(cr.getArgumento1()) != -1) {
            boolean isreg = false;
            int index = isInVariablesGlobales(cr.getArgumento1());
            String temp1 = getTempDisponible();
            lineas.add("        lw $" + temp1 + ", _" + variablesglobales.get(index).getId());
            temps[Integer.parseInt(temp1.substring(1))] = cr.getArgumento1();
            String temp2 = getTempDisponible();
            if (cr.getArgumento2().matches("[0-9]+")) {
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        li $" + temp2 + ", " + cr.getArgumento2());
            } else if (isInVariablesGlobales(cr.getArgumento2()) != -1) {
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        lw $" + temp2 + ", _" + cr.getArgumento2());
            } else if (isInVariablesLocales(cr.getArgumento2()) != null) {
                Token vartoken = isInVariablesLocales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        lw $" + temp2 + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");

            } else if (isInParametros(cr.getArgumento2()) != null) {
                String s = buscarEnS(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + s);

            } else if (cr.getArgumento2().matches("RET[0-9]+")) {
                isreg = true;
                if (buscarTemp(cr.getArgumento2()) != null) {
                    String t = buscarTemp(cr.getArgumento2());
                    lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + t);
                    setTempDisponible(t);
                    contadorcallfunction--;
                } else {
                    contadorcallfunction--;
                    lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $v0");
                }
            } else {
                String tem = buscarTemp(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + tem);
                setTempDisponible(tem);
            }
            if (!isreg) {
                lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + temp2);
            }
            setTempDisponible(temp1);
            setTempDisponible(temp2);
        } else if (isInVariablesLocales(cr.getArgumento1()) != null) {
            Token vartoken = isInVariablesLocales(cr.getArgumento1());
            boolean isreg = false;
            String temp1 = getTempDisponible();
            lineas.add("        lw $" + temp1 + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
            temps[Integer.parseInt(temp1.substring(1))] = cr.getArgumento1();
            String temp2 = getTempDisponible();
            if (cr.getArgumento2().matches("[0-9]+")) {
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        li $" + temp2 + ", " + cr.getArgumento2());
            } else if (isInVariablesGlobales(cr.getArgumento2()) != -1) {
                int index = isInVariablesGlobales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        lw $" + temp2 + ", _" + cr.getArgumento2());

            } else if (isInVariablesLocales(cr.getArgumento2()) != null) {
                Token vartoken1 = isInVariablesLocales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        lw $" + temp2 + ", -" + (offsetvarlocales + vartoken1.getOffset()) + "($fp)");

            } else if (isInParametros(cr.getArgumento2()) != null) {
                String s = buscarEnS(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + s);

            } else if (cr.getArgumento2().matches("RET[0-9]+")) {
                isreg = true;
                if (buscarTemp(cr.getArgumento2()) != null) {
                    String t = buscarTemp(cr.getArgumento2());
                    lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + t);
                    setTempDisponible(t);
                    contadorcallfunction--;
                } else {
                    contadorcallfunction--;
                    lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $v0");
                }
            } else {
                String tem = buscarTemp(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + tem);
                setTempDisponible(tem);
            }
            if (!isreg) {
                lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + temp2);
            }
            setTempDisponible(temp1);
            setTempDisponible(temp2);
        } else if (isInParametros(cr.getArgumento1()) != null) {
            String s = buscarEnS(cr.getArgumento1());
            boolean isreg = false;
            String temp2 = getTempDisponible();
            if (cr.getArgumento2().matches("[0-9]+")) {
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        li $" + temp2 + ", " + cr.getArgumento2());
            } else if (isInVariablesGlobales(cr.getArgumento2()) != -1) {
                int index = isInVariablesGlobales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        lw $" + temp2 + ", _" + cr.getArgumento2());

            } else if (isInVariablesLocales(cr.getArgumento2()) != null) {
                Token vartoken1 = isInVariablesLocales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        lw $" + temp2 + ", -" + (offsetvarlocales + vartoken1.getOffset()) + "($fp)");

            } else if (isInParametros(cr.getArgumento2()) != null) {
                String s1 = buscarEnS(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + op + " $" + temp + ", $" + s + ", $" + s1);

            } else if (cr.getArgumento2().matches("RET[0-9]+")) {
                isreg = true;
                if (buscarTemp(cr.getArgumento2()) != null) {
                    String t = buscarTemp(cr.getArgumento2());
                    lineas.add("        " + op + " $" + temp + ", $" + s + ", $" + t);
                    setTempDisponible(t);
                    contadorcallfunction--;
                } else {
                    contadorcallfunction--;
                    lineas.add("        " + op + " $" + temp + ", $" + s + ", $v0");
                }
            } else {
                String tem = buscarTemp(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + op + " $" + temp + ", $" + s + ", $" + tem);
                setTempDisponible(tem);
            }
            if (!isreg) {
                lineas.add("        " + op + " $" + temp + ", $" + s + ", $" + temp2);
            }
            setTempDisponible(temp2);
        } else if (cr.getArgumento1().matches("RET[0-9]+")) {
            String re = "v0";
            if (buscarTemp(cr.getArgumento1()) != null) {
                re = buscarTemp(cr.getArgumento1());
            }
            contadorcallfunction--;
            boolean isreg = false;
            String temp2 = getTempDisponible();
            if (cr.getArgumento2().matches("[0-9]+")) {
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        li $" + temp2 + ", " + cr.getArgumento2());
            } else if (isInVariablesGlobales(cr.getArgumento2()) != -1) {
                int index = isInVariablesGlobales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        lw $" + temp2 + ", _" + cr.getArgumento2());

            } else if (isInVariablesLocales(cr.getArgumento2()) != null) {
                Token vartoken1 = isInVariablesLocales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        lw $" + temp2 + ", -" + (offsetvarlocales + vartoken1.getOffset()) + "($fp)");

            } else if (isInParametros(cr.getArgumento2()) != null) {
                String s1 = buscarEnS(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + op + " $" + temp + ", $" + re + ", $" + s1);

            } else if (cr.getArgumento2().matches("RET[0-9]+")) {
                isreg = true;
                if (buscarTemp(cr.getArgumento2()) != null) {
                    String t = buscarTemp(cr.getArgumento2());
                    lineas.add("        " + op + " $" + temp + ", $" + re + ", $" + t);
                    setTempDisponible(t);
                    contadorcallfunction--;
                } else {
                    contadorcallfunction--;
                    lineas.add("        " + op + " $" + temp + ", $" + re + ", $v0");
                }
            } else {
                String tem = buscarTemp(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + op + " $" + temp + ", $" + re + ", $" + tem);
                setTempDisponible(tem);
            }
            if (!isreg) {
                lineas.add("        " + op + " $" + temp + ",$" + re + ", $" + temp2);
            }
            setTempDisponible(temp2);
            if (!re.equals("v0")) {
                setTempDisponible(re);
            }
        } else {
            String temp1 = buscarTemp(cr.getArgumento1());
            boolean isreg = false;
            String temp2 = getTempDisponible();
            if (cr.getArgumento2().matches("[0-9]+")) {
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        li $" + temp2 + ", " + cr.getArgumento2());
            } else if (isInVariablesGlobales(cr.getArgumento2()) != -1) {
                int index = isInVariablesGlobales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        lw $" + temp2 + ", _" + cr.getArgumento2());

            } else if (isInVariablesLocales(cr.getArgumento2()) != null) {
                Token vartoken1 = isInVariablesLocales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        lw $" + temp2 + ", -" + (offsetvarlocales + vartoken1.getOffset()) + "($fp)");

            } else if (isInParametros(cr.getArgumento2()) != null) {
                String s1 = buscarEnS(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + s1);

            } else if (cr.getArgumento2().matches("RET[0-9]+")) {
                isreg = true;
                if (buscarTemp(cr.getArgumento2()) != null) {
                    String t = buscarTemp(cr.getArgumento2());
                    lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + t);
                    setTempDisponible(t);
                    contadorcallfunction--;
                } else {
                    contadorcallfunction--;
                    lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $v0");
                }
            } else {
                String tem1 = buscarTemp(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + tem1);
                setTempDisponible(tem1);
            }
            if (!isreg) {
                lineas.add("        " + op + " $" + temp + ", $" + temp1 + ", $" + temp2);
            }
            setTempDisponible(temp1);
            setTempDisponible(temp2);
        }

        lineas.add("");

    }

    private void insertCondition(CuadruploRow cr, String oprel) {
        String etiq = cr.getRespuesta().substring(5);
        if (cr.getArgumento1().equals("true") || cr.getArgumento1().equals("false")) {
            boolean isreg = false;
            String temp1 = getTempDisponible();
            String bool = "0";
            if (cr.getArgumento1().toLowerCase().equals("true")) {
                bool = "1";
            }
            lineas.add("        li $" + temp1 + ", " + bool);
            temps[Integer.parseInt(temp1.substring(1))] = bool;
            String temp2 = getTempDisponible();
            if (cr.getArgumento2().equals("true") || cr.getArgumento2().equals("false")) {
                String bool1 = "0";
                if (cr.getArgumento2().toLowerCase().equals("true")) {
                    bool1 = "1";
                }
                temps[Integer.parseInt(temp2.substring(1))] = bool1;
                lineas.add("        li $" + temp2 + ", " + bool1);
            } else if (cr.getArgumento2().matches("[0-9]+")) {
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        li $" + temp2 + ", " + cr.getArgumento2());
            } else if (isInVariablesGlobales(cr.getArgumento2()) != -1) {
                int index = isInVariablesGlobales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                if (variablesglobales.get(index).getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp2 + ", _" + cr.getArgumento2());
                } else if (variablesglobales.get(index).getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp2 + ", _" + cr.getArgumento2());
                }
            } else if (isInVariablesLocales(cr.getArgumento2()) != null) {
                Token vartoken = isInVariablesLocales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                if (vartoken.getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp2 + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
                } else if (vartoken.getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp2 + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
                }

            } else if (isInParametros(cr.getArgumento2()) != null) {
                String s = buscarEnS(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + oprel + " $" + temp1 + ", $" + s + ", _" + etiq);

            } else if (cr.getArgumento2().matches("RET[0-9]+")) {
                isreg = true;
                if (buscarTemp(cr.getArgumento2()) != null) {
                    String t = buscarTemp(cr.getArgumento2());
                    lineas.add("        " + oprel + " $" + temp1 + ", $" + t + ", _" + etiq);
                    setTempDisponible(t);
                    contadorcallfunction--;
                } else {
                    contadorcallfunction--;
                    lineas.add("        " + oprel + " $" + temp1 + ", $v0, _" + etiq);
                }
            } else {
                String tem = buscarTemp(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + oprel + " $" + temp1 + ", $" + tem + ", _" + etiq);
                setTempDisponible(tem);
            }
            if (!isreg) {
                lineas.add("        " + oprel + " $" + temp1 + ", $" + temp2 + ", _" + etiq);
            }
            setTempDisponible(temp1);
            setTempDisponible(temp2);
        } else if (cr.getArgumento1().matches("[0-9]+")) {
            boolean isreg = false;
            String temp1 = getTempDisponible();
            lineas.add("        li $" + temp1 + ", " + cr.getArgumento1());
            temps[Integer.parseInt(temp1.substring(1))] = cr.getArgumento1();
            String temp2 = getTempDisponible();
            if (cr.getArgumento2().equals("true") || cr.getArgumento2().equals("false")) {
                String bool1 = "0";
                if (cr.getArgumento2().toLowerCase().equals("true")) {
                    bool1 = "1";
                }
                temps[Integer.parseInt(temp2.substring(1))] = bool1;
                lineas.add("        li $" + temp2 + ", " + bool1);
            } else if (cr.getArgumento2().matches("[0-9]+")) {
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        li $" + temp2 + ", " + cr.getArgumento2());
            } else if (isInVariablesGlobales(cr.getArgumento2()) != -1) {
                int index = isInVariablesGlobales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                if (variablesglobales.get(index).getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp2 + ", _" + cr.getArgumento2());
                } else if (variablesglobales.get(index).getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp2 + ", _" + cr.getArgumento2());
                }
            } else if (isInVariablesLocales(cr.getArgumento2()) != null) {
                Token vartoken = isInVariablesLocales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                if (vartoken.getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp2 + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
                } else if (vartoken.getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp2 + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
                }

            } else if (isInParametros(cr.getArgumento2()) != null) {
                String s = buscarEnS(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + oprel + " $" + temp1 + ", $" + s + ", _" + etiq);

            } else if (cr.getArgumento2().matches("RET[0-9]+")) {
                isreg = true;
                if (buscarTemp(cr.getArgumento2()) != null) {
                    String t = buscarTemp(cr.getArgumento2());
                    lineas.add("        " + oprel + " $" + temp1 + ", $" + t + ", _" + etiq);
                    setTempDisponible(t);
                    contadorcallfunction--;
                } else {
                    contadorcallfunction--;
                    lineas.add("        " + oprel + " $" + temp1 + ", $v0, _" + etiq);
                }
            } else {
                String tem = buscarTemp(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + oprel + " $" + temp1 + ", $" + tem + ", _" + etiq);
                setTempDisponible(tem);
            }
            if (!isreg) {
                lineas.add("        " + oprel + " $" + temp1 + ", $" + temp2 + ", _" + etiq);
            }
            setTempDisponible(temp1);
            setTempDisponible(temp2);
        } else if (isInVariablesGlobales(cr.getArgumento1()) != -1) {
            boolean isreg = false;
            int index = isInVariablesGlobales(cr.getArgumento1());
            String temp1 = getTempDisponible();
            lineas.add("        lw $" + temp1 + ", _" + variablesglobales.get(index).getId());
            temps[Integer.parseInt(temp1.substring(1))] = cr.getArgumento1();
            String temp2 = getTempDisponible();
            if (cr.getArgumento2().equals("true") || cr.getArgumento2().equals("false")) {
                String bool1 = "0";
                if (cr.getArgumento2().toLowerCase().equals("true")) {
                    bool1 = "1";
                }
                temps[Integer.parseInt(temp2.substring(1))] = bool1;
                lineas.add("        li $" + temp2 + ", " + bool1);
            } else if (cr.getArgumento2().matches("[0-9]+")) {
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        li $" + temp2 + ", " + cr.getArgumento2());
            } else if (isInVariablesGlobales(cr.getArgumento2()) != -1) {
                int index1 = isInVariablesGlobales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                if (variablesglobales.get(index1).getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp2 + ", _" + cr.getArgumento2());
                } else if (variablesglobales.get(index1).getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp2 + ", _" + cr.getArgumento2());
                }
            } else if (isInVariablesLocales(cr.getArgumento2()) != null) {
                Token vartoken = isInVariablesLocales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                if (vartoken.getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp2 + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
                } else if (vartoken.getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp2 + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
                }

            } else if (isInParametros(cr.getArgumento2()) != null) {
                String s = buscarEnS(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + oprel + " $" + temp1 + ", $" + s + ", _" + etiq);

            } else if (cr.getArgumento2().matches("RET[0-9]+")) {
                isreg = true;
                if (buscarTemp(cr.getArgumento2()) != null) {
                    String t = buscarTemp(cr.getArgumento2());
                    lineas.add("        " + oprel + " $" + temp1 + ", $" + t + ", _" + etiq);
                    setTempDisponible(t);
                    contadorcallfunction--;
                } else {
                    contadorcallfunction--;
                    lineas.add("        " + oprel + " $" + temp1 + ", $v0, _" + etiq);
                }
            } else {
                String tem = buscarTemp(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + oprel + " $" + temp1 + ", $" + tem + ", _" + etiq);
                setTempDisponible(tem);
            }
            if (!isreg) {
                lineas.add("        " + oprel + " $" + temp1 + ", $" + temp2 + ", _" + etiq);
            }
            setTempDisponible(temp1);
            setTempDisponible(temp2);
        } else if (isInVariablesLocales(cr.getArgumento1()) != null) {
            Token vartoken = isInVariablesLocales(cr.getArgumento1());
            boolean isreg = false;
            String temp1 = getTempDisponible();
            if (vartoken.getTipo().toString().equals("Integer")) {
                lineas.add("        lw $" + temp1 + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
            } else if (vartoken.getTipo().toString().equals("Boolean")) {
                lineas.add("        lb $" + temp1 + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
            }
            temps[Integer.parseInt(temp1.substring(1))] = cr.getArgumento1();
            String temp2 = getTempDisponible();
            if (cr.getArgumento2().equals("true") || cr.getArgumento2().equals("false")) {
                String bool1 = "0";
                if (cr.getArgumento2().toLowerCase().equals("true")) {
                    bool1 = "1";
                }
                temps[Integer.parseInt(temp2.substring(1))] = bool1;
                lineas.add("        li $" + temp2 + ", " + bool1);
            } else if (cr.getArgumento2().matches("[0-9]+")) {
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        li $" + temp2 + ", " + cr.getArgumento2());
            } else if (isInVariablesGlobales(cr.getArgumento2()) != -1) {
                int index = isInVariablesGlobales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                if (variablesglobales.get(index).getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp2 + ", _" + cr.getArgumento2());
                } else if (variablesglobales.get(index).getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp2 + ", _" + cr.getArgumento2());
                }

            } else if (isInVariablesLocales(cr.getArgumento2()) != null) {
                Token vartoken1 = isInVariablesLocales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                if (vartoken1.getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp2 + ", -" + (offsetvarlocales + vartoken1.getOffset()) + "($fp)");
                } else if (vartoken1.getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp2 + ", -" + (offsetvarlocales + vartoken1.getOffset()) + "($fp)");
                }

            } else if (isInParametros(cr.getArgumento2()) != null) {
                String s = buscarEnS(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + oprel + " $" + temp1 + ", $" + s + ", _" + etiq);

            } else if (cr.getArgumento2().matches("RET[0-9]+")) {
                isreg = true;
                if (buscarTemp(cr.getArgumento2()) != null) {
                    String t = buscarTemp(cr.getArgumento2());
                    lineas.add("        " + oprel + " $" + temp1 + ", $" + t + ", _" + etiq);
                    setTempDisponible(t);
                    contadorcallfunction--;
                } else {
                    contadorcallfunction--;
                    lineas.add("        " + oprel + " $" + temp1 + ", $v0, _" + etiq);
                }
            } else {
                String tem = buscarTemp(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + oprel + " $" + temp1 + ", $" + tem + ", _" + etiq);
                setTempDisponible(tem);
            }
            if (!isreg) {
                lineas.add("        " + oprel + " $" + temp1 + ", $" + temp2 + ", _" + etiq);
            }
            setTempDisponible(temp1);
            setTempDisponible(temp2);
        } else if (isInParametros(cr.getArgumento1()) != null) {
            String s = buscarEnS(cr.getArgumento1());
            boolean isreg = false;
            String temp2 = getTempDisponible();
            if (cr.getArgumento2().equals("true") || cr.getArgumento2().equals("false")) {
                String bool1 = "0";
                if (cr.getArgumento2().toLowerCase().equals("true")) {
                    bool1 = "1";
                }
                temps[Integer.parseInt(temp2.substring(1))] = bool1;
                lineas.add("        li $" + temp2 + ", " + bool1);
            } else if (cr.getArgumento2().matches("[0-9]+")) {
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        li $" + temp2 + ", " + cr.getArgumento2());
            } else if (isInVariablesGlobales(cr.getArgumento2()) != -1) {
                int index = isInVariablesGlobales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                if (variablesglobales.get(index).getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp2 + ", _" + cr.getArgumento2());
                } else if (variablesglobales.get(index).getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp2 + ", _" + cr.getArgumento2());
                }
            } else if (isInVariablesLocales(cr.getArgumento2()) != null) {
                Token vartoken1 = isInVariablesLocales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                if (vartoken1.getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp2 + ", -" + (offsetvarlocales + vartoken1.getOffset()) + "($fp)");
                } else if (vartoken1.getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp2 + ", -" + (offsetvarlocales + vartoken1.getOffset()) + "($fp)");
                }

            } else if (isInParametros(cr.getArgumento2()) != null) {
                String s1 = buscarEnS(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + oprel + " $" + s + ", $" + s1 + ", _" + etiq);

            } else if (cr.getArgumento2().matches("RET[0-9]+")) {
                isreg = true;
                if (buscarTemp(cr.getArgumento2()) != null) {
                    String t = buscarTemp(cr.getArgumento2());
                    lineas.add("        " + oprel + " $" + s + ", $" + t + ", _" + etiq);
                    setTempDisponible(t);
                    contadorcallfunction--;
                } else {
                    contadorcallfunction--;
                    lineas.add("        " + oprel + " $" + s + ", $v0, _" + etiq);
                }
            } else {
                String tem = buscarTemp(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + oprel + " $" + s + ", $" + tem + ", _" + etiq);
                setTempDisponible(tem);
            }
            if (!isreg) {
                lineas.add("        " + oprel + " $" + s + ", $" + temp2 + ", _" + etiq);
            }
            setTempDisponible(temp2);
        } else if (cr.getArgumento1().matches("RET[0-9]+")) {
            String re = "v0";
            if (buscarTemp(cr.getArgumento1()) != null) {
                re = buscarTemp(cr.getArgumento1());
            }
            contadorcallfunction--;
            boolean isreg = false;
            String temp2 = getTempDisponible();
            if (cr.getArgumento2().equals("true") || cr.getArgumento2().equals("false")) {
                String bool1 = "0";
                if (cr.getArgumento2().toLowerCase().equals("true")) {
                    bool1 = "1";
                }
                temps[Integer.parseInt(temp2.substring(1))] = bool1;
                lineas.add("        li $" + temp2 + ", " + bool1);
            } else if (cr.getArgumento2().matches("[0-9]+")) {
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        li $" + temp2 + ", " + cr.getArgumento2());
            } else if (isInVariablesGlobales(cr.getArgumento2()) != -1) {
                int index = isInVariablesGlobales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                if (variablesglobales.get(index).getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp2 + ", _" + cr.getArgumento2());
                } else if (variablesglobales.get(index).getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp2 + ", _" + cr.getArgumento2());
                }
            } else if (isInVariablesLocales(cr.getArgumento2()) != null) {
                Token vartoken1 = isInVariablesLocales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                if (vartoken1.getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp2 + ", -" + (offsetvarlocales + vartoken1.getOffset()) + "($fp)");
                } else if (vartoken1.getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp2 + ", -" + (offsetvarlocales + vartoken1.getOffset()) + "($fp)");
                }

            } else if (isInParametros(cr.getArgumento2()) != null) {
                String s1 = buscarEnS(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + oprel + " $" + re + ", $" + s1 + ", _" + etiq);

            } else if (cr.getArgumento2().matches("RET[0-9]+")) {
                isreg = true;
                if (buscarTemp(cr.getArgumento2()) != null) {
                    String t = buscarTemp(cr.getArgumento2());
                    lineas.add("        " + oprel + " $" + re + ", $" + t + ", _" + etiq);
                    setTempDisponible(t);
                    contadorcallfunction--;
                } else {
                    contadorcallfunction--;
                    lineas.add("        " + oprel + " $" + re + ", $v0, _" + etiq);
                }
            } else {
                String tem = buscarTemp(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + oprel + " $" + re+ ", $" + tem + ", _" + etiq);
                setTempDisponible(tem);
            }
            if (!isreg) {
                lineas.add("        " + oprel + " $" + re + ", $" + temp2 + ", _" + etiq);
            }
            setTempDisponible(temp2);
            if (buscarTemp(cr.getArgumento1()) != null) {
                setTempDisponible(re);
            }
        } else {
            String temp1 = buscarTemp(cr.getArgumento1());
            boolean isreg = false;
            String temp2 = getTempDisponible();
            if (cr.getArgumento2().equals("true") || cr.getArgumento2().equals("false")) {
                String bool1 = "0";
                if (cr.getArgumento2().toLowerCase().equals("true")) {
                    bool1 = "1";
                }

                temps[Integer.parseInt(temp2.substring(1))] = bool1;
                lineas.add("        li $" + temp2 + ", " + bool1);
            } else if (cr.getArgumento2().matches("[0-9]+")) {
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                lineas.add("        li $" + temp2 + ", " + cr.getArgumento2());
            } else if (isInVariablesGlobales(cr.getArgumento2()) != -1) {
                int index = isInVariablesGlobales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                if (variablesglobales.get(index).getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp2 + ", _" + cr.getArgumento2());
                } else if (variablesglobales.get(index).getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp2 + ", _" + cr.getArgumento2());
                }

            } else if (isInVariablesLocales(cr.getArgumento2()) != null) {
                Token vartoken1 = isInVariablesLocales(cr.getArgumento2());
                temps[Integer.parseInt(temp2.substring(1))] = cr.getArgumento2();
                if (vartoken1.getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp2 + ", -" + (offsetvarlocales + vartoken1.getOffset()) + "($fp)");
                } else if (vartoken1.getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp2 + ", -" + (offsetvarlocales + vartoken1.getOffset()) + "($fp)");
                }

            } else if (buscarEnS(cr.getArgumento2()) != null) {
                String s1 = buscarEnS(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + oprel + " $" + temp1 + ", $" + s1 + ", _" + etiq);

            } else {
                String tem1 = buscarTemp(cr.getArgumento2());
                isreg = true;
                lineas.add("        " + oprel + " $" + temp1 + ", $" + tem1 + ", _" + etiq);
                setTempDisponible(tem1);
            }
            if (!isreg) {
                lineas.add("        " + oprel + " $" + temp1 + ", $" + temp2 + ", _" + etiq);
            }
            setTempDisponible(temp1);
            setTempDisponible(temp2);
        }

    }

    private void insertParam(CuadruploRow cr) {
        String a = getADisponible();
        if (cr.getArgumento1().matches("[0-9]+")) {
            lineas.add("        li $" + a + ", " + cr.getArgumento1());
            reg_a[Integer.parseInt(a.substring(1))] = cr.getArgumento1();
        } else if (cr.getArgumento1().equals("true") || cr.getArgumento1().equals("false")) {
            String bool = "0";
            if (cr.getArgumento1().equals("true")) {
                bool = "1";
            }
            lineas.add("        li $" + a + ", " + bool);
            reg_a[Integer.parseInt(a.substring(1))] = bool;
        } else if (isInVariablesGlobales(cr.getArgumento1()) != -1) {
            int index = isInVariablesGlobales(cr.getArgumento1());
            if (variablesglobales.get(index).getTipo().toString().equals("Integer")) {
                lineas.add("        lw $" + a + ", _" + cr.getArgumento1());
            } else if (variablesglobales.get(index).getTipo().toString().equals("Boolean")) {
                lineas.add("        lb $" + a + ", _" + cr.getArgumento1());
            }
            reg_a[Integer.parseInt(a.substring(1))] = cr.getArgumento1();
        } else if (isInVariablesLocales(cr.getArgumento1()) != null) {
            Token vartoken = isInVariablesLocales(cr.getArgumento1());
            if (vartoken.getTipo().toString().equals("Integer")) {
                lineas.add("        lw $" + a + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
            } else if (vartoken.getTipo().toString().equals("Boolean")) {
                lineas.add("        lb $" + a + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
            }
            reg_a[Integer.parseInt(a.substring(1))] = cr.getArgumento1();
        } else if (buscarEnS(cr.getArgumento1()) != null) {
            String s = buscarEnS(cr.getArgumento1());
            lineas.add("        move $" + a + ", $" + s);
        } else {
            String temp = buscarTemp(cr.getArgumento1());
            lineas.add("        move $" + a + ", $" + temp);
            setTempDisponible(temp);
        }
    }

    private Token isInParametros(String var) {
        return tds.get_offsert_var_parametros(funcionactual, var);
    }

    private Token isInVariablesLocales(String var) {
        return tds.get_offsert_var_locales(funcionactual, var);
    }

    private int isInVariablesGlobales(String var) {
        int index = -1;
        for (int i = 0; i < variablesglobales.size(); i++) {
            if (variablesglobales.get(i).getId().equals(var)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public ArrayList<String> Generar() {

        generarData();
        generarText();
        return lineas;
    }

    private String getTempDisponible() {
        String dis = null;
        int index = -1;
        for (int i = 0; i < temps.length; i++) {
            if (temps[i].equals("")) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            dis = "t" + index;
        }
        return dis;
    }

    private String buscarTemp(String te) {
        String dis = null;
        int index = -1;
        for (int i = 0; i < temps.length; i++) {
            if (temps[i].equals(te)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            dis = "t" + index;
        }
        return dis;
    }

    private void setTempDisponible(String temp) {
        temps[Integer.parseInt(temp.substring(1))] = "";
    }

    private String buscarEnS(String parametro) {
        String dis = null;
        int index = -1;
        for (int i = 0; i < reg_s.length; i++) {
            if (reg_s[i].equals(parametro)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            dis = "s" + index;
        }
        return dis;
    }

    private String getADisponible() {
        String dis = null;
        int index = -1;
        for (int i = 0; i < reg_a.length; i++) {
            if (reg_a[i].equals("")) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            dis = "a" + index;
        }
        return dis;
    }

    private void setADisponible(String a) {
        reg_a[Integer.parseInt(a.substring(1))] = "";
    }

}
