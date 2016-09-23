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
public class identificador extends terminal{
    public String val;
    public identificador(String v){
        val = v;
    }
    public String toString(int nivel) {
      String tabs = "";
       for(int i =0; i<nivel;i++){
           tabs +=  "\t";
       }
       return "\n"+tabs+"identificador\n"+tabs+"\t"+val;
    }
}
