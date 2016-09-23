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
public class valores {
    exp expresion;
    String cadenatruefalse;
    Integer numero;
    identificador id;
    
    public valores(exp e){
        expresion = e;
    }
    public valores(String c){
        cadenatruefalse = c;
    }
    public valores(Integer i){
        numero = i;
    }
    public valores(identificador i){
        id = i;
    }
    
     public String toString(int nivel) {
      String tabs = "";
       for(int i =0; i<nivel;i++){
           tabs +=  "\t";
       }
       if(id!=null){
            return id.toString(nivel);
       }else if(cadenatruefalse!=null){
           return "\n"+tabs+cadenatruefalse;
       }else if(numero!=null){
           return "\n"+tabs+numero;
       }else if(expresion!=null){
           return expresion.toString(nivel +1);
       }
       return "";
    }
    
    
}
