/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.codigofinal;

import java.io.File;
import java.util.ArrayList;
import pc.CodigoIntermedio.CuadruploRow;
import pc.CodigoIntermedio.Cuadruplos;
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
        while (numlineasigfun < cuadruplos.getRows().size()) {
            numlineasigfun = cuadruplos.get_num_linea_sig_func(numlineasigfun);
            String name = cuadruplos.getRows().get(numlineasigfun).getArgumento1();
            funcionactual = cuadruplos.getRows().get(numlineasigfun).getArgumento1().substring(10);
            if (cuadruplos.getRows().get(numlineasigfun).getArgumento1().toUpperCase().equals("ETIQUEFUNCMAIN")) {
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
                }
                lineas.add("        li $v0, 10");
                lineas.add("        syscall");
                numlineasigfun = cuadruplos.get_num_linea_fin_func(name) + 1;
            } else {

            }

        }
    }

    private void insertVaribalesLocales() {
        ArrayList<Token> varlocales = tds.get_variables_locales(funcionactual);
        if (varlocales.size() > 0) {
            int offset = offsetvarlocales + varlocales.get(varlocales.size() - 1).getOffset() + varlocales.get(varlocales.size() - 1).getTipo().getTamano();
            while (offset % 4 != 0) {
                offset++;
            }
            lineas.add("        sub $sp, $sp, " + offset);
            lineas.add("");
        }
    }

    private void insertAsignacion(CuadruploRow cr) {
        if (isInVariablesGlobales(cr.getResputa()) != -1) {
            int index = isInVariablesGlobales(cr.getResputa());
            String forma = "sw";
            if (variablesglobales.get(index).getTipo().toString().equals("Boolean")) {
                forma = "sb";
            }
            if (buscarTemp(cr.getArgumento1()) != null) {
                String temp = buscarTemp(cr.getArgumento1());
                lineas.add("        " + forma + " $" + temp + ", _" + variablesglobales.get(index).getId());
                setTempDisponible(temp);
                lineas.add("");
            } else if (cr.getArgumento1().equals("RET")) {
                lineas.add("        " + forma + " $v0, _" + variablesglobales.get(index).getId());
                lineas.add("");
            } else if (buscarEnS(cr.getArgumento1()) != null) {
                String s = buscarEnS(cr.getArgumento1());
                lineas.add("        " + forma + " $" + s + ", _" + variablesglobales.get(index).getId());
                lineas.add("");
            }
        } else if (isInVariablesLocales(cr.getResputa()) != null) {
            Token vartoken = isInVariablesLocales(cr.getResputa());
            String forma = "sw";
            if (vartoken.getTipo().toString().equals("Boolean")) {
                forma = "sb";
            }
            if (buscarTemp(cr.getArgumento1()) != null) {
                String temp = buscarTemp(cr.getArgumento1());
                lineas.add("        " + forma + " $" + temp + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
                setTempDisponible(temp);
                lineas.add("");
            } else if (cr.getArgumento1().equals("RET")) {
                lineas.add("        " + forma + " $v0, -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
                lineas.add("");
            } else if (buscarEnS(cr.getArgumento1()) != null) {
                String s = buscarEnS(cr.getArgumento1());
                lineas.add("        " + forma + " $" + s + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
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
                temps[Integer.parseInt(temp.substring(1))] = cr.getResputa();
            } else if (isInVariablesLocales(cr.getArgumento1()) != null) {
                Token vartoken = isInVariablesLocales(cr.getArgumento1());
                if (vartoken.getTipo().toString().equals("Integer")) {
                    lineas.add("        lw $" + temp + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
                } else if (vartoken.getTipo().toString().equals("Boolean")) {
                    lineas.add("        lb $" + temp + ", -" + (offsetvarlocales + vartoken.getOffset()) + "($fp)");
                }
                temps[Integer.parseInt(temp.substring(1))] = cr.getResputa();
            } else if (buscarEnS(cr.getArgumento1()) != null) {
                String s = buscarEnS(cr.getArgumento1());
                lineas.add("        move $" + temp + ", $" + s);
            } else if (cr.getArgumento1().matches("[0-9]+")) {
                lineas.add("        li $" + temp + ", " + cr.getArgumento1());
                temps[Integer.parseInt(temp.substring(1))] = cr.getResputa();
            } else if (cr.getArgumento1().toLowerCase().equals("true") || cr.getArgumento1().toLowerCase().equals("false")) {
                String bool = "0";
                if (cr.getArgumento1().equals("true")) {
                    bool = "1";
                }
                lineas.add("        li $" + temp + ", " + bool);
                temps[Integer.parseInt(temp.substring(1))] = cr.getResputa();
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
            if (funcionactual.toLowerCase().equals("main")) {
                int offset = offsetvarlocales + vartoken.getOffset();
                if (vartoken.getTipo().toString().equals("Integer")) {
                    lineas.add("        li $v0, 1");
                    lineas.add("        lw $a0, -" + offset + "($fp)");
                    lineas.add("        syscall");
                } else if (vartoken.getTipo().toString().equals("Boolean")) {
                    lineas.add("        jal readboolean");
                    lineas.add("        sb $v0, -" + offset + "($fp)");
                }

                lineas.add("");
            }
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
        } else if (cr.getArgumento1().equals("RET")) {
            String temp = getTempDisponible();
            lineas.add("        move $" + temp + ", $v0");
            lineas.add("        li $v0, 1");
            lineas.add("        move $a0, $" + temp);
            lineas.add("        syscall");
            lineas.add("");
        } else if (buscarEnS(cr.getArgumento1()) != null) {
            String s = buscarEnS(cr.getArgumento1());
            lineas.add("        li $v0, 1");
            lineas.add("        move $a0, $" + s);
            lineas.add("        syscall");
            lineas.add("");
        }
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

}
