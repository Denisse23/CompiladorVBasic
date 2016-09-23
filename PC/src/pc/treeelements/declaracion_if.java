/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.treeelements;

import java.util.ArrayList;

/**
 *
 * @author Denisse
 */
public class declaracion_if extends declaracion{
    operacion_logica oplogica;
    ArrayList<declaracion> declaraciones;
    ArrayList<declaracion> d_else;
    
    public declaracion_if(operacion_logica op,ArrayList<declaracion> d, ArrayList<declaracion> de ){
        oplogica = op;
        declaraciones = d;
        d_else = de;
    }
    
     public declaracion_if(operacion_logica op,ArrayList<declaracion> d ){
        oplogica = op;
        declaraciones = d;
    }
     
    public void addDeclaracion(declaracion d){
        declaraciones.add(d);
    }
    
    public void addDeclaracionelse(declaracion d){
        d_else.add(d);
    }
    
    @Override
    public String toString(int nivel) {
       String tabs = "";
       for(int i =0; i<nivel;i++){
           tabs +=  "\t";
       }
        String re = "\n"+tabs+"declaracion_if"+oplogica.toString(nivel+1);
       
       if(declaraciones!=null && declaraciones.size()>0){
       re+= "\n"+tabs+"\t"+"declaraciones";
       for(int i =0; i<declaraciones.size();i++){
           re +=  declaraciones.get(i).toString(nivel+2);
       }
       }
       if(d_else!=null && d_else.size()>0){
       re += "\n"+tabs+"\t"+"else\n"+tabs+"\t\tdeclaraciones";
       for(int i =0; i<d_else.size();i++){
           re +=  d_else.get(i).toString(nivel+3);
       }
       }
       return re;
    }
}
