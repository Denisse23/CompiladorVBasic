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
public class exp {
    
    public valores v1;
    String op;
    exp expresion1;
    exp expresion2;
    public  boolean bandera;
    public exp(exp e1, String ope, exp e){
        expresion1 = e1;
        op = ope;
        expresion2 =e;
    }
   
    public exp(exp v){
        expresion1 = v;
       
       
    }
    
    public exp(valores v){
        v1 = v;
        bandera = true;
       
    }
     public String toString(int nivel) {
      String tabs = "";
       for(int i =0; i<nivel;i++){
           tabs +=  "\t";
       }
       String re ="";
      
            if(expresion1!=null)
             re+= "\n"+tabs+"exp"+expresion1.toString(nivel +1);
            if(op!=null)
            re+= "\n"+tabs+"\t"+op;
            if(expresion2!=null)
                return re+ tabs+expresion2.toString(nivel +1);
            if(v1!=null){
                 return re+ v1.toString(nivel);
            }
       
       
       return re;
    }
}
