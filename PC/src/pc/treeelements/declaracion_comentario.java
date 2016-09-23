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
public class declaracion_comentario extends declaracion {
    String comentario;
    public declaracion_comentario(String c){
        comentario = c;
    }
    
     public String toString(int nivel) {
       String tabs = "";
       for(int i =0; i<nivel;i++){
           tabs +=  "\t";
       }
       String re = "\n"+tabs+"declaracion_comentario\n"+tabs+"\t"+comentario;
       return re;
    }
}
