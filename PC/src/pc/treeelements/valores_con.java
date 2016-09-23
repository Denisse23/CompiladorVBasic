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
public class valores_con {
    operacion_logica oplogica;
    String cadenatruefalse;
    Integer numero;
    identificador id;
    
    public valores_con(operacion_logica e){
        oplogica = e;
    }
    public valores_con(String c){
        cadenatruefalse = c;
    }
    public valores_con(Integer i){
        numero = i;
    }
    public valores_con(identificador i){
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
       }else if(oplogica!=null){
           return oplogica.toString(nivel +1);
       }
       return "";
    }
    
}
