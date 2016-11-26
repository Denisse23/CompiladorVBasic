/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.CodigoIntermedio;

import pc.treeelements.Node;
import pc.treeelements.*;

/**
 *
 * @author Denisse
 */
public class RecorridoArbol {

    Cuadruplos cuadruplos;

    public RecorridoArbol() {
        cuadruplos = new Cuadruplos();
    }

    public void PreOrden(Node ast) {
        for (Node hijo : ast.getHijos()) {
            if (!hijo.isLeaf()) {

                if (hijo instanceof Stmt_Procedimiento) {
                    insertProcedimiento(hijo);
                }

                if (hijo instanceof Stmt_Funcion) {
                    insertFuncion(hijo);
                }

                if (hijo instanceof Stmt_Asignacion) {
                    insertAsignacion(hijo);
                }

                if (hijo instanceof Stmt_Return) {
                    insertReturn(hijo);
                }

                if (hijo instanceof Stmt_If) {
                    ((Stmt_If) hijo).siguiente = cuadruplos.etiquetaNueva();
                    insertIf(hijo);
                }
            }
        }
    }

    public void insertProcedimiento(Node n) {
        cuadruplos.generarCuadruplo("ETIQUEFUNC", "ETIQUE" + "FUNC" + ((Stmt_Procedimiento) n).getIdentificador().getVal(), "", "");
        PreOrden(((Stmt_Procedimiento) n).getBody());
    }

    public void insertFuncion(Node n) {
        cuadruplos.generarCuadruplo("ETIQUEFUNC", "ETIQUE" + "FUNC" + ((Stmt_Funcion) n).getIdentificador().getVal(), "", "");
        PreOrden(((Stmt_Funcion) n).getBody());
    }

    public void insertReturn(Node n) {
        String t = "";
        if (!((Stmt_Return) n).getExp().isLeaf()) {
            t = insertExp(((Stmt_Return) n).getExp());
        } else {
            t = ((Stmt_Return) n).getExp().getVal();
        }
        cuadruplos.generarCuadruplo("RET", "", "", t);
    }

    public void insertAsignacion(Node n) {
        String t = insertExp(((Stmt_Asignacion) n).getExp());
        cuadruplos.generarCuadruplo("=", t, "", ((Stmt_Asignacion) n).getIdentificador().getVal());
    }

    public String insertExp(Node n) {
        String t = "";
        String t1 = "";
        String t2 = "";
        if (n.isLeaf()) {
            t = cuadruplos.temporalNuevo();
            cuadruplos.generarCuadruplo("=", n.getVal(), "", t);
        } else {
            if (!((Exp) n).getLeft().isLeaf()) {
                if (((Exp) n).getLeft() instanceof Stmt_Llamada_Funcion) {
                    t1 = insertLlamada(((Exp) n).getLeft());
                } else {
                    t1 = insertExp(((Exp) n).getLeft());
                }
            }

            if (!((Exp) n).getRight().isLeaf()) {
                if (((Exp) n).getRight() instanceof Stmt_Llamada_Funcion) {
                    t2 = insertLlamada(((Exp) n).getRight());
                } else {
                    t2 = insertExp(((Exp) n).getRight());
                }
            }

            t = AscendenteExp(n, t1, t2);
        }
        return t;
    }

    public String insertLlamada(Node n) {
        int cont = 0;
        String t = "";
        for (Node ar : ((Stmt_Llamada_Funcion) n).getArguments().getHijos()) {
            t = insertExp(((Stmt_Argumento) ar).getExp());
            cuadruplos.generarCuadruplo("PARAM", "", "", t);
            cont++;
        }
        cuadruplos.generarCuadruplo("CALL", ((Stmt_Llamada_Funcion) n).getIdentificador().getVal(), cont + "", "");
        return "RET";
    }

    public String AscendenteExp(Node n, String t1, String t2) {
        if (t1.equals("")) {
            t1 = ((Exp) n).getLeft().getVal();
        }
        if (t2.equals("")) {
            t2 = ((Exp) n).getRight().getVal();
        }
        String temp = cuadruplos.temporalNuevo();
        cuadruplos.generarCuadruplo(n.getVal(), t1, t2, temp);
        return temp;
    }

    public void insertIf(Node n) {
        ((Stmt_If) n).getCondition().verdadera = cuadruplos.etiquetaNueva();
        if (!((Stmt_If) n).getElseBody().isLeaf()) {
            ((Stmt_If) n).getCondition().falsa = cuadruplos.etiquetaNueva();
        } else {
            ((Stmt_If) n).getCondition().falsa = n.siguiente;
        }
        insertExpCondicional(((Stmt_If) n).getCondition());
        cuadruplos.generarCuadruplo("ETIQUE", ((Stmt_If) n).getCondition().verdadera, "", "");
        ((Stmt_If) n).getBody().siguiente = n.siguiente;
        PreOrden(((Stmt_If) n).getBody());
        if (!((Stmt_If) n).getElseBody().isLeaf()) {
            cuadruplos.generarCuadruplo("GOTO", n.siguiente, "", "");
            cuadruplos.generarCuadruplo("ETIQUE", ((Stmt_If) n).getCondition().falsa, "", "");
            ((Stmt_If) n).getElseBody().siguiente = n.siguiente;
            PreOrden(((Stmt_If) n).getElseBody());
            cuadruplos.generarCuadruplo("ETIQUE", ((Stmt_If) n).getElseBody().siguiente, "", "");
        }
    }

    public void insertExpCondicional(Node n) {
        if (((Condition) n).getVal().toLowerCase().equals("not")) {
            ((Not) n).getElement().falsa = ((Condition) n).verdadera;
            ((Not) n).getElement().verdadera = ((Condition) n).falsa;
            insertExpCondicional(((Not) n).getElement());
        } else if (((Condition) n).getVal().toLowerCase().equals("and")) {
            ((Condition) n).getLeft().verdadera = cuadruplos.etiquetaNueva();
            ((Condition) n).getLeft().falsa = ((Condition) n).falsa;
            insertExpCondicional(((Condition) n).getLeft());
            cuadruplos.generarCuadruplo("ETIQUE", ((Condition) n).getLeft().verdadera, "", "");
            ((Condition) n).getRight().verdadera = ((Condition) n).verdadera;
            ((Condition) n).getRight().falsa = ((Condition) n).falsa;
            insertExpCondicional(((Condition) n).getRight());
        } else if (((Condition) n).getVal().toLowerCase().equals("or")) {
            ((Condition) n).getLeft().verdadera = ((Condition) n).verdadera;
            ((Condition) n).getLeft().falsa = cuadruplos.etiquetaNueva();
            insertExpCondicional(((Condition) n).getLeft());
            cuadruplos.generarCuadruplo("ETIQUE", ((Condition) n).getLeft().falsa, "", "");
            ((Condition) n).getRight().verdadera = ((Condition) n).verdadera;
            ((Condition) n).getRight().falsa = ((Condition) n).falsa;
            insertExpCondicional(((Condition) n).getRight());
        } else {
            AscendenteExpCondicional(n);
        }
    }

    public void AscendenteExpCondicional(Node n) {
        /*
        if (n.isLeaf()) {
            if(n.getVal().toLowerCase().equals("true")){
                cuadruplos.generarCuadruplo("GOTO", n.verdadera, "", "");
            }else{
                cuadruplos.generarCuadruplo("GOTO", n.falsa, "", "");
            }
        } else {
        */
            String t1 = "";
            String t2 = "";
            if (((Condition) n).getLeft().isLeaf()) {
                t1 = ((Condition) n).getLeft().getVal();
            } else {
                t1 = insertExp(((Condition) n).getLeft());
            }
            if (((Condition) n).getRight().isLeaf()) {
                t2 = ((Condition) n).getRight().getVal();
            } else {
                t2 = insertExp(((Condition) n).getRight());
            }
            String etiquv = ((Condition) n).verdadera;
            String etiquf = ((Condition) n).falsa;
            if (((Condition) n).getLeft().getVal().toLowerCase().equals("not")) {
                Node te = ((Condition) n).getLeft();
                t1 = ((Not) te).getElement().getVal();
                etiquv = ((Condition) n).falsa;
                etiquf = ((Condition) n).verdadera;

            } else if (((Condition) n).getRight().getVal().toLowerCase().equals("not")) {
                Node te = ((Condition) n).getRight();
                t2 = ((Not) te).getElement().getVal();
                etiquv = ((Condition) n).falsa;
                etiquf = ((Condition) n).verdadera;
            }
            cuadruplos.generarCuadruplo("IF" + ((Condition) n).getVal(), t1, t2, "GOTO " + etiquv);
            cuadruplos.generarCuadruplo("GOTO", etiquf, "", "");
        //}

    }

    public Cuadruplos getCuadruplos() {
        return cuadruplos;
    }
}
