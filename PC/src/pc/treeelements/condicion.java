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
public class condicion {
    public valores_con v1;
    public operacion_logica oplogica;
    String op;
    condicion condicion1;
    condicion condicion2;
    public  boolean bandera;
    public condicion(condicion e1, String ope, condicion e){
        condicion1 = e1;
        op = ope;
        condicion2 =e;
    }
   
    public condicion(condicion e1){
        condicion1 = e1;
    }
    
    public condicion(valores_con v){
        v1 = v;
       // v2 = vv;
       
    }
     public condicion(operacion_logica e1){
        oplogica = e1;
    }
     public String toString(int nivel) {
      String tabs = "";
       for(int i =0; i<nivel;i++){
           tabs +=  "\t";
       }
       String re ="";
      
            if(condicion1!=null)
             re+= "\n"+tabs+"condicion"+condicion1.toString(nivel +1);
            if(op!=null)
            re+= "\n"+tabs+"\t"+op;
            if(condicion2!=null)
                return re+ tabs+condicion2.toString(nivel +1);
            if(v1!=null){
                 return re+ v1.toString(nivel);
            }
            if(oplogica!=null){
                 return re+ oplogica.toString(nivel);
            }
       
       
       return re;
    }
}
