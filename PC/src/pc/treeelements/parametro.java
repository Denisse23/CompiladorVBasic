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
public class parametro {
    String tipoparametro;
    identificador identificador;
    tipovar tipo;
    public parametro(String tp, identificador id, tipovar t){
        tipoparametro = tp;
        identificador = id;
        tipo = t;
    }
    public String toString(int nivel) {
       String tabs = "";
       for(int i =0; i<nivel;i++){
           tabs +=  "\t";
       }
       String re = "\n"+tabs+"parametro\n"+tabs+"\ttipoparametro\n"+tabs+"\t\t"+tipoparametro+identificador.toString(nivel+1)
               +"\n"+tabs+"\ttipo";
      
           re += tipo.toString(nivel+2);
       
       return re;
    }
}
