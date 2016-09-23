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
public class tipovar {
    identificador identificador;
    String val;
    public tipovar(String v){
        val = v;
    }
    public tipovar(identificador i){
        identificador = i;
    }
    
    public String toString(int nivel) {
      String tabs = "";
       for(int i =0; i<nivel;i++){
           tabs +=  "\t";
       }
       if(identificador!=null){
            return "\n"+tabs+"tipovar"+identificador.toString(nivel+1);
       }else{
           return "\n"+tabs+"tipovar\n"+tabs+"\t"+val;
       }
    }

}
