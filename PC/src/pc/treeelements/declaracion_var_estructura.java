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
public class  declaracion_var_estructura {
    public identificador identificador;
    public tipovar tipo;
    public declaracion_var_estructura(identificador id, tipovar t){
        identificador = id;
        tipo =t;
    }
    
    
    public String toString(int nivel) {
       String tabs = "";
       for(int i =0; i<nivel;i++){
           tabs +=  "\t";
       }
       String re = "\n"+tabs+"declaracion_var"+identificador.toString(nivel+1);
      
           re+= "\n"+tabs+"\ttipo"+tipo.toString(nivel+2);
       
       return re;
    }
}
