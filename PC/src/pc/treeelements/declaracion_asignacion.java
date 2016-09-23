/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.treeelements;

/**
 *
 * @author Denisse
 */
public class declaracion_asignacion extends declaracion{
    public identificador identificador;
    public String op;
    exp exp;
    public declaracion_asignacion(identificador id, String o, exp e ){
        identificador = id;
        exp =e;
        op =o;
    }
    @Override
    public String toString(int nivel) {
       String tabs = "";
       for(int i =0; i<nivel;i++){
           tabs +=  "\t";
       }
       String re = "\n"+tabs+"declaracion_aisgnacion"+identificador.toString(nivel+1);
       re+= "\n"+tabs+"\t"+op+exp.toString(nivel + 1);
       return re;
    }
}
