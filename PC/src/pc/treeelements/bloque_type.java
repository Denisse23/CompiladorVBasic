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
public class bloque_type extends bloque {
    
    identificador identificador;
    ArrayList<declaracion_var_estructura> declaraciones_var;
    ArrayList<declaracion> declaraciones;
   
    public bloque_type( ){
       declaraciones_var = new ArrayList();
       declaraciones = new ArrayList();
    }
     
     public void setIdentificador(identificador id){
        identificador = id;
    }
     public void addDeclaracion(declaracion d){
        declaraciones.add(d);
    }
    
    public void addDeclaracionVar(declaracion_var_estructura dv){
        declaraciones_var.add(dv);
    }
    
    
    
    
    @Override
    public String toString(int nivel) {
       String tabs = "";
       for(int i =0; i<nivel;i++){
           tabs +=  "\t";
       }
       String re = "\n"+tabs+"bloque_type"+identificador.toString(nivel+1);
       if(declaraciones_var.size()>0){
        re += "\n"+tabs+"\tdeclaraciones_variable";
       for(int i =0; i<declaraciones_var.size();i++){
           re +=  declaraciones_var.get(i).toString(nivel+2);
       }
       }
       
       if(declaraciones.size()>0){
        re += "\n"+tabs+"\tdeclaraciones";
       for(int i =0; i<declaraciones.size();i++){
           re +=  declaraciones.get(i).toString(nivel+2);
       }
       }
      
       return re;
    }
}
