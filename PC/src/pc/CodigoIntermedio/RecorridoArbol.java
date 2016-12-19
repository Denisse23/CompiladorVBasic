/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.CodigoIntermedio;

import pc.tabla.TablasDeSimbolos;
import pc.treeelements.Node;
import pc.treeelements.*;

/**
 *
 * @author Denisse
 */
public class RecorridoArbol {

    Cuadruplos cuadruplos;
    TablasDeSimbolos tds;

    public RecorridoArbol(TablasDeSimbolos tds) {
        this.tds = tds;
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
                    hijo.siguiente = ast.siguiente;
                    insertReturn(hijo);
                }

                if (hijo instanceof Stmt_If) {

                    hijo.siguiente = cuadruplos.etiquetaNueva();
                    insertIf(hijo);
                    cuadruplos.generarCuadruplo("ETIQUE", hijo.siguiente, "", "");
                }

                if (hijo instanceof Stmt_For) {
                    hijo.siguiente = cuadruplos.etiquetaNueva();
                    insertFor(hijo);
                    cuadruplos.generarCuadruplo("ETIQUE", hijo.siguiente, "", "");
                }

                if (hijo instanceof Stmt_While) {
                    hijo.siguiente = cuadruplos.etiquetaNueva();
                    insertWhile(hijo);
                    cuadruplos.generarCuadruplo("ETIQUE", hijo.siguiente, "", "");
                }

                if (hijo instanceof Stmt_Llamada_Funcion) {
                    insertLlamada(hijo);
                }

                if (hijo instanceof Stmt_Lectura) {
                    insertLectura(hijo);
                }

                if (hijo instanceof Stmt_Escritura) {
                    insertEscritura(hijo);
                }
            }
        }

    }

    public void insertProcedimiento(Node n) {
        cuadruplos.generarCuadruplo("ETIQUEFUNC", "ETIQUE" + "FUNC" + ((Stmt_Procedimiento) n).getIdentificador().getVal(), "", "");
        PreOrden(((Stmt_Procedimiento) n).getBody());
        cuadruplos.generarCuadruplo("FINETIQUEFUNC", "ETIQUE" + "FUNC" + ((Stmt_Procedimiento) n).getIdentificador().getVal(), "", "");
    }

    public void insertFuncion(Node n) {
        cuadruplos.generarCuadruplo("ETIQUEFUNC", "ETIQUE" + "FUNC" + ((Stmt_Funcion) n).getIdentificador().getVal(), "", "");
        PreOrden(((Stmt_Funcion) n).getBody());
        cuadruplos.generarCuadruplo("FINETIQUEFUNC", "ETIQUE" + "FUNC" + ((Stmt_Funcion) n).getIdentificador().getVal(), "", "");
    }

    public void insertReturn(Node n) {
        String t = "";
        if (!((Stmt_Return) n).getExp().isLeaf()) {
            t = insertExp(((Stmt_Return) n).getExp());
        } else {
            if(!((Stmt_Return) n).getExp().getVal().contains(".")){
               t = ((Stmt_Return) n).getExp().getVal();
            }else{
                int place =tds.get_offset_type(((Stmt_Return) n).getExp().getVal());
                t = ((Stmt_Return) n).getExp().getVal().split("\\.")[0]+"["+place+"]";
            }
          
        }
        cuadruplos.generarCuadruplo("RET", t, "", "");
        if(!n.siguiente.equals(""))
            cuadruplos.generarCuadruplo("GOTO", n.siguiente, "", "");
    }

    public void insertAsignacion(Node n) {
        String t = insertExp(((Stmt_Asignacion) n).getExp());
        if(!((Stmt_Asignacion) n).getIdentificador().getVal().contains(".")){
           cuadruplos.generarCuadruplo("=", t, "", ((Stmt_Asignacion) n).getIdentificador().getVal());
        }else{
            int place =tds.get_offset_type(((Stmt_Asignacion) n).getIdentificador().getVal());
            cuadruplos.generarCuadruplo("=", t, "", ((Stmt_Asignacion) n).getIdentificador().getVal().split("\\.")[0]+"["+place+"]");
        }
    }
    public String insertExp(Node n) {
        
        String t = "";
        String t1 = "";
        String t2 = "";
        if (n.isLeaf()) {
            t = cuadruplos.temporalNuevo();
            if(!n.getVal().contains(".")){
                cuadruplos.generarCuadruplo("=", n.getVal(), "", t);
            }else{
                 int place =tds.get_offset_type(n.getVal());
                cuadruplos.generarCuadruplo("=", n.getVal().split("\\.")[0]+"["+place+"]", "", t);
            }
        } else if(n instanceof Stmt_Llamada_Funcion){
            t = insertLlamada(n);
        }else {
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
            if (!((Stmt_Argumento) ar).getExp().isLeaf()) {
                t = insertExp(((Stmt_Argumento) ar).getExp());
                cuadruplos.generarCuadruplo("PARAM", t, "", "");
            } else {
                if(!((Stmt_Argumento) ar).getExp().getVal().contains(".")){
                    cuadruplos.generarCuadruplo("PARAM", ((Stmt_Argumento) ar).getExp().getVal(), "", "");
                }else{
                    int place =tds.get_offset_type(((Stmt_Argumento) ar).getExp().getVal());
                    cuadruplos.generarCuadruplo("PARAM", ((Stmt_Argumento) ar).getExp().getVal().split("\\.")[0]+"["+place+"]", "", "");
                }
            }
            cont++;
        }
        cuadruplos.generarCuadruplo("CALL", ((Stmt_Llamada_Funcion) n).getIdentificador().getVal(), cont + "", "");
        
    return "RET"+(cuadruplos.getRows().size()-1);
    }

    public String AscendenteExp(Node n, String t1, String t2) {
        if (t1.equals("")) {
            if(!((Exp) n).getLeft().getVal().contains(".")){
                t1 = ((Exp) n).getLeft().getVal();
            }else{
                 int place =tds.get_offset_type(((Exp) n).getLeft().getVal());
                 t1 = ((Exp) n).getLeft().getVal().split("\\.")[0]+"["+place+"]";
            } 
        }
        if (t2.equals("")) {
            if(!((Exp) n).getRight().getVal().contains(".")){
                t2 = ((Exp) n).getRight().getVal();
             }else{
                 int place =tds.get_offset_type(((Exp) n).getRight().getVal());
                 t2 = ((Exp) n).getRight().getVal().split("\\.")[0]+"["+place+"]";
            } 
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
        }

    }

    public void insertFor(Node n) {
        String t = insertExp(((Stmt_Asignacion_For) (((Stmt_For) n).getAsignacion())).getExp());
        String identificador =  ((Stmt_Asignacion_For) ((Stmt_For) n).getAsignacion()).getIdentificadorOVar().getVal();
        if(identificador.contains(".")){
            int place = tds.get_offset_type(identificador);
            identificador = identificador.split("\\.")[0]+"["+place+"]";
        }
        cuadruplos.generarCuadruplo("=", t, "", identificador);
        ((Stmt_For) n).comienzo = cuadruplos.etiquetaNueva();
        cuadruplos.generarCuadruplo("ETIQUE", ((Stmt_For) n).comienzo, "", "");
        String et = cuadruplos.etiquetaNueva();
        cuadruplos.generarCuadruplo("IF<=",  identificador, ((Stmt_For) n).getExpTo().getVal(), "GOTO " + et);
        cuadruplos.generarCuadruplo("GOTO", n.siguiente, "", "");
        cuadruplos.generarCuadruplo("ETIQUE", et, "", "");
        ((Stmt_For) n).getBody().siguiente = cuadruplos.etiquetaNueva();
        PreOrden(((Stmt_For) n).getBody());
        cuadruplos.generarCuadruplo("ETIQUE", ((Stmt_For) n).getBody().siguiente, "", "");
        cuadruplos.generarCuadruplo("+",identificador, "1", cuadruplos.temporalNuevo());
        cuadruplos.generarCuadruplo("=", cuadruplos.getLastTemp(), "", identificador);
        cuadruplos.generarCuadruplo("GOTO", n.comienzo, "", "");

    }

    public void insertWhile(Node n) {
        n.comienzo = cuadruplos.etiquetaNueva();
        cuadruplos.generarCuadruplo("ETIQUE", n.comienzo, "", "");
        ((Stmt_While) n).getCondition().verdadera = cuadruplos.etiquetaNueva();
        ((Stmt_While) n).getCondition().falsa = n.siguiente;

        insertExpCondicional(((Stmt_While) n).getCondition());
        cuadruplos.generarCuadruplo("ETIQUE", ((Stmt_While) n).getCondition().verdadera, "", "");
        ((Stmt_While) n).getBody().siguiente = n.comienzo;
        PreOrden(((Stmt_While) n).getBody());
        cuadruplos.generarCuadruplo("GOTO", n.comienzo, "", "");
    }

    public void insertLectura(Node n) {
        if(!((Stmt_Lectura) n).getIdentificador().getVal().contains(".")){
            cuadruplos.generarCuadruplo("READ", ((Stmt_Lectura) n).getIdentificador().getVal(), "", "");
        }else{
           int place =tds.get_offset_type(((Stmt_Lectura) n).getIdentificador().getVal());
            cuadruplos.generarCuadruplo("READ", ((Stmt_Lectura) n).getIdentificador().getVal().split("\\.")[0]+"["+place+"]", "", "");
        }
    }

    public void insertEscritura(Node n) {
        if (!((Stmt_Escritura) n).getExp().isLeaf()) {
            String t = insertExp((Exp)((Stmt_Escritura) n).getExp());
            cuadruplos.generarCuadruplo("PRINT", t, "", "");
        } else {
             if(!((Stmt_Escritura) n).getExp().getVal().contains(".")){
                 cuadruplos.generarCuadruplo("PRINT", ((Stmt_Escritura) n).getExp().getVal(), "", "");
             }else{
                 int place =tds.get_offset_type(((Stmt_Escritura) n).getExp().getVal());
                 cuadruplos.generarCuadruplo("PRINT", ((Stmt_Escritura) n).getExp().getVal().split("\\.")[0]+"["+place+"]", "", "");
             }
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
        String t1 = "";
        String t2 = "";
        if (((Condition) n).getLeft().isLeaf()) {
            if(!((Condition) n).getLeft().getVal().contains(".")){
                t1 = ((Condition) n).getLeft().getVal();
            }else{
                int place = tds.get_offset_type(((Condition) n).getLeft().getVal());
                t1 = ((Condition) n).getLeft().getVal().split("\\.")[0]+"["+place+"]";
            }
        } else {
            t1 = insertExp(((Condition) n).getLeft());
        }
        if (((Condition) n).getRight().isLeaf()) {
            if(!((Condition) n).getRight().getVal().contains(".")){
                t2 = ((Condition) n).getRight().getVal();
            }else{
                int place = tds.get_offset_type(((Condition) n).getRight().getVal());
                t2 = ((Condition) n).getRight().getVal().split("\\.")[0]+"["+place+"]";
            }
        } else {
            t2 = insertExp(((Condition) n).getRight());
        }
        String etiquv = ((Condition) n).verdadera;
        String etiquf = ((Condition) n).falsa;
        if (((Condition) n).getLeft().getVal().toLowerCase().equals("not")) {
            Node te = ((Condition) n).getLeft();
            if(!((Not) te).getElement().getVal().contains(".")){
                t1 = ((Not) te).getElement().getVal();
            }else{
                int place = tds.get_offset_type(((Not) te).getElement().getVal());
                t1 = ((Not) te).getElement().getVal().split("\\.")[0]+"["+place+"]";
            }
            etiquv = ((Condition) n).falsa;
            etiquf = ((Condition) n).verdadera;

        } else if (((Condition) n).getRight().getVal().toLowerCase().equals("not")) {
            Node te = ((Condition) n).getRight();
            if(!((Not) te).getElement().getVal().contains(".")){
                t2 = ((Not) te).getElement().getVal();
            }else{
                int place = tds.get_offset_type(((Not) te).getElement().getVal());
                t2 = ((Not) te).getElement().getVal().split("\\.")[0]+"["+place+"]";
            }
            etiquv = ((Condition) n).falsa;
            etiquf = ((Condition) n).verdadera;
        }
        cuadruplos.generarCuadruplo("IF" + ((Condition) n).getVal(), t1, t2, "GOTO " + etiquv);
        cuadruplos.generarCuadruplo("GOTO", etiquf, "", "");

    }

    public Cuadruplos getCuadruplos() {
        return cuadruplos;
    }
}
