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
public class operacion_logica {
    public condicion c;
    String op;
    condicion condicion1;
    operacion_logica  oplog;
    public operacion_logica(condicion e1, String ope, operacion_logica e){
        condicion1 = e1;
        op = ope;
        oplog =e;
    }
   
    public operacion_logica(condicion v){
        condicion1 = v;
       
       
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
            if(oplog!=null)
                return re+ tabs+oplog.toString(nivel +1);
            
       
       
       return re;
    }
}
